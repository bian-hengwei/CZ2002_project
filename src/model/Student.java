package src.model;

import src.boundary.FileHandler;
import src.model.Index;

import java.util.HashSet;
import java.util.Set;


/**
 * Student model that stores all student information
 */
public class Student {
    
    /**
     * Student account.
     */
    private String account;
    
    /**
     * Student name.
     */
    private String name;
    
    /**
     * Student nationality.
     */
    private String nationality;
    
    /**
     * Student matriculation number.
     */
    private String matricNo;
    
    /**
     * Student major.
     */
    private String major;
    
    /**
     * Student year.
     */
    private String year;
    
    /**
     * Current AU registered
     */
    private int currentAu;
    
    /**
     * Courses taken in previous semesters.
     */
    private Set<String> takenCourses;
    
    /**
     * Current indexes registered.
     */
    private Set<Index> currentIndexes;
    
    /**
     * Courses on wait list.
     */
    private Set<Index> onWaitlist;
    
    /**
     * Student gender.
     */
    private String gender;

    /**
     * Constructor of student that initializes the student
     */
    public Student() {
        takenCourses = new HashSet<String>();
        currentIndexes = new HashSet<Index>();
        onWaitlist = new HashSet<Index>();
        currentAu = 0;
    }

    /**
     * Calls read row to read student access time
     * @param index the student information
     * @return the access time fetched
     */
    public String[] readTime(String index) {
        return FileHandler.readRow("access_time", index);
    }

    // getters

    /**
     * getter of student account
     * @return account
     */
    public String getAccount() {
        return account;
    }
    
    /**
     * getter of student name
     * @return name
     */
    public String getName() {
        return name;
    }

    /**
     * getter of nationality
     * @return nationality
     */
    public String getNationality() {
        return nationality;
    }

    /**
     * getter of matric number
     * @return matric number
     */
    public String getMatricNo() {
        return matricNo;
    }

    /**
     * getter of major
     * @return major
     */
    public String getMajor() {
        return major;
    }

    /**
     * getter of student year
     * @return year
     */
    public String getYear() {
        return year;
    }

    /**
     * getter of current au taken
     * @return current au
     */
    public int getCurrentAu() {
        return currentAu;
    }

    /**
     * getter of set of taken courses
     * @return taken courses
     */
    public Set<String> getTakenCourses() {
        return takenCourses;
    }

    /**
     * getter of set of current indexes
     * @return current indexes
     */
    public Set<Index> getCurrentIndexes(){
        return currentIndexes;
    }

    /**
     * getter of set of indexes student is on wait list
     * @return on wait list
     */
    public Set<Index> getOnWaitlist() {
        return onWaitlist;
    }

    /**
     * getter of gender
     * @return gender
     */
    public String getGender() {
        return gender;
    }

    // setters

    /**
     * setter of student account
     * @param account student account
     */
    public void setAccount(String account) {
        this.account = account;
    }
    
    /**
     * setter of student name
     * @param n student name
     */
    public void setName(String n) {
        name = n;
    }

    /**
     * setter of student nationality
     * @param n student nationality
     */
    public void setNationality(String n) {
        nationality = n;
    }

    /**
     * setter of student matric number
     * @param matricNo student matric number
     */
    public void setMatricNo(String matricNo) {
        this.matricNo = matricNo;
    }

    /**
     * setter of student major
     * @param major student major
     */
    public void setMajor(String major) {
        this.major = major;
    }

    /**
     * setter of student current year
     * @param year student current year
     */
    public void setYear(String year) {
        this.year = year;
    }

    /**
     * setter of current au taken
     * @param currentAu current au taken
     */
    public void setCurrentAu(int currentAu) {
        this.currentAu = currentAu;
    }

    /**
     * setter of gender
     * @param g gender
     */
    public void setGender(String g) {
        this.gender = g;
    }

    // Data structure operations

    /**
     * Add course to taken courses
     * @param course taken course
     */
    public void addTakenCourses(String course) {
        takenCourses.add(course);
    }

    /**
     * Remove course from taken courses
     * @param course course to be removed
     */
    public void removeTakenCourses(String course) {
        takenCourses.remove(course);
    }

    /**
     * Add index to current taking indexes
     * @param index current index
     */
    public void addCurrentIndexes(Index index) {
        currentIndexes.add(index);
        currentAu += index.getAu();
    }

    /**
     * Remove index from current taking
     * @param index index to be removed
     */
    public void removeCurrentIndexes(Index index) {
        currentIndexes.remove(index);
        currentAu -= index.getAu();
    }

    /**
     * Add an index to on waitlist
     * @param index waitlist index
     */
    public void addOnWaitlist(Index index) {
        onWaitlist.add(index);
    }

    /**
     * Remove waitlist index
     * @param index waitlist to be removed
     */
    public void removeOnWaitlist(Index index) {
        onWaitlist.remove(index);
    }

    /**
     * Get one index from registered indexes
     * @param index index to be found
     * @return the found index
     */
    public Index getIndex(int index) {
        for(Index idx : currentIndexes) {
            if(idx.getIndexNumber() == index) {
                return idx;
            }
        }
        System.out.println("Index cannot be found among registered indexes.");
        return null;
    }

    /**
     * Get index from on waitlist
     * @param index the index to be found
     * @return the found waitlist index
     */
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
