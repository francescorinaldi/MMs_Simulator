package classes_package;

import java.io.IOException;

public class Start {
	
	public Start(){
		
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
