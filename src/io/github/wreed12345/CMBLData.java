package io.github.wreed12345;

import java.util.List;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Contains all usable data from a CMBL file
 * 
 * @author william
 */
public class CMBLData {

	private List<Double> time;
	private List<Double> xPosition;
	private List<Double> yPosition;
	private final int amountOfValues;
	
	/**
	 * Creates a new data object for a CMBL File
	 * 
	 * @param filePath
	 *            the path to the CMBL File
	 */
	public CMBLData(String filePath) {
		time = new ArrayList<Double>();
		xPosition = new ArrayList<Double>();
		yPosition = new ArrayList<Double>();
		parseData(filePath);
		if((time.size() != xPosition.size()) || (xPosition.size() != yPosition.size())) {
			//throw new RuntimeException("Values for Time, X/Y Position are not of the same size!");
		}
		amountOfValues = time.size();

	}

	/**
	 * ENUM to keep track of what the parser is looking for
	 */
	private enum CurrentIteration {
		TIME, XPOSITION, YPOSITION;
	}

	/*
	 * Order of numbers: 1. Time values 2. X values in meters 3. Y values in
	 * meters (not sure if units are always meters, sometimes may be mm for some
	 * reason)
	 */

	/**
	 * Parses all usable data from the file
	 * 
	 * @param filePath
	 *            path to the file
	 */
	private void parseData(String filePath) {
		try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
			String line;
			CurrentIteration curit = CurrentIteration.TIME;
			PRELIM: while ((line = br.readLine()) != null) {
				if (line.contains("<ColumnCells>")) {
					boolean getNumbers = true;
					while (getNumbers) {
						line = br.readLine();
						if (line.contains("<")) {
							getNumbers = false;
							switch (curit) {
							case TIME:
								curit = CurrentIteration.XPOSITION;
								break;
							case XPOSITION:
								curit = CurrentIteration.YPOSITION;
								break;
							case YPOSITION:
								break PRELIM;
							default:
								throw new RuntimeException("More than three sets of numbers were found");
							}
							break;
						}
						switch (curit) {
						case TIME:
							time.add(Double.valueOf(line));
							break;
						case XPOSITION:
							xPosition.add(Double.valueOf(line) / 1000);
							// TODO: check if this is always in mm. In the above
							// case it is so it is being converted to m
							break;
						case YPOSITION:
							// TODO: same as above
							yPosition.add(Double.valueOf(line) / 1000);
							break;
						default:
							throw new RuntimeException("More than three sets of numbers were found");
						}
					}
				}
			}
			br.close();
		} catch (FileNotFoundException e) {
			System.err.println("CMBL file was not found");
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	/**
	 * @return all time values extracted from the CMBL file
	 */
	public List<Double> getTimeVales() {
		return time;
	}

	/**
	 * @return all X Position values extracted from the CMBL file
	 */
	public List<Double> getXPositionValues() {
		return xPosition;
	}

	/**
	 * @return all Y Position values extracted from the CMBL file
	 */
	public List<Double> getYPositionValues() {
		return yPosition;
	}
	
	/**
	 * @return the amount of values contained in this CMBL file
	 * (EX: 20 values meaning 20 values for time, 20 for x pos, 20 for y pos... etc
	 */
	public int amountOfValues() {
		return amountOfValues;
	}
}
