package classes_package;



import umontreal.iro.lecuyer.randvar.ExponentialGen;
import umontreal.iro.lecuyer.randvar.RandomVariateGen;
import umontreal.iro.lecuyer.rng.MRG32k3a;

import java.util.ListIterator;


public class ServersHandlerDeactivationThreshold extends ServersHandler {
	
	boolean useSuggestedSlots=false;//se TRUE non verrà utilizzata la variabile delayDeactivationTimeOut
	boolean useOldSchool=true;
	boolean usaSlowStart=false;
	int ultimeAccensioni=1,lastActivationInterval=0;
	double lastSlowStartPhase=0.0;
	RandomVariateGen genArr;
	public ServersHandlerDeactivationThreshold(){

	}

	
	//classe rinominabile in ServersHandlerDeactivationThreshold
	public void main (double booting, double timeout, int isteresi, Simulation simulation, int simulationNumber) 
	{
		double interval=10.0;//Interval in seconds between two values in the log file.
		mainQueue = new Queue(1000); //A global queue for all the classes is instantiated 
		int i;
		isteresiLength=isteresi;
		averageBootingTime=booting;
		firstDeactivationTimeOut=timeout;
		delayDeactivationTimeOut=0.0;
		minBillingTime=3600.0;
		genArr = new ExponentialGen (new MRG32k3a(), 1/(averageBootingTime));
		//Get the necessary information about VMs, thresholds and payments
		XMLparser.XMLReserved(typeOfReservedVMs, thresholds);//Ask the XML to populate reservedVMs and Payments for the reservedVms
		XMLparser.XMLOnDemand(ondemandVMs, thresholds);//Ask the XMLparser to populate singleVMs and thresholds, parsing the XML OnDemand file.


		for (Threshold t : thresholds){
			System.out.println("ID soglia: "+t.Id+". Attiva a workload: "+t.WorkLoad+". Da attivare: ");
			for (SingleVM vm : t.VMsToActivate){
				System.out.println("N°: "+vm.num+" di tipo: "+vm.vm_name);
			}
		}
		for(i=0;i<(thresholds.size()-isteresiLength);i++)
		{
			if(i<isteresiLength){
				thresholds.get(i).deactivationWorkload=0;
			}
			
			thresholds.get(i+isteresiLength).deactivationWorkload=thresholds.get(i).WorkLoad;
		}
		for( Threshold ts: thresholds){
			System.out.println("Soglia con id:"+ts.Id+ "e workload teorico:"+ts.WorkLoad+" e deactivation :"+ts.deactivationWorkload);
		}
		
		//Start the Manager
		mainManager=new Manager(interval, serversList, mainQueue,this,simulationNumber, simulation);
		paymentsHandler = new PaymentsHandler(typeOfReservedVMs, ondemandVMs, mainManager, minBillingTime);

        mainManager.startManager();
		return;

	}

	// invoked by manager when it starts a new interval
	public void newLambda(double arrivalRate, double currentTime){
		
			this.searchForNewBucketsToActivate(arrivalRate, currentTime);
			this.setNewBucketsForDeactivation(arrivalRate, currentTime);		
	}

    //i mark the bucket to be rechecked after an hour if lambda is below the deactivationworkload
	public void setNewBucketsForDeactivation(double arrivalRate, double currentTime){

		Threshold ts;
		int index;
		Threshold selectedTs;
		ListIterator<Threshold> activatedThresholdsIterator = activatedThresholds.listIterator();
		while(activatedThresholdsIterator.hasNext())
		{
			ts=activatedThresholdsIterator.next();
			
			if(arrivalRate<=ts.deactivationWorkload)//(ts.WorkLoad-(ts.WorkLoad*0.20)))
			{
					index=activatedThresholds.indexOf(ts);
					//System.out.println("INDEX:"+index);
					selectedTs=activatedThresholds.get(index);
					selectedTs.arrivalRateValue=arrivalRate;
				
					selectedTs.deactivationTime=currentTime+firstDeactivationTimeOut;

					thresholdsToBeDeactivated.add(selectedTs);

					activatedThresholdsIterator.remove();
			}
		}
		this.searchForNewBucketsToDeactivate(currentTime, arrivalRate);
	}

    //It calculates the time when it will end the current billing time, to update the deactivationTime of a bucket of VMs
	public double nextDeactivationSlot(double tsactivationTime, double currentTime){
		double nextDeactivationSlot=tsactivationTime;
		boolean minFound=false;
		while(!minFound){
			nextDeactivationSlot+=minBillingTime;
			if(nextDeactivationSlot>=currentTime)
			{
				minFound=true;
			}
		}
		
		return nextDeactivationSlot;
		
	}
	
