import java.util.Set;
import java.util.Iterator;

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
        System.out.println("---------- Courses Registered ----------");
    	for(Index idx : indexes){
            System.out.println("Course: " + idx.getCourseId() + " Index: " + idx.getIndexNumber());
        }
    	System.out.println();
    }

    public void printOnWaitlist(Set<Index> indexes) {
        Iterator<Index> iterate = indexes.iterator();
        System.out.println("---------- Courses on Waitlist ----------");
        while(iterate.hasNext()){
            System.out.println("Course: " + iterate.next().getCourseId() + "Index: " + iterate.next().getIndexNumber());
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
        onWaitlistString = onWaitlistString.substring(0, onWaitlistString.length()-1);

        String takenCourses = String.join("&", student.getTakenCourses());

        String content = String.join(",", student.getAccount(), student.getName(), 
                                     student.getNationality(), student.getMatricNo(), 
                                     student.getMajor(), student.getYear(), 
                                     Integer.toString(student.getCurrentAu()), 
                                     takenCourses, 
                                     currentIndexesString, onWaitlistString);
        FileHandler.writeLine("student_information", student.getAccount(), content);
    }
}