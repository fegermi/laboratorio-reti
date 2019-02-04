package assignment4;

public class MainClass {
	
	//Parametri: nContiCorrenti (int) numero conti correnti
	//           nMovimentiMin (int)  numero movimenti minimi per ogni conto corrente 
	//	         nMovimentiMax (int)  numero movimenti massimi per ogni conto corrente 
	public static void main(String[] args) {
		
		int nContiCorrenti = 1000;               //Parametri di default
		int nMovimentiMin  = 3000;
		int nMovimentiMax  = 5000;
		String file        = "dati.dat";
		
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
		
		System.out.println("--------------------");
		System.out.println("nContiCorrenti = " +  nContiCorrenti);
		System.out.println("nMovimentiMin  = " +  nMovimentiMin);
		System.out.println("nMovimentiMax  = " +  nMovimentiMax);
		System.out.println("--------------------\n");

		Writer writer = new Writer(nContiCorrenti, nMovimentiMin, nMovimentiMax);
		Reader reader = new Reader(nContiCorrenti);
		
		//Serializzazione
		writer.startSerializable(file);
		
		//Stampa oggetti serializzati
		writer.printfSerializable();
		
		//Deserializzazione
		reader.starDeserializable(file);
		
		//Stampa calcolo
		reader.printCausali();
	}
}
