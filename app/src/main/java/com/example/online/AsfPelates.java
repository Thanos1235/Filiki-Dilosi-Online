package com.example.online;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.internal.InternalTokenProvider;

import java.util.Iterator;

public class AsfPelates extends AppCompatActivity {

    private DatabaseReference mRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_asf_pelates);

        mRef = FirebaseDatabase.getInstance().getReference().child("Λίστα Χρηστών");

        findViewById(R.id.aposundesh).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseAuth.getInstance().signOut();
                Intent intent4 = new Intent();
                intent4.setClassName("com.example.online", "com.example.online.MainActivity");
                startActivity(intent4);
            }
        });

        //final EditText search = findViewById(R.id.seachid);

        findViewById(R.id.searchgo).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditText search = findViewById(R.id.seachid);
                String searchparam = search.getText().toString().trim();
                System.out.println(searchparam);
                if(TextUtils.isEmpty(searchparam)){
                    Toast.makeText(AsfPelates.this, "Το πεδίο αναζήτησης είναι κενό. " +
                            "και προσπαθήστε ξανά", Toast.LENGTH_LONG);
                }else {
                    Intent intent5 = new Intent(AsfPelates.this, StoixeiaPel.class);
                    intent5.putExtra("searchuid", searchparam);
                    startActivity(intent5);
                }
            }
        });
    }
}