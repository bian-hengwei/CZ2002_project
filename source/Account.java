import java.io.BufferedReader;
import java.io.IOException;
import java.io.FileReader;
import java.io.FileNotFoundException;
import java.util.Queue;
import java.util.Set;
import java.util.LinkedList;

public abstract class Account {

    final private String ANSI_RESET = "\u001B[0m";
    final private String ANSI_RED   = "\u001B[31m";

    public Queue<String> readPasswords(String filename) {
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
            System.out.println("Error: password file " + filename + " not found.");
            System.exit(1);
        } catch (IOException e) {
            System.out.println("Error: failed to read " + filename + ".");
            System.exit(1);
        }
        return passwordInfo;
    }

    public String[] readInfo(String account, String filename) {
        String[] currentTuple = new String[9];
        boolean found = false;
        try {
            FileReader fRead = new FileReader(filename);
            BufferedReader bRead = new BufferedReader(fRead);
            String newline;
            while ((newline = bRead.readLine()) != null && !found) {
                currentTuple = newline.split(",");
                if (currentTuple[0].equals(account)) 
                    found = true;
            }
            bRead.close();
            fRead.close();
        } catch(FileNotFoundException e) {
            System.out.println("Error: info file " + filename + " not found.");
            System.exit(1);
        } catch (IOException e) {
            System.out.println("Error: failed to read " + filename + ".");
            System.exit(1);
        }
        return found ? currentTuple : null;
    }
}
