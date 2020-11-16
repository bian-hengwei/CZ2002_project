import java.util.Map;
import java.util.HashMap;
import java.util.Set;
import java.util.Queue;
import java.util.LinkedList;
import java.util.Iterator;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.FileReader;
import java.io.FileNotFoundException;
import java.util.HashSet;

public class Student extends Account
{

    private String name;
    private String nationality;
    private String matricNo;
    private String major;
    private String year;
    private Set<Course> takenCourses;
    private Set<Index> currentIndexes;
    private Set<Index> onWaitlist;

    // does this store total au taken or total au registered for this semester?
    private int currentAU;

    public Student() {
        takenCourses = new HashSet<Course>();
        currentIndexes = new HashSet<Index>();
        onWaitlist = new HashSet<Index>();
    }

    public Queue<String> readPasswords() {
        return super.readPasswords("..\\data\\student_passwords.csv");
    }

    public String[] readInfo(String account) {
        return super.readInfo(account, "..\\data\\student_information.csv");
    }

    public String[] readTime(String index) {
        String filename = "..\\data\\student_time.csv";
        String[] currentTuple = new String[5];
        boolean found = false;
        try {
            FileReader fRead = new FileReader(filename);
            BufferedReader bRead = new BufferedReader(fRead);
            String newline;
            while ((newline = bRead.readLine()) != null && !found) {
                currentTuple = newline.split(",");
                if (currentTuple[0].equals(index)) 
                    found = true;
            }
            bRead.close();
            fRead.close();
        } catch(FileNotFoundException e) {
            System.out.println("Error: time file " + filename + " not found.");
            System.exit(1);
        } catch (IOException e) {
            System.out.println("Error: failed to read " + filename + ".");
            System.exit(1);
        }
        return found ? currentTuple : null;
    }

    public void setName(String n) { name = n; }

    public void setNationality(String n) { nationality = n; }

    public String getMatricNo(){ return matricNo; }

    public String getMajor(){ return major; }

    public String getYear(){ return year; }

    public Set<Course> getTakenCourses(){ return takenCourses; }

    public Set<Index> getCurrentIndexes(){ return currentIndexes; }

    public Index getCurrentIndexes(int index){
        Iterator<Index> iterate = currentIndexes.iterator();
        while(iterate.hasNext()){
            if(iterate.next().getIndexNumber() == index){
                break;
            }
        }
        return iterate.next();
    }

    public Set<Index> getonWaitlist(){ return onWaitlist; }

    public Index getonWaitlist(int index){
        Iterator<Index> iterate = onWaitlist.iterator();
        while(iterate.hasNext()){
            if(iterate.next().getIndexNumber() == index){
                break;            }
        }
        return iterate.next();
    }

    public int getCurrentAu(){ return currentAU; }

    public void setMatricNo(String matricNo){ this.matricNo = matricNo; }

    public void setMajor(String major){ this.major = major; }

    public void setYear(String year){ this.year = year; }

    public void addTakenCourses(Course course){
        takenCourses.add(course);
    }

    public void removeTakenCourses(Course course){
        takenCourses.remove(course);
    }

    public void removeCurrentIndexes(Index index){
        currentIndexes.remove(index);
    }

    public void removeonWaitlist(Index index){
        onWaitlist.remove(index);
    }

    public void setAu(int currentAu){ this.currentAU = currentAu; }
}
