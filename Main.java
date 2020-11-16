import java.util.Scanner;
import java.util.Map;
import java.util.HashMap;
import java.util.Set;

/**
 * 
 */
public class Main {

    private static StudentController studentController;

    public static void main(String[] args) {

        Scanner scan = new Scanner(System.in);

        boolean quit = false;
        while(!quit) {
            System.out.println();
            System.out.println("-----------------------------------------");
            System.out.println("| Student Automated Registration System |");
            System.out.println("-----------------------------------------");
            System.out.println("Please choose type of login: ");
            System.out.println("1. Admin login");
            System.out.println("2. Student login");
            System.out.println("3. Quit");
            int login = scan.nextInt();
            scan.nextLine();
            switch(login){
                case 1:
                    adminMain();
                    break;
                case 2:
                    studentLogin(scan);
                    break;
                case 3:
                    quit = true;
                    break;
                default:
                    System.out.println("Invalid option");
            }
        }

        scan.close();

    }

    public static void adminLogin(Scanner scan) {

    }

    public static void studentLogin(Scanner scan) {
        boolean success = false;
        while(!success) {
            System.out.println("Logging in as student...");
            System.out.printf("Account: ");
            String account = scan.nextLine();
            if (account.equals(""))
                return;
            System.out.printf("Password: ");
            String password = scan.nextLine();
            System.out.println("Checking password...");
            studentController = new StudentController();
            success = studentController.login(account, password);
        }
        studentMain(scan);
    }

    public static void adminMain(){

    }

    public static void studentMain(Scanner scan){
        int choice = 0;

        while(choice != 7){
            System.out.println("Please select one of the functions: ");
            System.out.println("1. Add Course");
            System.out.println("2. Drop Course");
            System.out.println("3. Check/Print Courses Registered and on Wait list");
            System.out.println("4. Change Index Number of Course");
            System.out.println("5. Swap Index Number with Another Student");
            System.out.println("6. Check Vacancies Available");
            System.out.println("7. Exit");
            choice = scan.nextInt();

            switch(choice){

                default:
                    System.out.println("TBD");
                    break;
/*
                case 1:
                    studentController.addCourse();
                    break;

                case 2:
                    studentController.dropCourse();
                    break;

                case 3:
                    studentController.printCoursesRegistered();
                    studentController.printOnWaitlist();
                    break;

                case 4:
                    studentController.changeIndex();
                    break;

                case 5:
                    studentController.swapIndex();
                    break;

                case 6:
                    studentController.reclassify();
                    break;*/
            }
        }
    }
}


















