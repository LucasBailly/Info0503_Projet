import scv.*;

public class TestFournisseur {
    public static void main (String[] args){

        //Création du fournisseur
        Fournisseur fournisseur = new Fournisseur();

        //Affichage des stocks
        fournisseur.afficherStocks();

        //Création de 2 moteurs, 2 couleurs et 2 options
        Motorisation moteur_1 = Motorisation.V6;
        Motorisation moteur_2 = Motorisation.V12;
        Couleur couleur_1 = Couleur.NOIR;
        Couleur couleur_2 = Couleur.JAUNE;
        Options option_1 = Options.ABS;
        Options option_2 = Options.CLIMATISATION;

        //Est-ce que le fournisseur possède ces 6 éléments ?
        fournisseur.possedeMotorisation(moteur_1) ;
        fournisseur.possedeMotorisation(moteur_2) ;
        fournisseur.possedeCouleur(couleur_1);
        fournisseur.possedeCouleur(couleur_2);
        fournisseur.possedeOption(option_1);
        fournisseur.possedeOption(option_2);

        //Envois d'un moteur vers l'usine
        Motorisation moteur_vers_usine = fournisseur.envoyerMotorisation(Motorisation.V6);

        //Affichage des stocks
        fournisseur.afficherStocks();


    }
}
