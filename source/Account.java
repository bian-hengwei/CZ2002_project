import java.util.Set;

public abstract class Account {

    private String name;
    private String nationality;

    // getters

    public String getName() {
        return name;
    }

    public String getNationality() {
        return nationality;
    }

    // setters

    public void setName(String n) {
        name = n;
    }

    public void setNationality(String nation) {
        nationality = nation;
    }

}
