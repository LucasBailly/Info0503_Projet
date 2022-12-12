import org.json.JSONObject;
import scv.Commande;
import scv.Constructeur;
import scv.Modele;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.InputStreamReader;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.PrintWriter;

import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class ConstructeurFordTCP {
    public static final int portEcouteTCP = 5001;
    public static final int portEcouteHttp = 5000;

    public static void main(String[] args) {

        ServerSocket socketServerTCP = null;

        try{
            socketServerTCP = new ServerSocket(portEcouteHttp);
        }catch (IOException e ){
            System.err.println("Création de la socket impossible : " + e);
            System.exit(0);
        }

        ServerSocket socketUsine = null;
        try {
            socketUsine = new ServerSocket(portEcouteTCP);
        } catch(IOException e) {
            System.err.println("Création de la socket impossible : " + e);
            System.exit(0);
        }

        Socket socketHttp = null;
        try{
            socketHttp = socketServerTCP.accept();
        }catch (IOException e){
            System.err.println("Erreur lors de l'attente d'une connexion : " + e);
            System.exit(0);
        }

        Socket socketTCP = null;
        try{
            socketTCP = socketUsine.accept();
        }catch (IOException e){
            System.err.println("Erreur lors de l'attente d'une connexion : " + e);
            System.exit(0);
        }


        BufferedReader inputHttp = null;
        PrintWriter outputHttp = null;
        try {
            inputHttp = new BufferedReader(new InputStreamReader(socketHttp.getInputStream()));
            outputHttp = new PrintWriter(new BufferedWriter(new OutputStreamWriter(socketHttp.getOutputStream())), true);
        } catch(IOException e) {
            System.err.println("Association des flux impossible : " + e);
            System.exit(0);
        }

        BufferedReader inputUsine = null;
        PrintWriter outputUsine = null;
        try {
            inputUsine = new BufferedReader(new InputStreamReader(socketTCP.getInputStream()));
            outputUsine = new PrintWriter(new BufferedWriter(new OutputStreamWriter(socketTCP.getOutputStream())), true);
        } catch(IOException e) {
            System.err.println("Association des flux impossible : " + e);
            System.exit(0);
        }

        String message = "";
        try{
            message = inputHttp.readLine();
        }catch (IOException e){
            System.err.println("Erreur lors de la lecture : " + e);
            System.exit(0);
        }
        System.out.println("Commande: " + message);

        JSONObject json = new JSONObject(message);
        Commande commande = Commande.fromJSON(json);
        JSONObject objet = commande.toJSON();
        message = objet.toString();
        outputUsine.println(objet);

        try {
            message = inputUsine.readLine();
        } catch(IOException e) {
            System.err.println("Erreur lors de la lecture : " + e);
            System.exit(0);
        }
        System.out.println("Emplacement sur le parking: " + message);

        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DATE, 14);
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        String dateLivraison = sdf.format(calendar.getTime());
        outputHttp.println(dateLivraison);

        try {
            inputHttp.close();
            inputUsine.close();
            outputHttp.close();
            outputUsine.close();
            socketHttp.close();
            socketUsine.close();
        } catch(IOException e) {
            System.err.println("Erreur lors de la fermeture des flux et de la socket : " + e);
            System.exit(0);
        }

    }
}
