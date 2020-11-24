package src;

import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

public class Main {

    private static StudentController studentController;
    private static AdminController adminController;
    private static Set<Index> indexes;
    private static Set<Course> courses;
    private static Scanner scan;
    private static InputScanner is;

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

    public static void adminLogin() {
        boolean success = false;
        adminController = new AdminController();
        success = adminController.init();
        if (!success) {
            return;
        }
        adminMain();
    }

    public static void studentLogin() {
        boolean success = false;
        studentController = new StudentController();
        success = studentController.init(indexes);
        if (!success) {
            return;
        }
        studentMain();
    }

    public static void adminMain() {
        
        int choice = 0;
        while (choice != 7) {
            System.out.println();
            System.out.println("Please select one of the functions: ");
            System.out.println("1. Edit Student Access Period");
            System.out.println("2. Add Student");
            System.out.println("3. Add/Update A Course");
            System.out.println("4. Check Available Slot For An Index Number");
            System.out.println("5. Print Student List By Index Number");
            System.out.println("6. Print Student List By Course");
            System.out.println("7. Exit");
            System.out.printf("Your choice: ");
            choice = is.nextInt(1, 8);

            switch (choice) {
                case 1:
                    adminController.editAccessPeriod();
                    break;

                case 2:
                    adminController.addStudent();
                    break;

                case 3:
                    adminController.addUpdateCourse(courses, indexes);
                    break;

                case 4:
                    adminController.checkVacancy(indexes);
                    break;

                case 5:
                    adminController.printByIndex(indexes);
                    break;

                case 6:
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

    public static void studentMain() {
        
        int choice = 0;
        while (choice != 7) {
            // print basic student information
            System.out.println();
            studentController.printModelDetail();
            System.out.println("Please select one of the functions: ");
            System.out.println("1. Add Course");
            System.out.println("2. Drop Course");
            System.out.println("3. Check/Print Courses Registered and on Wait list");
            System.out.println("4. Change Index Number of Course");
            System.out.println("5. Swap Index Number with Another Student");
            System.out.println("6. Check Vacancies Available");
            System.out.println("7. Exit");
            System.out.printf("Your choice: ");
            choice = is.nextInt(1, 8);

            switch (choice) {

                case 1:
                    studentController.addCourse(indexes);
                    break;

                case 2:
                    studentController.dropCourse(indexes);
                    break;

                case 3:
                    studentController.printCoursesRegistered();
                    studentController.printOnWaitlist();
                    break;

                case 4:
                    studentController.changeIndex(indexes);
                    break;

                case 5:
                    studentController.swapIndex(indexes);
                    break;

                case 6:
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
