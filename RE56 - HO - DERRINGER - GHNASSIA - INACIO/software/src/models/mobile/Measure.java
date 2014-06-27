package models.mobile;

public class Measure {
	
	private int rxLev;
	private int noise;
	private long time;
	
	public Measure(int rxLev, int noise, long time) {
		this.setRxLev(rxLev);
		this.setTime(time);
		this.setNoise(noise);
	}
	
	public Measure(int rxLev, long time) {
		this.setRxLev(rxLev);
		this.setTime(time);
	}

	public int getRxLev() {
		return this.rxLev;
	}

	public void setRxLev(int rxLev) {
		this.rxLev = rxLev;
	}

	public long getTime() {
		return this.time;
	}

	public void setTime(long time) {
		this.time = time;
	}

	public int getNoise() {
		return this.noise;
	}

	public void setNoise(int noise) {
		this.noise = noise;
	}
}
