import java.util.Set;
import java.util.iterator;

public class StudentView {
    public void printStudentInfo(String studentName, String nationality, String matricNo, String major, String major, int year, int currentAu){
    	System.out.println("Student Name: " + studentName);
    	System.out.println("Nationality: ", + nationality);
    	System.out.println("Matriculation Number: " + matricNo);
    	System.out.println("Major: " + major);
    	System.out.println("Year: " + year);
    	System.out.println("Number of AUs taken: " + currentAu);
    }

    public void printCoursesRegistered(Set<Index> indexes){
    	Iterator<Index> iterate = indexes.iterator();
    	System.out.println("__________ Courses Registered __________");
    	while(indexes.hasNext()){
    		System.out.println("Course: " + iterate.next().getCourseId() + " Index: " + iterate.next().getIndexNumber());
    	}
    	System.out.println();
    }
}