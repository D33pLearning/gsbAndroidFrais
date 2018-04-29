<?php


include 'includes\fct.inc.php';
include 'includes\class.pdogsb.inc.php';

$pdo = PdoGsb::getPdoGsb();

// controle de reception de parametre
if(isset($_REQUEST["operation"])){
	
	// demande de récuperation du dernier visiteur
	if ($_REQUEST["operation"]=="dernier") {
		
		try{
			print("dernier%");
			$req = $pdo->getLesVisiteurs();
			//$req->execute();
			
			if($ligne = $req->fetch(PDO::FETCH_ASSOC)){
				print(json_encode($ligne));
			}
			
		}catch(PDOExeption $e){
			print "Erreur !".$e->getMessage();
			die();
		}
	}
	elseif($_REQUEST["operation"]=="enreg"){
		
		try{
			//recup post
			$lesdonnees = $_REQUEST["lesdonnees"];
			$donnees = json_decode($lesdonnees);
			$id = $donnees[0];
			$nom = $donnees[1];
			$prenom = $donnees[2];
			$login = $donnees[3];
			$mdp = $donnees[4];
			$adresse = $donnees[5];
			$cp = $donnees[6];
			$ville = $donnees[7];
			$dateembauche = $donnees[8];
			$estComptable = $donnees[9];
			
			// insertion
			print("enreg%");
			$larequete = "insert into visiteur (id, nom , prenom, login, mdp, adresse, cp, ville, dateembauche, estComptable)";
			$larequete .= " values ($id, $nom , $prenom, $login, $mdp, $adresse, $cp, $ville, NOW(), $estComptable)";
			print ($larequete);
                        //$pdo->majFraisHFRefuse($estComptable);
			
		}catch(PDOExeption $e){
			print "Erreur !".$e->getMessage();
			die();
		}
		
	}
        
        elseif($_REQUEST["operation"]=="connexion"){
            
            try {
                //print("connexion php\n");
                $lesdonnees = $_REQUEST["lesdonnees"];
		$donnees = json_decode($lesdonnees);
		$loginAndroid = $donnees[0];
		$mdpAndroid = $donnees[1];
                $mdp = $pdo->getMdpVisiteur($loginAndroid);
                
                // on a trouvé un mdp pour ce login donc le login est correct
                if ($mdp != "" && $mdp != null){
                    // le mot de passe est correct
                    if (strcmp($mdp[0], $mdpAndroid) == 0){
                        //print("connexion%");
                        print("connexion%");
                        $infosUtilisateur = $pdo->getInfosVisiteur($loginAndroid, $mdpAndroid);
                        print(json_encode($infosUtilisateur));
                    // se connecter
                    }
                    // le mot de passe est incorrect
                    else {
                        print("Le mot de passe est incorrect");
                    }
                }
                else {
                    print("L'identifiant est incorrect");
                }
                
            }
            
            catch(Exception $e){
                
                print "Erreur !".$e->getMessage();
		die();
            }
            
        }
}

?>