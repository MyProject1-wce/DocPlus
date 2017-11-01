package com.example.dell.docplus;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Doc_profile extends AppCompatActivity {
    EditText name,mobile,email,age,gender,degree,special,exp,cname,cadd,fees,mobe,degreee,cnamee,cadde,feese;
    DatabaseReference dr;
    Button btn,btne;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doc_profile);
        ActionBar actionBar=getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);      /*  Toolbar toolbar = (Toolbar) findViewById(R.id.action_edit);
        setSupportActionBar(toolbar);*/
        name=(EditText)findViewById(R.id.profile_doc_name);
        mobile=(EditText)findViewById(R.id.profile_doc_mob);
        mobe=(EditText)findViewById(R.id.profile_doc_mobEdit);
        mobe.setVisibility(View.INVISIBLE);
        email=(EditText)findViewById(R.id.profile_doc_email);
        age=(EditText)findViewById(R.id.profile_doc_agetext);
        gender=(EditText)findViewById(R.id.profile_doc_gender);
        special=(EditText)findViewById(R.id.profile_doc_speciality);
        degree=(EditText)findViewById(R.id.profile_doc_degree);
        degreee=(EditText)findViewById(R.id.profile_doc_degreeEdit);
        degreee.setVisibility(View.INVISIBLE);
        exp=(EditText)findViewById(R.id.profile_doc_experience);
        cname=(EditText)findViewById(R.id.profile_doc_cname);
        cnamee=(EditText)findViewById(R.id.profile_doc_cnameEdit);
        cnamee.setVisibility(View.INVISIBLE);
        cadd=(EditText)findViewById(R.id.profile_doc_cadd);
        cadde=(EditText)findViewById(R.id.profile_doc_caddEdit);
        cadde.setVisibility(View.INVISIBLE);
        fees=(EditText)findViewById(R.id.profile_doc_fees);
        fees.setEnabled(true);
        feese=(EditText)findViewById(R.id.profile_doc_feesEdit);
        feese.setVisibility(View.INVISIBLE);
        btn=(Button)findViewById(R.id.profile_doc_btn);
        btne=(Button)findViewById(R.id.profile_doc_btnEdit);
        btne.setVisibility(View.INVISIBLE);
        Log.d("txyz","1");
        dr= FirebaseDatabase.getInstance().getReference().child("doctors").child(FirebaseAuth.getInstance().getCurrentUser().getUid());
        Log.d("txyz","2");
        setProfile();
    }
    void setProfile(){
        dr.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                dataSnapshot.getChildren();
                name.setText(dataSnapshot.child("name").getValue().toString());
                mobile.setText(dataSnapshot.child("mobile").getValue().toString());
                email.setText(dataSnapshot.child("email").getValue().toString());
                age.setText(dataSnapshot.child("age").getValue().toString());
                gender.setText(dataSnapshot.child("gender").getValue().toString());
                special.setText(dataSnapshot.child("speciality").getValue().toString());
                degree.setText(dataSnapshot.child("degree").getValue().toString());
                exp.setText(dataSnapshot.child("experience").getValue().toString());
                cname.setText(dataSnapshot.child("cname").getValue().toString());
                cadd.setText(dataSnapshot.child("cadd").getValue().toString());
                fees.setText(dataSnapshot.child("fees").getValue().toString());
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
    public void onBackPressed() {
        //your method call
        super.onBackPressed();
        Intent in=new Intent(Doc_profile.this,Doc_MainActivity.class);
        startActivity(in);
        finish();
    }

    public void editUser(View view) {
        mobile.setVisibility(View.INVISIBLE);
        mobe.setText(mobile.getText());
        mobe.setVisibility(View.VISIBLE);

        degree.setVisibility(View.INVISIBLE);
        degreee.setText(degree.getText());
        degreee.setVisibility(View.VISIBLE);

        cname.setVisibility(View.INVISIBLE);
        cnamee.setText(cname.getText());
        cnamee.setVisibility(View.VISIBLE);

        cadd.setVisibility(View.INVISIBLE);
        cadde.setText(cadd.getText());
        cadde.setVisibility(View.VISIBLE);

        fees.setVisibility(View.INVISIBLE);
        feese.setText(fees.getText());
        feese.setVisibility(View.VISIBLE);

        btn.setVisibility(View.INVISIBLE);
        btne.setVisibility(View.VISIBLE);
        //InputMethodManager imm = (InputMethodManager) getSystemService(this.INPUT_METHOD_SERVICE);
        //imm.toggleSoftInput(InputMethodManager.SHOW_FORCED,InputMethodManager.HIDE_IMPLICIT_ONLY);
        // imm.showSoftInput(fees, InputMethodManager.SHOW_IMPLICIT);
    }

    public void saveUser(View view) {
      mobe.setVisibility(View.INVISIBLE);
        mobile.setText(mobe.getText());
        mobile.setVisibility(View.VISIBLE);

        degreee.setVisibility(View.INVISIBLE);
        degree.setText(degreee.getText());
        degree.setVisibility(View.VISIBLE);

        cnamee.setVisibility(View.INVISIBLE);
        cname.setText(cnamee.getText());
        cname.setVisibility(View.VISIBLE);

        cadde.setVisibility(View.INVISIBLE);
        cadd.setText(cadde.getText());
        cadd.setVisibility(View.VISIBLE);

        feese.setVisibility(View.INVISIBLE);
        fees.setText(feese.getText());
        fees.setVisibility(View.VISIBLE);
        dr=FirebaseDatabase.getInstance().getReference().child("doctors").child(FirebaseAuth.getInstance().getCurrentUser().getUid());
        /*HashMap<String,String> hm=new HashMap<>();
        hm.put("mobile",mobe.getText().toString());
        hm.put("degree",degreee.getText().toString());
        hm.put("cname",cnamee.getText().toString());
        hm.put("cadd",cadde.getText().toString());
        hm.put("fees",feese.getText().toString());
        dr.setValue(hm, new DatabaseReference.CompletionListener() {
            @Override
            public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {
                if (databaseError != null) {
                   Toast.makeText(Doc_profile.this,"Data could not be saved " + databaseError.getMessage(),Toast.LENGTH_LONG).show();
                } else {
                    mobe.setVisibility(View.INVISIBLE);
                    mobile.setText(mobe.getText());
                    mobile.setVisibility(View.VISIBLE);

                    degreee.setVisibility(View.INVISIBLE);
                    degree.setText(degreee.getText());
                    degree.setVisibility(View.VISIBLE);

                    cnamee.setVisibility(View.INVISIBLE);
                    cname.setText(cnamee.getText());
                    cname.setVisibility(View.VISIBLE);

                    cadde.setVisibility(View.INVISIBLE);
                    cadd.setText(cadde.getText());
                    cadd.setVisibility(View.VISIBLE);

                    feese.setVisibility(View.INVISIBLE);
                    fees.setText(feese.getText());
                    fees.setVisibility(View.VISIBLE);
                    btne.setVisibility(View.INVISIBLE);
                    btn.setVisibility(View.VISIBLE);
                    Toast.makeText(Doc_profile.this,"Data saved successfully.",Toast.LENGTH_LONG).show();
                }
            }
        });*/
        dr.child("mobile").setValue(mobe.getText().toString());
        dr.child("degree").setValue(degreee.getText().toString());
        dr.child("cname").setValue(cnamee.getText().toString());
        dr.child("cadd").setValue(cadde.getText().toString());
        dr.child("fees").setValue(feese.getText().toString());
        btne.setVisibility(View.INVISIBLE);
        btn.setVisibility(View.VISIBLE);
    }

   /* @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_edit, menu);
        return true;
    }*/

}
