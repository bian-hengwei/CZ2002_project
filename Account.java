public abstract class Account {

    protected String id;
    protected String name;

    public Account(String id, String name) {
        this.id = id;
        this.name = name;
    }

    // public boolean validatePassword(String password) {
    //     if(password == this.password){
    //         return true;
    //     }
    //     else{
    //         return false;
    //     }
    // }
    
    abstract void writeInfo();
}
