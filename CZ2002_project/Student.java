public class Student
{
    String studentName;
    String matricNumber;
    String gender;
    String nationality;
    String account;
    String major;
    String[] coursesTaken;
    int year;

    public Student(String studentName, String matricNumber, String gender, String nationality, String account, String major, String[] coursesTaken, int year)
    {
        this.studentName = studentName;
        this.matricNumber = matricNumber;
        this.gender = gender;
        this.nationality = nationality;
        this.account = account;
        this.major = major;
        this.coursesTaken = coursesTaken;
        this.year = year;
    }
}