public class ExceptionExamples {
    public static void main(String[] args) {
        System.out.println("1. ArithmeticException:");
        try {
            int result = 10 / 0;
        } catch (ArithmeticException e) {
            System.out.println("   Error: " + e.getMessage());
        }


        System.out.println("\n2. NullPointerException:");
        try {
            String str = null;
            System.out.println(str.length());
        } catch (NullPointerException e) {
            System.out.println("   Error: " + e.getMessage());
        }


        System.out.println("\n3. ArrayIndexOutOfBoundsException:");
        try {
            int[] arr = new int[5];
            arr[10] = 50;
        } catch (ArrayIndexOutOfBoundsException e) {
            System.out.println("   Error: " + e.getMessage());
        }


        System.out.println("\n4. NumberFormatException:");
        try {
            String numStr = "abc";
            int num = Integer.parseInt(numStr);
        } catch (NumberFormatException e) {
            System.out.println("   Error: " + e.getMessage());
        }


        System.out.println("\n5. StringIndexOutOfBoundsException:");
        try {
            String text = "Hello";
            char ch = text.charAt(10);
        } catch (StringIndexOutOfBoundsException e) {
            System.out.println("   Error: " + e.getMessage());
        }
    }
}