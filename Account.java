import java.io.BufferedReader;
import java.io.IOException;
import java.io.FileReader;
import java.io.FileNotFoundException;
import java.util.Queue;
import java.util.Set;
import java.util.LinkedList;

public abstract class Account {

    private String account;
    final private String ANSI_RESET = "\u001B[0m";
    final private String ANSI_RED   = "\u001B[31m";

    public Queue<String> readPasswords(String account, String filename) {
        Queue<String> passwordInfo = new LinkedList<String>();
        try {
            FileReader fRead = new FileReader(filename);
            BufferedReader bRead = new BufferedReader(fRead);
            String newline;
            while ((newline = bRead.readLine()) != null) 
                passwordInfo.add(newline);
            bRead.close();
            fRead.close();
        } catch(FileNotFoundException e) {
            System.out.println(ANSI_RED + "Error: password file " + filename + " not found." + ANSI_RESET);
            System.exit(1);
        } catch (IOException e) {
            System.out.println(ANSI_RED + "Error: failed to read " + filename + "." + ANSI_RESET);
            System.exit(1);
        }
        return passwordInfo;
    }
}
