package assignment4;

import java.io.Serializable;
import java.util.*;

public class Movimento implements Serializable {
	private static final long serialVersionUID = 1L;
	private Date data; 
	private String causale; 
	
	public Movimento(Date data, String causale){
		if(data == null || causale == null) throw new NullPointerException();
		if(!causale.equals("Bonifico") && !causale.equals("Accredito") && !causale.equals("Bollettino")
				&& !causale.equals("F24") && !causale.equals("PagoBancomat"))  throw new IllegalArgumentException();
		
		this.data = data;
		this.causale = causale;
	}
	
	public Date getData(){
		return data;
	}
	
	public String getCausale() {
		return causale;
	}
	
	public String toString() {
		return "Data: " + this.data + " Causale: " + this.causale + "\n";
	} 

}
