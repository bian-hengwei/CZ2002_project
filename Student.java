import java.util.Map;
import java.util.HashMap;
import java.util.Set;

public class Student 
{

    private String matricNo;
    private String ntuAccount;
    private String major;
    private int year;
    private Set<Course> takenCourses;
    private Set<Course> currentCourses;
    private int currentAU;

    public Student(String ntuAcc)
    {
        ntuAccount = ntuAcc;
    }

    public String getMatricNo(){
        return matricNo;
    }

    public String getNtuAccount(){
        return ntuAccount;
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

    public Set<Course> getCurrentCourses(){
        return currentCourses;
    }

    public int getCurrentAu(){
        return currentAU;
    }

    public void setMatricNo(String matricNo){
        this.matricNo = matricNo;
    }

    public void setNtuAccount(String ntuAccount){
        this.ntuAccount = ntuAccount;
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

    public void addCurrentCourses(Course course){
        currentCourses.add(course);
    }

    public void removeCurrentCourses(Course course){
        currentCourses.remove(course);
    }

    public void setAu(int currentAu){
        this.currentAU = currentAu;
    }
}
