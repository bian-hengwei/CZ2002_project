import java.util.Queue;
import java.util.Set;
import java.util.Scanner;

public class AdminController extends AccountController {

    private Admin model;
    private AdminView view;
    private Scanner scan;

    public AdminController() {
        model = new Admin();
        view = new AdminView();
        scan = new Scanner(System.in);
        super.setPrefix("admin");
    }

    // initialize methods

    public boolean init() {
        System.out.println("Checking password...");
        boolean success = login();
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
        String[] modelInfo = readInfo(account);
        if (modelInfo == null)
            return false;
        //model.setName(modelInfo[1]);
        //model.setNationality(modelInfo[2]);
        return true;
    }

    // 1
    public void editAccessPeriod() {
        System.out.println("You are editing the access period for students");
        System.out.println("You can set the access period for students from any school and any year");
        System.out.printf("School: ");
        String school = scan.nextLine();
        System.out.printf("Year: ");
        int year = scan.nextInt();
        scan.nextLine();
        if (year < 1 || year > 4) {
            System.out.println("Invalid entry, exiting...");
            return;
        }
        String index = String.format("%sY%d", school, year);
        System.out.printf("Start date (YYYYDDMM): ");
        String start = scan.nextLine();
        System.out.printf("End date (YYYYDDMM): ");
        String end = scan.nextLine();
        System.out.printf("Start time (HHMM): ");
        String stime = scan.nextLine();
        System.out.printf("End time (HHMM): ");
        String etime = scan.nextLine();
        String content = String.join(",", index, start, end, stime, etime);
        FileHandler.writeLine("access_time", index, content);
        System.out.printf("Access time for student in %s successfully changed\n", index);
    }

    // 2
   public void addStudent() {
        System.out.println("Adding new student to student list...");
        String[] accountInfo = promptForPassword();
        if (FileHandler.readRow("student_passwords", accountInfo[0]) != null) {
            System.out.println("Current account already exists");
            System.out.println("Exiting...");
            return;
        }
        System.out.println("Student account does not exist");
        System.out.println("Saving account and password...");
        byte[] salt = getSalt();
        String saltString = byteArrToHexStr(salt);
        String hashed = hash(accountInfo[1], salt);
        String content = String.join(",", accountInfo[0], saltString, hashed, ";");
        FileHandler.writeLine("student_passwords", accountInfo[0], content);
        System.out.println("Successfully saved");
        // save student tbd
    }

    // 3
    public void updateCourse(Set<Index> indexes) {
        System.out.println("Course control system");
        System.out.println("Please enter the index that you want to add / update");
        int indexNumber = scan.nextInt();
        IndexController iControl = new IndexController();
        scan.nextLine();
        boolean found = false;
        for (Index idx: indexes) {
            if (idx.getIndexNumber() == indexNumber) {
                iControl.setModel(idx);
                found = true;
                break;
            }
        }
        if (!found) {
            System.out.println("Index not found in the system");
            indexes.add(iControl.getModel());
            iControl.addIndex(indexNumber);
        } else {
            System.out.println("Index found in the system");
            iControl.updateIndex();
        }
    }

}
