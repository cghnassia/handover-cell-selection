package views.area;

import java.awt.BorderLayout;
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
		this.setLayout(new BorderLayout());
		
		this.add(this.mobileLabel, BorderLayout.CENTER);
		this.mobileLabel.setCursor(new Cursor(Cursor.HAND_CURSOR));
		
		this.setOpaque(false);
		this.update();
	}
	
	public JLabel getMobileLabel() {
		return this.mobileLabel;
	}
	
	public void update() {
		AreaModel areaModel = AreaModel.Instance();
		
		int width = (int) (this.mobileLabel.getPreferredSize().getWidth());
		int height = (int) (this.mobileLabel.getPreferredSize().getHeight());
		
		int x = (int) (this.mobileModel.getX() / (double) areaModel.getAreaScale() - width / 2);
		int y = (int) (this.mobileModel.getY() / (double) areaModel.getAreaScale()  - height / 2);
		
		this.setBounds(x, y, width, height);
	}
	
}
