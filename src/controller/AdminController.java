package src.controller;

import src.boundary.FileHandler;
import src.boundary.InputScanner;
import src.model.Admin;
import src.model.Course;
import src.model.Index;

import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.Scanner;

public class AdminController extends AccountController {

    private Admin model;
    private Scanner scan;
    private InputScanner is;

    /**
    * construct AdminController object.
    */
    public AdminController() {
        model = new Admin();
        scan = new Scanner(System.in);
        is = new InputScanner();
        super.setPrefix("admin");
    }

    // initialize methods

    /**
    * initialize admin object, check if admin is legit.
    * @return returns true if initialization is successful.
    */
    public boolean init() {
        boolean success = login();
        if (!success) {
            return success;
        }
        success = readAdmin(getAccount());
        if (!success) {
            System.out.println("Admin information not found");
            System.out.println("Please try another account");
            return success;
        }
        return success;
    }

    /**
    * read in admin information and update admin object details
    * @param account account of admin that need to be read.
    * @return return true if admin is read successfully, else, return false
    */
    public boolean readAdmin(String account) {
        String[] modelInfo = readInfo(account, 0);
        if (modelInfo == null)
            return false;
        model.setAccount(modelInfo[0]);
        model.setName(modelInfo[1]);
        model.setNationality(modelInfo[2]);
        return true;
    }

