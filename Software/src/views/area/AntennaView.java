package views.area;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Font;

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
	
	public AntennaView(Antenna antennaModel) {
				
		this.setAntennaModel(antennaModel);
		this.antennaLabel = new JLabel(LoadImage.load(LoadImage.ANTENNA));
		this.antennaDesc = new JLabel("Antenna " + this.antennaModel.getId(), JLabel.CENTER);
		
		this.setLayout(new BorderLayout());
		this.setOpaque(false);
		
		this.antennaDesc.setForeground(antennaModel.getLocationArea().getColor());
		this.antennaDesc.setFont(new Font("Serif", Font.ITALIC, 10));
		
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
		
		AreaModel areaModel = AreaModel.Instance();
		
		int width = (int) Math.max( this.antennaLabel.getPreferredSize().getWidth(), this.antennaDesc.getPreferredSize().getWidth());
		int height = (int) (this.antennaLabel.getPreferredSize().getHeight() + this.antennaDesc.getPreferredSize().getHeight());
		
		int x = (int) (this.antennaModel.getX() / (double) areaModel.getAreaScale() - width / 2);
		int y = (int) (this.antennaModel.getY() / (double) areaModel.getAreaScale()  - height / 2);
		
		this.setBounds(x, y, width, height);
	}
	

}
