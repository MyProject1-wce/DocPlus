package com.example.dell.docplus;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class Make_Appointment extends AppCompatActivity {
    String uid;
    EditText datetext;
    Calendar c;
    TextView name,degree,special,exp,cname,cadd;
    //Calendar calendar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_make__appointment);
        //Initialization
        name=(TextView)findViewById(R.id.make_apt_name);
        degree=(TextView)findViewById(R.id.make_apt_degree);
        special=(TextView)findViewById(R.id.make_apt_Speciality);
        exp=(TextView)findViewById(R.id.make_apt_expr);
        cname=(TextView)findViewById(R.id.make_apt_clinic_name);
        cadd=(TextView)findViewById(R.id.make_apt_clinic_addr);
        uid= this.getIntent().getExtras().getString("uid");
        //TextView t=(TextView)findViewById(R.id.display);
        //t.setText(uid);
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
                        String myFormat="dd/MM/yy";
                        SimpleDateFormat sdf=new SimpleDateFormat(myFormat, Locale.US);
                        datetext.setText(sdf.format(c.getTime()));
                        //datetext.setText(c.get(Calendar.MONTH)+" " +c.get(Calendar.YEAR)+" "+c.get(Calendar.DAY_OF_MONTH));
                    }

                },mYear, mMonth, mDay);

                Calendar maxDate = Calendar.getInstance();
                maxDate.set(Calendar.DAY_OF_MONTH, mcurrentDate.get(Calendar.DAY_OF_MONTH) + 3);
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
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

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
