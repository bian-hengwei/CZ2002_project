import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;
import java.util.Set;

public class Student extends Account
{

    private String name;
    private String nationality;
    private String matricNo;
    private String major;
    private String year;
    private Set<String> takenCourses;
    private Set<Index> currentIndexes;
    private Set<Index> onWaitlist;
    private int currentAU;
    private final String SEP = System.getProperty("file.separator");

    public Student() {
        takenCourses = new HashSet<String>();
        currentIndexes = new HashSet<Index>();
        onWaitlist = new HashSet<Index>();
    }

    public Set<String> readPasswords() {
        return FileHandler.readToSet("student_passwords");
    }

    public String[] readInfo(String account) {
        return super.readInfo(account, ".." + SEP + "data" + SEP + "student_information.csv");
    }

    public String[] readTime(String index) {
        String filename = ".." + SEP + "data" + SEP + "student_time.csv";
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

    public Set<String> getTakenCourses(){ return takenCourses; }

    public Set<Index> getCurrentIndexes(){ return currentIndexes; }

    public Index getCurrentIndexes(int index){
        for(Index idx : currentIndexes){
            if(idx.getIndexNumber() == index){
                return idx;
            }
        }
        return null;
    }

    public Set<Index> getOnWaitlist(){ return onWaitlist; }

    public Index getOnWaitlist(int index){
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

    public void addTakenCourses(String course){
        takenCourses.add(course);
    }

    public void removeTakenCourses(String course){
        takenCourses.remove(course);
    }

    public void removeCurrentIndexes(Index index){
        currentIndexes.remove(index);
    }

    public void addCurrentIndexes(Index index){
        currentIndexes.add(index);
    }

    public void addOnWaitlist(Index index){
        onWaitlist.add(index);
    }

    public void removeOnWaitlist(Index index){
        onWaitlist.remove(index);
    }

    public void setAu(int currentAu){ this.currentAU = currentAu; }
}
