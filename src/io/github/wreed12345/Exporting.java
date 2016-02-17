package io.github.wreed12345;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import javafx.embed.swing.SwingFXUtils;
import javafx.scene.SnapshotParameters;
import javafx.scene.chart.ScatterChart;
import javafx.scene.image.WritableImage;
import javafx.scene.input.Clipboard;
import javafx.scene.input.ClipboardContent;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

/**
 * Handles everything involved with saving the graph and exporting it
 * @author william
 */
public class Exporting {
	
	/**
	 * @param chart the chart you wish to save
	 * @param stage the stage you would like the file chooser to appear on
	 */
	public void saveChartAsPng(ScatterChart<Double, Double> chart, Stage stage) {
		WritableImage exportedImage = chart.snapshot(new SnapshotParameters(), null);
		
		FileChooser fileChooser = new FileChooser();
		fileChooser.setTitle("Save Graph Image");
		File file = fileChooser.showSaveDialog(stage);
		
		//ensure that png extension is present
		if(!(file.getName().endsWith(".png")))
			file = new File(file.toString() + ".png");
		
		//might want to put this somewhere else when implementing copy pasting
		try {
			ImageIO.write(SwingFXUtils.fromFXImage(exportedImage, null), "png", file);
		} catch (IOException e) {
			System.err.println("Error saving PNG file (picture) of chart."); 
			e.printStackTrace();
		}
	}
	
	/**
	 * Sets the image content of the clipboard to the chart supplied
	 * @param chart chart you wish to copy to the clipboard
	 */
	public void copyChartToClipboard(ScatterChart<Double, Double> chart) {
		WritableImage image = chart.snapshot(new SnapshotParameters(), null);
		ClipboardContent cc = new ClipboardContent();
		cc.putImage(image);
		Clipboard.getSystemClipboard().setContent(cc);
	}
}
