package views.area;


import java.awt.Color;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.Set;

import javax.swing.JLayeredPane;
import javax.swing.JSlider;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import views.menu.MenuView;
import models.application.ApplicationModel;
import models.area.AreaModel;
import models.mobile.Mobile;
import models.network.Antenna;
import models.network.Cell;
import models.network.CellGSM;
import models.network.CellUMTS;
import models.network.Network;
import config.MainConfig;
import events.AreaAntennaMoveEvent;
import events.AreaAntennaSelectEvent;
import events.AreaControlEvent;
import events.AreaDataEvent;
import events.AreaListener;
import events.AreaMoveEvent;
import events.MenuControlEvent;
import events.MenuListener;

public class AreaView extends JLayeredPane {
	
	private AreaModel areaModel;
	
	private CellsLayerView cellsLayer;
	private MobileLayerView mobileLayer;
	private ScaleLayerView scaleLayer;
	
	private Point positionMousePressed;
	
	private Antenna activeAntenna;
	
	public AreaView(AreaModel areaModel)  {
		
		this.setAreaModel(areaModel);
		
		this.cellsLayer = new CellsLayerView();
		this.mobileLayer = new MobileLayerView();
		this.scaleLayer = new ScaleLayerView();
		this.activeAntenna = null;
		
		this.setPositionMousePressed(new Point());
		
		this.add(this.cellsLayer, new Integer(0));
		this.add(this.mobileLayer, new Integer(1));
		this.add(this.scaleLayer, new Integer(2));
		
		this.setBackground(MainConfig.AREA_COLOR);
		this.setOpaque(true);
		this.setFocusable(true);
		
		this.updateScale();
		this.setLiseners();
	}
	
	public void addAreaListener(AreaListener areaListener) {
		this.listenerList.add(AreaListener.class, areaListener);
	}
	
	public void removeAreaListener(AreaListener areaListener) {
		this.listenerList.remove(AreaListener.class, areaListener);
	}
	
	private void setLiseners() {
		this.addMouseListener(new AreaClickAdapter());
		this.mobileLayer.addMouseListener(new AreaClickAdapter());
		this.addKeyListener(new KeyMoveListener());
		
		this.mobileLayer.addMouseMotionListener(new AreaMotionAdapter());
		
		for (AntennaView antennaView: this.cellsLayer.getAntennas()) {
			antennaView.addMouseMotionListener(new AreaMotionAdapter());
			antennaView.addMouseListener(new AreaClickAdapter());
		}
		
		this.scaleLayer.getScaleSlider().addChangeListener(new AreaScaleAdapter());
		
		this.addMouseMotionListener(new AreaMotionAdapter());
		this.addMouseListener(new AreaClickAdapter());
		
	}
	
	public AreaModel getAreaModel() {
		return this.areaModel;
	}

	public void setAreaModel(AreaModel areaModel) {
		this.areaModel = areaModel;
	}
	
	public MobileLayerView getMobileLayerView() {
		return this.mobileLayer;
	}
	
	public CellsLayerView getCellsLayerView() {
		return this.cellsLayer;
	}
	
	public Point getPositionMousePressed() {
		return this.positionMousePressed;
	}

	public void setPositionMousePressed(Point positionMousePressed) {
		this.positionMousePressed = positionMousePressed;
	}
	
	public void resize() {
		this.cellsLayer.setBounds(0, 0, this.getAreaModel().getAreaWidth(), this.getAreaModel().getAreaHeight());
		this.scaleLayer.resize();
		//this.mobileLayer.resize():
	}
	
	public void updateAntenna(Antenna antennaModel) {
		for (AntennaView antennaView : this.getCellsLayerView().getAntennas()) {
			if (antennaView.getAntennaModel() == antennaModel) {
				
				antennaView.update();
				break;
			}
		}
	}
	
