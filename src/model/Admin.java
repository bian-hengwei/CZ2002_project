package src.model;


/**
 * Admin entity
 */
public class Admin{

    
    /**
     * Admin account
     */
    private String account;
    
    /**
     * Admin name
     */
    private String name;
    
    /**
     * Admin nationality
     */
    private String nationality;

    /**
     * Constructor of admin
     */
    public Admin() {}

    // getters

    /**
     * getter of account
     * @return account
     */
    public String getAccount() {
        return account;
    }

    /**
     * getter of name
     * @return name
     */
    public String getName() {
        return name;
    }

    /**
     * getter of nationality
     * @return nationality
     */
    public String getNationality() {
        return nationality;
    }

    // setters

    /**
     * setter of account
     * @param account admin account
     */
    public void setAccount(String account) {
        this.account = account;
    }
    
    /**
     * setter of name
     * @param n admin name
     */
    public void setName(String n) {
        name = n;
    }

    /**
     * setter of nationality
     * @param n admin nationality
     */
    public void setNationality(String n) {
        nationality = n;
    }

}
