import java.util.concurrent.locks.*;

public class Count {
	final Lock lockBonifico; 
	final Lock lockAccredito;
	final Lock lockBollettino;
	final Lock lockF24;
	final Lock lockPagoBancomat;
	
	private int nBonifico;
	private int nAccredito;
	private int nBollettino;
	private int nF24;
	private int nPagoBancomat;
	
	public Count() {
		lockBonifico     = new ReentrantLock();
	    lockAccredito    = new ReentrantLock();
		lockBollettino   = new ReentrantLock();
		lockF24          = new ReentrantLock();
		lockPagoBancomat = new ReentrantLock();
		nBonifico    	 = 0;
		nAccredito       = 0;
		nBollettino   	 = 0;
		nF24             = 0;
		nPagoBancomat    = 0;
	}
	
	public void incNBonifico(int i) {
		if(i < 0) throw new IllegalArgumentException();
		lockBonifico.lock();
		nBonifico = nBonifico + i;
		lockBonifico.unlock();
	}
	
	public void incNAccredito(int i) {
		if(i < 0) throw new IllegalArgumentException();
		lockAccredito.lock();
		nAccredito = nAccredito + i;
		lockAccredito.unlock();
	}
	
	public void incNBollettino(int i) {
		if(i < 0) throw new IllegalArgumentException();
		lockBollettino.lock();
		nBollettino = nBollettino + i;
		lockBollettino.unlock();
	}
	
	public void incNF24(int i) {
		if(i < 0) throw new IllegalArgumentException();
		lockF24.lock();
		nF24 = nF24 + i;
		lockF24.unlock();
	}
	
	public void incNPagoBancomat(int i) {
		if(i < 0) throw new IllegalArgumentException();
		lockPagoBancomat.lock();
		nPagoBancomat = nPagoBancomat + i;
		lockPagoBancomat.unlock();
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
	
	public String toString() {
		return "Numero totale causali: \n" + "Bonifici: " + this.getNBonifico() + "\nAccrediti: " + this.getNAccredito() + 
				"\nBollettini: " + this.getNBollettino() + "\nF24: " + this.getNF24() + "\nPagobancomat: " + this.getNPagoBancomat() + "\n";
	}

}
