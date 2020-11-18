import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;
import java.util.Set;

public class Student extends Account {

    private String matricNo;
    private String major;
    private String year;
    private int currentAU;
    private Set<String> takenCourses;
    private Set<Index> currentIndexes;
    private Set<Index> onWaitlist;

    public Student() {
        takenCourses = new HashSet<String>();
        currentIndexes = new HashSet<Index>();
        onWaitlist = new HashSet<Index>();
        super.setPrefix("student");
    }

    public String[] readTime(String index) {
        return FileHandler.readRow("access_time", index);
    }

    // getters

    public String getMatricNo() {
        return matricNo;
    }

    public String getMajor() {
        return major;
    }

    public String getYear() {
        return year;
    }

    public int getCurrentAu() {
        return currentAU;
    }

    public Set<String> getTakenCourses() {
        return takenCourses;
    }

    public Set<Index> getCurrentIndexes(){
        return currentIndexes;
    }

    public Set<Index> getOnWaitlist() {
        return onWaitlist;
    }

    // setters

    public void setMatricNo(String matricNo) {
        this.matricNo = matricNo;
    }

    public void setMajor(String major) {
        this.major = major;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public void setCurrentAu(int currentAu) {
        this.currentAU = currentAu;
    }

    // Data structure operations

    public void addTakenCourses(String course) {
        takenCourses.add(course);
    }

    public void removeTakenCourses(String course) {
        takenCourses.remove(course);
    }

    public void addCurrentIndexes(Index index) {
        currentIndexes.add(index);
    }

    public void removeCurrentIndexes(Index index) {
        currentIndexes.remove(index);
    }

    public void addOnWaitlist(Index index) {
        onWaitlist.add(index);
    }

    public void removeOnWaitlist(Index index) {
        onWaitlist.remove(index);
    }

    // TODO: deal with null output
    public Index getIndex(int index) {
        for(Index idx : currentIndexes){
            if(idx.getIndexNumber() == index) {
                return idx;
            }
        }
        return null;
    }

    // TODO: deal with null output
    public Index getOnWaitlist(int index) {
        for(Index idx : onWaitlist){
            if(idx.getIndexNumber() == index) {
                return idx;
            }
        }
        return null;
    }

}
