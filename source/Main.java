import java.io.Console;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;

public class Main {

    private static StudentController studentController;
    private static AdminController adminController;
    private static Set<Index> indexes;
    private static Set<Course> courses;
    private static Scanner scan;

    public static void main(String[] args) {

        indexes = new HashSet<Index>();
        courses = new HashSet<Course>();
        readIndexes();

        scan = new Scanner(System.in);

        boolean quit = false;
        while(!quit) {
            System.out.println();
            System.out.println("--------------------------------------------");
            System.out.println("| My STudent Automated Registration System |");
            System.out.println("--------------------------------------------");
            System.out.println("Please choose type of login: ");
            System.out.println("1. Admin login");
            System.out.println("2. Student login");
            System.out.println("3. Quit");
            System.out.print("Your choice: ");
            int login = scan.nextInt();
            scan.nextLine();
            switch(login) {
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
        }
        //saveIndexes();
        scan.close();
    }

    public static void readIndexes() {
        Set<String> indexesString = FileHandler.readToSet("index_information");
        for (String line: indexesString) {
            String[] indexInfoArray = line.split(",");
            Index idx = new Index();
            idx.setCourseId(indexInfoArray[0]);
            idx.setCourseName(indexInfoArray[1]);
            idx.setSchool(indexInfoArray[2]);
            idx.setAu(Integer.parseInt(indexInfoArray[3]));
            idx.setIndexNumber(Integer.parseInt(indexInfoArray[4]));
            idx.setVacancy(Integer.parseInt(indexInfoArray[5]));
            idx.setLectureTime(indexInfoArray[6]);
            idx.setLectureVenue(indexInfoArray[7]);
            idx.setExamTime(indexInfoArray[8]);
            idx.setExamVenue(indexInfoArray[9]);
            idx.setTutorialTime(indexInfoArray[10]);
            idx.setTutorialVenue(indexInfoArray[11]);
            idx.setLabTime(indexInfoArray[12]);
            idx.setLabVenue(indexInfoArray[13]);
            idx.setWaitlist(indexInfoArray[14]);
            idx.setStudentList(indexInfoArray[15]);

            boolean added = false;
            for (Course c: courses) {
                if (c.getCourseId().equals(idx.getCourseId())) {
                    c.addIndex(idx);
                    added = true;
                    break;
                }
            }
            if (!added) {
                Course course = new Course(idx.getCourseId());
                course.addIndex(idx);
                courses.add(course);
            }
            indexes.add(idx);
        }
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

    public static void adminLogin() {
        boolean success = false;
        adminController = new AdminController();
        success = adminController.init();
        if (!success) {
            return;
        }
        adminMain();
    }

    public static void adminMain() {
        int choice = 0;

        while(choice != 7) {
            System.out.println("Please select one of the functions: ");
            System.out.println("1. Edit Student Access Period");
            System.out.println("2. Add Student");
            System.out.println("3. Add/Update A Course");
            System.out.println("4. Check Available Slot For An Index Number");
            System.out.println("5. Print Student List By Index Number");
            System.out.println("6. Print Student List By Course");
            System.out.println("7. Exit");
            System.out.print("Your choice: ");
            choice = scan.nextInt();
            scan.nextLine();

            switch(choice) {
                case 1:
                    adminController.editAccessPeriod();
                    break;

                case 2:
                    adminController.addStudent();
                    break;

                default:
                    System.out.println("TBD");
                    break;
            }
        }
    }

    public static void studentMain() {
        int choice = 0;

        while(choice != 7) {
            System.out.println("Please select one of the functions: ");
            System.out.println("1. Add Course");
            System.out.println("2. Drop Course");
            System.out.println("3. Check/Print Courses Registered and on Wait list");
            System.out.println("4. Change Index Number of Course");
            System.out.println("5. Swap Index Number with Another Student");
            System.out.println("6. Check Vacancies Available");
            System.out.println("7. Exit");
            System.out.print("Your choice: ");
            choice = scan.nextInt();
            scan.nextLine();

            switch(choice) {

                default:
                    System.out.println("Please choose an option.");
                    break;

                case 1:
                    studentController.addCourse(indexes);
                    break;

                case 2:
                    studentController.dropCourse();
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
            }
        }
    }

    public static void saveIndexes() {
        StringBuilder sb = new StringBuilder();
        sb.append("id,name,school,au,index,vacancy,lectureTime,lectureVenue,examTime,");
        sb.append("examVenue,tutorialTime,tutorialVenue,labTime,labVenue,waitlist,studentList,;\n");
        for (Index idx: indexes) {
            String lectureTime = String.join("&", idx.getLectureTime());
            String lectureVenue = String.join("&", idx.getLectureVenue());
            String waitlist = String.join("&", idx.getWaitList());
            String studentList = String.join("&", idx.getStudentList());
            sb.append(String.format("%s,%s,%s,%d,%d,%d,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,;\n", 
                idx.getCourseId(), idx.getCourseName(), idx.getSchool(), 
                idx.getAu(), idx.getIndexNumber(), idx.getVacancy(), 
                lectureTime, lectureVenue, idx.getExamTime(), 
                idx.getExamVenue(), idx.getTutorialTime(), idx.getTutorialVenue(), 
                idx.getLabTime(), idx.getLabVenue(), waitlist, studentList)
            );
        }
        FileHandler.save("index_information", sb.toString());
        System.out.println("Indexes successfully saved.");
    }

}