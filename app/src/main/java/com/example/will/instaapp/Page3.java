package com.example.will.instaapp;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
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


public class Page3 extends AppCompatActivity implements View.OnClickListener{

    TextView tvNomPatient;
    TextView tvDerniereDate;
    EditText edPoids;
    EditText edTemperature;
    EditText edTension;
    EditText taCommentaires;

    private boolean newData = true;
    private int idPatient;
    private int idInfirmier;
    private int idDonnees;
    private int idHospi;

    public boolean isNewData() {
        return newData;
    }

    public void setNewData(boolean newData) {
        this.newData = newData;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_page3);

        this.tvNomPatient = findViewById(R.id.tvNomPatient);
        this.tvDerniereDate = findViewById(R.id.tvDerniereMaj);
        this.edPoids = findViewById(R.id.edPoids);
        this.edTemperature = findViewById(R.id.edTemperature);
        this.edTension = findViewById(R.id.edTension);
        this.taCommentaires = findViewById(R.id.taCommentaires);

        this.idPatient = getIntent().getIntExtra("idPatient", 0);
        this.idInfirmier = getIntent().getIntExtra("idInfirmier", 0);
        this.idHospi = getIntent().getIntExtra("idHospi", 0);

        getDataPatients();

        Button buttonMaj = findViewById(R.id.buttonMaj);

        buttonMaj.setOnClickListener(this);

    }

    private void getDataPatients()
    {
        GetDataPatient dataPatients = new GetDataPatient();
        dataPatients.execute(new String[]{String.valueOf(idPatient)});
    }
    @Override
    public void onClick(View v) {

        if(v.getId() == R.id.buttonMaj){
            Log.e("click", "clicc");
            String tempereature = this.edTemperature.getText().toString();
            String poids = this.edPoids.getText().toString().replaceAll("\\s+","%20");
            String tension = this.edTension.getText().toString().replaceAll("\\s+","%20");
            String autres = this.taCommentaires.getText().toString().replaceAll("\\s+","%20");


            if (Page3.this.isNewData())
            {
                String[] arrayInsert = {tempereature, poids, tension, autres, String.valueOf(this.idPatient), String.valueOf(this.idInfirmier), "insert"};
                InsertDataPatient insertData = new InsertDataPatient();
                insertData.execute(arrayInsert);
            }
            else
            {
                String[] arrayInsert = {tempereature, poids, tension, autres, String.valueOf(this.idDonnees), String.valueOf(this.idInfirmier), "update"};
                InsertDataPatient insertData = new InsertDataPatient();
                insertData.execute(arrayInsert);
            }
        }
    }

    public int getIdPatient() {
        return idPatient;
    }

    public void setIdPatient(int idPatient) {
        this.idPatient = idPatient;
    }

    public int getIdInfirmier() {
        return idInfirmier;
    }

    public void setIdInfirmier(int idInfirmier) {
        this.idInfirmier = idInfirmier;
    }

    public int getIdDonnees() {
        return idDonnees;
    }

    public void setIdDonnees(int idDonnees) {
        this.idDonnees = idDonnees;
    }

    public int getIdHospi() {
        return idHospi;
    }

    public void setIdHospi(int idHospi) {
        this.idHospi = idHospi;
    }

    private void setDataPatient(JSONObject dataPatient)
    {
        try
        {
            this.tvNomPatient.setText("Données de: "+dataPatient.getString("nom")+" "+dataPatient.getString("prenom"));
            String url = "http://163.172.49.216/insta_medic/app/files/"+dataPatient.getString("idPatient")+"/"+dataPatient.getString("urlphoto");

            new DownloadImageTask((ImageView) findViewById(R.id.imageView3)).execute(url);

            if(dataPatient.getInt("nb") > 0)
            {
                Page3.this.setNewData(false);
                this.idDonnees = dataPatient.getInt("idDonnes");
                this.edPoids.setText(dataPatient.getString("poids"));
                this.edTemperature.setText(dataPatient.getString("temperature"));
                this.edTension.setText(dataPatient.getString("tension"));
                this.taCommentaires.setText(dataPatient.getString("autres"));
                this.tvDerniereDate.setText("Derniere mise à jour: "+dataPatient.getString("derniereMaj"));
            }

        }catch (Exception e)
        {
            Log.e("Exception", e.toString());
        }


    }

    class GetDataPatient extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... idPatient) {
            String url = "http://163.172.49.216/insta_medic/app/android/getInfosPatient.php";
            String parametres = "?idPatient=" + idPatient[0];
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
                Log.e("res", resultat);
                JSONArray jsonResultat = new JSONArray(resultat);
                JSONObject dataSet = jsonResultat.getJSONObject(0);

                if (dataSet.has("erreur"))
                {
                    Toast.makeText(Page3.this, dataSet.getString("erreur"), Toast.LENGTH_SHORT).show();
                }

                Page3.this.setDataPatient(dataSet);

            } catch (Exception exp) {
                Log.e("erreur", exp.toString());
            }
        }
    }

    class InsertDataPatient extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... data) {
            String url = "http://163.172.49.216/insta_medic/app/android/setInfosPatients.php";

            String parametres = "";
            parametres = "?temperature=" + data[0];
            parametres += "&poids=" + data[1];
            parametres += "&tension=" + data[2];
            parametres += "&autres=" + data[3];
            parametres += "&idInfirmier=" + data[5];
            if (data[6] == "update") {
                parametres += "&idDonnees=" + data[4];
                parametres += "&action=" + data[6];
            } else if (data[6] == "insert") {
                parametres += "&idPatient=" + data[4];
                parametres += "&idHospi=" + Page3.this.getIdHospi();
                parametres += "&action=" + data[6];
            }

            url = url + parametres;


            String resultat = null;
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
        protected void onPostExecute(String resultat) {
            if(resultat == null)
            {
                Log.e("erreur", "pas de resultat");
            }
            else try {
                Log.e("res", resultat);
                JSONArray jsonResult = new JSONArray(resultat);
                JSONObject dataSet = jsonResult.getJSONObject(0);

                if (dataSet.has("erreur")) {
                    Toast.makeText(Page3.this, dataSet.getString("erreur"), Toast.LENGTH_LONG).show();
                    Log.e("error", dataSet.getString("erreur"));
                }else if(dataSet.has("result")) {
                    Toast.makeText(Page3.this, dataSet.getString("result"), Toast.LENGTH_LONG).show();
                    Log.e("result", dataSet.getString("result"));
                }

                Page3.this.getDataPatients();

            } catch (Exception exp) {
                Log.e("erreur", exp.toString());
            }
        }
        }
    }

    class DownloadImageTask extends AsyncTask<String, Void, Bitmap> {
        ImageView bmImage;

        public DownloadImageTask(ImageView bmImage) {
            this.bmImage = bmImage;
        }

        protected Bitmap doInBackground(String... urls) {
            String urldisplay = urls[0];
            Bitmap mIcon11 = null;
            try {
                InputStream in = new java.net.URL(urldisplay).openStream();
                mIcon11 = BitmapFactory.decodeStream(in);
            } catch (Exception e) {
                Log.e("Error", e.getMessage());
                e.printStackTrace();
            }
            return mIcon11;
        }

        protected void onPostExecute(Bitmap result) {
            bmImage.setImageBitmap(result);
        }
    }

