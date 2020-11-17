import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Queue;
import java.util.Set;
import java.util.LinkedList;
import java.util.Calendar;
import java.util.Scanner;
import java.util.Iterator;

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
    public boolean login(String account, String password, Set<Index> indexes) {
        Queue<String> allPasswords = model.readPasswords();
        String currentRecord;
        boolean success = false;
        currentRecord = allPasswords.poll();
        while (currentRecord != null && !success) {
            String[] currentTuple = currentRecord.split(",");
            //byte[] salt = currentTuple[3].getBytes();
            //byte[] salt = getSalt();
            String hashedPassword = hash(password);
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
        success = readStudent(account, indexes);
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

    public boolean readStudent(String account, Set<Index> indexes) {
        String[] modelInfo = model.readInfo(account);
        if (modelInfo == null)
            return false;
        model.setName(modelInfo[1]);
        model.setNationality(modelInfo[2]);
        model.setMatricNo(modelInfo[3]);
        model.setMajor(modelInfo[4]);
        model.setYear(modelInfo[5]);
        // TODO: course and index initialize
        for (String taken: modelInfo[6].split("&")) {
            System.out.println(taken);
            model.addTakenCourses(taken);
        }
        for (String taking: modelInfo[7].split("&")) {
            System.out.println(taking);
            if (taking.trim().equals(""))
                break;
            for (Index idx: indexes) {
                if (idx.getIndexNumber() == Integer.parseInt(taking))
                    model.addCurrentIndexes(idx);
            }
        }
        for (String wl: modelInfo[8].split("&")) {
            System.out.println(wl);
            if (wl.trim().equals(""))
                break;
            for (Index idx: indexes) {
                if (idx.getIndexNumber() == Integer.parseInt(wl))
                    model.addOnWaitlist(idx);
            }
        }
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


    private String hash(String password, byte[] salt) {
        String generatedPassword = null;
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-1");
            md.update(salt);
            byte[] bytes = md.digest(password.getBytes());
            StringBuilder sb = new StringBuilder();
            for(int i=0; i< bytes.length ;i++)
            {
                sb.append(Integer.toString((bytes[i] & 0xff) + 0x100, 16).substring(1));
            }
            generatedPassword = sb.toString();
        } catch(NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        System.out.println("generated password: " + generatedPassword);
        return generatedPassword;
    }

    private String hash(String pwd) {
        return pwd;
    }

    private byte[] getSalt() {
        System.out.println("Getting salt...");
        try {
            SecureRandom sr = SecureRandom.getInstance("SHA1PRNG");
            byte[] salt = new byte[16];
            sr.nextBytes(salt);
            System.out.println("Byte array: " + new String(salt));
            return salt;
        } catch(NoSuchAlgorithmException e) {
            System.out.println("NoSuchAlgorithmException");
            e.printStackTrace();
        }
        return null;
    }

// 1
    public void addCourse(Set<Index> indexes){
        System.out.println("Please enter the index you want to add: ");
        int i = scan.nextInt();
        Index index;
        for (Index idx : indexes){
            if(idx.getIndexNumber() == i){
                index = idx;
                if(index.getVacancy() != 0){
                    // modify index
                    index.addStudent(model.getMatricNo());
                    index.setVacancy(index.getVacancy() - 1);
                    // modify student
                    model.addCurrentIndexes(index);
                    System.out.println("You have successfully added index " + i);

                }

                else{
                    // put into waitlist of index
                    index.addWaitlist(model.getMatricNo());
                    // put index on student onWaitlist
                    model.addOnWaitlist(index);
                    System.out.println("You have been successfully added onto waitlist of index " + i);
                }
                break;
            }
        }
    }

// 2
    public void dropCourse() {
        System.out.println("Please choose the index to drop from below: ");

        // print the list of registered indexes
        System.out.println("Registered Courses:");
        printCoursesRegistered();

        // print list of indexes on WaitList        
        System.out.println("Courses on WaitList: ");
        printOnWaitlist();

        System.out.println("please choose type of course to drop:");
        System.out.println("1. Registered Course");
        System.out.println("2. Course on WaitList");
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

            // if there are students on WaitList
            if(i1.getWaitListLength() > 0){
                String matricNo = i1.removeWaitlist();
                i1.addStudent(matricNo);
                //// add email here/////
            }
            else{
                i1.setVacancy(i1.getVacancy() + 1);
            }
            break;

            case 2:
            // get the index object
            Index i2 = model.getOnWaitlist(dropIndex);
            // remove index from onWaitList
            model.removeOnWaitlist(i2);
            // remove student from WaitList queue in index
            i2.removeWaitlist(model.getMatricNo());

        }
        System.out.println(dropIndex + "is successfully dropped");
    }

// 3
    public void printCoursesRegistered() {
        view.printCoursesRegistered(model.getCurrentIndexes());
    }

    public void printOnWaitlist(){
        view.printOnWaitlist(model.getOnWaitlist());
    }

// 4
    // this is written in index class
    public void checkVacancy(Set<Index> indexes) {
        String check = "y";
        int i;
        Index index;
        while(check == "y"){
            System.out.println("please enter the index you want to check: ");
            i = scan.nextInt();
            Iterator<Index> iterate = indexes.iterator();
            for (Index idx : indexes){
                if(idx.getIndexNumber() == i){
                    index = idx;
                    IndexView view = new IndexView();
                    IndexController indexController = new IndexController(index, view);
                    indexController.printVacancy();
                    break;
                }
            }
            System.out.println("Would you like to check vacancy for another index? (please enter y/n)");
            check = scan.next();
        }
    }

    public void changeIndex(Set<Index> indexes) {
        /*logic:
          1. input current index and new index
          2. check if they are the same course using getCourseId if yes:
          3. check new course vacancy if yes:
          4. drop current, add new
          5. if no: invalid, loop again*/

        int curIndex;
        int nIndex;
        Index currentIndex = new Index();
        Index newIndex = new Index();
        Boolean curFound = false;
        Boolean nFound = false;

        System.out.println("---------- Change Index -----------");
        System.out.println("Please enter current index: ");
        curIndex = scan.nextInt();
        System.out.println("Please enter new index: ");
        nIndex = scan.nextInt();
        for(Index idx1 : model.getCurrentIndexes()){
            if(idx1.getIndexNumber() == curIndex){
                currentIndex = idx1;
                curFound = true;
                break;
            }
        }

        for(Index idx2 : indexes){
            if(idx2.getIndexNumber() == nIndex){
                newIndex = idx2;
                nFound = true;
                break;
            }
        }

        if(!curFound){
            System.out.println("You have not registered the current index you entered.");
        }
        else if(!nFound){
            System.out.println("The new index you entered is not valid.");
        }
        else if(currentIndex.getCourseId() != newIndex.getCourseId()){
            System.out.println("The indexes you entered are not from the same course.");
        }
        else{
            if(newIndex.getVacancy() > 0){
                // confirm to change
                IndexView indexView = new IndexView();
                IndexController currentIndexController = new IndexController(currentIndex, indexView);
                IndexController newIndexController = new IndexController(newIndex, indexView);
                System.out.println("Current Index Information: ");
                currentIndexController.printIndexDetail();
                System.out.println();
                System.out.println("New Index Information");
                newIndexController.printIndexDetail();
                System.out.println();
                System.out.println("Please enter y to confirm the change, enter n to cancel.");
                String confirm = scan.next();

                if(confirm == "y"){
                    // drop currentIndex
                    model.removeCurrentIndexes(currentIndex);
                    // remove student from studentlist in index
                    currentIndex.removeStudent(model.getMatricNo());
                    // if there are students on waitlist for currentIndex, register the student at head of queue
                    if(currentIndex.getWaitListLength() > 0){
                        String matricNo = currentIndex.removeWaitlist();
                        currentIndex.addStudent(matricNo);
                    }
                    // else, vacancy of currentCourse + 1
                    else{
                        currentIndex.setVacancy(currentIndex.getVacancy() + 1);
                    }

                    // add newIndex
                    newIndex.addStudent(model.getMatricNo());
                    newIndex.setVacancy(newIndex.getVacancy() - 1);
                    // modify student
                    model.addCurrentIndexes(newIndex);
                    System.out.println("You have successfully changed from index " + currentIndex + " to index " + newIndex);
                }
                else{
                    System.out.println("Changing of indexes cancelled.");
                }
            }
            else{
                System.out.println("The new Index do not have vacancy.");
            }
        }
    }
    

    public void swapIndex(Course c, Student s) {}

    public void reclassify(Course c, String newType) {}
}













