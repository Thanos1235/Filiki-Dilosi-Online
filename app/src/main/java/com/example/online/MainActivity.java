package com.example.online;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;

import android.app.Notification;
import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.text.TextUtils;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

//import com.google.android.gms.common.api.Response;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.messaging.FirebaseMessaging;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    private FirebaseAuth mAuth1;
    private FirebaseAuth.AuthStateListener mAuthListener;

    @Override
    protected void onStart() {
        super.onStart();
        mAuth1.addAuthStateListener(mAuthListener);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mAuth1 = FirebaseAuth.getInstance();
        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser currentUser1 = mAuth1.getCurrentUser();
                if (currentUser1 != null) {
                    Intent intent1 = new Intent();
                    intent1.setClassName("com.example.online", "com.example.online.StoixeiaXr");
                    startActivity(intent1);
                }
            }
        };

        final EditText eismail = findViewById(R.id.eismail);
        final EditText eiskod = findViewById(R.id.eispass);
        final CheckBox eischeck = findViewById(R.id.eischeck);
        eiskod.setTransformationMethod(PasswordTransformationMethod.getInstance());
        eischeck.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (!b){
                    eiskod.setTransformationMethod(PasswordTransformationMethod.getInstance());
                } else {
                    eiskod.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                }
            }
        });

        findViewById(R.id.eisodos).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = eismail.getText().toString();
                String password = eiskod.getText().toString();

                if (TextUtils.isEmpty(email) || TextUtils.isEmpty(password)){
                    Toast.makeText(MainActivity.this,
                            "Ένα από τα δύο πεδία είναι κενό. Παρακαλώ συμπληρώστε και τα δύο πεδία.",
                            Toast.LENGTH_LONG).show();
                } else {
                    mAuth1.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(!task.isSuccessful()){
                                Toast.makeText(MainActivity.this,
                                        "Παρουσιάστηκε πρόβλημα. Παρακαλώ προσπαθήστε ξανά.",
                                        Toast.LENGTH_LONG).show();
                            }
                        }
                    });
                }
            }
        });

        findViewById(R.id.neosxr).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent2 = new Intent();
                intent2.setClassName("com.example.online", "com.example.online.DimiourgiaXristi");
                startActivity(intent2);
            }
        });

        findViewById(R.id.eisasf).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = eismail.getText().toString();
                String password = eiskod.getText().toString();

                if (TextUtils.isEmpty(email) || TextUtils.isEmpty(password)){
                    Toast.makeText(MainActivity.this,
                            "Ένα από τα δύο πεδία είναι κενό. Παρακαλώ συμπληρώστε και τα δύο πεδία.",
                            Toast.LENGTH_LONG).show();
                } else {
                    mAuth1.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(!task.isSuccessful()){
                                Toast.makeText(MainActivity.this,
                                        "Παρουσιάστηκε πρόβλημα. Παρακαλώ προσπαθήστε ξανά.",
                                        Toast.LENGTH_LONG).show();
                            } else {
                                Intent intent3 = new Intent();
                                intent3.setClassName("com.example.online", "com.example.online.AsfPelates");
                                startActivity(intent3);
                            }
                        }
                    });
                }
            }
        });
    }
}