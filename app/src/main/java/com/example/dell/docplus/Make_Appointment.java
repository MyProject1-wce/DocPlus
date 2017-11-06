package com.example.dell.docplus;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;

import de.hdodenhof.circleimageview.CircleImageView;

public class Make_Appointment extends AppCompatActivity {
    String uid,url,docname,docmobile,patname,patmobile,mylat,mylong,doclat,doclong;
    EditText datetext,datetext1;
    Calendar c,c1;
    TextView name,degree,special,exp,cname,cadd;
    EditText slotTime,ailment;
    CircleImageView ciw;
    CharSequence [] slots1=null;
    Dialog dialog1;
    Date date;
    //Calendar calendar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_make__appointment);
        ActionBar actionBar=getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeButtonEnabled(true);
        //Initialization
        name=(TextView)findViewById(R.id.make_apt_name);
        degree=(TextView)findViewById(R.id.make_apt_degree);
        special=(TextView)findViewById(R.id.make_apt_Speciality);
        ailment= (EditText) findViewById(R.id.make_apt_ailment);
        slotTime= (EditText) findViewById(R.id.make_apt_slot);
        exp=(TextView)findViewById(R.id.make_apt_expr);
        cname=(TextView)findViewById(R.id.make_apt_clinic_name);
        ciw= (CircleImageView) findViewById(R.id.make_apt_profile_pic);
        cadd=(TextView)findViewById(R.id.make_apt_clinic_addr);
        uid= this.getIntent().getExtras().getString("uid");
        //TextView t=(TextView)findViewById(R.id.display);
        //t.setText(uid);

        //Date Picker 1
        Calendar calendar=Calendar.getInstance();
        datetext=(EditText)findViewById(R.id.make_apt_date);
        datetext.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                //To show current date in the datepicker
                final Calendar mcurrentDate=Calendar.getInstance();
                int mYear=mcurrentDate.get(Calendar.YEAR);
                int mMonth=mcurrentDate.get(Calendar.MONTH);
                int mDay=mcurrentDate.get(Calendar.DAY_OF_MONTH);
                c=Calendar.getInstance();

                DatePickerDialog mDatePicker=new DatePickerDialog(Make_Appointment.this, new DatePickerDialog.OnDateSetListener() {
                    public void onDateSet(DatePicker datepicker, int year, int month, int day) {
                       datepicker.setMinDate(System.currentTimeMillis());
                        datepicker.setMaxDate(mcurrentDate.getTimeInMillis());
                        c.set(Calendar.YEAR,year);
                        c.set(Calendar.MONTH,month);
                        c.set(Calendar.DAY_OF_MONTH,day);
                        String myFormat="dd-MM-yy";
                        SimpleDateFormat sdf=new SimpleDateFormat(myFormat, Locale.US);
                        datetext.setText(sdf.format(c.getTime()));
                        date=c.getTime();
                        DatabaseReference dr=FirebaseDatabase.getInstance().getReference().child("apptlist").child(uid).child(datetext.getText().toString());
                        dr.child("temp").setValue("c");
                        //datetext.setText(c.get(Calendar.MONTH)+" " +c.get(Calendar.YEAR)+" "+c.get(Calendar.DAY_OF_MONTH));
                    }

                },mYear, mMonth, mDay);

                Calendar maxDate = Calendar.getInstance();
                maxDate.set(Calendar.DAY_OF_MONTH, mcurrentDate.get(Calendar.DAY_OF_MONTH) + 6);
                maxDate.set(Calendar.MONTH, mcurrentDate.get(Calendar.MONTH));
                maxDate.set(Calendar.YEAR, mcurrentDate.get(Calendar.YEAR));
                //maxDate.set(Calendar.HOUR, 23);
                //maxDate.set(Calendar.MINUTE, 59);

