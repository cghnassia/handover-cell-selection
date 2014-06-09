package models.mobile;

import java.util.Collection;
import java.util.HashSet;

import models.network.CellUMTS;

public class ActiveSet extends HashSet<CellUMTS> {
	
	private int sizeMax;
	
	public ActiveSet(int sizeMax) {
		super();
		this.setSizeMax(sizeMax);
	}
	
	@Override
	public boolean add(CellUMTS e) {
		
		if(this.size() < this.sizeMax) {
			
			boolean res = super.add(e);
			
			if(res) {
				e.setInActiveSet(true);
			}
			
			return res;
		}
		
		return false;
	}
	
	@Override
	public boolean remove(Object o) {	
		
		boolean res = super.remove(o);
		
		if(res) {
	 		CellUMTS cellUMTS = (CellUMTS) o;
			cellUMTS.setInActiveSet(false);
		}
		
		return res;
	}
	
	//@Override
	/*public boolean removeAll(Collection<?> c) {
		
		for (Object o: c) {
			if(this.contains(o)) {
				CellUMTS cellUMTS = (CellUMTS) o;
				cellUMTS.setInActiveSet(false);
			}
		}
		
		return super.removeAll(c);
	}*/
	
	@Override
	public void clear() {
		for (Object o: this) {
			if(this.contains(o)) {
				CellUMTS cellUMTS = (CellUMTS) o;
				cellUMTS.setInActiveSet(false);
			}
		}
		
		super.clear();
	}
	
	public int getSizeMax() {
		return this.sizeMax;
	}
	
	public void setSizeMax(int sizeMax) {
		this.sizeMax = sizeMax;
	}
	
	
}
