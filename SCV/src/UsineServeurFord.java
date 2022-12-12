import org.json.JSONObject;
import scv.*;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.InputStreamReader;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.PrintWriter;

import java.net.*;
import java.nio.Buffer;

public class UsineServeurFord {

    public static final int portEcouteTCP = 5001;

    public static final int portEcouteUDP = 2025;
    public static final int portReceptionUDP = 2026;

    public static void main(String[] args) {
        //Création d'un objet Usine et Cosntructeur pour permettre la récupération de la commande
        Parking parking1 = new Parking(5,200);
        Parking parking2 = new Parking(3, 150);
        Parking parking3 = new Parking(10, 50);
        Parking[] parkings = {parking1, parking2, parking3} ;
        Constructeur constructeur_1 = new Constructeur("Ford");
        Usine usine = new Usine(constructeur_1, parkings);
        Modele modele = new Modele("Focus", Motorisation.V6,Couleur.VERT,Options.DIRECTION_ASSISTE);
        usine.creerVehicule(modele);

        //Partie TCP de l'Usine :
        Socket socketConstructeur = null;

        try{
            socketConstructeur = new Socket("localhost",portEcouteTCP);
        }catch (UnknownHostException e){
            System.err.println("Erreur sur l'hôte : " + e);
            System.exit(0);
        }
        catch (IOException e ){
            System.err.println("Création de la socket impossible : " + e);
            System.exit(0);
        }

        BufferedReader input = null;
        PrintWriter output = null;
        try{
            input = new BufferedReader(new InputStreamReader(socketConstructeur.getInputStream()));
            output = new PrintWriter(new BufferedWriter(new OutputStreamWriter(socketConstructeur.getOutputStream())),true);
        }catch (IOException e){
            System.err.println("Association de flux impossible : " + e);
            System.exit(0);
        }

        String commande = "";
        try{
            commande = input.readLine();
        }catch (IOException e){
            System.err.println("Erreur lors de la lecture : " + e);
            System.exit(0);
        }
        System.out.println("Lu : " + commande);

        JSONObject commandeJSON = new JSONObject(commande);
        Commande commande1 = Commande.fromJSON(commandeJSON);
        System.out.println(commande1);
        String emplacement = usine.chercherVehicule(commande1);

        if (!emplacement.equals("-1")){
            output.println(emplacement);
        }else{
            output.println("Nous n'avons pas la voiture en stock");
            //Partie UDP du serveur Usine
            // Création de la socket
            DatagramSocket socket = null;
            DatagramSocket socket2 = null;
            // Création du message
            DatagramPacket msgReponse = null;

            try {
                socket = new DatagramSocket(portEcouteUDP);
            } catch(SocketException e) {
                System.err.println("Erreur lors de la création de la socket : " + e);
                System.exit(0);
            }

            // Création de la socket
            try {
                socket2 = new DatagramSocket();
            } catch(SocketException e) {
                System.err.println("Erreur lors de la création de la socket : " + e);
                System.exit(0);
            }

            byte[] tampon = new byte[1024];
            DatagramPacket msg = new DatagramPacket(tampon, tampon.length);

            try{
                socket.receive(msg);
                String texte = new String(msg.getData(), 0 , msg.getLength());
                System.out.println("Lu : " + texte);
            }catch (IOException e){
                System.err.println("Erreur lors de la reception du message : " + e);
                System.exit(0);
            }

            socket.close();
            socket2.close();
        }

        try{
            input.close();
            output.close();
            socketConstructeur.close();
        }catch (IOException e){
            System.err.println("Erreur lors de la fermeture des flux et de la socket : " + e);
            System.exit(0);
        }

    }
}
