package scv;

import org.json.JSONException;
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


public class Client {

    //ATTRIBUTS
    private String nom;
    private String prenom;


    //CONSTRUCTEURS
    public Client(){
        nom = "Simpson";
        prenom = "Bart";
    }
    public Client(String nom, String prenom){
        this.nom = nom;
        this.prenom = prenom;
    }
    public Client(Client client){
        nom = client.getNom();
        prenom = client.getPrenom();
    }

    //GETTERS
    public String getNom(){
        return nom;
    }
    public String getPrenom(){
        return prenom;
    }

    //METHODES
   // public Vehicule recevoirVehicule(String vehicule_json_chiffre, String mot_de_passe){
       // byte[] bytes = null;

       // System.out.println(vehicule_json_chiffre);
       // Vehicule vehicule = null ;
       // System.out.println("start try");
      //  try {
         //   bytes = Base64.getDecoder().decode(vehicule_json_chiffre);
            //bytes = vehicule_json_chiffre.getBytes("UTF-8");
          //  SecretKeySpec specification = new SecretKeySpec(mot_de_passe.getBytes("UTF-8"), "AES");
           // Cipher dechiffreur = Cipher.getInstance("AES");
           // dechiffreur.init(Cipher.DECRYPT_MODE, specification);
           // System.out.println("doFinal?");
           // bytes = dechiffreur.doFinal(bytes);
           // System.out.println("OUI");
           // String vehicule_json_string = new String(bytes);
           // JSONObject jsonObject = new JSONObject(vehicule_json_string);
           // System.out.println(jsonObject.toString());
           // vehicule = Vehicule.fromJSON(jsonObject);
       // } catch(NoSuchAlgorithmException | NoSuchPaddingException | InvalidKeyException | IllegalBlockSizeException | BadPaddingException | JSONException | UnsupportedEncodingException e) {
           // System.err.println("Erreur lors du chiffrement : " + e);
           // System.exit(0);
        //}
       // return vehicule;
    //}
}
