package classes_package;

import java.awt.BorderLayout;
import java.awt.Color;

import javax.swing.JPanel;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.ValueAxis;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;

import org.jfree.data.xy.XYDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;
import org.jfree.ui.ApplicationFrame;
import org.jfree.ui.RefineryUtilities;

/**
 * An example to show how we can create a dynamic chart.
*/
public class DynamicGrapherReconfigurations extends ApplicationFrame {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/** The time series data. */
    private XYSeries series;
    

    /** The most recent value added. */
   
    /** Timer to refresh graph after every 1/4th of a second */
   
    /**
     * Constructs a new dynamic chart application.
     *
     * @param title  the frame title.
     */
    public DynamicGrapherReconfigurations(final String title) {

        super(title);
        this.series = new XYSeries("Active VMs");
       
        final XYSeriesCollection dataset = new XYSeriesCollection(this.series);
        final JFreeChart chart = createChart(dataset);
       
       
        //Sets background color of chart
        chart.setBackgroundPaint(Color.LIGHT_GRAY);
       
        //Created JPanel to show graph on screen
        final JPanel content = new JPanel(new BorderLayout());
       
        //Created Chartpanel for chart area
        final ChartPanel chartPanel = new ChartPanel(chart);
       
        //Added chartpanel to main panel
        content.add(chartPanel);
        
        //Sets the size of whole window (JPanel)
        chartPanel.setPreferredSize(new java.awt.Dimension(800, 500));
       
        //Puts the whole content on a Frame
        setContentPane(content);
       

    }

    /**
     * Creates a sample chart.
     *
     * @param dataset  the dataset.
     *
     * @return A sample chart.
     */
    private JFreeChart createChart(final XYDataset dataset) {
        final JFreeChart result = ChartFactory.createXYLineChart(
            "Reconfigurations",
            "Total Requests",
            "VMs activated",
            dataset,
            PlotOrientation.VERTICAL,
            true,
            true,
            false
        );
       
        final XYPlot plot = result.getXYPlot();
       
        plot.setBackgroundPaint(new Color(0xffffe0));
        plot.setDomainGridlinesVisible(true);
        plot.setDomainGridlinePaint(Color.lightGray);
        plot.setRangeGridlinesVisible(true);
        plot.setRangeGridlinePaint(Color.lightGray);
                
        ValueAxis xaxis = plot.getDomainAxis();
        //xaxis.setAutoRange(true);
       
        //Domain axis would show data of 60 seconds for a time
        //xaxis.setFixedAutoRange(7200000.0);  // 60 seconds
        //xaxis.setVerticalTickLabels(true);
        xaxis.setRange(0.0, 140000000.0);

        ValueAxis yaxis = plot.getRangeAxis();
        yaxis.setRange(0.0, 35.0);
       
        return result;
    }
    /**
     * Generates an random entry for a particular call made by time for every 1/4th of a second.
     *
     * @param e  the action event.
     */
//    public void actionPerformed(final ActionEvent e) {
//       
//        final double factor = 0.9 + 0.2*Math.random();
//        this.lastValue = this.lastValue * factor;
//       
//        final Millisecond now = new Millisecond();
//        this.series.add(new Millisecond(), 11);
//       
//        System.out.println("Current Time in Milliseconds = " + now.toString()+", Current Value : "+this.lastValue);
//    }
    public void newValue(int numberOfPackets, int value) {
        
       
       
        this.series.add(numberOfPackets, value);
       
    }  
    

    /**
     * Starting point for the dynamic graph application.
     *
     * @param args  ignored.
     */
    public void main(final String[] args) {

        final DynamicGrapherReconfigurations demo = new DynamicGrapherReconfigurations("Dynamic Line And TimeSeries Chart");
        demo.pack();
        RefineryUtilities.centerFrameOnScreen(demo);
        demo.setVisible(true);

    }

}  