package models.mobile;

import java.util.HashSet;
import java.util.Set;
import java.util.Timer;

import com.sun.org.apache.bcel.internal.classfile.PMGClass;

import controllers.AreaController;
import models.application.ApplicationModel;
import models.network.Cell;
import models.network.CellGSM;
import models.network.CellManager;
import models.utilities.Formulas;

public class ModuleGSM extends Module {
	
	protected Mobile mobile;
	protected MeasurementSet measurements;
	protected Set<CellGSM> neighbors;
		
	public ModuleGSM(Mobile mobile) {
		super(mobile);
		this.measurements = new MeasurementSet();
	}
	
	/*public void updateModule() {
		Mobile m = this.getMobile();
		
		if (this.getService() == null) {
			this.doSelection();
		}
		else if (this.isCircuit() || this.isPacket()) {
			if(this.isCircuit()) {
				this.doHandover();
			}
			if (this.isPacket()) {
				this.doDataReselection();
			}
		}
		else {
			this.doIdleReselection();
		}
		
		//we remove selective cell if the strength is not good enough
		if (this.getService() != null && this.getService().getStrength(m.getX(), m.getY()) < this.getService().getRxAccessMin()) {
			this.setService(null);
			this.measurements.removeAllCellMeasurements();
		}
	}*/
	
	public Set<CellGSM> getNeighbors() {
		return this.neighbors;
	}
	
	public void setNeighbors(Set<CellGSM> neighbors) {
		this.neighbors = neighbors;
		this.resetMeasurements();
	}
	
	@Override
	public void doMeasurements() {
		
		Set<CellGSM> targets;
		if(this.getMobile().getService() == null) {
			targets = CellManager.Instance().getCellsGSM();
		}
		else {
			targets = this.neighbors;
		}
		
		for (CellGSM cellGSM: targets) {
			
			int strength = cellGSM.getStrength(this.getMobile().getX(), this.getMobile().getY());
			
			if(strength > CellGSM.COVERAGE_STRENGTH) {
				Measure measure = new Measure(strength, System.currentTimeMillis());
				
				//We only compute noise if we are not only in idle mode
				if(this.getMobile().isCalling() || this.getMobile().isData()) {
					measure.setNoise(this.getSINR(cellGSM));
				}
				
				this.measurements.addMeasure(cellGSM, measure);
			}
			else {
				this.measurements.removeCellMeasurement(cellGSM);
			}
		}
		
		if(this.getMobile().getService() != null && this.getMobile().getService().getType() == Cell.CELLTYPE_GSM) {
			
			int strength = this.getMobile().getService().getStrength(this.getMobile().getX(), this.getMobile().getY());
			Measure measure = new Measure(strength, System.currentTimeMillis());
			
			//We only compute noise if we are not in idle mode
			if(this.getMobile().isCalling() || this.getMobile().isData()) {
				measure.setNoise(this.getSINR((CellGSM) this.getMobile().getService()));
			}
			
			this.measurements.addMeasure(this.getMobile().getService(), measure);
		}	
	}
	
	@Override
	public void resetMeasurements() {
		
		Set<Cell> removedCells = new HashSet<>();
		for (CellMeasurement cellMeasurement: this.measurements.getCellMeasurements()) {
			
			Cell cellGSM = cellMeasurement.getCell();
			if(cellGSM == this.getMobile().getService()) {
				continue;
			}
			
			if(! this.getNeighbors().contains(cellGSM)) {
				removedCells.add(cellGSM);
			}
		}
		
		for(Cell rCell: removedCells) {
			this.measurements.removeCellMeasurement(rCell);
		}
		//this.measurements.removeAllCellMeasurements();
	}
	
	@Override
	public CellGSM doSelection () {
		
		//we try to select
		CellGSM cCell = null;
		CellMeasurement cMeasurement = null;
		for (CellMeasurement pMeasurement: this.measurements.getCellMeasurements()) {
			
			if(pMeasurement.getSize() >= this.getMobile().getMeasureCount()) {
				CellGSM pCell = (CellGSM) pMeasurement.getCell();
				if (pCell.getC1Criterion(pMeasurement.getStrengthAverage(this.getMobile().getMeasureCount())) > 0) {
					if(cCell == null) {
						cMeasurement = pMeasurement;
						cCell = pCell;
					}
					else if (pCell.getC1Criterion(pMeasurement.getStrengthAverage(this.getMobile().getMeasureCount())) > cMeasurement.getStrengthAverage(this.getMobile().getMeasureCount())) {
						cMeasurement = pMeasurement;
						cCell = pCell;
					}
				}
				
			}
		}
		
		return cCell;
	}
	
