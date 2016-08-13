package classes_package;

import java.io.IOException;

public class Start {
	
	// This boolean value will become true when the user press "OK"
	private static boolean init = false;
	
	// New settings [Default value]
	public static Settings settings = new Settings();

	// The two different threads, that will be executed in sequence
	public static Thread startSimulation;
	public static Thread askSettings;
	
	public Start(){
	}
	
	public static void main(String args[]) {
		
		// This lock object is used to guarantee threads "sequentiality".
		Object lock = new Object();
		System.out.println("START [1]: Sto per creare entrambi i thread.");
		
		startSimulation = new Thread(() -> {
            synchronized (lock) {
            	System.out.println("START [4]: Ho creato lo StartSimulation, che partirà quanto la variabile boolean init sarà true.");
                while (init == false) {
                    try {
                        lock.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }

            System.out.println("START [5]: La variabile init è true: che i giochi abbiano inizio.");
            System.out.println("START [6]: I settings uploadati sono: ");
            settings.main();
            
            Simulation simulation = new Simulation() ;
    		try {
    			simulation.main(settings);
    		} catch (IOException e) {
    		// TODO Auto-generated catch block
    		e.printStackTrace();
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
	
	public static boolean isInit() {
		return init;
	}

	public static void setInit(boolean init) {
		Start.init = init;
	}

	public static Settings getSettings() {
		return settings;
	}

	public static void setSettings(Settings settings) {
		Start.settings = settings;
	}
	
}
