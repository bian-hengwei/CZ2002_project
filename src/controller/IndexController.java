package src.controller;

import src.boundary.EmailSender;
import src.boundary.InputScanner;
import src.boundary.Notification;
import src.model.Course;
import src.model.Index;
import src.view.IndexView;

import java.util.Set;
import java.util.Scanner;

public class IndexController {
    private Index model;
    private IndexView view;

    /** 
    * Set up Index Controller.
    */
    public IndexController() {
        model = new Index();
        view = new IndexView();
    }

    /** 
    * Set up Index Controller with a parameter.
    * @param model A set initiated under Index class, a specific input index.
    */
    public IndexController(Index model) {
        this.model = model;
        view = new IndexView();
    }

    /** 
    * Print vacancy of the model.
    * to print out vacancy of the model.
    */
    public void printVacancy() {
        view.printVacancy(model);
    }

    /** 
    * Gets model of an index.
    * @return An Index representing the index of a model.
    */
    public Index getModel() {
        return model;
    }

    /** 
    * Sets model of an index.
    * @param i An Index containing the model's index.
    */
    public void setModel(Index i) {
        model = i;
    }

    /** 
    * Gets view of an index.
    * @return An IndexView representing view of the model.
    */
    public IndexView getView() {
        return view;
    }

    /** 
    * Check whether there is time clash of 2 indexes when student wants to add a course or swap index with peer.
    * @param targetIndex The input index, it is an object of Index class.
    * @return A boolean indicating whether there is a time clash.
    */
    private boolean timeClashWithIndex(Index targetIndex) {
        int modelStartTime;
        int modelEndTime;
        int targetStartTime;
        int targetEndTime;
        String modelDay;
        String targetDay;
        String modelOddEven = "";
        String targetOddEven = "";

        boolean noTimeClash = true;

        // targetSet: all taken time
        // modelSet: all taken time

        String[] targetArray = new String[4];
        String[] modelArray = new String[4];

        targetArray[0] = targetIndex.getLectureTime()[0];
        targetArray[1] = targetIndex.getLectureTime()[1];
        targetArray[2] = targetIndex.getTutorialTime();
        targetArray[3] = targetIndex.getLabTime();

        modelArray[0] = model.getLectureTime()[0];
        modelArray[1] = model.getLectureTime()[1];
        modelArray[2] = model.getTutorialTime();
        modelArray[3] = model.getLabTime();
        int i;
        int j;

        String targetExamTime = targetIndex.getExamTime();
        String modelExamTime = model.getExamTime();

        if((!targetExamTime.equals("")) && (!modelExamTime.equals(""))) {

            int targetExamDate = Integer.parseInt(targetExamTime.substring(0, 4));
            int targetExamStart = Integer.parseInt(targetExamTime.substring(4, 8));
            int targetExamEnd = Integer.parseInt(targetExamTime.substring(9, 13));

            int modelExamDate = Integer.parseInt(modelExamTime.substring(0, 4));
            int modelExamStart = Integer.parseInt(modelExamTime.substring(4, 8));
            int modelExamEnd = Integer.parseInt(modelExamTime.substring(9, 13));


            // check exam time
            if ((modelExamDate == targetExamDate) && 
                ((modelExamStart > targetExamStart &&  modelExamStart < targetExamEnd) || 
                (modelExamEnd > targetExamStart &&  modelExamEnd < targetExamEnd) || 
                (modelExamStart > targetExamStart &&  modelExamEnd < targetExamEnd) || 
                (targetExamStart > modelExamStart &&  targetExamEnd < modelExamEnd) || 
                (targetExamStart == modelExamStart))) {
                    noTimeClash = false;
            }
        }

        for(i = 0; i < 4; i++) {
            if(modelArray[i].equals("")) {
                continue;
            }
            if(i == 3) {
                modelOddEven = modelArray[i].substring(0, 3);
                modelStartTime = Integer.parseInt(modelArray[i].substring(6, 10));
                modelEndTime = Integer.parseInt(modelArray[i].substring(11, 15));
                modelDay = modelArray[i].substring(3, 6);
            } else {
                modelStartTime = Integer.parseInt(modelArray[i].substring(3, 7));
                modelEndTime = Integer.parseInt(modelArray[i].substring(8, 12));
                modelDay = modelArray[i].substring(0, 3);
            }
            for (j = 0; j < 4; j++) {
                if (targetArray[j].equals("")) {
                    continue;
                }
                if (i == 3) {
                    targetOddEven = targetArray[i].substring(0, 3);
                    targetStartTime = Integer.parseInt(targetArray[i].substring(6, 10));
                    targetEndTime = Integer.parseInt(targetArray[i].substring(11, 15));
                    targetDay = targetArray[i].substring(3, 6);
                } else {
                    targetStartTime = Integer.parseInt(targetArray[i].substring(3, 7));
                    targetEndTime = Integer.parseInt(targetArray[i].substring(8, 12));
                    targetDay = targetArray[i].substring(0, 3);
                }
                if (i == 3 && j == 3 && !modelOddEven.equals(targetOddEven)) {
                    continue;
                }
                if ((modelStartTime > targetStartTime && modelStartTime < targetEndTime) || 
                    (modelEndTime > targetStartTime && modelEndTime < targetEndTime) || 
                    (modelStartTime > targetStartTime && modelEndTime < targetEndTime) || 
                    (targetStartTime > modelStartTime && targetEndTime < modelEndTime) || 
                    (targetStartTime == modelStartTime)) {
                    noTimeClash = false;
                }
            }
        }
        return noTimeClash;
    }

