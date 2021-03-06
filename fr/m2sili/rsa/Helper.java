package fr.m2sili.rsa;

import java.math.BigInteger;
import java.util.Random;

/**
 * Created by Morgane TROYSI on 17/01/17.
 */

public class Helper {
    /**
     * Génère aléatoirement un grand entier premier
     * @return un grand entier premier
     */
    public BigInteger generatePrime() {
        return BigInteger.probablePrime(1663, new Random());
    }

    /**
     * Génère aléatoirement un grand entier premier qui doit être différent de celui passé en paramètre
     * @return un grand entier premier différent de celui passé en paramètre
     */
    public BigInteger generatePrime(BigInteger p) {
        BigInteger q = BigInteger.probablePrime(1663, new Random());
        while (q.equals(p)) {
            q = BigInteger.probablePrime(1663, new Random());
        }
        return q;
    }

    /**
     * Génère aléatoirement un petit entier impair et premier avec le nombre  passé en paramètre
     * @param m un grand entier
     * @return randomLong un petit entier impair et premier avec le nombre passé en paramètre
     */
    public BigInteger generatePrimeWith(BigInteger m) {
        Random rnd = new Random();
        long randomLong = rnd.nextLong();
        // Deux entiers sont premiers entre eux si leur PGCD est égal à 1
        // L'entier généré doit être impair
        while(randomLong % 2 == 0 || !m.gcd(BigInteger.valueOf(randomLong)).equals(BigInteger.ONE) || randomLong < 0) {
            randomLong = rnd.nextLong();
        }
        return BigInteger.valueOf(randomLong);
    }


    /**
     * Génère une clé publique
     * @return clé publique
     */
    public PublicKey generatePublicKey() {
        PublicKey publicKey = new PublicKey();
        // Génération de q et p
        publicKey.setP(this.generatePrime());
        publicKey.setQ(this.generatePrime(publicKey.getP()));

        // Calcul de n et m
        publicKey.setN(publicKey.getP().multiply(publicKey.getQ()));
        BigInteger first = publicKey.getP().subtract(BigInteger.ONE);
        BigInteger second = publicKey.getQ().subtract(BigInteger.ONE);
        publicKey.setM(first.multiply(second));

        // Calcul de e l'exposant public
        publicKey.setE(this.generatePrimeWith(publicKey.getM()));

        return publicKey;
    }

    /**
     * Génère une clé privée
     * @param pbk la clé publique pour génerer la clé privée
     * @return clé privée
     */
	public PrivateKey generatePrivateKey(PublicKey pbk){
    	PrivateKey pvk = new PrivateKey();
    	
		//Entrées
		BigInteger a=pbk.getE(), b=pbk.getM(); 			// entiers naturels en entrée a = e et b = m 
	
		//Sorties
		BigInteger r; 									// entiers relatifs tels que r = pgcd(a, b) et r = a*u+b*v 
	
		//Init
		BigInteger r1=a, r2=b, u1=BigInteger.ONE, v1=BigInteger.ZERO, u2=BigInteger.ZERO, v2=BigInteger.ONE; 	
		BigInteger q;									//quotient entier
		BigInteger rs, us, vs;							//variables de stockages    	
	
		while(r2 != BigInteger.ZERO){
			q = r1.divide(r2);
			rs = r1; us = u1; vs = v1;
			r1 = r2; u1 = u2; v1 = v2;
			r2 = rs.subtract(q.multiply(r2)); u2 = us.subtract(q.multiply(u2)); v2 = vs.subtract(q.multiply(v2));
		}
	
		BigInteger k = BigInteger.valueOf(-1);
		while(u1.compareTo(BigInteger.valueOf(2)) != 1 || u1.compareTo(b) != -1 ){
			u1 = u1.subtract(k.multiply(b));
			k = k.subtract(BigInteger.valueOf(-1));
		}
	
		pvk.setN(pbk.getN());
		pvk.setU(u1);
		pvk.setV(v1);
    	
        return pvk;
    }

    /**
     * Chiffre le message passé en paramètre grâce à la clé publique également fournie en paramètre
     * @param message le message à chiffrer
     * @param key la clé publique pour chiffrer le message
     * @return une chaîne de caractères correspondant au message chiffré, chaque nombre séparé par un espace.
     */
    public String encryption(String message, PublicKey key) {
        String result = "";
        for(int i = 0; i < message.length(); i++) {
            int c = (int) message.charAt(i);
            BigInteger s = BigInteger.valueOf(c).modPow(key.getE(), key.getN());
            result += s.toString() + " ";
        }
        return result;
    }
    
    /**
     * Dechiffre un texte avec une clé privée
     * @param pvk la clé privée pour déchiffrer le texte
     * @param txt le texte a déchiffrer
     * @return le texte déchiffré
     */
	public String decryption(PrivateKey pvk, String txt){
		//variables
		String[] tab = txt.split(" ");
		String res = "";
		int tmp_int;
		String tmp_str;
		BigInteger val_char;
		
		//decryptage
		for(int i=0; i<tab.length; ++i){
			val_char = new BigInteger(tab[i]);
			val_char = val_char.modPow(pvk.getU(), pvk.getN());
			tmp_str = val_char + "";
			tmp_int = Integer.parseInt(tmp_str);
			res += (char)tmp_int;
		}
		return res;
	}
}
