package events;

import java.awt.AWTEvent;
import java.awt.Event;

import models.network.Antenna;
import views.area.AreaView;
import views.menu.MenuView;

public class AreaMoveEvent extends AWTEvent {
	
	private int x;
	private int y;
	
	public static final int MOBILE_MOVE	= 0;
	public static final int AREA_MOVE 	= 1;
		
	public AreaMoveEvent(AreaView areaView, int type, int x, int y) {
		super(areaView, type);
		this.setX(x);
		this.setY(y);
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
