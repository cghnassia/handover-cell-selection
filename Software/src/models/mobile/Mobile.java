package models.mobile;

import models.application.ApplicationModel;
import models.area.AreaModel;
import config.MainConfig;

public class Mobile {
	
	public static final int POWER = 10;
	
	private float x;
	private float y;
	private int speed;
	
	private boolean isCalling;
	private boolean isData;
	
	private ModuleGSM moduleGSM;
	private ModuleUMTS moduleUMTS;
	
	private Motion motion;
	
	private static Mobile mobile;
	
	public Mobile() {
		
		this.isCalling = false;
		this.isData = false;
		
		this.moduleGSM = new ModuleGSM();
		this.moduleUMTS = new ModuleUMTS();
		
		this.motion = new Motion();
	}
	
	public static Mobile Instance() {
		if (Mobile.mobile == null) {
			Mobile.mobile = new Mobile();
		}
		
		return Mobile.mobile;
	}
	
	public float getX() {
		return this.x;
	}
	
	public void setX(float x) {
		AreaModel areaModel = AreaModel.Instance();
		
		this.x = x;
		if(this.x < 0) {
			this.x = 0;
		}
		else if (this.x > areaModel.getAreaWidth() * areaModel.getAreaScale()) {
			this.x = areaModel.getAreaWidth() * areaModel.getAreaScale();
		}
	}
	
	public float getY() {
		return this.y;
	}
	
	public void setY(float y) {
		AreaModel areaModel = AreaModel.Instance();
		
		this.y = y;
		if(this.y < 0) {
			this.y = 0;
		}
		else if (this.y > areaModel.getAreaHeight() * areaModel.getAreaScale()) {
			this.y = areaModel.getAreaHeight() * areaModel.getAreaScale();
		}
	}
	
	public int getSpeed() {
		return this.speed;
	}
	
	public void setSpeed(int speed) {
		this.speed = speed;
	}
	
	public ModuleGSM getModuleGSM() {
		return this.moduleGSM;
	}
	
	public ModuleUMTS getModuleUMTS() {
		return this.moduleUMTS;
	}
	
	public Motion getMotion() {
		return this.motion;
	}
	
	public void update() {
		ApplicationModel applicationModel = ApplicationModel.Instance();

		if (this.getMotion().getMotionHorizontal() == Motion.MOVE_LEFT) {
			this.setX(this.getX() - this.speed / applicationModel.getFpsRate());
		}
		else if (this.getMotion().getMotionHorizontal() == Motion.MOVE_RIGHT) {
			this.setX(this.getX() + this.speed / applicationModel.getFpsRate());
		}
		
		if (this.getMotion().getMotionVertical() == Motion.MOVE_UP) {
			this.setY(this.getY() - this.speed / applicationModel.getFpsRate());
		}
		else if (this.getMotion().getMotionVertical() == Motion.MOVE_DOWN) {
			this.setY(this.getY() + this.speed / applicationModel.getFpsRate());
		}
	}
	
	public void checkIsInArea() {
		//To be sure the phone stay in the Area
		this.setX(this.getX());
		this.setY(this.getY());
	}
}