	public void updateCell(Cell cellModel) {
		
		for (CellView cellView : this.getCellsLayerView().getCells()) {
			if(cellView.getCellModel() == cellModel) {
				cellView.update();
				break;
			}
		}
	}
	
	public void updateCellNeighborActive(Cell cellModel, boolean isNeighborActive) {
		
		for (CellView cellView : this.getCellsLayerView().getCells()) {
			if(cellView.getCellModel() == cellModel) {
				cellView.setNeighborActive(isNeighborActive);
				break;
			}
		}
	}
	
	public void updateMobile() {
		this.getMobileLayerView().update();
	}
	
	public void updateLayerView() {
		this.getCellsLayerView().updateCellsVisible();
	}
	
	public void updateScale() {
		int width = (int) this.scaleLayer.getScaleLabel().getPreferredSize().getWidth();
		int value = AreaModel.Instance().getAreaScale() * width;

		
		if(value >= 4000) {
			this.scaleLayer.getScaleLabel().setText(value / 1000 + " km");
		}
		else {
			this.scaleLayer.getScaleLabel().setText(value + " m");
		}
		
		//this.scaleLayer.resize();
		//this.scaleLayer.getScaleLabel().repaint();
	}
	
	public void updateActiveAntenna(Antenna activeAntenna) {
		
		for (AntennaView antennaView: this.getCellsLayerView().getAntennas()) {
			
			if(this.activeAntenna != null && antennaView.getAntennaModel() == this.activeAntenna) {
					antennaView.setActive(false);
			}
			
			if(activeAntenna != null) {
				if (antennaView.getAntennaModel() == activeAntenna) {
					antennaView.setActive(true);
				}
				else { // Manque GSM avec UMTS neighbors et inversement
					if (activeAntenna.getCellGSM() == null || antennaView.getAntennaModel().getCellGSM() == null) {
						//pass
					}
					else if (activeAntenna.getCellGSM().getNeighbors().contains(antennaView.getAntennaModel().getCellGSM())) {
						this.updateCellNeighborActive(antennaView.getAntennaModel().getCellGSM(), true);
					}
					else {
						this.updateCellNeighborActive(antennaView.getAntennaModel().getCellGSM(), false);
					}
					
					if (activeAntenna.getCellUMTS() == null || antennaView.getAntennaModel().getCellUMTS() == null) {
						//pass
					}
					else if (activeAntenna.getCellUMTS().getNeighbors().contains(antennaView.getAntennaModel().getCellUMTS())) {
						this.updateCellNeighborActive(antennaView.getAntennaModel().getCellUMTS(), true);
					}
					else {
						this.updateCellNeighborActive(antennaView.getAntennaModel().getCellUMTS(), false);
					}
					
				}
			}
			else {
				this.updateCellNeighborActive(antennaView.getAntennaModel().getCellUMTS(), false);
				this.updateCellNeighborActive(antennaView.getAntennaModel().getCellGSM(), false);
			}
		}
		
		this.activeAntenna = activeAntenna;
	}
	
	protected void fireFocusControlEvent(AreaControlEvent controlEvent) {
		Object[] listeners = this.listenerList.getListenerList();
	     
	     int numListeners = listeners.length;
	     for (int i = 0; i< numListeners; i += 2) 
	     {
	          if (listeners[i] == AreaListener.class) 
	          {
	               // pass the event to the listeners event dispatch method
	                ((AreaListener)listeners[i+1]).areaClicked(controlEvent);
	          }            
	     }
	}
	
	protected void fireKeyMoveControlEvent(AreaControlEvent controlEvent) {
		Object[] listeners = this.listenerList.getListenerList();
	     
	     int numListeners = listeners.length;
	     for (int i = 0; i< numListeners; i += 2) 
	     {
	          if (listeners[i] == AreaListener.class) 
	          {
	               // pass the event to the listeners event dispatch method
	                ((AreaListener)listeners[i+1]).keyboardMoved(controlEvent);
	          }            
	     }
	}
	
