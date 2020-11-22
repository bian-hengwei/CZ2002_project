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
        String[] modelInfo = readInfo(account, 0);
        if (modelInfo == null)
            return false;
        model.setAccount(modelInfo[0]);
        model.setName(modelInfo[1]);
        model.setNationality(modelInfo[2]);
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
        System.out.println();
        // save student tbd
        // invalid data entry
    }

    // 3
    public void addUpdateCourse(Set<Course> courses, Set<Index> indexes) {
        boolean quit = false;
        while (!quit) {
            System.out.println();
            System.out.println("Select an option: ");
            System.out.println("1. Add a new course");
            System.out.println("2. Modify an existing course");
            System.out.println("3. Exit");
            System.out.printf("Option: ");
            int option = scan.nextInt();
            scan.nextLine();
            String courseId;
            switch (option) {
                case 1:
                    System.out.printf("Course ID: ");
                    courseId = scan.nextLine();
                    if (!addCourse(courseId, courses))
                        break;
                    addUpdateIndex(courseId, courses, indexes);
                    break;

                case 2:
                    System.out.printf("Course ID: ");
                    courseId = scan.nextLine();
                    if (!updateCourse(courseId, courses))
                        break;
                    addUpdateIndex(courseId, courses, indexes);
                    break;

                case 3:
                    quit = true;
                    break;

                default:
                    System.out.println("Invalid option");
            }
        }
    }

    private boolean addCourse(String courseId, Set<Course> courses) {
        for (Course c: courses) {
            if (c.getCourseId().equals(courseId)) {
                System.out.println("Error: current course already exists");
                return false;
            }
        }
        Course newCourse = new Course(courseId);
        System.out.printf("School of the course: ");
        newCourse.setSchool(scan.nextLine());
        courses.add(newCourse);
        return true;
    }

    private boolean updateCourse(String courseId, Set<Course> courses) {
        Course course = null;
        for (Course c: courses) {
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
            System.out.println("2. Change school");
            System.out.println("3. Continue");
            System.out.printf("Option: ");
            int option = scan.nextInt();
            scan.nextLine();
            switch (option) {
                case 1:
                    System.out.printf("Course ID: ");
                    String newId = scan.nextLine();
                    course.setCourseId(newId);
                    break;

                case 2:
                    System.out.printf("School: ");
                    String school = scan.nextLine();
                    course.setSchool(school);
                    break;

                case 3:
                    quit = true;
                    break;

                default:
                    System.out.println("Invalid option");
            }
        }
        return true;
    }

    private void addUpdateIndex(String courseId, Set<Course> courses, Set<Index> indexes) {
        Course course = null;
        for (Course c: courses) {
            if (c.getCourseId().equals(courseId)) {
                course = c;
            }
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
            int option = scan.nextInt();
            scan.nextLine();
            switch (option) {
                case 1:
                    if (course.size() == 0) {
                        System.out.println("No index added");
                        break;
                    }
                    for (Index i: course.getIndexes())
                        System.out.printf(i.getIndexNumber() + " ");
                    System.out.println();
                    break;

                case 2:
                    System.out.printf("Index number: ");
                    indexNo = scan.nextInt();
                    scan.nextLine();
                    if (indexes.contains(indexNo)) {
                        System.out.println("Index already exists");
                    } else {
                        Index idxAdd = new Index(indexNo);
                        course.addIndex(idxAdd);
                        indexes.add(idxAdd);
                        System.out.println("Index added");
                        IndexController iControlAdd = new IndexController(idxAdd);
                        iControlAdd.editIndex(indexes);
                    }
                    break;

                case 3:
                    System.out.printf("Index number: ");
                    indexNo = scan.nextInt();
                    scan.nextLine();
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
                    System.out.printf("Index number: ");
                    indexNo = scan.nextInt();
                    scan.nextLine();
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

    public void printByIndex(Index index) {
        // create index controller for the index and print
        IndexController ic = new IndexController(index);
        ic.printStudents();
    }

    public void printByIndex(Set<Index> indexes) {
        // get index number
        Index index = new Index();
        printByIndex(index);
    }

    public void printByCourse(Set<Course> courses) {

        // prompts for course id input
        System.out.printf("Enter the course Id: ");
        String id = scan.nextLine();

        // loop through all courses to find any match
        Course course = null;
        for (Course c: courses) {
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

        // loop through all indexes in the course and print accordingly
        for (Index idx: course.getIndexes()) {
            printByIndex(idx);
        }
    }

}
