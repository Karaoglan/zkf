import java.math.BigInteger;
import java.util.Random;

public class Main {
  public static void main(String[] args) {

    long p = 47;
    long q = 53;

    long plainNumber1 = 10;
    long plainNumber2 = -2;

    if (p == q) {
      System.err.println("p and q cannot be same");
    }

    long n = p * q;

    long gLambda = lowestCommonMultiplier(p - 1, q - 1);

    // random int relative prime to n^2 >= 0
    //new Random((long) n * n);
    long g = 69;//new Random(101).nextInt();

    long r = 113;//new Random(150).nextInt();

    if (greaterCommonDivisor(g, n * n) != 1) {
      System.err.println("WARNING: g is NOT relatively prime to n*n. Will not work!!!");
    }

    long l = (modPow(g, gLambda, n * n) - 1) / n;

    long gMu = modInverse(l, n);

    long k1 = modPow(g, plainNumber1, n * n);
    long k2 = modPow(r, n, n * n);

    long cipher = (k1 * k2) % (n * n);

    long lDecr = (modPow(cipher, gLambda, n * n) - 1) / n;

    long messageDecr = (lDecr * gMu) % n;

    System.out.println("p=" + p + "\tq=" + q);
    System.out.println("g=" + g + "\tr=" + r);
    System.out.println("================");
    System.out.println("Mu:\t\t" + gMu + "\tgLambda:\t" + gLambda);
    System.out.println("================");
    System.out.println("Public key (n,g):\t\t" + n + " " + g);
    System.out.println("Private key (lambda,mu):\t" + gLambda + " " + gMu);
    System.out.println("================");
    System.out.println("Message: " + plainNumber1);
    System.out.println("Cipher:\t\t" + cipher);
    System.out.println("Decrypted:\t" + messageDecr);
    System.out.println("================");
    System.out.println("Now we will add a ciphered value of 2 to the encrypted value");

    long k3 = modPow(g, plainNumber2, n * n);
    long cipher2 = (k3 * k2) % (n * n);
    System.out.println("Cipher2: " + cipher2);

    long cipherTotal = (cipher * cipher2) % (n * n);
    System.out.println("cipherTotal: " + cipherTotal);

    long l3 = (modPow(cipherTotal, gLambda, n * n) - 1) / n;
    long message2 = (l3 * gMu) % n;
    System.out.println("Result: " + message2);
  }

  static int modPow(long base, long exponent, long modulo) {
    BigInteger baseBig = BigInteger.valueOf(base);
    BigInteger exponentBig = BigInteger.valueOf(exponent);
    BigInteger moduloBig = BigInteger.valueOf(modulo);

    return (baseBig.modPow(exponentBig, moduloBig)).intValue();
  }

  static long modInverse(long a, long m)
  {
    a = a % m;
    for (long x = 1; x < m; x++)
      if ((a * x) % m == 1)
        return x;
    return 1;
  }


  static long greaterCommonDivisor(long a, long b) {
    if (b == 0) {
      return a;
    }
    return greaterCommonDivisor(b, a % b);
  }

  static long lowestCommonMultiplier(long a, long b) {
    return a * b / greaterCommonDivisor(a, b);
  }



}
