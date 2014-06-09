package views.area;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JLabel;

import config.MainConfig;
import models.area.AreaModel;
import models.mobile.Mobile;
import models.network.Cell;
import models.network.CellUMTS;
import models.network.Network;

public class CellView extends JLabel {
	
	private Cell cellModel;
	private boolean isNeighborActive;
	
	public CellView(Cell cellModel) {
		this.setCellModel(cellModel);
		this.isNeighborActive = false;
		this.setOpaque(false);
		this.update();
	}
	
	public Cell getCellModel() {
		return this.cellModel;
	}
	
	public void setCellModel(Cell cellModel) {
		this.cellModel = cellModel;
	}
	
	public void update() {
		
		AreaModel areaModel = AreaModel.Instance();
		int x = (this.cellModel.getAntenna().getX() - this.cellModel.getRadius()) / areaModel.getAreaScale();
		int y = (this.cellModel.getAntenna().getY() - this.cellModel.getRadius()) / areaModel.getAreaScale();
		int size = this.cellModel.getRadius() * 2 / areaModel.getAreaScale();
		
		this.setBounds(x - areaModel.getAreaX(), y - areaModel.getAreaY(), size, size);
		this.repaint();
	}
	
	public void setNeighborActive(boolean value) {
		//System.out.println("neighbor " + this.getCellModel().getId() + " : " + value);
		this.isNeighborActive = value;
		this.repaint();
	}
	
	@Override
	protected void paintComponent(Graphics g) {
		// TODO Auto-generated method stub
		super.paintComponents(g);
		int size = (int) this.getBounds().getWidth();
		Network network = Network.Instance();
		Graphics2D g2d = (Graphics2D) g;
		
		int coordX = (int) (size - size * Math.cos(Math.PI / 6));
		
		int[] coordsx = {(int) (size * 0.5), size - coordX, size - coordX,size / 2, coordX, coordX};
		int[] coordsy = {0, (int) (size * 0.25), (int) (size * 0.75), size, (int) (size * 0.75), (int) (size * 0.25)};
		
		if(this.getCellModel().isSelected()) {
			g2d.setStroke(new BasicStroke(3));
		}
		else {
			g2d.setStroke(new BasicStroke(1));
		}
		
		if(this.isNeighborActive) {
			//System.out.println("active : " + this.getCellModel().getId());
			g2d.setColor(new Color(125, 125, 125, 155));
			g2d.fillPolygon(coordsx, coordsy, 6);
		}
		else if(this.getCellModel().getType() == Cell.CELLTYPE_GSM) {
			if(this.getCellModel().isActive()) {
			//if(this.getCellModel().isSelected() && (Mobile.Instance().isCalling()|| Mobile.Instance().isData())) {
				g2d.setColor(MainConfig.CELLGSM_COLOR_FILLED);
				g2d.fillPolygon(coordsx, coordsy, 6);
			}
			else {
				g2d.setColor(MainConfig.CELLGSM_COLOR);
				g2d.drawPolygon(coordsx, coordsy, 6);
			}
		}
		else { // Cell.CELLTYPE_UMTS
			CellUMTS cellUMTS = (CellUMTS) this.getCellModel();
			
			if(cellUMTS.isActive()) {
			//if(this.getCellModel().isSelected() && (Mobile.Instance().isCalling()|| Mobile.Instance().isData())) {
				g2d.setColor(MainConfig.CELLUMTS_COLOR_FILLED_ACTIVE);
				g2d.fillPolygon(coordsx, coordsy, 6);
			}
			else if (cellUMTS.isInActiveSet()) {
				g2d.setColor(MainConfig.CELLUMTS_COLOR_FILLED_ACTIVESET);
				g2d.fillPolygon(coordsx, coordsy, 6);
			}
			else {
				g2d.setColor(MainConfig.CELLUMTS_COLOR);
				g2d.drawPolygon(coordsx, coordsy, 6);
			}
		}
	}

}
