import org.json.simple.JSONObject;

public class Movimento {
	private String data; 
	private String causale; 
	JSONObject movimentoJson;    // { "data": _ , "causale": _ }
		
	public Movimento(String data, String causale){
		if(data == null || causale == null) throw new NullPointerException();
		if(!causale.equals("Bonifico") && !causale.equals("Accredito") && !causale.equals("Bollettino")
				&& !causale.equals("F24") && !causale.equals("PagoBancomat"))  throw new IllegalArgumentException();
		
		this.data = data;
		this.causale = causale;
		movimentoJson = new JSONObject();
	}

	public String getData(){
		return data;
	}
	
	public String getCausale() {
		return causale;
	}
	
	public String toString() {
		return "Data: " + this.getData() + " Causale: " + this.getCausale() + "\n";
	} 
	
	@SuppressWarnings("unchecked")
	public JSONObject parserMovimeto() {
		movimentoJson.put("data", data);
		movimentoJson.put("causale", causale);
		return movimentoJson;
	}

}

