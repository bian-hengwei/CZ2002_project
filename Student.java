import java.util.Map;
import java.util.HashMap;
import java.util.Set;
import java.util.Queue;
import java.util.LinkedList;
import java.util.Iterator;

public class Student extends Account
{

    private String name;
    private String nationality;
    private String matricNo;
    private String major;
    private int year;
    private Set<Course> takenCourses;
    private Set<Index> currentIndexes;
    private Set<Index> onWaitlist;

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

    public Index getCurrentIndexes(int index){
        Iterator<Index> iterate = currentIndexes.iterator();
        while(iterate.hasNext()){
            if(iterate.next().getIndexNumber() == index){
                break;
            }
        }
        return iterate.next();
    }

    public Set<Index> getOnWaitlist(){
        return onWaitlist;
    }

    public Index getOnWaitlist(int index){
        Iterator<Index> iterate = onWaitlist.iterator();
        while(iterate.hasNext()){
            if(iterate.next().getIndexNumber() == index){
                break;            }
        }
        return iterate.next();
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

    public void removeOnWaitlist(Index index){
        onWaitlist.remove(index);
    }

    public void setAu(int currentAu){
        this.currentAU = currentAu;
    }
}
