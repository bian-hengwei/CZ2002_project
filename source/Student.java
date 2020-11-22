import java.util.HashSet;
import java.util.Set;

public class Student {

    private String account;
    private String name;
    private String nationality;
    private String matricNo;
    private String major;
    private String year;
    private int currentAU;
    private Set<String> takenCourses;
    private Set<Index> currentIndexes;
    private Set<Index> onWaitlist;
    private String email;

    public Student() {
        takenCourses = new HashSet<String>();
        currentIndexes = new HashSet<Index>();
        onWaitlist = new HashSet<Index>();
    }

    public String[] readTime(String index) {
        return FileHandler.readRow("access_time", index);
    }

    // getters

    public String getAccount() {
        return account;
    }
    
    public String getName() {
        return name;
    }

    public String getNationality() {
        return nationality;
    }

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

    public String getEmail(){
        return email;
    }

    // setters

    public void setAccount(String account) {
        this.account = account;
    }
    
    public void setName(String n) {
        name = n;
    }

    public void setNationality(String n) {
        nationality = n;
    }

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

    public void setEmail(String email) {
        this.email = email;
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
        for(Index idx : currentIndexes) {
            if(idx.getIndexNumber() == index) {
                return idx;
            }
        }
        System.out.println("Index cannot be found among registered indexes.");
        return null;
    }

    // TODO: deal with null output
    public Index getOnWaitlist(int index) {
        for(Index idx : onWaitlist){
            if(idx.getIndexNumber() == index) {
                return idx;
            }
        }
        System.out.println("Index cannot be found among waitlisted indexes.");
        return null;
    }

}
