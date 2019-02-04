import java.io.IOException;

import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class Reader {
	private List<ContoCorrente> ccDeserializza;    //Lista cc deserializzati
	private ExecutorService executor;
	private long nContiCorrenti;
	private float dimFile;
	Count count;                                   //Oggetto contenete i risultati totali delle causali 
	JSONParser parser;
	JSONObject jsonObject;
	
	public Reader(int nContiCorrenti) {
		if(nContiCorrenti <= 0) throw new IllegalArgumentException();
		ccDeserializza	= new ArrayList<>(nContiCorrenti);
		nContiCorrenti = 0;
		dimFile = 0;
		executor = Executors.newFixedThreadPool(6);       
		count = new Count();
		parser = new JSONParser();
	}
	
	//Deserializzazione 
	@SuppressWarnings("unchecked")
	public void starDeserializable(String file) {
		if(file == null) throw new NullPointerException();
		int i = 0;
		try {
			FileChannel inChannel = FileChannel.open(Paths.get(file), StandardOpenOption.READ);
			StringBuilder sb = new StringBuilder(); 
			ByteBuffer buffer = ByteBuffer.allocateDirect(1024);
			boolean stop = false;
			
			//Leggo dal file 
			while (!stop) {
				int bytesRead=inChannel.read(buffer);
				if (bytesRead == -1) stop = true;
				buffer.flip(); 
				for(i = 0; i < buffer.limit(); i++) {
					sb.append((char) buffer.get(i));
				}
				buffer.clear();
			}
			
			dimFile = sb.length();
			inChannel.close();
			Object obj = parser.parse(sb.toString());
			jsonObject = (JSONObject) obj;
			
		}catch (IOException e) { e.printStackTrace(); }
		catch (ParseException e) { e.printStackTrace();} 

		//Parsing file json
		Long nContiCorrentiJson =  (Long) jsonObject.get("nContiCorrenti");
		nContiCorrenti = nContiCorrentiJson;
			
		JSONArray contiCorrentiJson =  (JSONArray) jsonObject.get("conticorrenti");
		Iterator<JSONObject> iteratorCc = contiCorrentiJson.iterator();
		while (iteratorCc.hasNext()) {
			JSONObject tmpICc = iteratorCc.next();
			ContoCorrente cc = new ContoCorrente((String) tmpICc.get("nome"));
			JSONArray movimentiJson = (JSONArray) tmpICc.get("movimenti");
			Iterator<JSONObject> iteratorM = movimentiJson.iterator();
			while (iteratorM.hasNext()) {
				JSONObject tmpIM = iteratorM.next();
				Movimento m = new Movimento( (String) tmpIM.get("data"), (String) tmpIM.get("causale"));
				cc.addMovimento(m);
			}
				
			Runnable worker = new ThOcc("Thread " + i++, cc, count);
			executor.execute(worker);                                         //Start thread
			ccDeserializza.add(cc); 		
		}
	
		executor.shutdown();                                                                        
		while( !executor.isTerminated() ) {}  //Aspetto che tutti i task finiscano
		System.out.println("\nTutti i thread sono terminati\n");
	}
	
	public void printfDeserializable() {
		for(int i = 0; i < nContiCorrenti; i++) {
			System.out.println(ccDeserializza.get(i).toString());
			System.out.println("");
		}
	}
	
	public float dimFile() {
		return dimFile / 1000;  //KB
	}
	
	public void printCausali(){ 
		System.out.println(count.toString());
	}
}
