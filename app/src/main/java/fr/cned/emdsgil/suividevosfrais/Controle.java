package fr.cned.emdsgil.suividevosfrais;

import android.content.Context;
import android.provider.ContactsContract;

import org.json.JSONArray;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Jeremy on 21/03/2018.
 */

public class Controle {

    // propriétés
    private static Controle instance = null ;
    private static String nomFic = "saveprofil" ;
    //private static AccesLocal accesLocal ;
    private static AccesDistant accesDistant;
    private static Context context;

    /**
     * Constructeur
     */
    private Controle() {
        super();
    }

    /**
     * Création de l'instance unique
     * @return
     */
    public static final Controle getInstance(Context contexte) {
        if (Controle.instance == null) {
            Controle.context = contexte;
            Controle.instance = new Controle() ;
            //accesLocal = new AccesLocal(contexte);
            accesDistant = new AccesDistant() ;
            //profil = accesLocal.recupDernier();

            // recupSerialize(contexte);

            List maListe = new ArrayList();

            maListe.add("monid");
            maListe.add("monnom");
            maListe.add("monprenom");
            maListe.add("monlogin");
            maListe.add("monmdp");
            maListe.add("monadresse");
            maListe.add("moncp");
            maListe.add("maDate");
            maListe.add("maville");
            maListe.add(1);
            JSONArray visiteur = new JSONArray(maListe);

            accesDistant.envoi("dernier", visiteur);
        }
        return Controle.instance ;
    }
}
