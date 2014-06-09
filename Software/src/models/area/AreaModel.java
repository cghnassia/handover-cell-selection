package models.area;

import java.awt.Menu;

import models.application.ApplicationModel;
import models.menu.MenuModel;

public class AreaModel {
	
	private int areaX;
	private int areaY;
	private int areaWidth;
	private int areaHeight;
	
	private int areaScale;
	
	private static AreaModel areaModel;
	
	public static final int AREA_DEFAULT_SCALE	= 15;
	public static final int AREA_MIN_SCALE	  	= 5;
	public static final int AREA_MAX_SCALE    	= 50;
	
	private AreaModel() {
		this.setAreaWidth(ApplicationModel.Instance().getApplicationWidth() - MenuModel.MENU_WIDTH);
		this.setAreaHeight(ApplicationModel.Instance().getApplicationHeight());
		this.setAreaScale(AreaModel.AREA_DEFAULT_SCALE);
		this.setAreaX(0);
		this.setAreaY(0);
	}
	
	public static AreaModel Instance() {
		
		if (AreaModel.areaModel == null) {
			AreaModel.areaModel = new AreaModel();
		}
		
		return AreaModel.areaModel;
	}

	public int getAreaWidth() {
		return this.areaWidth;
	}

	public void setAreaWidth(int areaWidth) {
		this.areaWidth = areaWidth;
	}

	public int getAreaHeight() {
		//WHAT THE FUCK I DON'T UNDERSTAND
		return this.areaHeight - 20;
	}

	public void setAreaHeight(int areaHeight) {
		this.areaHeight = areaHeight;
	}

	public int getAreaScale() {
		return areaScale;
	}

	public void setAreaScale(int areaScale) {
		this.areaScale = areaScale;
	}

	public int getAreaX() {
		return areaX;
	}

	public void setAreaX(int areaX) {
		this.areaX = areaX;
		
		if(this.areaX < 0) {
			this.areaX = 0;
		}
		
		if ((this.areaX + this.getAreaWidth()) * this.getAreaScale() > this.getAreaWidth() * AreaModel.AREA_MAX_SCALE) {
			this.areaX = (this.getAreaWidth() * AreaModel.AREA_MAX_SCALE) / this.getAreaScale() - this.getAreaWidth();
		}
	}

	public int getAreaY() {
		return areaY;
	}

	public void setAreaY(int areaY) {
		this.areaY = areaY;
		
		if(this. areaY < 0) {
			this.areaY = 0;
		}
		else if ((this.areaY + this.getAreaHeight()) * this.getAreaScale() > this.getAreaHeight() * AreaModel.AREA_MAX_SCALE) {
			this.areaY = (this.getAreaHeight() * AreaModel.AREA_MAX_SCALE) / this.getAreaScale() - this.getAreaHeight();
		}
	}
	
	public void checkBounds() {
		this.setAreaX(this.getAreaX());
		this.setAreaY(this.getAreaY());
	}

}
