import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.charset.StandardCharsets;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.Iterator;
import java.util.Set;

public class ServerFtp {
	
	public static int DEFAULT_PORT = 9000;
	
	public static void main(String[] args){
		int port = DEFAULT_PORT;
		
		//parsing input 
		if(args.length > 0) {
			try {
				port = Integer.parseInt(args[0]);
			} catch (NumberFormatException e) {
				System.err.println("[ServerFtp] Errore argomenti: port ");
			}
		}else {
			System.out.println("[ServerFtp] default_port = " + DEFAULT_PORT);
		}
		
		System.out.println("[ServerFtp] Started");
		
		try {
			ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
			serverSocketChannel.socket().bind(new InetSocketAddress(port));
			serverSocketChannel.configureBlocking(false);
		
			Selector selector = Selector.open();                   
			serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);
			ByteBuffer buf = ByteBuffer.allocate(1024*1024);
			String pathFile = null;
			
			while(true) {	
				selector.select();
				Set<SelectionKey> readyKeys = selector.selectedKeys();
				Iterator<SelectionKey> iterator = readyKeys.iterator();
				
				while(iterator.hasNext()) {
					SelectionKey key = iterator.next(); 
					iterator.remove();                   //Rimuovo la chiave dal Selected Set
					try {
						if(key.isAcceptable()) {
							register(selector, serverSocketChannel);
						} 
						if(key.isReadable()) {
							pathFile = readPathFile(selector, buf, key);
						}
						if(key.isWritable()) {
							sendFile(selector, buf, key, pathFile);
						}
					}catch (IOException ex) { 
						key.cancel();
						try{ key.channel().close(); }
						catch (IOException cex) {} 
					}
				}
			}
		}catch (IOException e) {
			e.printStackTrace();
		}	
	}		

	private static void register(Selector selector, ServerSocketChannel serverSocket) throws IOException {
		SocketChannel client = serverSocket.accept();
		System.out.println("[ServerFtp] Connessione accettata: " + client.getRemoteAddress());
        client.configureBlocking(false);
        client.register(selector, SelectionKey.OP_READ);
	}
	
	private static String readPathFile(Selector selector, ByteBuffer buf, SelectionKey key) throws IOException {
		StringBuilder pathFile = new StringBuilder();
		SocketChannel client = (SocketChannel) key.channel();
		
		// Leggo il path del file inviato dal client 
		while (client.read(buf) >=0) {             
			buf.flip();
			while (buf.hasRemaining()) {
				pathFile.append(StandardCharsets.UTF_8.decode(buf).toString());
			}
			buf.clear();
		}
		System.out.println("[ServerFtp] file richiesto: " + pathFile.toString());
		client.register(selector, SelectionKey.OP_WRITE);
		client.shutdownInput();
		return pathFile.toString();
	}
	
	private static void sendFile(Selector selector, ByteBuffer buf, SelectionKey key, String pathFile) throws IOException   {
		SocketChannel client = (SocketChannel) key.channel();
		
		//Apro il canale in lettura 
		FileChannel inChannel;
		try {
			inChannel = FileChannel.open(Paths.get(pathFile), StandardOpenOption.READ);
		} catch (IOException e) {
			System.err.printf("[ServerFtp] File " + pathFile + " non trovato\n");    
	        client.close();
			return;
		}
		
        boolean stop = false;
        System.out.println("[ServerFtp] Inizio lettura file e invio al client" );
   
        //Leggo il file e scrivo lo scrivo al client
        while (!stop) {
            int bytesRead = inChannel.read(buf);
            if (bytesRead == -1) stop = true;
       
            buf.flip();
			while (buf.hasRemaining()) {
				client.write(buf);
			}
            buf.clear();
        }
        inChannel.close();
        client.close();
        System.out.println("[ServerFtp] File inviato al client");
	}
}






