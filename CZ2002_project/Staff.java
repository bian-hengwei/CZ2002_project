import java.io.*;

public class Staff extends People {
    public Staff(String id, String password, String name) {
        super(id, password, name);
    }

    public void writeInfo() {

    }

    public void printStudentList(int indexNumber) throws IOException {
        FileReader fRead;
        try {
            fRead = new FileReader("StudentData.csv");
            BufferedReader bRead = new BufferedReader(fRead);
            System.out.println("Student list by index number:");
            String[] studentList = new String[9];
            String line;
            while((line = bRead.readLine()) != null){
                studentList = line.split("\\s*,\\s*");
                System.out.printf("id: %s password: %s name: %s gender: %s nationality: %s account: %s major: %s coursesTaken: %s year %s", studentList[0],studentList[1],studentList[2],studentList[3],studentList[4],studentList[5],studentList[6],studentList[7],studentList[9]);
            }
            bRead.close();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }


}
