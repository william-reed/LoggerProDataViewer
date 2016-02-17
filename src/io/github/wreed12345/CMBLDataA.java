package io.github.wreed12345;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

/**
 * Contains all usable data from a CMBL file
 * 
 * @author william
 */
public class CMBLDataA {

	private List<Double> time;
	private List<Double> xPosition;
	private List<Double> yPosition;
	private final int amountOfValues;
	
	public static void main(String args[]) {
		new CMBLDataA("assets/acceleratingcar.cmbl");
	}
	
	/**
	 * Creates a new data object for a CMBL File
	 * 
	 * @param filePath
	 *            the path to the CMBL File
	 */
	public CMBLDataA(String filePath) {
		time = new ArrayList<Double>();
		xPosition = new ArrayList<Double>();
		yPosition = new ArrayList<Double>();
		try {
			parseData(filePath);
		} catch(Exception e ){
			e.printStackTrace();
		}
		if((time.size() != xPosition.size()) || (xPosition.size() != yPosition.size())) {
			throw new RuntimeException("Values for Time, X/Y Position are not of the same size!");
		}
		amountOfValues = time.size();

	}

	/**
	 * Parses all usable data from the file
	 * 
	 * @param filePath
	 *            path to the file
	 * @throws ParserConfigurationException 
	 * @throws IOException 
	 * @throws SAXException 
	 * @throws FileNotFoundException 
	 * @throws XPathExpressionException 
	 */
	private void parseData(String filePath) {
		
		try {
			DocumentBuilderFactory builderFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder builder = null;
		    builder = builderFactory.newDocumentBuilder();
		    
		    Document document = builder.parse(new FileInputStream(filePath));
		    
			XPath xPath =  XPathFactory.newInstance().newXPath();
			
			NodeList nodeList = (NodeList) xPath.compile(getExpressionForDataType("Time")).evaluate(document, XPathConstants.NODESET);
			for (int i = 0; i < nodeList.getLength(); i++) {
				String numbers[] = nodeList.item(i).getFirstChild().getNodeValue().toString().split("\n");
//				System.out.println("start");
//				for(String s : numbers) 
//					System.out.println(s);
//				System.out.println("done");
				//convert to doubles
				//time.add(numbers);
			}
			
			nodeList = (NodeList) xPath.compile(getExpressionForDataType("X")).evaluate(document, XPathConstants.NODESET);
			for (int i = 0; i < nodeList.getLength(); i++) {
			}
			
			nodeList = (NodeList) xPath.compile(getExpressionForDataType("Y")).evaluate(document, XPathConstants.NODESET);
			for (int i = 0; i < nodeList.getLength(); i++) {
			}
			
		} catch (ParserConfigurationException e) {
		    e.printStackTrace();  
		} catch (SAXException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (XPathExpressionException e) {
			e.printStackTrace();
		}
	}
	/*
	 * DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        DocumentBuilder db = dbf.newDocumentBuilder();
        
        File file = new File("assets/acceleratingcar.cmbl");
        Document d = db.parse(new FileInputStream(file));
        XPathFactory xpf = XPathFactory.newInstance();
        XPath xp = xpf.newXPath();
       
        //NodeList nodes = (NodeList) xp.evaluate("//ColumnCells/text()",  d,  XPathConstants.NODESET);
        NodeList nodes = (NodeList) xp.evaluate("//DataColumn[DataObjectName = 'Time']/ColumnCells/text()",  d,  XPathConstants.NODESET);
        
        for (int i = 0; i < nodes.getLength(); i++) {
            System.out.println(nodes.item(i).toString());
        }
	 */
	
	private String getExpressionForDataType(String dataType) {
		return "//DataColumn[DataObjectName = '" + dataType + "']/ColumnCells";
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
