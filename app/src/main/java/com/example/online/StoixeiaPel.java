package com.example.online;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Iterator;

public class StoixeiaPel extends AppCompatActivity {

    private DatabaseReference mRef;
    private FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stoixeia_pel);

        Intent intent5 = getIntent();
        String uid = intent5.getStringExtra("searchuid");

        mRef = FirebaseDatabase.getInstance().getReference().child("Λίστα Χρηστών").child(uid);
        mRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Iterator i = dataSnapshot.getChildren().iterator();
                StringBuilder addable2 = new StringBuilder();
                while (i.hasNext()) {
                    String key = (((DataSnapshot) i.next()).getKey());
                    String value = ((dataSnapshot).child(key).getValue().toString());
                    addable2.append(key).append(": ").append(value).append("\n");
                }
                TextView listapel = findViewById(R.id.stoixeiaasf);
                listapel.setText(addable2);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        findViewById(R.id.aposundeshasf).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseAuth.getInstance().signOut();
                Intent intent6 = new Intent();
                intent6.setClassName("com.example.online", "com.example.online.MainActivity");
                startActivity(intent6);
            }
        });
    }
}