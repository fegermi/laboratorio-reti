package assignment4;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ContoCorrente implements Serializable {
	private static final long serialVersionUID = 1L;
	private String nome;
	private List<Movimento> movimenti; 
	
	public ContoCorrente(String nome) {
		if(nome == null) throw new NullPointerException();
		this.nome = nome;
		movimenti = new ArrayList<Movimento>();
	}
	
	//Genera n movimenti random con data <= 2 anni
	public List<Movimento> randomMovimenti(int n){
		if(n <= 0) throw new IllegalArgumentException();
		List<Movimento> tmp = new ArrayList<>(n);
		long ms;
		for(int i = 0; i < n; i++) {
			ms = (long) (System.currentTimeMillis() - (Math.random()*63155200000L)); //63155200000 ms = 2 anni 
			Date dataTmp = new Date(ms);
			String causale = randomCausale();
			try {
				tmp.add(new Movimento(dataTmp, causale));
			}catch(NullPointerException e) {
				e.printStackTrace();
			}
			catch (IllegalArgumentException e) {
				e.printStackTrace();
			}
		}
		return tmp;
	}
	
	//Genera una causale randomica, ognuna ha la stessa probalilità di comparire
	private String randomCausale() {        
		int n = (int)(Math.random()*100); 
		if(n <= 20) return "Bonifico";
		if(n <= 40) return "Accredito";
		if(n <= 60) return "Bollettino";
		if(n <= 80) return "F24";
		if(n <= 100) return "PagoBancomat";
		return null;	
	}
	
	public void setMovimenti(List<Movimento> m) {                                                       
		if(m == null) throw new NullPointerException();
		this.movimenti = m;
	}
	
	public void setNome(String nome) {
		if(nome == null) throw new NullPointerException();
		this.nome = nome;
	}
	
	public String getNome() {
		return this.nome;	
	}
	
	public List<Movimento> getMovimenti() {
		return movimenti;
	}
	
	public String toString() {
		return "[" + this.nome + "] Tot movimenti: " + this.movimenti.size() + "\nMovimenti:\n" + movimenti.toString();
	}
	
}
