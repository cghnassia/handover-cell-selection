package models.utilities;

public class Formulas {
	
	public static final int ALGORITHM_BPSK = 1;
	
	//static class
	private Formulas() {}
	
	public static double toLinear(double db) {
		return Math.pow(10, db/10.0);
	}
	
	public static double toDB(double value) {
		return 10 * Math.log10(value);
	}
	
	public static int noiseToRxQUAL(int db) {
		
		int res;
		if (db >= 9) {
			res = 0;
		}
		else if (db >= 8) {
			res = 1;
		}
		else if (db >= 7) {
			res = 2;
		}
		else if (db >= 6) {
			res = 3;
		}
		else if (db >= 5.5) {
			res = 4;
		}
		else if (db >= 5) {
			res = 5;
		}
		else {
			res = 6;
		}
		
		return res;
	}
	
	//Only GPRS right now ? What about EDGE
	public static double noiseToDataThrougput(int db, int timeSlots) {
		
		double CS1 = 9.05; //kbits/s
		double CS2 = 13.4;
		double CS3 = 15.6;
		double CS4 = 21.4;
		
		double codingScheme = 0;
		switch (noiseToRxQUAL(db)) {
			case 0:
				codingScheme = CS4;
				break;
			case 1:
				codingScheme = CS3;
				break;
			case 2:
				codingScheme = CS2;
				break;
			case 3:
				codingScheme = CS1;
				break;
			default:
				codingScheme = 0;
				break;
		}
		
		return codingScheme * timeSlots;
	}
	
	
	//For Later
	/*public static double SIRtoBER(double sir, int algorithm) {
		
		double ber = 0;
		
		if (algorithm == Formulas.ALGORITHM_BPSK) {
			switch ((int) Math.round(sir)) {
				case 0:
					ber = Math.pow(10, -1);
					break;
				case 1:
					ber = Math.pow(10, -1.1);
					break;
				case 2:
					ber = Math.pow(10, -1.4);
					break;
				case 3:
					ber = Math.pow(10, -1.8);
					break;
				case 4:
					ber = Math.pow(10, -2);
					break;
				case 5:
					ber = Math.pow(10, -2.1);
					break;
				case 6:
					ber = Math.pow(10, -2.6);
					break;
				case 7:
					ber = Math.pow(10, -3.1);
					break;
				case 8:
					ber = Math.pow(10, -3.8);
					break;
				case 9:
					ber = Math.pow(10, -4.2);
					break;
				case 10:
					ber = Math.pow(10, -5.7);
					break;
				case 11:
					ber = Math.pow(10, -6.3);
					break;
				default:
					ber = Math.pow(10, -8);
					break;
			}
		}
		
		return ber;
	}
	
	public static int BERtoRXQUALL(double ber) {
		
		int rxQuall;

		if(ber * 100 < 0.2) {
			rxQuall = 0;
		}
		else if(ber * 100 < 0.4) {
			rxQuall = 1;
		}
		else if (ber * 100 < 0.8) {
			rxQuall = 2;
		}
		else if (ber * 100< 1.6) {
			rxQuall = 3;
		}
		else if (ber * 100 < 3.2) {
			rxQuall = 4;
		}
		else if (ber * 100 < 6.4) {
			rxQuall = 5;
		}
		else if (ber * 100 < 12.8) {
			rxQuall = 6;
		}
		else {
			rxQuall = 7;
		}
		
		return rxQuall;
	}*/
	
}
