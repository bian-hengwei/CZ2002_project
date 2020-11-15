import java.util.Queue;
import java.util.Set;
import java.util.LinkedList;
import java.util.Scanner;

public class StudentController{

	private Student model;
	private StudentView view;
    Scanner scan = new Scanner(System.in);


/*  should constructor be sth like this??? the students and student view should have some parameters

    private Index model;
    private IndexView view;

    public IndexController(Index model, IndexView view){
        this.model = model;
        this.view = view;
    }
*/
/*
    public StudentController(Student model, StudentView view){
        this.model = model;
        this.view = view;
    }
*/
    public StudentController() {
        model = new Student();
        view = new StudentView();
    }

//  here the parameter could be String model.getMatricNo()???
    public void login(String account, String password) {
        String hashedPassword = hash(password);
        Queue<String> allPasswords = model.readPasswords(account);
        String currentRecord;
        String[] currentTuple = new String[2];
        boolean success = false;
        currentRecord = allPasswords.poll();
        while (currentRecord != null && !success) {
            currentTuple = currentRecord.split(",");
            if (currentTuple[0].equals(account) && currentTuple[1].equals(hashedPassword)) {
                System.out.println("Login success.");
                System.out.println("Current account: " + account);
                success = true;
            }
            currentRecord = allPasswords.poll();
        }
        if (!success) {
            System.out.println("Login failed.");
            System.out.println("Check account and password and try again.");
            return;
        }
    }

    private String hash(String pwd) {
        return pwd;
    }

///////probably need to write the admin class first?
    // public boolean compareTime(/*start datetime, end datetime, current datetime*/){
    //     // return if student can access the system
    //     return true;
    // }

    public void readInfo(){
        return;
    }

    // private String hash(String pwd) { return ""; }

    // public void addCourse(Course c, int index){
    //     Index idx = c.getIndex(index);
    //     int courseAU = c.getAU();
    //     if (currentCourses.contains(c)){
    //         System.out.println("Current course already registered.");
    //     }
    //     else if (courseAU + currentAU > 21){
    //         System.out.println("Exceeding maximum workload! This course cannot be added.");
    //     }
    //     else if (c.getVacancy(index) > 0){
    //         c.registerStudent(index);
    //         System.out.println("Successfully registered to course " + c.getId());
    //         currentCourses.add(c);
    //     }
    //     else{
    //         c.waitlist(matricNo, index);
    //         System.out.println("Current index full. Putting on wait list...");
    //     }
    // }

    public void dropCourse() {
        System.out.println("Please choose the index to drop from below: ");

        // print the list of registered indexes
        System.out.println("Registered Courses:");
        printCoursesRegistered();

        // print list of indexes on waitlist        
        System.out.println("Courses on waitlist: ");
        printOnWaitlist();

        System.out.println("please choose type of course to drop:");
        System.out.println("1. Registered Course");
        System.out.println("2. Course on Waitlist");
        int courseType = scan.nextInt();

        System.out.printf("Index: ");
        int dropIndex = scan.nextInt();

        switch(dropIndex){
            case 1:
            // get the index object
            Index i1 = model.getCurrentIndexes(dropIndex);
            model.removeCurrentIndexes(i1);
            // remove student from studentlist in index
            i1.removeStudent(model.getMatricNo());

            // if there are students on waitlist
            if(i1.getWaitListLength() > 0){
                String matricNo = i1.removeWaitlist();
                i1.addStudent(matricNo);
            }
            else{
                i1.setVacancy(i1.getVacancy() + 1);
            }
            break;

            case 2:
            // get the index object
            Index i2 = model.getOnWaitlist(dropIndex);
            // remove index from onwaitlist
            model.removeOnWaitlist(i2);
            // remove student from waitlist queue in index
            i2.removeWaitlist(model.getMatricNo());

        }
        System.out.println(dropIndex + "is successfully dropped");
    }

    public void printCoursesRegistered() {
        view.printCoursesRegistered(model.getCurrentIndexes());
    }

    public void printOnWaitlist(){
        view.printOnWaitlist(model.getOnWaitlist());
    }

////// this is written in index class
    // public void checkVacancy(Course c) {}

    // public void changeIndex(Course c, int idx) {
    

    // public void swapIndex(Course c, Student s) {}

    // public void reclassify(Course c, String newType) {}
}