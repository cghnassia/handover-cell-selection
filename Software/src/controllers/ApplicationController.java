package controllers;

import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;

import views.application.ApplicationView;
import models.application.ApplicationModel;


public class ApplicationController  {
	
	private ApplicationModel applicationModel;
	private ApplicationView applicationView;
	
	private static ApplicationController applicationController;
	
	private ApplicationController(ApplicationModel applicationModel, ApplicationView applicationView) {
		
		this.setApplicationModel(applicationModel);
		this.setApplicationView(applicationView);
		
		this.setListeners();
		
		/*this.applicationModel = new ApplicationModel();
		this.applicationView = new ApplicationView(this.applicationModel);
		
		this.applicationView.addComponentListener(new ComponentResizeListener());
		this.applicationView.getAreaView().addMouseListener(new MouseAreaListener());
		this.applicationView.getAreaView().addKeyListener(new MoveListener());
		this.applicationView.getAreaView().addMouseMotionListener(new MouseMobileAdapter());
		this.applicationView.getMenuView().getSliderSpeed().addChangeListener(new SliderSpeedListener());
		this.applicationView.getMenuView().getCheckBoxGSM().addChangeListener(new CheckBoxNetworkListener());
		this.applicationView.getMenuView().getCheckBoxGPRS().addChangeListener(new CheckBoxNetworkListener());
		this.applicationView.getMenuView().getCheckBoxEDGE().addChangeListener(new CheckBoxNetworkListener());
		this.applicationView.getMenuView().getCheckBoxUMTS().addChangeListener(new CheckBoxNetworkListener());

		Thread threadModel = new Thread(this.applicationModel, "Model");
		Thread threadView = new Thread(this.applicationView, "Model");
		
		threadModel.start();
		threadView.start();*/
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
	}
	
	
	
	
	//Listener for all frame
	/*class ComponentResizeListener implements ComponentListener {

		@Override
		public void componentResized(ComponentEvent e) {
			// TODO Auto-generated method stub
			MainConfig.WINDOW_WIDTH = ApplicationController.this.applicationView.getWidth();
			MainConfig.WINDOW_HEIGHT = ApplicationController.this.applicationView.getHeight();
			
			ApplicationController.this.applicationView.getAreaView().getCellsLayerView().setBounds(0, 0, MainConfig.WINDOW_WIDTH - MainConfig.MENU_WIDTH, MainConfig.WINDOW_HEIGHT - MainConfig.INFO_HEIGHT);
			ApplicationController.this.applicationView.getAreaView().getMobileLayerView().setBounds(0, 0, MainConfig.WINDOW_WIDTH - MainConfig.MENU_WIDTH, MainConfig.WINDOW_HEIGHT - MainConfig.INFO_HEIGHT);
			
			if(ApplicationController.this.applicationModel.getMobile().getX() > (MainConfig.WINDOW_WIDTH - MainConfig.MENU_WIDTH) * MainConfig.AREA_SCALE) {
				ApplicationController.this.applicationModel.getMobile().setX((MainConfig.WINDOW_WIDTH - MainConfig.MENU_WIDTH) * MainConfig.AREA_SCALE);
			}
			
			if(ApplicationController.this.applicationModel.getMobile().getY() > (MainConfig.WINDOW_HEIGHT - MainConfig.INFO_HEIGHT) * MainConfig.AREA_SCALE) {
				ApplicationController.this.applicationModel.getMobile().setY((MainConfig.WINDOW_HEIGHT - MainConfig.INFO_HEIGHT) * MainConfig.AREA_SCALE);
			}
		}

		@Override
		public void componentMoved(ComponentEvent e) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void componentShown(ComponentEvent e) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void componentHidden(ComponentEvent e) {
			// TODO Auto-generated method stub
			
		}
		
	}
	
	//Listener for area
	class MoveListener implements KeyListener {
		
		public void keyPressed(KeyEvent event) {
			switch(event.getKeyCode()) {
				case KeyEvent.VK_LEFT:
					ApplicationController.this.applicationModel.getMobile().getMotion().addMotion(Motion.MOVE_LEFT);
					break;
				case KeyEvent.VK_RIGHT:
					ApplicationController.this.applicationModel.getMobile().getMotion().addMotion(Motion.MOVE_RIGHT);
					break;
				case KeyEvent.VK_UP:
					ApplicationController.this.applicationModel.getMobile().getMotion().addMotion(Motion.MOVE_UP);
					break;
				case KeyEvent.VK_DOWN:
					ApplicationController.this.applicationModel.getMobile().getMotion().addMotion(Motion.MOVE_DOWN);
					break;
				default:
					break;
			}
		}
		
		public void keyReleased(KeyEvent event) {
			switch(event.getKeyCode()) {
				case KeyEvent.VK_LEFT:
					ApplicationController.this.applicationModel.getMobile().getMotion().removeMotion(Motion.MOVE_LEFT);
					break;
				case KeyEvent.VK_RIGHT:
					ApplicationController.this.applicationModel.getMobile().getMotion().removeMotion(Motion.MOVE_RIGHT);
					break;
				case KeyEvent.VK_UP:
					ApplicationController.this.applicationModel.getMobile().getMotion().removeMotion(Motion.MOVE_UP);
					break;
				case KeyEvent.VK_DOWN:
					ApplicationController.this.applicationModel.getMobile().getMotion().removeMotion(Motion.MOVE_DOWN);
					break;
				default:
					break;
			}
			//System.out.println(event.getKeyCode() + " released");
		}
		
		public void keyTyped(KeyEvent event) {
			//nothing to do;
		}
	}
	
	class MouseAreaListener implements MouseListener {

		@Override
		public void mouseClicked(MouseEvent e) {
			// TODO
			ApplicationController.this.applicationView.getAreaView().requestFocus();
			
		}

		@Override
		public void mousePressed(MouseEvent e) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void mouseReleased(MouseEvent e) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void mouseEntered(MouseEvent e) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void mouseExited(MouseEvent e) {
			// TODO Auto-generated method stub
			
		}
	}
	
	class MouseMobileAdapter implements MouseMotionListener {

		@Override
		public void mouseDragged(MouseEvent e) {
			if(e.getX() > 0 && e.getY() > 0 && e.getX() < MainConfig.WINDOW_WIDTH - MainConfig.MENU_WIDTH && e.getY() < MainConfig.WINDOW_HEIGHT - MainConfig.INFO_HEIGHT) {
				ApplicationController.this.getModel().getMobile().setX(e.getX() * MainConfig.AREA_SCALE);
				ApplicationController.this.getModel().getMobile().setY(e.getY() * MainConfig.AREA_SCALE);
			}
		}

		@Override
		public void mouseMoved(MouseEvent e) {
			// TODO Auto-generated method stub
			
		}
		
	}
	
	//listeners for menu
	class SliderSpeedListener implements ChangeListener {

		@Override
		public void stateChanged(ChangeEvent e) {
			int value = ApplicationController.this.getView().getMenuView().getSliderSpeed().getValue();
			ApplicationController.this.getModel().getMobile().setSpeed(value);
			ApplicationController.this.getView().getMenuView().updateSpeed(value);
			
		}
	}
	
	class CheckBoxNetworkListener implements ChangeListener {

		@Override
		public void stateChanged(ChangeEvent e) {
			// TODO Auto-generated method stub
			if (ApplicationController.this.getView().getMenuView().getCheckBoxGSM().isSelected()) {
				ApplicationController.this.getView().getMenuView().getCheckBoxGPRS().setEnabled(true);
				ApplicationController.this.getView().getMenuView().getCheckBoxEDGE().setEnabled(true);
			}
			else {
				ApplicationController.this.getView().getMenuView().getCheckBoxGPRS().setEnabled(false);
				ApplicationController.this.getView().getMenuView().getCheckBoxEDGE().setEnabled(false);
			}
			
			ApplicationController.this.getModel().getNetwork().setGSM(ApplicationController.this.getView().getMenuView().getCheckBoxGSM().isSelected());
			ApplicationController.this.getModel().getNetwork().setUMTS(ApplicationController.this.getView().getMenuView().getCheckBoxUMTS().isSelected());
			ApplicationController.this.getModel().getNetwork().setGPRS(ApplicationController.this.getView().getMenuView().getCheckBoxGPRS().isSelected() && ApplicationController.this.getView().getMenuView().getCheckBoxGPRS().isEnabled());
			ApplicationController.this.getModel().getNetwork().setEDGE(ApplicationController.this.getView().getMenuView().getCheckBoxEDGE().isSelected() && ApplicationController.this.getView().getMenuView().getCheckBoxEDGE().isEnabled());
			
			//ApplicationController.this.getView().getAreaView().getCellsLayerView().repaint();
		}
		
	}*/
	
}
