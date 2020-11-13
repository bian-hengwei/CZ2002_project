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
    private Set<Integer> indexes;  // map indexNumber to Index object

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

    // create a index and put it into indexes map
    public void createIndex(int indexNumber, int vacancy)
    {
        Index i = new Index(this, indexNumber, vacancy);
        indexes.add(indexNumber);
    }

    public void removeIndex(int indexNumber){
        indexes.remove(indexNumber);
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

    // // given index, return vacancy for that index
    // public int getVacancy(int indexNumber) 
    // {
    //     return indexes.get(indexNumber).getVacancy();
    // }

    // return all the index of this course in a set
    public Set<Integer> getIndexes(){
        return indexes;
    }
}
