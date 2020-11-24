package src.model;

public class Admin{

    private String account;
    private String name;
    private String nationality;

    public Admin() {}

    // getters

    public String getAccount() {
        return account;
    }
    
    public String getName() {
        return name;
    }

    public String getNationality() {
        return nationality;
    }

    // setters

    public void setAccount(String account) {
        this.account = account;
    }
    
    public void setName(String n) {
        name = n;
    }

    public void setNationality(String n) {
        nationality = n;
    }

}