	//it marks new bucket to activate after the booting time(120 seconds)
	public void searchForNewBucketsToActivate(double arrivalRate, double currentTime){
		int index;
		Threshold ts,selectedTs;
		if(((int) (Math.random()*10))%2==0){
			genArr = new ExponentialGen (new MRG32k3a(), 1/(averageBootingTime));
		}
		ListIterator<Threshold> thresholdsIterator = thresholds.listIterator();
		while(thresholdsIterator.hasNext())
		{
			ts=thresholdsIterator.next();
			index=-1;
			if(arrivalRate>=ts.WorkLoad)
			{
				index=thresholds.indexOf(ts);
				selectedTs=thresholds.get(index);
				selectedTs.activationTime=currentTime+genArr.nextDouble();
				selectedTs.arrivalRateValue=arrivalRate;
				thresholdsToBeActivated.add(selectedTs);

				thresholdsIterator.remove();
				if(usaSlowStart){
					if((mainManager.elapsedIntervals-lastActivationInterval<10)
							&&(mainManager.elapsedIntervals>20)
							&&(ultimeAccensioni<4)
							&&(arrivalRate>100)
							&&(currentTime-lastSlowStartPhase>360)){
						for(int i=0;i<=ultimeAccensioni*2;i++){
							if(thresholdsIterator.hasNext()){
								ts=thresholdsIterator.next();
								index=thresholds.indexOf(ts);
								selectedTs=thresholds.get(index);
								selectedTs.activationTime=currentTime+genArr.nextDouble();
								selectedTs.arrivalRateValue=arrivalRate;
								thresholdsToBeActivated.add(selectedTs);	
							}
						}
						lastSlowStartPhase=currentTime;
						ultimeAccensioni++;
						System.out.println("aumento ultime accensioni a "+ultimeAccensioni);
						System.out.println("ne ho accesi "+ultimeAccensioni*2);
					}else{
						if(currentTime-lastSlowStartPhase>720) ultimeAccensioni=1;
					}
					lastActivationInterval=mainManager.elapsedIntervals;
				}
			}
		}
	}
	
	//i decide whether to shut down VMs after their deactivation time according to the current arrival rate
	public void searchForNewBucketsToDeactivate(double currentTime, double arrivalRate){
		int index;
		Server serv;
		Threshold ts;
		ListIterator<Threshold> toBeDeactivatedThresholdsIterator = thresholdsToBeDeactivated.listIterator();
		while(toBeDeactivatedThresholdsIterator.hasNext())
		{
			ts=toBeDeactivatedThresholdsIterator.next();
			
			if(currentTime>=ts.deactivationTime)
			{
				if(arrivalRate<ts.deactivationWorkload)
				{

						ListIterator<Server> serversIterator = serversList.listIterator();
						while(serversIterator.hasNext())
						{
							serv=serversIterator.next();
							if(serv.thresholdID==ts.Id)
							{
								if(serv.reserved==1)
								{
									paymentsHandler.calculateReservedCost(serv,ts.activationTime, currentTime);
								}else{
									paymentsHandler.calculateOnDemandCost(serv,ts.activationTime, currentTime);
								}
								serversIterator.remove();
							}
						}

						index=thresholdsToBeDeactivated.indexOf(ts);
						thresholds.add(thresholdsToBeDeactivated.get(index));
						toBeDeactivatedThresholdsIterator.remove();
	
				}
				else{
					if(useOldSchool){
						index=thresholdsToBeDeactivated.indexOf(ts);
						activatedThresholds.add(thresholdsToBeDeactivated.get(index));
						toBeDeactivatedThresholdsIterator.remove();
					}
					else{
							if(useSuggestedSlots){
								ts.deactivationTime=this.nextDeactivationSlot(ts.activationTime, currentTime);
								//System.out.println("prossimo deactivationtime calcolato: "+ts.deactivationTime);
							}else{
								ts.deactivationTime+=delayDeactivationTimeOut;
							//	System.out.println("prossimo deactivationtime calcolato: "+ts.deactivationTime);
		
							}
					}
				}
				

			}
		}
	}

    //each time a new request arrives in the queue, the Arrivals class invokes this function to check for new bucket to be activated
	public void newCurrentTime(double currentTime, double arrivalRate){//Every time a new request arrives into the queue, Arrivals class invoke this method with the currentTime
												//to check whether there is some new VM to activate.

			int index,i ; //,smallVMs=0,mediumVMs=0,largeVMs=0,xlargeVMs=0;
			Threshold ts;
			
			this.searchForNewBucketsToDeactivate(currentTime, arrivalRate);

			
			ListIterator<Threshold> thresholdsIterator = thresholdsToBeActivated.listIterator();
			while(thresholdsIterator.hasNext())
			{
				ts=thresholdsIterator.next();
				index=-1;
				if(currentTime>=ts.activationTime)
				{
					for(SingleVM vm: ts.VMsToActivate)
					{	
							for(VMOnDemand vm_demand: ondemandVMs){//search for the mu value of VM type named as vm.vm_name
								if(vm_demand.name.equals(vm.vm_name))
								{
									index=ondemandVMs.indexOf(vm_demand);
								}
							}
							if(index>=0)
							{
								for(i=0;i<vm.num;i++)
								{
									this.runNewServer(ondemandVMs.get(index).mu, serverNumber,ts.Id,ondemandVMs.get(index).name,vm.r,vm.reservationType);			
									serverNumber++;
								}
								
							}
							
					}
					//System.out.println("/////[ON]///// THRESHOLD ID: "+ts.Id+" --------------------------------------------------------------------------------");
				//	System.out.println("Attivate: "+smallVMs+" small, "+mediumVMs+" medium, "+largeVMs+" large, "+xlargeVMs+" xlarge.");
			//		System.out.println("Accensione registrata con arrival-rate: "+ts.arrivalRateValue); //+" da attivarsi a: "+ts.activationTime+" e ora e':"+currentTime);
					index=thresholdsToBeActivated.indexOf(ts);
					activatedThresholds.add(thresholdsToBeActivated.get(index));
					thresholdsIterator.remove();
					activatedBuckets++;
					//System.out.println("Elementi nell'activated: "+activatedThresholds.size()+" currentTime: "+currentTime+"--"+this.activatedBuckets);
	
				}
			}
			
		
	}
}
