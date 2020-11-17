import java.util.Queue;
import java.util.Set;
import java.util.LinkedList;
import java.util.Scanner;
import java.util.Calendar;

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
    public boolean login(String account, String password) {
        String hashedPassword = hash(password);
        Queue<String> allPasswords = model.readPasswords();
        String currentRecord;
        String[] currentTuple = new String[2];
        boolean success = false;
        currentRecord = allPasswords.poll();
        while (currentRecord != null && !success) {
            currentTuple = currentRecord.split(",");
            if (currentTuple[0].equals(account) && currentTuple[1].equals(hashedPassword)) {
                System.out.println("Logged in successfully.");
                System.out.println("Current account: " + account);
                success = true;
            }
            currentRecord = allPasswords.poll();
        }
        if (!success) {
            System.out.println("Login failed.");
            System.out.println("Check account and password and try again.");
            System.out.println("Or press enter to quit");
            return success;
        }
        success = readStudent(account);
        if (!success) {
            System.out.println("Student information not found");
            System.out.println("Try another account or contact admin for help");
            return success;
        }
        success = checkTime();
        if (!success) {
            System.out.println("Try access the system at your assigned access time");
            return success;
        }
        return success;
    }

    public boolean readStudent(String account) {
        String[] modelInfo = model.readInfo(account);
        if (modelInfo == null)
            return false;
        model.setName(modelInfo[1]);
        model.setNationality(modelInfo[2]);
        model.setMatricNo(modelInfo[3]);
        model.setMajor(modelInfo[4]);
        model.setYear(modelInfo[5]);
        // TODO: course and index initialize
        return true;
    }

    public boolean checkTime() {
        String index = model.getMajor() + model.getYear();
        String[] accessTime = model.readTime(index);
        if (accessTime == null) {
            System.out.println("Your group is not assigned access time yet");
        }
        Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH) + 1;
        int day = cal.get(Calendar.DAY_OF_MONTH);
        int date = year * 10000 + month * 100 + day;
        int hour = cal.get(Calendar.HOUR_OF_DAY);
        int minute = cal.get(Calendar.MINUTE);
        int time = hour * 100 + minute;
        boolean success = (
            Integer.parseInt(accessTime[1]) < date && 
            Integer.parseInt(accessTime[2]) > date && 
            Integer.parseInt(accessTime[3]) < time && 
            Integer.parseInt(accessTime[4]) > time
            );
        if (!success) {
            System.out.println("Your assigned time is: ");
            System.out.printf("%s:%s - %s:%s from %s/%s/%s to %s/%s/%s\n", 
                accessTime[3].substring(0, 2), 
                accessTime[3].substring(2, 4),
                accessTime[4].substring(0, 2),
                accessTime[4].substring(2, 4),
                accessTime[1].substring(6, 8),
                accessTime[1].substring(4, 6),
                accessTime[1].substring(0, 4),
                accessTime[2].substring(6, 8),
                accessTime[2].substring(4, 6),
                accessTime[2].substring(0, 4)
                );
        }
        return success;
    }


    private String hash(String pwd) {
        return pwd;
    }

    public void readInfo(){
        return;
    }

    public void dropCourse() {}
    //     System.out.println("Please choose the index to drop from below: ");

    //     // print the list of registered indexes
    //     System.out.println("Registered Courses:");
    //     printCoursesRegistered();

    //     // print list of indexes on WaitList        
    //     System.out.println("Courses on WaitList: ");
    //     printOnWaitList();

    //     System.out.println("please choose type of course to drop:");
    //     System.out.println("1. Registered Course");
    //     System.out.println("2. Course on WaitList");
    //     int courseType = scan.nextInt();

    //     System.out.printf("Index: ");
    //     int dropIndex = scan.nextInt();

    //     switch(dropIndex){
    //         case 1:
    //         // get the index object
    //         Index i1 = model.getCurrentIndexes(dropIndex);
    //         model.removeCurrentIndexes(i1);
    //         // remove student from studentlist in index
    //         i1.removeStudent(model.getMatricNo());

    //         // if there are students on WaitList
    //         if(i1.getWaitListLength() > 0){
    //             String matricNo = i1.removeWaitlist();
    //             i1.addStudent(matricNo);
    //         }
    //         else{
    //             i1.setVacancy(i1.getVacancy() + 1);
    //         }
    //         break;

    //         case 2:
    //         // get the index object
    //         Index i2 = model.getonWaitList(dropIndex);
    //         // remove index from onWaitList
    //         model.removeOnWaitList(i2);
    //         // remove student from WaitList queue in index
    //         i2.removeWaitList(model.getMatricNo());

    //     }
    //     System.out.println(dropIndex + "is successfully dropped");
    // }

    // public void printCoursesRegistered() {
    //     view.printCoursesRegistered(model.getCurrentIndexes());
    // }

    // public void printOnWaitList(){
    //     view.printOnWaitList(model.getOnWaitList());
    // }

////// this is written in index class
    // public void checkVacancy(Course c) {}

    // public void changeIndex(Course c, int idx) {
    

    // public void swapIndex(Course c, Student s) {}

    // public void reclassify(Course c, String newType) {}
}