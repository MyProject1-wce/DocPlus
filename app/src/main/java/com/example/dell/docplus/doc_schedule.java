package com.example.dell.docplus;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

import adapter.Model1;
import adapter.ScheduleAdapter;

public class doc_schedule extends AppCompatActivity {
   private LinearLayoutManager linearLayoutManager;
    private ArrayList <Model1> models;
    private RecyclerView recyclerView;
    private ScheduleAdapter scheduleAdapter;
    private Calendar c;
    String tdate;
    private EditText date;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doc_schedule);
        ActionBar actionBar=getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeButtonEnabled(true);
        recyclerView= (RecyclerView) findViewById(R.id.doc_schedule_recyclerview);
        date= (EditText) findViewById(R.id.doc_schedule_date);
        linearLayoutManager=new LinearLayoutManager(this);
        models=new ArrayList<>();
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setHasFixedSize(true);
        date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Calendar mcurrentDate=Calendar.getInstance();
                int mYear=mcurrentDate.get(Calendar.YEAR);
                int mMonth=mcurrentDate.get(Calendar.MONTH);
                int mDay=mcurrentDate.get(Calendar.DAY_OF_MONTH);
                c=Calendar.getInstance();
                DatePickerDialog mDatePicker=new DatePickerDialog(doc_schedule.this, new DatePickerDialog.OnDateSetListener() {
                    public void onDateSet(DatePicker datepicker, int year, int month, int day) {
                        c.set(Calendar.YEAR,year);
                        c.set(Calendar.MONTH,month);
                        c.set(Calendar.DAY_OF_MONTH,day);
                        String myFormat="dd-MM-yy";
                        SimpleDateFormat sdf=new SimpleDateFormat(myFormat, Locale.US);
                        date.setText(sdf.format(c.getTime()));

                    }

                },mYear, mMonth, mDay);
                Calendar maxDate = Calendar.getInstance();
                maxDate.set(Calendar.DAY_OF_MONTH, mcurrentDate.get(Calendar.DAY_OF_MONTH) + 6);
                maxDate.set(Calendar.MONTH, mcurrentDate.get(Calendar.MONTH));
                maxDate.set(Calendar.YEAR, mcurrentDate.get(Calendar.YEAR));
                mDatePicker.getDatePicker().setMinDate(mcurrentDate.getTimeInMillis());
                mDatePicker.getDatePicker().setMaxDate(maxDate.getTimeInMillis());
                mDatePicker.setTitle("Pick date");
                mDatePicker.show();
            }});
        }
    String patname,patmobile,patailm,edate,slot,timestamp,patid;
    public void onBackPressed() {
        //your method call
        super.onBackPressed();
        Intent in=new Intent(doc_schedule.this,Doc_MainActivity.class);
        startActivity(in);
        finish();
    }
    public boolean onOptionsItemSelected(MenuItem item) {
        onBackPressed();
        //this.finish();
        return true;

    }
    public void checkDate(View view) {
        tdate=date.getText().toString();
        models=new ArrayList<>();
        String uid= FirebaseAuth.getInstance().getCurrentUser().getUid();
        if(tdate==null){return;}
        DatabaseReference dr= FirebaseDatabase.getInstance().getReference().child("apptlist").child(uid).child(tdate);
        dr.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for(DataSnapshot ds:dataSnapshot.getChildren()){
                    if(!ds.getKey().equals("temp")){
                        String aptid=ds.getValue().toString();
                        DatabaseReference dr1=FirebaseDatabase.getInstance().getReference().child("appointment").child(aptid);

                        dr1.addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {
                                dataSnapshot.getChildren();

                                patname=dataSnapshot.child("patname").getValue().toString();
                                patmobile=dataSnapshot.child("patmob").getValue().toString();
                                patailm=dataSnapshot.child("ailment").getValue().toString();
                                edate=dataSnapshot.child("apptdate").getValue().toString();
                                slot=dataSnapshot.child("apptslot").getValue().toString();
                                timestamp=dataSnapshot.child("timestamp").getValue().toString();
                                patid=dataSnapshot.child("patid").getValue().toString();
                                Log.d("txyz11",patname);
                                models.add(new Model1(patname,patmobile,patailm,edate,slot,timestamp,patid));
                                Log.d("txyz11",""+models.size());
                                //RelativeLayout.LayoutParams lp =
                                    //    new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.WRAP_CONTENT); //<--- ADD
                                //recyclerView.setLayoutParams(lp);//<--- ADD
                                ScheduleAdapter scheduleAdapter=new ScheduleAdapter(doc_schedule.this,models);
                                recyclerView.setAdapter(scheduleAdapter);
                            }
                            @Override
                            public void onCancelled(DatabaseError databaseError) {
                            }
                        });

                    }
                }
                Log.d("txyza",""+models.size());
                if (models.size()==0){
                    //Toast.makeText(doc_schedule.this,"No Records Found",Toast.LENGTH_SHORT).show();
                    recyclerView.setAdapter(null);
                }
               /* else
                {
                    ScheduleAdapter scheduleAdapter=new ScheduleAdapter(doc_schedule.this,models);
                    recyclerView.setAdapter(scheduleAdapter);
                }*/
            }



            /*public void onDataChange(DataSnapshot dataSnapshot) {
                for(DataSnapshot ds:dataSnapshot.getChildren()){
                    if (tdate.equals(ds.getKey())){
                        for(DataSnapshot ds2:ds.getChildren()){
                            String aptid=ds.getValue().toString();
                            Log.d("txyz","aptid"+aptid);
                            DatabaseReference dr1=FirebaseDatabase.getInstance().getReference().child("appointment").child(aptid);
                            dr1.addValueEventListener(new ValueEventListener() {
                                @Override
                                public void onDataChange(DataSnapshot dataSnapshot) {
                                    dataSnapshot.getChildren();
                                    if(!(dataSnapshot.getKey().equals("temp"))){
                                        Log.d("txyz","aptid"+dataSnapshot.getKey().toString());
                                    String patname,patmobile,patailm,date,slot,timestamp,patid;
                                    patname=dataSnapshot.child("patname").getValue().toString();
                                    patmobile=dataSnapshot.child("patmob").getValue().toString();
                                    patailm=dataSnapshot.child("ailment").getValue().toString();
                                    date=dataSnapshot.child("apptdate").getValue().toString();
                                    slot=dataSnapshot.child("apptslot").getValue().toString();
                                    timestamp=dataSnapshot.child("timestamp").getValue().toString();
                                    patid=dataSnapshot.child("patid").getValue().toString();
                                    models.add(new Model1(patname,patmobile,patailm,date,slot,timestamp,patid));}
                                }

                                @Override
                                public void onCancelled(DatabaseError databaseError) {

                                }
                            });

                        }
                        if (models.size()==0){
                            Toast.makeText(doc_schedule.this,"No Records Found",Toast.LENGTH_SHORT).show();
                            recyclerView.setAdapter(null);
                        }
                        else
                        {
                            ScheduleAdapter scheduleAdapter=new ScheduleAdapter(doc_schedule.this,models);
                            recyclerView.setAdapter(scheduleAdapter);
                        }
                    }
                }
            }*/

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        /*DatabaseReference dr=FirebaseDatabase.getInstance().getReference();
        dr.child("apptlist").child(uid);
        dr.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for(DataSnapshot ds:dataSnapshot.getChildren()){
                    if(tdate.equals(ds.getKey())){
                        for(DataSnapshot dr2:ds.getChildren()){
                            String aptid=(String) ds.getValue();
                            DatabaseReference dr1=FirebaseDatabase.getInstance().getReference().child("appointment").child(aptid);
                            dr1.addValueEventListener(new ValueEventListener() {
                                @Override

                                //TODO Add name and mobile number to the database under appointment
                                public void onDataChange(DataSnapshot dataSnapshot) {
                                    String docid,patid,alterdate,timestamp,timeslot,aptdate;
                                    dataSnapshot.getChildren();
                                    dataSnapshot.child("");
                                }

                                @Override
                                public void onCancelled(DatabaseError databaseError) {

                                }
                            });
                        }
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });*/

    }
}