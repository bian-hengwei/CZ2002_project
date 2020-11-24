package src.boundary;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.FileNotFoundException;
import java.util.HashSet;
import java.util.Set;


/*
 * test
 */
public class FileHandler {

    private static final String ANSI_RESET = "\u001B[0m";
    private static final String ANSI_RED   = "\u001B[31m";

    public static Set<String> readToSet(String filename) {
        String path = generatePath(filename);
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
        } catch (FileNotFoundException e) {
            System.out.printf("%sError: file %s not found.\n%s", ANSI_RED, path, ANSI_RESET);
            System.exit(1);
        } catch (IOException e) {
            System.out.printf("%sError: failed to read %s.\n%s", ANSI_RED, path, ANSI_RESET);
            System.exit(1);
        }
        return info;
    }

    public static String[] readRow(String filename, String attribute) {
        return readRow(filename, attribute, 0);
    }

    public static String[] readRow(String filename, String attribute, int col) {
        String path = generatePath(filename);
        String[] row = null;
        boolean found = false;
        try {
            FileReader fRead = new FileReader(path);
            BufferedReader bRead = new BufferedReader(fRead);
            String newline;
            while ((newline = bRead.readLine()) != null && !found) {
                row = newline.split(",");
                if (row[col].equals(attribute)) 
                    found = true;
            }
            bRead.close();
            fRead.close();
        } catch (FileNotFoundException e) {
            System.out.printf("%sError: file %s not found.\n%s", ANSI_RED, path, ANSI_RESET);
            System.exit(1);
        } catch (IOException e) {
            System.out.printf("%sError: failed to read %s.\n%s", ANSI_RED, path, ANSI_RESET);
            System.exit(1);
        }
        return found ? row : null;
    }

    public static void save(String filename, String content) {
        String path = generatePath(filename);
        try {
            FileWriter fWrite = new FileWriter(path);
            fWrite.write(content);
            fWrite.close();
        } catch (IOException e) {
            System.out.printf("%sError: failed to write to %s.\n%s", ANSI_RED, path, ANSI_RESET);
            System.exit(1);
        }
    }

    public static void writeLine(String filename, String attribute, String line) {
        String path = generatePath(filename);
        StringBuilder sb = new StringBuilder();
        boolean found = false;
        try {
            FileReader fRead = new FileReader(path);
            BufferedReader bRead = new BufferedReader(fRead);
            String newline;
            while ((newline = bRead.readLine()) != null) {
                String[] tuple = newline.split(",");
                if (tuple[0].equals(attribute)) {
                    sb.append(line);
                    found = true;
                } else
                    sb.append(newline);
                sb.append("\n");
            }
            bRead.close();
            fRead.close();
        } catch (FileNotFoundException e) {
            System.out.printf("%sError: file %s not found.\n%s", ANSI_RED, path, ANSI_RESET);
            System.exit(1);
        } catch (IOException e) {
            System.out.printf("%sError: failed to read %s.\n%s", ANSI_RED, path, ANSI_RESET);
            System.exit(1);
        }
        if (!found) 
            sb.append(line + "\n");
        String content = sb.toString().substring(0, sb.length()-1);
        try {
            FileWriter fWrite = new FileWriter(path);
            fWrite.write(content);
            fWrite.close();
        } catch (IOException e) {
            System.out.printf("%sError: failed to write to %s.\n%s", ANSI_RED, path, ANSI_RESET);
            System.exit(1);
        }
    }

    private static String generatePath(String filename) {
        final String SEP = System.getProperty("file.separator");
        return String.format("data%s%s.csv", SEP, filename);
    }

}
