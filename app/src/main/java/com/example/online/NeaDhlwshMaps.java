package com.example.online;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentActivity;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.PermissionInfo;
import android.location.Location;
import android.location.LocationManager;
import android.location.LocationProvider;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.PermissionRequest;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.Projection;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class NeaDhlwshMaps extends FragmentActivity
        implements
        OnMapReadyCallback,
        GoogleMap.OnMyLocationButtonClickListener,
        GoogleMap.OnMyLocationClickListener,
        ActivityCompat.OnRequestPermissionsResultCallback {

    private static final int LOCATION_PERMISSION_REQUEST_CODE = 1;

    private boolean permissionDenied = false;

    private GoogleMap mMap;

    Location location;
    LocationManager locationManager;
    private DatabaseReference mRef;

    final private String FCM_API = "https://fcm.googleapis.com/fcm/send";
    final private String serverKey = "key=" + "AAAAJzVvQ-o:APA91bFapl0U-c0n4t3ILu3IfwMxH1gB9eShkubS-aZoemw5eZNXoc0N45cb-M2lpa9AFY4cmySv5gCavVpY9zleXYgqmMR0EdYDexlzGNsQ-wnOjigTYQcC6ogN4UmLufQXgrBJOuGc";
    final private String contentType = "application/json";
    final String TAG = "NOTIFICATION TAG";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nea_dhlwsh_maps);


        final Intent intent7 = getIntent();
        final HashMap<String, String> data1 = (HashMap<String, String>)intent7.getSerializableExtra("stoixeia");
        final String key = intent7.getStringExtra("iddhlwshs").toString();
        final String idb = intent7.getStringExtra("idb").toString();

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        //locationManager.requestLocationUpdates();
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
        }
        //System.out.println(location.getLatitude());
        //System.out.println(location.getLongitude());

        findViewById(R.id.qwe).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Double lat = location.getLatitude();
                Double lon = location.getLongitude();
                data1.put("Longitude", lon.toString());
                data1.put("Latitude", lat.toString());
                data1.put("Επιβεβαιωμένη", "Όχι");

                mRef = FirebaseDatabase.getInstance().getReference().child("Λίστα Δηλώσεων/"+key);
                mRef.setValue(data1);
                Toast.makeText(NeaDhlwshMaps.this, "Όλα έτοιμα! Έχει σταλεί ειδοποίηση στον Οδηγό Β. Ευχαριστούμε που χρησιμοποιήσατε την εφαρμογή Φιλική Δήλωση Online", Toast.LENGTH_LONG).show();

                String TOPIC = "/topics/"+idb;
                String NOTIFICATION_TITLE = "Φιλική Δήλωση Online";
                String NOTIFICATION_MESSAGE = "Σας απεστάλη αίτημα νέας Φιλικής Δήλωσης. Αν είσαστε ο δεύτερος οδηγός του ατυχήματος, παρακαλώ πατήστε επάνω σε αυτή την ειδοποίηση, αλλιώς απλώς αγνοήστε την ειδοποίηση.";

                JSONObject notif = new JSONObject();
                JSONObject notifcationBody = new JSONObject();
                try {
                    notifcationBody.put("title", NOTIFICATION_TITLE);
                    notifcationBody.put("message", NOTIFICATION_MESSAGE);

                    notif.put("to", TOPIC);
                    notif.put("data", notifcationBody);
                } catch (JSONException e) {
                    Log.e(TAG, "onCreate: " + e.getMessage() );
                }
                sendNotification(notif);

                Intent intent31 = new Intent(NeaDhlwshMaps.this, StoixeiaXr.class);
                startActivity(intent31);
            }
        });
    }


    private void sendNotification(JSONObject notif) {
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(FCM_API, notif,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.i(TAG, "onResponse: " + response.toString());
                        //edtTitle.setText("");
                        //edtMessage.setText("");
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(NeaDhlwshMaps.this, "Request error", Toast.LENGTH_LONG).show();
                        Log.i(TAG, "onErrorResponse: Didn't work");
                    }
                }){
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("Authorization", serverKey);
                params.put("Content-Type", contentType);
                return params;
            }
        };
        MySingleton.getInstance(getApplicationContext()).addToRequestQueue(jsonObjectRequest);
    }
    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        LatLng athome = new LatLng(37.9888946, 23.7600405);
        Marker marker = mMap.addMarker(new MarkerOptions().position(athome).title("Τόπος Ατυχήματος").draggable(true));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(athome));

        mMap.setOnMyLocationButtonClickListener(this);
        mMap.setOnMyLocationClickListener(this);
        enableMyLocation();
        //mMap.addMarker(new MarkerOptions().position(loc));

        mMap.setOnMarkerDragListener(new GoogleMap.OnMarkerDragListener() {
            @Override
            public void onMarkerDragStart(Marker marker) {
                Log.d("System out", "onMarkerDragStart..."+marker.getPosition().latitude+"..."+marker.getPosition().longitude);
            }

            @Override
            public void onMarkerDrag(Marker marker) {
                Log.i("System out", "onMarkerDrag...");
            }

            @Override
            public void onMarkerDragEnd(Marker marker) {
                Log.d("System out", "onMarkerDragEnd..."+marker.getPosition().latitude+"..."+marker.getPosition().longitude);

                mMap.animateCamera(CameraUpdateFactory.newLatLng(marker.getPosition()));
            }
        });
    }

    private void enableMyLocation() {
        // [START maps_check_location_permission]
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            if (mMap != null) {
                mMap.setMyLocationEnabled(true);
            }
        } else {
            // Permission to access the location is missing. Show rationale and request permission
            //PermissionUtils.requestPermission(this, LOCATION_PERMISSION_REQUEST_CODE,
                    //Manifest.permission.ACCESS_FINE_LOCATION, true);
        }
    }

    @Override
    public boolean onMyLocationButtonClick() {
        return false;
    }

    @Override
    public void onMyLocationClick(@NonNull Location location) {

    }


}