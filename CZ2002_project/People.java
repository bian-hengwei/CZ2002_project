public abstract class People {
    protected String id;
    protected String password;
    protected String name;

    public People(String id, String password, String name){
        this.id = id;
        this.password = password;
        this.name = name;
    }

    public boolean validatePassword(String password){
        if(password == this.password){
            return true;
        }
        else{
            return false;
        }
    }
    
    abstract void writeInfo();
}
