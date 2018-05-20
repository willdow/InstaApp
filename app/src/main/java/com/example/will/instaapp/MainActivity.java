package com.example.will.instaapp;

import android.content.Intent;
import android.media.Image;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import static android.text.InputType.TYPE_TEXT_VARIATION_PASSWORD;
import static android.text.InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    private EditText idLogin, idPassword;
    private Button idButtonConnexion;
    private ImageButton idSeePassword;
    private Infirmier unInfirmier;

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

        public void onClick(View v) {

            if(v.getId() == R.id.idConnexion){
                String login = this.idLogin.getText().toString();
                String password = this.idPassword.getText().toString();

                if (login != "" || password != "") {
                    final MainActivity ma = this;

                    Thread unT = new Thread(new Runnable() {
                        @Override
                        public void run() {
                            VerifConnexion uneConnexion = new VerifConnexion();
                            uneConnexion.execute(unInfirmier);

                            runOnUiThread(new Runnable() {

                                @Override
                                public void run() {
                                    if (Infirmier == null) {
                                        Toast.makeText(ma, "Vérifier vos identifiants", Toast.LENGTH_LONG).show();
                                    } else {
                                        Toast.makeText(ma, "Bienvenue " + Infirmier.getNom(), Toast.LENGTH_LONG).show();
                                        Intent unIntent;
                                        unIntent = new Intent(ma, Page2.class);
                                        //unIntent.putExtra("email", leCandidat.getEmail());
                                        startActivity(unIntent);
                                    }
                                }
                            });
                        }
                    });
                    unT.start();
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
    }
}
