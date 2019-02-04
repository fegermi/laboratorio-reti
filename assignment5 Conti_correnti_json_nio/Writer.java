import java.io.IOException;

import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

public class Writer {
	private int nContiCorrenti;
	private int nMovimentiMin;
	private int nMovimentiMax;
	private List<ContoCorrente> contiCorrenti;        
	JSONObject objJson;
	JSONArray contiCorrentiJson;
	
	public Writer(int nContiCorrenti, int nMovimentiMin, int nMovimentiMax) {
		if(nContiCorrenti <= 0 || nMovimentiMin <= 0 || nMovimentiMax <= 0 || nMovimentiMin > nMovimentiMax) throw new IllegalArgumentException();
		
		this.contiCorrenti = new ArrayList<>(nContiCorrenti);          
		this.nContiCorrenti = nContiCorrenti;
		this.nMovimentiMin = nMovimentiMin;
		this.nMovimentiMax = nMovimentiMax;
		this.objJson = new JSONObject();
		this.contiCorrentiJson = new JSONArray();
	}
		
	@SuppressWarnings("unchecked")
	public void startSerializable(String file) {
		if(file == null) throw new NullPointerException();
		
		for(int i= 0; i < nContiCorrenti; i++) {
			
			//Calcolo randomicamente il valore nMovimenti
			Random rand = new Random();
			int c = (nMovimentiMax-nMovimentiMin) + 1;
			int nMovimenti = rand.nextInt(c) + nMovimentiMin; 
			ContoCorrente tmpCc = new ContoCorrente("Conto " + i);       //Creo un nuovo conto corrente
			tmpCc.randomMovimenti(nMovimenti);                           //Creo movimenti casuali su quel conto corrente
			
			System.out.println(tmpCc.toString());
			System.out.println("");
			
			contiCorrenti.add(i ,tmpCc);
			contiCorrentiJson.add(tmpCc.parserCc());  
		}
		
		objJson.put("nContiCorrenti", nContiCorrenti);
		objJson.put("conticorrenti", contiCorrentiJson);
		
		try {
			FileChannel outChannel = FileChannel.open(Paths.get(file), StandardOpenOption.WRITE, StandardOpenOption.TRUNCATE_EXISTING, StandardOpenOption.CREATE);
			ByteBuffer buffer = ByteBuffer.allocateDirect(objJson.toJSONString().length());
			buffer.put(objJson.toJSONString().getBytes());
			buffer.flip();                                         //Il buffer passa da modalità scrittura a lettura
			System.out.println("Scrivo il file JSON...");
			while (buffer.hasRemaining())
				outChannel.write(buffer);
			buffer.clear(); 
			outChannel.close();
			System.out.println("Scrittura completata\n");
			
		} catch(IOException e) {
			e.printStackTrace();
		}
	}
	
	public void printfContiCorrenti() {
		for(int i = 0; i < nContiCorrenti; i++) {
			System.out.println(contiCorrenti.get(i).toString());
			System.out.println("");
		}
	}

}
