package com.flitterman.mesprospects;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.preference.Preference;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

public class MainActivity extends AppCompatActivity {

    private EditText editTextUsername;
    private EditText editTextPassword;
    private TextView errorUsername;
    private TextView errorPassword;
    private CheckBox rememberMe;
    private SharedPreferences preferences;

    // public final static boolean REMEMBERME = false;

    /*
    EXERCICE 2 — CYCLE DE VIE D'UNE APPLICATION

    • Lorsque l'application se lance : les méthodes "onCreate" et "onResume" sont exécutées
    • Lorsque l'on change l'orientation du téléphone : les méthodes "onPause", "onSaveInstanceState", "onDestroy", "onCreate", "onRestoreInstanceState" et "onResume" sont exécutées successivement.
    • Lorsque l'on ferme l'application : rien ne se passe
    • Lorsque l'on met l'application en arrière plan : les méthodes "onPause" et "onSaveInstanceState" sont exécutées successivement
    • Lorsque l'on relance l'application : les méthodes "onDestroy", "onCreate", "onRestoreInstanceStat", et "onResume" sont successivement exécutées
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editTextUsername = (EditText)findViewById(R.id.editText2);
        editTextPassword = (EditText)findViewById(R.id.editText3);

        errorUsername = (TextView)findViewById(R.id.textView4);
        errorPassword = (TextView)findViewById(R.id.textView5);

        rememberMe = (CheckBox)findViewById(R.id.checkBox);

        preferences = PreferenceManager.getDefaultSharedPreferences(this);


        editTextUsername.setText(preferences.getString("remember_me_username", ""));
        editTextPassword.setText(preferences.getString("remember_me_password", ""));
        rememberMe.setChecked(preferences.getBoolean("remember_me", false));

        Log.i("YNOV", "on create");
    }

    public void empty (View v) {
        editTextUsername.setText("");
        editTextPassword.setText("");
    }

    public void login(View v) {
        String username = editTextUsername.getText().toString();
        String password = editTextPassword.getText().toString();

        int errors = 0;

        errorUsername.setVisibility(View.GONE);
        errorPassword.setVisibility(View.GONE);

        if (username.equals("")) {
            errorUsername.setVisibility(View.VISIBLE);
            errors++;
        }

        if (password.equals("") || password.length() < 8) {
            errorPassword.setVisibility(View.VISIBLE);
            errors++;
        }

        if (errors != 0) {
            return;
        }


        SharedPreferences.Editor editor = preferences.edit();
        if (rememberMe.isChecked()) {
            editor.putBoolean("remember_me", true);
            editor.putString("remember_me_username", username);
            editor.putString("remember_me_password", password);
            editor.commit();
        } else {
            editor.putBoolean("remember_me", false);
            editor.putString("remember_me_username", "");
            editor.putString("remember_me_password", "");
            editor.commit();
        }

        String urlString = "http://c174a415.ngrok.io/login/" + username;
        new MakeRequest().execute(urlString);

        startActivity(new Intent(MainActivity.this, ListProspectActivity.class));
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.i("YNOV", "on destroy");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.i("YNOV", "on pause");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.i("YNOV", "on resume");
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        Log.i("YNOV", "on save instance state");
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        Log.i("YNOV", "on restore instance state");
    }
}

class MakeRequest extends AsyncTask<String, Integer, String> {

    @Override
    protected String doInBackground(String... urls) {

        try {
            URL url = new URL(urls[0]);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.connect();
            BufferedReader rd = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String content = "", line;
            while ((line = rd.readLine()) != null) {
                content += line + "\n";
            }
            return content;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }
}
