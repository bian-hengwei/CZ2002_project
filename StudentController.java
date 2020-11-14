import java.util.Queue;
import java.util.Set;
import java.util.LinkedList;

public class StudentController{

	private Student model;
	private StudentView view;


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

    public void dropCourse(Index index) {
        model.removeCurrentIndex(index);
    }

    public void printCoursesRegistered() {
        view.printCoursesRegistered(Set<Index> model.getCurrentIndexes());
    }

////// this is written in index class
    // public void checkVacancy(Course c) {}

    // public void changeIndex(Course c, int idx) {
    
}

    // public void swapIndex(Course c, Student s) {}

    // public void reclassify(Course c, String newType) {}
}