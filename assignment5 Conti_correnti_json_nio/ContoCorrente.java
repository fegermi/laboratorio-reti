import java.text.SimpleDateFormat;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

public class ContoCorrente {
	private String nome;
	private List<Movimento> movimenti; 
	JSONObject contoCorrenteJson;       // { "nome": _ , "movimenti: [ { "data": _ , "causale": _ }
	JSONArray movimentiJson;            // [{ "data": _ , "causale": _ }, ...]
	
	public ContoCorrente(String nome) {
		if(nome == null) throw new NullPointerException();
		this.nome = nome;
		movimenti = new ArrayList<Movimento>();
		contoCorrenteJson = new JSONObject();
		movimentiJson = new JSONArray();
	}
	
	//Genera n movimenti random con data <= 2 anni
	@SuppressWarnings("unchecked")
	public void randomMovimenti(int n){
		if(n <= 0) throw new IllegalArgumentException();
		long ms;
		for(int i = 0; i < n; i++) {
			ms = (long) (System.currentTimeMillis() - (Math.random()*63155200000L)); //63155200000 ms = 2 anni 
			Date dataTmp = new Date(ms);
			SimpleDateFormat formatter = new SimpleDateFormat("dd-M-yyyy hh:mm:ss");  
			String strDate = formatter.format(dataTmp);  
		    strDate = formatter.format(dataTmp);  
			
			String causale = randomCausale();
			
			Movimento tmpM = new Movimento(strDate, causale);
			try {
				movimenti.add(tmpM);
				movimentiJson.add(tmpM.parserMovimeto());
			}catch(NullPointerException e) {
				e.printStackTrace();
			}
			catch (IllegalArgumentException e) {
				e.printStackTrace();
			}
		}
	}
	
	//Genera una causale randomica, ognuna ha la stessa probalilita' di comparire
	private String randomCausale() {        
		int n = (int)(Math.random()*100); 
		if(n <= 20) return "Bonifico";
		if(n <= 40) return "Accredito";
		if(n <= 60) return "Bollettino";
		if(n <= 80) return "F24";
		if(n <= 100) return "PagoBancomat";
		return null;	
	}
	
	public String getNome() {
		return this.nome;	
	}
	
	public List<Movimento> getMovimenti() {
		return movimenti;
	}
	
	public void addMovimento(Movimento m) {
		movimenti.add(m);
	}
	
	public String toString() {
		return "[" + this.getNome() + "] Tot movimenti: " + this.movimenti.size() + "\nMovimenti:\n" + movimenti.toString();
	}
	
	@SuppressWarnings("unchecked")
	public JSONObject parserCc() {
		contoCorrenteJson.put("nome", nome);
		contoCorrenteJson.put("movimenti", movimentiJson);
		return contoCorrenteJson;
	}
	
}
