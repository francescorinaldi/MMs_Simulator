package classes_package;

import java.io.IOException;

public class EntryPoint {
	
	public EntryPoint(){
		
	}
	
	public static void main(String args[]){
		RepetitaIuvant repe = new RepetitaIuvant();
		try {
			repe.main();
		} catch (IOException e) {
				// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
}
