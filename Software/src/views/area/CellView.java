package views.area;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JLabel;

import config.MainConfig;
import models.area.AreaModel;
import models.network.Cell;

public class CellView extends JLabel {
	
	private Cell cellModel;
	
	public CellView(Cell cellModel) {
		this.setCellModel(cellModel);
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
		
		this.setBounds(x, y, size, size);
		this.repaint();
	}
	
	@Override
	protected void paintComponent(Graphics g) {
		// TODO Auto-generated method stub
		super.paintComponents(g);
		
		int size = (int) this.getBounds().getWidth();
		
		Graphics2D g2d = (Graphics2D) g;
		
		g2d.setStroke(new BasicStroke(1));
		g2d.setColor(MainConfig.CELLGSM_COLOR);
		
		int coordX = (int) (size - size * Math.cos(Math.PI / 6));
		
		int[] coordsx = {(int) (size * 0.5), size - coordX, size - coordX,size / 2, coordX, coordX};
		int[] coordsy = {0, (int) (size * 0.25), (int) (size * 0.75), size, (int) (size * 0.75), (int) (size * 0.25)};
		
		/*for (int i = 0; i < 6; i++) {
			System.out.println(coordsx[i] + ", " + coordsy[i]);
		}
		System.out.println("----------");*/
		
		g2d.drawPolygon(coordsx, coordsy, 6);
	}

}
