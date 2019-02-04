public class CurrentThread {
public static void main(String args[]) {
Thread current = Thread.currentThread();
System.out.println("ID: "+ current.getId());
System.out.println("NOME: "+ current.getName());
System.out.println("PRIORITA: "+ current.getPriority());
System.out.println("NOMEGRUPPO"+
current.getThreadGroup().getName());
}}
