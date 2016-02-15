package com.example.feng.leagueoflegend;

import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private String result = null;
    public static ChampionDBHelper cdb;
    public static ArrayList<Champion> champions;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        cdb = new ChampionDBHelper(this);
        champions = new ArrayList<>();

        Toast.makeText(getApplicationContext(),"Loading Data...", Toast.LENGTH_SHORT).show();

        ConnectivityManager connMgr = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();

        if (networkInfo != null && networkInfo.isConnected())
        {
            new NetworkingTask().execute();
        }
        else
        {
            Context context = getApplicationContext();
            CharSequence text = "Network connection NOT available";
            int duration = Toast.LENGTH_SHORT;
            Toast toast = Toast.makeText(context, text, duration);
            toast.show();
        }




    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        if(id == R.id.enter)
        {
            Intent i = new Intent(getApplicationContext(),Main2Activity.class);
            startActivity(i);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private class NetworkingTask extends AsyncTask<Void, Void, Void> {

        protected Void doInBackground(Void... params) {
            HttpURLConnection urlConnection = null;
            BufferedReader reader = null;

            try {

                String sUrl = "http://ddragon.leagueoflegends.com/cdn/5.23.1/data/en_US/champion.json";
                URL url = new URL(sUrl);
                urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setRequestMethod("GET");
                urlConnection.connect();

                // Read the input stream
                InputStream inputStream = urlConnection.getInputStream();

                if(inputStream == null)
                {
                    return null;
                }

                //Place input stream into a buffered reader
                reader = new BufferedReader(new InputStreamReader(inputStream));
                String line;
                StringBuffer buffer = new StringBuffer();
                while ((line = reader.readLine()) != null)
                {
                    buffer.append(line + "\n");
                }
                if(buffer.length()==0)
                {
                    return null;
                }

                //Create data from URL
                result = buffer.toString();

            } catch (IOException e) {
                Log.e("MainActivity", "Error ", e);
            }
            finally {
                if (urlConnection != null)
                    urlConnection.disconnect();
                if (reader != null) {
                    try {
                        reader.close();
                    } catch (final IOException e) {
                        Log.e("MainActivity", "Error closing stream", e);
                    }
                }
            }

            return null;

        }

        @Override
        protected void onPostExecute(Void results) {

            try
            {
                JSONObject firstLevelJson = new JSONObject(result);
                JSONObject secondLevelJson = firstLevelJson.getJSONObject("data");
                String temp = secondLevelJson.toString();
                JSONTokener token = new JSONTokener(temp);
                JSONObject obj = (JSONObject)token.nextValue();
                JSONArray array = obj.names();

                for(int i = 0; i < array.length(); i++)
                {
                    JSONObject tempJson = obj.getJSONObject(array.get(i).toString());
                    JSONObject image = tempJson.getJSONObject("image");
                    JSONArray tags = tempJson.getJSONArray("tags");
                    String tagTwo;
                    if(tags.length() == 1)
                    {
                        tagTwo = null;
                    }
                    else
                    {
                        tagTwo = tags.get(1).toString();
                    }
                    JSONObject stats = tempJson.getJSONObject("stats");

                    cdb.insertChampion(tempJson.getString("key"), tempJson.getString("name"), tempJson.getString("title"), tempJson.getString("blurb"),
                           tags.get(0).toString(), tagTwo, stats.getString("hp"),
                            stats.getString("hpregen"), stats.getString("mp"), stats.getString("mpregen"),
                            stats.getString("movespeed"), stats.getString("attackdamage"), stats.getString("attackspeedperlevel"),
                            stats.getString("attackrange"), stats.getString("armor"), stats.getString("spellblock"), image.getString("full"));

                }
                Toast.makeText(getApplicationContext(),"Loading Data Complete", Toast.LENGTH_SHORT).show();
                champions = cdb.getAllChampions();
            }
            catch(JSONException e)
            {
                Log.e("JSON", "JSON Exception:" + e);
            }

        }

    }


}
