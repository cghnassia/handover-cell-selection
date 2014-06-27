package views.area;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.SwingConstants;

import models.area.AreaModel;
import config.MainConfig;

public class ScaleLayerView extends JPanel {
	
	private AreaModel areaModel;
	private JLabel scaleLabel;
	private JSlider scaleSlider;
	
	public ScaleLayerView() {
		this.areaModel = AreaModel.Instance();
		this.scaleSlider = new JSlider(AreaModel.AREA_MIN_SCALE, AreaModel.AREA_MAX_SCALE, AreaModel.AREA_MAX_SCALE - AreaModel.AREA_DEFAULT_SCALE + AreaModel.AREA_MIN_SCALE);
		this.scaleLabel = new JLabel(" ", SwingConstants.CENTER);
		
		this.scaleLabel.setBorder(BorderFactory.createMatteBorder(0, 0, 3, 0, Color.BLACK));
		
		this.setLayout(new BorderLayout());
		this.add(this.scaleSlider, BorderLayout.CENTER);
		
		JPanel labelContainer = new JPanel();
		labelContainer.setOpaque(false);
		labelContainer.setLayout(new BoxLayout(labelContainer, BoxLayout.Y_AXIS));
		this.scaleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
		
		labelContainer.add(this.scaleLabel);
		this.add(labelContainer, BorderLayout.NORTH);
		
		this.setOpaque(false);
		this.resize();
	}
	
	public void resize() {
		int width = (int) this.scaleSlider.getPreferredSize().getWidth();
		int height = (int) (this.scaleSlider.getPreferredSize().getHeight() + this.scaleLabel.getPreferredSize().getHeight());
		
		int x = areaModel.getAreaWidth() - width;
		int y = areaModel.getAreaHeight() - height;
		
		this.setBounds(x,  y,  width, height);
		
		Dimension d = new Dimension(width - 30, (int) this.scaleLabel.getPreferredSize().getHeight());
		this.scaleLabel.setPreferredSize(d);
		this.scaleLabel.setMaximumSize(d);
		this.scaleLabel.setMinimumSize(d);

	}
	
	public JSlider getScaleSlider() {
		return this.scaleSlider;
	}
	
	public JLabel getScaleLabel () {
		return this.scaleLabel;
	}
	


}
