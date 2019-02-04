package assignment2;

import java.util.concurrent.locks.ReentrantLock;

public class Computer {
	private String nome;
	private ReentrantLock pcLock;
	
	public Computer(String nome) {
		this.nome = nome;
		this.pcLock = new ReentrantLock();
	}
	
	public String getNome() {
		return this.nome;
	}
	
	public void setNome(String nome) {
		this.nome = nome;
	}
	
	public ReentrantLock getLock() {
		return this.pcLock;
	}
	
	public boolean isFree() {
		return !(this.pcLock.isLocked());
	}
}
