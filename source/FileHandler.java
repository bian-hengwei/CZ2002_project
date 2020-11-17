import java.io.BufferedReader;
import java.io.IOException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.FileNotFoundException;
import java.util.HashSet;
import java.util.Set;

public class FileHandler {

    private static final String SEP = System.getProperty("file.separator");
    private static final String ANSI_RESET = "\u001B[0m";
    private static final String ANSI_RED   = "\u001B[31m";

    public static Set<String> readToSet(String filename) {
        String path = String.format("..%sdata%s%s.csv", SEP, SEP, filename);
        Set<String> info = new HashSet<String>();
        try {
            FileReader fRead = new FileReader(path);
            BufferedReader bRead = new BufferedReader(fRead);
            String newline;
            bRead.readLine();
            while ((newline = bRead.readLine()) != null) {
                info.add(newline);
            }
            bRead.close();
            fRead.close();
        } catch(FileNotFoundException e) {
            System.out.printf("%sError: file %s not found.\n%s", ANSI_RED, path, ANSI_RESET);
            System.exit(1);
        } catch(IOException e) {
            System.out.printf("%sError: failed to read %s.\n%s", ANSI_RED, path, ANSI_RESET);
            System.exit(1);
        }
        return info;
    }

    public static String[] readRow(String filename, String attribute) {
        String path = String.format("..%sdata%s%s.csv", SEP, SEP, filename);
        String[] row = null;
        boolean found = false;
        try {
            FileReader fRead = new FileReader(filename);
            BufferedReader bRead = new BufferedReader(fRead);
            String newline;
            while ((newline = bRead.readLine()) != null && !found) {
                row = newline.split(",");
                if (row[0].equals(attribute)) 
                    found = true;
            }
            bRead.close();
            fRead.close();
        } catch(FileNotFoundException e) {
            System.out.printf("%sError: file %s not found.\n%s", ANSI_RED, path, ANSI_RESET);
            System.exit(1);
        } catch(IOException e) {
            System.out.printf("%sError: failed to read %s.\n%s", ANSI_RED, path, ANSI_RESET);
            System.exit(1);
        }
        return found ? row : null;
    }

    public static void save(String filename, String content) {
        String path = String.format("..%sdata%s%s.csv", SEP, SEP, filename);
        try {
            FileWriter fWrite = new FileWriter(path);
            fWrite.write(content);
            fWrite.close();
        } catch(IOException e) {
            System.out.printf("%sError: failed to write to %s.\n%s", ANSI_RED, path, ANSI_RESET);
            System.exit(1);
        }
    }

}
