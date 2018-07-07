package com.example.lenovo.ismart;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;
import android.widget.Toolbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.DialogFragment;

public class Settings extends AppCompatActivity {
    FragmentManager fragmentManager;

    ListView listView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        Toolbar toolbar = (Toolbar) findViewById(R.id.setting_toolbar);
        toolbar.setTitle("Settings");
        setActionBar(toolbar);


        // Get ListView object from xml
        listView = (ListView) findViewById(R.id.setting_list);

        // Defined Array values to show in ListView
        String[] values = new String[] { "Change Language",
                "About us",
                "Feedback",
                "Rate us",
                "Share app"
        };

        // Define a new Adapter
        // First parameter - Context
        // Second parameter - Layout for the row
        // Third parameter - ID of the TextView to which the data is written
        // Forth - the Array of data

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, android.R.id.text1, values);


        // Assign adapter to ListView
        listView.setAdapter(adapter);

        // ListView Item Click Listener
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // ListView Clicked item index
                int itemPosition     = position;

                // ListView Clicked item value
                String  itemValue    = (String) listView.getItemAtPosition(position);

                if(position==0) {
                    // Show Alert
                   showchangelanguage();
                }
                if(position==1) {
                    // Show Alert
                    Intent MIntent = new Intent(Settings.this, about_us.class);
                    startActivity(MIntent);
                }

                Toast.makeText(getApplicationContext(),
                        "Position :"+itemPosition+"  ListItem : " +itemValue , Toast.LENGTH_LONG)
                        .show();

            }
        });
}

private void showchangelanguage(){
    final String[] language = {"English","Hindi"};

    final AlertDialog.Builder builder = new AlertDialog.Builder(Settings.this);
    builder.setTitle("Choose your Language");
    builder.setSingleChoiceItems(language, -1, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int i) {
                    if (i == 0) {
                        //english
                        Toast.makeText(Settings.this, "selected language is English", Toast.LENGTH_LONG).show();
                    } else if (i == 1) {
                        //hindi
                        Toast.makeText(Settings.this, "selected language is Hindi", Toast.LENGTH_LONG).show();
                    }
                    dialog.dismiss();
                }
            });

        AlertDialog mDialog = builder.create();
        mDialog.show();
        }
}
