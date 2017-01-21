package fr.m2sili.rsa;

/**
 * Created by Morgane TROYSI on 21/01/17.
 */
public class Client {
    private static Helper helper = new Helper();

    private PublicKey publicKey;
    private PrivateKey privateKey;


    public PublicKey getPublicKey() {
        return publicKey;
    }

    public void setPublicKey(PublicKey publicKey) {
        this.publicKey = publicKey;
    }

    public PrivateKey getPrivateKey() {
        return privateKey;
    }

    public void setPrivateKey(PrivateKey privateKey) {
        this.privateKey = privateKey;
    }

    public static Helper getHelper() {
        return helper;
    }
}
