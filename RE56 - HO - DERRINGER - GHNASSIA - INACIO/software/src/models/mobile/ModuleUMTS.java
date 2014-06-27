package models.mobile;

import java.util.HashSet;
import java.util.Set;

import views.info.InfoConsoleView;

import com.sun.xml.internal.fastinfoset.algorithm.BuiltInEncodingAlgorithm.WordListener;

import controllers.InfoController;
import models.network.Cell;
import models.network.CellGSM;
import models.network.CellManager;
import models.network.CellUMTS;
import models.utilities.Formulas;

public class ModuleUMTS extends Module{
	
	protected ActiveSet activeSet;
	protected Set<CellUMTS> neighbors;
	protected MeasurementSet measurements;
	
	public ModuleUMTS(Mobile mobile) {
		super(mobile);
		this.measurements = new MeasurementSet();
		this.activeSet = new ActiveSet(3);
	}
	
	public Set<CellUMTS> getNeighbors() {
		return this.neighbors;
	}
	
	public void setNeighbors(Set<CellUMTS> neighbors) {
		this.neighbors = neighbors;
		this.resetMeasurements();
		this.resetActiveSet();
	}
	
	public ActiveSet getActiveSet() {
		return this.activeSet;
	}

	@Override
	public void doMeasurements() {
		Set<CellUMTS> targets;
		if(this.getMobile().getService() == null) {
			targets = CellManager.Instance().getCellsUMTS();
		}
		else {
			targets = this.neighbors;
		}
		for (CellUMTS cellUMTS: targets) {
			
			if(! cellUMTS.isEnabled()) {
				this.measurements.removeCellMeasurement(cellUMTS);
				continue;
			}
			
			int strength = cellUMTS.getStrength(this.getMobile().getX(), this.getMobile().getY());
			
			if(strength > CellUMTS.COVERAGE_STRENGTH) {
				Measure measure = new Measure(strength, this.getEcIo(cellUMTS), System.currentTimeMillis());
				this.measurements.addMeasure(cellUMTS, measure);
			}
			else {
				this.measurements.removeCellMeasurement(cellUMTS);
			}
		}
		
		if(this.getMobile().getService() != null && this.getMobile().getService().getType() == Cell.CELLTYPE_UMTS) {
			int strength = this.getMobile().getService().getStrength(this.getMobile().getX(), this.getMobile().getY());
			
			Measure measure = new Measure(strength, this.getEcIo((CellUMTS) this.getMobile().getService()), System.currentTimeMillis());
			this.measurements.addMeasure(this.getMobile().getService(), measure);
		}	
		
	}

	@Override
	public void resetMeasurements() {
		
		Set<Cell> removedCells = new HashSet<>();
		for (CellMeasurement cellMeasurement: this.measurements.getCellMeasurements()) {
			
			Cell cellUMTS = cellMeasurement.getCell();
			if(cellUMTS == this.getMobile().getService()) {
				continue;
			}
			
			if(! this.getNeighbors().contains(cellUMTS)) {
				removedCells.add(cellUMTS);
			}
		}
		
		
		for(Cell rCell: removedCells) {
			this.measurements.removeCellMeasurement(rCell);
		}
	}
	
	public void clearMeasurements() {
		this.measurements.removeAllCellMeasurements();
	}
	
	public void resetActiveSet() {
		
		Set<CellUMTS> removedCells = new HashSet<>();
		for (CellUMTS active: this.getActiveSet()) {
			
			if(active != this.getMobile().getService()) {
				continue;
			}
			
			if(! this.getNeighbors().contains(active)) {
				removedCells.add(active);
			}
		}
		
		this.getActiveSet().removeAll(removedCells);
	}

	@Override
	public Cell doSelection() {

		//we try to select
		CellUMTS cCell = null;
		CellMeasurement cMeasurement = null;
		
		for (CellMeasurement pMeasurement: this.measurements.getCellMeasurements()) {
					
			if(pMeasurement.getSize() >= this.getMobile().getMeasureCount()) {
				CellUMTS pCell = (CellUMTS) pMeasurement.getCell();
				
				if (pCell.getSRxLevCriterion(pMeasurement.getStrengthAverage(this.getMobile().getMeasureCount())) >= 0) {
					if (cCell == null) {
						cCell = pCell;
						cMeasurement = pMeasurement;
					}
					else if (pCell.getSQualCriterion(pMeasurement.getNoiseAverage(this.getMobile().getMeasureCount())) > cCell.getSQualCriterion(cMeasurement.getNoiseAverage(this.getMobile().getMeasureCount()))) {
						cCell = pCell;
						cMeasurement = pMeasurement;
					}
				}
			}
		}
		
		if (cCell != null) {
			if(this.getMobile().getService() == null) {  
				InfoController.Instance().publishConsole("Selection", "UMTS Cell at Antenna " + cCell.getAntenna().getId() + " has been selected (strength average is " + cMeasurement.getStrengthAverage(this.getMobile().getMeasureCount())  + " dBm, Ec/Io is " + cMeasurement.getNoiseAverage(this.getMobile().getMeasureCount()) + ")");
			}
			else {
				String message = "Moved from";
				message += " GSM Cell at Antenna " + this.getMobile().getService().getAntenna().getId();
				message += " to UMTS Cell at Antenna " + cCell.getAntenna().getId() + " (strength average is " + cMeasurement.getStrengthAverage(this.getMobile().getMeasureCount()) + " dBm, Ec/Io is " + cMeasurement.getNoiseAverage(this.getMobile().getMeasureCount()) + ")";
				InfoController.Instance().publishConsole("Idle reselection (inter-system)", message);
			}
		}
		
		return cCell;
	}

