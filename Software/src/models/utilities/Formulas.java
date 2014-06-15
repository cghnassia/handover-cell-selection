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
	public static double noiseToGPRSDataThroughput(int db, int timeSlots) {
		
		double CS1 = 9.05; //kbits/s
		double CS2 = 13.4;
		double CS3 = 15.6;
		double CS4 = 21.4;
		
		double codingScheme = 0;
		if (db >= 19) {
			codingScheme = CS4;
		}
		else if (db >= 16) {
			codingScheme = CS3;
		}
		else if (db >= 15) {
			codingScheme = CS2;
		}
		else if (db >= 13) {
			codingScheme = CS1;
		}
		
		return codingScheme * timeSlots;
	}
	
	public static double noiseToEdgeDataThroughput(int db, int timeSlots) {
		
		double MSC1 = 8.8;
		double MSC2 = 11.2;
		double MSC3 = 14.8;
		double MSC4 = 17.6;
		double MSC5 = 22.4;
		double MSC6 = 29.6;
		double MSC7 = 44.8;
		double MSC8 = 54.4;
		double MSC9 = 59.2;
		
		double codingScheme = 0;
		if (db > 40) {
			codingScheme = MSC9;
		}
		else if (db > 35) {
			codingScheme = MSC8;
		}
		else if (db > 33) {
			codingScheme = MSC7;
		}
		else if (db > 30) {
			codingScheme = MSC6;
		}
		else if (db > 27) {
			codingScheme = MSC5;
		}
		else if (db > 23) {
			codingScheme = MSC4;
		}
		else if (db > 20) {
			codingScheme = MSC3;
		}
		else if (db > 12) {
			codingScheme = MSC2;
		}
		else if (db > 10) {
			codingScheme = MSC1;
		}
		
		return codingScheme * timeSlots;
	}
	
	
	public static double noiseToUMTSDataThroughput(int db) {
		double res = 0;
		
		if (db >= 3) {
			res = 2000;
		}
		else if (db >= 0) {
			res = 768;
		}
		else if (db >= -10) {
			res = 384;
		}
		else if (db >= -15) {
			res = 128;
		}
		else if (db >= -18) {
			res = 64;
		}
		else if (db >= -20) { //It should only be voice
			res = 12.2;
		}
		
		return res;	
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
	
	public static boolean isUMTSMeasurement(int strength, int qs) {
		boolean res = false;
		
		if(qs == 0 && strength < -98) {
			res = true;
		}
		else if(qs == 1 && strength < -94) {
			res = true;
		}
		else if(qs == 2 && strength < -90) {
			res = true;
		}
		else if(qs == 3 && strength < -86) {
			res = true;
		}
		else if(qs == 4 && strength < -82) {
			res = true;
		}
		else if(qs == 5 && strength < -78) {
			res = true;
		}
		else if(qs == 6 && strength < -74) {
			res = true;
		}
		else if(qs == 7) {
			res = true;
		}
		else if(qs == 8 && strength > -78) {
			res = true;
		}
		else if(qs == 9 && strength > -74) {
			res = true;
		}
		else if(qs == 10 && strength > -70) {
			res = true;
		}
		else if(qs == 11 && strength > -66) {
			res = true;
		}
		else if(qs == 12 && strength > -62) {
			res = true;
		}
		else if(qs == 13 && strength > -58) {
			res = true;
		}
		else if(qs == 14 && strength > -54) {
			res = true;
		}
		
		return res;
	}
	
}
