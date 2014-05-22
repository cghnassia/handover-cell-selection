package views.area;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Graphics;
import java.io.File;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

import models.area.AreaModel;
import models.mobile.Mobile;
import models.utilities.LoadImage;
import config.MainConfig;

public class MobileLayerView extends JPanel {
	
	private Mobile mobileModel;
	private JLabel mobileLabel;
	
	public MobileLayerView() {
		
		this.mobileModel = Mobile.Instance();
		this.mobileLabel = new JLabel(LoadImage.load(LoadImage.MOBILE));
		
		this.setOpaque(false);
		this.setLayout(null);
		
		this.add(this.mobileLabel);
		this.mobileLabel.setCursor(new Cursor(Cursor.HAND_CURSOR));
		
		this.updatePosition();
	}
	
	public JLabel getMobileLabel() {
		return this.mobileLabel;
	}
	
	public void refresh() {
		this.updatePosition();
	}
	
	private void updatePosition() {
		AreaModel areaModel = AreaModel.Instance();
		this.mobileLabel.setBounds(this.mobileModel.getX() / areaModel.getAreaScale() - MainConfig.MOBILE_WIDTH / 2, this.mobileModel.getY() / areaModel.getAreaScale() - MainConfig.MOBILE_HEIGHT / 2, MainConfig.MOBILE_WIDTH, MainConfig.MOBILE_HEIGHT);
	}
	
}
