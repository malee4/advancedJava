package Algorithm;

public class AlgoExp {
    public static void main(String[] args) {
        // System.out.println(prime(3));
        // System.out.println(prime(37));
        // System.out.println(prime(99));

        long startTime = System.currentTimeMillis(); 
        for (int i = 0; i < 100_000; i++) {
            prime(i);
        }
        long endTime = System.currentTimeMillis();
        System.out.println(endTime - startTime);

        startTime = System.currentTimeMillis(); 
        for (int i = 0; i < 100_000; i++) {
            primeOptimized(i);
        }
        endTime = System.currentTimeMillis();
        System.out.println(endTime - startTime);

        startTime = System.currentTimeMillis(); 
        for (int i = 0; i < 100_000; i++) {
            primeSqrt(i);
        }
        endTime = System.currentTimeMillis();
        System.out.println(endTime - startTime);


        

    }

    public static boolean prime(int n) {
        // conditions to be prime
        // can't have any factors other than one and itself
        
        for (int i = 2; i < n; i++) {
            if (n % i == 0) return false;
            else continue;
        }
        return true;
    }

    public static boolean primeOptimized(int n) {
        // conditions to be prime
        // can't have any factors other than one and itself

        for (int i = 2; i < n / 2; i++) {
            if (n % i == 0) return false;
            else continue;
        }
        return true;
    }

    public static boolean primeSqrt(int n) {
        // conditions to be prime
        // can't have any factors other than one and itself

        // for (int i = 2; i < Math.sqrt(n); i++) {
        //     if (n % i == 0) return false;
        //     else continue;
        // }
        // return true;

        for (int i = 2; i < n / (i-1); i++) {
            if (n % i == 0) return false;
            else continue;
        }
        return true;
    }
}