    // 1
    /**
    * let admin enter the school, year, start date, end date and the starting and ending time. update access_time.csv according to what admin entered.
    */
    public void editAccessPeriod() {
        System.out.println("You can set the access period for students from any school and any year");
        System.out.printf("School: ");
        String school = scan.nextLine().toUpperCase();
        System.out.printf("Year: ");
        int year = is.nextInt(1, 5);
        String index = String.format("%sY%d", school, year);
        System.out.printf("Start date (YYYYMMDD): ");
        String start = scan.nextLine();
        System.out.printf("End date (YYYYMMDD): ");
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
    /**
    * let admin add a new student account to system. checks if student already exists. If student do not exist, let admin enter student details. student details are then saves into student_information.csv file.
    */
   public void addStudent() {
        String[] accountInfo = promptForPassword();
        if (FileHandler.readRow("student_passwords", accountInfo[0]) != null) {
            System.out.println("Current student account already exists");
            System.out.println("Exiting...");
            return;
        }
        System.out.println("Student account does not exist");
        byte[] salt = getSalt();
        String saltString = byteArrToHexStr(salt);
        String hashed = hash(accountInfo[1], salt);
        String content = String.join(",", accountInfo[0], saltString, hashed, ";");
        System.out.println();
        // save student tbd
        // invalid data entry
        String name, nationality, matricNumber, major, year, gender = "";
        System.out.println("Please enter student information");
        System.out.printf("Student name: ");
        name = scan.nextLine();
        System.out.printf("Student nationality: ");
        nationality = scan.nextLine();
        System.out.printf("Student matric number: ");
        matricNumber = scan.nextLine();

        String[] schoolsList = new String[] {"SCBE", "CEE", "SCSE", "EEE", "MSE", 
                "MAE", "NBS", "ADM", "SOH", "SOSS", "WKWSCI", "SBS", "SPMS", "ASE", "LKCM"};
        List<String> schools = Arrays.asList(schoolsList);
        while (true) {
            System.out.printf("Student major (school): ");
            major = scan.nextLine().toUpperCase();
            if (schools.contains(major)) {
                break;
            }
            System.out.println("Invalid school name");
        }

        System.out.printf("Student year: Year ");
        int y = is.nextInt(1, 5);
        year = String.format("Y%d", y);

        while (!(gender.equals("M") && !(gender.equals("F")))) {
            System.out.printf("Student gender (M/F): ");
            gender = scan.nextLine().toUpperCase();
            if (!(gender.equals("M") && !(gender.equals("F")))) {
                System.out.println("Invalid gender");
            }
        }

        System.out.println("Initializing student...");
        StudentController sControl = new StudentController();
        sControl.readPersonalInfo(new String[]{accountInfo[0], name, nationality, matricNumber, major, year, gender});

        System.out.println("New student account generated");
        System.out.println("Saving student...");
        FileHandler.writeLine("student_passwords", accountInfo[0], content);
        sControl.saveStudentInfo();
        System.out.println("Student saved");
    }

    // 3

    /**
    * let admin add or update a course. admin could add a new course, modify indexes of existing course and modify other information of existing course.
    * @param courses all courses that are valid
    * @param indexes all indexes that are valid
    */
    public void addUpdateCourse(Set<Course> courses, Set<Index> indexes) {
        boolean quit = false;
        while (!quit) {
            System.out.println();
            System.out.println("Select an option: ");
            System.out.println("1. Add a new course");
            System.out.println("2. Modify indexes of existing course");
            System.out.println("3. Modify other information of existing course");
            System.out.println("4. Exit");
            System.out.printf("Option: ");
            int option = is.nextInt(1, 5);
            String courseId;
            switch (option) {
                case 1:
                    System.out.println();
                    System.out.println("----- Add a new course -----");
                    System.out.printf("Course ID: ");
                    courseId = scan.nextLine();
                    addCourse(courseId, courses);
                    break;

                case 2:
                    System.out.println();
                    System.out.println("----- Modify indexes of existing course -----");
                    System.out.printf("Course ID: ");
                    courseId = scan.nextLine();
                    addUpdateIndex(courseId, courses, indexes);
                    break;


                case 3:
                    System.out.println();
                    System.out.println("----- Modify other information of existing course -----");
                    System.out.printf("Course ID: ");
                    courseId = scan.nextLine();
                    updateCourse(courseId, courses);

                case 4:
                    quit = true;
                    break;

                default:
                    System.out.println("Invalid option");
            }
        }
    }

    /**
    * let admin add a new course that does not exist previously. if course is successfully added, admin is recommended to select option 2 and 3 to add in more details of the newly added course.
    * @param courseId course ID of the new course that admin want to add
    * @param courses all the courses that are valid, used to check if course ID admin entered already exist
    */
    private void addCourse(String courseId, Set<Course> courses) {
        for (Course c: courses) {
            if (c.getCourseId().equals(courseId)) {
                System.out.println("Error: this course already exists");
                return;
            }
        }
        Course newCourse = new Course(courseId);
        System.out.printf("Course name: ");
        newCourse.setCourseName(scan.nextLine());
        System.out.printf("School of the course: ");
        newCourse.setSchool(scan.nextLine());
        System.out.printf("AU: ");
        newCourse.setAu(is.nextInt(1, 10));
        System.out.println("Course successfully added, please select option 2 and 3 to add more details.");
        courses.add(newCourse);
    }

    /**
    * let admin update a course. admin could change course id, change course name, change au, change lecture time and change exam time. If courseId entered does not exist, error message will be printed.
    * @param courseId course ID of the existing course that admin want to update
    * @param courses all courses that are valid. used to check if the course ID admin entered exist.
    * @return returns true if course is successfully updated, else return false.
    */
    private boolean updateCourse(String courseId, Set<Course> courses) {
        Course course = null;
        for (Course c : courses) {
            if (c.getCourseId().equals(courseId)) {
                course = c;
            }
        }
        if (course == null) {
            System.out.println("Error: current course does not exist");
            return false;
        }
        boolean quit = false;
        while (!quit) {
            System.out.println();
            System.out.println("Select an option: ");
            System.out.println("1. Change course id");
            System.out.println("2. Change course name");
            System.out.println("3. Change school");
            System.out.println("4. Change AU");
            System.out.println("5. Change lecture time");
            System.out.println("6. Change exam time");            
            System.out.println("7. Continue");
            System.out.printf("Option: ");
            int option = is.nextInt(1, 8);
            switch (option) {

                case 1:
                    System.out.println();
                    System.out.println("----- Change course id -----");
                    System.out.printf("Course ID: ");
                    String newId = scan.nextLine();
                    course.setCourseId(newId);
                    break;

                case 2:
                    System.out.println();
                    System.out.println("----- Change course name -----");
                    System.out.printf("Course name: ");
                    String newName = scan.nextLine();
                    course.setCourseName(newName);
                    break;


                case 3:
                    System.out.println();
                    System.out.println("----- Change school -----");
                    System.out.printf("School: ");
                    String school = scan.nextLine();
                    course.setSchool(school);
                    break;

                case 4:
                    System.out.println();
                    System.out.println("----- Change AU -----");
                    System.out.printf("AU: ");
                    int au = is.nextInt(1, 10);
                    course.setAu(au);

                case 5:
                    System.out.println();
                    System.out.println("----- Change lecture time -----");
                    System.out.printf(String.join(" ", "Lecture Time:", "0.", course.getLectureTime()[0], 
                        "1.", course.getLectureTime()[1], "\n"));
                    System.out.println("Select index to edit (or empty lecture to delete)");
                    System.out.printf("Option: ");
                    int op = is.nextInt(0);
                    if (op != 0 && op != 1) {
                        System.out.println("Invalid option");
                        break;
                    }
                    System.out.printf("New lecture time (MON1030-1130): ");
                    course.setLectureTime(op, scan.nextLine());
                    System.out.printf("New lecture venue: ");
                    course.setLectureVenue(op, scan.nextLine());
                    System.out.println("Successfully saved");
                    break;

                case 6:
                    System.out.println();
                    System.out.println("----- Change exam time -----");
                    System.out.printf("New exam time (MMDDHHMM-HHMM): ");
                    course.setExamTime(scan.nextLine());
                    System.out.printf("New exam venue: ");
                    course.setExamVenue(scan.nextLine());
                    System.out.println("Successfully saved");
                    break;

                case 7:
                    quit = true;
                    break;

                default:
                    System.out.println("Invalid option");
            }
        }
        return true;
    }

    /**
    * let admin add or update an index. admin could display all indexes, add new index, modify existing index or remove current index. courseId will be checked to see if it exists, if not, error message will be displayed.
    * @param courseId course id of the course that admin want to change the index of.
    * @param courses all courses that are valid.
    * @param indexes all indexes that are valid.
    */
    private void addUpdateIndex(String courseId, Set<Course> courses, Set<Index> indexes) {
        Course course = null;
        for (Course c: courses) {
            if (c.getCourseId().equals(courseId)) {
                course = c;
            }
        }
        if (course == null) {
            System.out.println("Error: current course does not exist");
            return;
        }
        boolean quit = false;
        while (!quit) {
            System.out.println();
            System.out.println("Choose an option:");
            System.out.println("1. Display all indexes");
            System.out.println("2. Add new index");
            System.out.println("3. Modify existing index");
            System.out.println("4. Remove current index");
            System.out.println("5. Exit");
            System.out.printf("Option: ");
            int indexNo;
            int option = is.nextInt(1, 6);
            switch (option) {
                case 1:
                    System.out.println();
                    System.out.println("----- Display all indexes -----");
                    if (course.size() == 0) {
                        System.out.println("No index added");
                        break;
                    }
                    for (Index i: course.getIndexes())
                        System.out.printf(i.getIndexNumber() + " ");
                    System.out.println();
                    break;

                case 2:
                    System.out.println();
                    System.out.println("----- Add new index -----");
                    System.out.printf("Index number: ");
                    indexNo = is.nextInt(0);
                    if (indexes.contains(indexNo)) {
                        System.out.println("Index already exists");
                    } else {
                        Index idxAdd = new Index(indexNo);
                        course.addIndex(idxAdd);
                        indexes.add(idxAdd);
                        idxAdd.setCourse(course);
                        System.out.println("Index added");
                        IndexController iControlAdd = new IndexController(idxAdd);
                        iControlAdd.editIndex(indexes);
                    }
                    break;

                case 3:
                    System.out.println();
                    System.out.println("----- Modify existing index -----");
                    System.out.printf("Index number: ");
                    indexNo = is.nextInt(0);
                    boolean modify = false;
                    for (Index i: course.getIndexes()) {
                        if (i.getIndexNumber() == indexNo) {
                            modify = true;
                        }
                    }
                    if (!modify) {
                        System.out.println("Index not found");
                    } else {
                        Index idxModify = course.getIndex(indexNo);
                        IndexController iControlModify = new IndexController(idxModify);
                        iControlModify.editIndex(indexes);
                    }
                    break;

                case 4:
                    System.out.println();
                    System.out.println("----- Remove current index -----");
                    System.out.printf("Index number: ");
                    indexNo = is.nextInt(0);
                    boolean delete = false;
                    for (Index i: course.getIndexes()) {
                        if (i.getIndexNumber() == indexNo) {
                            delete = true;
                            course.removeIndex(indexNo);
                            indexes.remove(i);
                            System.out.println("Successfully deleted");
                            break;
                        }
                    }
                    if (!delete) {
                        System.out.println("Index not found");
                    }
                    break;

                case 5:
                    quit = true;
                    break;

                default:
                    System.out.println("Invalid option");
            }
        }

    }

    /**
    * print students that are registered for an index.
    * @param index index that students are registered for
    */
    public void printByIndex(Index index) {
        // create index controller for the index and print
        IndexController ic = new IndexController(index);
        ic.printStudents();
    }

    /**
    * calls printByIndex(Index index) for every index in the set. 
    * @param indexes set of indexes that need to be looped through
    */
    public void printByIndex(Set<Index> indexes) {
        // get index number
        System.out.printf("Enter the index Id: ");
        int id = is.nextInt(0);

        // loop through all courses to find any match
        Index idx = null;
        for (Index i : indexes) {
            if (i.getIndexNumber() == id) {
                idx = i;
                break;
            }
        }

        if (idx == null) {
            System.out.println("Index not found");
            return;
        }

        printByIndex(idx);
    }

    /**
    * print all indexes and students registered for the indexes of a course. checks if the course id entered is legit, if not, error message is printed.
    * @param courses all courses that are valid.
    */
    public void printByCourse(Set<Course> courses) {

        // prompts for course id input
        System.out.printf("Enter the course Id: ");
        String id = scan.nextLine();

        // loop through all courses to find any match
        Course course = null;
        for (Course c : courses) {
            if (c.getCourseId().equals(id)) {
                course = c;
                break;
            }
        }

        // ends if no matches found
        if (course == null) {
            System.out.println("Course id not found");
            return;
        }

        Set<Index> courseIndexes = course.getIndexes();

        // loop through all indexes in the course and print accordingly
        for (Index idx : courseIndexes) {
            printByIndex(idx);
        }
    }
}
