package com.example.dell.docplus;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;

import adapter.DoctorAdapter;
import adapter.Model;

public class Doc_Search extends AppCompatActivity {
    LinearLayoutManager linearLayoutManager;
    ArrayList<Model> models;
    DoctorAdapter doctorAdapter;
    RecyclerView recyclerView;
    EditText city,special;
    String tcity,tspecial;
    Dialog dialog;
    ProgressDialog pb;
    StorageReference sr;
    DatabaseReference dr;
    Uri myuri;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doc__search);
        ActionBar actionBar=getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeButtonEnabled(true);
        pb=new ProgressDialog(this);
        pb.setTitle("Search Doctor");
        pb.setMessage("Searching Doctor For You");
        pb.setCanceledOnTouchOutside(false);
        recyclerView = (RecyclerView) findViewById(R.id.doc_search_recyclerview);
        linearLayoutManager=new LinearLayoutManager(this);
      // models= detailsList.getList();
        models=new ArrayList<>();
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setHasFixedSize(true);
      /*  DoctorAdapter doctorAdapter=new DoctorAdapter(Doc_Search.this,models);
        recyclerView.setAdapter(doctorAdapter);*/
        city = (EditText) findViewById(R.id.doc_search_city);
        special=(EditText)findViewById(R.id.doc_search_special);


    }


   /* Moved to doctor adapter
   public void makeAppt(View view) {
        Toast.makeText(this,"Hello",Toast.LENGTH_LONG).show();
        Intent intent=new Intent(Doc_Search.this,Make_Appointment.class);
        startActivity(intent);
        view.setOnClickListener();
    }*/

    public void search(View view) {
        if(tcity==null&&tspecial==null){return;}
        dr= FirebaseDatabase.getInstance().getReference().child("doctors");
        sr= FirebaseStorage.getInstance().getReference().child("images");
        models=new ArrayList<>();
        dr.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for(DataSnapshot ds:dataSnapshot.getChildren()){
                    String name,spec,exp,add,cnm,cty,uid;

                    //String url=sr.child("users/me/profile.png").getDownloadUrl().getResult().toString();

                    //image.setImageBitmap(Bitmap.createScaledBitmap(bmp, image.getWidth(),
                           // image.getHeight(), false)));

                    name=ds.child("name").getValue().toString();
                    spec=ds.child("speciality").getValue().toString();
                    exp=ds.child("experience").getValue().toString();
                    cnm=ds.child("cname").getValue().toString();
                    add=ds.child("cname").getValue().toString();
                    cty=ds.child("city").getValue().toString();
                    uid=ds.getKey();
                    Log.d("txyz","Ret: "+name+" "+spec+" "+exp+" "+cnm+" "+add+" "+tcity);

                    if(cty.equals(tcity) && spec.equals(tspecial))
                        models.add(new Model(R.drawable.a,name,spec,exp,cnm,add,uid));
                    else if(tspecial.equals("All")&& cty.equals(tcity))
                        models.add(new Model(R.drawable.a,name,spec,exp,cnm,add,uid));
                    //Log.d("txyz","Ret: "+name+" "+spec+" "+exp+" "+cnm+" "+add+" "+tcity);
                }
                if(models.size()==0){
                        Toast.makeText(Doc_Search.this,"No Records Found",Toast.LENGTH_SHORT).show();
                recyclerView.setAdapter(null);}
                else {
                    DoctorAdapter doctorAdapter=new DoctorAdapter(Doc_Search.this,models);
                    recyclerView.setAdapter(doctorAdapter);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        dr.keepSynced(true);
        //pb.show();
        /*try{
          Thread.sleep(5000);
        }catch (Exception e)
        {}
        //pb.dismiss();
        Toast.makeText(this,"Selection: "+tcity +" : "+tspecial,Toast.LENGTH_LONG).show();*/
    }


    public void pickSpecial(View view) {
        dialog=new Dialog(Doc_Search.this);
        final CharSequence[] items={"All","Anesthesiologist","Cardiologist","Dermatologist","Nephrologist","Neurologist","Gynecologist","Ophthalmologist","Otolaryngologist","Pathologist","Pediatrician","Plastic Surgeon","Psychiatrist","Rheumatologist","Urologist" };
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Select Speciality");
        builder.setSingleChoiceItems(items, -1, new DialogInterface.OnClickListener() {

            public void onClick(DialogInterface dialog, int item) {
                special.setText(items[item]);
                tspecial=""+items[item];
                dialog.dismiss();

            }
        });
        dialog = builder.create();
        dialog.show();
    }
    public void onBackPressed() {
        //your method call
        super.onBackPressed();
        Intent in=new Intent(Doc_Search.this,Patient_MainActivity.class);
        startActivity(in);
        finish();
    }
    public boolean onOptionsItemSelected(MenuItem item) {
        onBackPressed();
        //this.finish();
        return true;

    }
    public void pickCity(View view) {
       dialog=new Dialog(Doc_Search.this);
        final CharSequence[] items={"Ahmednagar","Akola","Amravati","Aurangabad","Baramati","Barshi","Beed","Bhusawal","Buldana","Chandrapur","Dhule","Gondiya","Ichalkaranji","Jalgaon","Jalna","Kolhapur","Latur","Manmad","Mumbai","Nagpur","Nashik","Osmanabad","Panvel","Parbhani","Pune","Sangli-Miraj-Kupwad","Solapur","Vasai","Virar","Wardha","Washim","Yavatmal" };
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Select City");
        builder.setSingleChoiceItems(items, -1, new DialogInterface.OnClickListener() {

            public void onClick(DialogInterface dialog, int item) {
                city.setText(items[item]);
                tcity=""+items[item];
                dialog.dismiss();

            }
        });
        dialog = builder.create();
        dialog.show();

    }
}
