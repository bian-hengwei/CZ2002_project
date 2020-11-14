import java.util.Map;
import java.util.HashMap;
import java.util.Set;
import java.util.Queue;
import java.util.LinkedList;

public class Student extends Account
{

    private String name;
    private String nationality;
    private String matricNo;
    private String major;
    private int year;
    private Set<Course> takenCourses;
    private Set<Index> currentIndexes;

    // does this store total au taken or total au registered for this semester?
    private int currentAU;

    public Queue<String> readPasswords(String account) {
        return super.readPasswords(account, "student_passwords.csv");
    }

    public String getMatricNo(){
        return matricNo;
    }

    public String getMajor(){
        return major;
    }

    public int getYear(){
        return year;
    }

    public Set<Course> getTakenCourses(){
        return takenCourses;
    }

    public Set<Index> getCurrentIndexes(){
        return currentIndexes;
    }

    public int getCurrentAu(){
        return currentAU;
    }

    public void setMatricNo(String matricNo){
        this.matricNo = matricNo;
    }

    public void setMajor(String major){
        this.major = major;
    }

    public void setYear(int year){
        this.year = year;
    }

    public void addTakenCourses(Course course){
        takenCourses.add(course);
    }

    public void removeTakenCourses(Course course){
        takenCourses.remove(course);
    }

    public void removeCurrentIndexes(Index index){
        currentIndexes.remove(index);
    }

    public void setAu(int currentAu){
        this.currentAU = currentAu;
    }
}
