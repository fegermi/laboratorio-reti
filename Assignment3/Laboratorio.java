package assignment2;

import java.util.LinkedList;
import java.util.Queue;

public class Laboratorio {
	
	private Computer computer[]; 
	private Tutor tutor;
	private int nComputer;
	private boolean open;
	private Thread threadTutor;
	
	//Code utenti 
	private Queue<Professore> QProf;
	private Queue<Tesista> QTesista;
	private Queue<Studente> QStudente;
	
	//Costruttore
	public Laboratorio(int nComputer) {
		this.nComputer = nComputer;
		this.tutor = null; 
		this.open = false;
		this.threadTutor = null;
		this.QProf = new LinkedList<Professore>();
		this.QStudente = new LinkedList<Studente>();
		this.QTesista = new LinkedList<Tesista>();
		this.computer = new Computer[nComputer + 1];
		this.computer[0] = null; 							//computer[1] è il primo pc
		
		//Creo nComputer e l'inserisco nell'array computer
		for(int i = 1; i <= this.nComputer; i++) {          
			this.computer[i] = new Computer("Computer " + i);
		}
	}
	
	public boolean apriLaboratorio() {
		if(this.tutor == null) return false;   //Controllo che il tutor sia stato assegnato
		this.open = true;
		this.tutor.setLaboratorio(this);       //Passo il tutor al laboratorio 
		this.threadTutor = new Thread(this.tutor);
		this.threadTutor.start();              //Start thread tutor
		return true;	
	}
	
	public void richiesta(Professore p) {
		System.out.println("[Laboratorio] " + p.getNome() + " messo in coda" );
		this.QProf.add(p);
	}
	
	public void richiesta(Studente s) {
		System.out.println("[Laboratorio] " + s.getNome() + " messo in coda" );
		this.QStudente.add(s);
	}
	
	public void richiesta(Tesista t) {
		System.out.println("[Laboratorio] " + t.getNome() + " messo in coda" );
		this.QTesista.add(t);
	}
	
	public void richiesta(Utente u) {
		if(u instanceof Professore) this.richiesta((Professore) u);
		else if(u instanceof Tesista) this.richiesta((Tesista) u);
		else this.richiesta((Studente) u);
	}
		
	public void chiudiLaboratorio() {
		this.open = false;
	}
	
	public void setTutor(Tutor tutor) {
		this.tutor = tutor;
	}
	
	public Computer[] getComputer() {
		return computer;
	}
	
	public Queue<Professore> getQProf() {
		return QProf;
	}

	public Queue<Tesista> getQTesista() {
		return QTesista;
	}

	public Queue<Studente> getQStudente() {
		return QStudente;
	}
	
	public boolean isOpen() {
		return open;
	}

}
