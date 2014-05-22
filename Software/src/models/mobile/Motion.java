package models.mobile;

public class Motion {
	
	public static final int MOVE_NONE = 0;
	public static final int MOVE_RIGHT = 1;
	public static final int MOVE_LEFT = 2;
	public static final int MOVE_UP = 3;
	public static final int MOVE_DOWN = 4;
	
	private int moveHorizontal;		//-1 -> left and +1 -> right
	private int moveVertical;		//-1 -> down and +1 -> up			//current speed
	
	public Motion() {
		this.moveHorizontal = 0;
		this.moveVertical = 0;
	}
	
	public void addMotion(int move) {
		switch (move) {
			case Motion.MOVE_LEFT:
				this.moveHorizontal = -1;
				break;
			case Motion.MOVE_RIGHT:
				this.moveHorizontal = 1;
				break;
			case Motion.MOVE_DOWN:
				this.moveVertical = -1;
				break;
			case Motion.MOVE_UP:
				this.moveVertical = 1;
				break;
			default:
				break;
		}
	}
	
	public void removeMotion(int move) {
		if (move == Motion.MOVE_LEFT && this.moveHorizontal == -1) {
			this.moveHorizontal= 0;
		}
		else if (move == Motion.MOVE_RIGHT && this.moveHorizontal == 1) {
			this.moveHorizontal= 0;
		}
		else if (move == Motion.MOVE_DOWN && this.moveVertical == -1) {
			this.moveVertical= 0;
		}
		else if (move == Motion.MOVE_UP && this.moveVertical == 1) {
			this.moveVertical= 0;
		}
	}
	
	public int getMotionHorizontal() {
		if (this.moveHorizontal == -1) {
			return Motion.MOVE_LEFT;
		}
		else if (this.moveHorizontal == 1) {
			return Motion.MOVE_RIGHT;
		}
		
		return Motion.MOVE_NONE;
	}
	
	public int getMotionVertical() {
		if (this.moveVertical == -1) {
			return Motion.MOVE_DOWN;
		}
		else if (this.moveVertical == 1) {
			return Motion.MOVE_UP;
		}
		
		return Motion.MOVE_NONE;
	}
	
	
	
}
