public class Statistics {
	private int transmitted;
	private int received;
	private int rttMin;
	private float rttAvg;
	private int rttMax;
	private float somma;
	
	public Statistics() {
		transmitted = 0;
		received = 0;
		rttMin = 2000;
		rttAvg = 0;
		rttMax = 0;
		somma = 0;
	}
	
	public void transmitted() {
		transmitted++;
	}
	
	public void rtt(int i) {
		received++;
		somma += i;
		rttMax = Math.max(rttMax, i); 
		rttMin = Math.min(rttMin, i);
		rttAvg = somma / received;
	}

	public String toString() {
		return transmitted + " packets transmitted, " + received + " packets received, " 
				+ (100*(transmitted-received))/(transmitted) + "% packet loss\n" 
				+ "round-trip (ms) min/avg/max = " + rttMin + "/" + String.format("%.2f", rttAvg) + "/" + rttMax;
	}
	
	
}
