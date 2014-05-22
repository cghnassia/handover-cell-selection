package models.network;

import java.util.HashSet;
import java.util.Set;



public class AntennaManager {
	
	private Set<Antenna> antennas;
	
	private static AntennaManager antennaManager;
	
	private AntennaManager() {
		
		this.antennas = new HashSet<>();
	}
	
	public static AntennaManager Instance() {
		
		if (AntennaManager.antennaManager == null) {
			AntennaManager.antennaManager = new AntennaManager();
		}
		
		return AntennaManager.antennaManager;
	}
	
	public void addAntenna(Antenna antenna) {
		
		this.antennas.add(antenna);
	}
	
	public Set<Antenna> getAntennas() {
		
		return this.antennas;
	}
	
	public Antenna getAntenna(int id) {
		
		Antenna res = null;
		
		for(Antenna antenna: this.getAntennas()) {
			if(antenna.getId() == id) {
				res = antenna;
				break;
			}
		}
		
		return res;
 	}
	
	public void removeLocationAra(int id) {
		
		for(Antenna antenna: this.getAntennas()) {
			if(antenna.getId() == id) {
				this.antennas.remove(antenna);
				break;
			}
		}
	}
}

