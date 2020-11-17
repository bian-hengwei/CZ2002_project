import java.util.Queue;

public class Admin extends Account {

    private String name;
    private String nationality;
    private final String SEP = System.getProperty("file.separator");

    public Queue<String> readPasswords() {
        return super.readPasswords(".." + SEP + "data" + SEP + "admin_passwords.csv");
    }

    public String[] readInfo(String account) {
        return super.readInfo(account, ".." + SEP + "data" + SEP + "admin_information.csv");
    }

    public void setName(String n) { name = n; }

    public void setNationality(String n) { nationality = n; }

}
