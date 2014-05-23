package views.area;


import java.awt.Color;
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
import events.AreaListener;
import events.AreaMobileMoveEvent;
import events.MenuControlEvent;
import events.MenuListener;

public class AreaView extends JLayeredPane {
	
	private AreaModel areaModel;
	
	private CellsLayerView cellsLayer;
	private MobileLayerView mobileLayer;
	
	private Point positionMousePressed;
	
	public AreaView(AreaModel areaModel)  {
		
		this.setAreaModel(areaModel);
		
		this.cellsLayer = new CellsLayerView();
		this.mobileLayer = new MobileLayerView();
		this.setPositionMousePressed(new Point());
		
		this.add(this.cellsLayer, new Integer(0));
		this.add(this.mobileLayer, new Integer(1));
		
		this.setBackground(MainConfig.AREA_COLOR);
		this.setOpaque(true);
		this.setFocusable(true);
		
		this.setLiseners();
	}
	
	public void addAreaListener(AreaListener areaListener) {
		this.listenerList.add(AreaListener.class, areaListener);
	}
	
	public void removeAreaListener(AreaListener areaListener) {
		this.listenerList.remove(AreaListener.class, areaListener);
	}
	
	private void setLiseners() {
		this.addMouseListener(new MouseAreaListener());
		this.addKeyListener(new KeyMoveListener());
		
		this.mobileLayer.addMouseMotionListener(new AreaMotionAdapter());
		this.mobileLayer.addMouseListener(new AreaClickAdapter());
		
		for (AntennaView antennaView: this.cellsLayer.getAntennas()) {
			antennaView.addMouseMotionListener(new AreaMotionAdapter());
			antennaView.addMouseListener(new AreaClickAdapter());
		}
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
	
	public void updateMobile() {
		this.getMobileLayerView().update();
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
	
	protected void fireMoveMobileEvent(AreaMobileMoveEvent areaMobileMoveEvent) {
		Object[] listeners = this.listenerList.getListenerList();
	     
	     int numListeners = listeners.length;
	     for (int i = 0; i< numListeners; i += 2) 
	     {
	          if (listeners[i] == AreaListener.class) 
	          {
	               // pass the event to the listeners event dispatch method
	                ((AreaListener)listeners[i+1]).mobileMoved(areaMobileMoveEvent);
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
			int type = AreaControlEvent.FOCUS_REQUESTED;
			AreaView.this.fireFocusControlEvent(new AreaControlEvent(AreaView.this, type));
			
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
			Point positionPressed = AreaView.this.positionMousePressed;
			
			if (e.getSource() instanceof AntennaView) {
				int type = AreaAntennaMoveEvent.ANTENNA_MOVE;
				Antenna antenna = ((AntennaView) e.getSource()).getAntennaModel();
				AreaView.this.fireMoveAntennaEvent(new AreaAntennaMoveEvent(AreaView.this, type, antenna, (int) (e.getX() - positionPressed.getX()), (int) (e.getY() - (positionPressed.getY()))));
			}
			else {	// e.getSource() instanceof MobileLayer
				int type = AreaMobileMoveEvent.MOBILE_MOVE;
				AreaView.this.fireMoveMobileEvent(new AreaMobileMoveEvent(AreaView.this, type, (int) (e.getX() - positionPressed.getX()), (int) (e.getY() - positionPressed.getY())));
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
			if (e.getSource() instanceof AntennaView) {
				int type = AreaAntennaSelectEvent.ANTENNA_SELECT;
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

}
