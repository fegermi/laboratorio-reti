import java.util.ArrayList;
import java.util.List;

public class MainClass {

    public static void main(String[] args){
        //Mainclass pathFile
        //  path file (string) file log da leggere 

        String file = "web.log.txt";
        List<String> sOut = new ArrayList<>();

        //Parsing input
        if(args.length >= 1) {
            file = args[0];
        }

        //--------------------- Versione single-threaded ---------------------

        System.out.println("<Versione single-threaded>");

        Long time1 = System.currentTimeMillis();

        ReaderSingle readSingle = new ReaderSingle(file);
        readSingle.startReaderSingle();
        sOut = readSingle.getSOut();

        Writter writeSingle = new Writter("output_single.log.txt", sOut);
        writeSingle.startWritter();

        Long time2 = System.currentTimeMillis();

        System.out.println("Tempo impiegato [single-threaded] : " + (time2 - time1) + " ms\n");

        //--------------------- Versione multi-threaded ---------------------

        System.out.println("<Versione multi-threaded>");

        time1 = System.currentTimeMillis();

        ReaderMulti readMulti = new ReaderMulti(file);
        readMulti.startReaderMulti();
        sOut = readMulti.getSOut();

        Writter writeMulti = new Writter("output_multi.log.txt", sOut);
        writeMulti.startWritter();

        time2 = System.currentTimeMillis();

        System.out.println("Tempo impiegato [multi-threaded] : " + (time2 - time1) + " ms");

    }
}