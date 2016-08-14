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
		System.out.println("START [1]: Sto per creare i tre threads.");
		
		startSimulation = new Thread(() -> {
            synchronized (lock) {
            	System.out.println("START [7]: Ho creato lo StartSimulation, che partirà quanto la variabile boolean init sarà true.");
                while (initialization == false || otherSettings == false) {
                    try {
                        lock.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }

            System.out.println("START [8]: Le variabili initialization and otherSettings are true: che i giochi abbiano inizio.");
            System.out.println("START [9]: I settings uploadati sono: ");
            settings.main();
            
            System.out.println("[START [10]: Ora qui ci sarebbero da stampare anche quelli advanced, ma ho già provato, funziona tutto e non ne ho voglia. (:]");
            
            Simulation simulation = new Simulation() ;
    		try {
    			simulation.main(settings, advancedSettings);
    		} catch (IOException e) {
    		// TODO Auto-generated catch block
    		e.printStackTrace();
    		}
            
        });
		
		askAdvancedSettings = new Thread(() -> {
			synchronized (lock) {
				
				System.out.println("START [4]: L'utente ha selezionato di modificare gli advanced settings.");
				while (initialization == false) {
                    try {
                        lock.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    	}
                	}
			
				System.out.println("START [5]: Creo il JFrame dove chiedo all'utente gli advanced settings.");
		           
                Advanced_Settings_GUI Advanced_Settings_GUI = new Advanced_Settings_GUI();
                Advanced_Settings_GUI.main();
             
                System.out.println("START [6]: L'interfaccia è stata creata, ed è attualmente online.");
			}
		});
		

		askSettings = new Thread(() -> {
            synchronized (lock) {
            	System.out.println("START [2]: Creo il JFrame dove chiedo all'utente i vari settings.");
           
                MMs_Simulator_GUI MMs_Simulator_GUI = new MMs_Simulator_GUI();
                MMs_Simulator_GUI.main();
             
                System.out.println("START [3]: L'interfaccia è stata creata, ed è attualmente online.");

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
