package events;

import java.awt.AWTEvent;
import java.awt.Event;

import models.network.Cell;
import views.area.AreaView;
import views.menu.MenuView;

public class CellEvent extends AWTEvent {
	
	public static final int CELL_SELECTED = 0;
	public static final int CELL_UNSELECTED = 1;
	
	public static final int CELL_ACTIVED = 2;
	public static final int CELL_UNACTIVED = 3;
	
	public static final int CELL_ACTIVESET_ADDED = 4;
	public static final int CELL_ACTIVESET_REMOVED = 5;

		
	public CellEvent(Cell cellModel, int type) {
		super(cellModel, type);
	}

	
	
}