	@Override
	public Cell doIdleReselection() {
		

		CellUMTS cCell = (CellUMTS) this.getMobile().getService();
		CellMeasurement cMeasurement = this.measurements.getCellMeasurement(this.getMobile().getService());
		
		if(cMeasurement != null && cMeasurement.getSize() >= this.getMobile().getMeasureCount()) {
			
			int cSQual = 0;
			int strength = cMeasurement.getStrengthAverage(this.getMobile().getMeasureCount());
			if(cCell.getSRxLevCriterion(strength) < 0) {
				cCell = null;
				cMeasurement = null;
			}
			else {
				cSQual = cCell.getSQualCriterion(cMeasurement.getNoiseAverage(this.getMobile().getMeasureCount()));
			}
			
			for (CellMeasurement pMeasurement: this.measurements.getCellMeasurements()) {
				

				if(pMeasurement.getSize() >= this.getMobile().getMeasureCount()) {

					if(pMeasurement == cMeasurement) {
						continue;
					}
				
					CellUMTS pCell = (CellUMTS) pMeasurement.getCell();

					if (pCell.getSRxLevCriterion(pMeasurement.getStrengthAverage(this.getMobile().getMeasureCount())) >= 0) {
						
						int pSQual = pCell.getSQualCriterion(pMeasurement.getNoiseAverage(this.getMobile().getMeasureCount()));
						
						if(cCell == null) {
							cCell = pCell;
							cMeasurement = pMeasurement;
							cSQual = pSQual;
						}
						else if (pSQual > cSQual) {
							cCell = pCell;
							cMeasurement = pMeasurement;
							cSQual = pSQual;
						}
					}
				}
			}
		}
		
		if(cCell != null && cCell != this.getMobile().getService()) {
			String message = "Moved from";
			message += " UMTS Cell at Antenna " + this.getMobile().getService().getAntenna().getId();
			message += " to UMTS Cell at Antenna " + cCell.getAntenna().getId() + " (strength average is " + cMeasurement.getStrengthAverage(this.getMobile().getMeasureCount()) + " dBm, Ec/Io is " + cMeasurement.getNoiseAverage(this.getMobile().getMeasureCount()) + ")";;
			InfoController.Instance().publishConsole("Idle reselection (intra-system)", message);
		}
		
		
		return cCell;
	}

