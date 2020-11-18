import java.util.Queue;
import java.util.Set;

public class AdminController extends AccountController {

    private Admin model;
    private AdminView view;

    public AdminController() {
        model = new Admin();
        view = new AdminView();
        super.setPrefix("administrator");
    }

    // initialize methods

    public boolean init() {
        Set<String> allPasswords = model.readPasswords();
        System.out.println("Checking password...");
        boolean success = login(allPasswords);
        if (!success) {
            return success;
        }
        success = readAdmin(getAccount());
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

}
