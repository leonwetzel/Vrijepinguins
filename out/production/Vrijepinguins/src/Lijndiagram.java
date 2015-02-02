/**
 * Copyright 2011 - 2014 Xeiam LLC.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextArea;

import com.xeiam.xchart.Chart;
import com.xeiam.xchart.XChartPanel;
import com.xeiam.xchart.demo.charts.ExampleChart;

/**
 * Realtime
 * <p>
 * Demonstrates the following:
 * <ul>
 * <li>real-time chart updates
 * <li>fixed window
 */
public class Lijndiagram implements ExampleChart {

    private List<Double> bData;
    private List<Double> vData;
    private List<Double> pData;
    private List<Double> cData;
    // private ArrayList<List<Double>> data;
    
    public static final String KONIJNEN 	= "Konijnen";
    public static final String VOSSEN 		= "Vossen";
    public static final String PENGUIN 		= "github.model.Penguin";
    public static final String COCKROACH 	= "github.model.Cockroach";
    // private JPanel panel;

    public Lijndiagram() {
        //data = new ArrayList<List<Double>>();
        bData = new ArrayList<Double>();
        vData = new ArrayList<Double>();
        pData = new ArrayList<Double>();
        cData = new ArrayList<Double>();
        for(int i = 0;i<4;i++){
        	putData(i,0.00);
        }
    }
    	
	public void setup(JFrame frame, JPanel panel)
	{
		XChartPanel chartPanel= buildPanel();
		panel.add(chartPanel);
	}
    
	public XChartPanel buildPanel() {

        return new XChartPanel(getChart());
	}	
	
	public void putData(int type, double amount){
    	if(type==0){
    		bData.add(amount);
    	}else if(type==1){
    		vData.add(amount);
    	}else if(type==2){
    		pData.add(amount);
    	}else if(type==3){
    		cData.add(amount);
    	}
	}
    	
    public Chart getChart(){
    	/*bData = getRandomData(6);
    	vData = getRandomData(6);
    	pData = getRandomData(7);
    	cData = getRandomData(8);*/
    	
    	// Create Chart
    	Chart chart = new Chart(250,200);
    	chart.setChartTitle("Lijndiagram");
    	chart.setXAxisTitle("Stappen");
    	chart.setYAxisTitle("Animals");
    	chart.addSeries(KONIJNEN, null, bData);
    	chart.addSeries(VOSSEN, null, vData);
    	chart.addSeries(PENGUIN, null, pData);
    	chart.addSeries(COCKROACH, null, cData);
    	
    	
    	return chart;
    	
    }

    /*
    private List<Double> getRandomData(int numPoints) {

        List<Double> data = new ArrayList<Double>();
        for (int i = 0; i < numPoints; i++) {
            data.add(Math.random() * 100);
        }
        return data;
    }*/

    public void updateData() {

        // Get some new data
       /* List<Double> newData = getRandomData(1);
        List<Double> vosData = getRandomData(1);

        bData.addAll(newData);
        vData.addAll(vosData);*/

        // Limit the total number of points
        while (	bData.size() > 100000 &&
        		vData.size() > 100000 &&
        		pData.size() > 100000 && 
        		cData.size() > 100000) {
            bData.remove(0);
            vData.remove(0);
            pData.remove(0);
            cData.remove(0);
        }

    }

    public List<Double> getbData() {
        return bData;
    }
    public List<Double> getvData() {
        return vData;
    }
    public List<Double> getpData() {
        return pData;
    }
    public List<Double> getcData() {
        return cData;
    }
    
    public void setbData(double amount){
    	bData.add(amount);
    }
    public void setvData(double amount){
    	vData.add(amount);
    }
    public void setpData(double amount){
    	pData.add(amount);
    }
    public void setcData(double amount){
    	cData.add(amount);
    }
}

//Setup the panel
// final Lijndiagram lijndiagram = new Lijndiagram();
//  final XChartPanel chartPanel = lijndiagram.buildPanel();
//  final XChartPanel vosPanel = lijndiagram.buildPanel();

 // Schedule a job for the event-dispatching thread:
 // creating and showing this application's GUI.
 // javax.swing.SwingUtilities.invokeLater(new Runnable() {
//  	JPanel panel = new JPanel();
 	
 //@Override
	 // public void run() {

	// Create and set up the window.
 //JFrame frame = new JFrame("Staafdiagram (Vossen & Konijnen)");
 ///frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//  panel.add(chartPanel);
//  panel.add(vosPanel);

 // Display the window.
 //frame.pack();
 //frame.setVisible(true);
 //    }
 // });

 // Simulate a data feed
// TimerTask chartUpdaterTask = new TimerTask() {
	//
//     @Override
//     public void run() {

 //        lijndiagram.updateData();
//         chartPanel.updateSeries(KONIJNEN, lijndiagram.getyData());
//         vosPanel.updateSeries(VOSSEN, lijndiagram.getvData());
//      }
//  };

	//  Timer timer = new Timer();
// timer.scheduleAtFixedRate(chartUpdaterTask, 0, 500);


