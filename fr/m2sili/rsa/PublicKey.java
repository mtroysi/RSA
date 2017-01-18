package fr.m2sili.rsa;

import java.math.BigInteger;

/**
 * Created by Morgane TROYSI on 17/01/17.
 */

public class PublicKey {
    /**
     * n = p * q
     */
    private BigInteger n;
    
    /**
     * m = (p-1) * (q-1)
     */
    private BigInteger m;

    /**
     * e = exposant public
     */
    private BigInteger e;
    
    /**
     * p = premier grand nombre premier choisi
     */
    private BigInteger p;
    
    /**
     * q = deuxieme grand nombre premier choisi
     */
    private BigInteger q;


    public BigInteger getN() {
        return n;
    }

    public void setN(BigInteger n) {
        this.n = n;
    }

    public BigInteger getM() {
        return m;
    }

    public void setM(BigInteger m) {
        this.m = m;
    }

    public BigInteger getE() {
        return e;
    }

    public void setE(BigInteger e) {
        this.e = e;
    }

    public BigInteger getP() {
        return p;
    }

    public void setP(BigInteger p) {
        this.p = p;
    }

    public BigInteger getQ() {
        return q;
    }

    public void setQ(BigInteger q) {
        this.q = q;
    }
}
