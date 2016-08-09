package classes_package;


import umontreal.iro.lecuyer.randvar.ExponentialGen;
import umontreal.iro.lecuyer.randvar.RandomVariateGen;
import umontreal.iro.lecuyer.rng.MRG32k3a;
import umontreal.iro.lecuyer.rng.RandomStream;



public class Server {

	Queue queue;
	double nextDequeueTime=0.0;
	Integer ServerN, thresholdID, i, reserved;
	RandomVariateGen genServ;
	double 	dequeuedTime=0,serviceTime=0,mu;
	String name, reservationType;
	Manager manager;
	RandomStream rs = new MRG32k3a();


	public Server(Queue mainQueue, double muMain, Integer ServerNumber, int thresholdid, String serverName, Manager mainManager, int r, String resType)
	{
		queue=mainQueue;
		ServerN=ServerNumber;
		mu=muMain;
		thresholdID=thresholdid;
		name=serverName;
		manager=mainManager;
		reserved=r;
		reservationType=resType;
		genServ=new ExponentialGen (new MRG32k3a(), mu);;
	}

	public void performDequeue() {
		 
				if(queue.getQueueSize()>0){
						Request request = queue.dequeue();
						if(dequeuedTime+serviceTime<request.arrivTime)
						{
								dequeuedTime=request.arrivTime; //Time in queue=0
						}else{
								dequeuedTime=dequeuedTime+serviceTime;
						}
							//manager.storeSingleWaitInQueue(dequeuedTime-request.arrivTime);
						manager.dequeuedPacketsInInterval++;
						queue.addWaitingTime(dequeuedTime-request.arrivTime);
				
						
						
						if(((int) (Math.random()*10))%2==0){
							genServ = new ExponentialGen (rs, mu);
							rs.resetStartStream();
						}
						serviceTime=genServ.nextDouble();
				
						manager.serviceTimeTot+=serviceTime;
						manager.sommaResponseTimes+=(dequeuedTime-request.arrivTime)+serviceTime;
						//System.out.println("Sono il server:"+ServerN+"dequeued pacchetto e lo servo in "+serviceTime+" secondi");
						nextDequeueTime=dequeuedTime+serviceTime;
						//request.queuedTime=	dequeuedTime-request.arrivTime;
						//request.servTime=serviceTime;
							/*if(queue.getQueueSize()>0){
								System.out.println(" Request n°"+request.requestNumber+" dequeued time:"+ dequeuedTime+"("+ServerN+") in queue for:"+request.queuedTime+" seconds. Scheduled service time:"+request.servTime
								+ " Queue: " + queue.getQueueSize() +"\n");
							}*/
				}				
		   
	}
}
