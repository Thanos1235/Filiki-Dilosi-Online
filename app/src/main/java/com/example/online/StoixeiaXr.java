package com.example.online;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

public class StoixeiaXr extends AppCompatActivity {

    private DatabaseReference mRef;
    private FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

    final private String FCM_API = "https://fcm.googleapis.com/fcm/send";
    final private String serverKey = "key=" + "AAAAJzVvQ-o:APA91bFapl0U-c0n4t3ILu3IfwMxH1gB9eShkubS-aZoemw5eZNXoc0N45cb-M2lpa9AFY4cmySv5gCavVpY9zleXYgqmMR0EdYDexlzGNsQ-wnOjigTYQcC6ogN4UmLufQXgrBJOuGc";
    final private String contentType = "application/json";
    final String TAG = "NOTIFICATION TAG";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stoixeia_xr);

        final String uid = user.getUid();
        mRef = FirebaseDatabase.getInstance().getReference().child("Λίστα Χρηστών").child(uid);
        mRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Iterator i = dataSnapshot.getChildren().iterator();
                StringBuilder addable = new StringBuilder();
                while (i.hasNext()) {
                    String key = (((DataSnapshot) i.next()).getKey());
                    String value = ((dataSnapshot).child(key).getValue().toString());
                    addable.append(key).append(": ").append(value).append("\n");
                }
                TextView lista = findViewById(R.id.stoixeia);
                lista.setText(addable);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        findViewById(R.id.logout).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseAuth.getInstance().signOut();
                Intent intent4 = new Intent();
                intent4.setClassName("com.example.online", "com.example.online.MainActivity");
                startActivity(intent4);
            }
        });

        findViewById(R.id.neadhlwsh).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent5 = new Intent();
                intent5.setClassName("com.example.online", "com.example.online.NeaDhlwsh1");
                startActivity(intent5);
            }
        });
    }
}