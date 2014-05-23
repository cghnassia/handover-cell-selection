package views.area;

import java.awt.BorderLayout;
import java.awt.Color;

import javax.swing.JPanel;
import javax.swing.JSlider;

import models.area.AreaModel;
import config.MainConfig;

public class ScaleLayerView extends JPanel {
	
	private AreaModel areaModel;
	private JSlider scaleSlider;
	
	public ScaleLayerView() {
		this.areaModel = AreaModel.Instance();
		this.scaleSlider = new JSlider(AreaModel.AREA_MIN_SCALE, AreaModel.AREA_MAX_SCALE, AreaModel.AREA_MAX_SCALE - AreaModel.AREA_DEFAULT_SCALE + AreaModel.AREA_MIN_SCALE);
		
		this.setLayout(new BorderLayout());
		this.add(scaleSlider, BorderLayout.CENTER);
	
		this.setOpaque(false);
		this.resize();
	}
	
	public void resize() {
		int width = (int) this.scaleSlider.getPreferredSize().getWidth();
		int height = (int) this.scaleSlider.getPreferredSize().getHeight();
		
		int x = areaModel.getAreaWidth() - width;
		int y = areaModel.getAreaHeight() - height;
		
		this.setBounds(x,  y,  width, height);
	}
	
	public JSlider getScalSlider() {
		return this.scaleSlider;
	}
	


}
