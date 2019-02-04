public class MainClass {
	
	public static void main(String args[]){
		
		// Avvio server
		Thread server = new Thread() {
			public void run()
			{
				// Parametri: porta
				ServerFtp.main(new String[]{"9000"});
			}
		};
		server.start();
		
		// Avvio client
		// Parametri: host porta files...
		ClientFtp.main(new String[]{"127.0.0.1", "9000", "input1.txt", "input2.txt"});	
	}

}
