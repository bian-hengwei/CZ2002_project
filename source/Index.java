import java.util.HashSet;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Set;

public class Index {

    private String courseId;
    private String courseName;
    private String school;
    private int au;
    private int indexNumber;
    private int vacancy;
    private String[] lectureTime;
    private String[] lectureVenue;
    private String examTime;
    private String examVenue;
    private String tutorialTime;
    private String tutorialVenue;
    private String labTime;
    private String labVenue;
    private Queue<String> waitlist;
    private Set<String> studentList;
    
    public Index() {
        waitlist = new LinkedList<String>();
        studentList = new HashSet<String>();
    }

    // getters

    public String getCourseId() {
        return courseId;
    }

    public String getCourseName() {
        return courseName;
    }

    public String getSchool() {
        return school;
    }

    public int getAu() {
        return au;
    }

    public int getIndexNumber() {
        return indexNumber;
    }

    public int getVacancy() {
        return vacancy;
    }

    public String[] getLectureTime() {
        return lectureTime;
    }

    public String[] getLectureVenue() {
        return lectureVenue;
    }

    public String getExamTime() {
        return examTime;
    }

    public String getExamVenue() {
        return examVenue;
    }

    public String getTutorialTime() {
        return tutorialTime;
    }

    public String getTutorialVenue() {
        return tutorialVenue;
    }

    public String getLabTime() {
        return labTime;
    }

    public String getLabVenue() {
        return labVenue;
    }

    public Queue<String> getWaitList() {
        return waitlist;
    }

    public Set<String> getStudentList() {
        return studentList;
    }

    // setters

    public void setCourseId(String cid) {
        courseId = cid;
    }

    public void setCourseName(String cName) {
        courseName = cName;
    }

    public void setSchool(String s) {
        school = s;
    }

    public void setAu(int a) {
        au = a; 
    }

    public void setIndexNumber(int idxNo){
        indexNumber = idxNo;
    }

    public void setVacancy(int vac){
        vacancy = vac;
    }

    public void setLectureTime(String lecture) {
        lectureTime = lecture.split("&");
    }

    public void setLectureVenue(String venue) {
        lectureVenue = venue.split("&");
    }

    public void setExamTime(String exam) {
        examTime = exam;
    }

    public void setExamVenue(String venue) {
        examVenue = venue;
    }

    public void setTutorialTime(String tutorial) {
        tutorialTime = tutorial;
    }

    public void setTutorialVenue(String venue) {
        tutorialVenue = venue;
    }

    public void setLabTime(String lab) {
        labTime = lab;
    }

    public void setLabVenue(String venue) {
        labVenue = venue;
    }

    public void setWaitlist(String wl) {
        if (wl.equals(""))
            return;
        String[] matricNoArray = wl.split("&");
        for (String matricNo: matricNoArray) {
            addWaitlist(matricNo);
        }
    }

    public void setStudentList(String sl) {        
        if (sl.equals(""))
            return;
        String[] matricNoArray = sl.split("&");
        for (String matricNo: matricNoArray) {
            addStudent(matricNo);
        }
    }

    // lists operations

    public String removeWaitlist() {
        return waitlist.poll();
    }

    @SuppressWarnings("unchecked")
    public void removeWaitlist(String matricNo) {
        LinkedList<String> ll = (LinkedList) waitlist;
        ll.remove(matricNo);
        waitlist = ll;
    }

    public int getWaitListLength() {
        return waitlist.size();
    }

    public void addWaitlist(String matricNo) {
        waitlist.add(matricNo);
    }

    public boolean waitlistContains(String matricNo) {
        return waitlist.contains(matricNo);
    }

    public void removeStudent(String matricNo) {
        studentList.remove(matricNo);
    }

    public void addStudent(String matricNo) {
        studentList.add(matricNo);
    }

    public boolean containsStudent(String matricNo) {
        return studentList.contains(matricNo);
    }

}
