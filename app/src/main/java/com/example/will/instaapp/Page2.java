package com.example.will.instaapp;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
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
import java.lang.reflect.Field;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class Page2 extends AppCompatActivity implements View.OnClickListener{

    private String[] infoPatients;
    private ArrayList<Patient> lesPatients = new ArrayList<Patient>();
    private Infirmier unInfirmier;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_page2);
        this.unInfirmier = (Infirmier) getIntent().getSerializableExtra("serialize_data");



        for (Field field : unInfirmier.getClass().getDeclaredFields()) {
            field.setAccessible(true);
            String name = field.getName();
            Object value = null;
            try {
                value = field.get(unInfirmier);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }

            Log.e("infirmier", name+" "+value);
        }

        TextView tv = findViewById(R.id.idNomInfirmier);
        tv.setText(unInfirmier.getEtat_civil()+" "+unInfirmier.getNom()+" "+unInfirmier.getPrenom()+" du service de "+unInfirmier.getNomService());


        ListePatients uneListePatients = new ListePatients();
        uneListePatients.execute(new String[]{String.valueOf(unInfirmier.getIdService())});

        ListView lv = (ListView) findViewById(R.id.list_patients);

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView<?> adapterView,
                                    View view, int position, long id) {
                Log.e("n°", String.valueOf(lesPatients.get(position).getIdPatient()));
                Intent intent = new Intent(Page2.this, Page3.class);
                intent.putExtra("idPatient", lesPatients.get(position).getIdPatient());
                intent.putExtra("idHospi", lesPatients.get(position).getIdHospi());
                intent.putExtra("idInfirmier", Page2.this.unInfirmier.getIdInfirmier());

                startActivity(intent);
            }
        });

        if (lesPatients.size() !=0) {
            Log.e("erreur", "test");
        }
    }

    public void setData(String[] listData)
    {
        ListView lv = (ListView) findViewById(R.id.list_patients);
        lv.setAdapter(new ArrayAdapter<String>(Page2.this,
                android.R.layout.simple_list_item_1, listData));
    }

    public ArrayList<Patient> getLesPatients() {
        return lesPatients;
    }

    public void setLesPatients(ArrayList<Patient> lesPatients) {
        this.lesPatients = lesPatients;
    }

    public String[] getInfoPatients() {
        return infoPatients;
    }

    public void setInfoPatients(String[] infoPatients) {
        this.infoPatients = infoPatients;
    }

    @Override
    public void onClick(View v) {
    }

    class ListePatients extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... idPatient) {
            String url = "http://163.172.49.216/insta_medic/app/android/listePatients.php";
            String parametres = "?idService=" + idPatient[0];
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
                JSONArray jsonResultat = new JSONArray(resultat);
                if(jsonResultat.getJSONObject(0).has("erreur"))
                {
                    Toast.makeText(Page2.this, jsonResultat.getJSONObject(0).getString("erreur"), Toast.LENGTH_SHORT).show();
                }
                for (int i = 0; i < jsonResultat.length(); i++)
                {
                    JSONObject object = jsonResultat.getJSONObject(i);
                    Patient unPatient = new Patient(object.getInt("idPatient"), object.getInt("idHospi"), object.getString("nom"),object.getString("prenom"), object.getString("nomService")
                            + " - " +object.getString("etage")+ " - " +object.getString("idChambre"));

                    Page2.this.getLesPatients().add(unPatient);
                }

                String[] listeData = new String[Page2.this.getLesPatients().size()];

                int cpt = 0;

                for (Patient unPatient : Page2.this.getLesPatients())
                {
                    listeData[cpt] = unPatient.getNom()+" "+unPatient.getPrenom()+" "+unPatient.getChambreComplete();
                    cpt++;
                }


                Page2.this.setData(listeData);

            } catch (Exception exp) {
                Log.e("erreur", exp.toString());
            }
        }
    }
}


