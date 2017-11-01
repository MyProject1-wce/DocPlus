package com.example.dell.docplus;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends AppCompatActivity {
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private ProgressDialog pd;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        /*Intent intent=new Intent(MainActivity.this,Patient_Signup.class);
        startActivity(intent);
        finish();*/
        pd=new ProgressDialog(MainActivity.this,ProgressDialog.STYLE_SPINNER);
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
                    final String uid=user.getUid();
                    Log.d("tabc",uid);
                    DatabaseReference doc=FirebaseDatabase.getInstance().getReference().child("doctors");
                    doc.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            for(DataSnapshot ds:dataSnapshot.getChildren()){
                                String curr=ds.getKey();
                                if(curr.equals(uid)){
                                    pd.dismiss();
                                    Intent intent=new Intent(MainActivity.this,Doc_MainActivity.class);
                                    startActivity(intent);
                                    finish();
                                }
                            }
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });
                    doc=FirebaseDatabase.getInstance().getReference().child("patients");
                    doc.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            for(DataSnapshot ds:dataSnapshot.getChildren()){
                                String curr=ds.getKey();
                                if(curr.equals(uid)){
                                    pd.dismiss();
                                    Intent intent=new Intent(MainActivity.this,Patient_MainActivity.class);
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
        setContentView(R.layout.activity_main);
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
        Intent intent=new Intent(MainActivity.this,LoginActivity.class);
        intent.putExtra("flag","2");
        startActivity(intent);
        finish();
    }

    public void docLogin(View view) {
        Intent intent=new Intent(MainActivity.this,LoginActivity.class);
        intent.putExtra("flag","1");
        startActivity(intent);
        finish();
    }
}
