package com.ismartapp.lenovo.ismart;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import android.widget.Toolbar;

public class feedback extends AppCompatActivity {
    Button b;
    EditText e1, e2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feedback);
        b = (Button) findViewById(R.id.button);
        e1 = (EditText) findViewById(R.id.editText1);
        e2 = (EditText) findViewById(R.id.editText2);

        Toolbar toolbar = (Toolbar) findViewById(R.id.feedback_toolbar);
        toolbar.setTitle("Feedback");
        setActionBar(toolbar);
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Intent.ACTION_SEND);
                i.setType("message/html");
                i.putExtra(Intent.EXTRA_EMAIL, new String[]{"shivammaindola07@gmail.com"});
                i.putExtra(Intent.EXTRA_SUBJECT, "Feedback from App");
                i.putExtra(Intent.EXTRA_TEXT,  e1.getText() + "\n" + e2.getText());
                try {
                    startActivity(Intent.createChooser(i, "Send feedback..."));
                } catch (android.content.ActivityNotFoundException ex) {
                    Toast.makeText(getApplicationContext(), "They're are no email clients installed.", Toast.LENGTH_SHORT).show();
                }
                finish();
            }
        });

    }

}
