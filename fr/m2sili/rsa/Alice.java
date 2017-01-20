package fr.m2sili.rsa;

import java.math.BigInteger;
import java.net.*;
import java.io.*;
import java.util.Scanner;

/**
 * Created by Morgane TROYSI on 17/01/17.
 */
public class Alice {
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

	public static void setHelper(Helper helper) {
		Alice.helper = helper;
	}

	public static void main(String[] args) {
		Alice alice = new Alice();
		alice.setPublicKey(alice.getHelper().generatePublicKey());
		alice.setPrivateKey(alice.getHelper().generatePrivateKey(alice.getPublicKey()));
        PublicKey bobKey = new PublicKey();

    	//test du nombre de paramètre
		if(args.length!=2){
			System.out.println("Nombre d'arguments incorrect : il faut l'ip du serveur en premier paramètre et son port en second.");
			System.exit(-1);	//fin du programme
		}

		//variable port & ip
		int port=Integer.parseInt(args[1]);
		String ip=args[0];
		System.out.println("Client lancé !");
		System.out.println("L'ip du serveur choisi est : "+ip);
		System.out.println("Le port du serveur choisi est : "+port);
		System.out.println();

		try{

			//création du socket d'envoi
			Socket connexion=new Socket(ip,port);

			//flux de connexion
			PrintWriter sortie=new PrintWriter(connexion.getOutputStream());
			BufferedReader entree=new BufferedReader(new InputStreamReader(connexion.getInputStream()));

			String saisie = null;

            //Envoi de la clé clé publique à Bob
            System.out.println("Alice > Voici ma clé publique : " + alice.getPublicKey().getN() + " " + alice.getPublicKey().getE());
            sortie.println(alice.getPublicKey().getN() + ":" + alice.getPublicKey().getE());
            sortie.flush();

            //Réception clé publique Bob
            saisie=entree.readLine();
            bobKey.setN(new BigInteger(saisie.split(":")[0]));
            bobKey.setE(new BigInteger(saisie.split(":")[1]));
            System.out.println("Alice > Clé publique reçue.");

			//boucle de saisie
			while(true) {
				//lecture de la chaine
				Scanner sc = new Scanner(System.in);
				System.out.print("Alice > ");
				saisie = sc.nextLine();

				//envoi de la chaine
                System.out.println("Alice > Envoi du message chiffré en cours...");
				sortie.println(alice.getHelper().encryption(saisie, bobKey));

				//nettoyage
				sortie.flush();

				//lecture de la reception
				saisie = entree.readLine();
                System.out.println("Alice > Message reçu. Déchiffrement en cours...");
				System.out.println("Alice > Message reçu : " + alice.getHelper().decryption(alice.getPrivateKey(), saisie));
				System.out.println();

                break;
			}

			//fermeture du client
			connexion.close();
			System.out.println("Fermeture du client...");

		}
		catch(Exception E) {
			System.out.println("Erreur dans le fonctionnement du client !");
		}

    }
}
