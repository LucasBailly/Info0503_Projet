import java.io.IOException;

import java.net.DatagramSocket;
import java.net.DatagramPacket;
import java.net.SocketException;
import java.net.InetAddress;
import java.net.UnknownHostException;

import java.util.Scanner;

public class FournisseurUDP {
    public static int portEcoute = 2025;
    public static int portReception = 2026;

    public static void main(String[] args) {
        DatagramSocket socket = null;
        DatagramSocket socketReception = null;

        DatagramPacket msgReponse = null;
        Scanner clavier = new Scanner(System.in);

        try {
            socketReception = new DatagramSocket(portReception);
        } catch (SocketException e) {
            System.err.println("Erreur lors de la création de la socket : " + e);
            System.exit(0);
        }

        // Création de la socket
        try {
            socket = new DatagramSocket();
        } catch (SocketException e) {
            System.err.println("Erreur lors de la création de la socket : " + e);
            System.exit(0);
        }

        // Création du message
        DatagramPacket msg = null;

        try {
            InetAddress adresse = InetAddress.getByName(null);
            String message = "Bonjour je suis le fournisseur A";
            byte[] tampon = message.getBytes();
            msg = new DatagramPacket(tampon, tampon.length, adresse, portEcoute);

        } catch(UnknownHostException e) {
            System.err.println("Erreur lors de la création du message : " + e);
            System.exit(0);
        }

        // Envoi du message
        try {
            socket.send(msg);
        } catch(IOException e) {
            System.err.println("Erreur lors de l'envoi du message : " + e);
            System.exit(0);
        }

        msg = null;
        while (!msg.equals("OK")) {

            try {
                InetAddress adresse = InetAddress.getByName(null);
                System.out.println("Saisissez votre demande : ");
                String message = clavier.nextLine();
                byte[] tamponReception = message.getBytes();
                msg = new DatagramPacket(tamponReception, tamponReception.length, adresse, portEcoute);

            } catch (UnknownHostException e) {
                System.err.println("Erreur lors de la création du message : " + e);
                System.exit(0);
            }

            // Envoi du message
            try {
                socket.send(msg);
            } catch (IOException e) {
                System.err.println("Erreur lors de l'envoi du message : " + e);
                System.exit(0);
            }

            // Fermeture de la socket
            socket.close();
            socketReception.close();
        }
    }

}
