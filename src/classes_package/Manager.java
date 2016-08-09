package classes_package;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.LineNumberReader;
import java.io.PrintWriter;
import java.util.LinkedList;
import java.util.ListIterator;

import org.jfree.ui.RefineryUtilities;




public class Manager {

	Integer nextServerToServe;
	LinkedList <Server> serversList;
	ServersHandler handler;
	double arrivalRate=0.0, serviceTimeTot=0.0, sommaServiceTimeIntervallo=0.0, muSum, totalCost=0.0, sommaResponseTimes=0.0;
	Queue mainQueue;
	Arrivals arrivals_generator;
	double interval;
	int i, percent=0, index=0,filter=0, elapsedIntervals=0,lastThresholdInterval=0, debugLevel=1, dequeuedPacketsInInterval, generatedPacketsInInterval;
	int simulationNumber;
	//double[] data = new double[40000000];
	//int[] data = new int[40000000];
	//RandomVariateGen genServ = new ExponentialGen (new MRG32k3a(), 20.34);
	RepetitaIuvant repet;

	
	public Manager(double intrvl, LinkedList <Server> servList ,Queue queue, ServersHandler mainHandler, int simNumber, RepetitaIuvant repe){
		serversList=servList;
		handler=mainHandler;
		mainQueue = queue; 
		arrivals_generator = new Arrivals (this,queue);
		interval=intrvl;
		simulationNumber=simNumber;
		repet=repe;
	}
	
	public void startManager () 
		{
			double lambda=0.0;
			BufferedReader br = null;
			double currentTime=0.0;
			int linesNumber=0;
			double awt, peak, availability;
			linesNumber=this.rowsInTheFile("traccia10seconds.txt");
			
			try {
	
				String sCurrentLine;
				br = new BufferedReader(new FileReader("traccia10seconds.txt"));
				
			//creazione grafici dinamici
				final DynamicGrapherReconfigurations reconfigurationsGraph = new DynamicGrapherReconfigurations("Reconfigurations");
				final DynamicGrapherRequestsInQueue requestsInQueueGraph = new DynamicGrapherRequestsInQueue("Requests in Queue");
				final DynamicGrapherArrivalRate arrivalRateGraph = new DynamicGrapherArrivalRate("Arrival Rate");
		        reconfigurationsGraph.pack();
		        RefineryUtilities.centerFrameOnScreen(reconfigurationsGraph);
		        reconfigurationsGraph.setVisible(true);
		        
		        RefineryUtilities.positionFrameRandomly(requestsInQueueGraph);
		        requestsInQueueGraph.pack();
		        requestsInQueueGraph.setVisible(true);
		        
		        RefineryUtilities.positionFrameRandomly(arrivalRateGraph);
		        arrivalRateGraph.pack();
		        arrivalRateGraph.setVisible(true);
		        
	       // DebugOnFile debug= new DebugOnFile(this);
		        
		      //This cycle is analyzing every row in the log file.
				while ((sCurrentLine = br.readLine()) != null) { 
					    sommaServiceTimeIntervallo=0.0;
					    dequeuedPacketsInInterval=0;
					    generatedPacketsInInterval=0;
					    muSum=0.0;
						elapsedIntervals++;
						this.printProgressBar(elapsedIntervals, linesNumber);
							
						lambda=Integer.parseInt(sCurrentLine);
						if(lambda==0){
							lambda=1;
						}
						arrivalRate=lambda/interval;
						
						for(Server serv: serversList){
							muSum+=serv.mu;
						}
						
						//ogni 10 intervalli aggiorno i grafici
					if(elapsedIntervals%10==0){
							reconfigurationsGraph.newValue(arrivals_generator.totalRequests, serversList.size());
							requestsInQueueGraph.newValue(arrivals_generator.totalRequests, mainQueue.waitList.size());
							arrivalRateGraph.newValue(arrivals_generator.totalRequests, arrivalRate, muSum);
						}
						currentTime=arrivals_generator.newSimulation(lambda,interval, serversList);

						//serviceTimeMedio=sommaServiceTimeIntervallo/arrivals_generator.requestsInInterval;
						//debug onfile utilizzato per scovare eventuali errori nel generatore di esponenziali
//						if((arrivals_generator.totalRequests>96000000)&&(arrivals_generator.totalRequests<99000000)){
//							debug.writeOnFile();
//						}
					
						handler.newLambda(arrivalRate, currentTime);
						
				}
				
				//After Arrivals has finished there is the need to keep dequeuing until the queue is empty
				while(mainQueue.getQueueSize()>0){
					this.nextDequeueTime();
					this.checkQueue();
				}
				
				
				Server serv;
				ListIterator<Server> serversIterator = serversList.listIterator();
				while(serversIterator.hasNext())
				{
					serv=serversIterator.next();
					//System.out.println("lunghezza thresholds"+handler.thresholds.size());
					for(Threshold ts: handler.activatedThresholds){
						if(serv.thresholdID==ts.Id){
							System.out.println("lo activationTime: "+ts.activationTime+" id: "+ts.Id);
							if(serv.reserved==1){
								handler.paymentsHandler.calculateReservedCost(serv, ts.activationTime , currentTime);

							}else{
								handler.paymentsHandler.calculateOnDemandCost(serv, ts.activationTime , currentTime);

							}
						}

					}
					serversIterator.remove();
				}
				
				for(VMReservedPayment payment: handler.typeOfReservedVMs.get(0).VMPayments){
					if(payment.reservation_type.equals("medium")){
						totalCost+=3*payment.periodic_payment*(currentTime/94670778);

					}
					if(payment.reservation_type.equals("light")){
						totalCost+=2*payment.periodic_payment*(currentTime/94670778);

					}if(payment.reservation_type.equals("heavy")){
						totalCost+=payment.periodic_payment*(currentTime/94670778);

					}
					
				}
				
				
				
				
				
				PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter("risultato_simulaz_"+(int)handler.averageBootingTime+"_"+handler.isteresiLength+"_"+(int)handler.firstDeactivationTimeOut+".txt", true)));
				out.println("------------------------------------------------ "+(simulationNumber+1)+":");
				out.println("Numero massimo pacchetti in coda: "+mainQueue.maxDimension);
				out.println("Tempo medio attesa in coda:"+String.format("%.4f",mainQueue.totalWaitingTimeinQueue/arrivals_generator.totalRequests)+" secondi");
				out.println("Response time:"+String.format("%.4f",sommaResponseTimes/arrivals_generator.totalRequests)+" secondi");
				out.println("Service time:"+String.format("%.4f",serviceTimeTot/arrivals_generator.totalRequests)+" secondi");
				out.println("Picco di attesa: "+String.format("%.2f", mainQueue.maxWaitingTimeInQueue)+" secondi");
				out.println("Costo Totale: "+String.format("%.2f", totalCost)+" $");
				out.println("Reconfigurations: "+handler.activatedBuckets);
				out.println("Availability: "+String.format("%.4f",100.00-((double)mainQueue.lostRequests/(double)arrivals_generator.totalRequests)*100.0)+"%");
				out.println("Pacchetti totali: "+arrivals_generator.totalRequests);
				//System.out.println("currenttime finale: "+currentTime);
				
