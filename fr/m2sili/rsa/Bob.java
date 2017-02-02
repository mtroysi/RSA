package fr.m2sili.rsa;

import java.math.BigInteger;
import java.net.*;
import java.io.*;

/**
 * Created by Morgane TROYSI on 17/01/17.
 */
public class Bob {

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

		// Génération des clés
		Client bob = new Client();
		bob.setPublicKey(bob.getHelper().generatePublicKey());
		bob.setPrivateKey(bob.getHelper().generatePrivateKey(bob.getPublicKey()));
		PublicKey aliceKey = new PublicKey();

		try {

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
			System.out.println("\033[1;33mBob >\033[1;37m Clé publique reçue.");

			//Envoi de la clé clé publique à Alice
			System.out.println("\033[1;33mBob >\033[1;37m Voici ma clé publique : " + bob.getPublicKey().getN() + " " + bob.getPublicKey().getE());
			sortie.println(bob.getPublicKey().getN() + ":" + bob.getPublicKey().getE());
			sortie.flush();
			System.out.println();

			//boucle de lecture
			boolean quit = true; 
			while(quit) {
				//lecture du message chiffré
				saisie=entree.readLine();
				System.out.println("\033[1;33mBob >\033[1;37m Message reçu. Déchiffrement en cours...");

				//déchiffrement du message
				saisie = bob.getHelper().decryption(bob.getPrivateKey(), saisie);
				System.out.println("\033[1;36mAlice >\033[1;37m " +saisie);
				
				//cas fermeture du serveur
				if(saisie.equals("exit") || saisie.equals("quit") || saisie.equals("end") || saisie.equals("disconnect")){
					quit = false;
				}
				
				sortie.println(bob.getHelper().encryption(saisie, aliceKey));
				System.out.println("\033[1;33mBob >\033[1;37m Message chiffré envoyé");
				System.out.println();

				//nettoyage
				sortie.flush();
			}
	
			//fermeture du serveur
			connexion.close();
			System.out.println("Fermeture du serveur...");

		} catch(IOException E) {
			System.out.println("Erreur dans le fonctionnement du serveur !");
		}
    }
}
