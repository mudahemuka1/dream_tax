import java.util.Scanner;

public class Registration {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String nid = "", firstName = "", telephone = "", email = "";

        System.out.println("=== Online Registration ===");

        while (nid.isEmpty()) {
            System.out.print("Enter National ID (NID): ");
            nid = scanner.nextLine().trim();
        }
        while (firstName.isEmpty()) {
            System.out.print("Enter First Name: ");
            firstName = scanner.nextLine().trim();
        }
        while (telephone.isEmpty()) {
            System.out.print("Enter Telephone: ");
            telephone = scanner.nextLine().trim();
        }
        while (email.isEmpty()) {
            System.out.print("Enter Email: ");
            email = scanner.nextLine().trim();
        }

        System.out.println("\n=== Registration Successful ===");
        System.out.println("National ID: " + nid);
        System.out.println("First Name: " + firstName);
        System.out.println("Telephone: " + telephone);
        System.out.println("Email: " + email);
        System.out.println("Thank you for your registration!");
    }
}