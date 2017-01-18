package fr.m2sili.rsa;

import java.math.BigInteger;
import java.util.Random;

/**
 * Created by Morgane TROYSI on 17/01/17.
 */

public class Helper {
    /**
     * Génère aléatoirement un grand entier premier
     * @return p (ou q) un grand entier premier
     */
    public BigInteger generatePrime() {
        return BigInteger.probablePrime(500, new Random());
    }

    /**
     * Génère aléatoirement un petit entier impair et premier avec le nombre  passé en paramètre
     * @param m un entier : m = (p-1) * (q-1)
     * @return randomLong un petit entier impair et premier avec le nombre passé en paramètre
     */
    public BigInteger generatePrimeWith(BigInteger m) {
        Random rnd = new Random();
        long randomLong = rnd.nextLong();
        // Deux entiers sont premiers entre eux si leur PGCD est égal à 1
        // L'entier généré doit être impair
        while(randomLong % 2 != 0 && !m.gcd(BigInteger.valueOf(randomLong)).equals(1)) {
            randomLong = rnd.nextLong();
        }
        return BigInteger.valueOf(randomLong);
    }

    /**
     * Génère un nombre vérifiant l'équation diophantienne e*u+m*v = PGCD(e, m) à l'aide de l'algorithme d'Euclide "étendu"
     * @param m un entier : m = (p-1) * (q-1)
     * @param e un entier premier avec m
     * @return u Génère un nombre vérifiant l'équation diophantienne
     */
    public BigInteger caclulCoefficientBezout(BigInteger m, BigInteger e) {
        return null;
    }

    /**
     * Génère une clé publique
     * @return clé publique
     */
    public Key generatePublicKey() {
        return null;
    }

    /**
     * Génère une clé privée
     * @return clé privée
     */
    public Key generatePrivateKey() {
        return null;
    }
}
