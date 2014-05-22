package events;

import java.awt.AWTEvent;
import java.awt.Event;

import models.network.Antenna;
import views.area.AreaView;
import views.menu.MenuView;

public class AreaAntennaSelectEvent extends AWTEvent {
	
	private Antenna antenna;
	
	public static final int ANTENNA_SELECT = 1;
		
	public AreaAntennaSelectEvent(AreaView areaView, int type, Antenna antenna) {
		super(areaView, type);
		this.setAntenna(antenna);
	}

	public Antenna getAntenna() {
		return this.antenna;
	}

	public void setAntenna(Antenna antenna) {
		this.antenna = antenna;
	}
}
