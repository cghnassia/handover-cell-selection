package models.network;

import java.util.HashSet;
import java.util.Set;



public class LocationAreaManager {
	
	private Set<LocationArea> locationAreas;
	
	private static LocationAreaManager locationAreaManager;
	
	private LocationAreaManager() {
		
		this.locationAreas = new HashSet<>();
	}
	
	public static LocationAreaManager Instance() {
		
		if (LocationAreaManager.locationAreaManager == null) {
			LocationAreaManager.locationAreaManager = new LocationAreaManager();
		}
		
		return LocationAreaManager.locationAreaManager;
	}
	
	public void addLocationArea(LocationArea locationArea) {
		this.locationAreas.add(locationArea);
	}
	
	public Set<LocationArea> getLocationAreas() {
		
		return this.locationAreas;
	}
	
	public LocationArea getLocationArea(int id) {
		
		LocationArea res = null;
		
		for(LocationArea locationArea: this.getLocationAreas()) {
			if(locationArea.getId() == id) {
				res = locationArea;
				break;
			}
		}
		
		return res;
 	}
	
	public void removeLocationAra(int id) {
		
		for(LocationArea locationArea: this.getLocationAreas()) {
			if(locationArea.getId() == id) {
				this.locationAreas.remove(locationArea);
				break;
			}
		}
	}
}
