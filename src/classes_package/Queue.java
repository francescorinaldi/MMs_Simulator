package classes_package;

import java.util.concurrent.BlockingDeque;
import java.util.concurrent.LinkedBlockingDeque;


public class Queue {
	
	private boolean infiniteQueue;

	BlockingDeque<Request> waitList;
	Integer size, lostRequests=0,maxDimension=0;
	double totalWaitingTimeinQueue=0.0, maxWaitingTimeInQueue=0;

	private boolean enqueueLimited(Request request){
		
		if(waitList.size()>maxDimension){
			maxDimension=waitList.size();
		}
		
		if(waitList.size()<size){
			waitList.addLast(request);	
			return true;
		}else{
			lostRequests++;
			return false;
		}
	}
	private boolean enqueueUnlimited(Request request){
		waitList.addLast(request);
		if(waitList.size()>maxDimension){
			maxDimension=waitList.size();
		}
			
		return true;
	}	
	
	public void addWaitingTime(double time){
		if(time>maxWaitingTimeInQueue){
			maxWaitingTimeInQueue=time;
			
		}
		totalWaitingTimeinQueue+=time;
	}
	
	public boolean enqueue(Request request){
		if(infiniteQueue){
			return this.enqueueUnlimited(request);
		}
		else{
			return this.enqueueLimited(request);
		}
	}
	
	public Request dequeue(){	
			return waitList.removeFirst();		
	}
	
	public Queue(Integer queueSize){
		waitList = new LinkedBlockingDeque<Request>();	
		size=queueSize;
		infiniteQueue=false;
		}
	
	public Queue(){
		waitList = new LinkedBlockingDeque<Request>();	
		infiniteQueue=true;
		}

	
	public int getQueueSize(){
			return waitList.size();
	}
}
