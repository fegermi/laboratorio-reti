import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.List;

public class ReaderSingle {
    String file;
    List<String> sOut;  //Contiene le stringhe da scrivere sul file

    public ReaderSingle(String file){
        if(file == null) throw new NullPointerException();
        this.file = file;
        sOut = new ArrayList<>();
    }

    public void startReaderSingle() {
        try {
            FileChannel inChannel = FileChannel.open(Paths.get(file), StandardOpenOption.READ);
            StringBuilder sb = new StringBuilder();
            ByteBuffer buffer = ByteBuffer.allocateDirect(1024 * 1024);
            boolean stop = false;

            System.out.println("Inizio lettura "+ file);
            //Leggo dal file
            while (!stop) {
                int bytesRead = inChannel.read(buffer);
                if (bytesRead == -1) stop = true;
                buffer.flip();
                for (int i = 0; i < buffer.limit(); i++) {
                    sb.append((char) buffer.get(i));
                }
                buffer.clear();
            }
            inChannel.close();
            System.out.println("Lettura file completata\n");
            System.out.println("Inizio risoluzione indirizzi ip");
            System.out.println("Attendere...");

            //Parsing file
            String[] riga = sb.toString().split("\n");     //Array contenente tutte le righe del file
            String[] tmp;
            for(int i = 0; i < riga.length; i++) {
                tmp = riga[i].split(" ", 2);
                GetByNameSingle getByName = new GetByNameSingle(tmp[0], tmp[1]);    //tmp[0] contiene l'ip, tmp[1] contiene il dato
                sOut.add(i, getByName.startGetByNameSingle());   //Aggiungo a sOut: Reverse resolution + dato
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public List<String> getSOut(){
        return sOut;
    }
}

