import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.Headers;
import scv.*;
import scv.Vehicule;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.io.OutputStream;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URI;


public class OptionHandler implements HttpHandler {

    private Constructeur constructeur;
    private int portHTTP;

    public OptionHandler(Constructeur c, int p){
        constructeur= new Constructeur(c);
        portHTTP = p;
    }

    public void handle(HttpExchange t){



        String reponse = "<!DOCTYPE html>\n" +
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
            br = new BufferedReader(new InputStreamReader(t.getRequestBody(),"utf-8"));
        } catch(UnsupportedEncodingException e) {
            System.err.println("Erreur lors de la récupération du flux " + e);
            System.exit(0);
        }

        // Récupération des données en POST
        try {
            query = br.readLine();
        } catch(IOException e) {
            System.err.println("Erreur lors de la lecture d'une ligne " + e);
            System.exit(0);
        }



        Modele modele = new Modele(constructeur.getModele(query.split("Modele=")[1]));

        reponse += "<body>\n" +
                "<input name='Modele' hidden value='"+modele.getNom()+"'>"+
                "    <div class=\"container\">\n" +
                "       <h1>Constructeur :"+ constructeur.getNom() +"</h1>\n" +
                "       <h2 name='Modele'>"+ modele.getNom()+"</h2>\n"+
                "       <form method=\"POST\" action=\"http://localhost:"+portHTTP+"/commande\">";

            //SELECT MOTORISATION
            reponse +=
                        "       <h3>Choisir la Motorisation :</h3>\n"+
                        "       <div class=\"form-group\">\n" +
                        "           <select id=\"constructeurId\" name=\"Motorisation\" class=\"form-control\">\n";
            for (Motorisation i : modele.getMotorisationsPotentielles()) {
                reponse +=         "<option value='"+i+ "' selected>"+i+"</option>\n";
            }
            reponse +=  "           </select>\n" +
                        "       </div>\n" ;

            //SELECT COULEUR
            reponse +=
                        "       <h3>Choisir la Couleur :</h3>\n"+
                        "       <div class=\"form-group\">\n" +
                        "           <select id=\"constructeurId\" name=\"Couleur\" class=\"form-control\">\n";
            for (Couleur i : modele.getCouleursPotentielles()) {
                reponse +=         "<option value='"+i+ "' selected>"+i+"</option>\n";
            }
            reponse +=  "           </select>\n" +
                                " </div>\n";

        //SELECT OPTIONS
        reponse +=
                "       <h3>Choisir les Options :</h3>"+
                        "       <div class=\"form-group\">\n" +
                        "           <select id=\"constructeurId\" name=\"Options\" class=\"form-control\">\n";
        for (Options i : modele.getOptionsPotentielles()) {
            reponse +=         "<option value='"+i+ "' selected>"+i+"</option>\n";
        }
        reponse +=      "           </select>\n" +
                        " </div>\n";






            reponse +=  "       <button type=\"submit\" name='Modele' value='"+modele.getNom() +"' class=\"btn btn-primary mb-2\">Confirmer</button>"+
                        "       </form>\n" +
                        "</div>\n" +
                        "</body>";

        reponse +=
                "<?php\n" +
                "include 'Commande.php';"+
                "if(isset($_POST['confirmer'])) {\n" +

                "    $commande_1 = new Commande($constructeur, $modele, $moteur, $couleur, $option);\n" +

                "    $string_json = json_encode($commande_1);\n" +
                "    var_dump($string_json);\n" +

                "    $etudiant_clone = json_decode($string_json, true);\n" +
                "    var_dump($etudiant_clone[\"modele\"]);\n" +
                "?>";


        // Envoi de l'en-tête Http
        try {
            Headers h = t.getResponseHeaders();
            h.set("Content-Type", "text/html; charset=utf-8");
            t.sendResponseHeaders(200, reponse.getBytes().length);
        } catch(IOException e) {
            System.err.println("Erreur lors de l'envoi de l'en-tête : " + e);
            System.exit(0);
        }

        // Envoi du corps (données HTML)
        try {
            OutputStream os = t.getResponseBody();
            os.write(reponse.getBytes());
            os.close();
        } catch(IOException e) {
            System.err.println("Erreur lors de l'envoi du corps : " + e);
        }
    }
}
