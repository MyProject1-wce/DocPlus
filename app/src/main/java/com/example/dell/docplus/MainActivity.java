package com.example.dell.docplus;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.vistrav.ask.Ask;

import java.util.List;


public class MainActivity extends AppCompatActivity implements android.location.LocationListener {
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private ProgressDialog pd;
    int id = 7;
    List<Address> list;
    Geocoder geocoder;
    LocationManager locationManager;
    GoogleApiClient gac;
    private double latitude=-1.0,longitude=-1.0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        /*Intent intent=new Intent(MainActivity.this,Patient_Signup.class);
        startActivity(intent);
        finish();*/
        //gac=new GoogleApiClient.Builder(this).addConnectionCallbacks((GoogleApiClient.ConnectionCallbacks) this).addOnConnectionFailedListener((GoogleApiClient.OnConnectionFailedListener) this).addApi(LocationServices.API).build();`
        Ask.on(this).id(id).forPermissions(Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION).withRationales("Grant Location permission", "Grant Location permission").go();
        pd = new ProgressDialog(MainActivity.this, ProgressDialog.STYLE_SPINNER);
        pd.setTitle("Login");
        pd.setMessage("Logining in!!");
        pd.setCanceledOnTouchOutside(false);
        pd.show();
        mAuth = FirebaseAuth.getInstance();
        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null) {
                    // User is signed in
                    final String uid = user.getUid();
                    Log.d("tabc", uid);
                    DatabaseReference doc = FirebaseDatabase.getInstance().getReference().child("doctors");
                    doc.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            for (DataSnapshot ds : dataSnapshot.getChildren()) {
                                String curr = ds.getKey();
                                if (curr.equals(uid)) {
                                    pd.dismiss();
                                    Intent intent = new Intent(MainActivity.this, Doc_MainActivity.class);
                                    startActivity(intent);
                                    finish();
                                }
                            }
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });
                    doc = FirebaseDatabase.getInstance().getReference().child("patients");
                    doc.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            for (DataSnapshot ds : dataSnapshot.getChildren()) {
                                String curr = ds.getKey();
                                if (curr.equals(uid)) {
                                    pd.dismiss();
                                    Intent intent = new Intent(MainActivity.this, Patient_MainActivity.class);
                                    startActivity(intent);
                                    finish();
                                }
                            }
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {
                        }
                    });
                    Log.d("txyz", "onAuthStateChanged:signed_in:" + user.getUid());
                } else {
                    // User is signed out
                    pd.dismiss();
                    Log.d("txyz", "onAuthStateChanged:signed_out");
                }
                // ...
            }
        };
        Log.d("txyz1", "abc");
        locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        Log.d("txyz1", "abc");
        locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 1000, 1, (LocationListener) this);
    }


    @Override
    public void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(mAuthListener);
    }

    @Override
    public void onStop() {
        super.onStop();
        if (mAuthListener != null) {
            mAuth.removeAuthStateListener(mAuthListener);
        }
    }

    public void patLogin(View view) {

        Intent intent = new Intent(MainActivity.this, LoginActivity.class);
        intent.putExtra("flag", "2");
        startActivity(intent);
        finish();
    }

    public void docLogin(View view) {
        Intent intent = new Intent(MainActivity.this, LoginActivity.class);
        intent.putExtra("flag", "1");
        startActivity(intent);
        finish();
    }

    public void clickme(View view) {
        Intent intent = new Intent(MainActivity.this, MapsActivity.class);
        //intent.putExtra("dbref",temp.getKey());
        intent.putExtra("latitude", 16.8457018);
        intent.putExtra("longitude", 74.6012751);
        startActivity(intent);

    }


    @Override
    public void onLocationChanged(Location location) {
        latitude=location.getLatitude();
        longitude=location.getLongitude();
    }

    @Override
    public void onStatusChanged(String s, int i, Bundle bundle) {

    }

    @Override
    public void onProviderEnabled(String s) {

    }

    @Override
    public void onProviderDisabled(String s) {

    }

    public void showLoc(View view) {
        if(latitude!=-1.0 && longitude!=-1.0 ) {
            Toast.makeText(MainActivity.this,""+latitude+""+longitude,Toast.LENGTH_SHORT).show();
        }
        Intent intent =new Intent(MainActivity.this,MapsActivity.class);
        intent.putExtra("latitude",latitude);
        intent.putExtra("longitude",longitude);
        startActivity(intent);

    }
}

