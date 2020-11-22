import java.util.Set;

public class IndexView {

    // print vacancy of a course
    public void printVacancy(Index idx) {
        System.out.println("Course id: " + idx.getCourseId());
        System.out.println("Index: " + idx.getIndexNumber());
        System.out.printf("Vacancy: [%d/%d]\n", idx.getVacancy(), idx.getVacancy() + idx.studentListSize());
        System.out.println("Length of Wait list: " + idx.getWaitListLength());
    }

    public void printModelDetail(Index idx) {
        System.out.printf("Printing index: %d\n", idx.getIndexNumber());
        System.out.printf("Course ID: %s\n", idx.getCourseId());
        System.out.printf("Course Name: %s\n", idx.getCourseName());
        System.out.printf("School: %s\n", idx.getSchool());
        System.out.printf("AU: %d\n", idx.getAu());
        System.out.printf("Vacancy: %d\n", idx.getVacancy());
        System.out.printf(String.join(" ", "Lecture Time:", idx.getLectureTime()[0], idx.getLectureTime()[1], "\n"));
        System.out.printf(String.join(" ", "Lecture Venue:", idx.getLectureVenue()[0], idx.getLectureVenue()[1], "\n"));
        System.out.printf("Exam Time: %s\n", idx.getExamTime());
        System.out.printf("Exam Venue: %s\n", idx.getExamVenue());
        System.out.printf("Tutorial Time: %s\n", idx.getTutorialTime());
        System.out.printf("Tutorial Venue: %s\n", idx.getTutorialVenue());
        System.out.printf("Lab Time: %s\n", idx.getLabTime());
        System.out.printf("Lab Venue: %s\n", idx.getLabVenue());
    }
    
    public void printStudents(Index idx) {
        Set<String> studentsInfo = FileHandler.readToSet("student_information");
        System.out.println("Printing students...");
        for (String studentRecord: studentsInfo) {
            if (idx.getStudentList().contains(studentRecord.split(",")[3])) {
                System.out.println(studentRecord.split(",")[3] + " " + studentRecord.split(",")[4]);
            }
        }
    }
}