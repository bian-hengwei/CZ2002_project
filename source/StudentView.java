import java.util.Set;

public class StudentView {

    public void printStudentInfo(String studentName, String nationality, String matricNo, String major, int year, int currentAu){
        System.out.println("Student Name: " + studentName);
        System.out.println("Nationality: " + nationality);
        System.out.println("Matriculation Number: " + matricNo);
        System.out.println("Major: " + major);
        System.out.println("Year: " + year);
        System.out.println("Number of AUs taken: " + currentAu);
    }

    public void printCoursesRegistered(Set<Index> indexes) {
        if (indexes.size() == 0) {
            System.out.println("Not registered to any course yet");
            return;
        }
        System.out.println("---------- Courses Registered ----------");
        for (Index idx : indexes) {
            System.out.printf("Course: %s %s, Index: %s, School: %s\n", idx.getCourseId(), idx.getCourseName(), 
                idx.getIndexNumber(), idx.getSchool());
        }
        System.out.println();
    }

    public void printOnWaitlist(Set<Index> indexes) {
        if (indexes.size() == 0) {
            System.out.println("Not registered on any waitlist");
            return;
        }
        System.out.println("---------- Courses on Waitlist ----------");
        for (Index idx : indexes) {
            System.out.printf("Course: %s %s, Index: %s, School: %s\n", idx.getCourseId(), idx.getCourseName(), 
                idx.getIndexNumber(), idx.getSchool());
        }
        System.out.println();
    }

    public void saveStudentInfo(Student student) {
        Set<Index> currentIndexes = student.getCurrentIndexes();
        Set<Index> onWaitlist = student.getOnWaitlist();
        String currentIndexesString = "";
        String onWaitlistString = "";
        for(Index idx : currentIndexes) {
            currentIndexesString += idx.getIndexNumber() + "&";
        }
        currentIndexesString = currentIndexesString.substring(0, currentIndexesString.length()-1);

        for(Index idx : onWaitlist) {
            onWaitlistString += idx.getIndexNumber() + "&";
        }
        if(onWaitlistString.length() > 0){
            onWaitlistString = onWaitlistString.substring(0, onWaitlistString.length()-1);
        }

        String takenCourses = String.join("&", student.getTakenCourses());

        String content = String.join(",", student.getAccount(), student.getName(), 
                                     student.getNationality(), student.getMatricNo(), 
                                     student.getMajor(), student.getYear(), 
                                     takenCourses, 
                                     currentIndexesString, onWaitlistString, 
                                     student.getEmail(), 
                                     Integer.toString(student.getCurrentAu()), ";,");
        FileHandler.writeLine("student_information", student.getAccount(), content);
    }
}