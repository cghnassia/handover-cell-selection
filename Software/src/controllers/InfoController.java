package controllers;

import views.info.InfoView;
import models.info.InfoModel;

public class InfoController {

	private InfoModel infoModel;
	private InfoView infoView;	
	
	private static InfoController infoController;
	
	public InfoController(InfoModel infoModel, InfoView infoView) {
		this.setInfoView(infoView);
		this.setInfoModel(infoModel);
	}
	
	public static InfoController Instance() {
		if(InfoController.infoController == null) {
			InfoModel infoModel = InfoModel.Instance();
			InfoView infoView = new InfoView(infoModel);
			InfoController.infoController = new InfoController(infoModel, infoView);
		}
		
		return InfoController.infoController;
	}

	public InfoModel getInfoModel() {
		return this.infoModel;
	}

	public void setInfoModel(InfoModel infoModel) {
		this.infoModel = infoModel;
	}

	public InfoView getInfoView() {
		return this.infoView;
	}

	public void setInfoView(InfoView infoView) {
		this.infoView = infoView;
	}
}
