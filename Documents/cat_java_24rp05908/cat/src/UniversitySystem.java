class Person {
    private String name;
    private int age;

    public Person(String name, int age) {
        this.name = name;
        this.age = age;
    }

    public void displayDetails() {
        System.out.println("Name: " + name + ", Age: " + age);
    }
}


class Student extends Person {
    private String registrationNumber;

    public Student(String name, int age, String registrationNumber) {
        super(name, age); // Call the superclass constructor
        this.registrationNumber = registrationNumber;
    }

    @Override
    public void displayDetails() {
        super.displayDetails();
        System.out.println("Registration Number: " + registrationNumber + " (Student)");
    }
}


class Instructor extends Person {
    private double salary;

    public Instructor(String name, int age, double salary) {
        super(name, age);
        this.salary = salary;
    }

    @Override
    public void displayDetails() {
        super.displayDetails();
        System.out.println("Salary: $" + salary + " (Instructor)");
    }
}


public class UniversitySystem {
    public static void main(String[] args) {
        Student student1 = new Student("Alice", 20, "S1001");
        Instructor instructor1 = new Instructor("Dr. Smith", 45, 75000.0);

        System.out.println("=== University Member Details ===");
        student1.displayDetails();
        System.out.println(); // Empty line for readability
        instructor1.displayDetails();
    }
    }
