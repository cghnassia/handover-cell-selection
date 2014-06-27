package views.area;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JLabel;

import config.MainConfig;
import models.application.ApplicationModel;
import models.area.AreaModel;
import models.network.Antenna;
import models.utilities.LoadImage;

public class AntennaView extends JLabel {
	
	private Antenna antennaModel;
	private JLabel antennaLabel;
	private JLabel antennaDesc;
	
	private boolean isActive;

	public AntennaView(Antenna antennaModel) {
				
		this.setAntennaModel(antennaModel);
		this.antennaLabel = new JLabel(LoadImage.load(LoadImage.ANTENNA));
		this.antennaDesc = new JLabel("Antenna " + this.antennaModel.getId(), JLabel.CENTER);
		
		this.isActive = false;
		
		this.setLayout(new BorderLayout());
		this.setOpaque(false);
		
		this.add(this.antennaLabel, BorderLayout.CENTER);
		this.add(this.antennaDesc, BorderLayout.NORTH);
		
		this.antennaLabel.setCursor(new Cursor(Cursor.HAND_CURSOR));
		this.update();
	}
	
	public Antenna getAntennaModel() {
		return this.antennaModel;
	}
	
	public void setAntennaModel(Antenna antennaModel) {
		this.antennaModel = antennaModel;
	}
	
	public void update() {
		
		this.antennaDesc.setForeground(antennaModel.getLocationArea().getColor());
		this.antennaDesc.setFont(new Font("Serif", Font.ITALIC, 10));
		
		AreaModel areaModel = AreaModel.Instance();
		
		int width = (int) Math.max( this.antennaLabel.getPreferredSize().getWidth(), this.antennaDesc.getPreferredSize().getWidth());
		int height = (int) (this.antennaLabel.getPreferredSize().getHeight() + this.antennaDesc.getPreferredSize().getHeight());
		
		int x = (int) (this.antennaModel.getX() / (double) areaModel.getAreaScale() - width / 2);
		int y = (int) (this.antennaModel.getY() / (double) areaModel.getAreaScale() - height / 2);
		
		this.setBounds(x - areaModel.getAreaX(), y - areaModel.getAreaY(), width, height);
	}
	
	public void setActive(boolean value) {
		this.isActive = value;
		this.repaint();
	}
	
	@Override
	protected void paintComponent(Graphics g) {
		// TODO Auto-generated method stub
		super.paintComponent(g);
		
		Graphics2D g2d = (Graphics2D) g;
		
		if(this.isActive) {
			
			g2d.setColor(new Color(75, 75, 150, 150));
			g2d.fillOval(this.antennaLabel.getX() + 8, this.antennaLabel.getY(), this.antennaLabel.getWidth() - 16, this.antennaLabel.getHeight());
		}
	}
	

}
