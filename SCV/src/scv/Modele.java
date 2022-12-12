package scv;// import org.json.JSONObject;

import org.json.JSONObject;
import java.util.ArrayList;

public class Modele {
    //ATTRIBUTS
    private String nom ;
    private Motorisation motorisation ;
    private Couleur couleur ;
    private Options option ;

    private ArrayList<Motorisation> motorisationsPotentielles ;
    private ArrayList<Couleur> couleursPotentielles ;
    private ArrayList<Options> optionsPotentielles ;

    //CONSTRUCTEURS
    public Modele(){
        nom = "toto_modele";
        option = Options.NULL;
        motorisation = Motorisation.V6;
        couleur = Couleur.NOIR;
        motorisationsPotentielles = new ArrayList<Motorisation>();
        couleursPotentielles = new ArrayList<Couleur>();
        optionsPotentielles = new ArrayList<Options>();
    }
    public Modele(String nom){
        this.nom = nom ;
        motorisationsPotentielles = new ArrayList<Motorisation>();
        couleursPotentielles = new ArrayList<Couleur>();
        optionsPotentielles = new ArrayList<Options>();
    }
    public Modele(Modele m){
        this.nom = m.getNom();
        motorisationsPotentielles = new ArrayList<Motorisation>(m.getMotorisationsPotentielles());
        couleursPotentielles = new ArrayList<Couleur>(m.getCouleursPotentielles());
        optionsPotentielles = new ArrayList<Options>(m.getOptionsPotentielles());
        option = m.getOption();
        couleur = m.getCouleur();
        motorisation = m.getMotorisation();
    }

    public Modele(String nom, Motorisation motorisation, Couleur couleur, Options option){
        this.nom = nom;
        this.motorisation = motorisation;
        this.couleur = couleur;
        this.option = option;
        this.motorisationsPotentielles = new ArrayList<Motorisation>();
        this.couleursPotentielles = new ArrayList<Couleur>();
        this.optionsPotentielles = new ArrayList<Options>();

    }

    //GETTERS
    public String getNom(){
        return nom;
    }
    public Motorisation getMotorisation(){
        return motorisation;
    }
    public Couleur getCouleur(){
        return couleur;
    }
    public Options getOption(){
        return option;
    }
    public ArrayList<Options> getOptionsPotentielles(){
        return optionsPotentielles;
    }
    public ArrayList<Motorisation> getMotorisationsPotentielles(){
        return motorisationsPotentielles;
    }
    public ArrayList<Couleur> getCouleursPotentielles(){
        return couleursPotentielles;
    }


    public void ajouterCouleurPotentielle(Couleur c){
        couleursPotentielles.add(c);
    }
    public void ajouterMotorisationPotentielle(Motorisation m){
        motorisationsPotentielles.add(m);
    }
    public void ajouterOptionPotentielle(Options o){
        optionsPotentielles.add(o);
    }

    //AFFICHAGE
    public String toString(){
        return "scv.Modele : "+nom+" ; Moteur : "+motorisation+" ; scv.Couleur : "+couleur+" ; Option : "+option ;
    }



    //TOJSON
     public JSONObject toJSON(){
         JSONObject json = new JSONObject();
         json.put("nom", this.nom);
         json.put("motorisation", this.motorisation.toString());
         json.put("couleur" , this.couleur.toString());
         json.put("option", this.option.toString());

         return json;
     }

    public static Modele fromJSON(JSONObject json){
        Motorisation motorisation = Motorisation.valueOf(json.getString("motorisation"));
        Couleur couleur = Couleur.valueOf(json.getString("couleur"));
        Options option = Options.valueOf(json.getString("option"));
      return new Modele(json.getString("nom"), motorisation, couleur, option);
    }
}
