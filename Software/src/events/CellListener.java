package events;

import java.util.EventListener;

public interface CellListener extends EventListener {
	 
	void cellSelected(CellEvent e);
	void cellUnselected(CellEvent e);
	
	void cellActived(CellEvent e);
	void cellUnactived(CellEvent e);
	
	void cellAddedInActiveSet(CellEvent e);
	void cellRemovedFromActiveSet(CellEvent e);
}
