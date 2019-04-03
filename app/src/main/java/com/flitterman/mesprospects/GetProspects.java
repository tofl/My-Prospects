import android.os.AsyncTask;
import android.util.Log;

import com.flitterman.mesprospects.HttpHandler;
import com.flitterman.mesprospects.Prospect;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Async task class to get json by making HTTP call

static class GetProspects extends AsyncTask<Void, Void, Void> {

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        // Showing progress dialog

    }

    @Override
    protected Void doInBackground(Void... arg0) {
        HttpHandler sh = new HttpHandler();

        // Making a request to url and getting response
        String jsonStr = sh.makeServiceCall(url);

        //Log.e("YNOV", "Response from url: " + jsonStr);

        if (jsonStr != null) {
            try {
                JSONArray jsonObj = new JSONArray(jsonStr);

                // looping through All Prospects
                for (int i = 0; i < jsonObj.length(); i++) {
                    JSONObject c = jsonObj.getJSONObject(i);

                    String name = c.getString("Nom");
                    String email = c.getString("Mail");
                    String address = c.getString("Adresse 1");


                    Prospect prospect = new Prospect();
                    prospect.setName(name);
                    prospect.setMail(email);

                    // adding contact to contact list
                    listProspects.add(prospect);
                }
            } catch (final JSONException e) {
                Log.e("YNOV", "Json parsing error: " + e.getMessage());
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(getApplicationContext(),
                                "Json parsing error: " + e.getMessage(),
                                Toast.LENGTH_LONG)
                                .show();
                    }
                });

            }
        } else {
            Log.e("YNOV", "Couldn't get json from server.");
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Toast.makeText(getApplicationContext(),
                            "Couldn't get json from server. Check LogCat for possible errors!",
                            Toast.LENGTH_LONG)
                            .show();
                }
            });

        }

        return null;
    }

    @Override
    protected void onPostExecute(Void result) {
        super.onPostExecute(result);
        // Dismiss the progress dialog

        // Mise à jour de la liste
        adapter.addAll(listProspects);
        adapter.notifyDataSetChanged();

    }

}
*/