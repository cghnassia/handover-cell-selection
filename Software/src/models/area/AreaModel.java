package models.area;

public class AreaModel {
	
	private int areaWidth;
	private int areaHeight;
	
	private int areaScale;
	
	private static AreaModel areaModel;
	
	public static final int AREA_SCALE = 15;
	
	private AreaModel() {
		this.setAreaScale(AreaModel.AREA_SCALE);
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
		return this.areaHeight;
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

}
