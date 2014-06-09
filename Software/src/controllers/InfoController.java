package controllers;

import views.info.InfoView;
import models.info.InfoModel;
import models.network.Cell;

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
	
	public void Selection(Cell cell) {
	
	}

	public void idleReselection (Cell cellBefore, Cell cellAfter) {
		
	}
	
	public void handover(Cell cellBefore, Cell cellAfter) {
		
	}
	
	public void dataReslection(Cell cellBefore, Cell cellAfter) {
		
	}
}
