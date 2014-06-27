package models.network;

import java.awt.Color;

public class LocationArea implements Comparable<LocationArea>{
	
	private int id;
	private Color color;

	public LocationArea(int id, Color color) {
		this.setId(id);
		this.setColor(color);
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Color getColor() {
		return color;
	}

	public void setColor(Color color) {
		this.color = color;
	}
	
	public boolean equals(LocationArea locationArea) {
		return this.getId() == locationArea.getId();
	}
	
	@Override
	public int compareTo(LocationArea o) {
		return new Integer(this.getId()).compareTo(new Integer(o.getId()));
	}
}