				//debug.writeOnFile();
				 out.close();
				 
				
				//awt, peak, totalCost, availability
				 awt=mainQueue.totalWaitingTimeinQueue/arrivals_generator.totalRequests;
				 peak=mainQueue.maxWaitingTimeInQueue;
				 availability=100.00-(((double)mainQueue.lostRequests/(double)arrivals_generator.totalRequests)*100.0);
				// segna i massimi
				 if(awt>repet.maxAwt) repet.maxAwt=awt;
				 if(peak> repet.maxPeak) repet.maxPeak=peak;
				 if(totalCost>repet.maxCost) repet.maxCost=totalCost;
				 if(availability> repet.maxAvailability) repet.maxAvailability= availability;
				 if(handler.activatedBuckets>repet.maxReconf) repet.maxReconf=handler.activatedBuckets;
				 //segna i minimi
				 if(awt<repet.minAwt) repet.minAwt=awt;
				 if(peak< repet.minPeak) repet.minPeak=peak;
				 if(totalCost<repet.minCost) repet.minCost=totalCost;
				 if(availability<repet.minAvailability) repet.minAvailability= availability;
				 if(handler.activatedBuckets<repet.minReconf) repet.minReconf=handler.activatedBuckets;
				 //sommeeeeee
				 repet.totAvailability+=availability;
				 repet.totAwt+=awt;
				 repet.totCost+=totalCost;
				 repet.totPeak+=peak;
				 repet.totReconf+=handler.activatedBuckets;
				return;
				
			}
		    catch (IOException e) { 
		    	System.out.print("Impossible to load the workload log file. The specified file-name does not exist");}
		    finally {
					try {
						if (br != null)br.close();
					} catch (IOException ex) {
						ex.printStackTrace();
					}
		    }
		}
	
	
	public void printProgressBar(int readLines, int linesNumber){
		
		if(readLines%(linesNumber/80)==0)
		{
			percent++;
			 StringBuilder bar = new StringBuilder("[");

			    for(i = 0; i < 80; i++){
			        if( i < percent){
			            bar.append("=");
			        }else if( i == percent){
			            bar.append(">");
			        }else{
			            bar.append(" ");
			        }
			    }

			    bar.append("]   ");
			    System.out.print("\r");
			    System.out.print(bar.toString());
		}
	}
	
	public int rowsInTheFile(String filename){
		LineNumberReader lnr;
		int rows=0;
		try {
			lnr = new LineNumberReader(new FileReader(new File(filename)));
			try {
				lnr.skip(Long.MAX_VALUE);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			if(lnr.getLineNumber()>0){
				rows=lnr.getLineNumber();
			}
			try {
				lnr.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		return  rows+1;
	}
	
	public  Boolean checkQueue(){//check if it is time to perform a new dequeue from the Queue
		serversList.get(nextServerToServe).performDequeue();
		return true;	
	}
	
	public synchronized double nextDequeueTime(){//this function returns the next dequeue time(the minimum) and set the next Server which will serve the next request
		double time=9999.0;
		if(serversList.size()>0){
			time=serversList.getFirst().nextDequeueTime;
		}
		for(Server S: serversList) {
			if(time>=S.nextDequeueTime){
				time=S.nextDequeueTime;
				this.nextServerToServe=serversList.indexOf(S);
			}
		}
		return time;
	}

	public void newCurrentTime(double currentTime){
		handler.newCurrentTime(currentTime, arrivalRate);
	}

}
