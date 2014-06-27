package controllers;

import config.MainConfig;
import events.AreaAntennaMoveEvent;
import events.AreaAntennaSelectEvent;
import events.AreaControlEvent;
import events.AreaDataEvent;
import events.AreaListener;
import events.AreaMoveEvent;
import models.application.ApplicationModel;
import models.area.AreaModel;
import models.info.InfoModel;
import models.menu.MenuModel;
import models.mobile.Mobile;
import models.mobile.Motion;
import models.network.Antenna;
import models.network.AntennaManager;
import models.network.Cell;
import models.network.CellGSM;
import models.network.CellManager;
import models.network.CellUMTS;
import views.area.AreaView;

public class AreaController implements Runnable {
	
	private AreaModel areaModel;
	private AreaView areaView;
	
	private static AreaController areaController;
	
	private AreaController(AreaModel areaModel, AreaView areaView) {
		this.setAreaView(areaView);
		this.setAreaModel(areaModel);
		
		this.setListeners();
		this.resize();
		
		Mobile mobile = Mobile.Instance();
		mobile.setX(areaModel.getAreaWidth() * areaModel.getAreaScale() / 2);
		mobile.setY(areaModel.getAreaHeight() * areaModel.getAreaScale() / 2);
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
			public void mobileMoved(AreaMoveEvent e) {
	
				AreaModel areaModel = AreaModel.Instance();
				Mobile mobile = Mobile.Instance();
				mobile.setX(mobile.getX() + e.getX() * areaModel.getAreaScale());
				mobile.setY(mobile.getY() + e.getY() * areaModel.getAreaScale());		
				
				AreaController.this.getAreaView().updateMobile();
			}
			
			@Override
			public void keyboardMoved(AreaControlEvent e) {
				switch (e.getID()) {
					case AreaControlEvent.KEYLEFT_PRESSED:
						Mobile.Instance().getMotion().addMotion(Motion.MOVE_LEFT);
						break;
					case AreaControlEvent.KEYLEFT_RELEASED:
						Mobile.Instance().getMotion().removeMotion(Motion.MOVE_LEFT);
						break;	
					case AreaControlEvent.KEYRIGHT_PRESSED:
						Mobile.Instance().getMotion().addMotion(Motion.MOVE_RIGHT);
						break;
					case AreaControlEvent.KEYRIGHT_RELEASED:
						Mobile.Instance().getMotion().removeMotion(Motion.MOVE_RIGHT);
						break;
					case AreaControlEvent.KEYUP_PRESSED:
						Mobile.Instance().getMotion().addMotion(Motion.MOVE_UP);
						break;
					case AreaControlEvent.KEYUP_RELEASED:
						Mobile.Instance().getMotion().removeMotion(Motion.MOVE_UP);
						break;
					case AreaControlEvent.KEYDOWN_PRESSED:
						Mobile.Instance().getMotion().addMotion(Motion.MOVE_DOWN);
						break;
					case AreaControlEvent.KEYDOWN_RELEASED:
						Mobile.Instance().getMotion().removeMotion(Motion.MOVE_DOWN);
						break;
				}
				

			}
			
			@Override
			public void areaClicked(AreaControlEvent e) {

				AreaController.this.getAreaView().requestFocus();
				MenuController.Instance().getMenuView().getMenuAntennaView().setModelAntenna(null);
				AreaController.this.getAreaView().updateActiveAntenna(null);
				
			}
			
			@Override
			public void antennaSelected(AreaAntennaSelectEvent e) {
				
				AreaController.this.getAreaView().requestFocus();
				MenuController.Instance().getMenuView().getMenuAntennaView().setModelAntenna(e.getAntenna());
				AreaController.this.getAreaView().updateActiveAntenna(e.getAntenna());
				
			}
			
			@Override
			public void antennaMoved(AreaAntennaMoveEvent e) {
				AreaModel areaModel = AreaController.this.getAreaModel();
				Antenna antennaModel = e.getAntenna();
				antennaModel.setX(antennaModel.getX() + e.getX() * areaModel.getAreaScale());
				antennaModel.setY(antennaModel.getY() + e.getY() * areaModel.getAreaScale());
				
				AreaController.this.getAreaView().updateAntenna(antennaModel);
				AreaController.this.getAreaView().updateCell(antennaModel.getCellGSM());
				AreaController.this.getAreaView().updateCell(antennaModel.getCellUMTS());
			}

			@Override
			public void scaleChanged(AreaDataEvent e) {
				AreaModel areaModel = AreaController.this.getAreaModel();
				
				int areaScale = AreaModel.AREA_MAX_SCALE - e.getValue() + AreaModel.AREA_MIN_SCALE;
				//int areaX = (int) ((double) areaModel.getAreaX() + (double) areaModel.getAreaWidth() * (1 - (double) areaScale / (double) areaModel.getAreaScale()));
				//int areaY = (int) ((double) areaModel.getAreaY() + (double) areaModel.getAreaHeight() * (1 - (double) areaScale / (double) areaModel.getAreaScale()));
				
				//System.out.println("old area X : " + areaModel.getAreaX());
				//System.out.println("new area X : " + areaX);
				
				/*System.out.println("areaScale : before " + areaModel.getAreaScale() + "after " + areaScale);
				System.out.println("areaWidth : " + areaModel.getAreaWidth());
				System.out.println("areaX : before " + areaModel.getAreaX() + " - after " + areaX);*/
				
				areaModel.setAreaScale(areaScale);
				//areaModel.setAreaX(areaX);
				//areaModel.setAreaY(areaY);
				
				AreaController.this.getAreaView().updateScale();
				
				for (Antenna antennaModel : AntennaManager.Instance().getAntennas()) {
					AreaController.this.getAreaView().updateAntenna(antennaModel);
					AreaController.this.getAreaView().updateCell(antennaModel.getCellGSM());
					AreaController.this.getAreaView().updateCell(antennaModel.getCellUMTS());
				}
				
				//Mobile.Instance().checkIsInArea();
				areaModel.checkBounds();
				
			}

			@Override
			public void areaMoved(AreaMoveEvent e) {
				int x = e.getX();
				int y = e.getY();

				AreaModel areaModel = AreaController.this.getAreaModel();
				areaModel.setAreaX(areaModel.getAreaX() - x);
				areaModel.setAreaY(areaModel.getAreaY() - y);
				
				AreaController.this.getAreaView().updateMobile();
				for (Antenna antennaModel : AntennaManager.Instance().getAntennas()) {
					AreaController.this.getAreaView().updateAntenna(antennaModel);
					AreaController.this.getAreaView().updateCell(antennaModel.getCellGSM());
					AreaController.this.getAreaView().updateCell(antennaModel.getCellUMTS());
				}
		
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
	
	@Override
	public void run() {
		while(true) {
			try {
				Mobile.Instance().updatePosition();
				this.getAreaView().updateMobile();
				Thread.currentThread().sleep(1000 / ApplicationModel.Instance().getFpsRate());
			}
			catch(InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

}
