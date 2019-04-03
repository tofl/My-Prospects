package com.flitterman.mesprospects;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Layout;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.view.ViewGroup.LayoutParams;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class ListProspectActivity extends AppCompatActivity {

    private String url = "http://dev.audreybron.fr/flux/flux_prospects.json";
    private ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_prospect);

        listView = findViewById(R.id.list_prospects);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //Intent intent = new Intent(this, ViewProspect.class);
                //intent.putExtra("name", "monnom"); // Pour comniquer une info à la nouvelle activité, utiliser un extra
            }
        });

        final List<String> prospects = new ArrayList<String>();

        JsonArrayRequest request = new JsonArrayRequest(url, new Response.Listener<JSONArray>() {

            @Override
            public void onResponse(JSONArray response) {
                for (int i = 0; i < response.length(); i++) {
                    try {
                        JSONObject prospect = response.getJSONObject(i);
                        prospects.add(i, prospect.getString("Prénom") + " " + prospect.getString("Nom"));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                ArrayAdapter<String>  arrayAdapter = new ArrayAdapter<String>(ListProspectActivity.this, android.R.layout.simple_list_item_1, prospects);
                listView.setAdapter(arrayAdapter);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        });

        RequestQueue queue = Volley.newRequestQueue(this);
        queue.add(request);

        new GetProspects().execute(url);
    }
}


class GetProspects extends AsyncTask<String, String, Void> {

    @Override
    protected Void doInBackground(String... urls) {
        HttpHandler sh = new HttpHandler();
        String jsonString = sh.makeServiceCall(urls[0]);
        Log.e("HeyOh", "Json string : " + jsonString);

        ArrayList<Prospect> listProspect = new ArrayList<Prospect>();


        if (jsonString != null) {
            try {
                JSONArray jsonObj = new JSONArray(jsonString);

                for (int i = 0; i < jsonObj.length(); i++) {
                    JSONObject c = jsonObj.getJSONObject(i);

                    String lastname = c.getString("Nom");
                    String firstname = c.getString("Prénom");
                    String email = c.getString("Mail");
                    String addr1 = c.getString("Adresse 1");
                    String addr2 = c.getString("Addresse 2");
                    String postCode = c.getString("Code Postal");
                    String town = c.getString("Ville");
                    String phone = c.getString("Téléphone");
                    String mail = c.getString("Mail");
                    String company = c.getString("Nom Entreprise");

                    Prospect prospect = new Prospect();
                    prospect.setName(lastname);
                    prospect.setFirstName(firstname);
                    prospect.setMail(email);
                    prospect.setAddr1(addr1);
                    prospect.setAddr2(addr2);
                    prospect.setPostCode(postCode);
                    prospect.setTown(town);
                    prospect.setTelephone(phone);
                    prospect.setMail(mail);
                    prospect.setCompanyName(company);

                    listProspect.add(prospect);
                }
            } catch (Exception e) {
                Log.e("Ynov", "There was an error : " + e.getMessage());
            }
        } else {
            Log.e("Ynov", "La connexion au serveur n'a pas pu être établie");
        }

        return null;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected void onPostExecute(Void result) {
        super.onPostExecute(result);
        Prospect test = new Prospect();
        test.setMail("tom.flitterman@hotmail.fr");

        Log.e("HeyOh", test.getMail());
    }

}