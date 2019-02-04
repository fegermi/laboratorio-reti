import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.channels.SocketChannel;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.List;

public class ClientFtp {
	
	public static void main(String args[]){
		
		String host = "";
		int port = 0;
		List<String> files = new ArrayList<>();
		int nFile = args.length - 2;
		
		// Parsing input
		if(args.length > 2){        
			host = args[0] ;
			try {
				port = Integer.parseInt(args[1]);
			}catch(NumberFormatException e){
				System.err.println("[ClientFtp] Errore argomenti: host port files... \n");
			}
			for(int i = 0; i< nFile; i++) {
				files.add(args[i+2]);
			}
	
		}else {
			System.err.println("[ClientFtp] Errore argomenti: host port files...\n");
		}
		
		try {
			//Per ogni richiesta di download 
			for(int i = 0; i < nFile; i++) {
				SocketAddress address = new InetSocketAddress(host, port);
				SocketChannel socketChannel = SocketChannel.open(address);
				System.out.println("[ClientFtp] Connesso a " + socketChannel.getRemoteAddress());                                                                                                                                        // TODO
			
				ByteBuffer buf = ByteBuffer.allocate(1024*1024);  
            
				//Scrivo al server il path file
				buf.put(files.get(i).getBytes());
				buf.flip();              
				while (buf.hasRemaining()) {
					socketChannel.write(buf);
				}
				buf.clear();
				socketChannel.shutdownOutput(); // Chiudo il socketChannel in scrittura  
				System.out.println("[ClientFtp] Richiesta download: " + files.get(i));
				
				//Apro il canale in scrittura
				FileChannel outChannel = FileChannel.open(Paths.get("Download_" + files.get(i)), StandardOpenOption.WRITE, StandardOpenOption.TRUNCATE_EXISTING, StandardOpenOption.CREATE);
				
				System.out.println("[ClientFtp] Inizio download");
				
				//Leggo il file ricevuto dal server e lo scrivo tramite FileChannel
				while (socketChannel.read(buf) >= 0) {        
					buf.flip();
					while (buf.hasRemaining()) {
						outChannel.write(buf);
					}
					buf.clear();
				}
				outChannel.close();
				socketChannel.close();
				System.out.println("[ClientFtp] Download completato\n");
			}
			System.out.println("[ClientFtp] client stop");
			
		}catch (IOException e) {
			e.printStackTrace();
		} 
	} 
}
