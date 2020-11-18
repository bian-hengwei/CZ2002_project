import java.util.Set;

public abstract class Account {

    private String name;
    private String nationality;
    private String prefix;

    // getters

    public String getName() {
        return name;
    }

    public String getNationality() {
        return nationality;
    }

    public String getPrefix() {
        return prefix;
    }

    // setters

    public void setName(String n) {
        name = n;
    }

    public void setNationality(String nation) {
        nationality = nation;
    }

    public void setPrefix(String pre) {
        prefix = pre;
    }

    // read information

    public Set<String> readPasswords() {
        return FileHandler.readToSet(prefix + "_passwords");
    }

    public String[] readInfo(String account) {
        return FileHandler.readRow(prefix + "_information", account);
    }

}
