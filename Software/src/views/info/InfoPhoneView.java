package views.info;

import java.awt.Color;
import java.awt.Dimension;

import javax.swing.JPanel;

import models.info.InfoModel;

public class InfoPhoneView extends JPanel {
	
	public InfoPhoneView() {
		
		this.setPreferredSize(new Dimension(300, InfoModel.Instance().getInfoHeight()));
		this.setBackground(new Color(0, 0, 255));
	}
}
