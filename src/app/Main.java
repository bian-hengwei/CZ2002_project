package src.app;

import src.controller.StudentController;
import src.controller.AdminController;
import src.boundary.MainIO;
import src.boundary.InputScanner;
import src.model.Course;
import src.model.Index;

import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;


/**
 * Application file of STARS system
 * Reads in all classes and indexes information
 * Asks administrator or student to login and modifies the registration information
 */
public class Main {

    private static StudentController studentController;
    private static AdminController adminController;
    private static Set<Index> indexes;
    private static Set<Course> courses;
    private static Scanner scan;
    private static InputScanner is;

    /**
     * Main function of STARS system
     * Builds the set of data
     * Asks user to choose login mode or exit
     */
    public static void main(String[] args) {

        indexes = new HashSet<Index>();
        courses = new HashSet<Course>();

        MainIO.readCourses(courses);
        MainIO.readIndexes(courses, indexes);

        scan = new Scanner(System.in);
        is = new InputScanner();

        boolean quit = false;
        while (!quit) {
            System.out.println();
            System.out.println("--------------------------------------------");
            System.out.println("| My STudent Automated Registration System |");
            System.out.println("--------------------------------------------");
            System.out.println("Please choose type of login: ");
            System.out.println("1. Admin login");
            System.out.println("2. Student login");
            System.out.println("3. Quit");
            System.out.printf("Your choice: ");
            int login = is.nextInt(1, 4);
            switch (login) {
                case 1:
                    adminLogin();
                    break;
                case 2:
                    studentLogin();
                    break;
                case 3:
                    quit = true;
                    break;
                default:
                    System.out.println("Invalid option");
            }
            MainIO.saveCourses(courses);
            MainIO.saveIndexes(indexes);
        }
        scan.close();
    }

    /**
     * Login of administrator
     * First builds an empty admin controller
     * And try to use admin controller to login and build the administrator model
     * If the initialization is unsuccessful then go back to main
     * Else go to the menu of administrator
     */
    public static void adminLogin() {
        boolean success = false;
        adminController = new AdminController();
        success = adminController.init();
        if (!success) {
            return;
        }
        adminMain();
    }

    /**
     * Login of student
     * First builds an empty student controller
     * And try to use student controller to login and build the student model
     * If the initialization is unsuccessful then go back to main
     * Else go to the menu of student
     */
    public static void studentLogin() {
        boolean success = false;
        studentController = new StudentController();
        success = studentController.init(indexes);
        if (!success) {
            return;
        }
        studentMain();
    }

    /**
     * Main menu of administrator
     * Asks user to enter any of the 7 options
     * Perform corresponding function if the input is not 7
     * Continuously prompt for input if the input
     * Exit if input is 7
     */
    public static void adminMain() {
        
        int choice = 0;
        while (choice != 7) {
            System.out.println();
            System.out.println("Please select one of the functions: ");
            System.out.println("1. Edit student access period");
            System.out.println("2. Add student");
            System.out.println("3. Add/Update a course");
            System.out.println("4. Check available slot of an index number");
            System.out.println("5. Print student list by index number");
            System.out.println("6. Print student list by course");
            System.out.println("7. Exit");
            System.out.printf("Your choice: ");
            choice = is.nextInt(1, 8);

            switch (choice) {
                case 1:
                    System.out.println();
                    System.out.println("----- Edit student access period -----");
                    adminController.editAccessPeriod();
                    break;

                case 2:
                    System.out.println();
                    System.out.println("----- Add student -----");
                    adminController.addStudent();
                    break;

                case 3:
                    System.out.println();
                    System.out.println("----- Add/Update a course -----");
                    adminController.addUpdateCourse(courses, indexes);
                    break;

                case 4:
                    System.out.println();
                    System.out.println("----- Check available slot of an index number -----");
                    adminController.checkVacancy(indexes);
                    break;

                case 5:
                    System.out.println();
                    System.out.println("----- Print student list bu index number -----");
                    adminController.printByIndex(indexes);
                    break;

                case 6:
                    System.out.println();
                    System.out.println("----- Print student list by course -----");
                    // looking for input 6
                    adminController.printByCourse(courses);
                    break;

                case 7:
                    break;

                default:
                    System.out.println("Invalid option");
                    break;
            }
        }
    }

    /**
     * Main menu of student
     * Asks user to enter any of the 7 options
     * Perform corresponding function if the input is not 7
     * Continuously prompt for input if the input
     * Exit if input is 7
     */
    public static void studentMain() {
        
        int choice = 0;
        while (choice != 7) {
            // print basic student information
            System.out.println();
            studentController.printModelDetail();
            System.out.println("Please select one of the functions: ");
            System.out.println("1. Add Course");
            System.out.println("2. Drop Course");
            System.out.println("3. Check/Print courses registered and on waitlist");
            System.out.println("4. Change index number of course");
            System.out.println("5. Swap index number with another student");
            System.out.println("6. Check vacancies available");
            System.out.println("7. Exit");
            System.out.printf("Your choice: ");
            choice = is.nextInt(1, 8);

            switch (choice) {

                case 1:
                    System.out.println();
                    System.out.println("----- Add course -----");
                    studentController.addCourse(indexes);
                    break;

                case 2:
                    System.out.println();
                    System.out.println("----- Drop course -----");
                    studentController.dropCourse(indexes);
                    break;

                case 3:
                    System.out.println();
                    System.out.println("----- Check/Print courses registered and on waitlist -----");
                    studentController.printCoursesRegistered();
                    studentController.printOnWaitlist();
                    break;

                case 4:
                    System.out.println();
                    System.out.println("----- Change index number of course -----");
                    studentController.changeIndex(indexes);
                    break;

                case 5:
                    System.out.println();
                    System.out.println("----- Swap index number with another student -----");
                    studentController.swapIndex(indexes);
                    break;

                case 6:
                    System.out.println();
                    System.out.println("----- Check vacancies available -----");
                    studentController.checkVacancy(indexes);
                    break;

                case 7:
                    studentController.saveStudentInfo();
                    break;

                default:
                    System.out.println("Invalid option");
                    break;
            }
        }
    }

}
