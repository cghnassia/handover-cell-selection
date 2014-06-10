package controllers;

import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;

import events.CellEvent;
import events.CellListener;
import views.application.ApplicationView;
import views.area.AreaView;
import models.application.ApplicationModel;
import models.mobile.Mobile;
import models.network.Cell;
import models.network.CellManager;


public class ApplicationController  {
	
	private ApplicationModel applicationModel;
	private ApplicationView applicationView;
	private Mobile mobile;
	
	private static ApplicationController applicationController;
	
	private ApplicationController(ApplicationModel applicationModel, ApplicationView applicationView) {
		
		this.setApplicationModel(applicationModel);
		this.setApplicationView(applicationView);
		this.mobile = Mobile.Instance();
		
		this.setListeners();

		//Thread used for Measure
		Thread threadMobile = new Thread(this.mobile, "Mobile");
		threadMobile.start();
	}
	
	public static ApplicationController Instance() {
		
		if(ApplicationController.applicationController == null) {
			ApplicationModel applicationModel = ApplicationModel.Instance();
			ApplicationView applicationView = new ApplicationView(applicationModel);
			ApplicationController.applicationController = new ApplicationController(applicationModel, applicationView);
		}
		
		return ApplicationController.applicationController;
	}
	
	public ApplicationModel getApplicationModel() {
		return this.applicationModel;
	}
	
	public void setApplicationModel(ApplicationModel applicationModel) {
		this.applicationModel = applicationModel;
	}
	
	public ApplicationView getApplicationView() {
		return this.applicationView;
	}
	
	public void setApplicationView(ApplicationView applicationView) {
		this.applicationView = applicationView;
	}
	
	private void setListeners() {
		
		this.getApplicationView().addComponentListener(new ComponentListener() {
			
			@Override
			public void componentShown(ComponentEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void componentResized(ComponentEvent e) {
				
				
				int width = ApplicationController.this.getApplicationView().getWidth();
				int height = ApplicationController.this.getApplicationView().getHeight();
				
				ApplicationController.this.getApplicationModel().setApplicationWidth(width);
				ApplicationController.this.getApplicationModel().setApplicationHeight(height);
				
				AreaController.Instance().resize();
			}
			
			@Override
			public void componentMoved(ComponentEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void componentHidden(ComponentEvent e) {
				// TODO Auto-generated method stub
				
			}
		});

		for (Cell cell : CellManager.Instance().getCellsGSM()) {
			cell.addCellListener(new CellListener() {
				@Override
				public void cellUnselected(CellEvent e) {
					AreaController areaController = AreaController.Instance();
					areaController.getAreaView().updateCell((Cell) e.getSource());
				}
				
				@Override
				public void cellSelected(CellEvent e) {
					AreaController areaController = AreaController.Instance();
					areaController.getAreaView().updateCell((Cell) e.getSource());
				}

				@Override
				public void cellActived(CellEvent e) {
					AreaController areaController = AreaController.Instance();
					areaController.getAreaView().updateCell((Cell) e.getSource());
					
				}

				@Override
				public void cellUnactived(CellEvent e) {
					AreaController areaController = AreaController.Instance();
					areaController.getAreaView().updateCell((Cell) e.getSource());
					
				}

				@Override
				public void cellAddedInActiveSet(CellEvent e) {
					//no active set in GSM
				}

				@Override
				public void cellRemovedFromActiveSet(CellEvent e) {
					//no active set in GSM
				}
			});
		}
		
		for (Cell cell : CellManager.Instance().getCellsUMTS()) {
			cell.addCellListener(new CellListener() {
				@Override
				public void cellUnselected(CellEvent e) {
					AreaController areaController = AreaController.Instance();
					areaController.getAreaView().updateCell((Cell) e.getSource());
				}
				
				@Override
				public void cellSelected(CellEvent e) {
					AreaController areaController = AreaController.Instance();
					areaController.getAreaView().updateCell((Cell) e.getSource());
				}

				@Override
				public void cellActived(CellEvent e) {
					AreaController areaController = AreaController.Instance();
					areaController.getAreaView().updateCell((Cell) e.getSource());
					
				}

				@Override
				public void cellUnactived(CellEvent e) {
					AreaController areaController = AreaController.Instance();
					areaController.getAreaView().updateCell((Cell) e.getSource());
					
				}
				
				@Override
				public void cellAddedInActiveSet(CellEvent e) {
					AreaController areaController = AreaController.Instance();
					areaController.getAreaView().updateCell((Cell) e.getSource());
					
					//System.out.println("Active Set : " + Mobile.Instance().getModuleUMTS().getActiveSet());
					
				}

				@Override
				public void cellRemovedFromActiveSet(CellEvent e) {
					AreaController areaController = AreaController.Instance();
					areaController.getAreaView().updateCell((Cell) e.getSource());	
					
					//System.out.println("Removed from Active Set : " + Mobile.Instance().getModuleUMTS().getActiveSet());
				}
			});
		}
	}
}