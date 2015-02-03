package vrijepinguins.view; /**
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

import java.awt.Color;
import java.util.ArrayList;
import java.util.HashMap;
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

    private List<Double> bData,cData, hData, dData, pData, vData;
    private List<Double> yData;
    //private double b = 0.00,v = 0.00,p = 0.00,c = 0.00;
    

    public static final String KONIJNEN = "Rabbit",VOSSEN = "Fox",PENGUIN = "Penguin";
    public static final String COCKROACH = "Cockroach";
    private XChartPanel chartPanel;

    public Lijndiagram() {
        //data = new ArrayList<List<Double>>();
        bData = new ArrayList<Double>();
        vData = new ArrayList<Double>();
        pData = new ArrayList<Double>();
        cData = new ArrayList<Double>();
        hData = new ArrayList<Double>();
        dData = new ArrayList<Double>();
        //yData = new ArrayList<Double>();
       // double b = 1.00,v = 0.00,p = 0.00,c = 0.00;
        for(int i = 0;i<6;i++){
        	putData(i,0.00);
        }
    }
    	
	public void setup(JFrame frame, JPanel panel,String populationDetails)//,List<Actor>actor)
	{
		//putData(actor);
		chartPanel= buildPanel();
		putData(populationDetails);
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
    	}else if(type==4){
    		hData.add(amount);
    	}else if(type==5){
    		dData.add(amount);
    	}
	}
	
	
	
	public void putData(String populationDetails){
		ArrayList<String> nameArray = new ArrayList<String>();
		ArrayList<Double> amountArray = new ArrayList<Double>();
		
		String pop2 = populationDetails.replace("vrijepinguins.model.","");
		pop2 = pop2.replace(":","");
		
		String[] stringArray = pop2.split(" ");
		for(int i =0;i<stringArray.length;i++)
		{
			if(i%2==0){
				nameArray.add(stringArray[i]);
			}else{
				amountArray.add(Double.parseDouble(stringArray[i]));
			}
		}
		
		
		
		sethData(amountArray.get(0));
		setvData(amountArray.get(1));
		setpData(amountArray.get(2));
		setcData(amountArray.get(3)/10);
		setbData(amountArray.get(4));
		setdData(amountArray.get(5));
		
		chartPanel.updateSeries(nameArray.get(0),gethData());
		chartPanel.updateSeries(nameArray.get(1),getvData());
		chartPanel.updateSeries(nameArray.get(2),getpData());
		chartPanel.updateSeries(nameArray.get(3),getcData());
		chartPanel.updateSeries(nameArray.get(4),getbData());
		chartPanel.updateSeries(nameArray.get(5),getdData());
		
		updateData();
	}
	
	//public void putData(HashMap )
    	
	
	
    public Chart getChart(){
    	
    	// Create Chart
    	Chart chart = new Chart(500,400);
    	chart.setChartTitle("vrijepinguins.view.Lijndiagram");
    	chart.setXAxisTitle("Stappen");
    	chart.setYAxisTitle("Animals");
    	//yData = getRandomData(5);
    	chart.addSeries("Rabbit", null, bData).setLineColor(Color.WHITE);
    	chart.addSeries("Fox", null, vData).setLineColor(Color.BLUE);
    	chart.addSeries("Penguin", null, pData).setLineColor(Color.CYAN);
    	chart.addSeries("Cockroach", null, cData).setLineColor(new Color(127,51,0));
    	chart.addSeries("Hunter",null, hData).setLineColor(Color.RED);
    	chart.addSeries("Druids",null,dData).setLineColor(Color.GRAY);
    	
    	
    	
    	return chart;
    	
    }


    public void updateData() {

    	// Get some new data
       /* List<Double> newData = getRandomData(1);
        List<Double> vosData = getRandomData(1);

        bData.addAll(newData);
        vData.addAll(vosData);*/

        // Limit the total number of points
        while (	bData.size() > 200 &&
        		vData.size() > 200 &&
        		pData.size() > 200 &&
        		cData.size() > 200 &&
        		hData.size() > 200 &&
        		dData.size() > 200) {
            bData.remove(0);
            vData.remove(0);
            pData.remove(0);
            cData.remove(0);
            hData.remove(0);
            dData.remove(0);
        }
        
       chartPanel.updateSeries(KONIJNEN,getcData());
       chartPanel.updateSeries(VOSSEN,getpData());
       chartPanel.updateSeries(PENGUIN,getvData());
       chartPanel.updateSeries(COCKROACH,getdData());
       chartPanel.updateSeries("Hunter",gethData());
       chartPanel.updateSeries("Druids",getbData());

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
    public List<Double> gethData() {
        return hData;
    }
    public List<Double> getdData() {
        return dData;
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
    public void sethData(double amount){
    	hData.add(amount);
    }
    public void setdData(double amount){
    	dData.add(amount);
    }
}
