import java.net.InetAddress;
import java.net.UnknownHostException;

public class GetByNameSingle {
    private String ip;
    private String dato;
    private String s;

    public GetByNameSingle(String ip, String dato){
        if(ip == null || dato == null) throw new NullPointerException();
        this.ip = ip;
        this.dato = dato;
        s = null;
    }

    public String startGetByNameSingle() {
        try {
            InetAddress address = InetAddress.getByName(ip);     //Reverse resolution
            s = address.getHostName() + " " + dato + "\n";
        }catch (UnknownHostException e) {
            e.printStackTrace();
        }
        return s;
    }
}