package com.example.online;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.messaging.FirebaseMessaging;

import java.util.HashMap;

public class NeosXr extends AppCompatActivity {

    private FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    private DatabaseReference mDataBase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_neos_xr);

        final String idget = user.getUid().toString();

        TextView idxr = findViewById(R.id.idxrhsth);
        idxr.setText("ID: "+idget);

        //ola ta pedia pou sumplirose o neos xristis
        final EditText Epitheto = findViewById(R.id.epitheto);
        final EditText Onoma = findViewById(R.id.onoma);
        final EditText Adt = findViewById(R.id.adt);
        final EditText Imerominiagenn = findViewById(R.id.imerominiagenn);
        final EditText Ardiplomatos = findViewById(R.id.ardiplomatos);
        final EditText Katigoriadipl = findViewById(R.id.katigoriadipl);
        final EditText Diarkeiadipl = findViewById(R.id.diarkeiadipl);
        final EditText Tilefono = findViewById(R.id.tilefono);
        final EditText Email = findViewById(R.id.email);
        final EditText Dieuthinsi = findViewById(R.id.dieuthinsi);
        final EditText Tk = findViewById(R.id.tk);
        final EditText Xora = findViewById(R.id.xora);
        final EditText Marka = findViewById(R.id.marka);
        final EditText Montelo = findViewById(R.id.montelo);
        final EditText Tuposoximatos = findViewById(R.id.tuposoximatos);
        final EditText Arpinakidas = findViewById(R.id.arpinakidas);
        final EditText Xoraautokinitou = findViewById(R.id.xoraautokinitou);
        final EditText Onomaasfalistikis = findViewById(R.id.onomaasflistikis);
        final EditText Arsumvolaiou = findViewById(R.id.arsumvolaiou);
        final EditText Arprasiniskartas = findViewById(R.id.arprasiniskartas);
        final EditText Diarkeiaasfaleias = findViewById(R.id.diarkeiaasfaleias);
        final EditText Praktoreio = findViewById(R.id.praktoreio);

        final Spinner drop = findViewById(R.id.spinner);
        String[] epiloges = new String[]{"ΝΑΙ", "ΟΧΙ"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, epiloges);
        drop.setAdapter(adapter);

        mDataBase = FirebaseDatabase.getInstance().getReference().child("Λίστα Χρηστών");

        findViewById(R.id.kataxorisi).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //pairnei tis times apo ta sumpliromena pedia
                String epitheto = Epitheto.getText().toString().trim();
                String onoma = Onoma.getText().toString().trim();
                String adt = Adt.getText().toString().trim();
                String imerominiagenn = Imerominiagenn.getText().toString().trim();
                String ardiplomatos = Ardiplomatos.getText().toString().trim();
                String katigoriadipl = Katigoriadipl.getText().toString().trim();
                String diarkeiadipl = Diarkeiadipl.getText().toString().trim();
                String tilefono = Tilefono.getText().toString().trim();
                String email = Email.getText().toString().trim();
                String dieuthinsi = Dieuthinsi.getText().toString().trim();
                String tk = Tk.getText().toString().trim();
                String xora = Xora.getText().toString().trim();
                String marka = Marka.getText().toString().trim();
                String montelo = Montelo.getText().toString().trim();
                String tuposoximatos = Tuposoximatos.getText().toString().trim();
                String arpinakidas = Arpinakidas.getText().toString().trim();
                String xoraautokinitou = Xoraautokinitou.getText().toString().trim();
                String onomaasfalistikis = Onomaasfalistikis.getText().toString().trim();
                String arsumvolaiou = Arsumvolaiou.getText().toString().trim();
                String arprasiniskartas = Arprasiniskartas.getText().toString().trim();
                String diarkeiaasfaleias = Diarkeiaasfaleias.getText().toString().trim();
                String praktoreio = Praktoreio.getText().toString().trim();

                //apothikeuei tis times se hashmap
                HashMap <String, String> data = new HashMap<String, String>();
                data.put("ID", idget);
                data.put("Επίθετο", epitheto);
                data.put("Όνομα", onoma);
                data.put("ΑΔΤ", adt);
                data.put("Ημερομηνία γέννησης", imerominiagenn);
                data.put("Αριθμός Διπλώματος", ardiplomatos);
                data.put("Κατηγορία Διπλώματος", katigoriadipl);
                data.put("Διάρκεια Διπλώματος", diarkeiadipl);
                data.put("Τηλέφωνο", tilefono);
                data.put("E-mail", email);
                data.put("Διεύθυνση", dieuthinsi);
                data.put("Ταχυδρομικός Κώδικας", tk);
                data.put("Χώρα", xora);
                data.put("Μάρκα", marka);
                data.put("Μοντέλο", montelo);
                data.put("Τύπος Οχήματος", tuposoximatos);
                data.put("Αριθμός Πινακίδας", arpinakidas);
                data.put("Χώρα Αυτοκινήτου", xoraautokinitou);
                data.put("Όνομα Ασφαλιστικής", onomaasfalistikis);
                data.put("Αριθμός Συμβολαίου", arsumvolaiou);
                data.put("Αριθμός Πράσινης Κάρτας", arprasiniskartas);
                data.put("Διάρκεια Ασφαλείας", diarkeiaasfaleias);
                data.put("Πρακτορείο", praktoreio);
                data.put("Μεικτή Ασφάλεια", drop.getSelectedItem().toString().trim());

                //pernaei to hashmap me tis times sti vasi
                mDataBase.child(user.getUid()).setValue(data);
                FirebaseMessaging.getInstance().subscribeToTopic(idget);
                Toast.makeText(NeosXr.this, "Όλα Έτοιμα!", Toast.LENGTH_SHORT).show();
                Intent intent3 = new Intent();
                intent3.setClassName("com.example.online", "com.example.online.StoixeiaXr");
                startActivity(intent3);
            }
        });
    }
}