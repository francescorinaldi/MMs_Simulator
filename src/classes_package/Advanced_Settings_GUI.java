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
public class Advanced_Settings_GUI extends JPanel
                                    implements PropertyChangeListener {
    
	//Default values for the fields
    private int maxReconf = 0;
    private int minReconf = 999999;
    private int totReconf = 0;
    private double maxAwt = 0.0;
    private double maxAvailability = 0.0;
    private double maxPeak = 0.0;
    private double maxCost = 0.0;
    private double minAwt = 999.0;
    private double minAvailability = 9999.0;
    private double minPeak = 9999.0;
    private double minCost = 999999.0;
    private double totAwt = 0.0;
    private double totAvailability = 0.0;
    private double totPeak = 0.0;
    private double totCost = 0.0;

    //Labels to identify the fields
    private JLabel maxReconfLabel;
    private JLabel minReconfLabel;
    private JLabel totReconfLabel;
    private JLabel maxAwtLabel;
    private JLabel maxAvailabilityLabel;
    private JLabel maxPeakLabel;
    private JLabel maxCostLabel;
    private JLabel minAwtLabel;
    private JLabel minAvailabilityLabel;
    private JLabel minPeakLabel;
    private JLabel minCostLabel;
    private JLabel totAwtLabel;
    private JLabel totAvailabilityLabel;
    private JLabel totPeakLabel;
    private JLabel totCostLabel;
    
    //Strings for the labels
    private static String maxReconfString = "Max Reconfiguration: ";
    private static String minReconfString = "Min Reconfigurations: ";
    private static String totReconfString = "Tot Reconfigurations: ";
    private static String maxAwtString = "Max AWT: ";
    private static String maxAvailabilityString = "Max Availability: ";
    private static String maxPeakString = "Max Peak: ";
    private static String maxCostString = "Max Cost ";
    private static String minAwtString = "Min AWT: ";
    private static String minAvailabilityString = "Min Availability: ";
    private static String minPeakString = "Min Peak: ";
    private static String minCostString = "Min Cost ";
    private static String totAwtString = "Tot AWT: ";
    private static String totAvailabilityString = "Tot Availability: ";
    private static String totPeakString = "Tot Peak: ";
    private static String totCostString = "Tot Cost: ";
    

    //Fields for data entry
    private static JFormattedTextField maxReconfField;
    private static JFormattedTextField minReconfField;
    private static JFormattedTextField totReconfField;
    private static JFormattedTextField maxAwtField;
    private static JFormattedTextField maxAvailabilityField;
    private static JFormattedTextField maxPeakField;
    private static JFormattedTextField maxCostField;
    private static JFormattedTextField minAwtField;
    private static JFormattedTextField minAvailabilityField;
    private static JFormattedTextField minPeakField;
    private static JFormattedTextField minCostField;
    private static JFormattedTextField totAwtField;
    private static JFormattedTextField totAvailabilityField;
    private static JFormattedTextField totPeakField;
    private static JFormattedTextField totCostField;

    //Formats to format and parse numbers
    private NumberFormat decimalFormat;
    private NumberFormat integerFormat;
    
    // Create OK button
    private static JButton btnOK = new JButton("OK");

    // Create Cancel button
    private static JButton btnCancel = new JButton("Cancel");
    
    // Create Settings [Default Value]
    private static AdvancedSettings advancedSettings = new AdvancedSettings();

	public Advanced_Settings_GUI() {
        super(new BorderLayout());
        setUpFormats();
        
        //Creation of settings variable [default value]
        //Settings settings = new Settings();

        //Create the labels.
        maxReconfLabel = new JLabel(maxReconfString);
        minReconfLabel = new JLabel(minReconfString);
        totReconfLabel = new JLabel(totReconfString);
        maxAwtLabel = new JLabel(maxAwtString);
        maxAvailabilityLabel = new JLabel(maxAvailabilityString);
        maxPeakLabel = new JLabel(maxPeakString);
        maxCostLabel = new JLabel(maxCostString);
        minAwtLabel = new JLabel(minAwtString);
        minAvailabilityLabel = new JLabel(minAvailabilityString);
        minPeakLabel = new JLabel(minPeakString);
        minCostLabel = new JLabel(minCostString);
        totAwtLabel = new JLabel(totAwtString);
        totAvailabilityLabel = new JLabel(totAvailabilityString);
        totPeakLabel = new JLabel(totPeakString);
        totCostLabel = new JLabel(totCostString);

        //Create the text fields and set them up.
        maxReconfField = new JFormattedTextField(integerFormat);
        maxReconfField.setValue(new Integer(maxReconf));
        maxReconfField.setColumns(10);
        maxReconfField.addPropertyChangeListener("value", this);
        
        minReconfField = new JFormattedTextField(integerFormat);
        minReconfField.setValue(new Integer(minReconf));
        minReconfField.setColumns(10);
        minReconfField.addPropertyChangeListener("value", this);
        
        totReconfField = new JFormattedTextField(integerFormat);
        totReconfField.setValue(new Integer(totReconf));
        totReconfField.setColumns(10);
        totReconfField.addPropertyChangeListener("value", this);

        maxAwtField = new JFormattedTextField(decimalFormat);
        maxAwtField.setValue(new Double(maxAwt));
        maxAwtField.setColumns(10);
        maxAwtField.addPropertyChangeListener("value", this);

        maxAvailabilityField = new JFormattedTextField(decimalFormat);
        maxAvailabilityField.setValue(new Double(maxAvailability));
        maxAvailabilityField.setColumns(10);
        maxAvailabilityField.addPropertyChangeListener("value", this);

        maxPeakField = new JFormattedTextField(decimalFormat);
        maxPeakField.setValue(new Double(maxPeak));
        maxPeakField.setColumns(10);
        maxPeakField.addPropertyChangeListener("value", this);

        maxCostField = new JFormattedTextField(decimalFormat);
        maxCostField.setValue(new Double(maxCost));
        maxCostField.setColumns(10);
        maxCostField.addPropertyChangeListener("value", this);

        minAwtField = new JFormattedTextField(decimalFormat);
        minAwtField.setValue(new Double(minAwt));
        minAwtField.setColumns(10);
        minAwtField.addPropertyChangeListener("value", this);

        minAvailabilityField = new JFormattedTextField(decimalFormat);
        minAvailabilityField.setValue(new Double(minAvailability));
        minAvailabilityField.setColumns(10);
        minAvailabilityField.addPropertyChangeListener("value", this);

        minPeakField = new JFormattedTextField(decimalFormat);
        minPeakField.setValue(new Double(minPeak));
        minPeakField.setColumns(10);
        minPeakField.addPropertyChangeListener("value", this);

        minCostField = new JFormattedTextField(decimalFormat);
        minCostField.setValue(new Double(minCost));
        minCostField.setColumns(10);
        minCostField.addPropertyChangeListener("value", this);
        
        totAwtField = new JFormattedTextField(decimalFormat);
        totAwtField.setValue(new Double(totAwt));
        totAwtField.setColumns(10);
        totAwtField.addPropertyChangeListener("value", this);

        totAvailabilityField = new JFormattedTextField(decimalFormat);
        totAvailabilityField.setValue(new Double(totAvailability));
        totAvailabilityField.setColumns(10);
        totAvailabilityField.addPropertyChangeListener("value", this);

        totPeakField = new JFormattedTextField(decimalFormat);
        totPeakField.setValue(new Double(totPeak));
        totPeakField.setColumns(10);
        totPeakField.addPropertyChangeListener("value", this);

        totCostField = new JFormattedTextField(decimalFormat);
        totCostField.setValue(new Double(totCost));
        totCostField.setColumns(10);
        totCostField.addPropertyChangeListener("value", this);


        //Tell accessibility tools about label / textfield pairs.
        maxReconfLabel.setLabelFor(maxReconfField);
        minReconfLabel.setLabelFor(minReconfField);
        totReconfLabel.setLabelFor(totReconfField);
        maxAwtLabel.setLabelFor(maxAwtField);
        maxAvailabilityLabel.setLabelFor(maxAvailabilityField);
        maxPeakLabel.setLabelFor(maxPeakField);
        maxCostLabel.setLabelFor(maxCostField);
        minAwtLabel.setLabelFor(minAwtField);
        minAvailabilityLabel.setLabelFor(minAvailabilityField);
        minPeakLabel.setLabelFor(minPeakField);
        minCostLabel.setLabelFor(minCostField);
        totAwtLabel.setLabelFor(totAwtField);
        totAvailabilityLabel.setLabelFor(totAvailabilityField);
        totPeakLabel.setLabelFor(totPeakField);
        totCostLabel.setLabelFor(totCostField);
        

        //Lay out the labels in a panel.
        JPanel labelPane = new JPanel(new GridLayout(0,1));
        labelPane.add(maxReconfLabel);
        labelPane.add(minReconfLabel);
        labelPane.add(totReconfLabel);
        labelPane.add(maxAwtLabel);
        labelPane.add(maxAvailabilityLabel);
        labelPane.add(maxPeakLabel);
        labelPane.add(maxCostLabel);
        labelPane.add(minAwtLabel);
        labelPane.add(minAvailabilityLabel);
        labelPane.add(minPeakLabel);
        labelPane.add(minCostLabel);
        labelPane.add(totAwtLabel);
        labelPane.add(totAvailabilityLabel);
        labelPane.add(totPeakLabel);
        labelPane.add(totCostLabel);
        labelPane.add(btnOK);
        
        //Layout the text fields in a panel.
        JPanel fieldPane = new JPanel(new GridLayout(0,1));
        fieldPane.add(maxReconfField);
        fieldPane.add(minReconfField);
        fieldPane.add(totReconfField);
        fieldPane.add(maxAwtField);
        fieldPane.add(maxAvailabilityField);
        fieldPane.add(maxPeakField);
        fieldPane.add(maxCostField);
        fieldPane.add(minAwtField);
        fieldPane.add(minAvailabilityField);
        fieldPane.add(minPeakField);
        fieldPane.add(minCostField);
        fieldPane.add(totAwtField);
        fieldPane.add(totAvailabilityField);
        fieldPane.add(totPeakField);
        fieldPane.add(totCostField);
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
        
        if (source == maxReconfField) {
        	maxReconf = ((Number)maxReconfField.getValue()).intValue();
        } else if (source == minReconfField){
        	minReconf = ((Number)minReconfField.getValue()).intValue();
        } else if (source == totReconfField){
        	totReconf = ((Number)totReconfField.getValue()).intValue();
        } else if (source == maxAwtField) {
            maxAwt = ((Number)maxAwtField.getValue()).doubleValue();
        } else if (source == maxAvailabilityField) {
            maxAvailability = ((Number)maxAvailabilityField.getValue()).doubleValue();
        } else if (source == maxPeakField) {
            maxPeak = ((Number)maxPeakField.getValue()).doubleValue();
        } else if (source == maxCostField) {
            maxCost = ((Number)maxCostField.getValue()).doubleValue();
        } else if (source == minAwtField) {
            minAwt = ((Number)minAwtField.getValue()).doubleValue();
        } else if (source == minAvailabilityField) {
            minAvailability = ((Number)minAvailabilityField.getValue()).doubleValue();
        } else if (source == minPeakField) {
            minPeak = ((Number)minPeakField.getValue()).doubleValue();
        } else if (source == minCostField) {
            minCost = ((Number)minCostField.getValue()).doubleValue();
        } else if (source == totAwtField) {
            totAwt = ((Number)totAwtField.getValue()).doubleValue();
        } else if (source == totAvailabilityField) {
            totAvailability = ((Number)totAvailabilityField.getValue()).doubleValue();
        } else if (source == totPeakField) {
            totPeak = ((Number)totPeakField.getValue()).doubleValue();
        } else if (source == totCostField) {
            totCost = ((Number)totCostField.getValue()).doubleValue();
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
        JComponent newContentPane = new Advanced_Settings_GUI();
        newContentPane.setOpaque(true); //content panes must be opaque
        frame.setContentPane(newContentPane);
        
        btnOK.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent event) {
            	
                int maxReconf = Integer.parseInt(maxReconfField.getText());
                int minReconf = Integer.parseInt(minReconfField.getText());
                int totReconf = Integer.parseInt(totReconfField.getText());
                double maxAwt = Double.valueOf(maxAwtField.getText());
                double maxAvailability = Double.valueOf(maxAvailabilityField.getText());
                double maxPeak = Double.valueOf(maxPeakField.getText());
                double maxCost = Double.valueOf(maxCostField.getText());
                double minAwt = Double.valueOf(minAwtField.getText());
                double minAvailability = Double.valueOf(minAvailabilityField.getText());
                double minPeak = Double.valueOf(minPeakField.getText());
                double minCost = Double.valueOf(minCostField.getText());
                double totAwt = Double.valueOf(totAwtField.getText());
                double totAvailability = Double.valueOf(totAvailabilityField.getText());
                double totPeak = Double.valueOf(totPeakField.getText());
                double totCost = Double.valueOf(totCostField.getText());
            	
            	advancedSettings.setMaxReconf(maxReconf);
            	advancedSettings.setMinReconf(minReconf);
            	advancedSettings.setTotReconf(totReconf);
            	advancedSettings.setMaxAwt(maxAwt);
            	advancedSettings.setMaxAvailability(maxAvailability);
            	advancedSettings.setMaxPeak(maxPeak);
            	advancedSettings.setMaxCost(maxCost);
            	advancedSettings.setMinAwt(minAwt);
            	advancedSettings.setMinAvailability(minAvailability);
            	advancedSettings.setMinPeak(minPeak);
            	advancedSettings.setMinCost(minCost);
            	advancedSettings.setTotAwt(totAwt);
            	advancedSettings.setTotAvailability(totAvailability);
            	advancedSettings.setTotPeak(totPeak);
            	advancedSettings.setTotCost(totCost);
                
                //Start.setSettings(settings);
            	Start.setAdvancedSettings(advancedSettings);
                //Start.setInitialization(true);
                Start.setOtherSettings(true);
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
    	integerFormat = NumberFormat.getNumberInstance(Locale.UK);
    	integerFormat.setGroupingUsed(false);
    }
}
