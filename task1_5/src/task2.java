public class task2 {
    public static double calculateUn(int n) {
        if (n <= 1) {
            throw new IllegalArgumentException("n should be greater than 1");
        }
        return (double) n / (n + 1);
    }
    public static void main(String[] args) {
        double un = calculateUn(10);
        System.out.printf("%.6f\n", un);
        System.out.printf("Выражение не стремится ни к 0, ни к бесконечности, оно стремится к 1");
    }
}
