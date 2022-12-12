import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import scv.Constructeur;
import scv.Modele;

import java.io.*;
import java.net.URI;

public class ModeleHandler implements HttpHandler {

    private Constructeur constructeur;
    private int portHTTP;

    public ModeleHandler(Constructeur c, int p){
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

        reponse += "<body>\n" +
                "    <div class=\"container\">\n" +
                "       <h1>Constructeur :"+ constructeur.getNom() +"</h1>\n" +
                "       <form method=\"POST\" action=\"http://localhost:"+portHTTP+"/option\">";

        reponse +=
                "<h2>Choisir le modèle :</h2>"+
                        "<div class=\"form-group\">\n" +
                        "                <label for=\"modeleId\">Constructeur</label>\n" +
                        "                <select id=\"modeleId\" name=\"Modele\" class=\"form-control\">\n";
                     for (Modele i : constructeur.getModeles()) {
                            reponse +=         "<option value='"+i.getNom()+ "' selected>"+i.getNom()+"</option>\n";
                     }
                        reponse +="      </select>\n" +
                        "</div>\n" +
                        "<button type=\"submit\"  class=\"btn btn-primary mb-2\">Confirmer</button>"+
                        "</form>\n" +
                        "</div>\n" +
                        "</body>";



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