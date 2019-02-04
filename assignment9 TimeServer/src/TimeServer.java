import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.util.Date;

public class TimeServer {
	
	public static void main(String[] args) {
		
		// Parsing parametro di input                            
		if(args.length == 0){
			System.err.println("[TimeServer] ERR -arg 0");
			return ;
		}
		String dataGroup = args[0];
		
		byte[] dataOut;
		int port = 6900;   // Porta su cui spedire il messaggio
		int delay = 4000;  // Ritardo tra un invio e il successivo
		
		try(MulticastSocket ms = new MulticastSocket(6800)){
			InetAddress multicastGroup = InetAddress.getByName(dataGroup);
			System.out.println("[TimeServer] start dataGroup: " + dataGroup);
			 while(true) {	 
				 String timeStamp =  new Date(System.currentTimeMillis()).toString();
				 dataOut = timeStamp.getBytes();
				 DatagramPacket dp = new DatagramPacket(dataOut, dataOut.length, multicastGroup, port);
				 ms.send(dp);
				 System.out.println("[TimeServer] invio [" + timeStamp + "] in multicast");
				 Thread.sleep(delay);
			 }
		}catch(IOException | InterruptedException ex){ 
			System.out.println(ex); 
		}
	}
}
	

