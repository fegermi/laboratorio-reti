import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ReaderMulti {
    String file;
    ExecutorService executor;
    Share share;                 //Struttura dati condivisa

    public ReaderMulti(String file){
        if(file == null) throw new NullPointerException();
        this.file = file;
        executor = Executors.newFixedThreadPool(4);
        share = new Share();
    }

    public void startReaderMulti() {
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
            String[] riga = sb.toString().split("\n");           //Array contenente tutte le righe del file
            String[] tmp;
            for(int i=0; i < riga.length; i++) { //for
                tmp = riga[i].split(" ", 2);
                Runnable worker = new GetByNameMulti("Thread " + i, tmp[0], tmp[1], share);   //tmp[0] contiene l'ip, tmp[1] contiene il dato
                executor.execute(worker);                               //Start thread per la reverse resolution di un indirizzo ip
            }

            executor.shutdown();
            while( !executor.isTerminated() ) {}  //Aspetto che tutti i task finiscano
            System.out.println("Tutti i thread sono terminati\n");

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public List<String> getSOut(){
        return share.getSOut();
    }

}

