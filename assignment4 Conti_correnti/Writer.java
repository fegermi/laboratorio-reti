

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Writer {
	private int nContiCorrenti;
	private int nMovimentiMin;
	private int nMovimentiMax;
	private List<ContoCorrente> ccSerializza;        //Lista cc da serializzare
	private List<Movimento> mov;                     //Lista movimenti
	
	public Writer(int nContiCorrenti, int nMovimentiMin, int nMovimentiMax) {
		if(nContiCorrenti <= 0 || nMovimentiMin <= 0 || nMovimentiMax <= 0 || nMovimentiMin > nMovimentiMax) throw new IllegalArgumentException();
		
		ccSerializza = new ArrayList<>(nContiCorrenti);          
		mov = new ArrayList<>();
		this.nContiCorrenti = nContiCorrenti;
		this.nMovimentiMin = nMovimentiMin;
		this.nMovimentiMax = nMovimentiMax;
	}
	
	public void startSerializable(String file) {
		if(file == null) throw new NullPointerException();
	
		try(ObjectOutputStream output = new ObjectOutputStream(new FileOutputStream(file));){
			
			//Creo i conti correnti con i relativi movimenti randomici 
			for(int i = 0; i < nContiCorrenti; i++) {
				
				//Calcolo randomicamente il valore nMovimenti
				Random rand = new Random();
				int c = (nMovimentiMax-nMovimentiMin) + 1;
				int nMovimenti = rand.nextInt(c) + nMovimentiMin; 
				
				ccSerializza.add(i ,new ContoCorrente("Conto " + i));
				mov = ccSerializza.get(i).randomMovimenti(nMovimenti);
				ccSerializza.get(i).setMovimenti(mov);
				//output.writeObject(ccSerializza.get(i));
			}
			output.writeObject(ccSerializza);
		}catch (IOException e) {
			System.out.println("Impossibile serializzare il conto corrente");
			e.printStackTrace();
			System.exit(1);
		}
	}
	
	public void printfSerializable() {
		for(int i = 0; i < nContiCorrenti; i++) {
			System.out.println(ccSerializza.get(i).toString());
			System.out.println("");
		}
	}
}
