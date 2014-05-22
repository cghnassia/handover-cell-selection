package models.mobile;

import models.application.ApplicationModel;
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
	
	public int getX() {
		return Math.round(this.x);
	}
	
	public void setX(int x) {
		this.x = x;
	}
	
	public int getY() {
		return Math.round(this.y);
	}
	
	public void setY(int y) {
		this.y = y;
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
	
	/*public void updatePosition() {
		if (this.getMotion().getMotionHorizontal() == Motion.MOVE_LEFT) {
			this.x -= (float) this.speed / MainConfig.FPS_RATE;
			if(this.x < 0) {
				this.x = 0;
			}
		}
		else if (this.getMotion().getMotionHorizontal() == Motion.MOVE_RIGHT) {
			this.x += (float) this.speed/ MainConfig.FPS_RATE;
			if(this.x > (MainConfig.WINDOW_WIDTH - MainConfig.MENU_WIDTH) * MainConfig.AREA_SCALE) {
				this.x = (MainConfig.WINDOW_WIDTH - MainConfig.MENU_WIDTH) * MainConfig.AREA_SCALE;
			}
		}
		
		if (this.getMotion().getMotionVertical() == Motion.MOVE_UP) {
			this.y -= (float) this.speed / MainConfig.FPS_RATE;
			if(this.y < 0) {
				this.y = 0;
			}
		}
		else if (this.getMotion().getMotionVertical() == Motion.MOVE_DOWN) {
			this.y += (float) this.speed / MainConfig.FPS_RATE;;
			if(this.y > (MainConfig.WINDOW_HEIGHT - MainConfig.INFO_HEIGHT) * MainConfig.AREA_SCALE) {
				this.y = (MainConfig.WINDOW_HEIGHT - MainConfig.INFO_HEIGHT) * MainConfig.AREA_SCALE;
			}
		}
		
		//System.out.println("x" + this.x  + ", y :" + this.y);	
	}*/
}
