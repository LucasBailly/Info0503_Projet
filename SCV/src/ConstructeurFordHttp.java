import java.io.IOException;
import com.sun.net.httpserver.HttpServer;
import scv.*;
import java.net.InetSocketAddress;

public class ConstructeurFordHttp {

    private static final int portHTTP = 8080;

    public static void main(String[] args) {
        Constructeur ford = new Constructeur("Ford");

        //Création d'un premier modele "Fiesta"
        Modele fiesta = new Modele("Fiesta");
        fiesta.ajouterCouleurPotentielle(Couleur.BLANC);
        fiesta.ajouterCouleurPotentielle(Couleur.NOIR);
        fiesta.ajouterMotorisationPotentielle(Motorisation.V6);
        fiesta.ajouterOptionPotentielle(Options.ABS);
        fiesta.ajouterOptionPotentielle(Options.LIMITATEUR);
        fiesta.ajouterOptionPotentielle(Options.CLIMATISATION);

        //Création d'un second modele "Focus"
        Modele focus = new Modele("Focus");
        focus.ajouterCouleurPotentielle(Couleur.ROUGE);
        focus.ajouterCouleurPotentielle(Couleur.VERT);
        focus.ajouterMotorisationPotentielle(Motorisation.V6);
        focus.ajouterOptionPotentielle(Options.ABS);
        focus.ajouterOptionPotentielle(Options.LIMITATEUR);
        focus.ajouterOptionPotentielle(Options.CLIMATISATION);
        focus.ajouterOptionPotentielle(Options.DIRECTION_ASSISTE);

        //Ajout de ces modele au constructeur Ford
        ford.ajouterModele(focus);
        ford.ajouterModele(fiesta);

        HttpServer serveur = null;
        try {
            serveur = HttpServer.create(new InetSocketAddress(8080), 0);
        } catch(IOException e) {
            System.err.println("Erreur lors de la création du serveur " + e);
            System.exit(0);
        }

        serveur.createContext("/modele" , new ModeleHandler(ford, portHTTP));
        serveur.createContext("/option" , new OptionHandler(ford, portHTTP));
        serveur.createContext("/commande" , new CommandeHandler(ford, portHTTP));
        serveur.setExecutor(null);
        serveur.start();

        System.out.println("Serveur démarré. Pressez CRTL+C pour arrêter.");
    }
}
