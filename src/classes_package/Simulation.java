package classes_package;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class Simulation{
	int i, maxReconf, minReconf, totReconf;
	
	double maxAwt, maxAvailability, maxPeak, maxCost;
	double minAwt, minAvailability, minPeak, minCost;
	double totAwt, totAvailability, totPeak, totCost;
	
	double averageBootingTime = 0.0, firstDeactivationTimeOut = 0.0;
	
	public void main (Settings settings, AdvancedSettings advancedSettings) throws IOException{
		
		//These are the parameters needed to do several simulations.
		double [] bootingTime = {settings.getBootingTime()};
		double [] deactivationTimeout = {settings.getDeactivationTimeout()};
		int [] hysteresis = {settings.getHysteresis()};
		
		int iterations = settings.getIterations();
		// Number of iterations for each scenario.
		
		int j = 0;
		
		settings.main();
		
		for(j = 0; j<bootingTime.length; j++){
			
			i = 0;
			
			maxReconf = advancedSettings.getMaxReconf();
			minReconf = advancedSettings.getMinReconf();
			totReconf = advancedSettings.getTotReconf();
			maxAwt = advancedSettings.getMaxAwt();
			maxAvailability = advancedSettings.getMaxAvailability();
			maxPeak = advancedSettings.getMaxPeak();
			maxCost = advancedSettings.getMaxCost();
			minAwt = advancedSettings.getMinAwt();
			minAvailability = advancedSettings.getMinAvailability();
			minPeak = advancedSettings.getMinPeak();
			minCost = advancedSettings.getMinCost();
			totAwt = advancedSettings.getTotAwt();
			totAvailability = advancedSettings.getTotAvailability();
			totPeak = advancedSettings.getTotPeak();
			totCost = advancedSettings.getTotCost();
			
			averageBootingTime = bootingTime[j];
			firstDeactivationTimeOut = deactivationTimeout[j];
			
			int hysteresisLength = hysteresis[j];
			
				for(i = 0; i<iterations; i++){
                        ServersHandlerDeactivationThreshold mainHandlerHybridCopy = new ServersHandlerDeactivationThreshold ();
                        mainHandlerHybridCopy.main(averageBootingTime,firstDeactivationTimeOut,hysteresisLength,this,i);
					}

                    PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter("simulation_result_" + (int) averageBootingTime + "_" + hysteresisLength + "_" + (int) firstDeactivationTimeOut + ".txt", true)));
                    out.println("\n-----End of the Simulation with Booting Time: " + averageBootingTime + ", Hysteresis: " + hysteresisLength + ", Timeout: " + firstDeactivationTimeOut);
                    out.println("Average Awt: " + String.format("%.4f", totAwt / iterations) + ", Min: " + String.format("%.4f", minAwt) + ", Max: " + String.format("%.4f", maxAwt));
                    out.println("Average Peak: " + String.format("%.2f", totPeak / iterations) + ", Min : " + String.format("%.2f", minPeak) + ", Max : " + String.format("%.2f", maxPeak));
                    out.println("Average Cost: " + String.format("%.2f", totCost / iterations) + ", Min: " + String.format("%.2f", minCost) + ", Max : " + String.format("%.2f", maxCost));
                    out.println("Average Reconfigurations: " + (totReconf / iterations) + ", Min: " + minReconf + ", Max reconfigurations: " + maxReconf);
                    out.println("Average Availability: " + String.format("%.4f", totAvailability / iterations) + ", Min: " + String.format("%.4f", minAvailability) + ", Max: " + String.format("%.4f", maxAvailability));
                    out.close();

		}
	}
}
