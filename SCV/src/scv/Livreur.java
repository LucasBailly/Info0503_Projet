package scv;

public class Livreur {

    //ATTRIBUTS
    private String nom;
    private String vehicule_json_chiffre;

    //CONSTRUCTEURS
    public Livreur(){
        nom="Toto_Livreur";
    }
    public Livreur(String nom){
        this.nom = nom;
    }
    public Livreur(Livreur livreur){
        nom = livreur.getNom();
    }

    //GETTERS
    public String getNom() {
        return nom;
    }

    //METHODES
    public void recevoirVehicule(String vehicule_json_chiffre){
        this.vehicule_json_chiffre = vehicule_json_chiffre;
    }
    public String livrerVehicule(){
        return vehicule_json_chiffre;
    }
}
