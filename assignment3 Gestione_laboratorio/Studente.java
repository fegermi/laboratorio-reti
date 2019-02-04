package assignment2;

import java.util.concurrent.locks.ReentrantLock;

public class Studente implements Runnable, Utente{
	private String nome;
	private int idComputer;
	private Computer assegnato[];
	private int tempo;
	
	//Costruttore
	public Studente(String nome) {
		this.nome = nome;
		this.idComputer = 0;
		this.assegnato = null;
		this.tempo = 0;
	}
	
	@Override
	public void run() {         
		assert(this.assegnato.length == 1);               //Controllo che gli sia stato assegnato un pc 
		ReentrantLock lock = this.assegnato[0].getLock(); 
		assert(lock != null && !lock.isLocked());         //Controllo che la lock non sia null e che il pc non sia gia in uso da un' altro utente 
		lock.lock();
		System.out.println("[Studente] " + this.nome + " ha occupato il " + this.assegnato[0].getNome() + " per " + this.tempo + " ms");
		try{
			Thread.sleep(this.tempo);               //Simulazione uso computer
			}catch (InterruptedException e) {
				e.printStackTrace();
			}
		lock.unlock();
		System.out.println("[Studente] " + this.nome + " ha liberato il " + this.assegnato[0].getNome());
	}
	
	@Override
	public String getNome() {
		return this.nome;
	}
	
	@Override
	public void setNome(String nome) {
		this.nome = nome;
	}
	
	@Override
	public int getComputer() {   //Gli studenti prendono un pc qualunque 
		return -1;
	}
	
	@Override
	public void setComputer(int idComputer) { 
	}
	
	@Override
	public void assegnaComputers(Computer[] pcs, int tempo) {
		this.assegnato = pcs ;
		this.tempo = tempo ;
	}

}

