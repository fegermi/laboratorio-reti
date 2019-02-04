import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;

public class PingClient {
	
	public static void main(String args[]) {
		
		InetAddress serverAddr = null;
		int serverPort = 0;
		
		// Parsing parametri di input
		if(args.length == 0){
			System.out.println("[PingClient] ERR -arg 0");
			return ;
		}
		
		try {
			serverAddr = InetAddress.getByName(args[0]);
		}catch (UnknownHostException e) {
			System.err.println("[PingClient] ERR -arg 0");
			return;
		}	
		
		if(args.length == 1) {
			System.err.println("[PingClient] ERR -arg 1");
			return;
		}
		try {
			serverPort = Integer.parseInt(args[1]);
		}catch(NumberFormatException e) {
			System.err.println("[PingServer] ERR -arg 1");
			return;
		}
		
		//Statistiche
		Statistics statistics = new Statistics();
 		
		byte[] sendPing = new byte[512];
		int rtt;
		
		try(DatagramSocket clientSocket = new DatagramSocket();) {
			System.out.println("[PingClient] Start");
			// Creo i 10 messaggi di ping
			for(int i=0; i<10; i++) {
				long timeStamp =  System.currentTimeMillis();
				String message = "PING " + i + " " + timeStamp;
				sendPing = message.getBytes();
				DatagramPacket sendPacket = new DatagramPacket(sendPing, sendPing.length, serverAddr, serverPort);
				DatagramPacket receivePacket = new DatagramPacket(sendPing, sendPing.length, serverAddr, serverPort);
			
				// Imposto timeout di risposta
				clientSocket.setSoTimeout(2000);
				
				// Invia la richiesta
				clientSocket.send(sendPacket);
				statistics.transmitted();
			
				try {
					clientSocket.receive(receivePacket);
					
					rtt = (int) (System.currentTimeMillis() - timeStamp);
					statistics.rtt(rtt);
					System.out.println(message + " RTT: " + rtt + " ms");
				}catch(SocketTimeoutException e) {
					System.out.println(message + " RTT: " + "*");
				}
			}
		
		}catch (SocketException e) {
			e.printStackTrace();
		}catch (IOException e) {
			e.printStackTrace();
		}
		
		System.out.println("---- PING Statistics ----");
		System.out.println(statistics.toString());
	}
	
}
