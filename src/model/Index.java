package src.model;

import src.model.Course;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Set;


/**
 * Index model that stores all the information of the index
 */
public class Index {
    
    /**
     * Index number.
     */
    private int indexNumber;
    
    /**
     * Vacancy left.
     */
    private int vacancy;
    
    /**
     * Tutorial time.
     */
    private String tutorialTime;
    
    /**
     * Tutorial venue.
     */
    private String tutorialVenue;
    
    /**
     * Lab time.
     */
    private String labTime;
    
    /**
     * Lab venue.
     */
    private String labVenue;
    
    /**
     * Students on wait list.
     */
    private Queue<String> waitlist;
    
    /**
     * Registered students matric number list.
     */
    private Set<String> studentList;
    
    /**
     * Course object to refer to.
     */
    private Course course;
    
    /**
     * Constructs index object and initialize
     */
    public Index() {
        waitlist = new LinkedList<String>();
        studentList = new HashSet<String>();
        tutorialTime = "";
        tutorialVenue = "";
        labTime = "";
        labVenue = "";
    }

    
    /**
     * Constructs index object with index number
     * @param idx index number
     */
    public Index(int idx) {
        this();
        indexNumber = idx;
    }

    // getters

    /**
     * getter of course id
     * @return course's course id
     */
    public String getCourseId() {
        return course.getCourseId();
    }

    /**
     * getter of course name
     * @return course's course name
     */
    public String getCourseName() {
        return course.getCourseName();
    }

    /**
     * getter of school
     * @return course's school
     */
    public String getSchool() {
        return course.getSchool();
    }

    /**
     * getter of au
     * @return course's au
     */
    public int getAu() {
        return course.getAu();
    }

    /**
     * getter of index number
     * @return index number
     */
    public int getIndexNumber() {
        return indexNumber;
    }

    /**
     * getter of vacancy
     * @return vacancy
     */
    public int getVacancy() {
        return vacancy;
    }

    /**
     * getter of lecture time array
     * @return course's lecture time array
     */
    public String[] getLectureTime() {
        return course.getLectureTime();
    }

    /**
     * getter of lecture venue array
     * @return course's lecture venue array
     */
    public String[] getLectureVenue() {
        return course.getLectureVenue();
    }

    /**
     * getter of exam time
     * @return course's exam time
     */
    public String getExamTime() {
        return course.getExamTime();
    }

    /**
     * getter of exam venue
     * @return course's exam venue
     */
    public String getExamVenue() {
        return course.getExamVenue();
    }

    /**
     * getter of tutorial time
     * @return tutorial time
     */
    public String getTutorialTime() {
        return tutorialTime;
    }

    /**
     * getter of tutorial venue
     * @return tutorial venue
     */
    public String getTutorialVenue() {
        return tutorialVenue;
    }

    /**
     * getter of lab time
     * @return lab time
     */
    public String getLabTime() {
        return labTime;
    }

    /**
     * getter of lab venue
     * @return lab venue
     */
    public String getLabVenue() {
        return labVenue;
    }

    /**
     * getter of wait list
     * @return wait list
     */
    public Queue<String> getWaitList() {
        return waitlist;
    }

    /**
     * getter of student list
     * @return student list
     */
    public Set<String> getStudentList() {
        return studentList;
    }

    /**
     * getter of course object
     * @return course object
     */
    public Course getCourse() {
        return course;
    }

    // setters

    /**
     * setter of index number
     * @param idxNo index number
     */
    public void setIndexNumber(int idxNo){
        indexNumber = idxNo;
    }

    /**
     * setter of vacancy
     * @param vac vacancy
     */
    public void setVacancy(int vac){
        vacancy = vac;
    }

    /**
     * setter of tutorial time
     * @param tutorial tutorial time
     */
    public void setTutorialTime(String tutorial) {
        tutorialTime = tutorial;
    }

    /**
     * setter of tutorial venue
     * @param venue tutorial venue
     */
    public void setTutorialVenue(String venue) {
        tutorialVenue = venue;
    }

    /**
     * setter of lab time
     * @param lab lab time
     */
    public void setLabTime(String lab) {
        labTime = lab;
    }

    /**
     * setter of lab venue
     * @param venue lab venue
     */
    public void setLabVenue(String venue) {
        labVenue = venue;
    }

    /**
     * setter of wait list
     * @param wl wait list
     */
    public void setWaitlist(String wl) {
        if (wl.equals(""))
            return;
        String[] matricNoArray = wl.split("&");
        for (String matricNo: matricNoArray) {
            addWaitlist(matricNo);
        }
    }

    /**
     * setter of student list
     * @param sl student list
     */
    public void setStudentList(String sl) {        
        if (sl.equals(""))
            return;
        String[] matricNoArray = sl.split("&");
        for (String matricNo: matricNoArray) {
            addStudent(matricNo);
        }
    }

    /**
     * setter of course object
     * @param course course object
     */
    public void setCourse(Course course) {
        this.course = course;
    }

    // lists operations

    /**
     * Dequeue the first student
     * @return matric number of the first student
     */
    public String removeWaitlist() {
        return waitlist.poll();
    }

    /**
     * Remove one specific student
     * @param matricNo matric number looking for
     */
    @SuppressWarnings("unchecked")
    public void removeWaitlist(String matricNo) {
        LinkedList<String> ll = (LinkedList) waitlist;
        ll.remove(matricNo);
        waitlist = ll;
    }

    /**
     * Size of the waitlist
     * @return length of waitlist
     */
    public int getWaitListLength() {
        return waitlist.size();
    }

    /**
     * Add student to waitlist
     * @param matricNo student to be added
     */
    public void addWaitlist(String matricNo) {
        waitlist.add(matricNo);
    }

    /**
     * Check if student is in waitlist
     * @param matricNo student looked for
     * @return if student is found
     */
    public boolean waitlistContains(String matricNo) {
        return waitlist.contains(matricNo);
    }

    /**
     * Remove student from the course
     * @param matricNo student to be removed
     */
    public void removeStudent(String matricNo) {
        studentList.remove(matricNo);
    }

    /**
     * Add student to the course
     * @param matricNo student to be added
     */
    public void addStudent(String matricNo) {
        studentList.add(matricNo);
    }

    /**
     * Check if student is registered to the course
     * @param matricNo the student looked for
     * @return if the student is found
     */
    public boolean containsStudent(String matricNo) {
        return studentList.contains(matricNo);
    }

    /**
     * Size of the student list
     * @return student list length
     */
    public int studentListSize() {
        return studentList.size();
    }

}