	//Reminder : only called if service cell is GSM
	@Override
	public CellGSM doIdleReselection() {
		
		CellGSM cCell = (CellGSM) this.getMobile().getService();
		CellMeasurement cMeasurement = this.measurements.getCellMeasurement(cCell);

		if(cMeasurement != null && cMeasurement.getSize() >= this.getMobile().getMeasureCount()) {
			
			int strength = cMeasurement.getStrengthAverage(this.getMobile().getMeasureCount());
			if(cCell.getC1Criterion(strength) < 0) {
				cCell = null;
				cMeasurement = null;
			}
			
			for (CellMeasurement pMeasurement: this.measurements.getCellMeasurements()) {
				//System.out.println(pMeasurement.getCell().getId() + " : count = " + pMeasurement.getSize());
				if(pMeasurement.getSize() >= this.getMobile().getMeasureCount()) {
					
					if(pMeasurement == cMeasurement) {
						continue;
					}
				
					CellGSM pCell = (CellGSM) pMeasurement.getCell();
					if (pCell.getC1Criterion(pMeasurement.getStrengthAverage(this.getMobile().getMeasureCount())) > 0) {
						
						if (cCell == null) {
							cCell = pCell;
							cMeasurement = cMeasurement;
						}
						else if (pCell.getC2Criterion(pMeasurement.getStrengthAverage(this.getMobile().getMeasureCount()), pCell.getAntenna().getLocationArea() == this.getMobile().getService().getAntenna().getLocationArea()) > cCell.getC2Criterion(cMeasurement.getStrengthAverage(this.getMobile().getMeasureCount()), cCell.getAntenna().getLocationArea() == this.getMobile().getService().getAntenna().getLocationArea())) {
							cCell = pCell;
							cMeasurement = pMeasurement;
						}
					}
				}
			}
		}
		else {
			cCell = null;
			cMeasurement = null;
		}
		
		return cCell;
	}
	
	@Override
	public CellGSM doHandover() {
		
		//calculate the best potential cell
		CellGSM cCell = null;
		CellMeasurement cMeasurement = null;
		int cRxQual = 7;
		if (this.getMobile().getService().getType() == Cell.CELLTYPE_GSM) { 
			cCell = (CellGSM) this.getMobile().getService();
			cMeasurement = this.measurements.getCellMeasurement(cCell);
			
			//If the selected cell is not good enough enough, we cannot stay with it
			if (cMeasurement != null && cMeasurement.getSize() >= this.getMobile().getMeasureCount() ) {
				
				int strength = cMeasurement.getStrengthAverage(this.getMobile().getMeasureCount());
				if (cCell.getC1Criterion(strength) < 0) {
					cCell = null;
					cMeasurement= null;
				}
				else {
					cRxQual = Formulas.noiseToRxQUAL(cMeasurement.getNoiseAverage(this.getMobile().getMeasureCount()));
				}
			}
		}
	
			
		for (CellMeasurement pMeasurement: this.measurements.getCellMeasurements()) {
						
			if(pMeasurement == cMeasurement) {
				continue;
			}
				
			if (pMeasurement.getSize() < this.getMobile().getMeasureCount()) {
				continue;
			}
				
			CellGSM pCell = (CellGSM) pMeasurement.getCell();
				
			if (pCell.getC1Criterion(pMeasurement.getStrengthAverage(this.getMobile().getMeasureCount())) >= 0) {
				
				//if the new cell is far from more than 35km, we don't use it
				if(pCell.getDistance(this.getMobile().getX(), this.getMobile().getY()) > 35000) {
					continue;
				}
				
				int pRxQual = Formulas.noiseToRxQUAL(pMeasurement.getNoiseAverage(this.getMobile().getMeasureCount()));
				
				
				if(cCell == null) { //If we don't have a GSM, nothing to compare at this point
					cCell = pCell;
					cMeasurement = pMeasurement;
					cRxQual = pRxQual;
				}	
				else if(pRxQual < cRxQual) { //If we found a better quality, we do handover
					cCell = pCell;
					cMeasurement = pMeasurement;
					cRxQual = pRxQual;
				}
				//If same quality but better strength (based on C2 criterion), we do handover
				else if (pRxQual == cRxQual) {
					if (pCell.getC2Criterion(pMeasurement.getStrengthAverage(this.getMobile().getMeasureCount()), pCell.getAntenna().getLocationArea() == this.getMobile().getService().getAntenna().getLocationArea()) > cCell.getC2Criterion(cMeasurement.getStrengthAverage(this.getMobile().getMeasureCount()), cCell.getAntenna().getLocationArea() == this.getMobile().getService().getAntenna().getLocationArea())) {
						cCell = pCell;
						cMeasurement = pMeasurement;
						cRxQual = pRxQual;
					}
				
				}
			}
		}
		
		return cCell;
	}
	
	@Override
	public CellGSM doDataReselection() {
		return this.doHandover();
	}
	
	private int getSINR(CellGSM measuredCell) {
		
		double numerator = Formulas.toLinear(measuredCell.getStrength(this.getMobile().getX(), this.getMobile().getY()));
		double denominator = Formulas.toLinear(-121); // Thermal noise for GSM
		
		for(CellGSM cellGSM: CellManager.Instance().getCellsGSM()) {
			
			if(measuredCell == cellGSM) {
				continue;
			}
			
			int strength = cellGSM.getStrength(this.getMobile().getX(), this.getMobile().getY());
			int delta = Math.abs(measuredCell.getOffset() - cellGSM.getOffset());
			switch(delta) {
				case 0:
					denominator += Formulas.toLinear(strength);
					break;
				case 1:
					denominator += Formulas.toLinear(strength - 18);
					break;
				case 2:
					denominator += Formulas.toLinear(strength - 50);
					break;
				case 3:
					denominator += Formulas.toLinear(strength - 54);
					break;
				default:
					break;
			}
		}
		
		return (int) Math.round(Formulas.toDB(numerator / denominator));
	}

}
