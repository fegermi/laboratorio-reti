/* Assignment1_2 Ufficio_postale
 * Autore: Federico Germinario 545081
 */

package assignment1_2;

public class Assignment1_2 {

	public static void main(String[] args) {
		int nSportelli = 4;   //Valore di default
		int nPersone = 10;    //Persone minime allo sportello
		int tempoOperazione;
		
		//Parsing input: numero sportelli
		if(args.length >= 1) {
			try{
				int tmp = Integer.parseInt(args[0]);
	            if(tmp > 0) nSportelli = tmp;
	            }catch(NumberFormatException c){
	            	System.out.println("NumberFormatException");}
	    }
		
		//Creo l'ufficio postale
		UfficioPostale posta = new UfficioPostale(nSportelli);
		
		//Calcolo il numero di persone
		nPersone = (int) (nPersone + Math.random()*30); //nPersone: [10,40]
		
		for(int i = 0; i<nPersone; i++) {
			tempoOperazione = (int) (2000 + Math.random()*5000); //tempoOperazione : [2,7] s
			
			Persona persona = new Persona(i, "Persona "+i, tempoOperazione); //Creo una persona
			posta.addPersona(persona);	
		}
		
		//Apertura ufficio postale
		posta.startUfficioPostale();
	}
	
}

