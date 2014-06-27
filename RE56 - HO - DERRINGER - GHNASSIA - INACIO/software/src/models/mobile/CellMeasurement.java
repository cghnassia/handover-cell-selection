package models.mobile;

import java.util.ArrayList;
import java.util.List;

import models.network.Cell;

public class CellMeasurement {
	
	private Cell cell;
	private List<Measure> measures;
	
	public CellMeasurement(Cell cell) {
		this.cell = cell;
		this.measures = new ArrayList<>();
	}
	
	public Cell getCell() {
		return this.cell;
	}
	
	public void setCell(Cell cell) {
		this.cell = cell;
	}
	
	public int getSize() {
		return this.measures.size();
	}
	
	public List<Measure> getMeasures() {
		return this.measures;
	}
	
	public void addMeasure(Measure measure) {
		this.measures.add(measure);
	}
	
	public void clear() {
		this.measures.clear();
	}
	
	public int getStrengthAverage(int count) {
		int average = 0;
		
		for (int i = this.measures.size() - count; i < this.measures.size(); i++) {
			average += this.measures.get(i).getRxLev();
		}
		
		average /= count;
		
		return average;
	}
	
	public int getNoiseAverage(int count) {
		int average = 0;
		
		for (int i = this.measures.size() - count; i < this.measures.size(); i++) {
			average += this.measures.get(i).getNoise();
		}
		
		average /= count;
		
		return average;
	}
}
