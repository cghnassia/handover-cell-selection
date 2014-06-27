package models.mobile;

import java.util.HashSet;
import java.util.Set;

import models.network.Cell;

public class MeasurementSet {
	
	private Set<CellMeasurement> cellMeasurements;
	
	public MeasurementSet() {
		this.cellMeasurements = new HashSet<>();
	}
	
	public Set<CellMeasurement> getCellMeasurements() {
		return this.cellMeasurements;
	}
	
	public void addMeasure(Cell cell, Measure measure) {
		CellMeasurement cellMeasurement = null;
		
		for(CellMeasurement pCellMeasurement: this.cellMeasurements) {
			if (pCellMeasurement.getCell() == cell) {
				cellMeasurement = pCellMeasurement;
				break;
			}
		}
		
		if(cellMeasurement == null ) {
			cellMeasurement = new CellMeasurement(cell);
			this.cellMeasurements.add(cellMeasurement);
		}
		
		cellMeasurement.addMeasure(measure);
	}
	
	public CellMeasurement getCellMeasurement(Cell cell) {
		
		for(CellMeasurement cellMeasurement: this.cellMeasurements) {
			if (cellMeasurement.getCell() == cell) {
				return cellMeasurement;
			}
		}
		
		return null;
	}
	
	public void removeCellMeasurement(Cell cell) {
		for(CellMeasurement cellMeasurement: this.cellMeasurements) {
			if (cellMeasurement.getCell() == cell) {
				this.cellMeasurements.remove(cellMeasurement);
				break;
			}
		}
	}
	
	public void removeAllCellMeasurements() {
		this.cellMeasurements.removeAll(this.cellMeasurements);
	}
	
	
	
	
}
