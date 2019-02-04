import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.List;

public class Writter {
    String file;
    List<String> sOut; //Contiene le righe da scrivere sul file 

    public Writter(String file, List<String> sOut) {
        if (file == null || sOut == null) throw new NullPointerException();
        this.file = file;
        this.sOut = sOut;
    }

    public void startWritter() {
        try {
            FileChannel outChannel = FileChannel.open(Paths.get(file), StandardOpenOption.WRITE, StandardOpenOption.TRUNCATE_EXISTING, StandardOpenOption.CREATE);
            ByteBuffer buffer = ByteBuffer.allocateDirect(1024 * 1024);
            int arrayPos = 0;
            int lenght = 0;
            StringBuilder out = new StringBuilder();

            int size = sOut.size();
            for (int i = 0; i < size ; i++) {
                out.append(sOut.get(i));
            }

            System.out.println("Inizio scrittura file...");
            while(arrayPos < out.length()){
                lenght = Math.min(buffer.capacity(), (out.length() - arrayPos) );
                buffer.put(out.toString().getBytes(), arrayPos, lenght);
                arrayPos += lenght;
                buffer.flip();                             //Il buffer passa da modalita' scrittura a lettura
                while (buffer.hasRemaining())
                    outChannel.write(buffer);              //Scrittura sul canale 
                buffer.clear();
            }
            outChannel.close();
            System.out.println("Scrittura completata\n");
            System.out.println("File scitto:");
            System.out.println(out.toString());

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}