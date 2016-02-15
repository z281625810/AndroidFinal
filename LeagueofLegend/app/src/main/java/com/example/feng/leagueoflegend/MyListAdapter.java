package com.example.feng.leagueoflegend;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Image;
import android.os.AsyncTask;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.InputStream;
import java.util.ArrayList;

/**
 * Created by Feng on 12/2/2015.
 */
public class MyListAdapter extends ArrayAdapter<Champion> {
    private final Context context;
    private final ArrayList<Champion> itemsArrayList;

    public MyListAdapter(Context context, ArrayList<Champion> itemsArrayList) {
        super(context, R.layout.list_item, itemsArrayList);
        this.context = context;
        this.itemsArrayList = itemsArrayList;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // 1. Create inflater
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        // 2. Get rowView from inflater
        View rowView = inflater.inflate(R.layout.list_item, parent, false);
        // 3. Get the two text view from the rowView
        TextView labelView = (TextView) rowView.findViewById(R.id.label);
        TextView valueView = (TextView) rowView.findViewById(R.id.value);
        ImageView imageView = (ImageView) rowView.findViewById(R.id.image);

        // 4. Set the text for textView
        new DownloadImageTask(imageView).execute("http://ddragon.leagueoflegends.com/cdn/5.23.1/img/champion/" + itemsArrayList.get(position).getImage());
                labelView.setText(itemsArrayList.get(position).getName() + " --- " + itemsArrayList.get(position).getId());
        String tagOne = itemsArrayList.get(position).getTagOne();
        String tagTwo = itemsArrayList.get(position).getTagTwo();
        if(tagTwo == null) {
            valueView.setText(itemsArrayList.get(position).getTitle() + "\n" + tagOne);
        }
        else
        {
            valueView.setText(itemsArrayList.get(position).getTitle() + "\n" + tagOne + ", " + tagTwo);
        }

        // 5. return rowView
        return rowView;
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

}

