package src.model;

import src.model.Index;

import java.util.HashSet;
import java.util.Set;

public class Course {

    private String courseId;
    private String courseName;
    private String school;
    private int au;
    private Set<Index> indexes;
    private String[] lectureTime;
    private String[] lectureVenue;
    private String examTime;
    private String examVenue;

    public Course() {
        indexes = new HashSet<Index>();
        lectureTime = new String[]{"", ""};
        lectureVenue = new String[]{"", ""};
        examTime = "";
        examVenue = "";
    }

    public Course(String cid) {
        this();
        courseId = cid;
    }

    // getters

    public String getCourseId() {
        return courseId;
    }

    public String getCourseName() {
        return courseName;
    }

    public String getSchool() {
        return school;
    }

    public int getAu() {
        return au;
    }

    public String[] getLectureTime() {
        return lectureTime;
    }

    public String[] getLectureVenue() {
        return lectureVenue;
    }

    public String getExamTime() {
        return examTime;
    }

    public String getExamVenue() {
        return examVenue;
    }

    public Set<Index> getIndexes() {
        return indexes;
    }

    // setters

    public void setCourseId(String cid) {
        courseId = cid;
    }

    public void setCourseName(String cName) {
        courseName = cName;
    }

    public void setSchool(String s) {
        school = s;
    }

    public void setAu(int a) {
        au = a; 
    }

    public void setLectureTime(String lecture) {
        lectureTime = lecture.split("&");
        if (lectureTime.length == 0) {
            lectureTime = new String[]{"", ""};
        } else if (lectureTime.length == 1) {
            lectureTime = new String[]{lectureTime[0], ""};
        }
    }

    public void setLectureTime(int idx, String time) {
        lectureTime[idx] = time;
    }

    public void setLectureVenue(String venue) {
        lectureVenue = venue.split("&");
        if (lectureVenue.length == 0) {
            lectureVenue = new String[]{"", ""};
        } else if (lectureVenue.length == 1) {
            lectureVenue = new String[]{lectureVenue[0], ""};
        }
    }

    public void setLectureVenue(int idx, String venue) {
        lectureVenue[idx] = venue;
    }

    public void setExamTime(String exam) {
        examTime = exam;
    }

    public void setExamVenue(String venue) {
        examVenue = venue;
    }

    // indexes operations

    public void addIndex(Index idx) {
        indexes.add(idx);
    }

    public boolean removeIndex(Index idx) {
        return indexes.remove(idx);
    }

    public boolean removeIndex(int idx) {
        Index target = null;
        for (Index i: indexes) {
            if (i.getIndexNumber() == idx) {
                target = i;
            }
        }
        return indexes.remove(target);
    }

    public boolean contains(int idx) {
        for (Index i: indexes) {
            if (i.getIndexNumber() == idx) {
                return true;
            }
        }
        return false;
    }

    public Index getIndex(int idx) {
        for (Index i: indexes) {
            if (i.getIndexNumber() == idx) {
                return i;
            }
        }
        return null;
    }

    public int size() {
        return indexes.size();
    }
    
}
