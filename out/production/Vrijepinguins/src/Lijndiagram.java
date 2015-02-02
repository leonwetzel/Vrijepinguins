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

    private List<Double> yData;
    private List<Double> vData;
    public static final String KONIJNEN = "Konijnen";
    public static final String VOSSEN = "Vossen";

    public static void main(String[] args) {

        // Setup the panel
        final Lijndiagram lijndiagram = new Lijndiagram();
        final XChartPanel chartPanel = lijndiagram.buildPanel();
        final XChartPanel vosPanel = lijndiagram.buildPanel();

        // Schedule a job for the event-dispatching thread:
        // creating and showing this application's GUI.
        javax.swing.SwingUtilities.invokeLater(new Runnable() {

            @Override
            public void run() {

                // Create and set up the window.
                JFrame frame = new JFrame("Staafdiagram (Vossen & Konijnen)");
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                frame.add(chartPanel);
                frame.add(vosPanel);

                // Display the window.
                frame.pack();
                frame.setVisible(true);
            }
        });

        // Simulate a data feed
        TimerTask chartUpdaterTask = new TimerTask() {

            @Override
            public void run() {

                lijndiagram.updateData();
                chartPanel.updateSeries(KONIJNEN, lijndiagram.getyData());
                vosPanel.updateSeries(VOSSEN, lijndiagram.getvData());
            }
        };

        Timer timer = new Timer();
        timer.scheduleAtFixedRate(chartUpdaterTask, 0, 500);

    }

    public XChartPanel buildPanel() {

        return new XChartPanel(getChart());
    }

    @Override
    public Chart getChart() {

        yData = getRandomData(5);
        vData = getRandomData(6);

        // Create Chart
        Chart chart = new Chart(500, 400);
        chart.setChartTitle("Lijndiagram");
        chart.setXAxisTitle("Aantal stappen");
        chart.setYAxisTitle("Hoeveelheid actors");
        chart.addSeries(KONIJNEN, null, yData);
        chart.addSeries(VOSSEN, null, vData);

        return chart;
    }

    private List<Double> getRandomData(int numPoints) {

        List<Double> data = new ArrayList<Double>();
        for (int i = 0; i < numPoints; i++) {
            data.add(Math.random() * 100);
        }
        return data;
    }

    public void updateData() {

        // Get some new data
        List<Double> newData = getRandomData(1);
        List<Double> vosData = getRandomData(1);

        yData.addAll(newData);
        vData.addAll(vosData);

        // Limit the total number of points
        while (yData.size() > 100000 && vData.size() > 100000) {
            yData.remove(0);
            vData.remove(0);
        }

    }

    public List<Double> getyData() {

        return yData;
    }

    public List<Double> getvData() {
        return vData;
    }
}

