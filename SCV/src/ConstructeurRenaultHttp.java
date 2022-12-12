import java.io.IOException;
import com.sun.net.httpserver.HttpServer;
import scv.*;

import java.net.InetSocketAddress;;

public class ConstructeurRenaultHttp {

    private static final int portHTTP = 8081;

    public static void main(String[] args) {

        Constructeur renault = new Constructeur("Renault");

        //Création d'un premier modele "Twingo"
        Modele twingo = new Modele("Twingo");
        twingo.ajouterCouleurPotentielle(Couleur.BLANC);
        twingo.ajouterCouleurPotentielle(Couleur.NOIR);
        twingo.ajouterCouleurPotentielle(Couleur.BLEU);
        twingo.ajouterCouleurPotentielle(Couleur.VIOLET);
        twingo.ajouterMotorisationPotentielle(Motorisation.V6);
        twingo.ajouterMotorisationPotentielle(Motorisation.V8);
        twingo.ajouterOptionPotentielle(Options.LIMITATEUR);
        twingo.ajouterOptionPotentielle(Options.REGULATEUR);

        //Création d'un second modele "Megane"
        Modele megane = new Modele("Megane");
        megane.ajouterCouleurPotentielle(Couleur.ROUGE);
        megane.ajouterCouleurPotentielle(Couleur.VERT);
        megane.ajouterCouleurPotentielle(Couleur.GRIS);
        megane.ajouterCouleurPotentielle(Couleur.ORANGE);
        megane.ajouterMotorisationPotentielle(Motorisation.V8);
        megane.ajouterOptionPotentielle(Options.ABS);
        megane.ajouterOptionPotentielle(Options.LIMITATEUR);
        megane.ajouterOptionPotentielle(Options.CLIMATISATION);
        megane.ajouterOptionPotentielle(Options.DIRECTION_ASSISTE);
        megane.ajouterOptionPotentielle(Options.REGULATEUR);

        //Création d'un second modele "Zoe"
        Modele zoe = new Modele("Zoe");
        zoe.ajouterCouleurPotentielle(Couleur.NOIR);
        zoe.ajouterCouleurPotentielle(Couleur.BLANC);
        zoe.ajouterCouleurPotentielle(Couleur.GRIS);
        zoe.ajouterMotorisationPotentielle(Motorisation.V6);
        zoe.ajouterOptionPotentielle(Options.ABS);
        zoe.ajouterOptionPotentielle(Options.LIMITATEUR);
        zoe.ajouterOptionPotentielle(Options.CLIMATISATION);
        zoe.ajouterOptionPotentielle(Options.DIRECTION_ASSISTE);

        //Ajout de ces modele au constructeur Renault
        renault.ajouterModele(megane);
        renault.ajouterModele(zoe);
        renault.ajouterModele(twingo);


        HttpServer serveur = null;
        try {
            serveur = HttpServer.create(new InetSocketAddress(portHTTP), 0);
        } catch(IOException e) {
            System.err.println("Erreur lors de la création du serveur " + e);
            System.exit(0);
        }

        serveur.createContext("/modele" , new ModeleHandler(renault, portHTTP));
        serveur.createContext("/option" , new OptionHandler(renault, portHTTP));
        serveur.createContext("/commande" , new CommandeHandler(renault, portHTTP));
        serveur.setExecutor(null);
        serveur.start();

        System.out.println("Serveur démarré. Pressez CRTL+C pour arrêter.");

    }
}
