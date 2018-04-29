package fr.cned.emdsgil.suividevosfrais;
import android.content.Intent;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Jeremy on 21/02/2018.
 */

public class AccesDistant implements AsynchResponse {

    private static final String SERVEURADDR = "http://www.d33plearning.me/gsb/serveurgsb.php";
    public static int start = 1;

    public AccesDistant (){
        super();
    }

    //retour du serveur distant
    @Override
    public void processFinish(String output) {

        Log.d("serveur", "**********" + "Serveur OK");
        Log.d("serveur", "**********" + output);

        //decoupage du message recu avec :
        String[] message = output.split("%");
        Log.d("serveur", "**********" + message[0]);
        if (message[0].equals("enreg")) {
            Log.d("serveur", "je suis dans enreg");
            Log.d("message", "**********" + message);
            Log.d("enreg", "*************" + message[1]);
            }
        else if (message[0].equals("connexion")) {
            start = 1;
            Log.d("serveur", "je suis dans equals connexion");
            Log.d("message", "**********" + message);
                Log.d("connexion", "*************" + message[1]);
                try {
                        JSONObject profil = new JSONObject(message[1]);
                        String id = profil.getString("id");
                        String prenom = profil.getString("nom");
                        String nom = profil.getString("prenom");
                        Log.d("id", "*************" + id);
                        Log.d("prenom", "*************" + nom);
                        Log.d("nom", "*************" + prenom);
                    }
                catch (JSONException e) {

                    Log.d("connexion", "********JSON******** " + message[1]);

                }
            }
        else {
            Log.d("serveur", "je suis dans else");
            start = 0;
            if (message[0].equals("dernier")) {
                Log.d("dernier", "*************" + message[1]);
                } else {
                    if (message[0].equals("Erreur!")) {
                        Log.d("Erreur", "*************" + message[1]);
                    }

                }
            }

    }

    public void envoi(String operation, JSONArray lesDonneesJSON){

        AccesHTTP accesDonnees = new AccesHTTP();
        //lien de deélégation
        accesDonnees.delegate = this;
        //ajout parametres
        accesDonnees.addParam("operation", operation);
        accesDonnees.addParam("lesdonnees", lesDonneesJSON.toString());
        // appel au serveur
        accesDonnees.execute(SERVEURADDR);
    }
}
