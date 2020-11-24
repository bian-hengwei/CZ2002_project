package src;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Set;

public class Index {

    private int indexNumber;
    private int vacancy;
    private String tutorialTime;
    private String tutorialVenue;
    private String labTime;
    private String labVenue;
    private Queue<String> waitlist;
    private Set<String> studentList;
    private Course course;
    
    public Index() {
        waitlist = new LinkedList<String>();
        studentList = new HashSet<String>();
        tutorialTime = "";
        tutorialVenue = "";
        labTime = "";
        labVenue = "";
    }

    public Index(int idx) {
        this();
        indexNumber = idx;
    }

    // getters

    public String getCourseId() {
        return course.getCourseId();
    }

    public String getCourseName() {
        return course.getCourseName();
    }

    public String getSchool() {
        return course.getSchool();
    }

    public int getAu() {
        return course.getAu();
    }

    public int getIndexNumber() {
        return indexNumber;
    }

    public int getVacancy() {
        return vacancy;
    }

    public String[] getLectureTime() {
        return course.getLectureTime();
    }

    public String[] getLectureVenue() {
        return course.getLectureVenue();
    }

    public String getExamTime() {
        return course.getExamTime();
    }

    public String getExamVenue() {
        return course.getExamVenue();
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

    public Course getCourse() {
        return course;
    }

    // setters

    public void setIndexNumber(int idxNo){
        indexNumber = idxNo;
    }

    public void setVacancy(int vac){
        vacancy = vac;
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

    public void setCourse(Course course) {
        this.course = course;
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

    public int studentListSize() {
        return studentList.size();
    }

}
