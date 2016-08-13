package classes_package;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.text.NumberFormat;
import java.util.Locale;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

@SuppressWarnings("serial")
public class MMs_Simulator_GUI extends JPanel
                                    implements PropertyChangeListener {
    
	//Default values for the fields
    private double bootingTime = 900.00;
    private double deactivationTimeout = 3600.00;
    private int isteresi = 3;
    private int iterations = 1;

    //Labels to identify the fields
    private JLabel bootingTimeLabel;
    private JLabel deactivationTimeoutLabel;
    private JLabel isteresiLabel;
    private JLabel iterationsLabel;
    
    //Strings for the labels
    private static String bootingTimeString = "Booting Time: ";
    private static String deactivationTimeoutString = "DeactivationTimeout: ";
    private static String isteresiString = "Isteresi: ";
    private static String iterationsString = "Iterations: ";

    //Fields for data entry
    private static JFormattedTextField bootingTimeField;
    private static JFormattedTextField deactivationTimeoutField;
    private static JFormattedTextField isteresiField;
    private static JFormattedTextField iterationsField;

    //Formats to format and parse numbers
    private NumberFormat decimalFormat;
    
    // Create OK button
    private static JButton btnOK = new JButton("OK");

    // Create Cancel button
    private static JButton btnCancel = new JButton("Cancel");
    
    // Create Settings [Default Value]
    private static Settings settings = new Settings();
    

	public MMs_Simulator_GUI() {
        super(new BorderLayout());
        setUpFormats();
        
        //Creation of settings variable [default value]
        //Settings settings = new Settings();

        //Create the labels.
        bootingTimeLabel = new JLabel(bootingTimeString);
        deactivationTimeoutLabel = new JLabel(deactivationTimeoutString);
        isteresiLabel = new JLabel(isteresiString);
        iterationsLabel = new JLabel(iterationsString);

        //Create the text fields and set them up.
        bootingTimeField = new JFormattedTextField(decimalFormat);
        bootingTimeField.setValue(new Double(bootingTime));
        bootingTimeField.setColumns(10);
        bootingTimeField.addPropertyChangeListener("value", this);

        deactivationTimeoutField = new JFormattedTextField(decimalFormat);
        deactivationTimeoutField.setValue(new Double(deactivationTimeout));
        deactivationTimeoutField.setColumns(10);
        deactivationTimeoutField.addPropertyChangeListener("value", this);

        isteresiField = new JFormattedTextField();
        isteresiField.setValue(new Integer(isteresi));
        isteresiField.setColumns(10);
        isteresiField.addPropertyChangeListener("value", this);
        
        iterationsField = new JFormattedTextField();
        iterationsField.setValue(new Integer(iterations));
        iterationsField.setColumns(10);
        iterationsField.addPropertyChangeListener("value", this);
        
        //Tell accessibility tools about label / textfield pairs.
        bootingTimeLabel.setLabelFor(bootingTimeField);
        deactivationTimeoutLabel.setLabelFor(deactivationTimeoutField);
        isteresiLabel.setLabelFor(isteresiField);
        iterationsLabel.setLabelFor(iterationsField);

        //Lay out the labels in a panel.
        JPanel labelPane = new JPanel(new GridLayout(0,1));
        labelPane.add(bootingTimeLabel);
        labelPane.add(deactivationTimeoutLabel);
        labelPane.add(isteresiLabel);
        labelPane.add(iterationsLabel);
        labelPane.add(btnOK);
        
        //Layout the text fields in a panel.
        JPanel fieldPane = new JPanel(new GridLayout(0,1));
        fieldPane.add(bootingTimeField);
        fieldPane.add(deactivationTimeoutField);
        fieldPane.add(isteresiField);
        fieldPane.add(iterationsField);
        fieldPane.add(btnCancel);

        //Put the panels in this panel, labels on left,
        //text fields on right.
        setBorder(BorderFactory.createEmptyBorder(25, 90, 25, 90));
        add(labelPane, BorderLayout.CENTER);
        add(fieldPane, BorderLayout.LINE_END);
    }
    
    /** Called when a field's "value" property changes. */
    public void propertyChange(PropertyChangeEvent e) {
        Object source = e.getSource();
        if (source == bootingTimeField) {
            bootingTime = ((Number)bootingTimeField.getValue()).doubleValue();
        } else if (source == deactivationTimeoutField) {
            deactivationTimeout = ((Number)deactivationTimeoutField.getValue()).doubleValue();
        } else if (source == isteresiField) {
        	isteresi = ((Number)isteresiField.getValue()).intValue();
        } else if (source == iterationsField) {
        	iterations = ((Number)iterationsField.getValue()).intValue();
        }
    }
    
    /**
     * Create the GUI and show it.  For thread safety,
     * this method should be invoked from the
     * event-dispatching thread.
     */
    private static void createAndShowGUI() {
        //Make sure we have nice window decorations.
        JFrame.setDefaultLookAndFeelDecorated(true);

        //Create and set up the window.
        JFrame frame = new JFrame("Please select Booting Time, Deactivation Timeout and Isteresi: ");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        //Create and set up the content pane.
        JComponent newContentPane = new MMs_Simulator_GUI();
        newContentPane.setOpaque(true); //content panes must be opaque
        frame.setContentPane(newContentPane);
        
        btnOK.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent event) {
                double bootingTime = Double.valueOf(bootingTimeField.getText());
                double deactivationTimeout = Double.valueOf(deactivationTimeoutField.getText());
                int isteresi = Integer.parseInt(isteresiField.getText());
                int iterations = Integer.parseInt(iterationsField.getText());
                System.out.println("Booting Time: " + bootingTime + "\nDeactivation Timeout :" + deactivationTimeout + "\nIsteresi: " + isteresi + "\nIterations: " + iterations);
                
                settings.setBootingTime(bootingTime);
                settings.setDeactivationTimeout(deactivationTimeout);
                settings.setIsteresi(isteresi);
                settings.setIterations(iterations);
 
                settings.main();
                
                Start.setSettings(settings);
                Start.setInit(true);
                Start.startSimulation.start();
                
                frame.dispose();
            }
        });
        
        btnCancel.addActionListener(new ActionListener() {
        	
        	@Override
        	public void actionPerformed(ActionEvent e){
        		System.exit(0);
        	}
        	
        });

        //Display the window.
        frame.pack();
        frame.setVisible(true);
    }

    public void main() {
    	//Schedule a job for the event-dispatching thread:
        //creating and showing this application's GUI.
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                createAndShowGUI();
            }
        });
    }

    //Create and set up number formats. These objects also
    //parse numbers input by user.
    private void setUpFormats() {
    	decimalFormat = NumberFormat.getNumberInstance(Locale.UK);
    	decimalFormat.setGroupingUsed(false);
    	decimalFormat.setMaximumFractionDigits(100);
    	decimalFormat.setMinimumFractionDigits(2);
    }
}