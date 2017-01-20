package fr.m2sili.rsa;

import java.math.BigInteger;
import java.net.*;
import java.io.*;

/**
 * Created by Morgane TROYSI on 17/01/17.
 */
public class Bob {
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
		Bob.helper = helper;
	}

    public static void main(String[] args) {
		//test du nombre de paramètre
		if(args.length!=1){
			System.out.println("Nombre d'arguments incorrect : il faut seulement le port d'écoute en paramètre.");		
			System.exit(-1);	//fin du programme
		}

		//variable port
		int port=Integer.parseInt(args[0]);
		System.out.println("Le port choisi pour le serveur est : "+port);			
		System.out.println("Serveur lancé !");
		System.out.println();

		Bob bob = new Bob();
		bob.setPublicKey(bob.getHelper().generatePublicKey());
		bob.setPrivateKey(bob.getHelper().generatePrivateKey(bob.getPublicKey()));
		PublicKey aliceKey = new PublicKey();

		try{

			//création du server		
			ServerSocket serveur = new ServerSocket(port);
			
			//en attente de connexion
			System.out.println("En attente d'un client...");
			Socket connexion = serveur.accept();
			System.out.println("Client "+ connexion.getInetAddress() + " connecté !");

			System.out.println();
	
			//flux de communication
			BufferedReader entree = new BufferedReader(new InputStreamReader(connexion.getInputStream()));
			PrintWriter sortie = new PrintWriter(new BufferedOutputStream(connexion.getOutputStream()));
			
			String saisie = null;

			//Réception de la clé publique d'Alice
			saisie=entree.readLine();
			aliceKey.setN(new BigInteger(saisie.split(":")[0]));
			aliceKey.setE(new BigInteger(saisie.split(":")[1]));
			System.out.println("Bob > Clé publique reçue.");

			//Envoi de la clé clé publique à Alice
			System.out.println("Bob > Voici ma clé publique : " + bob.getPublicKey().getN() + " " + bob.getPublicKey().getE());
			sortie.println(bob.getPublicKey().getN() + ":" + bob.getPublicKey().getE());
			sortie.flush();

			//boucle de lecture
			while(true) {
				//lecture du message chiffré
				saisie=entree.readLine();
				System.out.println("Bob > Message reçu. Déchiffrement en cours...");

				//déchiffrement du message
				saisie = bob.getHelper().decryption(bob.getPrivateKey(), saisie);
				sortie.println(bob.getHelper().encryption(saisie, aliceKey));
				System.out.println("Bob > Message chiffré envoyé");

				//nettoyage
				sortie.flush();

				System.out.println();
				break;
			}
	
			//fermeture du serveur
			connexion.close();
			System.out.println("Fermeture du serveur...");

		} catch(IOException E) {
			System.out.println("Erreur dans le fonctionnement du serveur !");
		}
    }
}