	protected void fireSelectAntennaEvent(AreaAntennaSelectEvent areaAntennaSelectEvent) {
		Object[] listeners = this.listenerList.getListenerList();
	     
	     int numListeners = listeners.length;
	     for (int i = 0; i< numListeners; i += 2) 
	     {
	          if (listeners[i] == AreaListener.class) 
	          {
	               // pass the event to the listeners event dispatch method
	                ((AreaListener)listeners[i+1]).antennaSelected(areaAntennaSelectEvent);
	          }            
	     }
	}
	
	protected void fireMoveAntennaEvent(AreaAntennaMoveEvent areaAntennaMoveEvent) {
		Object[] listeners = this.listenerList.getListenerList();
	     
	     int numListeners = listeners.length;
	     for (int i = 0; i< numListeners; i += 2) 
	     {
	          if (listeners[i] == AreaListener.class) 
	          {
	               // pass the event to the listeners event dispatch method
	                ((AreaListener)listeners[i+1]).antennaMoved(areaAntennaMoveEvent);
	          }            
	     }
	}
	
	protected void fireMoveMobileEvent(AreaMoveEvent areaMobileEvent) {
		Object[] listeners = this.listenerList.getListenerList();
	     
	     int numListeners = listeners.length;
	     for (int i = 0; i< numListeners; i += 2) 
	     {
	          if (listeners[i] == AreaListener.class) 
	          {
	               // pass the event to the listeners event dispatch method
	                ((AreaListener)listeners[i+1]).mobileMoved(areaMobileEvent);
	          }            
	     }
	}
	
	protected void fireMoveAreaEvent(AreaMoveEvent areaMoveEvent) {
		Object[] listeners = this.listenerList.getListenerList();
	     
	     int numListeners = listeners.length;
	     for (int i = 0; i< numListeners; i += 2) 
	     {
	          if (listeners[i] == AreaListener.class) 
	          {
	               // pass the event to the listeners event dispatch method
	                ((AreaListener)listeners[i+1]).areaMoved(areaMoveEvent);
	          }            
	     }
	}
	
	protected void fireChangeScaleEvent(AreaDataEvent areaDataEvent) {
		Object[] listeners = this.listenerList.getListenerList();
	     
	     int numListeners = listeners.length;
	     for (int i = 0; i< numListeners; i += 2) 
	     {
	          if (listeners[i] == AreaListener.class) 
	          {
	               // pass the event to the listeners event dispatch method
	                ((AreaListener)listeners[i+1]).scaleChanged(areaDataEvent);
	          }            
	     }
	}
	
	/*public void refreshMobile() {
		this.mobileLayer.refresh();
	}
	
	public void refreshCells() {
		this.cellsLayer.repaint();
	}*/


	class MouseAreaListener implements MouseListener {

