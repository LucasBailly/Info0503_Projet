package scv;

import org.json.JSONObject;

public class Commande {
    private int numero;
    private Constructeur constructeur;
    private Modele modele;

    public Commande(int numero , Constructeur constructeur, Modele modele){
        this.numero = numero;
        this.constructeur = constructeur;
        this.modele = modele;
    }

    public Constructeur getConstructeur() {
        return constructeur;
    }

    public int getNumero() {
        return numero;
    }

    public Modele getModele() {
        return modele;
    }

    public void setConstructeur(Constructeur constructeur) {
        this.constructeur = constructeur;
    }

    public void setModele(Modele modele) {
        this.modele = modele;
    }

    public void setNumero(int numero) {
        this.numero = numero;
    }

    @Override
    public String toString() {
        return "Commande{" +
                "numero=" + numero +
                ", constructeur=" + constructeur.toString() +
                ", modele=" + modele.toString() +
                '}';
    }

    public JSONObject toJSON(){
        JSONObject json = new JSONObject();
        json.put("numeroCommande", this.getNumero());
        json.put("constructeur", this.getConstructeur().toJSON());
        json.put("modele", this.getModele().toJSON());
        return json;
    }

    public static Commande fromJSON(JSONObject json){
        return new Commande(json.getInt("numeroCommande"),Constructeur.fromJSON(json.getJSONObject("constructeur")), Modele.fromJSON(json.getJSONObject("modele")));

    }
}