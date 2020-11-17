import java.util.Queue;

public class AdminController{

    private Admin model;
    private AdminView view;

    public AdminController() {
        model = new Admin();
        view = new AdminView();
    }

    public boolean login(String account, String password) {
        String hashedPassword = hash(password);
        Queue<String> allPasswords = model.readPasswords();
        String currentRecord;
        String[] currentTuple = new String[2];
        boolean success = false;
        currentRecord = allPasswords.poll();
        while (currentRecord != null && !success) {
            currentTuple = currentRecord.split(",");
            if (currentTuple[0].equals(account) && currentTuple[1].equals(hashedPassword)) {
                System.out.println("Logged in successfully.");
                System.out.println("Current account: " + account);
                success = true;
            }
            currentRecord = allPasswords.poll();
        }
        if (!success) {
            System.out.println("Login failed.");
            System.out.println("Check account and password and try again.");
            System.out.println("Or press enter to quit");
            return success;
        }
        success = readAdmin(account);
        if (!success) {
            System.out.println("Admin information not found");
            System.out.println("Try another account");
            return success;
        }
        return success;
    }

    public boolean readAdmin(String account) {
        String[] modelInfo = model.readInfo(account);
        if (modelInfo == null)
            return false;
        model.setName(modelInfo[1]);
        model.setNationality(modelInfo[2]);
        return true;
    }

    private String hash(String pwd) {
        return pwd;
    }
}