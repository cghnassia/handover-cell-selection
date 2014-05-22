package controllers;

import events.AreaAntennaMoveEvent;
import events.AreaAntennaSelectEvent;
import events.AreaControlEvent;
import events.AreaListener;
import events.AreaMobileMoveEvent;
import models.application.ApplicationModel;
import models.area.AreaModel;
import models.info.InfoModel;
import models.menu.MenuModel;
import models.mobile.Mobile;
import models.network.Antenna;
import views.area.AreaView;

public class AreaController {
	
	private AreaModel areaModel;
	private AreaView areaView;
	
	private static AreaController areaController;
	
	private AreaController(AreaModel areaModel, AreaView areaView) {
		this.setAreaView(areaView);
		this.setAreaModel(areaModel);
		
		this.setListeners();
		this.resize();
	}
	
	public static AreaController Instance() {
		if (AreaController.areaController == null) {
			AreaModel areaModel = AreaModel.Instance();
			AreaView areaView = new AreaView(areaModel);
			AreaController.areaController = new AreaController(areaModel, areaView);
		}
		
		return AreaController.areaController;
	}
	
	public void setListeners() {
		this.areaView.addAreaListener(new AreaListener() {
			
			@Override
			public void mobileMoved(AreaMobileMoveEvent e) {
				AreaModel areaModel = AreaModel.Instance();
				Mobile mobile = Mobile.Instance();
				mobile.setX(e.getX() * areaModel.getAreaScale());
				mobile.setY(e.getY() * areaModel.getAreaScale());
				
			}
			
			@Override
			public void keyboardMoved(AreaControlEvent e) {
				// TODO Auto-generated method stub
			}
			
			@Override
			public void areaClicked(AreaControlEvent e) {
				AreaController.this.getAreaView().setRequestFocusEnabled(true);
				
			}
			
			@Override
			public void antennaSelected(AreaAntennaSelectEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void antennaMoved(AreaAntennaMoveEvent e) {
				AreaModel areaModel = AreaController.this.getAreaModel();
				Antenna antennaModel = e.getAntenna();
				antennaModel.setX(antennaModel.getX() + e.getX() * areaModel.getAreaScale());
				antennaModel.setY(antennaModel.getY() + e.getY() * areaModel.getAreaScale());
				AreaController.this.getAreaView().updateAntenna(antennaModel);
				
				AreaController.this.getAreaView().updateCell(antennaModel.getCellGSM());
			}
		});
	}

	public AreaModel getAreaModel() {
		return this.areaModel;
	}

	public void setAreaModel(AreaModel areaModel) {
		this.areaModel = areaModel;
	}

	public AreaView getAreaView() {
		return this.areaView;
	}

	public void setAreaView(AreaView areaView) {
		this.areaView = areaView;
	}
	
	public void resize() {
		
		ApplicationModel applicationModel = ApplicationModel.Instance();
		MenuModel menuModel = MenuModel.Instance();
		InfoModel infoModel = InfoModel.Instance();
		
		int width = applicationModel.getApplicationWidth() - menuModel.getMenuWidth();
		int height = applicationModel.getApplicationHeight() - infoModel.getInfoHeight();
		this.getAreaModel().setAreaWidth(width);
		this.getAreaModel().setAreaHeight(height);
		
		this.getAreaView().resize();
	}

}
