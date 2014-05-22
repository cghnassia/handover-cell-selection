package events;

import java.util.EventListener;

public interface AreaListener extends EventListener {
	 
	void keyboardMoved(AreaControlEvent e);
	void areaClicked(AreaControlEvent e);
	void antennaSelected(AreaAntennaSelectEvent e);
	void antennaMoved(AreaAntennaMoveEvent e);
	void mobileMoved(AreaMobileMoveEvent e);
}
