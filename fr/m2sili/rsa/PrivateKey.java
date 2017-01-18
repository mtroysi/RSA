package fr.m2sili.rsa;

import java.math.BigInteger;

/**
 * Created by Morgane TROYSI on 17/01/17.
 */

public class PrivateKey {
    /**
     * premier membre de la clé : n = p * q
     */
    private BigInteger n;

    /**
     * second membre de la clé :
     * si clé publique alors second = e = nombre premier avec m
     * si clé privée alors second = u coefficient e Bézout
     */
    private BigInteger second;

    public BigInteger getN() {
        return n;
    }

    public void setN(BigInteger n) {
        this.n = n;
    }

    public BigInteger getSecond() {
        return second;
    }

    public void setSecond(BigInteger second) {
        this.second = second;
    }
}
