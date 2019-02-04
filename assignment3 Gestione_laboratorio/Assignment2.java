package assignment2;

import java.util.Arrays;
import java.util.Collections;

public class Assignment2 {

	public static void main(String[] args) throws InterruptedException {
		int nStudenti   = 0; 
		int nTesisti    = 0; 
		int nProfessori = 0;
		int nUtenti     = 0;
		int nComputer   = 2;
		int k;   //Numero accessi al laboratorio  
		
		//Parsing input
		if(args.length >= 3) {
			try{
					int tmpNStudenti    = Integer.parseInt(args[0]);
					int tmpNTesisti     = Integer.parseInt(args[1]);
					int tmpNProfessori  = Integer.parseInt(args[2]);
					nStudenti = tmpNStudenti;
					nTesisti = tmpNTesisti;
					nProfessori = tmpNProfessori;
	            }catch(NumberFormatException c){}
	    } 
		else {
	    	System.out.println("Immettere nStudente, nTesisti, nProfessori");
	    	return;
	    }
		
		//Calcolo numero utenti 
		nUtenti = nStudenti + nTesisti + nProfessori;
		
		Tutor tutor = new Tutor("Tutor Federico");
		Laboratorio lab = new Laboratorio(nComputer);
		
		//Assegno al laboratorio il tutor
		lab.setTutor(tutor);
		
		Utente utenti[] = new Utente[nUtenti];
		int i, s = 0, t = 0, p = 0;
		
		System.out.println("Lista utenti:");
		
		//Inserisco gli utenti nella lista utenti
		for(i = 0; i<nStudenti; i++) {
			utenti[i] = new Studente("Studente " + (++s));
			System.out.println("[MAIN] Utente["+i+"] " + utenti[i].getNome() + " idComputer " + utenti[i].getComputer());
		}

		for(i = nStudenti; i < (nStudenti+nTesisti); i++) {
			utenti[i] = new Tesista("Tesista " + (++t));
			utenti[i].setComputer(1 + (int)(Math.random()*nComputer)); //Scelgo a caso il computer con il software per scrivere la tesi 
			System.out.println("[MAIN] Utente["+i+"] " + utenti[i].getNome() + " idComputer " + utenti[i].getComputer());
		}
		
		for(i = (nStudenti+nTesisti); i<nUtenti; i++) {
			utenti[i] = new Professore("Professore " + (++p));
			System.out.println("[MAIN] Utente["+i+"] " + utenti[i].getNome() + " idComputer " + utenti[i].getComputer());
		}
		
		System.out.println();
		
		//Apro il laboratorio
		lab.apriLaboratorio();
		System.out.println("[MAIN] Laboratorio aperto!");
		
		k = (1 + (int) (Math.random()*4));
		System.out.println("[MAIN] Numero accessi laboratorio: " + k);
		System.out.println();
		
		//Ogni utente entra k volte nel laboratorio
		for(i=0; i < k; i++){
			
			//Randomizzo l'array utenti
			Collections.shuffle(Arrays.asList(utenti));
			
			//Invio richieste al laboratorio e attendo al massimo 1s 
			for(int j=0; j<utenti.length; j++){
				lab.richiesta(utenti[j]);
				Thread.sleep((int)(Math.random()*1000));
			}
		}  
		
        //Chiudo il laboratorio
		lab.chiudiLaboratorio();
	}
}

