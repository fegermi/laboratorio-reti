import java.net.InetAddress;
import java.net.UnknownHostException;

public class GetByNameMulti implements Runnable {
    private String nome;
    private String ip;
    private String dato;
    private Share share;

    public GetByNameMulti(String nome, String ip, String dato, Share share){
        if(nome == null || ip == null || dato == null || share == null) throw new NullPointerException();
        this.nome = nome;
        this.ip = ip;
        this.dato = dato;
        this.share = share;
    }

    public String getNome() {
        return nome;
    }

    @Override
    public void run() {
        try {
            System.out.println("[" + this.getNome() + "]" + " start");
            InetAddress address = InetAddress.getByName(ip);
            share.setString(address.getHostName() + " " + dato + "\n");
        } catch (UnknownHostException e) {
            e.printStackTrace();}
    }
}
