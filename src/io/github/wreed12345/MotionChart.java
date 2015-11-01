package io.github.wreed12345;

import java.util.List;

import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.ScatterChart;
import javafx.scene.chart.XYChart.Data;
import javafx.scene.chart.XYChart.Series;

/**
 * @author william
 * Class that handles a lot of code that would be duplicated per each chart
 * for scatter plot creation. Labeled as motion chart since this program only
 * handles motion data, not temperature data or other values from CMBL files (yet)
 */
public class MotionChart {
	
	private ScatterChart<Number, Number> chart;
	//TOOD: need getters for the rest of these?
	private NumberAxis xAxis;
	private NumberAxis yAxis;
	private Series<Number, Number> xVersusy;
	
	/**
	 * Creates a new MotionChart
	 * @param x the values for the x axis 
	 * @param y the values for the y axis
	 * @param xLabel label of the x axis
	 * @param yLabel label of the y axis
	 * @param title chart title
	 */
	public MotionChart(List<Double> x, List<Double> y, String xLabel, String yLabel, String title) {
		//TODO: check to make sure size of x and y are the same
		
		double xMaxValue = getMaxValue(x);
		//increase the max value by 10 percent to add some padding on the chart
		xMaxValue = xMaxValue + (.1 * xMaxValue);
		//setup axis, min will always be 0, and scale is divided into 8 increments
		xAxis = new NumberAxis(xLabel, 0, xMaxValue, xMaxValue / 8);
		double yMaxValue = getMaxValue(y);
		yMaxValue = yMaxValue + (.1 * yMaxValue);
		yAxis = new NumberAxis(yLabel, 0, yMaxValue, yMaxValue / 8);
		
		chart = new ScatterChart<Number, Number>(xAxis, yAxis);
		chart.setTitle(title);
		
		xVersusy = new Series<Number, Number>();
		xVersusy.setName(yLabel + " v. " + xLabel);
		
		for(int i = 0; i < x.size(); i++){
			xVersusy.getData().add(new Data<Number, Number>(x.get(i), y.get(i)));
		}
		
		chart.getData().add(xVersusy);
	}
	
	
	/**
	 * @param values list of values
	 * @return the largest double in the list
	 */
	public double getMaxValue(List<Double> values) {
		double max = values.get(0);
		for(double d : values) 
			if(max < d)
				max = d;
		return max;
	}

	/**
	 * @return the scatter chart associated with this MotionChart
	 */
	public ScatterChart<Number, Number> getChart() {
		return chart;
	}
}
