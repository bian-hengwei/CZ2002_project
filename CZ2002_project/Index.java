import java.util.Queue;
import java.util.*;

public class Index
{
    private String courseId;  // cz2002
    private int indexNumber;  // 10203
    private int vacancy;
    private Time tutorialTime;
    private Time labTime;
    private Queue<String> waitlist;  
    private Set<String> studentList;

    public Index(String courseId, int indexNumber, int vacancy){
    	this.courseId = courseId;
    	this.indexNumber = indexNumber;
    	this.vacancy = vacancy;
    } 

    public String getCourseId(){
    	return courseId;
    }

    public int getIndexNumber(){
    	return indexNumber;
    }

    public int getVacancy(){
    	return vacancy;
    }

    public Time getTutorialTime(){
    	return tutorialTime;
    }

    public Time getLabTime(){
    	return labTime;
    }

    public Object[] getWaitList(){
    	return waitlist.toArray();
    }

    public Object[] getStudentList(){
    	return studentList.toArray();
    }

    public void setCourseId(String courseId){
    	this.courseId = courseId;
    }

    public void setIndexNumber(int indexNumber){
    	this.indexNumber = indexNumber;
    }

    public void setVacancy(int vacancy){
    	this.vacancy = vacancy;
    }

    public void addWaitlist(String ntuAccount){
    	waitlist.add(ntuAccount);
    }

    public void removeWaitList(String ntuAccount){

    }

    public void addStudentList(String ntuAccount){
    	studentList.add(ntuAccount);
    	vacancy -= 1;
    }

    public void removeStudentList(String ntuAccount){
    	studentList.remove(ntuAccount);
    }
}