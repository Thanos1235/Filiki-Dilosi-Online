package com.example.online;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class NeaDhlwsh1 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nea_dhlwsh1);

        Date hmerom = Calendar.getInstance().getTime();
        SimpleDateFormat hm = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault());
        final String hmform = hm.format(hmerom);
        TextView date = findViewById(R.id.date);
        date.setText(hmform);

        SimpleDateFormat wra = new SimpleDateFormat("HH:mm", Locale.getDefault());
        final String wraform = wra.format(hmerom);
        final EditText time = findViewById(R.id.time);
        time.setHint(wraform);

        findViewById(R.id.submit1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String realtime = time.getText().toString().trim();
                HashMap<String, String> data1 = new HashMap<String, String>();
                data1.put("Ημερομηνία",hmform);
                data1.put("Ώρα", realtime);

                Intent intent6 = new Intent(NeaDhlwsh1.this, NeaDhlwsh2.class);
                intent6.putExtra("stoixeia", data1);
                startActivity(intent6);
            }
        });
    }
}