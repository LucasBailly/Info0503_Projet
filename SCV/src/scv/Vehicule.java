package scv;

import org.json.JSONObject;

public class Vehicule implements Comparable {

    //ATTRIBUTS
    private Modele modele ;
    private String codeVin ;
    private String dateFabrication ;
    private Constructeur constructeur;

    //CONSTRUCTEURS
    public Vehicule(){
        modele = new Modele();
        codeVin = "x";
        dateFabrication = "15/02";
        constructeur = new Constructeur() ;
    }
    public Vehicule(Modele modele, String codeVin, String dateFabrication, Constructeur constructeur){
        this.modele = modele ;
        this.codeVin = codeVin ;
        this.dateFabrication = dateFabrication ;
        this.constructeur = new Constructeur(constructeur);
    }

    //GETTERS
    public Modele getModele(){
        return modele;
    }
    public String getCodeVin(){
        return codeVin;
    }
    public String getDateFabrication(){
        return dateFabrication;
    }
    public Constructeur getConstructeur(){
        return constructeur;
    }

    //AFFICHAGE
    public String toString(){
        return constructeur +" "+modele.getNom() ;
    }

    public void afficher(){
        System.out.println("scv.Constructeur : "+constructeur);
        System.out.println(modele);
        System.out.println("code VIN : "+codeVin);
        System.out.println("Date de Fabrication : "+dateFabrication);
    }

     public JSONObject toJSON(){
         JSONObject json = new JSONObject();
         json.put("modele", this.getModele().toJSON());
         json.put("codeVin" , this.codeVin);
         json.put("dateFabrication", this.dateFabrication);
         json.put("constructeur", this.getConstructeur().toJSON());

         return json;
     }

     //Ici il manque le fromJSON d'un constructeur, en attendant on utilise un constructeur par défaut
    // public static Vehicule fromJSON(JSONObject json){
       //  return new Vehicule(Modele.fromJSON(json.getJSONObject("modele")), json.getString("codeVin"), json.getString("dateFabrication"), Constructeur.fromJSON(json.getJSONObject("constructeur")));
     //}

    @Override
    public int compareTo(Object o) {
        Vehicule o2 = (Vehicule) o;
        if (o2.getCodeVin().equals(this.codeVin)){
            return 1;
        }
        else {
            return 0;
        }
    }
    public static Vehicule fromJSON(JSONObject json){
        return new Vehicule(Modele.fromJSON(json.getJSONObject("modele")), json.getString("codeVin"), json.getString("dateFabrication"), Constructeur.fromJSON(json.getJSONObject("constructeur")));
    }

}