		@Override
		public void mouseClicked(MouseEvent e) {
		/*	int type = AreaControlEvent.FOCUS_REQUESTED;
			AreaView.this.fireFocusControlEvent(new AreaControlEvent(AreaView.this, type));
			*/
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
	
	class KeyMoveListener implements KeyListener {

		@Override
		public void keyTyped(KeyEvent e) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void keyPressed(KeyEvent e) {
			
			int type = -1;
			
			switch(e.getKeyCode()) {
				case KeyEvent.VK_LEFT:
					type = AreaControlEvent.KEYLEFT_PRESSED;
					break;
				case KeyEvent.VK_RIGHT:
					type = AreaControlEvent.KEYRIGHT_PRESSED;
					break;
				case KeyEvent.VK_UP:
					type = AreaControlEvent.KEYUP_PRESSED;
					break;
				case KeyEvent.VK_DOWN:
					type = AreaControlEvent.KEYDOWN_PRESSED;
					break;
				default:
					break;
			}
			
			if(type != -1) {
				AreaControlEvent controlEvent = new AreaControlEvent(AreaView.this, type);
				AreaView.this.fireKeyMoveControlEvent(controlEvent);
			}
			
		}

		@Override
		public void keyReleased(KeyEvent e) {
			// TODO Auto-generated method stub
			int type = -1;
			
			switch(e.getKeyCode()) {
				case KeyEvent.VK_LEFT:
					type = AreaControlEvent.KEYLEFT_RELEASED;
					break;
				case KeyEvent.VK_RIGHT:
					type = AreaControlEvent.KEYRIGHT_RELEASED;
					break;
				case KeyEvent.VK_UP:
					type = AreaControlEvent.KEYUP_RELEASED;
					break;
				case KeyEvent.VK_DOWN:
					type = AreaControlEvent.KEYDOWN_RELEASED;
					break;
				default:
					break;
			}
			
			if(type != -1) {
				AreaControlEvent controlEvent = new AreaControlEvent(AreaView.this, type);
				AreaView.this.fireKeyMoveControlEvent(controlEvent);
			}
			
		}
		
	}
	
	class AreaMotionAdapter implements MouseMotionListener {

		@Override
		public void mouseDragged(MouseEvent e) {
			Point positionPressed = AreaView.this.getPositionMousePressed();
	
			if (e.getSource() instanceof AntennaView) {
				int type = AreaAntennaMoveEvent.ANTENNA_MOVE;
				Antenna antenna = ((AntennaView) e.getSource()).getAntennaModel();
				AreaView.this.fireMoveAntennaEvent(new AreaAntennaMoveEvent(AreaView.this, type, antenna, (int) (e.getX() - positionPressed.getX()), (int) (e.getY() - (positionPressed.getY()))));
			}
			else if (e.getSource() instanceof MobileLayerView) {
				AreaModel areaModel = AreaModel.Instance();
				int type = AreaMoveEvent.MOBILE_MOVE;
				AreaView.this.fireMoveMobileEvent(new AreaMoveEvent(AreaView.this, type, (int) (e.getX() - positionPressed.getX()), (int) (e.getY() - positionPressed.getY())));
			}
			else { //e.getSource() instanceof AreaView 
				int type = AreaMoveEvent.AREA_MOVE;
				AreaView.this.fireMoveAreaEvent(new AreaMoveEvent(AreaView.this, type, (int) (e.getX() - positionPressed.getX()), (int) (e.getY() - positionPressed.getY())));
				
				positionPressed.x = e.getX() ;
				positionPressed.y = e.getY();
			}
		}

		@Override
		public void mouseMoved(MouseEvent e) {
			// TODO Auto-generated method stub
			
		}
		
	}
	
	class AreaClickAdapter implements MouseListener {

		@Override
		public void mouseClicked(MouseEvent e) {
			int type = AreaControlEvent.FOCUS_REQUESTED;
			AreaView.this.fireFocusControlEvent(new AreaControlEvent(AreaView.this, type));
			
			if (e.getSource() instanceof AntennaView) {
				type = AreaAntennaSelectEvent.ANTENNA_SELECT;
				Antenna antenna = ((AntennaView) e.getSource()).getAntennaModel();
				AreaView.this.fireSelectAntennaEvent(new AreaAntennaSelectEvent(AreaView.this, type, antenna));
			}
			
		}

		@Override
		public void mousePressed(MouseEvent e) {
			AreaView.this.getPositionMousePressed().x = e.getX();
			AreaView.this.getPositionMousePressed().y = e.getY();
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
	
	class AreaScaleAdapter implements ChangeListener {

		@Override
		public void stateChanged(ChangeEvent e) {
			int type = AreaDataEvent.TYPE_SLIDER_SCALE;
			int value = ((JSlider) e.getSource()).getValue();
			
			AreaView.this.fireChangeScaleEvent(new AreaDataEvent(AreaView.this, type, value));
		}
		
	}

}
