package assignment2;

import java.util.concurrent.locks.ReentrantLock;

public class Professore implements Runnable, Utente{
	
	private String nome;
	private Computer assegnato[];
	private int tempo;
	
	//Costruttore
	public Professore(String nome) {
		this.nome = nome;
		this.assegnato = null;
		this.tempo = 0;
	}
	
	@Override
	public void run() {        
		
		//Lock di tutti i computer
		for(int i=1; i<this.assegnato.length; i++) {
			ReentrantLock lock = this.assegnato[i].getLock();
			assert(lock != null && !lock.isLocked());  
			lock.lock();
		}

		System.out.println("[Professore] " + this.nome + " ha occupato tutti i computer del laboratorio per " + this.tempo + " ms");
		try{
			Thread.sleep(this.tempo);               //Simulazione uso computer
			}catch (InterruptedException e) {
				e.printStackTrace();
			}
		
		//Unlock di tutti i computer
		for(int i=1; i<this.assegnato.length; i++){
			ReentrantLock lock = this.assegnato[i].getLock() ;
			lock.unlock();
		}	
		
		System.out.println("[Professore] " + this.nome + " ha liberato tutti i computer del laboratorio");
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
	public int getComputer() {   //I professori hanno accesso esclusivo a tutti i computer
		return 0;
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

