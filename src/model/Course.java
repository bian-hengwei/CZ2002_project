package src.model;

import src.model.Index;

import java.util.HashSet;
import java.util.Set;


/**
 * Course model that stores all information in any course
 */
public class Course {
    
    /**
     * Course id
     */
    private String courseId;
    
    /**
     * Course name
     */
    private String courseName;
    
    /**
     * School of course
     */
    private String school;
    
    /**
     * AU of course
     */
    private int au;
    
    /**
     * Set of indexes under course
     */
    private Set<Index> indexes;
    
    /**
     * Lecture times
     */
    private String[] lectureTime;
    
    /**
     * Lecture venues
     */
    private String[] lectureVenue;
    
    /**
     * Exam time
     */
    private String examTime;
    
    /**
     * Exam venue
     */
    private String examVenue;

    /**
     * Construct a new course and initialize variables
     */
    public Course() {
        indexes = new HashSet<Index>();
        lectureTime = new String[]{"", ""};
        lectureVenue = new String[]{"", ""};
        examTime = "";
        examVenue = "";
    }

    /**
     * Construct a new course with course id
     * @param cid course id
     */
    public Course(String cid) {
        this();
        courseId = cid;
    }

    // getters

    /**
     * getter of course id
     * @return course id
     */
    public String getCourseId() {
        return courseId;
    }

    /**
     * getter of course name
     * @return course name
     */
    public String getCourseName() {
        return courseName;
    }

    /**
     * getter of school
     * @return school
     */
    public String getSchool() {
        return school;
    }

    /**
     * getter of au
     * @return au
     */
    public int getAu() {
        return au;
    }

    /**
     * getter of lecture time array
     * @return lecture time array
     */
    public String[] getLectureTime() {
        return lectureTime;
    }

    /**
     * getter of lecture venue array
     * @return lecture venue array
     */
    public String[] getLectureVenue() {
        return lectureVenue;
    }

    /**
     * getter of exam time
     * @return exam time
     */
    public String getExamTime() {
        return examTime;
    }

    /**
     * getter of exam venue
     * @return exam venue
     */
    public String getExamVenue() {
        return examVenue;
    }

    /**
     * getter of indexes
     * @return indexes
     */
    public Set<Index> getIndexes() {
        return indexes;
    }

    // setters

    /**
     * setter of course id
     * @param cid course id
     */
    public void setCourseId(String cid) {
        courseId = cid;
    }

    /**
     * setter of course name
     * @param cName course name
     */
    public void setCourseName(String cName) {
        courseName = cName;
    }

    /**
     * setter of school
     * @param s school
     */
    public void setSchool(String s) {
        school = s;
    }

    /**
     * setter of course au
     * @param a course au
     */
    public void setAu(int a) {
        au = a; 
    }

    /**
     * setter of lecture time
     * @param lecture lecture time
     */
    public void setLectureTime(String lecture) {
        lectureTime = lecture.split("&");
        if (lectureTime.length == 0) {
            lectureTime = new String[]{"", ""};
        } else if (lectureTime.length == 1) {
            lectureTime = new String[]{lectureTime[0], ""};
        }
    }

    /**
     * setter of lecture time
     * @param idx index of lecture time
     * @param time lecture time
     */
    public void setLectureTime(int idx, String time) {
        lectureTime[idx] = time;
    }

    /**
     * setter of lecture venue
     * @param venue lecture venues
     */
    public void setLectureVenue(String venue) {
        lectureVenue = venue.split("&");
        if (lectureVenue.length == 0) {
            lectureVenue = new String[]{"", ""};
        } else if (lectureVenue.length == 1) {
            lectureVenue = new String[]{lectureVenue[0], ""};
        }
    }

    /**
     * setter of lecture venue
     * @param idx index of lecture venue
     * @param venue lecture venue
     */
    public void setLectureVenue(int idx, String venue) {
        lectureVenue[idx] = venue;
    }

    /**
     * setter of exam time
     * @param exam exam time
     */
    public void setExamTime(String exam) {
        examTime = exam;
    }

    /**
     * setter of exam venue
     * @param venue exam venue
     */
    public void setExamVenue(String venue) {
        examVenue = venue;
    }

    // indexes operations

    /**
     * Add index to set
     * @param idx index to be added
     */
    public void addIndex(Index idx) {
        indexes.add(idx);
    }

    /**
     * Remove index from set
     * @param idx index to be removed
     * @return if the removal is successful
     */
    public boolean removeIndex(Index idx) {
        return indexes.remove(idx);
    }

    /**
     * Remove index from set
     * @param idx index number to be removed
     * @return if the removal is successful
     */
    public boolean removeIndex(int idx) {
        Index target = null;
        for (Index i: indexes) {
            if (i.getIndexNumber() == idx) {
                target = i;
            }
        }
        return indexes.remove(target);
    }

    /**
     * Check if index is in the set
     * @param idx the index searching
     * @return if index is found
     */
    public boolean contains(int idx) {
        for (Index i: indexes) {
            if (i.getIndexNumber() == idx) {
                return true;
            }
        }
        return false;
    }

    /**
     * Get index object
     * @param idx index to get from set
     * @return the index object found
     */
    public Index getIndex(int idx) {
        for (Index i: indexes) {
            if (i.getIndexNumber() == idx) {
                return i;
            }
        }
        return null;
    }

    /**
     * Size of the set
     * @return set size
     */
    public int size() {
        return indexes.size();
    }
    
}
