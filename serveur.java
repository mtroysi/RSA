import java.net.*;
import java.io.*;

public class serveur {
//class gérant le serveur
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
