package fr.cned.emdsgil.suividevosfrais;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Jeremy on 22/03/2018.
 */

public class AuthActivity extends AppCompatActivity {

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auth);
        setTitle("GSB : Authentification");



        // identifiants saisis par le visiteur


        Button button= (Button) findViewById(R.id.btnConnction);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                EditText login;
                EditText password;
                TextView message;

                login = (EditText)findViewById(R.id.identifiant);
                password = (EditText)findViewById(R.id.mdp);
                message = (TextView) findViewById(R.id.message);

                // on crée un accès à la base
                AccesDistant accesDistant = new AccesDistant();

                List maListe = new ArrayList();

                // on ajoute les identifiants dans une liste
                maListe.add(login.getText().toString());
                maListe.add(password.getText().toString());

                // on convertie en JSONArray pour l'envoi des infos
                JSONArray logs = new JSONArray(maListe);

                // on utilise l'action connexion
                accesDistant.envoi("connexion", logs);


                // mauvais identifiants
                //message.setText("ERREUR");

                // bon identifiants
                if (AccesDistant.start == 1)
                    launchMenu();



            }
        });
    }

    public void launchMenu() {

        Intent intent = new Intent(AuthActivity.this, MainActivity.class);
        startActivity(intent);
        finish();
    }
}
