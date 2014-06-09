package models.network;

import models.area.AreaModel;


public class Antenna implements Comparable<Antenna> {
	
	private int id;
	private int x;
	private int y;
	private CellGSM cellGSM;
	private CellUMTS cellUMTS;
	private LocationArea locationArea;
	
	public Antenna(int id, int x, int y, LocationArea locationArea) {
		this.setId(id);
		this.setX(x);
		this.setY(y);
		this.setLocationArea(locationArea);
	}
	
	public int getId() {
		return this.id;
	}
	public void setId(int id) {
		this.id = id;
	}

	public CellGSM getCellGSM() {
		return this.cellGSM;
	}

	public void setCellGSM(CellGSM cellGSM) {
		
		if (this.cellGSM != null) {
			this.cellGSM.setAntenna(null);
		}
		
		if(cellGSM != null) {
			cellGSM.setAntenna(this);
		}
		
		this.cellGSM = cellGSM;
	}

	public CellUMTS getCellUMTS() {
		return this.cellUMTS;
	}

	public void setCellUMTS(CellUMTS cellUMTS) {
		
		if (this.cellUMTS != null) {
			this.cellUMTS.setAntenna(null);
		}
		
		if(cellUMTS != null) {
			cellUMTS.setAntenna(this);
		}
		
		this.cellUMTS = cellUMTS;
	}

	public LocationArea getLocationArea() {
		return this.locationArea;
	}

	public void setLocationArea(LocationArea locationArea) {
		this.locationArea = locationArea;
	}

	public int getX() {
		return this.x;
	}

	public void setX(int x) {
		AreaModel areaModel = AreaModel.Instance();
		
		this.x = x;
		
		if(this.x < 0) {
			this.x = 0;
		}
		else if (this.x > areaModel.getAreaWidth() * AreaModel.AREA_MAX_SCALE) {
			this.x = areaModel.getAreaWidth() * AreaModel.AREA_MAX_SCALE;
		}
	}

	public int getY() {
		return this.y;
	}

	public void setY(int y) {
		AreaModel areaModel = AreaModel.Instance();
	
		this.y = y;
		
		/*if(this.y < 0) {
			this.y = 0;
		}
		else if (this.y > areaModel.getAreaHeight() * AreaModel.AREA_MAX_SCALE) {
			this.y = areaModel.getAreaHeight() * AreaModel.AREA_MAX_SCALE;
		}*/
	}

	@Override
	public int compareTo(Antenna o) {
		return Integer.valueOf(this.getId()).compareTo(o.getId());
	}
	
	
}
