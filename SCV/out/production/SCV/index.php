<!DOCTYPE html>
<html lang="fr">
<head>
    <meta charset="UTF-8">
    <title>Concessionnaire</title>

    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.1.3/css/bootstrap.min.css">
</head>
<body>
    <div class="container">
        <h1>Choisir un constructeur</h1>
        <form method="POST" action="http://localhost:8080/modele" >
            <h3>Ford</h3>
            <button type="submit" name="confirmer" class="btn btn-primary mb-2">Confirmer</button>
        </form>

        <form method="POST" action="http://localhost:8081/modele" >
            <h3>Renault</h3>
            <button type="submit" name="confirmer" class="btn btn-primary mb-2">Confirmer</button>
        </form>
    </div>
</body>
<?php
//include 'Commande.php';

//if(isset($_POST['confirmer'])) {

   // if($_POST['constructeur'] == "Ford"){
    //    window.location.replace("http://localhost/SCV_NOUZILLE_BAILLY/SCV/src/ConstructeurFord.php");
    //}
//}

    //Récupération des données du formulaire
    //$constructeur = $_POST['constructeur'];
    //$modele = $_POST['modele'];
    //$moteur = $_POST['moteur'];
    //$couleur = $_POST['couleur'];
    //$option = $_POST['option'];

    //Création d'une instance de Commande
    //$commande_1 = new Commande($constructeur, $modele, $moteur, $couleur, $option);

    //Sérialisation Commande
    //$string_json = json_encode($commande_1);
    //var_dump($string_json);

    //Désérialisation Commande
    //$etudiant_clone = json_decode($string_json, true);    //true permet de return un tableau associatif
    //var_dump($etudiant_clone["modele"]);
?>