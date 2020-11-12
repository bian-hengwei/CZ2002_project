import java.util.Queue;
import java.util.Set;
import java.util.LinkedList;
import java.util.HashSet;

public class Index{
    private String courseId;  // cz2002
    private int indexNumber;  // 10203
    private int vacancy;
    private String tutorialTime;
    private String labTime;
    private Queue<String> waitlist;
    private Set<String> studentList;
    
    public Index() {
        waitlist = new LinkedList<String>();
        studentList = new HashSet<String>();
    }

    public Index(String courseId, int indexNumber, int vacancy){
        this();
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

    public String getTutorialTime(){
        return tutorialTime;
    }

    public String getLabTime(){
        return labTime;
    }

    public Queue<String> getWaitList(){
        return waitlist;
    }

    public Set<String> getStudentList(){
        return studentList;
    }

    public int getWaitListLength(){
        return waitlist.size();
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

    public void setTutorialTime(String tutorialTime) {
        this.tutorialTime = tutorialTime;
    }

    public void setLabTime(String labTime) {
        this.labTime = labTime;
    }

    public void addWaitlist(String matricNo){
        waitlist.add(matricNo);
    }

    public String removeWaitList(){
        return waitlist.poll();
    }

    public void addStudent(String matricNo){
        studentList.add(matricNo);
        vacancy -= 1;
    }

    public void removeStudent(String matricNo){
        studentList.remove(matricNo);
        vacancy ++;
    }
}