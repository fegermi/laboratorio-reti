/* Assignment1_2 Ufficio_postale
 * Autore: Federico Germinario 545081
 */

package assignment1_2;

public class Persona implements Runnable{
	private int numero;
	private String nome;
	private int tempoOperazione;

	//Costruttore
	public Persona(int numero, String nome, int tempoOperazione) { 
		if(numero < 0) throw new IllegalArgumentException();
		if(tempoOperazione < 0) throw new IllegalArgumentException();
		
		this.numero = numero;
		this.nome = nome;
		this.tempoOperazione = tempoOperazione;
	}
	
	//Metodi get
	public int getNumero(){
		return this.numero;
	}
	
	public String getNome() {
		return this.nome;
	}
	
	public int getTempoOperazione() {
		return this.tempoOperazione;
	}
	
	public void run() {
		System.out.println("["+this.nome+"] Inizio operazione allo sportello! durata: "+(this.tempoOperazione)/1000+" s");
		
		try {
			Thread.sleep(this.tempoOperazione);  //Simulazione operazione sportello
		} catch(InterruptedException e){
			throw new RuntimeException(e);
		}
		
		System.out.println("["+this.nome+"] Ha finito!");
	}
}
