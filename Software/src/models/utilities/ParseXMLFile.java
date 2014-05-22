package models.utilities;

import java.awt.Color;
import java.io.File;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import models.area.AreaModel;
import models.network.Antenna;
import models.network.AntennaManager;
import models.network.CellGSM;
import models.network.CellManager;
import models.network.CellUMTS;
import models.network.LocationArea;
import models.network.LocationAreaManager;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.w3c.dom.Node;

import config.MainConfig;

public class ParseXMLFile {
	
	public static void parse(String file) {
		
		try {
			File xmlFile = new File(file);
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			Document doc = dBuilder.parse(xmlFile);
			
			doc.getDocumentElement().normalize();
			NodeList areaList = doc.getElementsByTagName("Area");
			
			LocationAreaManager locationAreaManager = LocationAreaManager.Instance();
			AntennaManager antennasManager = AntennaManager.Instance();
			CellManager cellManager = CellManager.Instance();
			
			for (int i = 0; i < areaList.getLength(); i++) {
				
				if(!(areaList.item(i) instanceof Element)) {
					continue;
				}
				
				Element areaNode = (Element) areaList.item(i);
		
				LocationArea locationArea = new LocationArea(Integer.parseInt(areaNode.getAttribute("id")), new Color(Integer.parseInt(areaNode.getAttribute("color").substring(1), 16)));
				locationAreaManager.addLocationArea(locationArea);
				
				NodeList antennaList = areaNode.getChildNodes();
				for(int j = 0; j < antennaList.getLength(); j++) {
					
					if(!(antennaList.item(j) instanceof Element)) {
						continue;
					}
					
					Element antennaNode =  (Element) antennaList.item(j);
					
					Antenna antenna = new Antenna(Integer.parseInt(antennaNode.getAttribute("id")), Integer.parseInt(antennaNode.getAttribute("x")), Integer.parseInt(antennaNode.getAttribute("y")), locationArea);
					antennasManager.addAntenna(antenna);
					
					NodeList cellList = antennaNode.getChildNodes();
					for(int k = 0; k < cellList.getLength(); k++) {
						
						if(!(cellList.item(k) instanceof Element)) {
							continue;
						}
						
						Element cellNode = (Element) cellList.item(k);
						
						if (cellNode.getAttribute("type").contains("GSM")) {
							CellGSM cellGSM = new CellGSM(Integer.parseInt(cellNode.getAttribute("power")), Integer.parseInt(cellNode.getAttribute("frequency")), Integer.parseInt(cellNode.getAttribute("group")));
							cellManager.addCellGSM(cellGSM);
							antenna.setCellGSM(cellGSM);
						}
						else if(cellNode.getAttribute("type") == "UMTS") {
							CellUMTS cellUMTS = new CellUMTS(Integer.parseInt(cellNode.getAttribute("power")), Integer.parseInt(cellNode.getAttribute("frequency")));
							cellManager.addCellUMTS(cellUMTS);
							antenna.setCellUMTS(cellUMTS);
						}
					}
				}
			}
			
			
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
}
