package classes_package;

import java.io.IOException;

public class Start {
	
	// This boolean value will become true when the user press "OK"
	private static boolean initialization = false;
	private static boolean otherSettings = false;
	
	// New settings [Default value]
	public static Settings settings = new Settings();
	public static AdvancedSettings advancedSettings = new AdvancedSettings();


	// The two different threads, that will be executed in sequence
	public static Thread startSimulation;
	public static Thread askSettings;
	public static Thread askAdvancedSettings;
	
	public Start(){
	}
	
	public static void main(String args[]) {
		
		// This lock object is used to guarantee threads "sequentiality".
		Object lock = new Object();
		
		startSimulation = new Thread(() -> {
            synchronized (lock) {
                while (initialization == false || otherSettings == false) {
                    try {
                        lock.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }

            settings.main();
            
            Simulation simulation = new Simulation() ;
    		try {
    			simulation.main(settings, advancedSettings);
    		} catch (IOException e) {
    		e.printStackTrace();
    		}
            
        });
		
		askAdvancedSettings = new Thread(() -> {
			synchronized (lock) {

				while (initialization == false) {
                    try {
                        lock.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    	}
                	}
		           
                Advanced_Settings_GUI Advanced_Settings_GUI = new Advanced_Settings_GUI();
                Advanced_Settings_GUI.main();

			}
		});
		

		askSettings = new Thread(() -> {
            synchronized (lock) {
           
                MMs_Simulator_GUI MMs_Simulator_GUI = new MMs_Simulator_GUI();
                MMs_Simulator_GUI.main();

                lock.notify();
            }
        });

        askSettings.start();
		
	}
	
	public static boolean isInitialized() {
		return initialization;
	}

	public static void setInitialization(boolean init) {
		Start.initialization = init;
	}

	public static boolean OtherSettings() {
		return otherSettings;
	}

	public static void setOtherSettings(boolean otherSettings) {
		Start.otherSettings = otherSettings;
	}

	public static AdvancedSettings getAdvancedSettings() {
		return advancedSettings;
	}

	public static void setAdvancedSettings(AdvancedSettings advancedSettings) {
		Start.advancedSettings = advancedSettings;
	}

	public static Settings getSettings() {
		return settings;
	}

	public static void setSettings(Settings settings) {
		Start.settings = settings;
	}
	
}
