package views.info;

import java.awt.Color;
import java.awt.Dimension;

import javax.swing.JPanel;

import models.info.InfoModel;

public class InfoView extends JPanel {
	
	private InfoModel infoModel;

	public InfoView(InfoModel infoModel) {
		
		this.setInfoModel(infoModel);
		
		this.setPreferredSize(new Dimension(infoModel.getInfoWidth(), infoModel.getInfoHeight()));
		this.setBackground(new Color(0, 0, 255));
	}

	public InfoModel getInfoModel() {
		return this.infoModel;
	}

	public void setInfoModel(InfoModel infoModel) {
		this.infoModel = infoModel;
	}
}
