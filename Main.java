import java.util.Scanner;
import java.util.Map;
import java.util.HashMap;
import java.util.Set;

public class Main {
	Boolean mainContinue = 1;
	int login;

    public static void main(String[] args) {
    	Scanner scan = new Scanner(System.in);

    	System.out.println("-----------------------------------------");
    	System.out.println("| Student Automated Registration System |");
    	System.out.println("-----------------------------------------");
    	while(outerContinue){
    		System.out.println("Please choose type of login: ");
    		System.out.println("1. Admin login");
    		System.out.println("2. Student login");
    		System.out.println("3. Quit");
    		login = scan.nextInt();
    		switch(login){
    			case 1:
    			adminMain();
    			break;

    			case 2:
    			stuedntMain();
    			break;

    			case 3:
    			outerContinue = 0;
    			break;

	        StudentController sc = new StudentController();
	        System.out.println("Checking student... account: hbian001");
	        System.out.println("password: ilove2002");
	        sc.login("hbian001", "ilove2002");
	        while(continue){
    			System.out.println("Please select one of the functions: ");
    		}
    	}
    }

    public void adminMain(){

    }

    public void studentMain(){
    	int studentContinue = 1;
    	int studentChoide;

    	//Student login
    	StudentController studentController = new StudentController();
		System.out.println("Checking student... account: hbian001");
		System.out.println("password: ilove2002");
		studentController.login("hbian001", "ilove2002");

		// functions for students
		while(studentContinue){
			System.out.println("Please select one of the functions: ");
			System.out.println("1. Add Course");
			System.out.println("2. Drop Course");
			System.out.println("3. Check/Print Courses Registered and on Waitlist");
			System.out.println("4. Change Index Number of Course");
			System.out.println("5. Swop Index Number with Another Student");
			System.out.println("6. Re-Classify GERPE Course Type");
			System.out.println("7. Exit");
			studentChoice = scan.nextInt();

			switch(studentChoice){
				case 1:
				studentController.addCourse();
				break;

				case 2:
				studentController.dropCourse();
				break;

				case 3:
				studentController.printCoursesRegistered();
				studentController.printOnWaitlist();
				break;

				case 4:
				studentController.changeIndex();
				break;

				case 5:
				studentController.swapIndex();
				break;

				case 6:
				studentController.reclassify();
				break;

				case 7:
				studentContinue = 0;
				break;
			}
		}
    }

}


