	@Override
	public Cell doHandover() {
		
		HashSet<CellUMTS> removedCells;
		
		//If the service is not in the active set, we should insert in
		if(this.getMobile().getService().getType() == Cell.CELLTYPE_UMTS && ! this.getActiveSet().contains(this.getMobile().getService())) {
			CellUMTS service = (CellUMTS) this.getMobile().getService();
			this.getActiveSet().add(service);
			//InfoController.Instance().publishConsole("Active set", "UMTS Cell at Antenna " + service.getAntenna().getId() + " has been added to active set (service cell)");
		}
		
		//We remove cell which are not the minimum number of measures from the active set
		removedCells = new HashSet<>();
		for(CellUMTS active: this.getActiveSet()) {
			CellMeasurement cMeasurement = this.measurements.getCellMeasurement(active);
			
			if(! active.isEnabled()) {
				removedCells.add(active);
			}
			else if (cMeasurement == null || cMeasurement.getSize() < this.getMobile().getMeasureCount()) {
				removedCells.add(active);
				InfoController.Instance().publishConsole("Active set", "UMTS Cell at Antenna " + active.getAntenna().getId() + " has been removed from active set (not enough measurements)");
			}
		}
		this.getActiveSet().removeAll(removedCells);
		
		//Then we do link addition
		for(CellUMTS pCell: this.getNeighbors()) {
			
			if(! pCell.isEnabled()) {
				continue;
			}
			
			CellUMTS bestActive = this.getBestActive();
			int bestEcIo = 0;
			
			if(bestActive != null) {
				bestEcIo = this.measurements.getCellMeasurement(bestActive).getNoiseAverage(this.getMobile().getMeasureCount());
			}
			
			if(this.getActiveSet().size() == this.getActiveSet().getSizeMax()) {
				break;
			}
			
			if(this.getActiveSet().contains(pCell)) {
				continue;
			}
			
			CellMeasurement pMeasurement = this.measurements.getCellMeasurement(pCell);
			if(pMeasurement == null || pMeasurement.getSize() < this.getMobile().getMeasureCount()) {
				continue;
			}
			
			int strength =  pMeasurement.getStrengthAverage(this.getMobile().getMeasureCount());
			if (pCell.getSRxLevCriterion(strength) < 0) {
				continue;
			}
			
			int pEcIo = pMeasurement.getNoiseAverage(this.getMobile().getMeasureCount());
			if(bestActive == null || pCell.getSQualCriterion(pEcIo) - bestActive.getSQualCriterion(bestEcIo) > -bestActive.getActiveSetRange()) {
				this.getActiveSet().add(pCell);
				InfoController.Instance().publishConsole("Active set", "UMTS Cell at Antenna " + pCell.getAntenna().getId() + " has been added to active set (strength is " + pMeasurement.getStrengthAverage(this.getMobile().getMeasureCount()) + "dBm, Ec/Io is " + pMeasurement.getNoiseAverage(this.getMobile().getMeasureCount())  + " dB)");
			}
		}
		
		//Now we carry on with link removal
		removedCells = new HashSet<>();
		for(CellUMTS pCell: this.getActiveSet()) {
			
			CellUMTS bestActive = this.getBestActive();
			int bestEcIo = this.measurements.getCellMeasurement(bestActive).getNoiseAverage(this.getMobile().getMeasureCount());
			
			CellMeasurement pMeasurement = this.measurements.getCellMeasurement(pCell);
			if(pMeasurement == null || pMeasurement.getSize() < this.getMobile().getMeasureCount()) {
				removedCells.add(pCell);
				InfoController.Instance().publishConsole("Active set", "UMTS Cell at Antenna " + pCell.getAntenna().getId() + " has been removed from active set (not enough measurements)");
			}
			
			int strength =  pMeasurement.getStrengthAverage(this.getMobile().getMeasureCount());
			if (pCell.getSRxLevCriterion(strength) < 0) {
				removedCells.add(pCell);
				InfoController.Instance().publishConsole("Active set", "UMTS Cell at Antenna " + pCell.getAntenna().getId() + " has been removed from active set (strength is " + pMeasurement.getStrengthAverage(this.getMobile().getMeasureCount()) + "dBm, Ec/Io is " + pMeasurement.getNoiseAverage(this.getMobile().getMeasureCount())  + " dB)");
			}
			
			int pEcIo = pMeasurement.getNoiseAverage(this.getMobile().getMeasureCount());
			if(! removedCells.contains(pCell) && bestActive.getSQualCriterion(bestEcIo) - pCell.getSQualCriterion(pEcIo) > bestActive.getActiveSetRange()) {
				removedCells.add(pCell);
				InfoController.Instance().publishConsole("Active set", "UMTS Cell at Antenna " + pCell.getAntenna().getId() + " has been removed from active set (strength is " + pMeasurement.getStrengthAverage(this.getMobile().getMeasureCount()) + "dBm, Ec/Io is " + pMeasurement.getNoiseAverage(this.getMobile().getMeasureCount())  + " dB)");
			}
		}
		this.getActiveSet().removeAll(removedCells);
		
		//Eventually, we finish with  Combined link addition and removal
		if(this.getActiveSet().size() == this.getActiveSet().getSizeMax()) {
			
			boolean isSwitch = true;
			while(isSwitch) {
				
				CellUMTS worstActive = this.getWorstActive();
				CellUMTS bestMonitored = this.getBestMonitored();
				
				//all cell have already been added into active set
				if(bestMonitored == null) {
					isSwitch = false;
					break;
				}
				
				int worstActivetEcIo = this.measurements.getCellMeasurement(worstActive).getNoiseAverage(this.getMobile().getMeasureCount());
				int bestMonitoredEcIo = this.measurements.getCellMeasurement(bestMonitored).getNoiseAverage(this.getMobile().getMeasureCount());
				
				if(bestMonitored.getSQualCriterion(bestMonitoredEcIo) > worstActive.getSQualCriterion(worstActivetEcIo)) {
					this.getActiveSet().remove(worstActive);
					this.getActiveSet().add(bestMonitored);
					InfoController.Instance().publishConsole("Active set", "UMTS Cell at Antenna " + worstActive.getAntenna().getId() + "(Ec/Io is " + worstActivetEcIo + "dB) has been removed  " + " and UMTS Cell at Antenna " + bestMonitored.getAntenna().getId() + "(Ec/Io is " + bestMonitoredEcIo + "dB) has been added"  );

				}
				else {
					isSwitch = false;
				}
			}
			
		}
		
		return this.getBestActive();
	}

