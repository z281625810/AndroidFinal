package com.example.feng.leagueoflegend;


import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.ShareActionProvider;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;
import java.io.InputStream;



public class DetailActivity extends AppCompatActivity {

    private ShareActionProvider mShareActionProvider;
    private int n = 1;
    private int position;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        Bundle bundle = getIntent().getExtras();
        position = bundle.getInt("position");

        setTitle(MainActivity.champions.get(position).getName());

        TextView labelView = (TextView)findViewById(R.id.label);
        TextView valueView = (TextView)findViewById(R.id.value);
        ImageView imageView = (ImageView)findViewById(R.id.image);

        new DownloadImageTask(imageView).execute("http://ddragon.leagueoflegends.com/cdn/5.23.1/img/champion/" + MainActivity.champions.get(position).getImage());
        labelView.setText(MainActivity.champions.get(position).getName() + " --- " + MainActivity.champions.get(position).getId());
        String tagOne = MainActivity.champions.get(position).getTagOne();
        String tagTwo = MainActivity.champions.get(position).getTagTwo();
        if(tagTwo == null) {
            valueView.setText(MainActivity.champions.get(position).getTitle() + "\n" + tagOne);
        }
        else
        {
            valueView.setText(MainActivity.champions.get(position).getTitle() + "\n" + tagOne + ", " + tagTwo);
        }

        TextView statView = (TextView)findViewById(R.id.stats);
        String stats = "Description: \n" + MainActivity.champions.get(position).getDescription() + " \n\n"
                + "Health: " + MainActivity.champions.get(position).getHealth() + " \n"
                + "Health Regen: " + MainActivity.champions.get(position).getHealthRegen() + " \n"
                + "Mana: " + MainActivity.champions.get(position).getMana() + " \n"
                + "Mana Regen: " + MainActivity.champions.get(position).getManaRegen() + " \n"
                + "Move Speed: " + MainActivity.champions.get(position).getMoveSpeed() + " \n"
                + "Att. Damage: " + MainActivity.champions.get(position).getAttackDamage() + " \n"
                + "Att. Speed: " + MainActivity.champions.get(position).getAttackSpeed() + " \n"
                + "Att. Range: " + MainActivity.champions.get(position).getAttackRange() + " \n"
                + "Armor: " + MainActivity.champions.get(position).getArmor() + " \n"
                + "MR: " + MainActivity.champions.get(position).getMR() + " \n";

        statView.setText(stats);
    }

    private class DownloadImageTask extends AsyncTask<String, Void, Bitmap> {
        ImageView bitmapImage;

        public DownloadImageTask(ImageView bitmapImage) {
            this.bitmapImage = bitmapImage;
        }
        protected Bitmap doInBackground(String... urls) {
            String urlDisplay = urls[0];
            Bitmap mIcon = null;
            try {
                InputStream in = new java.net.URL(urlDisplay).openStream();
                mIcon = BitmapFactory.decodeStream(in);
            } catch (Exception e) {
                Log.e("Error", e.getMessage());
                e.printStackTrace();
            }

            return mIcon;
        }

        protected void onPostExecute(Bitmap result) {
            bitmapImage.setImageBitmap(result);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_detail, menu);

        // Get the menu item.
        MenuItem menuItem = menu.findItem(R.id.share);
        // Get the provider and hold onto it to set/change the share intent.
        mShareActionProvider = (ShareActionProvider) MenuItemCompat.getActionProvider(menuItem);
        // Set share Intent.
        // Note: You can set the share Intent afterwords if you don't want to set it right now.
        mShareActionProvider.setShareIntent(createShareIntent());

        return true;
    }

    private Intent createShareIntent() {

        TextView labelView = (TextView)findViewById(R.id.label);
        TextView valueView = (TextView)findViewById(R.id.value);
        TextView statView = (TextView)findViewById(R.id.stats);

        Intent shareIntent = new Intent(Intent.ACTION_SEND);
        shareIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_WHEN_TASK_RESET);
        shareIntent.setType("*/*");

        shareIntent.putExtra(Intent.EXTRA_TEXT, labelView.getText().toString() + "\n" + valueView.getText().toString() + "\n\n" + statView.getText().toString());

        return shareIntent;
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

        if (id == R.id.send)
        {
            TextView labelView = (TextView)findViewById(R.id.label);
            TextView valueView = (TextView)findViewById(R.id.value);
            TextView statView = (TextView)findViewById(R.id.stats);

            Intent i = new Intent(getApplicationContext(), SendActivity.class);
            i.putExtra("label", labelView.getText().toString());
            i.putExtra("value", valueView.getText().toString());
            i.putExtra("stat", statView.getText().toString());
            startActivity(i);

            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