                mDatePicker.getDatePicker().setMinDate(mcurrentDate.getTimeInMillis());
                mDatePicker.getDatePicker().setMaxDate(maxDate.getTimeInMillis());
                mDatePicker.setTitle("Select date");
                mDatePicker.show();
                //datetext.setText(c.get(Calendar.MONTH)+" " +c.get(Calendar.YEAR)+" "+c.get(Calendar.DAY_OF_MONTH));
            }
        });

        //Date Picker 2
        datetext1= (EditText) findViewById(R.id.make_apt_date1);
        datetext1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Calendar mcurrentDate=Calendar.getInstance();
                int mYear=mcurrentDate.get(Calendar.YEAR);
                int mMonth=mcurrentDate.get(Calendar.MONTH);
                int mDay=mcurrentDate.get(Calendar.DAY_OF_MONTH);
                c1=Calendar.getInstance();
                DatePickerDialog mDatePicker=new DatePickerDialog(Make_Appointment.this, new DatePickerDialog.OnDateSetListener() {
                    public void onDateSet(DatePicker datepicker, int year, int month, int day) {
                        datepicker.setMinDate(date.getTime());
                        datepicker.setMaxDate(mcurrentDate.getTimeInMillis());
                        c1.set(Calendar.YEAR,year);
                        c1.set(Calendar.MONTH,month);
                        c1.set(Calendar.DAY_OF_MONTH,day);
                        String myFormat="dd-MM-yy";
                        SimpleDateFormat sdf=new SimpleDateFormat(myFormat, Locale.US);
                        datetext1.setText(sdf.format(c1.getTime()));

                       // DatabaseReference dr=FirebaseDatabase.getInstance().getReference().child("apptlist").child(uid).child(datetext.getText().toString());
                        //dr.child("temp").setValue("c");
                        //datetext.setText(c.get(Calendar.MONTH)+" " +c.get(Calendar.YEAR)+" "+c.get(Calendar.DAY_OF_MONTH));
                    }

                },mYear, mMonth, mDay);
                Calendar maxDate = Calendar.getInstance();
                maxDate.set(Calendar.DAY_OF_MONTH, mcurrentDate.get(Calendar.DAY_OF_MONTH) + 6);
                maxDate.set(Calendar.MONTH, mcurrentDate.get(Calendar.MONTH));
                maxDate.set(Calendar.YEAR, mcurrentDate.get(Calendar.YEAR));
                mDatePicker.getDatePicker().setMinDate(date.getTime());
                mDatePicker.getDatePicker().setMaxDate(maxDate.getTimeInMillis());
                mDatePicker.setTitle("Select date");
                mDatePicker.show();
            }
        });
        //Retieve pat data
        final DatabaseReference dat=FirebaseDatabase.getInstance().getReference().child("patients").child(FirebaseAuth.getInstance().getCurrentUser().getUid());
        dat.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                    dataSnapshot.getChildren();
                    patname=dataSnapshot.child("name").getValue().toString();
                    patmobile=dataSnapshot.child("mobile").getValue().toString();
                    mylat=dataSnapshot.child("lat").getValue().toString();
                    mylong=dataSnapshot.child("long").getValue().toString();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        //Displaying info of doctor
        DatabaseReference dr= FirebaseDatabase.getInstance().getReference().child("doctors").child(uid);
        dr.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                dataSnapshot.getChildren();
                name.setText(dataSnapshot.child("name").getValue().toString());
                degree.setText(dataSnapshot.child("degree").getValue().toString());
                special.setText(dataSnapshot.child("speciality").getValue().toString());
                exp.setText(dataSnapshot.child("experience").getValue().toString());
                cname.setText(dataSnapshot.child("cname").getValue().toString());
                cadd.setText(dataSnapshot.child("cadd").getValue().toString());
                url=dataSnapshot.child("propic").getValue().toString();
                doclat=dataSnapshot.child("lat").getValue().toString();
                doclong=dataSnapshot.child("long").getValue().toString();
                Glide.with(Make_Appointment.this).load(url).override(120,120).into( ciw);
                docname=name.getText().toString();
                docmobile=dataSnapshot.child("mobile").getValue().toString();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }
    ArrayList<CharSequence> list;
    String slotSelected;
    public void chooseSlot(View view) {
        /*final CharSequence [] slots={"10:00am-10:30am","10:30am-11:00am","11:00am-11:30am","11:30am-12:00pm","12:00pm-12:30pm","12:30pm-01:00pm","01:00pm-01:30pm","01:30pm-02:00pm","02:00pm-02:30pm","02:30pm-03:00pm","03:00pm-03:30pm","03:30pm-04:00pm","04:00pm-04:30pm","04:30pm-05:00pm","05:00pm-05:30pm","05:30pm-06:00pm"};
        ArrayList<CharSequence> list=new ArrayList<>();
        for(int i=0;i<slots.length;i++){
            list.add(slots[i]);
        }
        DatabaseReference dr=FirebaseDatabase.getInstance().getReference().child("apptlist").child(uid);
        dr.child(datetext.getText().toString());
        dr.child("11.00am-11.30am").setValue("ssada");
        /*dr=FirebaseDatabase.getInstance().getReference().child("apptlist").child(uid).child(datetext.getText().toString());
        dr.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for(DataSnapshot ds:dataSnapshot.getChildren()){
                    if(ds==null) {
                        Log.d("txyz", "No Records");
                        break;
                    }
                    else
                    {
                        Log.d("txyz",ds.getKey()+" "+ds.getValue());
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        AlertDialog.Builder builder=new AlertDialog. Builder(this);
        builder.setTitle("Select Slots");
        builder.setMessage("Available Slots are: ");
        builder.setSingleChoiceItems(slots, -1, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                slotTime.setText(slots[i]);
                dialog1.dismiss();
            }
        });

        dialog1=builder.create();
        dialog1.show();*/
        dialog1=new Dialog(Make_Appointment.this);
        final CharSequence [] slots={"10:00am-10:30am","10:30am-11:00am","11:00am-11:30am","11:30am-12:00pm","12:00pm-12:30pm","02:00pm-02:30pm","02:30pm-03:00pm","03:00pm-03:30pm","03:30pm-04:00pm","04:00pm-04:30pm","04:30pm-05:00pm","05:00pm-05:30pm","05:30pm-06:00pm"};
        list=new ArrayList<>();
        for(CharSequence c: slots){
            list.add(c);
        }
        //DatabaseReference dr=FirebaseDatabase.getInstance().getReference().child("apptlist").child(uid);
        //dr.child(datetext.getText().toString()).child("10:00am-10:30am").setValue("Npoonno");
        Log.d("txyz3",uid+datetext.getText().toString());
        DatabaseReference dar=FirebaseDatabase.getInstance().getReference().child("apptlist").child(uid).child(datetext.getText().toString());
        dar.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for(DataSnapshot ds:dataSnapshot.getChildren()){
                    //Log.d("txyz2",""+ds.getChildrenCount());
                    if(!("temp".equals(ds.getKey()))) {
                       int a=list.indexOf(ds.getKey());
                        if(!(a<0||a>=list.size()))
                            list.remove(a);
                        Log.d("txyz",ds.getKey()+" "+ds.getValue()+" "+list.indexOf(ds.getKey()));

                    }
                    else
                    {
                        Log.d("txyz", "No Records");
                    }
                }
                slots1=new CharSequence[list.size()];
                slots1=list.toArray(slots1);
                Log.d("txyz5",""+slots1.length);
                AlertDialog.Builder builder = new AlertDialog.Builder(Make_Appointment.this);
                builder.setTitle("Select Slots");
                builder.setSingleChoiceItems(slots1, -1, new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialog, int item) {
                        slotTime.setText(slots1[item]);
                        slotSelected= String.valueOf(slots1[item]);
                        Log.d("txyz1",""+list.size());
                        dialog1.dismiss();

                    }
                });
                dialog1 = builder.create();
                dialog1.show();
                /*slots1=new CharSequence[list.size()];
                slots1=list.toArray(slots1);*/
                for(CharSequence s:slots1)
                    Log.d("txyz4",""+s);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        /*Log.d("txyz5",""+slots1.length);
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Select Slots");
        builder.setSingleChoiceItems(slots1, -1, new DialogInterface.OnClickListener() {

            public void onClick(DialogInterface dialog, int item) {
                slotTime.setText(slots1[item]);
                Log.d("txyz1",""+list.size());
                dialog1.dismiss();

            }
        });
        dialog1 = builder.create();
        dialog1.show();*/
    }
    private String doc_id,pat_id,apptDate,apptSlot,timestamp,alterdate,tailment,spec;
    private DatabaseReference databaseReference;

    public void makeAppointment(View view) {
        //patname="name";
        //patmobile="mobile";
        doc_id=uid;
        pat_id= FirebaseAuth.getInstance().getCurrentUser().getUid();
        Log.d("txyz","pat"+patname);
        apptDate=datetext.getText().toString();
        spec=special.getText().toString();
        apptSlot=slotTime.getText().toString();
        alterdate=datetext1.getText().toString();
        DateFormat df = new SimpleDateFormat("EEE,dd.MMM.yyyy,HH:mm:ss");
        timestamp = df.format(Calendar.getInstance().getTime());
        tailment=ailment.getText().toString();
        databaseReference=FirebaseDatabase.getInstance().getReference().child("appointment").push();
        HashMap<String,String> hm=new HashMap<>();
        hm.put("docid",doc_id);
        hm.put("patid",pat_id);
        hm.put("apptdate",apptDate);
        hm.put("apptslot",apptSlot);
        hm.put("timestamp",timestamp);
        hm.put("alterdate",alterdate);
        hm.put("ailment",tailment);
        hm.put("special",spec);
        hm.put("status","false");
        hm.put("docname",docname);
        hm.put("docmob",docmobile);
        hm.put("patname",patname);
        hm.put("patmob",patmobile);
        hm.put("mylat",mylat);
        hm.put("mylong",mylong);
        hm.put("doclat",doclat);
        hm.put("doclong",doclong);
        databaseReference.setValue(hm).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Toast.makeText(Make_Appointment.this,"Appointment Added Successfully",Toast.LENGTH_LONG).show();
            }
        });
        String aptid=databaseReference.getKey();
        databaseReference=FirebaseDatabase.getInstance().getReference().child("apptlist").child(doc_id).child(apptDate).child(apptSlot);
        databaseReference.setValue(aptid);
        databaseReference=FirebaseDatabase.getInstance().getReference().child("patlist").child(pat_id).child(aptid);
        databaseReference.setValue(aptid);
        Toast.makeText(Make_Appointment.this,"New Appointment Created",Toast.LENGTH_LONG).show();
        /*try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }*/

        onBackPressed();
    }
    public void onBackPressed() {
        //your method call
        super.onBackPressed();
        Intent in=new Intent(Make_Appointment.this,Doc_Search.class);
        startActivity(in);
        finish();
    }
    public boolean onOptionsItemSelected(MenuItem item) {
        onBackPressed();
        //this.finish();
        return true;

    }
   /* public void pickDate(View view) {
        DatePickerDialog.OnDateSetListener obj=new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                calendar.set(Calendar.YEAR,i);
                calendar.set(Calendar.MONTH,i1);
                calendar.set(Calendar.DAY_OF_MONTH,i2);
                datePicker.setMinDate(System.currentTimeMillis());
                datePicker.setMaxDate(calendar.getTimeInMillis());
                updateLabel();
            }
        };

       DatePickerDialog d= new DatePickerDialog(Make_Appointment.this,obj,calendar.get(Calendar.YEAR),calendar.get(Calendar.MONTH),calendar.get(Calendar.DAY_OF_MONTH));
        d.show();
    }

    private void updateLabel() {
        String myFormat="dd/MM/yy";
        SimpleDateFormat sdf=new SimpleDateFormat(myFormat, Locale.US);
        datetext.setText(sdf.format(calendar.getTime()));
    }*/
}
