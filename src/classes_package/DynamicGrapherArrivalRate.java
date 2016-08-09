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
import org.jfree.chart.renderer.xy.StandardXYItemRenderer;
import org.jfree.data.xy.XYDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;
import org.jfree.ui.ApplicationFrame;
import org.jfree.ui.RefineryUtilities;

/**
 * An example to show how we can create a dynamic chart.
*/
public class DynamicGrapherArrivalRate extends ApplicationFrame {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	 

	/** The time series data. */
    private XYSeries series;
    private XYSeries series1;

    private int datasetIndex = 0;



    /** The most recent value added. */
   
    /** Timer to refresh graph after every 1/4th of a second */
   
    /**
     * Constructs a new dynamic chart application.
     *
     * @param title  the frame title.
     */
    public DynamicGrapherArrivalRate(final String title) {

        super(title);
        this.series = new XYSeries("Arrival Rate");
        this.series1= new XYSeries("Servers Capacity");
       
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
            "Arrival Rate",
            "Total Requests",
            "Requests in Interval",
            dataset,
            PlotOrientation.VERTICAL,
            true,
            true,
            false
        );
       
        XYPlot plot = result.getXYPlot();
       
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
        xaxis.setRange(0.0, 140000000.0); //per grafici tesi 2500000 //deafult 140M

        ValueAxis yaxis = plot.getRangeAxis();
        yaxis.setRange(0.0, 550.0);
        
        final XYSeriesCollection dataset1 = new XYSeriesCollection(this.series1);

        this.datasetIndex++;
        plot.setDataset(this.datasetIndex, dataset1);
        plot.setRenderer(this.datasetIndex, new StandardXYItemRenderer());
        
        
        
       
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
    public void newValue(int numberOfPackets, double value, double value2) {
        
       
       
        this.series.add(numberOfPackets,value);
        this.series1.add(numberOfPackets,value2);
       
    }  
    

    /**
     * Starting point for the dynamic graph application.
     *
     * @param args  ignored.
     */
    public void main(final String[] args) {

        final DynamicGrapherArrivalRate demo = new DynamicGrapherArrivalRate("Dynamic Line And TimeSeries Chart");
        demo.pack();
        RefineryUtilities.centerFrameOnScreen(demo);
        demo.setVisible(true);

    }

}  