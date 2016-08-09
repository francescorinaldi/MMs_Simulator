package classes_package;

import java.util.ArrayList;

public class PaymentsHandler {
	
	ArrayList<VMReservedType> reservedVMs;
	ArrayList<VMOnDemand> onDemandVMs;
	Manager manager;
	double minBillingTime;
	
	public PaymentsHandler(ArrayList<VMReservedType> reserved,ArrayList<VMOnDemand> demand, Manager mainManager, double minBilling){
		
		reservedVMs=reserved;
		onDemandVMs=demand;
		manager=mainManager;
		minBillingTime=minBilling;
	}
	
	public void calculateReservedCost(Server serv, double activationTime, double currentTime){
		double activityPeriod=0.0;
		int i=1;
		boolean newActivityFound=false;
		
		activityPeriod=currentTime-activationTime;
		while(!newActivityFound){
			if((i*minBillingTime)>activityPeriod){ 
				newActivityFound=true;
			}
			i++;
		}
		activityPeriod=(i*minBillingTime)/(3600.0);
		for(VMReservedType reserved: reservedVMs)
		{

			if(serv.name.equals(reserved.name) ){

				for(VMReservedPayment payment: reserved.VMPayments)
				{

					if(serv.reservationType.equals(payment.reservation_type)){
						//System.out.println("aggiungo un periodic: "+payment.periodic_payment);
						manager.totalCost+=(payment.running_payment*activityPeriod);
					}
				}
			}
		}
		
		
	}
	public void calculateOnDemandCost(Server serv, double activationTime, double currentTime){
		double activityPeriod=0.0;
		int i=0;
		boolean newActivityFound=false;
		
		activityPeriod=currentTime-activationTime;
		//System.out.println("Nuovo activity period che vale prima del ricalcolo: "+(activityPeriod/3600.0)+" con activation "+activationTime);

		while(!newActivityFound){
			i++;

			if((i*minBillingTime)>activityPeriod){ 
				newActivityFound=true;
			}
		}
		activityPeriod=(i*minBillingTime)/(3600.0);
		//System.out.println("Nuovo activity period che vale "+activityPeriod);
		for(VMOnDemand demand: onDemandVMs){
			if(serv.name==demand.name){	
				manager.totalCost+=(demand.running_payment*activityPeriod);
			}
		}
		
	}

}
