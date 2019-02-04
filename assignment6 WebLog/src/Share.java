import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class Share {
    private CopyOnWriteArrayList sOut;   //ArrayList Thread safe in scrittura 
                                         //contenente le righe da scrivere sul file 
    public Share(){
        sOut = new CopyOnWriteArrayList();
    }

    public void setString(String sOut){
        this.sOut.add(sOut);
    }

    public List<String> getSOut(){
        return sOut;
    }
}
