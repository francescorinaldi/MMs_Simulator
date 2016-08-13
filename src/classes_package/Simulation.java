package classes_package;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class Simulation{
	int i, maxReconf=0, minReconf=999999, totReconf=0;
	double maxAwt=0.0, maxAvailability=0.0, maxPeak=0.0, maxCost=0.0;
	double minAwt=999.0, minAvailability=9999.0, minPeak=9999.0, minCost=999999.0;
	double totAwt=0.0, totAvailability=0.0, totPeak=0.0, totCost=0.0;
	double averageBootingTime=0.0;
	double firstDeactivationTimeOut=0.0;
	
	public void main (Settings settings) throws IOException{
		
		//These are the parameters needed to do several simulations.
		double [] bootingTime={settings.getBootingTime()};
		double [] deactivationTimeout={settings.getDeactivationTimeout()};
		int [] isteresi={settings.getIsteresi()};
		
		int iterazioni=1;                //NUMERO ITERAZIONI PER OGNI SCENARIO
		int j=0;
		
		for(j=0; j<bootingTime.length; j++){
			
			i=0;
			
			maxReconf=0;
			minReconf=999999;
			totReconf=0;
			maxAwt=0.0;
			maxAvailability=0.0;
			maxPeak=0.0;
			maxCost=0.0;
			minAwt=999.0;
			minAvailability=9999.0;
			minPeak=9999.0;
			minCost=999999.0;
			totAwt=0.0;
			totAvailability=0.0;
			totPeak=0.0;
			totCost=0.0;
			
			averageBootingTime=bootingTime[j];
			firstDeactivationTimeOut=deactivationTimeout[j];
			
			int isteresiLength=isteresi[j];
			
				for(i=0;i<iterazioni;i++){
						ServersHandlerDeactivationThreshold mainHandlerHybridCopy = new ServersHandlerDeactivationThreshold ();
						mainHandlerHybridCopy.main(averageBootingTime,firstDeactivationTimeOut,isteresiLength,this,i);
					}
				
				// This for cycle is useless because iterazioni is fixed to 1
				
			PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter("risultato_simulaz_"+(int)averageBootingTime+"_"+isteresiLength+"_"+(int)firstDeactivationTimeOut+".txt", true)));
			out.println("-----fine simulazione con booting time:"+averageBootingTime+" isteresi: "+isteresiLength+" timeout:"+firstDeactivationTimeOut);
			out.println("awt media: "+String.format("%.4f",totAwt/iterazioni)+" min: "+String.format("%.4f",minAwt)+" Max: "+String.format("%.4f",maxAwt));
			out.println("peak medio: "+String.format("%.2f",totPeak/iterazioni)+" Min : "+String.format("%.2f",minPeak)+ " Max : "+String.format("%.2f",maxPeak));
			out.println("cost medio: "+String.format("%.2f",totCost/iterazioni)+" Min: "+String.format("%.2f",minCost)+" Max : "+String.format("%.2f",maxCost));
			out.println("reconfigurations medio: "+(totReconf/iterazioni)+" Min: "+minReconf+" Max reconfigurations: "+maxReconf);
			out.println("availab media: "+String.format("%.4f",totAvailability/iterazioni)+" Min: "+String.format("%.4f",minAvailability)+" Max: "+String.format("%.4f",maxAvailability));
			out.close();
		}
	}
}
