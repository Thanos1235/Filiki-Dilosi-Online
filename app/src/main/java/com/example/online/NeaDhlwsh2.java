package com.example.online;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class NeaDhlwsh2 extends AppCompatActivity {

    private FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

    private DatabaseReference mRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nea_dhlwsh2);

        final Intent intent6 = getIntent();

        final Spinner dropdowna = findViewById(R.id.dropdowna);
        String[] sun8hkesa = {"ΠΑΡΑΚΑΛΩ ΕΠΙΛΕΞΤΕ ΣΥΝΘΗΚΗ ΑΤΥΧΗΜΑΤΟΣ ΟΔΗΓΟΥ Α...", "Σταθμευμένο", "Σε στάση", "Εκκίνηση από στάση", "Άνοιγμα θύρας", "Προς στάθμευση",
        "Εγκατέλειπε χώρο στάθμευσης, ιδιωτικό χώρο, χωματόδρομο", "Εισήρχετο σε χώρο στάθμευσης, ιδιωτικό χώρο, χωματόδρομο",
        "Είσοδος σε πλατεία με κυκλική πορεία", "Κίνηση σε πλατεία με κυκλική πορεία", "Πρόσκρουση στο πίσω μέροσ άλλου οχήματος " +
                "που προχωρούσε στην ίδια κατεύθυνση και στην ίδια λωρίδα", "Εκινείτο στην ίδια κατεύθυνση σε διαφορετική λωρίδα",
        "Άλλαζε λωρίδα", "Προσπερνούσε", "Έστριβε δεξιά", "Έστριβε αριστερά", "Έκανε όπισθεν", "Εκινείτο στο αντίθετο ρεύμα κυκλοφορίας",
        "Εκινείτο από δεξιά(σε διασταύρωση)", "Παρεβίασε σήμα προτεραιότητας ή κόκκινο σηματοδότη"};

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, sun8hkesa);
        dropdowna.setAdapter(adapter);

        final String key = intent6.getStringExtra("key");
        mRef = FirebaseDatabase.getInstance().getReference().child("Λίστα Δηλώσεων/"+key);
        TextView odhgies = findViewById(R.id.odhgies);
        odhgies.setText("Παρακαλώ συμπληρώστε τις συνθήκες του ατυχήματος ως οδηγός Α.\nΤο ID της δήλωσής σας είναι: "+key+" . Παρακαλώ ενημερώστε και τον οδηγό Β, καθώς θα πρέπει να το συμπληρώσει και ο ίδιος.");
        final EditText idogb = findViewById(R.id.idogb);

        findViewById(R.id.submit2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditText parathrhseisa = findViewById(R.id.parathrhseisa);
                String observationsa = parathrhseisa.getText().toString();
                String sun8a = String.valueOf(dropdowna.getSelectedItem());
                HashMap<String, String> data1 = (HashMap<String, String>)intent6.getSerializableExtra("stoixeia");
                data1.put("Συνθήκες οδηγού Α", sun8a);
                String ida = user.getUid();
                String idb = idogb.getText().toString();
                data1.put("ID οδηγού Β", idb);
                data1.put("ID οδηγού Α", ida);
                data1.put("Παρατηρήσεις οδηγού Α", observationsa);

                Intent intent7 = new Intent(NeaDhlwsh2.this, NeaDhlwshMaps.class);
                intent7.putExtra("stoixeia", data1);
                intent7.putExtra("iddhlwshs", key);
                intent7.putExtra("idb", idb);


                startActivity(intent7);
            }
        });
    }
}