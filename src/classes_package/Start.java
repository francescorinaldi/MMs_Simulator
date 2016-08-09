package classes_package;

import java.io.IOException;

import javax.swing.Box;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class Start {
	
	public Start(){
		
	}
	
	public static void main(String args[]){
		
		JTextField bootingTime = new JTextField(15);
	    JTextField deactivationTimeout = new JTextField(15);
	    JTextField isteresi = new JTextField(15);

	    JPanel myPanel = new JPanel();
	    myPanel.add(new JLabel("Booting Time:"));
	    myPanel.add(bootingTime);
	    myPanel.add(Box.createHorizontalStrut(15)); // a spacer
	    myPanel.add(new JLabel("Deactivation Timeout:"));
	    myPanel.add(deactivationTimeout);
	    myPanel.add(Box.createHorizontalStrut(15)); // another spacer
	    myPanel.add(new JLabel("Isteresi:"));
	    myPanel.add(isteresi);

	    int result = JOptionPane.showConfirmDialog(null, myPanel, 
	               "Please Enter X and Y Values", JOptionPane.OK_CANCEL_OPTION);
	    
	    if (result == JOptionPane.OK_OPTION) {
	         System.out.println("BootingTime: " + bootingTime.getText());
	         System.out.println("DeactivationTimeout: " + deactivationTimeout.getText());
	         System.out.println("Isteresi: " + isteresi.getText());
	      }
	      
		String[] options = new String[] {"Yes", "No", "Maybe", "Cancel"};
	    int response = JOptionPane.showOptionDialog(null, "Message", "Title",
	        JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE,
	        null, options, options[0]);
		
		//double bootingTime = Double.parseDouble(JOptionPane.showInputDialog("Enter the booting time."));
		//double deactivationTimeout = Double.parseDouble(JOptionPane.showInputDialog("Enter the deactivation timeout."));
		//int isteresi= Integer.parseInt(JOptionPane.showInputDialog("Enter the number of students."));
		
		
		//RepetitaIuvant repe = new RepetitaIuvant (double bootingTime, double deactivationTimeout, int isteresi);
		RepetitaIuvant repe = new RepetitaIuvant () ;
		try {
			repe.main();
		} catch (IOException e) {
				// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
}
