package scv;

import org.json.JSONObject;

import java.util.*;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Usine {

    //ATTRIBUTS
    private Parking[] parkings;
    private int taille;
	private Constructeur constructeur;
    private int compteur ;
    private HashMap<String, Vehicule> parking; 	//Hashmap regroupant tous les parkings de l'usine
    private HashMap<String, String> dico; 		// Dictionnaire permettant de trouver la place de parking avec le code vin.
    //Création de cet attribut pour le fromJSON()
    private static Parking[] parkings2;
    //Attributs permettant de stocker les commandes
    private static final int numeroCommande=0;
    private final Commande[] commandes = new Commande[10];


    //CONSTRUCTEURS
    public Usine(){
        constructeur = new Constructeur();
        parking = new HashMap<String, Vehicule>(taille,taille+1);
        dico = new HashMap<String, String>(12,1);
        Parking parking = new Parking(1, 10);
        parkings = new Parking[1];
        parkings[1] = parking;
    }
    public Usine(Constructeur constructeur , Parking[] p){
        this.constructeur = new Constructeur(constructeur) ;

        parkings = new Parking[p.length];
        int NBR_RANGEES, NBR_PLACES;
        taille=0;
        for(int i=0 ; i<p.length ; i++){
            NBR_RANGEES = p[i].getNbRange();
            NBR_PLACES = p[i].getNbRange();
            parkings[i] = new Parking(NBR_RANGEES, NBR_PLACES);

            taille += NBR_RANGEES*NBR_PLACES;
        }

        parking = new HashMap<String, Vehicule>(taille,taille+1);
        dico = new HashMap<String, String>(12,1);
    }
	public Usine(Usine usine){
		constructeur = new Constructeur(usine.getConstructeur());
		parking = new HashMap(usine.getParking());
		dico = new HashMap(usine.getDico());
	}
	
	//GETTERS
	public Constructeur getConstructeur(){
		return constructeur;
	}
	public String getDate(){
        SimpleDateFormat formater = new SimpleDateFormat("ddMMyy");
        Date aujourdhui = new Date();
        return ""+formater.format(aujourdhui) ;
    }
	public HashMap<String, Vehicule> getParking(){
		return parking;
	}
	public HashMap<String, String> getDico(){
		return dico;
	}

    //METHODES
    public String genererCodeVin(String dateFabrication){
        String abvConstructeur="AAAA" ;
        String cpt;

        char ch;
        int ascii;
        int n = constructeur.getNom().length();
        int positionString = 1;

        if (n>4){
            n = 5;
        }

        for(int i=1; i<n ; i++){
            ch=constructeur.getNom().charAt(i);
            ascii = ch;
            if(ascii>=97 && ascii<=122){
                ascii-=32;
                ch = (char)ascii;
            }
            // String tmp = ch+"";
            if(ch!='O' && ch!='Q' && ch!='I'){
                abvConstructeur = abvConstructeur.substring(0,positionString) + ch + abvConstructeur.substring(positionString+1);
            }
            positionString++;
        }

        if(compteur<10)
            cpt = "000000"+compteur;
        else if(compteur<100)
            cpt = "00000"+compteur;
        else if(compteur<1000)
            cpt = "0000"+compteur;
        else if(compteur<10000)
            cpt = "000"+compteur;
        else if(compteur<100000)
            cpt = "00"+compteur;
        else if(compteur<1000000)
            cpt = "0"+compteur;
        else
            cpt = ""+compteur;

        compteur++;

        return dateFabrication+abvConstructeur+cpt;

    }

    public String genererPlace(){
        int i = (int)(Math.random()*parkings.length);
        int j = 65 + (int)(Math.random()* ((90-parkings[i].getNbRange())-65) +1);
        int k = (int)(Math.random()* parkings[i].getNbPlace()+1);
        char c = (char)j;
        String place_string = i+" "+c+" "+k;

        return place_string;
    }


    //Créer un véhicule à partir d'un modèle, le range à une place de parking et return son codeVin
    public String creerVehicule(Modele modele){

        //S'il n'y a plus de place au parking, on ne créer pas la voiture
        if(parking.size() >= taille){
            return null;
        }
        //Sinon..

        //Création du véhicule
        String dateFabrication = getDate();
        String codeVin = genererCodeVin(dateFabrication);
        Vehicule vehicule = new Vehicule(modele, codeVin, dateFabrication, constructeur);

        //On cherche un place disponnible
        String place_string;
        do{
            place_string = genererPlace();
        }while(parking.get(place_string) != null);

        //On associe le codeVin du véhicule à la place trouvée
        dico.put(codeVin, place_string);

        //On associe la place trouvée au véhicule
        parking.put(place_string, vehicule);

        return codeVin;
    }

    //retourne un véhicule à partir de son code VIN
    public Vehicule recupererVehicule(String codeVin){
        String place_vehicule = dico.get(codeVin);
        return parking.get(place_vehicule);
    }
	
	public void afficherParking(){
		Iterator iterator = parking.entrySet().iterator();
        while (iterator.hasNext()) {
			Map.Entry mapentry = (Map.Entry) iterator.next();
			System.out.println(mapentry.getValue());
		}
	}
	
    /**
     * Permet de récupérer un véhicule à l'aide de son codeVIN et de renvoyer son JSON au format String
     * @param codeVin : le codeVIN du véhicule recherché
     * @return JSONObject au format String
     */
    public String fromVinToJSON(String codeVin){
         Vehicule vehicule = recupererVehicule(codeVin);
         JSONObject json = vehicule.toJSON();
         return json.toString();
     }

     public JSONObject toJSON(){
         int i = 1;
         JSONObject json = new JSONObject();
         JSONObject jsonVehicule = new JSONObject();
         for(Map.Entry mentry : parking.entrySet()) {
             if (mentry != null) {
                 jsonVehicule.put("Voiture "+i , mentry.getValue());
                 json.put("scv.Parking", mentry.getKey());
                 i++;
             }
         }
         json.put("Nombre Voiture", i-1);
         json.put("scv.Vehicule", jsonVehicule);
         json.put("nomUsine", this.constructeur.getNom());
         return json;
     }

    /**
     * @param json : un json d'une scv.Usine
     * @return un objet de type scv.Usine
     */
     public Usine fromJSON(JSONObject json){
         String nom = json.getString("nomUsine");
         parkings2 = new Parking[1];
         Usine usine = new Usine(new Constructeur(constructeur),parkings2);
         int nbVoiture = json.getInt("Nombre Voiture");
         for (int i = 1 ; i < nbVoiture ; i++){
             Vehicule vehicule = Vehicule.fromJSON((JSONObject) json.get("scv.Vehicule "+i));
             usine.creerVehicule(vehicule.getModele());
         }

         return usine;
    }
    public String toString(){
         return "scv.Usine existante";
    }

    //Fonction permettant l'ajout d'une commande dans le tableau de commande
    public void ajouterCommande(Commande commande){
        this.commandes[numeroCommande] = commande;
    }

    //Fonction permettant de récupérer la commande à un certain indice passé en paramètre
    public String recupererCommande(int numero){
        return this.commandes[numero].toString();
    }

    public boolean correspond(Commande c, Vehicule v){
        return  v.getModele().getMotorisation() == c.getModele().getMotorisation() &&
                v.getModele().getCouleur() == c.getModele().getCouleur() &&
                v.getModele().getOption() == c.getModele().getOption() ;
    }
    public String chercherVehicule(Commande c) {
        String res ="-1";
        Iterator iterator = parking.entrySet().iterator();
        Vehicule tmp = new Vehicule();
        while (iterator.hasNext()) {
            Map.Entry mapentry = (Map.Entry) iterator.next();
            if(correspond(c, (Vehicule)mapentry.getValue() ) )
                return mapentry.getKey().toString();
        }
        return res;
    }

}
