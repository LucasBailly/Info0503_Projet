import org.json.JSONObject;
import scv.*;

import java.io.UnsupportedEncodingException;

public class TestVehicule {

    public static void main (String[] args) throws UnsupportedEncodingException {
		//CREATION D'UN CONSTRUCTEUR
		Constructeur constructeur_1 = new Constructeur("Audi");

        //CREATION DE PLUSIEURS PARKINGS
        Parking parking_1 = new Parking(5, 200);
        Parking parking_2 = new Parking(3, 150);
        Parking parking_3 = new Parking(10, 50);
        Parking[] parkings = {parking_1, parking_2, parking_3} ;

        //CREATION D'UNE L'USINE
        Usine usine_1 = new Usine(constructeur_1, parkings);

        //AJOUT DE L'USINE AU CONSTRUCTEUR
        constructeur_1.ajouterUsine(usine_1);
        System.out.println("Affichage scv.Usine : "+ constructeur_1.getUsines()[0]);

        //CREATION D'UN LIVREUR
        Livreur livreur_1 = new Livreur();

        //CREATION D'UN CLIENT
        Client client_1 = new Client();

        //CREATION D'UN MODELES
        Modele modele_1 = new Modele();

        //CREATION D'UN VEHICULE
        String code_vin_1 = usine_1.creerVehicule(modele_1);

        //RECUPERATION DU VEHICULES
        Vehicule vehicule_1= usine_1.recupererVehicule(code_vin_1);
        System.out.println("scv.Vehicule envoyé");
        vehicule_1.afficher();

        //ENVOIS DU VEHICULE AU LIVREUR
        String motDePasse = "0123456789012345";
        String vehicule_chiffre = constructeur_1.fournirVehicule(vehicule_1, motDePasse);
        livreur_1.recevoirVehicule(vehicule_chiffre);

        Commande commande = new Commande(1,constructeur_1,modele_1);
//        String placeParking = usine_1.chercherVehicule(commande);
//        System.out.println("Voiture trouvée à l'emplacement : " + placeParking);

        JSONObject modeleJSON = modele_1.toJSON();
        System.out.println(commande.toJSON());
        Motorisation motorisation = Motorisation.valueOf("V6");
        System.out.println(motorisation);
//        System.out.println(Modele.fromJSON(modeleJSON));
//        String modeleString = modeleJSON.toString();
//        JSONObject temp = new JSONObject(modeleString);
//        System.out.println(Modele.fromJSON(temp));

        //LIVRAISON DU VEHICULE AU CLIENT
       // Vehicule vehicule_recu = client_1.recevoirVehicule(livreur_1.livrerVehicule(), motDePasse);
        //System.out.println("scv.Vehicule reçu");
        //vehicule_recu.afficher();
    }

}
