import java.util.Map;
import java.util.HashMap;
import java.util.*;

enum School
{
    SCBE, CEE, SCSE, EEE, MSE, MAE, NBS, ADM, SOH, SOSS, WKWSCI, SBS, SPMS, ASE, LKCM;
}

public class Course
{
    private String courseName;
    private String courseId;
    private School school;
    private Map<Integer, Index> indexes;  // map indexNumber to Index object
    private Time lectureTime;
    private Time examTime;
    private int au;

    // constructor
    public Course(String courseId, String courseName, School school, int au)
    {
        this.courseId = courseId;
        this.courseName = courseName;
        this.school = school;
        this.au = au;
    }

    public void setCourseName(String courseName){
        this.courseName = courseName;
    }

    public void setCourseId(String courseId){
        this.courseId = courseId;
    }

    public void setSchool(School school){
        this.school = school;
    } 

    // create a index and put it into indexes map
    public void createIndex(int indexNumber, int vacancy)
    {
        Index i = new Index(this.courseId, indexNumber, vacancy);
        indexes.put(indexNumber, i);
    }

    public void removeIndex(int indexNumber){
        indexes.remove(indexNumber);
    }

    public void lectureTime(Time lectureTime){
        this.lectureTime = lectureTime;
    }

    public void examTime(Time examTime){
        this.examTime = examTime;
    }

    public void setAu(int au){
        this.au = au;
    }

    public String getCourseName(){
        return courseName;
    }

    public String getCourseId(){
        return courseId;
    }

    public School getSchool(){
        return school;
    }

    public int getAu() 
    { 
        return au; 
    }

    // given index, return vacancy for that index
    public int getVacancy(int indexNumber) 
    { 
        Index i = indexes.get(indexNumber);
        return i.getVacancy();
    }

    // return all the index of this course in a set
    public Set<Integer> getIndex(){
        Set<Integer> indexArray = new HashSet<Integer>();
        for(Map.Entry<Integer, Index> entry : indexes.entrySet()){
            indexArray.add(entry.getKey());
        }
        return indexArray;
    }

    public Time getLectureTime(){
        return lectureTime;
    }

    public Time getExamTime(){
        return examTime;
    }

    /**
     * reads the course information according to the course index
     * @return if the course information is successfully read
     */
    public boolean readInfo()
    {
        return true;    /////// yinan: what is this for?
    }

    // print information of course, can put into view class if we use mvc
    public void printCourse()
    {
        System.out.println("Name of course: " + getCourseName());
        System.out.println("School: " + getSchool());
        System.out.println("Number of AU: " + getAu());
    }

    // add a student to the wait list of given index
    public void addWaitlist(String ntuAccount, int indexNumber) {
        Index i = indexes.get(indexNumber);
        i.addWaitlist(ntuAccount);
    }

    // add a student to studentList of given index, decrement vacancy
    public void registerStudent(int indexNumber, String ntuAccount) {
        Index i = indexes.get(indexNumber);
        i.addStudentList(ntuAccount);
    }

    public int checkVacancy(String courseId, int indexNumber)
    {
        Index i = indexes.get(indexNumber);
        return i.getVacancy();
    }

    public Time getTutorialTime(int indexNumber){
        Index i = indexes.get(indexNumber);
        return i.getTutorialTime();
    }

    public Time getLabTime(int indexNumber){
        Index i = indexes.get(indexNumber);
        return i.getLabTime();
    }

    

    /*
    public Index getIndex(int idx)
    {
        return indexes.getOrDefault(idx, null);
    }*/
}