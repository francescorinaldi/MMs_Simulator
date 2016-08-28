package classes_package;


import umontreal.iro.lecuyer.randvar.ExponentialGen;
import umontreal.iro.lecuyer.randvar.RandomVariateGen;
import umontreal.iro.lecuyer.rng.MRG32k3a;

import java.io.IOException;
import java.util.LinkedList;


public class Arrivals{
	
	
	RandomVariateGen genArr;
	Integer totalRequests, requestsInInterval;
	double currentTime, lambdaSwitchTimestamp, nextArrival,timeStamp;
	Boolean simulationIsRunning, successfullyEnqueued,checkQueueResult;
	Queue queue;
	Manager manager;

	public Arrivals (Manager mainManager, Queue mainQueue) {
		currentTime=0.0;
		lambdaSwitchTimestamp=0.0;
		totalRequests=0;
		queue=mainQueue;
		manager=mainManager;
	}
	

	public double newSimulation(double lambda, double interval,LinkedList <Server> serversList) throws IOException{
		
		genArr = new ExponentialGen (new MRG32k3a(), lambda/interval); //new exponential with new lambda
		Request request = new Request(); //new Request object
		//System.out.println(genArr.toString()+" n° iterazione: "+iterazione+"\n");
		simulationIsRunning=true;
		requestsInInterval=0;
		

		while(simulationIsRunning){
			
			timeStamp=currentTime;
			nextArrival=genArr.nextDouble();
			currentTime+=nextArrival;
			
			if(currentTime<=(lambdaSwitchTimestamp+interval)){
				
				manager.newCurrentTime(currentTime);
				
				successfullyEnqueued=false;
				totalRequests++;
				requestsInInterval++;
				request = new Request(); 
		        request.arrivTime =currentTime;
		        request.requestNumber=totalRequests;
		        manager.generatedPacketsInInterval++;

		        do{
		        	if((currentTime<=manager.nextDequeueTime())||(queue.getQueueSize()==0)){
		        		successfullyEnqueued=true;
		        		//debugging
		        		request.servListSize=manager.serversList.size();
		        		request.nextDequeueTimeAvailable=manager.nextDequeueTime();
		        		request.queueSize=queue.waitList.size();
		        		request.packetsNumber=totalRequests;
		        		//-----finedebug
		 		        	if(queue.enqueue(request)){
			 		        		//manager.storeSlistSize(manager.serversList.size());
		 		        		
		 		        	//	System.out.println("Request number "+TotalRequests+", enqueued with arrival time: "+currentTime+" - Queue: "+queue.getQueueSize()+"\n");
		 		        	}else{
		 		        		//System.out.println("Request number "+totalRequests+", enqueued with arrival time: "+currentTime+" - Queue: "+queue.getQueueSize()+ " - LOST, arrivalRate:"+lambda/interval);
		 		        	}
		        	}
		        	else{
		        		checkQueueResult=manager.checkQueue();
		        	}
		        	
		        }while(!successfullyEnqueued);
			}
			else{
				currentTime=timeStamp;
				lambdaSwitchTimestamp=currentTime;
				simulationIsRunning=false;
			}
		}
		return currentTime;
	}
	
	

	
	
}
