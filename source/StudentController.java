import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Calendar;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Set;
import java.util.Scanner;

public class StudentController{

	private Student model;
	private StudentView view;

    Scanner scan = new Scanner(System.in);

    public StudentController() {
        model = new Student();
        view = new StudentView();
    }

    public Student getModel(){
        return model;
    }

    public StudentView getView(){
        return view;
    }

    public boolean login(String account, String password, Set<Index> indexes) {
        Set<String> allPasswords = model.readPasswords();
        boolean success = false;
        for (String currentRecord: allPasswords) {
            String[] currentTuple = currentRecord.split(",");
            byte[] salt = hexStrToByteArr(currentTuple[1]);
            String hashedPassword = hash(password, salt);
            if (currentTuple[0].equals(account) && currentTuple[2].equals(hashedPassword)) {
                System.out.println("Logged in successfully.");
                System.out.println("Current account: " + account);
                success = true;
                break;
            }
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
            model.addTakenCourses(taken);
        }
        for (String taking: modelInfo[7].split("&")) {
            if (taking.trim().equals(""))
                break;
            for (Index idx: indexes) {
                if (idx.getIndexNumber() == Integer.parseInt(taking))
                    model.addCurrentIndexes(idx);
            }
        }
        for (String wl: modelInfo[8].split("&")) {
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

    // https://howtodoinjava.com/java/java-security/how-to-generate-secure-password-hash-md5-sha-pbkdf2-bcrypt-examples/#:~:text=PBKDF2%2C%20BCrypt%2C%20SCrypt-,Java%20Secure%20Hashing%20%E2%80%93%20MD5%2C%20SHA256%2C%20SHA512%2C%20PBKDF2%2C,weak%20and%20easy%20to%20guess.
    private String hash(String password, byte[] salt) {
        String generatedPassword = null;
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-512");
            md.update(salt);
            byte[] bytes = md.digest(password.getBytes());
            generatedPassword = byteArrToHexStr(bytes);
        } catch(NoSuchAlgorithmException e) {
            System.out.println("NoSuchAlgorithmException");
            e.printStackTrace();
        }
        return generatedPassword;
    }

    // https://www.tutorialspoint.com/convert-hex-string-to-byte-array-in-java#:~:text=To%20convert%20hex%20string%20to%20byte%20array%2C%20you%20need%20to,length%20of%20the%20byte%20array.
    private String byteArrToHexStr(byte[] bytes) {
        StringBuilder sb = new StringBuilder();
        for(int i = 0; i < bytes.length; i++) {
            sb.append(Integer.toString((bytes[i] & 0xff) + 0x100, 16).substring(1));
        }
        return sb.toString();
    }

    private byte[] hexStrToByteArr(String hex) {
        byte[] bytes = new byte[hex.length() / 2];
        for (int i = 0; i < bytes.length; i++) {
            int index = i * 2;
            int j = Integer.parseInt(hex.substring(index, index + 2), 16);
            bytes[i] = (byte) j;
        }
        return bytes;
    }

    private byte[] getSalt() {
        try {
            SecureRandom sr = SecureRandom.getInstance("SHA1PRNG");
            byte[] salt = new byte[16];
            sr.nextBytes(salt);
            return salt;
        } catch(NoSuchAlgorithmException e) {
            System.out.println("NoSuchAlgorithmException");
            e.printStackTrace();
        }
        return null;
    }

// 1 /////////// add conditions
    public void addCourse(Set<Index> indexes){
        System.out.println("Please enter the index you want to add: ");
        int i = scan.nextInt();
        Index index = new Index();

        boolean indexFound = false;
        boolean courseTaken = false;
        boolean currentlyRegistered = false;
        for (Index idx : indexes){
            if(idx.getIndexNumber() == i){
                index = idx;
                indexFound = true;
                break;
            }
        }

        for(String idx : model.getTakenCourses()){
            if(idx.equals(index.getCourseId())){
                courseTaken = true;
            }
        }

        for(Index idx : model.getCurrentIndexes()){
            if(idx.getCourseId().equals(index.getCourseId())){
                currentlyRegistered = true;
            }
        }

        if(!indexFound){
            System.out.println("Index entered is invalid.");
        }
        else if(model.getCurrentAu() + index.getAu() >= 21){
            System.out.println("Taking this course will result in you exceeding maximum AU of 22");
        }
        else if(courseTaken){
            System.out.println("You have taken this course before.");
        }
        // if this course is already registered
        else if(currentlyRegistered){
            System.out.println("This course is already registered");
        }
        else if(index.getVacancy() != 0){
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

        switch(courseType){
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
        System.out.println(dropIndex + " is successfully dropped");
    }

// 3 *****************
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
        while(check.equals("y")){
            System.out.println("please enter the index you want to check: ");
            i = scan.nextInt();
            scan.nextLine();
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
            check = scan.nextLine();
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
        else if(!currentIndex.getCourseId().equals(newIndex.getCourseId())){
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

                if(confirm.equals("y")){
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
                    System.out.println("You have successfully changed from index " + 
                        currentIndex.getIndexNumber() + " to index " + 
                        newIndex.getIndexNumber());
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
    

    public void swapIndex(Set<Index> indexes) {
        boolean success = false;
        int counter = 0;
        int peerIndex = -1;
        StudentController peer = new StudentController();
        System.out.println("Your index number: ");
        int myIndex = scan.nextInt();
        while(!success && counter < 3){
            System.out.println("Peer's username: ");
            String peerUsername = scan.next();
            System.out.println("Peer's password: ");
            String peerPassword = scan.next();
            System.out.println("Peer's index number: ");
            peerIndex = scan.nextInt();
            System.out.println("Trying peer's login...");
            success = peer.login(peerUsername, peerPassword, indexes);
            counter ++;
        }
        if(counter == 3 && !success){
            System.out.println("You have exceeded 3 tries.");
        }
        else{
            /* 1. check if 2 indexes are under same course
               2. check if they are really registered
               3. check time table clash (not yet)
               4. swap
            */

            Boolean valid = true;

            Index selfIndex = model.getCurrentIndexes(myIndex);
            Index otherIndex = peer.getModel().getCurrentIndexes(peerIndex);

            if(selfIndex == null){
                System.out.println("You are not registered for " + myIndex);
            }
            else if(otherIndex == null){
                System.out.println("Peer is not registered for " + peerIndex);
            }
            else if(!selfIndex.getCourseId().equals(otherIndex.getCourseId())){
                System.out.println("The two indexes entered are not from the same course.");
            }
            else{
                // student
                model.removeCurrentIndexes(selfIndex);
                peer.getModel().removeCurrentIndexes(otherIndex);

                model.addCurrentIndexes(otherIndex);
                peer.getModel().addCurrentIndexes(selfIndex);

                // index
                selfIndex.removeStudent(model.getMatricNo());
                otherIndex.removeStudent(peer.getModel().getMatricNo());

                selfIndex.addStudent(peer.getModel().getMatricNo());
                otherIndex.addStudent(model.getMatricNo());

                System.out.println("Indexes successfully switched");
            }
        }
    }
    
}


/*
1. add course
- check time clash no
- check exceed 22 au y
- check taken y 
- currently taking y
- email

2. drop course
- email

3. print courses registered

4. check vacancy

5. change index
- check clash
- send email

6. swap index
- email
- clash */












