package assignment4;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Reader {
	private List<ContoCorrente> ccDeserializza;   //Lista cc deserializzati
	private ExecutorService executor;
	private int nContiCorrenti;
	Count count;                                  //Oggetto contenete i risultati totali delle causali 
	
	public Reader(int nContiCorrenti) {
		if(nContiCorrenti <= 0) throw new IllegalArgumentException();
		ccDeserializza	= new ArrayList<>(nContiCorrenti);
		executor = Executors.newFixedThreadPool(10);           
		count = new Count(); 
		this.nContiCorrenti = nContiCorrenti;
	}
	
	//Deserializzazione 
	public void starDeserializable(String file) {
		try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file));){ 
			for(int i = 0; i < nContiCorrenti; i++) {
				ccDeserializza.add(i, (ContoCorrente) ois.readObject());
				Runnable worker = new ThOcc("Thread " + i, ccDeserializza.get(i), count);     
				executor.execute(worker);                                                      //Start thread
			}
		}catch (IOException e) {
			System.out.println("Errore nella creazione dello stream");
			e.printStackTrace();
			System.exit(1);
		}catch (ClassNotFoundException e) {
			System.out.println("Impossibile trovare la classe");
			e.printStackTrace();
			System.exit(1);
		}
	
		executor.shutdown();                                                                        
		while( !executor.isTerminated() ) {}  //Aspetto che tutti i task finiscano
		System.out.println("Tutti i thread sono terminati");
	}
	
	public void printfDeserializable() {
		for(int i = 0; i < nContiCorrenti; i++) {
			System.out.println(ccDeserializza.get(i).toString());
			System.out.println("");
		}
	}
	
	public void printCausali(){
		System.out.println(count.toString());
	}
}
