package src;

import java.util.Set;

public class MainIO {

    public static void readCourses(Set<Course> courses) {
        Set<String> coursesString = FileHandler.readToSet("course_information");
        for (String line: coursesString) {
            String[] indexInfoArray = line.split(",");
            Course course = new Course();
            course.setCourseId(indexInfoArray[0]);
            course.setCourseName(indexInfoArray[1]);
            course.setSchool(indexInfoArray[2]);
            course.setAu(Integer.parseInt(indexInfoArray[3]));
            course.setLectureTime(indexInfoArray[4]);
            course.setLectureVenue(indexInfoArray[5]);
            course.setExamTime(indexInfoArray[6]);
            course.setExamVenue(indexInfoArray[7]);
            courses.add(course);
        }
    }

    public static void readIndexes(Set<Course> courses, Set<Index> indexes) {
        Set<String> indexesString = FileHandler.readToSet("index_information");
        for (String line: indexesString) {
            String[] indexInfoArray = line.split(",");
            Index idx = new Index();
            idx.setIndexNumber(Integer.parseInt(indexInfoArray[1]));
            idx.setVacancy(Integer.parseInt(indexInfoArray[2]));
            idx.setTutorialTime(indexInfoArray[3]);
            idx.setTutorialVenue(indexInfoArray[4]);
            idx.setLabTime(indexInfoArray[5]);
            idx.setLabVenue(indexInfoArray[6]);
            idx.setWaitlist(indexInfoArray[7]);
            idx.setStudentList(indexInfoArray[8]);

            for (Course c : courses) {
                if (c.getCourseId().equals(indexInfoArray[0])) {
                    c.addIndex(idx);
                    idx.setCourse(c);
                    break;
                }
            }
            indexes.add(idx);
        }
    }

    public static void saveCourses(Set<Course> courses) {
        StringBuilder sb = new StringBuilder();
        sb.append("courseId,name,school,au,lectureTime,lectureVenue,examTime,examVenue,;\n");
        for (Course c : courses) {
            String lectureTime = String.join("&", c.getLectureTime());
            String lectureVenue = String.join("&", c.getLectureVenue());
            sb.append(String.format("%s,%s,%s,%d,%s,%s,%s,%s,;\n", 
                c.getCourseId(), c.getCourseName(), c.getSchool(), 
                c.getAu(), lectureTime, lectureVenue, c.getExamTime(), 
                c.getExamVenue())
            );
        }
        FileHandler.save("course_information", sb.toString());
        System.out.println("Courses successfully saved.");
    }

    public static void saveIndexes(Set<Index> indexes) {
        StringBuilder sb = new StringBuilder();
        sb.append("id,index,vacancy,");
        sb.append("examVenue,tutorialTime,tutorialVenue,labTime,labVenue,waitlist,studentList,;\n");
        for (Index idx: indexes) {
            String waitlist = String.join("&", idx.getWaitList());
            String studentList = String.join("&", idx.getStudentList());
            sb.append(String.format("%s,%d,%d,%s,%s,%s,%s,%s,%s,;\n", 
                idx.getCourseId(), idx.getIndexNumber(), idx.getVacancy(), 
                idx.getTutorialTime(), idx.getTutorialVenue(), 
                idx.getLabTime(), idx.getLabVenue(), waitlist, studentList)
            );
        }
        FileHandler.save("index_information", sb.toString());
        System.out.println("Indexes successfully saved.");
    }

}
