package controllers;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.swing.JComboBox;
import javax.swing.JList;

import events.MenuControlEvent;
import events.MenuDataEvent;
import events.MenuListener;
import models.application.ApplicationModel;
import models.menu.MenuModel;
import models.mobile.Mobile;
import models.network.Antenna;
import models.network.Cell;
import models.network.CellManager;
import models.network.LocationArea;
import models.network.LocationAreaManager;
import models.network.Network;
import views.application.ApplicationView;
import views.area.AreaView;
import views.menu.ComboOption;
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
				switch(e.getID()) {
					case MenuDataEvent.TYPE_SLIDER_SPEED:
						MenuController.this.getMenuView().updateSpeed(e.getValue());
						Mobile.Instance().setSpeed(e.getValue());
						break;
					case MenuDataEvent.TYPE_SLIDER_GSM_POWER:
						MenuController.this.getMenuView().getMenuAntennaView().updateGSMPower(e.getValue());
						if(MenuController.this.getMenuView().getMenuAntennaView().getModelAntenna() != null && MenuController.this.getMenuView().getMenuAntennaView().getModelAntenna().getCellGSM() != null ) {
							MenuController.this.getMenuView().getMenuAntennaView().getModelAntenna().getCellGSM().setPower(e.getValue());
							AreaController.Instance().getAreaView().updateCell(MenuController.this.getMenuView().getMenuAntennaView().getModelAntenna().getCellGSM());
						}
						break;
					case MenuDataEvent.TYPE_SLIDER_GSM_ACCESS_MIN:
						MenuController.this.getMenuView().getMenuAntennaView().updateGSMRxAccessMin(e.getValue());
						if(MenuController.this.getMenuView().getMenuAntennaView().getModelAntenna() != null && MenuController.this.getMenuView().getMenuAntennaView().getModelAntenna().getCellGSM() != null ) {
							MenuController.this.getMenuView().getMenuAntennaView().getModelAntenna().getCellGSM().setRxAccessMin(e.getValue());
						}
						break;
					case MenuDataEvent.TYPE_SLIDER_GSM_RESELECT_OFFSET:
						MenuController.this.getMenuView().getMenuAntennaView().updateGSMReselectOffset(e.getValue());
						if(MenuController.this.getMenuView().getMenuAntennaView().getModelAntenna() != null && MenuController.this.getMenuView().getMenuAntennaView().getModelAntenna().getCellGSM() != null ) {
							MenuController.this.getMenuView().getMenuAntennaView().getModelAntenna().getCellGSM().setReselectOffset(e.getValue());
						}
						break;
					case MenuDataEvent.TYPE_SLIDER_GSM_RESELECT_HYSTERESIS:
						MenuController.this.getMenuView().getMenuAntennaView().updateGSMReselectHysteresis(e.getValue());
						if(MenuController.this.getMenuView().getMenuAntennaView().getModelAntenna() != null && MenuController.this.getMenuView().getMenuAntennaView().getModelAntenna().getCellGSM() != null ) {
							MenuController.this.getMenuView().getMenuAntennaView().getModelAntenna().getCellGSM().setReselectHysteresis(e.getValue());
						}
						break;
				}

			}
			
			@Override
			public void checkboxSelected(MenuControlEvent e) {
				Network network = Network.Instance();
				
				switch(e.getID()) {
					case MenuControlEvent.TYPE_CHECKBOX_CELL_GSM:
						//Antenna for GSM
						break;
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
				Mobile m = Mobile.Instance();
				
				switch (e.getID()) {
					case MenuControlEvent.TYPE_BUTTON_CALL:
						m.setCalling(! m.isCalling());
						break;
					case MenuControlEvent.TYPE_BUTTON_DATA:
						m.setData(! m.isData());
						break;
				}
				
			}

			@Override
			public void ComboChanged(MenuDataEvent e) {
				int value = e.getValue();
				//System.out.print("area ID : " + value);
				switch(e.getID()) {
					case MenuDataEvent.TYPE_COMBO_AREA:
						Antenna antenna = MenuController.this.getMenuView().getMenuAntennaView().getModelAntenna();
						if(antenna != null) {
							antenna.setLocationArea(LocationAreaManager.Instance().getLocationArea(value));
							AreaController.Instance().getAreaView().updateAntenna(antenna);
						}
						break;
					default:
						break;
				}
				
			}

			@Override
			public void listChanged(MenuControlEvent e) {
				//for neighbors
				
				if(MenuController.this.getMenuView().getMenuAntennaView().getModelAntenna() != null) {
					
					CellManager cellManager = CellManager.Instance();
					Set<Cell> neighbors = new HashSet();
					
					Cell cell = null;
					switch(e.getID()) {
						case MenuControlEvent.TYPE_GSM_NEIGHBORS:
							List<ComboOption> options = MenuController.this.getMenuView().getMenuAntennaView().getMenuCellGSMView().getListNeighbors().getSelectedValuesList();
							for (ComboOption option: options ) {
								neighbors.add(cellManager.getCell(option.getId()));
							}
							cell = MenuController.this.getMenuView().getMenuAntennaView().getModelAntenna().getCellGSM();
							break;
						default:
							break;
					}
					
					if(cell != null) {
						cell.setNeighbors(neighbors);
						AreaController.Instance().getAreaView().updateActiveAntenna(cell.getAntenna());
					}
				}
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