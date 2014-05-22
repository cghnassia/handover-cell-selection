package models.network;

public class Network {
	
	private boolean isGSM;
	private boolean isGPRS;
	private boolean isEDGE;
	private boolean isUMTS;

	
	public Network() {
		this.setGSM(true);
		this.setGPRS(true);
		this.setEDGE(true);
		this.setUMTS(true);
	}


	public boolean isGSM() {
		return this.isGSM;
	}


	public void setGSM(boolean isGSM) {
		this.isGSM = isGSM;
	}


	public boolean isUMTS() {
		return this.isUMTS;
	}


	public void setUMTS(boolean isUMTS) {
		this.isUMTS = isUMTS;
	}


	public boolean isGPRS() {
		return this.isGPRS;
	}


	public void setGPRS(boolean isGPRS) {
		this.isGPRS = isGPRS;
	}


	public boolean isEDGE() {
		return this.isEDGE;
	}


	public void setEDGE(boolean isEDGE) {
		this.isEDGE = isEDGE;
	}

}
