package com.company.view;

import com.company.model.Measurement;
import com.company.model.Patient;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.data.xy.XYDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

import javax.imageio.plugins.bmp.BMPImageWriteParam;
import javax.swing.*;
import javax.xml.crypto.dsig.spec.XSLTTransformParameterSpec;
import java.awt.*;
import java.util.ArrayList;

/**
 * ChartWindow Class
 */
public class ChartWindow extends JFrame {


    private JButton exitButton = new JButton("Exit");
    private ArrayList<Measurement> measurements = new ArrayList<>();
    private XYSeriesCollection database;

    /**
     * ChartWindow constructor
     * @param measurements
     */
    public ChartWindow(ArrayList<Measurement> measurements) {
        this.measurements = measurements;
        this.getContentPane().add(prepareMainPanel());
        setFrame();
    }

    /**
     * creating MainPanel function
     * @return MainPanel
     */
    private JPanel prepareMainPanel(){
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setPreferredSize(new Dimension(1200,500));

        mainPanel.add(BorderLayout.WEST, prepareChartPanel(measurements));
        mainPanel.add(BorderLayout.NORTH, prepareInfoPanel(measurements));
        mainPanel.add(BorderLayout.EAST, prepareDataPanel(measurements));

        mainPanel.setBorder((BorderFactory.createTitledBorder("Patient data")));


        return mainPanel;
    }

    /**
     * creating ChartPanel function
     * @param measurements
     * @return ChartPanel
     */
    private JPanel prepareChartPanel(ArrayList<Measurement> measurements){

        XYDataset databaseBMI = prepareDatabaseBMI(measurements);
        XYDataset databaseWeight = prepareDatabaseWeight(measurements);

        JFreeChart chartBMI = ChartFactory.createXYLineChart("BMI chart", "date", "BMI",
                databaseBMI, PlotOrientation.VERTICAL, false, false, false);
        ChartPanel chartPanelBMI = new ChartPanel(chartBMI);
        final XYPlot plot1 = chartBMI.getXYPlot();

        XYLineAndShapeRenderer renderer1 = new XYLineAndShapeRenderer();

        plot1.setRenderer( renderer1 );

        JFreeChart chartWeight = ChartFactory.createXYLineChart("Weight chart", "date", "weight",
                databaseWeight, PlotOrientation.VERTICAL, false, false, false);
        ChartPanel chartPanelWeight = new ChartPanel(chartWeight);
        final XYPlot plot2 = chartWeight.getXYPlot();

        XYLineAndShapeRenderer renderer2 = new XYLineAndShapeRenderer();

        plot2.setRenderer( renderer2 );

        JPanel chartPanel = new JPanel();

        chartPanel.setLayout(new GridLayout(2, 1));
        chartPanel.add(chartPanelBMI);
        chartPanel.add(chartPanelWeight);

        return chartPanel;
    }

    /**
     * creating InfoPanel function
     * @param measurements
     * @return InfoPanel
     */
    private JPanel prepareInfoPanel(ArrayList<Measurement> measurements){

        JPanel infoPanel = new JPanel();

        infoPanel.setLayout(new GridLayout(1,6));
        infoPanel.setBackground(Color.pink);

        float BMImax = getBMImax(measurements);
        float BMImin = getBMImin(measurements);
        float BMIaver = getBMIaver(measurements);
        float Wmax = getWeightmax(measurements);
        float Wmin = getWeightmin(measurements);
        float Waver = getWeightaver(measurements);

        infoPanel.add(new JLabel ("BMI max: " + BMImax));
        infoPanel.add(new JLabel ("BMI min: " + BMImin));
        infoPanel.add(new JLabel ("BMI aver: " + BMIaver));
        infoPanel.add(new JLabel ("Weight max: " + Wmax));
        infoPanel.add(new JLabel ("Weight min: " + Wmin));
        infoPanel.add(new JLabel ("Weight aver: " + Waver));


        return infoPanel;
    }

    /**
     * creating DataPanel function
     * @param measurements
     * @return DataPanel
     */
    private JPanel prepareDataPanel(ArrayList<Measurement> measurements){

        JPanel dataPanel = new JPanel();
        int size = measurements.size();
        String[][] data = new String [size][3];
        for(int i=0; i<measurements.size(); i++ ) {

            String weight = Float.toString(measurements.get(i).getWeight());
            String BMI = Float.toString(measurements.get(i).getBMI());
            data[i][0] = weight;
            data[i][1] = BMI;
            data[i][2] = measurements.get(i).getDate();

        }
        String column[]={"Weight","BMI","DATA"};
        final JTable jt=new JTable(data,column);

        JScrollPane scrollPane = new JScrollPane();
        scrollPane.setViewportView(jt);

        dataPanel.add(scrollPane);

        return dataPanel;
    }

