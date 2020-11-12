

public class StudentController{

	private Student model;
	private StudentView view;

	public boolean checkPassword(String pwd){
        // TODO: file I/O by Yinan
        // String accountPassword = readPassword(ntuAccount);
        String accountPassword = "123456";
        String hashed = hash(pwd);
        return hashed == accountPassword;
    }

    public boolean compareTime(/*start datetime, end datetime, current datetime*/){
        // return if student can access the system
        return true;
    }

    public void readInfo(){
        // TODO: file I/O by Yinan
        // String[] info = readInfoFromFile(ntuAccount)
        matricNo = "U1234567A";
        major = "SCSE";
        year = 2;
    }

    private String hash(String pwd) { return ""; }

    public void addCourse(Course c, int index){
        Index idx = c.getIndex(index);
        int courseAU = c.getAU();
        if (currentCourses.contains(c)){
            System.out.println("Current course already registered.");
        }
        else if (courseAU + currentAU > 21){
            System.out.println("Exceeding maximum workload! This course cannot be added.");
        }
        else if (c.getVacancy(index) > 0){
            c.registerStudent(index);
            System.out.println("Successfully registered to course " + c.getId());
            currentCourses.add(c);
        }
        else{
            c.waitlist(matricNo, index);
            System.out.println("Current index full. Putting on wait list...");
        }
    }

    public void dropCourse(Course c) {}

    public void printCoursesRegistered() {}

    public void checkVacancy(Course c) {}

    public void changeIndex(Course c, int idx) {}

    public void swapIndex(Course c, Student s) {}

    public void reclassify(Course c, String newType) {}
}