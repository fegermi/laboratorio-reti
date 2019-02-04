import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;

public class PingServer {
	
	public static void main(String args[]) {
	   
		int port = 0;
		
		// Parsing parametri di input
		if(args.length == 0){
			System.out.println("[PingServer] ERR -arg 0");
			return ;
		}
		
		try {
			port = Integer.parseInt(args[0]);
		}catch(NumberFormatException e) {
			System.err.println("[PingServer] ERR -arg 0");
			return;
		}
		
		DatagramPacket request = new DatagramPacket(new byte[512], 512);
		try(DatagramSocket serverSocket = new DatagramSocket(port);){
			System.out.println("[PingServer] Start");
			while(true) {
				
				// Calcolo numero random [5, 800]
				long wait = 5 + (int)(Math.random() * ((800 - 5) + 1));
				request.setLength(512);
				serverSocket.receive(request);
								
				// Converto da byte a String
				byte data[] = request.getData() ;
				String msg = "" ;
				for(int i = 0; i < request.getLength()-1; i++) {
					msg += (char) data[i] ;
				}
				
				// Simulazione perdita del pacchetto con probabilità del 25 %
				if(Math.random() < 0.25 ){
					System.out.println(request.getSocketAddress() + "> " + msg + " ACTION: not send");
					continue ;
				}
				
				// Simulazione la latenza di rete
				Thread.sleep(wait);
				
				// Rimando il pacchetto
				DatagramPacket response =  new DatagramPacket(request.getData(), request.getLength(), request.getSocketAddress());
				serverSocket.send(response);
				System.out.println(request.getSocketAddress() + "> " + msg + " ACTION: delayed " + wait + " ms");
			}
		}catch (SocketException e) {
			e.printStackTrace();
		}catch (IOException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
}

