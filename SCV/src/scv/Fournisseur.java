package scv;

public class Fournisseur {
    //ATTRIBUTS
    private String nom;
    private static int SIZE = 50;
    private Motorisation [] motorisations;
    private Couleur [] couleurs;
    private Options [] options;


    //CONSTRUCTEURS
    public Fournisseur (){
        nom = "toto";
        motorisations = new Motorisation [SIZE];
        couleurs = new Couleur [SIZE];
        options = new Options [SIZE];

        for(int i=0 ; i<SIZE ; i++){
            motorisations[i] = Motorisation.NULL;
            couleurs[i] = Couleur.NULL;
            options[i] = Options.NULL;
        }

        motorisations[0] = Motorisation.V6	;
        motorisations[1] = Motorisation.V8;
        motorisations[2] = Motorisation.V8;
        couleurs[0] = Couleur.NOIR;
        couleurs[1] = Couleur.GRIS;
        couleurs[2] = Couleur.BLANC;
        couleurs[3] = Couleur.BLANC;
        couleurs[4] = Couleur.BLANC;
        options[0] = Options.ABS;
        options[1] = Options.ABS;
        options[2] = Options.ABS;
        options[3] = Options.REGULATEUR;
    }

    //METHODES

    //Return l'indice du premier moteur correspondant trouvé, 50 si aucun moteur n'a été trouvé
    public int rechercherMotorisation(Motorisation moteur){
        int i = 0;
        while((i < SIZE) && (moteur != motorisations[i])){
            i++;
        }
        return i;
    }
    //Return l'indice de la première couleur correspondante trouvée, 50 si aucune couleur n'a été trouvée
    public int rechercherCouleur(Couleur couleur){
        int i = 0;
        while((i < SIZE) && (couleur != couleurs[i])){
            i++;
        }
        return i;
    }
    //Return l'indice de la première option correspondante trouvée, 50 si aucune option n'a été trouvée
    public int rechercherOption(Options option){
        int i = 0;
        while((i < SIZE) && (option != options[i])){
            i++;
        }
        return i;
    }

    //Affiche la disponnibilité de la motorisation placée en paramètre
    public void possedeMotorisation(Motorisation moteur){
        if(rechercherMotorisation(moteur) == 50){
            System.out.println("La scv.Motorisation "+moteur+" n'est pas disponnible.");
        }
        else{
            System.out.println("La scv.Motorisation "+moteur+" est disponnible.");
        }
    }
    //Affiche la disponnibilité de la couleur placée en paramètre
    public void possedeCouleur(Couleur couleur){
        if(rechercherCouleur(couleur) == 50)
            System.out.println("La scv.Couleur "+couleur+" n'est pas disponnible.");
        else
            System.out.println("La scv.Couleur "+couleur+" est disponnible.");
    }
    //Affiche la disponnibilité de l'option placée en paramètre
    public void possedeOption(Options option){
        if(rechercherOption(option) == 50)
            System.out.println("L'Option "+option+" n'est pas disponnible.");
        else
            System.out.println("L'Option "+option+" est disponnible.");
    }

    //Return la motorisation placée en paramètre et la supprime des stocks
    //Return une motorisation NULL si aucune n'a été trouvée
    public Motorisation envoyerMotorisation(Motorisation moteur){
        int i = rechercherMotorisation(moteur);
        if(i == 50){
            return Motorisation.NULL;
        }
        else{
            Motorisation tmp = motorisations[i];
            motorisations[i] = Motorisation.NULL;
            return tmp ;
        }
    }
    //Return la couleur placée en paramètre et la supprime des stocks
    //Return une couleur NULL si aucune n'a été trouvée
    public Couleur envoyerCouleur(Couleur couleur){
        int i = rechercherCouleur(couleur);
        if(i == 50){
            return Couleur.NULL;
        }
        else{
            Couleur tmp = couleurs[i];
            couleurs[i] = Couleur.NULL;
            return tmp ;
        }
    }
    //Return l'option placée en paramètre et la supprime des stocks
    //Return une otpion NULL si aucune n'a été trouvée
    public Options envoyerOptions(Options option){
        int i = rechercherOption(option);
        if(i == 50){
            return Options.NULL;
        }
        else{
            Options tmp = options[i];
            options[i] = Options.NULL;
            return tmp ;
        }
    }

    //Affiche les stocks en scv.Motorisation, Couleurs et scv.Options
    public void afficherStocks(){
        int i;
        System.out.println();
        System.out.println("scv.Motorisation : ");
        for(i=0 ; i<SIZE ; i++){
            if(motorisations[i] != Motorisation.NULL){
                System.out.print(motorisations[i]+" / ");
            }
        }
        System.out.println();
        System.out.println();
        System.out.println("Couleurs : ");
        for(i=0 ; i<SIZE ; i++){
            if(couleurs[i] != Couleur.NULL){
                System.out.print(couleurs[i]+" / ");
            }
        }
        System.out.println();
        System.out.println();
        System.out.println("scv.Options : ");
        for(i=0 ; i<SIZE ; i++){
            if(options[i] != Options.NULL){
                System.out.print(options[i]+" / ");
            }
        }
        System.out.println();
        System.out.println();
    }

}
