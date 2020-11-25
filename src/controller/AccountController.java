package src.controller;

import src.boundary.FileHandler;
import src.boundary.InputScanner;
import src.model.Index;

import java.io.Console;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Scanner;
import java.util.Set;

public class AccountController {

    
    /**
     * Account type stored for accessing files
     */
    private String prefix;
    
    /**
     * Account
     */
    private String account;

    // getters

    /** 
    * Gets prefix of the account.
    * @return A string prefix.
    */
    public String getPrefix() {
        return prefix;
    }

    /** 
    * Gets account.
    * @return A string account.
    */
    public String getAccount() {
        return account;
    }

    // setters
    /** 
    * Sets prefix of the account.
    * @param pre A string containing prefix of the account.
    */
    public void setPrefix(String pre) {
        prefix = pre;
    }

    /** 
    * Sets account.
    * @param acc string account.
    */
    public void setAccount(String acc) {
        account = acc;
    }

    // common login methods
    /** 
    * Controls the login function and check the correctness of account and password.
    * @return A boolean indicating whether the login is successful.
    */
    public boolean login() {
        System.out.println("Checking password...");
        boolean success = false;
        int count = 0;
        while (!success && count < 3) {
            System.out.println("Logging in as " + prefix + "...");
            System.out.println("(Press enter to exit)");
            String[] loginInfo = promptForPassword();
            if (loginInfo == null) {
                return false;
            }
            success = checkPassword(loginInfo[0], loginInfo[1]);
            if (!success) {
                System.out.println("Login failed.");
                System.out.println("Check account and password and try again.");
                count ++;
                System.out.println(count);
            } else {
                System.out.println("Logged in successfully.");
                System.out.println("Current account: " + account);
            }
        }
        if(count == 3){
            System.out.println("You have exceeded 3 tries.");
        }
        return success;
    }

    /** 
    * Prompting users for password.
    * @return A String containing login information.
    */
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

    /** 
    * Checking the entered password.
    * @param acc the login account
    * @param password the password for checking
    * @return A boolean indicating whether the password is correct.
    */
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

    /** 
    * Read information of the account.
    * @param account the account to read for
    * @param col the col where account appears
    * @return A String containing account information.
    */
    public String[] readInfo(String account, int col) {
        return FileHandler.readRow(prefix + "_information", account, col);
    }

    // helper methods for hashing

    /** 
    * Helper methods for hashing.
    * Hash password to check whether it is correct.
    * @param password the password to be hashed
    * @param salt the byte array salt
    * @return A String containing generated password using hashing.
    */
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

    /** 
    * Convert byte array to hexadecimal string. Used by hash(String password, byte[] salt) to convert password
    * into hexadecimal.
    * Hashed password is changed to hexadecimal form.
    * @param bytes bytes array to be converted
    * @return A String containing converted password in hexadecimal.
    */
    public String byteArrToHexStr(byte[] bytes) {
        StringBuilder sb = new StringBuilder();
        for(int i = 0; i < bytes.length; i++) {
            sb.append(Integer.toString((bytes[i] & 0xff) + 0x100, 16).substring(1));
        }
        return sb.toString();
    }

    /** 
    * Convert hexadecimal string to byte array. Used by checkPassword(String acc, String password) to convert password
    * into byte.
    * Hashed password is changed to byte form.
    * @param hex hexadecimal array to be converted
    * @return An Array containing converted password in byte.
    */
    public byte[] hexStrToByteArr(String hex) {
        byte[] bytes = new byte[hex.length() / 2];
        for (int i = 0; i < bytes.length; i++) {
            int index = i * 2;
            int j = Integer.parseInt(hex.substring(index, index + 2), 16);
            bytes[i] = (byte) j;
        }
        return bytes;
    }
    
    /**
     * Gets random salt in SHA1
     * @return byte array salt
     */
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

    /** 
    * Both students and admin can check vacancy for indexes.
    * @param indexes A Set containing all the current indexes. 
    */
    public void checkVacancy(Set<Index> indexes) {
        Scanner scan = new Scanner(System.in);
        String check = "y";
        int i;
        while(check.equals("y")){
            Index index = null;
            System.out.println("please enter the index you want to check: ");
            InputScanner is = new InputScanner();
            i = is.nextInt(0);
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
            System.out.println("Would you like to check vacancy for another index? (y/n)");
            check = scan.nextLine();
        }
    }

}
