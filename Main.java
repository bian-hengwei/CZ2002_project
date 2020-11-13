import java.util.Scanner;
import java.util.Map;
import java.util.HashMap;
import java.util.Set;

public class Main {

    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);
        StudentController sc = new StudentController();
        System.out.println("Checking student... account: hbian001");
        System.out.println("password: ilove2002");
        sc.login("hbian001", "ilove2002");
    }

}