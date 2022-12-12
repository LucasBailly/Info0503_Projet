import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.Headers;
import org.json.JSONObject;
import scv.*;

import java.io.*;
import java.net.Socket;
import java.net.URI;
import java.net.UnknownHostException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class CommandeHandler implements HttpHandler {
    private static final int portEcouteFordTCP = 5000;
    private static final int portEcouteRenaultTCP = 5002;

    private Constructeur constructeur;
    private int port;

    public CommandeHandler(Constructeur c, int port){
        constructeur= new Constructeur(c);
        this.port = port;
    }

    public void handle(HttpExchange t) {
        String reponse = "";
        String nomConstructeur = "";
        String nomModele = "";
        String nomMotorisation = "";
        String nomCouleur = "";
        String nomOption = "";

        reponse += "<!DOCTYPE html>\n" +
                "<html lang=\"fr\">\n" +
                "<head>\n" +
                "    <meta charset=\"UTF-8\">\n" +
                "    <title>Concessionnaire</title>\n" +
                "    <link rel=\"stylesheet\" href=\"https://stackpath.bootstrapcdn.com/bootstrap/4.1.3/css/bootstrap.min.css\">\n" +
                "</head>";

        // Récupération des données en GET
        URI requestedUri = t.getRequestURI();
        String query = requestedUri.getRawQuery();

        // Utilisation d'un flux pour lire les données du message Http
        BufferedReader br = null;
        try {
            br = new BufferedReader(new InputStreamReader(t.getRequestBody(), "utf-8"));
        } catch (UnsupportedEncodingException e) {
            System.err.println("Erreur lors de la récupération du flux " + e);
            System.exit(0);
        }

        // Récupération des données en POST
        try {
            query = br.readLine();
        } catch (IOException e) {
            System.err.println("Erreur lors de la lecture d'une ligne " + e);
            System.exit(0);
        }


        if (query == null)
            reponse += "<b>Aucune</b></p>";
        else {
            String mots[] = query.split("&");
            nomConstructeur = constructeur.getNom();
            nomMotorisation = mots[0].split("Motorisation=")[1];
            nomCouleur = mots[1].split("Couleur=")[1];
            nomOption = mots[2].split("Options=")[1];
            nomModele = mots[3].split("Modele=")[1];

            Calendar calendar = Calendar.getInstance();
            calendar.add(Calendar.DATE, 14);
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");


            reponse += "<body>\n" +
                    "    <div class=\"container\">\n" +
                    "       <h1>Résumé de la commande :</h1>\n" +
                    "       <h2>Constructeur :</h2><p> " + nomConstructeur + "</p>\n" +
                    "       <h3>Modele :</h3><p>" + nomModele + "</p>\n" +
                    "       <h4>Motorisation :</h4><p>" + nomMotorisation + "</p>\n" +
                    "       <h4>Couleur :</h4> <p> " + nomCouleur + "</p>\n" +
                    "       <h4>Option :</h4><p> " + nomOption + "</p>\n\n\n";

        }

        Commande commande = new Commande(1, new Constructeur(nomConstructeur), new Modele(nomModele, Motorisation.valueOf(nomMotorisation), Couleur.valueOf(nomCouleur), Options.valueOf(nomOption)));
        JSONObject commandeJSON = commande.toJSON();
        String commandeString = commandeJSON.toString();
        Socket socket = null;
        if (this.port == 8080) {
            try {
                socket = new Socket("localhost", portEcouteFordTCP);
            } catch (UnknownHostException e) {
                System.err.println("Erreur sur l'hôte : " + e);
                System.exit(0);
            } catch (IOException e) {
                System.err.println("Création de la socket impossible : " + e);
                System.exit(0);
            }

            BufferedReader input = null;
            PrintWriter output = null;
            try {
                input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                output = new PrintWriter(new BufferedWriter(new OutputStreamWriter(socket.getOutputStream())), true);
            } catch (IOException e) {
                System.err.println("Association des flux impossible : " + e);
                System.exit(0);
            }

            output.println(commandeString);


            //Lecture de la date de livraison
            String message = "";
            try {
                message = input.readLine();
                reponse +=
                        "       Livraison prévue pour :" + message + "</div> +\n" +
                                "</body>";
            } catch (IOException e) {
                System.err.println("Erreur lors de la lecture : " + e);
                System.exit(0);
            }

            // Envoi de l'en-tête Http
            try {
                Headers h = t.getResponseHeaders();
                h.set("Content-Type", "text/html; charset=utf-8");
                t.sendResponseHeaders(200, reponse.getBytes().length);
            } catch (IOException e) {
                System.err.println("Erreur lors de l'envoi de l'en-tête : " + e);
                System.exit(0);
            }

            // Envoi du corps (données HTML)
            try {
                OutputStream os = t.getResponseBody();
                os.write(reponse.getBytes());
                os.close();
            } catch (IOException e) {
                System.err.println("Erreur lors de l'envoi du corps : " + e);
            }
        } else {
            try {
                socket = new Socket("localhost", portEcouteRenaultTCP);
            } catch (UnknownHostException e) {
                System.err.println("Erreur sur l'hôte : " + e);
                System.exit(0);
            } catch (IOException e) {
                System.err.println("Création de la socket impossible : " + e);
                System.exit(0);
            }

            BufferedReader input = null;
            PrintWriter output = null;
            try {
                input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                output = new PrintWriter(new BufferedWriter(new OutputStreamWriter(socket.getOutputStream())), true);
            } catch (IOException e) {
                System.err.println("Association des flux impossible : " + e);
                System.exit(0);
            }

            output.println(commandeString);

            //Lecture de la date de livraison
            String message = "";
            try {
                message = input.readLine();
                reponse +=
                        "       Livraison prévue pour :" + message + "</div> +\n" +
                                "</body>";
            } catch (IOException e) {
                System.err.println("Erreur lors de la lecture : " + e);
                System.exit(0);
            }

            // Envoi de l'en-tête Http
            try {
                Headers h = t.getResponseHeaders();
                h.set("Content-Type", "text/html; charset=utf-8");
                t.sendResponseHeaders(200, reponse.getBytes().length);
            } catch (IOException e) {
                System.err.println("Erreur lors de l'envoi de l'en-tête : " + e);
                System.exit(0);
            }

            // Envoi du corps (données HTML)
            try {
                OutputStream os = t.getResponseBody();
                os.write(reponse.getBytes());
                os.close();
            } catch (IOException e) {
                System.err.println("Erreur lors de l'envoi du corps : " + e);
            }
        }
    }
}
