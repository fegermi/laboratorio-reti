import java.util.ArrayList;
import java.util.List;

public class ThOcc implements Runnable{
	private String nome;
	private ContoCorrente cc;
	private Count count;
	private int nBonifico ;
	private int nAccredito;                                                 
	private int nBollettino;
	private int nF24;
	private int nPagoBancomat;
	
	public ThOcc(String nome, ContoCorrente cc, Count count) {                           
		if(nome == null || cc == null || count == null ) throw new NullPointerException();
		this.nome = nome;
		this.cc = cc;
		this.count = count;    
		this.nBonifico = 0;
		this.nAccredito = 0;
		this.nBollettino = 0;
		this.nF24 = 0;
		this.nPagoBancomat = 0;
	}
	
	public String getNome() {
		return this.nome;
	}
	
	public int getNBonifico() {
		return this.nBonifico;
	}
	
	public int getNAccredito() {
		return this.nAccredito;
	}
	
	public int getNBollettino() {
		return this.nBollettino;
	}
	
	public int getNF24() {
		return this.nF24;
	}
	
	public int getNPagoBancomat() {
		return this.nPagoBancomat;
	}

	@Override
	public void run() {
		System.out.println("[" + this.getNome() + "] Start");
		String causale;
		List<Movimento> mov = new ArrayList<Movimento>();
		mov = cc.getMovimenti();
		
		//Leggo la causale e aggiorno i contatori locali
		for (Movimento temp : mov) {
			causale = temp.getCausale();
			if(causale.equals("Bonifico")) nBonifico++;
			else if(causale.equals("Accredito")) nAccredito++;
			else if(causale.equals("Bollettino")) nBollettino++;
			else if(causale.equals( "F24")) nF24++;
			else if(causale.equals("PagoBancomat")) nPagoBancomat++;
		}
		
		//Aggiorno i contatori globali contenuti in count
		if(nBonifico > 0)     count.incNBonifico(nBonifico);
		if(nAccredito > 0)    count.incNAccredito(nAccredito);
		if(nBollettino > 0)   count.incNBollettino(nBollettino);
		if(nF24 > 0) 		  count.incNF24(nF24);
		if(nPagoBancomat > 0) count.incNPagoBancomat(nPagoBancomat);
		
		System.out.println("[" + this.getNome() +"] [" + cc.getNome() + "] n_bonifici: "     + this.getNBonifico());
		System.out.println("[" + this.getNome() +"] [" + cc.getNome() + "] n_accrediti: "    + this.getNAccredito());
		System.out.println("[" + this.getNome() +"] [" + cc.getNome() + "] n_bollettini: "   + this.getNBollettino());
		System.out.println("[" + this.getNome() +"] [" + cc.getNome() + "] n_F24: "          + this.getNF24());
		System.out.println("[" + this.getNome() +"] [" + cc.getNome() + "] n_pagoBancomat: " + this.getNPagoBancomat());
	}
	
}