    /**
     * creating data for BMI chart function
     * @param measurements
     * @return dataBaseBMI
     */
    private XYDataset prepareDatabaseBMI(ArrayList<Measurement> measurements){


        XYSeriesCollection dataBase = new XYSeriesCollection();
        XYSeries BMIchart = new XYSeries("BMI chart");

        int size = measurements.size();

        float [] XBMI = new float [size];
        int [] Y = new int[size];

        for(int i = 0; i<size; i++) {

            XBMI[i] = measurements.get(i).getBMI();
            Y[i] = i;

            BMIchart.add(Y[i], XBMI[i]);

        }

        dataBase.addSeries(BMIchart);

        return dataBase;
    }

    /**
     * creating data for weight chart function
     * @param measurements
     * @return dataBaseWeight
     */
    private XYDataset prepareDatabaseWeight(ArrayList<Measurement> measurements){


        XYSeriesCollection dataBase = new XYSeriesCollection();
        XYSeries Wchart = new XYSeries("Weight chart");

        int size = measurements.size();

        int [] Y = new int[size];
        float [] XWeight = new float [size];


        for(int i = 0; i<size; i++) {

            Y[i] = i;
            XWeight[i] = measurements.get(i).getWeight();

            Wchart.add(Y[i], XWeight[i]);
        }

        dataBase.addSeries(Wchart);

        return dataBase;
    }

    /**
     * calculating minimum BMI function
     * @param measurements
     * @return BMImin
     */
    private float getBMImin(ArrayList<Measurement> measurements){

        float BMImin = 0;
        int size = measurements.size();
        if(size>0) {
            BMImin = measurements.get(0).getBMI();

            for (int i = 1; i < size; i++) {

                float BMI = measurements.get(i).getBMI();
                if (BMI < BMImin){
                    BMImin = BMI;
                }
            }
        }
        return BMImin;
    }

    /**
     *calculating minimum Weight function
     * @param measurements
     * @return WeightMin
     */
    private float getWeightmin(ArrayList<Measurement> measurements){

        float Wmin = 0;
        int size = measurements.size();
        if(size>0) {
            Wmin = measurements.get(0).getWeight();

            for (int i = 1; i < size; i++) {

                float W = measurements.get(i).getWeight();
                if (W < Wmin){
                    Wmin = W;
                }
            }
        }
        return Wmin;
    }

    /**
     * calculating maximum BMI function
     * @param measurements
     * @return BMI max
     */
    private float getBMImax(ArrayList<Measurement> measurements){

        float BMImax = 0;
        int size = measurements.size();
        if(size>0) {
            BMImax = measurements.get(0).getBMI();

            for (int i = 1; i < size; i++) {

                float BMI = measurements.get(i).getBMI();
                if (BMI > BMImax){
                    BMImax = BMI;
                }
            }
        }
        return BMImax;
    }

    /**
     * calculating maximum weight  function
     * @param measurements
     * @return WeightMax
     */
    private float getWeightmax(ArrayList<Measurement> measurements){

        float Wmax = 0;
        int size = measurements.size();
        if(size>0) {
            Wmax = measurements.get(0).getWeight();

            for (int i = 1; i < size; i++) {

                float BMI = measurements.get(i).getWeight();
                if (BMI > Wmax){
                    Wmax = BMI;
                }
            }
        }
        return Wmax;
    }

    /**
     * calculating average BMI function
     * @param measurements
     * @return BMIaverage
     */
    private float getBMIaver(ArrayList<Measurement> measurements){

        float sum = 0;
        float BMIaver = 0;
        int size = measurements.size();
        if(size>0) {

            for (int i = 0; i < size; i++) {
                sum += measurements.get(i).getBMI();
            }
            BMIaver = sum/size;
        }

        return BMIaver;
    }

    /**
     * calculating average weight function
     * @param measurements
     * @return WeightAverage
     */
    private float getWeightaver(ArrayList<Measurement> measurements){

        float sum = 0;
        float Waver = 0;
        int size = measurements.size();
        if(size>0) {

            for (int i = 0; i < size; i++) {
                sum += measurements.get(i).getWeight();
            }
            Waver = sum/size;
        }

        return Waver;
    }

    /**
     * Setting frame
     */
    private void setFrame(){
        setTitle("Add data");
        pack();
        setResizable(false);
        setLocationRelativeTo(null);
        setVisible(true);
    }
}
