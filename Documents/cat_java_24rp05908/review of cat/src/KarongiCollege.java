import java.util.Scanner;

// Parent class
class KarangiCollege {
    String collegeName;
    String location;

    // Constructor to set common data
    KarangiCollege(String collegeName, String location) {
        this.collegeName = collegeName;
        this.location = location;
    }

    void displayCollege() {
        System.out.println("College Name: " + collegeName);
        System.out.println("Location: " + location);
    }
}

// Child class for Student
class Student extends KarangiCollege {
    String studentName;

    Student(String collegeName, String location, String studentName) {
        super(collegeName, location); // Calls parent class constructor
        this.studentName = studentName;
    }

    void displayStudent() {
        displayCollege(); // Show college info
        System.out.println("Student Name: " + studentName);
        System.out.println("-----------------------");
    }
}

// Child class for Teacher
class Teacher extends KarangiCollege {
    String teacherName;

    Teacher(String collegeName, String location, String teacherName) {
        super(collegeName, location);
        this.teacherName = teacherName;
    }

    void displayTeacher() {
        displayCollege();
        System.out.println("Teacher Name: " + teacherName);
        System.out.println("-----------------------");
    }
}

// Main Class
public class KarongiCollege {
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);

        // Ask for college information
        System.out.print("Enter College Name: ");
        String collegeName = input.nextLine();

        System.out.print("Enter Location: ");
        String location = input.nextLine();

        // Ask for student info
        System.out.print("Enter Student Name: ");
        String studentName = input.nextLine();

        // Ask for teacher info
        System.out.print("Enter Teacher Name: ");
        String teacherName = input.nextLine();

        System.out.println("\n===== INFORMATION OUTPUT =====");

        // Create and display Student object
        Student s1 = new Student(collegeName, location, studentName);
        s1.displayStudent();

        // Create and display Teacher object
        Teacher t1 = new Teacher(collegeName, location, teacherName);
        t1.displayTeacher();

        input.close();
    }
}
