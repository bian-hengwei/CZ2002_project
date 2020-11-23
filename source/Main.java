import java.util.HashSet;
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

        readCourses();
        readIndexes();

        scan = new Scanner(System.in);

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
            int login = scan.nextInt();
            scan.nextLine();
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
            saveCourses();
            saveIndexes();
        }
        saveCourses();
        saveIndexes();
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
            choice = scan.nextInt();
            scan.nextLine();

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
            choice = scan.nextInt();
            scan.nextLine();

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
                    System.out.println("Saving student...");
                    break;

                default:
                    System.out.println("Invalid option");
                    break;
            }
        }
    }

    public static void readCourses() {
        Set<String> coursesString = FileHandler.readToSet("course_information");
        for (String line: coursesString) {
            String[] indexInfoArray = line.split(",");
            Course course = new Course();
            course.setCourseId(indexInfoArray[0]);
            course.setCourseName(indexInfoArray[1]);
            course.setSchool(indexInfoArray[2]);
            course.setAu(Integer.parseInt(indexInfoArray[3]));
            course.setLectureTime(indexInfoArray[4]);
            course.setLectureVenue(indexInfoArray[5]);
            course.setExamTime(indexInfoArray[6]);
            course.setExamVenue(indexInfoArray[7]);
            courses.add(course);
        }
    }

    public static void readIndexes() {
        Set<String> indexesString = FileHandler.readToSet("index_information");
        for (String line: indexesString) {
            String[] indexInfoArray = line.split(",");
            Index idx = new Index();
            idx.setIndexNumber(Integer.parseInt(indexInfoArray[1]));
            idx.setVacancy(Integer.parseInt(indexInfoArray[2]));
            idx.setTutorialTime(indexInfoArray[3]);
            idx.setTutorialVenue(indexInfoArray[4]);
            idx.setLabTime(indexInfoArray[5]);
            idx.setLabVenue(indexInfoArray[6]);
            idx.setWaitlist(indexInfoArray[7]);
            idx.setStudentList(indexInfoArray[8]);

            for (Course c: courses) {
                if (c.getCourseId().equals(indexInfoArray[0])) {
                    c.addIndex(idx);
                    idx.setCourse(c);
                    break;
                }
            }
            indexes.add(idx);
        }
    }

    public static void saveIndexes() {
        StringBuilder sb = new StringBuilder();
        sb.append("id,index,vacancy,");
        sb.append("examVenue,tutorialTime,tutorialVenue,labTime,labVenue,waitlist,studentList,;\n");
        for (Index idx: indexes) {
            String waitlist = String.join("&", idx.getWaitList());
            String studentList = String.join("&", idx.getStudentList());
            sb.append(String.format("%s,%d,%d,%s,%s,%s,%s,%s,%s,;\n", 
                idx.getCourseId(), idx.getIndexNumber(), idx.getVacancy(), 
                idx.getTutorialTime(), idx.getTutorialVenue(), 
                idx.getLabTime(), idx.getLabVenue(), waitlist, studentList)
            );
        }
        FileHandler.save("index_information", sb.toString());
        System.out.println("Indexes successfully saved.");
    }

    public static void saveCourses() {
        StringBuilder sb = new StringBuilder();
        sb.append("courseId,name,school,au,lectureTime,lectureVenue,examTime,examVenue,;\n");
        for (Course c: courses) {
            String lectureTime = String.join("&", c.getLectureTime());
            String lectureVenue = String.join("&", c.getLectureVenue());
            sb.append(String.format("%s,%s,%s,%d,%s,%s,%s,%s,;\n", 
                c.getCourseId(), c.getCourseName(), c.getSchool(), 
                c.getAu(), lectureTime, lectureVenue, c.getExamTime(), 
                c.getExamVenue())
            );
        }
        FileHandler.save("course_information", sb.toString());
        System.out.println("Courses successfully saved.");
    }
}