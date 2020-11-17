import java.util.Queue;
import java.util.Set;
import java.util.HashSet;

enum School
{
    SCBE, CEE, SCSE, EEE, MSE, MAE, NBS, ADM, SOH, SOSS, WKWSCI, SBS, SPMS, ASE, LKCM;
}

public class Course
{
    private String courseId;
    private String courseName;
    private School school;
    private int au;
    private String[] lectureTime;
    private String[] lectureVenue;
    private String examTime;
    private String examVenue;
    private Set<Integer> indexes;

    // constructor
    public Course(String courseId, String courseName, School school, int au)
    {
        this.courseId = courseId;
        this.courseName = courseName;
        this.school = school;
        this.au = au;
        indexes = new HashSet<Integer>();
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

    public void setAu(int au){
        this.au = au;
    }

    public void setLectureTime(String[] lecture) {
        lectureTime = lecture;
    }

    public void setLectureVenue(String[] venue) {
        lectureVenue = venue;
    }

    public void setExamTime(String exam) {
        examTime = exam;
    }

    public void setExamVenue(String venue) {
        examVenue = venue;
    }

    // create a index and put it into indexes map
    public void createIndex(int indexNumber, int vacancy)
    {
        Index i = new Index(this, indexNumber, vacancy);
        indexes.add(indexNumber);
    }

    public void removeIndex(int indexNumber){
        indexes.remove(indexNumber);
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

    public Set<Integer> getIndexes(){
        return indexes;
    }
}
