public class MainClass {
	
	//Parametri: nContiCorrenti (int) numero conti correnti
	//           nMovimentiMin (int)  numero movimenti minimi per ogni conto corrente 
	//	         nMovimentiMax (int)  numero movimenti massimi per ogni conto corrente 
	public static void main(String[] args) {
		
		int nContiCorrenti = 20;               //Parametri di default
		int nMovimentiMin  = 30;
		int nMovimentiMax  = 30;
		String file        = "bank.json";
		
		//Parsing input
		if(args.length >= 3) {
			try{
				int tmpNContiCorrenti = Integer.parseInt(args[0]);
				int tmpNMovimentiMin  = Integer.parseInt(args[1]);
				int tmpNMovimentiMax  = Integer.parseInt(args[2]);
				if(tmpNContiCorrenti > 0) nContiCorrenti = tmpNContiCorrenti;
				if(tmpNMovimentiMin <= tmpNMovimentiMax && tmpNMovimentiMin > 0 && tmpNMovimentiMax > 0 ) {
					nMovimentiMin = tmpNMovimentiMin;
					nMovimentiMax = tmpNMovimentiMax;
				}else {
					System.out.println("nMovimentiMin, nMovimentiMax di default\n");
				}
				}catch(NumberFormatException e){
					e.printStackTrace();
				}
		}else {
			System.out.println("nMovimentiMin, nMovimentiMax di default\n");
		}
		
		System.out.println("----------------------");
		System.out.println("nContiCorrenti = " +  nContiCorrenti);
		System.out.println("nMovimentiMin  = " +  nMovimentiMin);
		System.out.println("nMovimentiMax  = " +  nMovimentiMax);
		System.out.println("----------------------\n");

		Writer writer = new Writer(nContiCorrenti, nMovimentiMin, nMovimentiMax); 
		Reader reader = new Reader(nContiCorrenti);
		
		Long time1 = System.currentTimeMillis();
		
		//Serializzazione
		writer.startSerializable(file);
		
		//Deserializzazione
		reader.starDeserializable(file);
		
		Long time2 = System.currentTimeMillis();
		
		reader.printCausali();
		System.out.println("Dimensione file " + "[" + file + "]: " + reader.dimFile() + " KB");
		System.out.println("Tempo impiegato: " + (time2 - time1) + " ms");
	}
}
