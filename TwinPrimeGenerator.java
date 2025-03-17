public class TwinPrimeGenerator {

    // Check if a number is prime
    private static boolean isPrime(int num) {
        if (num < 2) return false;
        for (int i = 2; i * i <= num; i++) {
            if (num % i == 0) return false;
        }
        return true;
    }

    // Generate the next twin prime in the range [min, max]
    public static int generateTwinPrime(int min, int max) {
        for (int i = min; i <= max; i++) {
            if (isPrime(i) && isPrime(i + 2)) {
                return i;
            }
        }
        throw new IllegalArgumentException("No twin primes found in the specified range.");
    }
}
