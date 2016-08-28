package classes_package;

import umontreal.iro.lecuyer.randvar.ExponentialGen;
import umontreal.iro.lecuyer.randvar.RandomVariateGen;
import umontreal.iro.lecuyer.rng.MRG32k3a;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.ListIterator;


public class ServersHandler {
	
	Manager mainManager;
	Queue mainQueue;
	LinkedList <Server> serversList = new LinkedList<Server>();
	int serverNumber=1, choice;
	//ArrayList<ReservedVM> reservedVMs = new ArrayList<ReservedVM>();
	ArrayList<ReservedVM> reservedToActivate = new ArrayList<ReservedVM>();

	ArrayList<VMReservedType> typeOfReservedVMs = new ArrayList<VMReservedType>();
	
	ArrayList<VMOnDemand> ondemandVMs = new ArrayList<VMOnDemand>();  //Different kind of VMs that have to be reserved(OnDemand).

	ArrayList<Threshold> thresholds= new ArrayList<Threshold>();//Theoretic values of thresholds fetched from XML file
	ArrayList<Threshold> activatedThresholds= new ArrayList<Threshold>();//Thresholds already used to activate related Servers
	ArrayList<Threshold> thresholdsToBeActivated= new ArrayList<Threshold>();//thresholds to be activated after boot time
	ArrayList<Threshold> thresholdsToBeDeactivated= new ArrayList<Threshold>();//thresholds to be activated after boot time

	int activatedBuckets=0, isteresiLength;
	double averageBootingTime, firstDeactivationTimeOut, delayDeactivationTimeOut, minBillingTime;
	PaymentsHandler paymentsHandler;
	
	public ServersHandler(){
		
	}
	
	public void main (int simulationNumber) 
	{
		//double interval=10.0;//Interval in seconds between two values in the log file.
		averageBootingTime=600.0;
		mainQueue = new Queue(1000); //A global queue for all the classes is instantiated 
		minBillingTime=3600.0;
	//	int repe=1;
		//Get the necessary information about VMs, thresholds and payments
		XMLparser.XMLReserved(typeOfReservedVMs, thresholds);//Ask the XML to populate reservedVMs and Payments for the reservedVms
		XMLparser.XMLOnDemand(ondemandVMs, thresholds);//Ask the XMLparser to populate singleVMs and thresholds, parsing the XML OnDemand file.

		//Rilevazione picchi prima di invocare Manager
		//this.peaksDiscovering(reservedVMs, interval);
		
		//Start the Manager
//		mainManager=new Manager(interval, serversList, mainQueue,this,simulationNumber, repe);
//		paymentsHandler = new PaymentsHandler(typeOfReservedVMs, ondemandVMs, mainManager, minBillingTime);
//
//		mainManager.startManager();
		
		return;

	}

	
	public void newLambda(double arrivalRate, double currentTime){
		
			this.searchForNewBucketsToActivate(arrivalRate, currentTime);
			this.searchForNewBucketsToDeactivate(arrivalRate, currentTime);
	}
	
	
	public void runNewServer(double muParameter,Integer serverNumber, int thresholdID, String name, int r, String reservationType){
		Server S = new Server(mainQueue, muParameter, serverNumber,thresholdID, name, mainManager, r, reservationType);
		serversList.add(S);
	}
	
	
	public void searchForNewBucketsToActivate(double arrivalRate, double currentTime){
		int index;
		Threshold ts,selectedTs;
		RandomVariateGen genArr = new ExponentialGen (new MRG32k3a(), 1/(averageBootingTime)); //average booting time
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
				//System.out.println("Threshold a : "+(maxReservedWorkload+ts.WorkLoad)+" si attivera' a : "+selectedTs.activationTime);

				thresholdsIterator.remove();
				//this.newCurrentTime(currentTime);


			}
		}
	}
	
	public void searchForNewBucketsToDeactivate(double arrivalRate, double currentTime){
		int index;  //,smallVMs=0,mediumVMs=0,largeVMs=0,xlargeVMs=0;
		Server serv;
		Threshold ts;
		ListIterator<Threshold> activatedThresholdsIterator = activatedThresholds.listIterator();
		while(activatedThresholdsIterator.hasNext())
		{
			ts=activatedThresholdsIterator.next();
			
			if(arrivalRate<ts.WorkLoad)
			{

				ListIterator<Server> serversIterator = serversList.listIterator();

				while(serversIterator.hasNext())
				{
					serv=serversIterator.next();
					if(serv.thresholdID==ts.Id)
					{
						if(serv.reserved==1)
						{
							paymentsHandler.calculateReservedCost(serv, ts.activationTime, currentTime);
						}else{
							paymentsHandler.calculateOnDemandCost(serv, ts.activationTime,currentTime);
						}
						serversIterator.remove();
					}
				}

				index=activatedThresholds.indexOf(ts);
				thresholds.add(activatedThresholds.get(index));
				activatedThresholdsIterator.remove();

				
			}
		}
	}
	
	public void newCurrentTime(double currentTime, double arrivalRate){//Every time a new request arrives into the queue, Arrivals class invoke this method with the currentTime
												//to check whether there is some new VM to activate.

			int index,i ; //,smallVMs=0,mediumVMs=0,largeVMs=0,xlargeVMs=0;
			Threshold ts;
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
									
									this.runNewServer(ondemandVMs.get(index).mu, serverNumber,ts.Id,ondemandVMs.get(index).name, vm.r,vm.reservationType);
									//System.out.println("-> attivo una OnDemand "+vm.vm_name+" con mu:"+ondemandVMs.get(index).mu);
								/*	if(ondemandVMs.get(index).name.equals("m1-small")){
										smallVMs++;
									}else{
										if(ondemandVMs.get(index).name.equals("c1-medium")){
											mediumVMs++;
										}else{
											if(ondemandVMs.get(index).name.equals("m1-large")){
												largeVMs++;
											}else{
												if(ondemandVMs.get(index).name.equals("c1-xlarge")){
													xlargeVMs++;
												}
											}
											
										}
									}*/
									
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
					//System.out.println("Elementi nell'activated: "+activatedThresholds.size()+" currentTime: "+currentTime);
	
				}
			}
			


	}
}
