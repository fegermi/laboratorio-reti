import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;

public class TimeClient {
	
	public static void main(String[] args) {
		
		// Parsing parametro di input                        
		if(args.length == 0){
			System.err.println("[TimeClient] ERR -arg 0");
			return ;
		}	
		String dataGroup = args[0];
		int portMS = 6900;           
		byte[] buf = new byte[512];
		
		try (MulticastSocket ms = new MulticastSocket(portMS)) {
			System.out.println("[TimeClient] start");
			InetAddress ia = InetAddress.getByName(dataGroup);
			ms.joinGroup(ia);                                                  // Si unisce a dataGroup
			System.out.println("[TimeClient] unito a dategroup: " + dataGroup);
			for(int i = 0; i < 10; i++) {                                     // Ricezione per 10 volte data/ora  
				DatagramPacket dp = new DatagramPacket(buf, buf.length);
				ms.receive(dp); 
				System.out.println("[TimeClient] Multicast received from " + dp.getSocketAddress() + " : " + new String(
						dp.getData(),
						dp.getOffset(),
						dp.getLength(),
						"UTF-8"));
			}
			System.out.println("[TimeClient] stop");
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
}
