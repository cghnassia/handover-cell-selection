package models.network;

import models.mobile.Mobile;

public abstract class Cell {
	
	private int id;
	private Antenna antenna;
	private int power;		//power in dBm
	private int frequency;	//frequency in MHz
	private int radius;		//radius in meters
	private int type;
	private Mobile mobile;
	
	public static final int CELLTYPE_GSM = 0;
	public static final int CELLTYPE_UMTS = 1;
	
	
	//J'ai supprime le mobile -- Attention
	public Cell(int power, int frequency) {
		
		this.setFrequency(frequency);
		this.setPower(power);
		
		this.calculateRadius();
	}
	
	public int getPower() {
		return this.power;
	}
	
	public void setPower(int power) {
		this.power = power;
	}
	
	public int getFrequency() {
		return this.frequency;
	}
	
	public void setFrequency(int frequency) {
		this.frequency = frequency;
	}
	
	public int getRadius() {
		return this.radius;
	}
	
	public void setRadius(int radius) {
		this.radius = radius;
	}
	
	protected abstract void calculateRadius();
	
	public int getType() {
		return this.type;
	}
	
	public void setType(int type) {
		this.type = type;
	}
	
	protected void setMobile(Mobile mobile) {
		this.mobile = mobile;
	}
	
	public Mobile getMobile() {
		return this.mobile;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Antenna getAntenna() {
		return antenna;
	}

	public void setAntenna(Antenna antenna) {
		this.antenna = antenna;
	}
}
