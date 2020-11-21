public class IndexView{
    
    // for students
    // step 3 of add course and drop course. needs to print the course details for students before confirming to add or drop. waitlist and student list should not be shown
    public void printIndexDetail(String courseId, int indexNumber, int vacancy, String tutorialTime, String labTime){
        System.out.println("Course id: " + courseId);
        System.out.println("Index: " + indexNumber);
        System.out.println("Tutorial Timing:" + tutorialTime);
        System.out.println("Lab Timing: " + labTime);
    }

    // for students
    // print vacancy of a course
    public void printVacancy(String courseId, int indexNumber, int vacancy, int waitlistLength){
        System.out.println("Course id: " + courseId);
        System.out.println("Index: " + indexNumber);
        System.out.println("Vacancy: " + vacancy);
        System.out.println("Length of Wait list: " + waitlistLength);
    }

    public void printIndex(Index idx) {
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
        System.out.println("Printing students...");
    }
}