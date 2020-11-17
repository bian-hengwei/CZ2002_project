import java.util.HashSet;
import java.util.Set;

public class Course {

    private String courseId;
    private Set<Index> indexes;

    public Course(String cid)
    {
        courseId = cid;
        indexes = new HashSet<Index>();
    }

    public String getCourseId() {
        return courseId;
    }

    public Set<Index> getIndexes() {
        return indexes;
    }

    public void setCourseId(String id) {
        courseId = id;
    }

    public void addIndex(Index idx) {
        indexes.add(idx);
    }

    public void removeIndex(Index idx) {
        indexes.remove(idx);
    }

    public void removeIndex(int idx) {
        Index target = null;
        for (Index i: indexes) {
            if (i.getIndexNumber() == idx) {
                target = i;
            }
        }
        indexes.remove(target);
    }
    
}
