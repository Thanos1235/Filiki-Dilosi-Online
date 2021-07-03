package com.example.online;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.core.Tag;

public class DimiourgiaXristi extends AppCompatActivity {

    private FirebaseAuth mAuth2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dimiourgia_xristi);

        mAuth2 = FirebaseAuth.getInstance();

        final EditText neomail = findViewById(R.id.neomail);
        final EditText neoskod = findViewById(R.id.neoskodikos);
        CheckBox check = findViewById(R.id.check);

        neoskod.setTransformationMethod(PasswordTransformationMethod.getInstance());
        check.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean ischecked) {
                if (!ischecked){
                    neoskod.setTransformationMethod(PasswordTransformationMethod.getInstance());
                } else {
                    neoskod.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                }
            }
        });


        findViewById(R.id.dimourgiabutton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String newmail = neomail.getText().toString();
                String newpass = neoskod.getText().toString();

                if(TextUtils.isEmpty(newmail) || TextUtils.isEmpty(newpass)){
                    Toast.makeText(DimiourgiaXristi.this,
                            "Ένα από τα δύο πεδία είναι κενό. Παρακαλώ συμπληρώστε και τα δύο πεδία.",
                            Toast.LENGTH_LONG).show();
                }else {
                    mAuth2.createUserWithEmailAndPassword(newmail, newpass).addOnCompleteListener(
                            DimiourgiaXristi.this, new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if(!task.isSuccessful()){
                                        Toast.makeText(DimiourgiaXristi.this,
                                                "Παρουσιάστηκε πρόβλημα. Παρακαλώ προσπαθήστε ξανά.",
                                                Toast.LENGTH_LONG).show();
                                    }else{
                                        Toast.makeText(DimiourgiaXristi.this, "Όλα έτοιμα!",
                                                Toast.LENGTH_LONG).show();
                                        Intent intent3 = new Intent();
                                        intent3.setClassName("com.example.online",
                                                "com.example.online.NeosXr");
                                        startActivity(intent3);
                                    }
                                }
                            });
                }
            }
        });
    }
}