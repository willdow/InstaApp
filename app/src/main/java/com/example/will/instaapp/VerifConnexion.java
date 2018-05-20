package com.example.will.instaapp;

import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by etien on 30/04/2018.
 */

class VerifConnexion extends AsyncTask<Infirmier, Void, Infirmier> {

    @Override
    protected Infirmier doInBackground(Infirmier... Infirmiers) {
        String url = "http://projet20.entreprise.lan/conn.php";
        String resultat = null;
        Infirmier unInfirmier = Infirmiers[0];
        Infirmier infirmierConnecte = null;

        try {
            URL uneUrl = new URL(url);
            HttpURLConnection uneUrlConnexion = (HttpURLConnection) uneUrl.openConnection();
            uneUrlConnexion.setRequestMethod("GET"); //Methode get
            uneUrlConnexion.setDoInput(true);//On ouvre envoie des donnees
            uneUrlConnexion.setDoOutput(true);//On ouvre la reception des donnees
            uneUrlConnexion.setReadTimeout(10000);
            uneUrlConnexion.setConnectTimeout(15000);

            uneUrlConnexion.connect();

            String parametres = "email=" + unInfirmier.getLogin();
            parametres += "&mdp=" + unInfirmier.getPassword();
            OutputStream fichier = uneUrlConnexion.getOutputStream();
            BufferedWriter unBuffer = new BufferedWriter(new OutputStreamWriter(fichier, "UTF-8"));

            unBuffer.write(parametres);

            unBuffer.flush();
            unBuffer.close();
            unBuffer.close();


            InputStream fichier2 = uneUrlConnexion.getInputStream();
            BufferedReader unBuffer2 = new BufferedReader(new InputStreamReader(fichier2, "UTF-8"));
            StringBuilder sb = new StringBuilder();
            String ligne = null;
            while ((ligne = unBuffer2.readLine()) != null) {
                sb.append(ligne);
            }
            unBuffer2.close();
            fichier2.close();
            resultat = sb.toString();
            Log.e("Resultat : ", resultat);
        } catch (IOException exp) {
            Log.e("Erreur : ", "Connexion Impossible a " + url);
        }

        if (resultat != null) {
            try {
                JSONArray tabJson = new JSONArray(resultat);
                JSONObject unObjet = tabJson.getJSONObject(0);
                int nb = unObjet.getInt("nb");
                if (nb >= 1) {
                    candidatConnecte = new Infirmier(unInfirmier.getEmail(),
                            unInfirmier.getPassword(),
                            unObjet.getString("nom"),
                            unObjet.getString("prenom"));
                }
            } catch (JSONException exp) {
                Log.e("Erreur : ", "Imposible de parser : " + resultat);
            }
        }


        return candidatConnecte;
    }
    @Override
    protected void onPostExecute (Infirmier candidat) {
        MainActivity.setInfirmier(Infirmier);
    }
}