package controllers;

import events.MenuControlEvent;
import events.MenuDataEvent;
import events.MenuListener;
import models.application.ApplicationModel;
import models.menu.MenuModel;
import models.mobile.Mobile;
import models.network.Cell;
import models.network.CellManager;
import models.network.Network;
import views.application.ApplicationView;
import views.area.AreaView;
import views.menu.MenuView;

public class MenuController {

	private MenuModel menuModel;
	private MenuView menuView;	
	
	private static MenuController menuController;
	
	private MenuController(MenuModel menuModel, MenuView menuView) {
		this.setMenuView(menuView);
		this.setMenuModel(menuModel);
				
		this.getMenuView().addMenuListener(new MenuListener() {
			
			@Override
			public void sliderChanged(MenuDataEvent e) {
				
				MenuController.this.getMenuView().updateSpeed(e.getValue());
				Mobile.Instance().setSpeed(e.getValue());
			}
			
			@Override
			public void checkboxSelected(MenuControlEvent e) {
				Network network = Network.Instance();
				
				switch(e.getID()) {
					case MenuControlEvent.TYPE_CHECKBOX_GSM:
						network.setGSM(! network.isGSM());
						break;
					case MenuControlEvent.TYPE_CHECKBOX_GPRS:
						network.setGPRS(! network.isGPRS());
						break;
					case MenuControlEvent.TYPE_CHECKBOX_EDGE:
						network.setEDGE(! network.isEDGE());
						break;
					case MenuControlEvent.TYPE_CHECKBOX_UMTS:
						network.setUMTS(! network.isUMTS());
						break;
				}
			
				MenuController.this.menuView.updateCheckBoxes();
				AreaController.Instance().getAreaView().updateLayerView();
			}
			
			@Override
			public void buttonClicked(MenuControlEvent e) {
				System.out.println("button clicked" + e.getID());
				
			}
		});
	}
	
	public static MenuController Instance() {
		
		if(MenuController.menuController == null) {
			MenuModel menuModel = MenuModel.Instance();
			MenuView menuView = new MenuView(menuModel);
			MenuController.menuController = new MenuController(menuModel, menuView);
		}
		
		return MenuController.menuController;
	}

	public MenuModel getMenuModel() {
		return this.menuModel;
	}

	public void setMenuModel(MenuModel menuModel) {
		this.menuModel = menuModel;
	}

	public MenuView getMenuView() {
		return this.menuView;
	}

	public void setMenuView(MenuView menuView) {
		this.menuView = menuView;
	}
}