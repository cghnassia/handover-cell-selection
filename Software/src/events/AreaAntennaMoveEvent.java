package events;

import java.awt.AWTEvent;
import java.awt.Event;

import models.network.Antenna;
import views.area.AreaView;
import views.menu.MenuView;

public class AreaAntennaMoveEvent extends AWTEvent {
	
	private Antenna antenna;
	private int x;
	private int y;
	
	public static final int ANTENNA_MOVE = 1;
		
	public AreaAntennaMoveEvent(AreaView areaView, int type, Antenna antenna, int x, int y) {
		super(areaView, type);
		this.setAntenna(antenna);
		this.setX(x);
		this.setY(y);
	}

	public Antenna getAntenna() {
		return this.antenna;
	}

	public void setAntenna(Antenna antenna) {
		this.antenna = antenna;
	}

	public int getX() {
		return this.x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return this.y;
	}

	public void setY(int y) {
		this.y = y;
	}

	
	
}
