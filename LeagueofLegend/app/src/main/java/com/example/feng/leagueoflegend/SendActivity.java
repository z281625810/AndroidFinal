package com.example.feng.leagueoflegend;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Iterator;

public class SendActivity extends AppCompatActivity {

    static int ATTACHMENT_SELECTED = 0;
    boolean hasAttachment = false;
    String attachmentPath;
    ArrayList<String> mList = new ArrayList<String>();
    Iterator it = mList.iterator();
    ArrayList<Uri> uris = new ArrayList<Uri>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send);
        Bundle bundle = getIntent().getExtras();
        String label = bundle.getString("label");
        String value = bundle.getString("value");
        String stat = bundle.getString("stat");

        setTitle("Send Email");

        EditText message = (EditText)findViewById(R.id.message);
        message.setText(label + "\n" + value + "\n\n" + stat);


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_send, menu);
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

        return super.onOptionsItemSelected(item);
    }

    public void send(View view)
    {
        EditText toField = (EditText) findViewById(R.id.to_whom);
        Editable toEditable = toField.getText();
        String toWhom = toEditable.toString();
        String[] addresses = new String[]{toWhom};
        EditText subjectField = (EditText) findViewById(R.id.subject);
        Editable subjectEditable = subjectField.getText();
        String subject = subjectEditable.toString();
        EditText messageField = (EditText) findViewById(R.id.message);
        Editable messageEditable = messageField.getText();
        String message = messageEditable.toString();
        Intent intent = new Intent(Intent.ACTION_SEND_MULTIPLE);
        intent.setType("message/rfc822");
        intent.putExtra(Intent.EXTRA_EMAIL, addresses);
        intent.putExtra(Intent.EXTRA_SUBJECT, subject);
        intent.putExtra(Intent.EXTRA_TEXT, message);
        if (hasAttachment)
        {
            Iterator it = mList.iterator();
            while(it.hasNext())
            {
                String u = (String) it.next();
                Uri uri = Uri.parse(u);
                uris.add(uri);
            }
            intent.putParcelableArrayListExtra(Intent.EXTRA_STREAM, uris);
        }
        if (intent.resolveActivity(getPackageManager()) != null)
        {
            startActivity(intent);
        }
        else
        {
            Context context = getApplicationContext();
            String text = "No app can respond to your intent";
            int duration = Toast.LENGTH_LONG;
            Toast toast = Toast.makeText(context, text, duration);
            toast.show();
        }
        finish();
    }

    public void attachFile(View view)
    {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("*/*");
        startActivityForResult(intent, ATTACHMENT_SELECTED);
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        if (resultCode == RESULT_OK)
        {
            if (requestCode == ATTACHMENT_SELECTED)
            {
                attachmentPath = data.getData().toString();
                mList.add(attachmentPath);
                hasAttachment = true;
                Context context = getApplicationContext();
                String text = "Add an attachment successfully";
                int duration = Toast.LENGTH_LONG;
                Toast toast = Toast.makeText(context, text, duration);
                toast.show();
            }
        }
    }
}
