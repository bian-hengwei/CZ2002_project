package src;

import java.util.Calendar;
import java.util.Set;
import java.util.Scanner;

public class StudentController extends AccountController {

    private Student model;
    private StudentView view;
    private Scanner scan;
    private InputScanner is;

    public StudentController() {
        model = new Student();
        view = new StudentView();
        scan = new Scanner(System.in);
        is = new InputScanner();
        super.setPrefix("student");
    }

    // getters

    public Student getModel() {
        return model;
    }

    // initialize methods

    public boolean init(Set<Index> indexes) {
        boolean success = login();
        if (!success) {
            return success;
        }
        success = readStudent(getAccount(), indexes, 0);
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

    public boolean readStudent(String ref, Set<Index> indexes, int col) {
        String[] modelInfo = readInfo(ref, col);
        if (modelInfo == null)
            return false;
        readPersonalInfo(modelInfo);
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

    public void readPersonalInfo(String[] modelInfo) {
        model.setAccount(modelInfo[0]);
        model.setName(modelInfo[1]);
        model.setNationality(modelInfo[2]);
        model.setMatricNo(modelInfo[3]);
        model.setMajor(modelInfo[4]);
        model.setYear(modelInfo[5]);
    }

    public boolean checkTime() {
        String index = model.getMajor() + model.getYear();
        String[] accessTime = model.readTime(index);
        if (accessTime == null) {
            System.out.println("Your group is not assigned access time yet");
            return false;
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

    // student functions

    // 1
    public void addCourse(Set<Index> indexes) {
        System.out.println("Please enter the index you want to add: ");
        int i = is.nextInt(0);
        Index index = null;
        IndexController indexController = null;

        boolean indexFound = false;
        boolean currentlyRegistered = false;
        boolean timeClash = false;

        Index clashedIndex;

        for (Index idx : indexes) {
            if (idx.getIndexNumber() == i){
                index = idx;
                indexController = new IndexController(index);
                indexFound = true;
                break;
            }
        }

        if (!indexFound) {
            System.out.println("Index entered is invalid.");
            return;
        }


        if (model.getCurrentAu() + index.getAu() >= 21) {
            System.out.println("Taking this course will result in you exceeding maximum AU of 22");
            return;
        }

        if (model.getTakenCourses().contains(index.getCourseId())) {
            System.out.println("You have taken this course before.");
            return;
        }

        for (Index idx : model.getCurrentIndexes()) {
            if (idx.getCourseId().equals(index.getCourseId())) {
                currentlyRegistered = true;
                break;
            }
        }

        if (currentlyRegistered) {
            System.out.println("This course is already registered");
            return;
        }

        clashedIndex = indexController.timeClashWithSet(model.getCurrentIndexes());
        if (clashedIndex != null) {
            System.out.println("This Index has a time clash with " + clashedIndex.getCourseId() + 
                               " which you are currently registered");
            return;
        }

        clashedIndex = indexController.timeClashWithSet(model.getOnWaitlist());
        if (clashedIndex != null) {
            System.out.println("This Index has a time clash with " + clashedIndex.getCourseId() + 
                               " which you are currently on waitlist");
            return;
        }

        if (index.getVacancy() != 0) {
            index.addStudent(model.getMatricNo());
            index.setVacancy(index.getVacancy() - 1);
            // modify student
            model.addCurrentIndexes(index);
            System.out.println("You have successfully added index " + i);
            printCoursesRegistered();
            printOnWaitlist();
        } else {
            // put into waitlist of index
            index.addWaitlist(model.getMatricNo());
            // put index on student onWaitlist
            model.addOnWaitlist(index);
            System.out.println("You have been successfully added onto waitlist of index " + i);
        }
    }

    // 2
    public void dropCourse(Set<Index> indexes) {
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
        int courseType = is.nextInt(1, 3);

        System.out.printf("Index: ");
        int dropIndex = is.nextInt(0);

        switch(courseType) {

            case 1:
            // get the index object
            Index i1 = model.getIndex(dropIndex);
            if (i1 == null) {
                System.out.println("You are not registered to " + dropIndex);
                return;
            }
            model.removeCurrentIndexes(i1);
            // remove student from student list in index
            i1.removeStudent(model.getMatricNo());
            i1.setVacancy(i1.getVacancy() + 1);

            IndexController ic = new IndexController(i1);
            ic.fixWaitlist(indexes);
            break;

            case 2:
            // get the index object
            Index i2 = model.getOnWaitlist(dropIndex);
            if (i2 == null) {
                System.out.println("You are not registered to " + dropIndex);
                return;
            }
            // remove index from onWaitList
            model.removeOnWaitlist(i2);
            // remove student from WaitList queue in index
            i2.removeWaitlist(model.getMatricNo());

        }
        System.out.println(dropIndex + " is successfully dropped");
    }

    // 3
    public void printCoursesRegistered() {
        view.printCoursesRegistered(model.getCurrentIndexes());
    }

    public void printOnWaitlist() {
        view.printOnWaitlist(model.getOnWaitlist());
    }

    // 4
    public void changeIndex(Set<Index> indexes) {
        /*logic:
          1. input current index and new index
          2. check if they are the same course using getCourseId if yes:
          3. check new course vacancy if yes:
          4. drop current, add new
          5. if no: invalid, loop again*/

        int curIndex;
        int nIndex;
        Index currentIndex = null;
        Index newIndex = null;
        boolean curFound = false;
        boolean nFound = false;
        Index clashedIndex = null;
        boolean timeClash = false;
        IndexController indexController = null;

        System.out.println("---------- Change Index -----------");
        System.out.println("Please enter current index: ");
        curIndex = is.nextInt(0);

        for(Index idx1 : model.getCurrentIndexes()){
            if(idx1.getIndexNumber() == curIndex){
                currentIndex = idx1;
                curFound = true;
                break;
            }
        }

        if (!curFound) {
            System.out.println("You have not registered the current index you entered.");
            return;
        }

        System.out.println("Please enter new index: ");
        nIndex = is.nextInt(0);

        for (Index idx2 : indexes) {
            if (idx2.getIndexNumber() == nIndex) {
                newIndex = idx2;
                nFound = true;
                indexController = new IndexController(newIndex);
                break;
            }
        }

        if (!nFound) {
            System.out.println("The new index you entered is not valid.");
            return;
        }

        if (!currentIndex.getCourseId().equals(newIndex.getCourseId())) {
            System.out.println("The indexes you entered are not from the same course.");
            return;
        }

        clashedIndex = indexController.timeClashWithSet(model.getCurrentIndexes());
        if (clashedIndex != null) {
            System.out.println("This Index has a time clash with " + clashedIndex.getCourseId() + 
                               " which you are currently registered");
            return;
        }

        clashedIndex = indexController.timeClashWithSet(model.getOnWaitlist());
        if(clashedIndex != null){
            System.out.println("This Index has a time clash with " + clashedIndex.getCourseId() + 
                               " which you are currently on waitlist");
            return;
        }


        if(newIndex.getVacancy() > 0){
            // confirm to change
            IndexController currentIndexController = new IndexController(currentIndex);
            IndexController newIndexController = new IndexController(newIndex);
            System.out.println("--- Current Index Information ---");
            currentIndexController.getView().printModelDetail(currentIndexController.getModel());
            System.out.println();
            System.out.println("--- New Index Information ---");
            newIndexController.getView().printModelDetail(newIndexController.getModel());
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
            if (newIndex.getVacancy() <= 0) {
                System.out.println("The new Index do not have vacancy.");
                return;
            }

            // confirm to change
            IndexController currentIndexController = new IndexController(currentIndex);
            IndexController newIndexController = new IndexController(newIndex);
            System.out.println("Current Index Information: ");
            currentIndexController.getView().printModelDetail(currentIndexController.getModel());
            System.out.println();
            System.out.println("New Index Information");
            newIndexController.getView().printModelDetail(newIndexController.getModel());
            System.out.println();
            System.out.println("Please enter y to confirm the change, enter n to cancel.");
            String confirm = scan.nextLine();

            if (confirm.equals("y")) {
                // drop currentIndex
                model.removeCurrentIndexes(currentIndex);
                // remove student from student list in index
                currentIndex.removeStudent(model.getMatricNo());
                // if there are students on waitlist for currentIndex, register the student at head of queue
                currentIndex.setVacancy(currentIndex.getVacancy() + 1);

                IndexController ic = new IndexController(currentIndex);
                ic.fixWaitlist(indexes);

                // add newIndex
                newIndex.addStudent(model.getMatricNo());
                newIndex.setVacancy(newIndex.getVacancy() - 1);
                // modify student
                model.addCurrentIndexes(newIndex);
                System.out.println("You have successfully changed from index " + 
                    currentIndex.getIndexNumber() + " to index " + 
                    newIndex.getIndexNumber());
            } else {
                System.out.println("Changing of indexes cancelled.");
            }
        }
    }

    // 5
    
    public void swapIndex(Set<Index> indexes) {
        boolean success = false;
        int peerIndex = -1;
        Index clashedIndex = null;
        StudentController peer = new StudentController();

        System.out.println("Your index number: ");
        int myIndex = is.nextInt(0);
        Index selfIndex = model.getIndex(myIndex);
        if(selfIndex == null){
            System.out.println("You are not registered for " + myIndex);
            return;
        }

        while(!success){
            System.out.println("Trying peer's login...");
            success = peer.init(indexes);
        }
        if(!success){
            System.out.println("You have exceeded 3 tries.");
            return;
        }

        System.out.println("Peer's index number: ");
        peerIndex = is.nextInt(0);

        /* 1. check if 2 indexes are under same course
           2. check if they are really registered
           3. check time table clash (not yet)
           4. swap
        */

        boolean valid = true;
        Index otherIndex = peer.getModel().getIndex(peerIndex);
        
        if(otherIndex == null){
            System.out.println("Peer is not registered for " + peerIndex);
            return;
        }
        
        if(!selfIndex.getCourseId().equals(otherIndex.getCourseId())){
            System.out.println("The two indexes entered are not from the same course.");
            return;
        }

        // check self clash
        IndexController selfIndexController = new IndexController(otherIndex);
        clashedIndex = selfIndexController.timeClashWithSet(model.getCurrentIndexes());
        if(clashedIndex != null){
            System.out.println("Peer's index has a time clash with your " + clashedIndex.getCourseId() + 
                               " which you are currently registered");
            return;
        }

        clashedIndex = selfIndexController.timeClashWithSet(model.getOnWaitlist());
        if(clashedIndex != null){
            System.out.println("Peer's index has a time clash with your " + clashedIndex.getCourseId() + 
                               " which you are currently on waitlist");
            return;
        }

        // check other clash
        IndexController otherIndexController = new IndexController(selfIndex);
        clashedIndex = otherIndexController.timeClashWithSet(peer.getModel().getCurrentIndexes());
        if(clashedIndex != null){
            System.out.println("Your index has a time clash with peer's " + clashedIndex.getCourseId() + 
                               " which peer is currently registered");
            return;
        }

        clashedIndex = otherIndexController.timeClashWithSet(peer.getModel().getOnWaitlist());
        if(clashedIndex != null){
            System.out.println("Peer's index has a time clash with peer's " + clashedIndex.getCourseId() + 
                               " which peer is currently on waitlist");
            return;
        }


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

    // 7

    public void saveStudentInfo() {
        view.saveStudentInfo(this.model);
    }

    public void printModelDetail() {
        view.printModelDetail(model);
    }
    
}

// test case for swap index
//3 20013
//15 20011