import java.util.HashSet;
import java.util.Set;

public class Course {

    private String courseId;
    private String school;
    private Set<Index> indexes;

    public Course() {
        indexes = new HashSet<Index>();
    }

    public Course(String cid) {
        this();
        courseId = cid;
    }

    // getters

    public String getCourseId() {
        return courseId;
    }

    public String getSchool() {
        return school;
    }

    public Set<Index> getIndexes() {
        return indexes;
    }

    // setters

    public void setCourseId(String id) {
        courseId = id;
        for (Index i: indexes) {
            i.setCourseId(id);
        }
    }

    public void setSchool(String s) {
        school = s;
        for (Index i: indexes) {
            i.setSchool(s);
        }
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
