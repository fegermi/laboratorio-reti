public class MainClass {
	
	public static void main(String args[]){
		
		// Avvio server ping 
		Thread server = new Thread() {
			public void run()
			{
				// Parametri: porta
				PingServer.main(new String[]{"9000"});
			}
		};
		server.start();
		
		// Avvio client ping 
		// Parametri: host portaServer
		PingClient.main(new String[]{"127.0.0.1", "9000"});	
	}
}
