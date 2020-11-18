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

    public void getPrefix() {
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

    public boolean login(Set<String> allPasswords) {
        boolean success = false;
        while (!success) {
            String[] loginInfo = promptForPassword();
            if (loginInfo == null) {
                return false;
            }
            success = checkPassword(loginInfo[0], loginInfo[1], allPasswords);
            if (!success) {
                System.out.println("Login failed.");
                System.out.println("Check account and password and try again.");
            }
        }
        return success;
    }

    public String[] promptForPassword() {
        Scanner scan = new Scanner(System.in);
        String[] loginInfo = new String[2];
        System.out.println("Logging in as " + prefix + "...");
        System.out.println("(press enter to exit)");
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

    public boolean checkPassword(String acc, String password, Set<String> allPasswords) {
        for (String currentRecord: allPasswords) {
            String[] currentTuple = currentRecord.split(",");
            byte[] salt = hexStrToByteArr(currentTuple[1]);
            String hashedPassword = hash(password, salt);
            if (currentTuple[0].equals(acc) && currentTuple[2].equals(hashedPassword)) {
                System.out.println("Logged in successfully.");
                System.out.println("Current account: " + acc);
                account = acc;
                return true;
            }
        }
        return false;
    }

    // private helper methods for hashing

    private String hash(String password, byte[] salt) {
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

    private String byteArrToHexStr(byte[] bytes) {
        StringBuilder sb = new StringBuilder();
        for(int i = 0; i < bytes.length; i++) {
            sb.append(Integer.toString((bytes[i] & 0xff) + 0x100, 16).substring(1));
        }
        return sb.toString();
    }

    private byte[] hexStrToByteArr(String hex) {
        byte[] bytes = new byte[hex.length() / 2];
        for (int i = 0; i < bytes.length; i++) {
            int index = i * 2;
            int j = Integer.parseInt(hex.substring(index, index + 2), 16);
            bytes[i] = (byte) j;
        }
        return bytes;
    }

    private byte[] getSalt() {
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

}
