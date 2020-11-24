package src.view;

import src.boundary.FileHandler;
import src.model.Index;
import src.model.Student;

import java.util.Set;

public class StudentView {

    /**
    * print detail of student (student name, matric number, major and year).
    * @param student This passes in the model(student) into the method. the details of student will be printed using the getter of student
    */
    public void printModelDetail(Student student) {
        System.out.println("Student Name: " + student.getName());
        System.out.println("Matriculation Number: " + student.getMatricNo());
        System.out.println("Major: " + student.getMajor());
        System.out.println("Year: " + student.getYear());
        System.out.println("Number of AUs taken: " + student.getCurrentAu());
    }

    /**
    * print the courses registered by a student. if the student is not registered for any index, "Not registered to any course yet" will be printed.
    * @param indexes This passes in the all the indexes that a student registered. 
    */
    public void printCoursesRegistered(Set<Index> indexes) {
        if (indexes.size() == 0) {
            System.out.println("Not registered to any course yet");
            return;
        }
        System.out.println("Registered Courses:");
        for (Index idx : indexes) {
            System.out.printf("Course: %s %s, Index: %s, School: %s\n", idx.getCourseId(), idx.getCourseName(), 
                idx.getIndexNumber(), idx.getSchool());
        }
        System.out.println();
    }

    /**
    * print the courses that a student is put on waitlist. if the student is not on waitlist for any index, "Not registered on any waitlist" will be printed.
    * @param indexes This passes in the all the indexes that a student is on waitlist
    */
    public void printOnWaitlist(Set<Index> indexes) {
        if (indexes.size() == 0) {
            System.out.println("Not registered on any waitlist");
            return;
        }
        System.out.println("Courses on WaitList: ");
        for (Index idx : indexes) {
            System.out.printf("Course: %s %s, Index: %s, School: %s\n", idx.getCourseId(), idx.getCourseName(), 
                idx.getIndexNumber(), idx.getSchool());
        }
        System.out.println();
    }

    /** 
    * save the info of a student to student_information.csv. registered and waitlisted indexes are first concatenated into a string with "&" separating the indexes.
    * @param student This passes in the student whose information will be saved in this method
    */
    public void saveStudentInfo(Student student) {
        System.out.println("Saving student...");
        Set<Index> currentIndexes = student.getCurrentIndexes();
        Set<Index> onWaitlist = student.getOnWaitlist();
        String currentIndexesString = "";
        String onWaitlistString = "";
        
        for(Index idx : currentIndexes) {
            currentIndexesString += idx.getIndexNumber() + "&";
        }
        if (currentIndexesString != "") {
            currentIndexesString = currentIndexesString.substring(0, currentIndexesString.length()-1);
        }

        for(Index idx : onWaitlist) {
            onWaitlistString += idx.getIndexNumber() + "&";
        }
        if(onWaitlistString.length() > 0){
            onWaitlistString = onWaitlistString.substring(0, onWaitlistString.length()-1);
        }

        String takenCourses = String.join("&", student.getTakenCourses());

        String content = String.join(",", student.getAccount(), student.getName(), 
            student.getNationality(), student.getMatricNo(), 
            student.getMajor(), student.getYear(), takenCourses, 
            currentIndexesString, onWaitlistString, Integer.toString(student.getCurrentAu()), ";,");
        FileHandler.writeLine("student_information", student.getAccount(), content);
        System.out.println("Student information successfully saved");
    }
}