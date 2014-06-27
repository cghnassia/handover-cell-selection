package models.info;

import models.application.ApplicationModel;

public class InfoModel {
	
	private int infoWidth;
	private int infoHeight;
	
	private static InfoModel infoModel;
	
	private static final int INFO_HEIGHT = 150;
	
	private InfoModel() {
		this.setInfoWidth(ApplicationModel.Instance().getApplicationWidth());
		this.setInfoHeight(InfoModel.INFO_HEIGHT);
	}
	
	public static InfoModel Instance() {
		
		if (InfoModel.infoModel == null) {
			InfoModel.infoModel = new InfoModel();
		}
		
		return InfoModel.infoModel;
	}
	
	public int getInfoWidth() {
		return this.infoWidth;
	}

	public void setInfoWidth(int infoWidth) {
		this.infoWidth = infoWidth;
	}

	public int getInfoHeight() {
		return this.infoHeight;
	}

	public void setInfoHeight(int infoHeight) {
		this.infoHeight = infoHeight;
	}
}
