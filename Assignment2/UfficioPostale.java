/* Assignment1_2 Ufficio_postale
 * Autore: Federico Germinario 545081
 */

package assignment1_2;

import java.util.*;
import java.util.concurrent.*;

public class UfficioPostale{
	private int nSportelli;
	private Queue<Persona> q;
	private ExecutorService executor ; 
	
	//Costruttore
	public UfficioPostale(int nSportelli) {
		if(nSportelli <= 0) throw new IllegalArgumentException();
		
		this.nSportelli = nSportelli;
		this.q = new LinkedList<Persona>();
	}
	
	//Metodo get
	public int getNSportelli(){
		return this.nSportelli;
	}
	
	public void addPersona(Persona p) {
		if(p == null) throw new NullPointerException();
		q.add(p);
	}
	
	public void startUfficioPostale() {
		System.out.println("L'ufficio postale � aperto con "+getNSportelli()+" sportelli attivi");
		System.out.println(""+this.q.size()+" persone in coda");
		
		this.executor = Executors.newFixedThreadPool(getNSportelli());
		
		while(!this.q.isEmpty()) { //Coda non vuota
			this.executor.execute(this.q.poll());
		}
		
		//Non ci sono piu' persone in coda
		this.executor.shutdown();
		while( !this.executor.isTerminated() ) {} //Aspetto che tutti i task finiscano
		System.out.println("Ufficio postale chiuso");
	}
	
}
