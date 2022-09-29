import java.util.*;

public class Main {

    public static double sqrt(double x) {
        if (x < 0) {
            String message = String.format("Expected non-negative number, got %.1f", x);
            throw new IllegalArgumentException(message);
        }
        return Math.sqrt(x);
    }

    /* Do not change code below */
    public static void main(String[] args) {
        String value = new Scanner(System.in).nextLine();
        try {
            double res = sqrt(Double.parseDouble(value));
            System.out.println(res);
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }
    }
}