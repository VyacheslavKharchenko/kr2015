import java.io.*;
import java.util.NoSuchElementException;
import java.util.Scanner;

public class RecordsSaver {
    public static final File RECORD_FILE = new File("record.txt");

    public static int loadRecord(){
        int record = 0;

        try {
            Scanner in = new Scanner(RECORD_FILE);
            record = in.nextInt();
            in.close();
        } catch (FileNotFoundException | NoSuchElementException e) {
            createFile();
            saveRecord(record);
        }

        return record;
    }

    public static void saveRecord(int record){
        try {
            BufferedWriter out = new BufferedWriter(new FileWriter(RECORD_FILE));
            out.write(Integer.toString(record));
            out.flush();

            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void createFile(){
        if (!RECORD_FILE.exists()) {
            try {
                RECORD_FILE.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}