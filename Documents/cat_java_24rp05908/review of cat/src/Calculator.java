import java.util.Scanner;

class Calculator {

    // Method to add two numbers
    int add(int a, int b) {
        return a + b;
    }

    // Method to subtract two numbers
    int sub(int a, int b) {
        return a - b;
    }

    public static void main(String[] args) {

        Scanner input = new Scanner(System.in);
        Calculator calc = new Calculator();

        System.out.print("Enter first number: ");
        int num1 = input.nextInt();

        System.out.print("Enter second number: ");
        int num2 = input.nextInt();

        int sum = calc.add(num1, num2);
        int difference = calc.sub(num1, num2);

        System.out.println("Sum = " + sum);
        System.out.println("Difference = " + difference);

        input.close();
    }
}