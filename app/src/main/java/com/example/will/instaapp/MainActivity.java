package com.example.will.instaapp;

import android.content.Intent;
import android.media.Image;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;
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
import java.util.ArrayList;

import static android.text.InputType.TYPE_TEXT_VARIATION_PASSWORD;
import static android.text.InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    private EditText idLogin, idPassword;
    private Button idButtonConnexion;
    private ImageButton idSeePassword;
    private static Infirmier unInfirmier = null;

    public Infirmier getUnInfirmier() {
        return unInfirmier;
    }

    public static void setInfirmier(Infirmier unInfirmier) {
        MainActivity.unInfirmier = unInfirmier;
    }

    public MainActivity getMainActivity()
    {
        return this;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        this.idLogin = (EditText) findViewById(R.id.idLogin);
        this.idPassword = (EditText) findViewById(R.id.idPassword);
        this.idButtonConnexion = (Button) findViewById(R.id.idButtonConnexion);
        this.idSeePassword = (ImageButton) findViewById(R.id.idSeePassword);

        this.idButtonConnexion.setOnClickListener(this);
        this.idSeePassword.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {

        if(v.getId() == R.id.idButtonConnexion){
            String login = this.idLogin.getText().toString();
            String password = this.idPassword.getText().toString();

            unInfirmier = new Infirmier(login, password);

            if (login != "" || password != "") {

                VerifConnexion task = new VerifConnexion();
                task.execute(new String[]{login, password});

/*                Thread unT = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        VerifConnexion uneConnexion = new VerifConnexion();
                        uneConnexion.execute(unInfirmier);

                        runOnUiThread(new Runnable() {

                            @Override
                            public void run() {
                                if (this.unInfirmier == null) {
                                    Toast.makeText(ma, "Vérifier vos identifiants", Toast.LENGTH_LONG).show();
                                } else {
                                    Toast.makeText(ma, "Bienvenue " + unInfirmier.getEtat_civil() + " " +unInfirmier.getNom(), Toast.LENGTH_LONG).show();
                                    //Intent unIntent;
                                    //unIntent = new Intent(ma, Page2.class);
                                    //unIntent.putExtra("MyClass", unInfirmier);
                                    //startActivity(unIntent);
                                    Log.e("erreur", "connexion impossible");
                                }
                            }
                        });
                    }
                });
                unT.start();*/
            }
            else
            {
                Toast.makeText(this, "Veuillez entrer vos identifiants", Toast.LENGTH_LONG).show();
            }
        }

        if(v.getId() == R.id.idSeePassword)
        {
            boolean checked = ((CheckBox) v).isChecked();

            if (checked)
            {
                idPassword.setInputType(InputType.TYPE_CLASS_TEXT | TYPE_TEXT_VARIATION_VISIBLE_PASSWORD );
            }
            else
            {
                idPassword.setInputType(InputType.TYPE_CLASS_TEXT | TYPE_TEXT_VARIATION_PASSWORD );
            }
        }

    }
    class VerifConnexion extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... params) {
            String url = "http://163.172.49.216/insta_medic/app/android/conn.php";
            String parametres = "?login=" + params[0];
            parametres += "&password=" + params[1];
            String resultat = null;
            url = url+parametres;
            Log.e("Connexion à : ", url);

            try {
                URL uneUrl = new URL(url);
                HttpURLConnection uneUrlConnexion = (HttpURLConnection) uneUrl.openConnection();
                uneUrlConnexion.setRequestMethod("GET"); //Methode get
                uneUrlConnexion.setDoInput(true);//On ouvre envoie des donnees
                uneUrlConnexion.setDoOutput(true);//On ouvre la reception des donnees
                uneUrlConnexion.setReadTimeout(10000);
                uneUrlConnexion.setConnectTimeout(15000);

                uneUrlConnexion.connect();

                OutputStream fichier = uneUrlConnexion.getOutputStream();
                BufferedWriter unBuffer = new BufferedWriter(new OutputStreamWriter(fichier, "UTF-8"));
                unBuffer.write(parametres);
                unBuffer.flush();
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

            } catch (IOException exp) {
                Log.e("Erreur : ", exp.toString());
            }

            return resultat;
        }
        @Override
        protected void onPostExecute (String resultat) {
            if(resultat == null)
            {
                Log.e("erreur", "pas de resultat");
            }
            else try {
                JSONArray jsonResulat = new JSONArray(resultat);
                JSONObject resObject = jsonResulat.getJSONObject(0);
//                if (jsonResulat.getString("erreur") != "") {
//                    Toast.makeText(MainActivity.this, jsonResulat.getString("erreur"), Toast.LENGTH_SHORT).show();
//                    Log.e("error", jsonResulat.getString("erreur"));
//                } else if (jsonResulat.getString("nb") != "") {
//                    Toast.makeText(MainActivity.this, jsonResulat.getString("nb"), Toast.LENGTH_SHORT).show();
//                    Log.e("error", jsonResulat.getString("nb"));
//                }
            } catch (Exception exp) {
                Log.e("erreur", exp.toString());
            }

//        if (resultat != null) {
//            try {
//                JSONArray tabJson = new JSONArray(resultat);
//                JSONObject unObjet = tabJson.getJSONObject(0);
//                String nb = unObjet.getString("nb");
//                Log.e("nb = ", nb);
//                if (nb == "1") {
//                    infirmierConnecte = new Infirmier(unObjet.getString("etat_civil"), unObjet.getString("nom"),
//                            unObjet.getString("prenom"), unObjet.getString("date_naissance"),
//                            unObjet.getString("adresse"), unObjet.getString("adressecomp"), unObjet.getString("code_postal"),
//                            unObjet.getString("Ville"), unObjet.getString("telephone"), unInfirmier.getLogin(), unObjet.getString("email"),
//                            unObjet.getString("urlphoto"), unObjet.getString("nomService"), unObjet.getInt("idService"));
//                }
//                else if (unObjet.getString("error") != null)
//                {
//                    Log.e("Erreur : ", "Imposible de parser : " + resultat);
//                }
//            } catch (JSONException exp) {
//                Log.e("Erreur : ", "Imposible de parser : " + resultat);
//            }
//        }
        }
    }
}


