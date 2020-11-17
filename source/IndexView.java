
enum CourseType{
	CORE, UE, BM, LA, STS, AHSS, LS;
}

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
}