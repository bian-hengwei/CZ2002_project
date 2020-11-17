import java.util.Queue;
import java.util.Set;
import java.util.LinkedList;
import java.util.HashSet;

public class Index{
    private String courseId;  // cz2002
    private String courseName;
    //private School school;
    private int au;
    private int indexNumber;  // 10203
    private int vacancy;
    private String[] lectureTime;
    private String[] lectureVenue;
    private String examTime;
    private String examVenue;
    private String tutorialTime;
    private String tutorialVenue;
    private String labTime;
    private String labVenue;
    private Queue<String> waitlist; // should we change to queue of students?
    private Set<String> studentList;
    
    public Index() {
        waitlist = new LinkedList<String>();
        studentList = new HashSet<String>();
    }

    public Index(Course c, int idx, int vac){
        this();
        courseId = c.getCourseId();
        courseName = c.getCourseName();
        au = c.getAu();
        //school = c.getSchool();
        lectureTime = c.getLectureTime();
        lectureVenue = c.getLectureVenue();
        examTime = c.getExamTime();
        examVenue = c.getExamVenue();
        indexNumber = idx;
        vacancy = vac;
    } 

    public String getCourseId(){
        return courseId;
    }

    public void setCourseId(String cid) {
        courseId = cid;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String cName) {
        courseName = cName;
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

    public void setIndexNumber(int indexNumber){
        this.indexNumber = indexNumber;
    }

    public void setVacancy(int vacancy){
        this.vacancy = vacancy;
    }

    public void setLectureTime(String[] lecture) {
        lectureTime = lecture;
    }

    public void setTutorialTime(String tutorialTime) {
        this.tutorialTime = tutorialTime;
    }

    public void setLabTime(String labTime) {
        this.labTime = labTime;
    }

    public void setExamTime(String exam) {
        examTime = exam;
    }

    public void setExamVenue(String venue) {
        examVenue = venue;
    }

    public void addWaitlist(String matricNo){
        waitlist.add(matricNo);
    }

    public String removeWaitlist(){
        return waitlist.poll();
    }

    @SuppressWarnings("unchecked")
    public void removeWaitlist(String matricNo){
        LinkedList<String> ll = (LinkedList) waitlist;
        ll.remove(matricNo);
        waitlist = ll;
    }

    public int waitlistSize() {
        return waitlist.size();
    }

    public void addStudent(String matricNo){
        studentList.add(matricNo);
    }

    public void removeStudent(String matricNo){
        studentList.remove(matricNo);
    }
}