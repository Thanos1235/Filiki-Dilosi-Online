package com.example.online;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class NeaDhlwsh3 extends AppCompatActivity {

    private DatabaseReference mRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nea_dhlwsh3);

        final Intent intent10 = getIntent();

        final Spinner dropdownb = findViewById(R.id.dropdownb);
        String[] sun8hkesb = {"ΠΑΡΑΚΑΛΩ ΕΠΙΛΕΞΤΕ ΣΥΝΘΗΚΗ ΑΤΥΧΗΜΑΤΟΣ ΟΔΗΓΟΥ Β...", "Σταθμευμένο", "Σε στάση", "Εκκίνηση από στάση", "Άνοιγμα θύρας", "Προς στάθμευση",
                "Εγκατέλειπε χώρο στάθμευσης, ιδιωτικό χώρο, χωματόδρομο", "Εισήρχετο σε χώρο στάθμευσης, ιδιωτικό χώρο, χωματόδρομο",
                "Είσοδος σε πλατεία με κυκλική πορεία", "Κίνηση σε πλατεία με κυκλική πορεία", "Πρόσκρουση στο πίσω μέροσ άλλου οχήματος " +
                "που προχωρούσε στην ίδια κατεύθυνση και στην ίδια λωρίδα", "Εκινείτο στην ίδια κατεύθυνση σε διαφορετική λωρίδα",
                "Άλλαζε λωρίδα", "Προσπερνούσε", "Έστριβε δεξιά", "Έστριβε αριστερά", "Έκανε όπισθεν", "Εκινείτο στο αντίθετο ρεύμα κυκλοφορίας",
                "Εκινείτο από δεξιά(σε διασταύρωση)", "Παρεβίασε σήμα προτεραιότητας ή κόκκινο σηματοδότη"};

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, sun8hkesb);
        dropdownb.setAdapter(adapter);

        final EditText edtTxt = findViewById(R.id.idkey);

        findViewById(R.id.submit3).setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                EditText parathrhseisb = findViewById(R.id.parathrhseisb);
                String observationsb = parathrhseisb.getText().toString();
                String sun8b = String.valueOf(dropdownb.getSelectedItem());
                Map<String, Object> data1 = new HashMap<String, Object>();
                data1.put("Συνθήκες οδηγού Β", sun8b);
                data1.put("Παρατηρήσεις οδηγού Β", observationsb);
                data1.put("Επιβεβαιωμένη", "ΝΑΙ");
                String key = edtTxt.getText().toString();
                Toast.makeText(NeaDhlwsh3.this, ""+key, Toast.LENGTH_LONG).show();

                mRef = FirebaseDatabase.getInstance().getReference().child("Λίστα Δηλώσεων/"+key);
                mRef.updateChildren(data1);

                Intent intent8 = new Intent(NeaDhlwsh3.this, StoixeiaXr.class);
                startActivity(intent8);
            }
        });
    }
}