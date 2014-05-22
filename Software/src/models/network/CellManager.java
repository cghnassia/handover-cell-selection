package models.network;

import java.util.HashSet;
import java.util.Set;

public final class CellManager {
	
	private Set<CellGSM> cellsGSM;
	private Set<CellUMTS> cellsUMTS;
	
	private static CellManager cellManager = null;
	
	private CellManager() {
		
		this.cellsGSM = new HashSet<>();
		this.cellsUMTS = new HashSet<>();
	}
	
	public static CellManager Instance() {
		
		if (CellManager.cellManager == null) {
			CellManager.cellManager = new CellManager();
		}
		
		return CellManager.cellManager;
	}
	
	public CellGSM getCellGSM(int id) {
		
		CellGSM res = null;
		
		for(CellGSM cellGSM: this.getCellsGSM()) {
			if(cellGSM.getId() == id) {
				res = cellGSM;
				break;
			}
		}
		
		return res;
	}
	
	public Set<CellGSM> getCellsGSM() {
		
		return this.cellsGSM;
	}
	
	public void addCellGSM(CellGSM cellGSM) {
		this.cellsGSM.add(cellGSM);
	}
	
	public void removeCellGSM(int id) {
		
		CellGSM cellGSM = this.getCellGSM(id);
		
		if(cellGSM != null) {
			this.getCellsGSM().remove(cellGSM);
		}
	}
	
	
	public Set<CellUMTS> getCellsUMTS() {
		
		return this.cellsUMTS;
	}
	
	public CellUMTS getCellUMTS(int id) {
		
		CellUMTS res = null;
		
		for(CellUMTS cellUMTS: this.getCellsUMTS()) {
			if(cellUMTS.getId() == id) {
				res = cellUMTS;
				break;
			}
		}
		
		return res;
	}
	
	public void addCellUMTS(CellUMTS cellUMTS) {
		this.cellsUMTS.add(cellUMTS);
	}
	
	public void removeCellUMTS(int id) {
		
		CellUMTS cellUMTS = this.getCellUMTS(id);
		
		if(cellUMTS != null) {
			this.getCellsUMTS().remove(cellUMTS);
		}
	}

	
	
	
	

}