    /** 
    * Check whether there is time clash of 2 indexes, comparing time slots of target index with all the time slots of 
    * the currently registered indexes.
    * @param indexes A set of registered indexes.
    * @return A null if there is no time clash
    */
    public Index timeClashWithSet(Set<Index> indexes) {
        for(Index idx : indexes) {
            if(!idx.getCourseId().equals(model.getCourseId()) && !timeClashWithIndex(idx)){
                return idx;
            }
        }
        return null;
    }

    /** 
    * Edit/update index information. A method under admin.
    * An admin can modify indexes of different courses, e.g, index number, vacancy etc.
    * @param indexes A set of current indexes.
    */
    public void editIndex(Set<Index> indexes) {
        Scanner scan = new Scanner(System.in);
        InputScanner is = new InputScanner();
        System.out.println("Editing index " + model.getIndexNumber());
        boolean quit = false;
        while (!quit) {
            System.out.println();
            System.out.println("Choose an option:");
            System.out.println("1. Print current index information");
            System.out.println("2. Change index number");
            System.out.println("3. Change vacancy");
            System.out.println("4. Set tutorial");
            System.out.println("5. Set lab");
            System.out.println("6. Exit");
            System.out.printf("Option: ");
            int option = is.nextInt(1, 7);
            switch (option) {

                case 1:
                    System.out.println();
                    System.out.println("----- Print current index information -----");
                    view.printModelDetail(model);
                    break;

                case 2:
                    System.out.println();
                    System.out.println("----- Change index number -----");
                    System.out.printf("New index number: ");
                    int newIndex = is.nextInt(0);
                    model.setIndexNumber(newIndex);
                    break;

                case 3:
                    System.out.println();
                    System.out.println("------ Change vacancy -----");
                    System.out.printf("New vacancy: ");
                    int newVacancy = is.nextInt(0);
                    model.setVacancy(newVacancy);
                    fixWaitlist(indexes);
                    break;

                case 4:
                    System.out.println();
                    System.out.println("----- Set tutorial -----");
                    System.out.printf("New tutorial time (MON1030-1130): ");
                    model.setTutorialTime(scan.nextLine());
                    System.out.printf("New tutorial venue: ");
                    model.setTutorialVenue(scan.nextLine());
                    System.out.println("Successfully saved");
                    break;

                case 5:
                    System.out.println();
                    System.out.println("----- Set lab -----");
                    System.out.printf("New lab time (ODDMON1030-1230): ");
                    model.setLabTime(scan.nextLine());
                    System.out.printf("New lab venue: ");
                    model.setLabVenue(scan.nextLine());
                    System.out.println("Successfully saved");
                    break;

                case 6:
                    quit = true;
                    break;

                default:
                    System.out.println("Invalid option");
            }
        }
    }

    /** 
    * Print all students under an input model(index).
    * view calls printStudent under IndexView to print out informaion of students.
    */
    public void printStudents() {
        // print with view
        view.printStudents(model);
    }

    /** 
    * Remove a student from waitlist and add him/her into the index if there is a vacancy available.
    * @param indexes A set of current indexes.
    * Send email to the students when they are removed from a waitlist and successfully get their index.
    */
    public void fixWaitlist(Set<Index> indexes) {
        while (model.getVacancy() > 0 && model.getWaitListLength() > 0) {
            Notification emailSender = new EmailSender();
            System.out.println("Found students on waitlist");
            System.out.println("Adding student on waitlist to course and sending email");
            String matricNo = model.removeWaitlist();

            ///// modify student
            StudentController sc = new StudentController();
            sc.readStudent(matricNo, indexes, 3);

            while (sc.getModel().getCurrentAu() + model.getAu() > 21) {
                String text = "Registered\nDear " + sc.getModel().getName() + ", you have already reached the AU limit so we apologize " + 
                    "for not being able to  register you to " +  model.getCourseId() + " with index " + model.getIndexNumber();
                emailSender.send(sc.getModel().getAccount(), text);
                matricNo = model.removeWaitlist();
                sc.readStudent(matricNo, indexes, 3);
            }

            sc.getModel().removeOnWaitlist(model);
            sc.getModel().addCurrentIndexes(model);
            model.addStudent(matricNo);
            model.setVacancy(model.getVacancy() - 1);
            sc.saveStudentInfo();

            String text = "Registered\nDear " + sc.getModel().getName() + ", you have successfully registered to " + 
                          model.getCourseId() + " with index " + model.getIndexNumber();

            emailSender.send(sc.getModel().getAccount(), text);
        }
        System.out.println("Finished waitlist operation");
    }

}
