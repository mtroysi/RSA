package fr.m2sili.rsa;

import java.net.*;
import java.io.*;

/**
 * Created by Morgane TROYSI on 17/01/17.
 */
public class Bob {
    private static Helper helper;

    private PublicKey publicKey;
    private PrivateKey privateKey;
    //est-ce qu'on stocke p, q, n, m ?


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

		try{

			//création du server		
			ServerSocket serveur=new ServerSocket(port);
			
			//en attente de connexion
			System.out.println("En attente d'un client...");
			Socket connexion=serveur.accept();
			System.out.println("Client "+connexion.getInetAddress()+" connecté !");

			System.out.println();
	
			//flux de communication
			BufferedReader entree=new BufferedReader(new InputStreamReader(connexion.getInputStream()));
			PrintWriter sortie=new PrintWriter(new BufferedOutputStream(connexion.getOutputStream()));
			
			String saisie=null;

			//boucle de lecture
			while(true){	
				//lecture de la chaine
				saisie=entree.readLine();
				System.out.println("Chaine reçu par le client : "+saisie);
			
				//test fin
				if(saisie.equals("Over&Out"))
					break;
	
				//envoi	de la chaine
				saisie+=" - Ok";	
				sortie.println(saisie);
				System.out.println("Chaine renvoyée au client : "+saisie);
				
				//nettoyage
				sortie.flush();

				System.out.println();
			}
	
			//fermeture du serveur
			connexion.close();
			System.out.println("Fermeture du serveur...");

		}catch(IOException E){
			System.out.println("Erreur dans le fonctionnement du serveur !");
		}
    }
}
