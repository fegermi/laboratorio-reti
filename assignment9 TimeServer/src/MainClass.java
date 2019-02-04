public class MainClass {
	
	public static void main(String args[]){
		
		// Avvio TimeServer
		Thread server = new Thread() {
			public void run()
			{
				// Parametri: dategroup (gruppo di multicast)
				TimeServer.main(new String[]{"239.255.0.1"});
			}
		};
		server.start();
		
		// Avvio TimeServer
		// Parametri: dategroup (gruppo di multicast)
		TimeClient.main(new String[]{"239.255.0.1"});	
	}
	
}
