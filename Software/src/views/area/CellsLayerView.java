package views.area;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Ellipse2D.Double;
import java.util.HashSet;
import java.util.Set;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

import config.MainConfig;
import models.application.ApplicationModel;
import models.area.AreaModel;
import models.network.Antenna;
import models.network.AntennaManager;
import models.network.Cell;
import models.network.CellGSM;
import models.network.CellManager;
import models.network.CellUMTS;
import models.network.Network;
import models.utilities.LoadImage;

public class CellsLayerView extends JPanel {
	
	private Set<AntennaView> antennas;
	private Set<CellView> cells;

	public CellsLayerView() {
		
		this.antennas = new HashSet<>();
		this.cells = new HashSet<>();
		
		Network network = Network.Instance();
		AntennaManager antennaManager = AntennaManager.Instance();
		for (Antenna antenna: antennaManager.getAntennas()) {
			
			AntennaView antennaView = new AntennaView(antenna);
			
			if(antenna.getCellGSM() != null) {
				CellView cellView = new CellView(antenna.getCellGSM());
				this.cells.add(cellView);
				this.add(cellView);
			}
			
			if(antenna.getCellUMTS() != null) {
				CellView cellView = new CellView(antenna.getCellUMTS());
				this.cells.add(cellView);
				this.add(cellView);
			}
			
			this.antennas.add(antennaView);
			this.add(antennaView);
		}
		
		this.setLayout(null);
		this.setOpaque(false);
		
		this.resize();
	}
	
	public Set<AntennaView> getAntennas() {
		return this.antennas;
	}
	
	public Set<CellView> getCells() {
		return this.cells;
	}
	
	public void updateCellsVisible() {
		Network network = Network.Instance();
		for (CellView cellView: this.cells) {
			if(cellView.getCellModel().getType() == Cell.CELLTYPE_GSM) {
				cellView.setVisible(network.isGSM());
			}
			else {
				cellView.setVisible(network.isUMTS());
			}
		}
	}
	
	public void resize() {
		AreaModel areaModel = AreaModel.Instance();
		this.setBounds(0, 0, areaModel.getAreaWidth(), areaModel.getAreaHeight());
	}
	
}
