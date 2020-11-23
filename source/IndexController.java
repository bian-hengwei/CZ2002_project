import java.util.Set;
import java.util.Scanner;

public class IndexController {
    private Index model;
    private IndexView view;

    public IndexController() {
        model = new Index();
        view = new IndexView();
    }

    public IndexController(Index model) {
        this.model = model;
        view = new IndexView();
    }

    public void printVacancy() {
        view.printVacancy(model);
    }

    public Index getModel() {
        return model;
    }

    public void setModel(Index i) {
        model = i;
    }

    public IndexView getView() {
        return view;
    }

    private boolean timeClashWithIndex(Index targetIndex){
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

        int targetExamDate = Integer.parseInt(targetExamTime.substring(0, 4));
        int targetExamStart = Integer.parseInt(targetExamTime.substring(4, 8));
        int targetExamEnd = Integer.parseInt(targetExamTime.substring(9, 13));

        int modelExamDate = Integer.parseInt(modelExamTime.substring(0, 4));
        int modelExamStart = Integer.parseInt(modelExamTime.substring(4, 8));
        int modelExamEnd = Integer.parseInt(modelExamTime.substring(9, 13));


        // check exam time
        if ((modelExamDate != targetExamDate) && 
            ((modelExamStart > targetExamStart && 
                modelExamStart < targetExamEnd) || 
            (modelExamEnd > targetExamStart && 
                modelExamEnd < targetExamEnd) || 
            (modelExamStart > targetExamStart && 
                modelExamEnd < targetExamEnd) || 
            (targetExamStart > modelExamStart && 
                targetExamEnd < modelExamEnd)))
                noTimeClash = false;

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
                if (modelStartTime > targetStartTime && modelStartTime < targetEndTime) {
                    noTimeClash = false;
                }
                else if (modelEndTime > targetStartTime && modelEndTime < targetEndTime) {
                    noTimeClash = false;
                    System.out.printf("%d, %d, 3\n", i, j);
                }
                else if (modelStartTime > targetStartTime && modelEndTime < targetEndTime) {
                    noTimeClash = false;
                    System.out.printf("%d, %d, 4\n", i, j);
                }
                else if (targetStartTime > modelStartTime && targetEndTime < modelEndTime) {
                    noTimeClash = false;
                    System.out.printf("%d, %d, 5\n", i, j);
                }
                else if(targetStartTime == modelStartTime){
                    noTimeClash = false;
                    System.out.printf("%d, %d, 6\n", i, j);
                }
            }
        }
        return noTimeClash;
    }

    public Index timeClashWithSet(Set<Index> indexes) {
        for(Index idx : indexes) {
            if(!idx.getCourseId().equals(model.getCourseId()) && !timeClashWithIndex(idx)){
                return idx;
            }
        }
        return null;
    }

    public void editIndex(Set<Index> indexes) {
        Scanner scan = new Scanner(System.in);
        System.out.println("Editing index " + model.getIndexNumber());
        boolean quit = false;
        while (!quit) {
            System.out.println();
            System.out.println("Choose an option:");
            System.out.println("1. Print current index information");
            System.out.println("2. Change index number");
            System.out.println("3. Change vacancy");
            System.out.println("4. Set lecture");
            System.out.println("5. Set exam");
            System.out.println("6. Set tutorial");
            System.out.println("7. Set lab");
            System.out.println("8. Exit");
            System.out.printf("Option: ");
            int option = scan.nextInt();
            scan.nextLine();
            switch (option) {

                case 1:
                    view.printModelDetail(model);
                    break;

                case 2:
                    System.out.printf("New index number: ");
                    int newIndex = scan.nextInt();
                    scan.nextLine();
                    model.setIndexNumber(newIndex);
                    break;

                case 3:
                    System.out.printf("New vacancy: ");
                    int newVacancy = scan.nextInt();
                    scan.nextLine();
                    model.setVacancy(newVacancy);
                    fixWaitlist(indexes);
                    break;

                case 4:
                    System.out.printf(String.join(" ", "Lecture Time:", "0.", model.getLectureTime()[0], 
                        "1.", model.getLectureTime()[1], "\n"));
                    System.out.println("Select index to edit (or empty lecture to delete)");
                    System.out.printf("Option: ");
                    int op = scan.nextInt();
                    scan.nextLine();
                    if (op != 0 && op != 1) {
                        System.out.println("Invalid option");
                        break;
                    }
                    System.out.printf("New lecture time (MON1030-1130): ");
                    model.setLectureTime(op, scan.nextLine());
                    System.out.printf("New lecture venue: ");
                    model.setLectureVenue(op, scan.nextLine());
                    System.out.println("Successfully saved");
                    break;

                case 5:
                    System.out.printf("New exam time (12011000-1100): ");
                    model.setExamTime(scan.nextLine());
                    System.out.printf("New exam venue: ");
                    model.setExamVenue(scan.nextLine());
                    System.out.println("Successfully saved");
                    break;

                case 6:
                    System.out.printf("New tutorial time (MON1030-1130): ");
                    model.setTutorialTime(scan.nextLine());
                    System.out.printf("New tutorial venue: ");
                    model.setTutorialVenue(scan.nextLine());
                    System.out.println("Successfully saved");
                    break;

                case 7:
                    System.out.printf("New lab time (ODDMON1030-1230): ");
                    model.setLabTime(scan.nextLine());
                    System.out.printf("New lab venue: ");
                    model.setLabVenue(scan.nextLine());
                    System.out.println("Successfully saved");
                    break;

                case 8:
                    quit = true;
                    break;

                default:
                    System.out.println("Invalid option");
            }
        }
    }

    public void printStudents() {
        // print with view
        view.printStudents(model);
    }

    public void fixWaitlist(Set<Index> indexes) {
        while (model.getVacancy() > 0 && model.getWaitListLength() > 0) {
            System.out.println("Found students on waitlist");
            System.out.println("Adding student on waitlist to course and sending email");
            String matricNo = model.removeWaitlist();
            model.addStudent(matricNo);
            model.setVacancy(model.getVacancy() - 1);

            ///// modify student
            StudentController sc = new StudentController();
            sc.readStudent(matricNo, indexes, 3);
            sc.getModel().removeOnWaitlist(model);
            sc.getModel().addCurrentIndexes(model);
            sc.saveStudentInfo();

            String text = "Dear " + sc.getModel().getName() + ", you have successfully registered " + 
                          model.getCourseId() + " with index " + model.getIndexNumber();

            EmailSender emailSender = new EmailSender();

            emailSender.send(sc.getModel().getAccount(), text, "Registered course");

            System.out.println("Finished waitlist operation");
        }
    }

}
