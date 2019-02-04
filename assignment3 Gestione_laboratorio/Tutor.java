package assignment2;

import java.util.LinkedList;
import java.util.Queue;
import java.util.Iterator;

public class Tutor implements Runnable {
	
	private String nome;
	private Laboratorio labDaGestire;
	private Queue<Thread> QThread;    //Coda thread
	
	public Tutor(String nome) {
		this.nome = nome;
		this.labDaGestire = null ;
		this.QThread = new LinkedList<Thread>();
	}
	
	@Override
	public void run() {
		assert(labDaGestire != null);  //Controllo che il laboratorio sia stato assegnato al tutor
		
		//Prendo le code dei vari utenti che hanno fatto richiesta di accesso al laboratorio
		Queue<Professore> Qp = this.labDaGestire.getQProf();
		Queue<Studente>   Qs = this.labDaGestire.getQStudente();
		Queue<Tesista>    Qt = this.labDaGestire.getQTesista();
		
		//Prendo i pc del laboratorio 
		Computer pc[] = this.labDaGestire.getComputer();
		boolean freePc[] = new boolean[pc.length];
		int nfree, tempo, i;
		
		while( !(!this.labDaGestire.isOpen() && Qp.isEmpty() && Qt.isEmpty() && Qs.isEmpty()) ) { //PERCHE' FUNZIONA ???
	    	nfree = 0;
	    	
	    	//Vado a vedere quali pc sono liberi 
	    	for(i = 1; i<pc.length; i++) {
	    		freePc[i] = pc[i].isFree();
	    		if(freePc[i]) nfree ++;
	    	}
	    	
	    	//Priorità: 1)prof 2)tesisti 3)studenti
	    	
	    	//Prof: Accedono in modo escusivo a tutti i pc del laboratoio
	    	if(Qp.size() != 0) { 
	    		if(nfree == pc.length -1) {                 //Se tutti i pc del lab sono liberi
	    			Professore professore = Qp.poll();
	    			tempo = (int) (2000 + (Math.random()*3000));
	    			professore.assegnaComputers(pc, tempo); //Assegno al professore tutti i pc 
	    			nfree = 0;
	    			System.out.println("[Tutor] " + this.nome + " Start task per " + professore.getNome());
	    			Thread thread = new Thread(professore);
	    			this.QThread.add(thread);
	    			thread.start();
	    		}
	    	}else { //Tesisti e studenti
	    		
	    		//Iteratore per scorrere la coda dei tesisti
	    		Iterator<Tesista> iter = Qt.iterator();
	    		Queue<Tesista> done = new LinkedList<Tesista>(); //Coda di appoggio 
	    		
	    		while(iter.hasNext()) {
	    			Tesista tesista = iter.next();
	    			int idComputer = tesista.getComputer();
	    			tempo = (int) (2000 + (Math.random()*3000));
	    			
	    			//Controllo che il pc richiesto sia libero
	    			if(freePc[idComputer]) {
	    				tesista.assegnaComputers(new Computer[]{pc[tesista.getComputer()]}, tempo); 
	    				freePc[idComputer] = false;
	    				nfree --;
	    				System.out.println("[Tutor] " + this.nome + " Start task per " + tesista.getNome() );
	    				Thread thread = new Thread(tesista);
	    				this.QThread.add(thread);
	    				thread.start();
	    				done.add(tesista);
	    			}
	    		}
	    		
	    		while(!done.isEmpty()) Qt.remove(done.poll());
	    		
	    		//Studenti
	    		while(nfree > 0 && !Qs.isEmpty()) {
	    			
	    			//Estraggo uno studente dalla coda
	    			Studente studente = Qs.poll();
	    			i=0;
	    			
	    			//Trovo un pc libero, sicuramente c'e
	    			while(!freePc[i]) i++;
	    			tempo = (int) (2000 + (Math.random()*3000));
	    			studente.assegnaComputers(new Computer[] { pc[i] }, tempo); //Assegno allo studente il computer libero trovato
	    			freePc[i] = false;
	    			nfree--;
	    			System.out.println("[Tutor] " + this.nome + " Start task per " + studente.getNome() );
	    			Thread thread = new Thread(studente);
	    			this.QThread.add(thread);
	    			thread.start();
	    		}
	    	}
	    	
	    	try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				Thread.currentThread().interrupt();
			}
	    }
	    
	    //Abbiamo terminato
	    while(!this.QThread.isEmpty()){
			Thread curr = this.QThread.poll();
			try {
				curr.join();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	    
	    System.out.println("[Tutor] " + this.nome + " Chiusura laboratorio!");
	    this.setLaboratorio(null);
	}
	
	
	public void setLaboratorio(Laboratorio labDaGestire) {
		this.labDaGestire = labDaGestire;
	}

}
