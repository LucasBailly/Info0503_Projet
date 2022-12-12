package scv;

import org.json.JSONObject;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;
import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.ArrayList;

public class Constructeur{
	
	//ATTRIBUTS
	private String nom;
	private Usine usines[];
	private ArrayList<Modele> modeles ;
	private final static int NOMBRE_USINES_MAX = 10;
	private int nbr_usines;
	
	//CONSTRUCTEURS
	public Constructeur(String nom){
		this.nom = nom;
		nbr_usines = 0;
		modeles = new ArrayList<Modele>();
		this.usines = new Usine [NOMBRE_USINES_MAX];
		for(int i=0; i<NOMBRE_USINES_MAX; i++){
			this.usines[i] = null;
		}
	}
	public Constructeur(){
		nom = "TOTO Constructor";
		usines = new Usine [NOMBRE_USINES_MAX];
		modeles = new ArrayList<Modele>();
		Parking[] parkings = {new Parking()};
		usines[1] = new Usine(this, parkings);
		for(int i=1 ; i<usines.length ; i++){
			this.usines[i] = null;
		}
	}

	public Constructeur(Constructeur constructeur){
		nom = constructeur.getNom();
		usines = constructeur.getUsines();
		modeles = new ArrayList<Modele>(constructeur.getModeles());
	}
	
	//GETTERS
	public String getNom(){
		return nom;
	}
	public Usine[] getUsines() {
		return usines;
	}
	public ArrayList<Modele> getModeles(){
		return modeles;
	}

	//TOSTRING
	public String toString(){
		return nom+", nombre d'usines : "+usines.length;
	}

	//JSON
	public JSONObject toJSON(){
		JSONObject json = new JSONObject();
		json.put("NomConstructeur", this.getNom());
		return json;
	}

	public static Constructeur fromJSON(JSONObject json){
		return new Constructeur(json.getString("NomConstructeur"));
	}

	//METHODES
	public void ajouterUsine(Usine usine){

		if(nbr_usines >= NOMBRE_USINES_MAX){
			System.out.println("Impossible d'ajouter une usine supplementaire.");
		}
		else{
			usines[nbr_usines] = new Usine(usine);
			nbr_usines++;
		}
	}
	public void ajouterModele(Modele modele){
		modeles.add(modele);
	}
	public Modele getModele(String m){
		int i=0;
		while(i< modeles.size() && !(modeles.get(i).getNom().equals(m))){
			i++;
		}
		return modeles.get(i);
	}
	public String fournirVehicule(Vehicule vehicule, String motDePasse)  {
		String message = vehicule.toJSON().toString();
		System.out.println(message);
		byte[] bytes ;
		String res;
		try {
			SecretKeySpec specification = new SecretKeySpec(motDePasse.getBytes("UTF-8"), "AES");
			Cipher chiffreur = Cipher.getInstance("AES");
			chiffreur.init(Cipher.ENCRYPT_MODE, specification);
			bytes = chiffreur.doFinal(message.getBytes("UTF-8"));
			res=Base64.getEncoder().encodeToString(bytes);
			System.out.println(res);
			return res ;
		} catch(NoSuchAlgorithmException | NoSuchPaddingException | InvalidKeyException | IllegalBlockSizeException | BadPaddingException | UnsupportedEncodingException e) {
			System.err.println("Erreur lors du chiffrement : " + e);
			return "-1";
		}
	}
}