	@Override
	public Cell doDataReselection() {
		return this.doHandover();
	}
	
	public int getEcIo(CellUMTS measuredCell) {
		
		double rscp = Formulas.toLinear(measuredCell.getStrength(this.getMobile().getX(), this.getMobile().getY()));
		
		double rssi = 0;
		for(CellUMTS cellUMTS: CellManager.Instance().getCellsUMTS()) {
			
			if(cellUMTS.getFrequency() != measuredCell.getFrequency()) {
				continue;
			}
			
			rssi += Formulas.toLinear(cellUMTS.getStrength(this.getMobile().getX(), this.getMobile().getY()));
		}
		
		return (int) Math.round(Formulas.toDB(256 * (double) rscp/ (double) rssi));
		
	}
	
	public double getDataThroughput(CellUMTS measuredCell) {
		int sinr = (int) (this.getEcIo(measuredCell) - 10 * Math.log10(256));
		return Formulas.noiseToUMTSDataThroughput(sinr);
	}
	
	private CellUMTS getBestActive() {
		
		CellUMTS bestActive = null;
		int bestEcIo = 0;
		
		for(CellUMTS cActive: this.getActiveSet()) {
			
			CellMeasurement cMeasurement = this.measurements.getCellMeasurement(cActive);
			if(cMeasurement.getSize() < this.getMobile().getMeasureCount()) {
				continue;
			}
			
			int cEciO = cMeasurement.getNoiseAverage(this.getMobile().getMeasureCount());
			if(bestActive == null || cEciO > bestEcIo) {
				bestActive = cActive;
				bestEcIo = cEciO;
			}
		}
		
		return bestActive;
	}
	
	private CellUMTS getWorstActive() {
		
		CellUMTS worstActive = null;
		int worstEcIo = 0;
		
		for(CellUMTS cActive: this.getActiveSet()) {
			
			CellMeasurement cMeasurement = this.measurements.getCellMeasurement(cActive);
			if(cMeasurement.getSize() < this.getMobile().getMeasureCount()) {
				continue;
			}
			
			int cEciO = cMeasurement.getNoiseAverage(this.getMobile().getMeasureCount());
			if(worstActive == null || cEciO < worstEcIo) {
				worstActive = cActive;
				worstEcIo = cEciO;
			}
		}
		
		return worstActive;
	}
	
	private CellUMTS getBestMonitored() {
		
		CellUMTS bestMonitored = null;
		int bestEcIo = 0;
		
		for(CellUMTS cMonitored: this.getActiveSet()) {
			
			if(! cMonitored.isEnabled()) {
				continue;
			}
			
			if(this.getActiveSet().contains(cMonitored)) {
				continue;
			}
			
			CellMeasurement cMeasurement = this.measurements.getCellMeasurement(cMonitored);
			if(cMeasurement.getSize() < this.getMobile().getMeasureCount()) {
				continue;
			}
			
			int strength =  cMeasurement.getStrengthAverage(this.getMobile().getMeasureCount());
			if(cMonitored.getSRxLevCriterion(strength) < 0) {
				continue;
			}
			
			int cEciO = cMeasurement.getNoiseAverage(this.getMobile().getMeasureCount());
			if(bestMonitored == null || cEciO > bestEcIo) {
				 bestMonitored = cMonitored;
				 bestEcIo = cEciO;
			}
		}
		
		return bestMonitored;
		
		
	}
	
	private CellUMTS getWorstMonitored() {
		
		CellUMTS worstMonitored = null;
		int worstEcIo = 0;
		
		for(CellUMTS cMonitored: this.getActiveSet()) {
			
			if(! cMonitored.isEnabled()) {
				continue;
			}
			
			if(this.getActiveSet().contains(cMonitored)) {
				continue;
			}
			
			CellMeasurement cMeasurement = this.measurements.getCellMeasurement(cMonitored);
			if(cMeasurement.getSize() < this.getMobile().getMeasureCount()) {
				continue;
			}
			
			int cEciO = cMeasurement.getNoiseAverage(this.getMobile().getMeasureCount());
			if(worstMonitored == null || cEciO < worstEcIo) {
				worstMonitored = cMonitored;
				worstEcIo = cEciO;
			}
		}
		
		return worstMonitored;
	}
	
}
