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
import models.network.CellGSM;
import models.network.CellManager;
import models.network.CellUMTS;
import models.network.LocationArea;
import models.network.LocationAreaManager;
import models.network.Network;
import views.application.ApplicationView;
import views.area.AreaView;
import views.menu.ComboOption;
import views.menu.MenuPhoneView;
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
							AreaController.Instance().getAreaView().updateCell(MenuController.this.getMenuView().getMenuAntennaView().getModelAntenna().getCellGSM());
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
					case MenuDataEvent.TYPE_SLIDER_UMTS_POWER:
						MenuController.this.getMenuView().getMenuAntennaView().updateUMTSPower(e.getValue());
						if(MenuController.this.getMenuView().getMenuAntennaView().getModelAntenna() != null && MenuController.this.getMenuView().getMenuAntennaView().getModelAntenna().getCellUMTS() != null ) {
							MenuController.this.getMenuView().getMenuAntennaView().getModelAntenna().getCellUMTS().setPower(e.getValue());
							AreaController.Instance().getAreaView().updateCell(MenuController.this.getMenuView().getMenuAntennaView().getModelAntenna().getCellUMTS());
						}
						break;
					case MenuDataEvent.TYPE_SLIDER_UMTS_ACCESS_MIN:
						MenuController.this.getMenuView().getMenuAntennaView().updateUMTSRxAccessMin(e.getValue());
						if(MenuController.this.getMenuView().getMenuAntennaView().getModelAntenna() != null && MenuController.this.getMenuView().getMenuAntennaView().getModelAntenna().getCellUMTS() != null ) {
							MenuController.this.getMenuView().getMenuAntennaView().getModelAntenna().getCellUMTS().setQRxLevMin(e.getValue());
							AreaController.Instance().getAreaView().updateCell(MenuController.this.getMenuView().getMenuAntennaView().getModelAntenna().getCellUMTS());
						}
						break;
					case MenuDataEvent.TYPE_SLIDER_UMTS_QUALITY_MIN:
						MenuController.this.getMenuView().getMenuAntennaView().updateUMTSQualityMin(e.getValue());
						if(MenuController.this.getMenuView().getMenuAntennaView().getModelAntenna() != null && MenuController.this.getMenuView().getMenuAntennaView().getModelAntenna().getCellUMTS() != null ) {
							MenuController.this.getMenuView().getMenuAntennaView().getModelAntenna().getCellUMTS().setQQualMin(e.getValue());
						}
						break;
					case MenuDataEvent.TYPE_SLIDER_UMTS_ACTIVESET_RANGE:
						MenuController.this.getMenuView().getMenuAntennaView().updateUMTSActiveSetRange(e.getValue());
						if(MenuController.this.getMenuView().getMenuAntennaView().getModelAntenna() != null && MenuController.this.getMenuView().getMenuAntennaView().getModelAntenna().getCellUMTS() != null ) {
							MenuController.this.getMenuView().getMenuAntennaView().getModelAntenna().getCellUMTS().setActiveSetRange(e.getValue());
						}
						break;

				}

			}
			
			@Override
			public void checkboxSelected(MenuControlEvent e) {
				//Network network = Network.Instance();
				Mobile mobile = Mobile.Instance();
				
				switch(e.getID()) {
					case MenuControlEvent.TYPE_CHECKBOX_CELL_GSM:
						if(MenuController.this.getMenuView().getMenuAntennaView().getModelAntenna() != null && MenuController.this.getMenuView().getMenuAntennaView().getModelAntenna().getCellGSM() != null ) {
							MenuController.this.getMenuView().getMenuAntennaView().getModelAntenna().getCellGSM().setEnabled(MenuController.this.getMenuView().getMenuAntennaView().getMenuCellGSMView().getCheckBoxGSM().isSelected());
							MenuController.this.getMenuView().getMenuAntennaView().getMenuCellGSMView().setActivated(MenuController.this.getMenuView().getMenuAntennaView().getModelAntenna().getCellGSM().isEnabled(), true);
							AreaController.Instance().getAreaView().updateCell(MenuController.this.getMenuView().getMenuAntennaView().getModelAntenna().getCellGSM());
							AreaController.Instance().getAreaView().updateActiveAntenna(MenuController.this.getMenuView().getMenuAntennaView().getModelAntenna());
						
							/*if(MenuController.this.getMenuView().getMenuAntennaView().getMenuCellGSMView().getCheckBoxGSM().isSelected()) {
								MenuController.this.getMenuView().getMenuAntennaView().getMenuCellGSMView().fillNeighbors(MenuController.this.getMenuView().getMenuAntennaView().getModelAntenna().getCellGSM());
							}
							else {
								MenuController.this.getMenuView().getMenuAntennaView().getMenuCellGSMView().fillNeighbors(null);
							}*/
							
						}
						break;
					case MenuControlEvent.TYPE_CHECKBOX_CELL_UMTS:
						if(MenuController.this.getMenuView().getMenuAntennaView().getModelAntenna() != null && MenuController.this.getMenuView().getMenuAntennaView().getModelAntenna().getCellUMTS() != null ) {
							MenuController.this.getMenuView().getMenuAntennaView().getModelAntenna().getCellUMTS().setEnabled(MenuController.this.getMenuView().getMenuAntennaView().getMenuCellUMTSView().getCheckBoxUMTS().isSelected());
							MenuController.this.getMenuView().getMenuAntennaView().getMenuCellUMTSView().setActivated(MenuController.this.getMenuView().getMenuAntennaView().getModelAntenna().getCellUMTS().isEnabled(), true);
							AreaController.Instance().getAreaView().updateCell(MenuController.this.getMenuView().getMenuAntennaView().getModelAntenna().getCellUMTS());
							AreaController.Instance().getAreaView().updateActiveAntenna(MenuController.this.getMenuView().getMenuAntennaView().getModelAntenna());
							
							/*if(MenuController.this.getMenuView().getMenuAntennaView().getMenuCellUMTSView().getCheckBoxUMTS().isSelected()) {
								MenuController.this.getMenuView().getMenuAntennaView().getMenuCellUMTSView().fillNeighbors(MenuController.this.getMenuView().getMenuAntennaView().getModelAntenna().getCellUMTS());
							}
							else {
								MenuController.this.getMenuView().getMenuAntennaView().getMenuCellUMTSView().fillNeighbors(null);
							}*/
						}
						break;
					case MenuControlEvent.TYPE_CHECKBOX_GSM:
						mobile.setGSM(! mobile.isGSM());
						break;
					case MenuControlEvent.TYPE_CHECKBOX_GPRS:
						mobile.setGPRS(! mobile.isGPRS());
						break;
					case MenuControlEvent.TYPE_CHECKBOX_EDGE:
						mobile.setEDGE(! mobile.isEDGE());
						break;
					case MenuControlEvent.TYPE_CHECKBOX_UMTS:
						mobile.setUMTS(! mobile.isUMTS());
						break;
				}
			
				MenuController.this.getMenuView().updateCheckBoxes();
				AreaController.Instance().getAreaView().updateLayerView();
			}
			
			@Override
			public void buttonClicked(MenuControlEvent e) {
				Mobile m = Mobile.Instance();
				
				switch (e.getID()) {
					case MenuControlEvent.TYPE_BUTTON_POWER:
						m.setPower(!m .isPower());
						if(m.isPower()) {
							MenuController.this.getMenuView().getMenuPhoneView().setButtonState(MenuController.this.getMenuView().getMenuPhoneView().getButtonPower(), MenuPhoneView.BUTTON_STATE_OFF);
						}
						else {
							MenuController.this.getMenuView().getMenuPhoneView().setButtonState(MenuController.this.getMenuView().getMenuPhoneView().getButtonPower(), MenuPhoneView.BUTTON_STATE_ON);
						}
						break;
					case MenuControlEvent.TYPE_BUTTON_CALL:
						m.setCalling(! m.isCalling());
						if(m.isCalling()) {
							MenuController.this.getMenuView().getMenuPhoneView().setButtonState(MenuController.this.getMenuView().getMenuPhoneView().getButtonCall(), MenuPhoneView.BUTTON_STATE_OFF);
						}
						else {
							MenuController.this.getMenuView().getMenuPhoneView().setButtonState(MenuController.this.getMenuView().getMenuPhoneView().getButtonCall(), MenuPhoneView.BUTTON_STATE_ON);
						}
						break;
					case MenuControlEvent.TYPE_BUTTON_DATA:
						m.setData(! m.isData());
						if(m.isData()) {
							MenuController.this.getMenuView().getMenuPhoneView().setButtonState(MenuController.this.getMenuView().getMenuPhoneView().getButtonData(), MenuPhoneView.BUTTON_STATE_OFF);
						}
						else {
							MenuController.this.getMenuView().getMenuPhoneView().setButtonState(MenuController.this.getMenuView().getMenuPhoneView().getButtonData(), MenuPhoneView.BUTTON_STATE_ON);
						}
						break;
				}
				
			}

			@Override
			public void ComboChanged(MenuDataEvent e) {
				int value = e.getValue();
				Antenna antenna = MenuController.this.getMenuView().getMenuAntennaView().getModelAntenna();
				
				CellGSM cellGSM;
				CellUMTS cellUMTS;
				
				switch(e.getID()) {
					case MenuDataEvent.TYPE_COMBO_AREA:
						if(antenna != null) {
							antenna.setLocationArea(LocationAreaManager.Instance().getLocationArea(value));
							AreaController.Instance().getAreaView().updateAntenna(antenna);
						}
						break;
					case MenuDataEvent.TYPE_COMBO_GSM_QSC:
						if(antenna != null) {
							cellGSM = MenuController.this.getMenuView().getMenuAntennaView().getModelAntenna().getCellGSM();
							if(cellGSM != null) {
								cellGSM.setQSC(value);
							}
						}
						break;
					case MenuDataEvent.TYPE_COMBO_GSM_QSI:
						if(antenna != null) {
							cellGSM = MenuController.this.getMenuView().getMenuAntennaView().getModelAntenna().getCellGSM();
							if(cellGSM != null) {
								cellGSM.setQSI(value);
							}
						}
						break;
					case MenuDataEvent.TYPE_COMBO_GSM_FREQUENCY:
						if(antenna != null) {
							cellGSM = MenuController.this.getMenuView().getMenuAntennaView().getModelAntenna().getCellGSM();
							if(cellGSM != null) {
								cellGSM.setFrequency(value);
								AreaController.Instance().getAreaView().updateCell(cellGSM);
							}
						}
						break;
					case MenuDataEvent.TYPE_COMBO_UMTS_FREQUENCY:
						if(antenna != null) {
							cellUMTS = MenuController.this.getMenuView().getMenuAntennaView().getModelAntenna().getCellUMTS();
							if(cellUMTS != null) {
								cellUMTS.setFrequency(value);
								AreaController.Instance().getAreaView().updateCell(cellUMTS);
							}
						}
						break;
					case MenuDataEvent.TYPE_COMBO_GSM_FREQUENCY_OFFSET:
						if(antenna != null) {
							cellGSM = MenuController.this.getMenuView().getMenuAntennaView().getModelAntenna().getCellGSM();
							if(cellGSM != null) {
								cellGSM.setFrequencyOffset(value);
								AreaController.Instance().getAreaView().updateCell(cellGSM);
							}
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
					List<ComboOption> options;
					
					Cell cell = null;
					switch(e.getID()) {
						case MenuControlEvent.TYPE_GSM_NEIGHBORS:
							 options = MenuController.this.getMenuView().getMenuAntennaView().getMenuCellGSMView().getListNeighbors().getSelectedValuesList();
							for (ComboOption option: options ) {
								neighbors.add(cellManager.getCell(option.getId()));
							}
							cell = MenuController.this.getMenuView().getMenuAntennaView().getModelAntenna().getCellGSM();
							break;
						case MenuControlEvent.TYPE_UMTS_NEIGHBORS:
							options = MenuController.this.getMenuView().getMenuAntennaView().getMenuCellUMTSView().getListNeighbors().getSelectedValuesList();
							for (ComboOption option: options ) {
								neighbors.add(cellManager.getCell(option.getId()));
							}
							cell = MenuController.this.getMenuView().getMenuAntennaView().getModelAntenna().getCellUMTS();
							break;
						default:
							break;
					}
					
					if(cell != null) {
						cell.setNeighbors(neighbors);
						AreaController.Instance().getAreaView().updateActiveAntenna(cell.getAntenna());
						
						if(Mobile.Instance().getService() != null && Mobile.Instance().getService() == cell) {
							Mobile.Instance().updateNeighbors();
						}
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