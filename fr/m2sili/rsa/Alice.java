package fr.m2sili.rsa;

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

			String saisie=null;

			//boucle de saisie
			while(true){
				//lecture de la chaine
				Scanner sc=new Scanner(System.in);
				System.out.print("Entrez une chaine à envoyer au serveur : ");
				saisie=sc.nextLine();

				//envoi de la chaine
				sortie.println(saisie);

				//nettoyage
				sortie.flush();

				//test fin
				if(saisie.equals("Over&Out"))
					break;

				//lecture de la reception
				saisie=entree.readLine();
				System.out.println("Chaine reçu par le serveur : "+saisie);
				System.out.println();
			}

			//fermeture du client
			connexion.close();
			System.out.println("Fermeture du client...");

		}catch(Exception E){
			System.out.println("Erreur dans le fonctionnement du client !");
		}

    }
}
