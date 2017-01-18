package fr.m2sili.rsa;

import java.math.BigInteger;

/**
 * Created by Morgane TROYSI on 17/01/17.
 */

public class PrivateKey {
    /**
     * u = premier coefficient de bézout (important & secret)
     */
    private BigInteger u;
    
    /**
     * v = deuxieme coefficient de bézout (inutile)
     */
    private BigInteger v;
    
    /**
     * n = vient de la clé publique
     */
    private BigInteger n;

    public BigInteger getU() {
        return u;
    }

    public void setU(BigInteger u) {
        this.u =u;
    }
    
    public BigInteger getN() {
        return n;
    }

    public void setN(BigInteger n) {
        this.n =n;
    }
    
    public BigInteger getV() {
        return v;
    }

    public void setV(BigInteger v) {
        this.v = v;
    }
}
