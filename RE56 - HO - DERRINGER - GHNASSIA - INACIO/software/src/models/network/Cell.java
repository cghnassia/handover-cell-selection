package models.network;

import java.util.HashSet;
import java.util.Set;

import javax.swing.event.EventListenerList;

import config.MainConfig;
import controllers.AreaController;
import events.AreaDataEvent;
import events.AreaListener;
import events.CellEvent;
import events.CellListener;
import models.area.AreaModel;
import models.mobile.Mobile;

public abstract class Cell implements Comparable<Cell> {
	
	private int id;
	private Antenna antenna;
	private int power;		//power in dBm
	private int frequency;	//frequency in MHz
	private int type;
	private boolean isEnabled;
	private boolean isSelected;
	private boolean isActive;
	private Set<Cell> neighbors;
	
	public static final int CELLTYPE_GSM = 0;
	public static final int CELLTYPE_UMTS = 1;
	
	protected EventListenerList listenerList;
	
	
	public Cell(int id) {
		
		this.setId(id);
		this.setEnabled(true);
		this.isSelected = false;
		this.isActive = false;
		this.neighbors = new HashSet<>();
		this.listenerList = new EventListenerList();
	}
	
	public int getPower() {
		return this.power;
	}
	
	public void setPower(int power) {
		this.power = power;
	}
	
	public int getFrequency() {
		return this.frequency;
	}
	
	public void setFrequency(int frequency) {
		this.frequency = frequency;
	}
	
	public abstract int getRadius();
	
	public int getType() {
		return this.type;
	}
	
	public void setType(int type) {
		this.type = type;
	}
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Antenna getAntenna() {
		return this.antenna;
	}

	public void setAntenna(Antenna antenna) {
		this.antenna = antenna;
	}

	public boolean isSelected() {
		return this.isSelected;
	}

	public void setSelected(boolean isSelected) {
		
		this.isSelected = isSelected;

		if(this.isSelected) {
			fireSelectEvent(new CellEvent(this, CellEvent.CELL_SELECTED));
		}
		else {
			fireUnselectEvent(new CellEvent(this, CellEvent.CELL_UNSELECTED));
		}
	}
	
	public boolean isActive() {
		return this.isActive;
	}

	public void setActive(boolean isActive) {
		
		this.isActive = isActive;
		
		if(this.isActive) {
			fireActivateEvent(new CellEvent(this, CellEvent.CELL_ACTIVED));
		}
		else {
			fireUnactivateEvent(new CellEvent(this, CellEvent.CELL_UNACTIVED));
		}
	}
	
	public void addCellListener(CellListener cellListener) {
		this.listenerList.add(CellListener.class, cellListener);
	}
	
	public void removeCellListener(CellListener cellListener) {
		this.listenerList.remove(CellListener.class, cellListener);
	}
	
	protected void fireSelectEvent(CellEvent cellEvent) {
		Object[] listeners = this.listenerList.getListenerList();
	     
	     int numListeners = listeners.length;
	     for (int i = 0; i< numListeners; i += 2) 
	     {
	          if (listeners[i] == CellListener.class) 
	          {
	               // pass the event to the listeners event dispatch method
	                ((CellListener)listeners[i+1]).cellSelected(cellEvent);
	          }            
	     }
	}
	
	protected void fireUnselectEvent(CellEvent cellEvent) {
		Object[] listeners = this.listenerList.getListenerList();
	     
	     int numListeners = listeners.length;
	     for (int i = 0; i< numListeners; i += 2) 
	     {
	          if (listeners[i] == CellListener.class) 
	          {
	               // pass the event to the listeners event dispatch method
	                ((CellListener)listeners[i+1]).cellUnselected(cellEvent);
	          }            
	     }
	}
	
	protected void fireActivateEvent(CellEvent cellEvent) {
		Object[] listeners = this.listenerList.getListenerList();
	     
	     int numListeners = listeners.length;
	     for (int i = 0; i< numListeners; i += 2) 
	     {
	          if (listeners[i] == CellListener.class) 
	          {
	               // pass the event to the listeners event dispatch method
	                ((CellListener)listeners[i+1]).cellActived(cellEvent);
	          }            
	     }
	}
	
	protected void fireUnactivateEvent(CellEvent cellEvent) {
		Object[] listeners = this.listenerList.getListenerList();
	     
	     int numListeners = listeners.length;
	     for (int i = 0; i< numListeners; i += 2) 
	     {
	          if (listeners[i] == CellListener.class) 
	          {
	               // pass the event to the listeners event dispatch method
	                ((CellListener)listeners[i+1]).cellUnactived(cellEvent);
	          }            
	     }
	}

	public Set<Cell> getNeighbors() {
		return this.neighbors;
	}

	public void setNeighbors(Set<Cell> neighbors) {
		this.neighbors = neighbors;
	}
	
	public int getStrength(float mobileX, float mobileY) {
		double distance = this.getDistance(mobileX, mobileY);
		double strength = this.getPower() -  20 * Math.log10(this.getFrequency()) - 20 * MainConfig.COEFF_REFRACTION * Math.log10(distance) + 27.55;
		
		return (int) Math.round(strength);
	}
	
	public double getDistance(float mobileX, float mobileY) {
		return Math.sqrt(Math.pow(Math.abs(this.getAntenna().getX() - mobileX), 2) + Math.pow(Math.abs(this.getAntenna().getY() - mobileY), 2));

	}
	
	@Override
	public int compareTo(Cell o) {
		return new Integer(this.getId()).compareTo(new Integer(o.getId()));
	}
	
	@Override
	public String toString() {
		
		return "Cell<" + this.getId() + ">";
	}

	public boolean isEnabled() {
		return this.isEnabled;
	}

	public void setEnabled(boolean isEnabled) {
		this.isEnabled = isEnabled;
	}
}
