import java.io.Console;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Scanner;
import java.util.Set;

public class AccountController {

    private String prefix;
    private String account;

    // getters

    public String getPrefix() {
        return prefix;
    }

    public String getAccount() {
        return account;
    }

    // setters

    public void setPrefix(String pre) {
        prefix = pre;
    }

    public void setAccount(String acc) {
        account = acc;
    }

    // common login methods

    public boolean login() {
        System.out.println("Checking password...");
        boolean success = false;
        while (!success) {
            System.out.println("Logging in as " + prefix + "...");
            System.out.println("(press enter to exit)");
            String[] loginInfo = promptForPassword();
            if (loginInfo == null) {
                return false;
            }
            success = checkPassword(loginInfo[0], loginInfo[1]);
            if (!success) {
                System.out.println("Login failed.");
                System.out.println("Check account and password and try again.");
            } else {
                System.out.println("Logged in successfully.");
                System.out.println("Current account: " + account);
            }
        }
        return success;
    }

    public String[] promptForPassword() {
        Scanner scan = new Scanner(System.in);
        String[] loginInfo = new String[2];
        System.out.printf("Account: ");
        loginInfo[0] = scan.nextLine();
        if (loginInfo[0].equals("")) {
            return null;
        }
        Console console = System.console();
        loginInfo[1] = new String(console.readPassword("Password: "));
        if (loginInfo[1].equals("")) {
            return null;
        }
        return loginInfo;
    }

    public boolean checkPassword(String acc, String password) {
        String[] info = FileHandler.readRow(prefix + "_passwords", acc);
        if (info == null)
            return false;
        byte[] salt = hexStrToByteArr(info[1]);
        String hashedPassword = hash(password, salt);
        if (info[2].equals(hashedPassword)) {
            account = acc;
            return true;
        }
        return false;
    }

    public String[] readInfo(String account) {
        return FileHandler.readRow(prefix + "_information", account);
    }

    // helper methods for hashing

    public String hash(String password, byte[] salt) {
        String generatedPassword = null;
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-512");
            md.update(salt);
            byte[] bytes = md.digest(password.getBytes());
            generatedPassword = byteArrToHexStr(bytes);
        } catch(NoSuchAlgorithmException e) {
            System.out.println("NoSuchAlgorithmException");
            e.printStackTrace();
        }
        return generatedPassword;
    }

    public String byteArrToHexStr(byte[] bytes) {
        StringBuilder sb = new StringBuilder();
        for(int i = 0; i < bytes.length; i++) {
            sb.append(Integer.toString((bytes[i] & 0xff) + 0x100, 16).substring(1));
        }
        return sb.toString();
    }

    public byte[] hexStrToByteArr(String hex) {
        byte[] bytes = new byte[hex.length() / 2];
        for (int i = 0; i < bytes.length; i++) {
            int index = i * 2;
            int j = Integer.parseInt(hex.substring(index, index + 2), 16);
            bytes[i] = (byte) j;
        }
        return bytes;
    }

    public byte[] getSalt() {
        try {
            SecureRandom sr = SecureRandom.getInstance("SHA1PRNG");
            byte[] salt = new byte[16];
            sr.nextBytes(salt);
            return salt;
        } catch(NoSuchAlgorithmException e) {
            System.out.println("NoSuchAlgorithmException");
            e.printStackTrace();
        }
        return null;
    }

    public void checkVacancy(Set<Index> indexes) {
        Scanner scan = new Scanner(System.in);
        String check = "y";
        int i;
        while(check.equals("y")){
            Index index = null;
            System.out.println("please enter the index you want to check: ");
            i = scan.nextInt();
            scan.nextLine();
            for (Index idx : indexes){
                if(idx.getIndexNumber() == i){
                    index = idx;
                    IndexController indexController = new IndexController(index);
                    indexController.printVacancy();
                    break;
                }
            }
            if (index == null) {
                System.out.println("Index not found");
            }
            System.out.println("Would you like to check vacancy for another index? (please enter y/n)");
            check = scan.nextLine();
        }
    }

}
