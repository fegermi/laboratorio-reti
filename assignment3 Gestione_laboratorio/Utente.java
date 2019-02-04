package assignment2;

public interface Utente {
	public String getNome();
	public void setNome(String nome);
	public int getComputer();
	public void setComputer(int idComputer);
	public void assegnaComputers(Computer[] pcs, int tempo); 
}