package fr.cned.emdsgil.suividevosfrais;

import android.content.Entity;
import android.os.AsyncTask;
import android.util.Log;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

/**
 * Created by Jeremy on 21/02/2018.
 */

public class AccesHTTP extends AsyncTask<String,Integer,Long> {

    private ArrayList<NameValuePair> parametres;
    private String ret = null;
    public AsynchResponse delegate = null;

    public AccesHTTP(){

        parametres = new ArrayList<NameValuePair>();

    }

    public void addParam(String nom, String valeur){

        // ajout parametre post
        parametres.add(new BasicNameValuePair(nom,valeur));
    }


    @Override
    protected Long doInBackground(String... strings) {
        HttpClient cnxHttp = new DefaultHttpClient();
        HttpPost paramCnx = new HttpPost(strings[0]);

        try {
            // encodage des parametres
            paramCnx.setEntity(new UrlEncodedFormEntity(parametres));
            //connexion et envoie des parametres
            HttpResponse reponse = cnxHttp.execute(paramCnx);
            //transformation reponse
            ret = EntityUtils.toString(reponse.getEntity());

        } catch (UnsupportedEncodingException e) {

        } catch (ClientProtocolException e){

            Log.d("Erreur protocole", "*****************" + e.toString());

        } catch (IOException e){
            Log.d("Erreur IO", "*****************" + e.toString());
        }
        return null;
    }

    @Override
    protected void onPostExecute(Long result){

        delegate.processFinish((ret.toString()));
    }
}
