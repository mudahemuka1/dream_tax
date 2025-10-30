public class Calculator {


    public int add(int a, int b) {
        return a + b;
    }


    public double add(double a, double b) {
        return a + b;
    }


    public int add(int[] numbers) {
        int sum = 0;
        for (int num : numbers) {
            sum += num;
        }
        return sum;
    }

    public static void main(String[] args) {
        Calculator calc = new Calculator();


        System.out.println("Adding two integers: " + calc.add(5, 3));
        System.out.println("Adding two doubles: " + calc.add(2.5, 3.7));

        int[] numbers = {1, 2, 3, 4, 5};
        System.out.println("Adding array of integers: " + calc.add(numbers));
    }
}
