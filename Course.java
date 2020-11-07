import java.util.Map;
import java.util.HashMap;

public class Course
{
    private String courseId;
    private String school;
    private Map<Integer, Index> indexes;
    private int noOfStudentRegistered;
    private String examTime;
    private int au;

    public Course(String id)
    {
        courseId = id;
    }

    /**
     * reads the course information according to the course index
     * @return if the course information is successfully read
     */
    public boolean readInfo()
    {
        return true;
    }

    public void printCourse()
    {
        String courseinfo = String.format("Course %s from school %s", courseId, school);
    }

    public Index getIndex(int idx)
    {
        return indexes.getOrDefault(idx, null);
    }

    public void waitlist(String student, int idx) {}

    public void registerStudent(int idx) {}

    public int getVacancy(int idx) { return 1; }

    public int getAU() { return au; }

    public String getId() { return courseId; }
}