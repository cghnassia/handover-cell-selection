package models.mobile;

import java.util.Set;

import models.network.CellUMTS;

public class ModuleUMTS extends Module{
	
	protected CellUMTS service;
	protected Set<CellUMTS> active_set;
	protected Set<CellUMTS> neighbors;
	protected Set<CellUMTS> cells;
	
	public ModuleUMTS() {
		super();
	}
	
	public Set<CellUMTS> getCells() {
		return this.cells;
	}
	
	public void setCells(Set<CellUMTS> cells) {
		this.cells = cells;
	}
	
}
