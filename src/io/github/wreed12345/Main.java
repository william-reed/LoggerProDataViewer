package io.github.wreed12345;

import java.util.ArrayList;
import java.util.List;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.chart.ScatterChart;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
 
 
public class Main extends Application {
 
    @Override public void start(Stage stage) {
    	//Possibly move this operation to another thread
    	
    	//TODO: change to use a file chooser
    	CMBLData c = new CMBLData("assets/acceleratingcar.cmbl");
        stage.setTitle("Scatter Chart");
        
        MotionChart displacementVTime = new MotionChart(c.getTimeVales(), c.getXPositionValues(), "Time", "Displacement", "Displacement v. Time");
        
        //setup tabs
        TabPane tabPane = new TabPane();
        Tab displacement, velocity, acceleration;
        displacement = new Tab("Displacement");
        velocity = new Tab("Velocity");
        acceleration = new Tab("Acceleration");
        
        //menu
        //TODO: probably move this somewhere else
        MenuBar menuBar = new MenuBar();
        
        Menu fileMenu = new Menu("File");
        MenuItem export = new MenuItem("Export");
        export.setOnAction(new EventHandler<ActionEvent>() {
        	public void handle (ActionEvent t) {
        		new Exporting().saveChartAsPng((ScatterChart)tabPane.getSelectionModel().getSelectedItem().getContent(), stage);
        	}
        });
        MenuItem exit = new MenuItem("Exit");
        exit.setOnAction(new EventHandler<ActionEvent>() {
        	public void handle (ActionEvent t) {
        		System.exit(0);
        	}
        });
        fileMenu.getItems().addAll(export, exit);
        
        Menu editMenu = new Menu("Edit");
        MenuItem copy = new MenuItem("Copy");
        copy.setOnAction(new EventHandler<ActionEvent>() {
        	public void handle (ActionEvent t) {
        		//TODO
        	}
        });
        
        menuBar.getMenus().addAll(fileMenu, editMenu);
        
        VBox vbox = new VBox(menuBar, tabPane);
        
        tabPane.getTabs().add(displacement);
        tabPane.getTabs().add(velocity);
        tabPane.getTabs().add(acceleration);
        
        List<Double> velocityValues = new ArrayList<Double>();
        for(int i = 0; i < (c.amountOfValues() - 1); i++){
        	double deltaDistance = (c.getXPositionValues().get(i + 1) - c.getXPositionValues().get(i));
        	double deltaTime = (c.getTimeVales().get(i + 1) - c.getTimeVales().get(i));
        	velocityValues.add(i, deltaDistance / deltaTime);
        }
        velocityValues.add(velocityValues.get(velocityValues.size() - 1));
        
        List<Double> accelerationValues = new ArrayList<Double>();
        for(int i = 0; i < (c.amountOfValues() - 1); i++){
        	double deltaVelocity = (velocityValues.get(i + 1) - velocityValues.get(i));
        	double deltaTime = (c.getTimeVales().get(i + 1) - c.getTimeVales().get(i));
        	accelerationValues.add(deltaVelocity / deltaTime);
        }
        accelerationValues.add(accelerationValues.get(accelerationValues.size() - 1));
        
        MotionChart velocityVTime = new MotionChart(c.getTimeVales(), velocityValues, "Time", "Velocity", "Velocity v. Time");
        MotionChart accelerationVTime = new MotionChart(c.getTimeVales(), accelerationValues, "Time", "Acceleration", "Acceleration v. Time");
        
        displacement.setContent(displacementVTime.getChart());
        velocity.setContent(velocityVTime.getChart());
        acceleration.setContent(accelerationVTime.getChart());
        Scene scene  = new Scene(vbox, 500, 400);
       
        
        
        stage.setScene(scene);
        stage.show();
    }
 
    public static void main(String[] args) {
        launch(args);
    }
}