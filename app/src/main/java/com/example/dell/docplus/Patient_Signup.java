package com.example.dell.docplus;



import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.NumberPicker;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class Patient_Signup extends AppCompatActivity {

    private FirebaseAuth mAuth;
    //FirebaseAuth.AuthStateListener authStateListener;
    private EditText name,email,mobile,pass1,pass2,add,city,history,number;

    private String tname,temail,tmobile,tpass1,tpass2 ,tadd,tcity,thistory,tage,tgender;
    private RadioButton rm,rf;
    //NumberPicker np;
    private DatabaseReference fd;
    private ProgressDialog pd;
    private Dialog d1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient__signup);
        name=(EditText)findViewById(R.id.signup_pat_name);
        email=(EditText)findViewById(R.id.signup_pat_email);
        mobile=(EditText)findViewById(R.id.signup_pat_mob);
        number=(EditText)findViewById(R.id.signup_pat_agetext);
        pass1=(EditText)findViewById(R.id.signup_pat_pass1);
        pass2=(EditText)findViewById(R.id.signup_pat_pass2);
        add=(EditText)findViewById(R.id.signup_pat_add);
        city=(EditText)findViewById(R.id.signup_pat_city);
        history=(EditText)findViewById(R.id.signup_pat_history);
        rm=(RadioButton)findViewById(R.id.signup_pat_rbm);
        rf=(RadioButton)findViewById(R.id.signup_pat_rbf);
        ((RadioGroup)findViewById(R.id.signup_pat_r)).check(R.id.signup_pat_rbm);
        mAuth=FirebaseAuth.getInstance();

        pd=new ProgressDialog(Patient_Signup.this);
        pd.setMessage("Creating a new User for U");
        pd.setTitle("Create User");
    }

    public void onBackPressed() {
        //your method call
        super.onBackPressed();
        Intent in=new Intent(Patient_Signup.this,LoginActivity.class);
        in.putExtra("flag","2");
        startActivity(in);
        finish();
    }

    public void CreateUser(View view) {
        tname=name.getText().toString();
        temail=email.getText().toString();
        tmobile=mobile.getText().toString();
        tpass1=pass1.getText().toString();
        tpass2=pass2.getText().toString();
        tadd=add.getText().toString();
        tcity=city.getText().toString();
        thistory=history.getText().toString();
        if(rm.isChecked())
            tgender="Male";
        else
            tgender="Female";
        tage="18" +
                "";
        tage=number.getText().toString();
        if(tname.equals(""))
        {
            name.setError("Empty Input");
            return;
        }

        if(tmobile.length()!=10)
        {
            mobile.setError("Enter 10 Digits");
            return;
        }
        if(temail.equals("") || !temail.contains("@"))
        {
            email.setError("Invalid Email ");
            return;
        }
        if(tpass1.length()<6 )
        {
            pass1.setError("Invalid input");
            return;
        }
        if(tpass2.length()<6 ||!tpass1.equals(tpass2))
        {
            pass2.setError("Not Matching Password");
            return;
        }
        if(tadd.equals(""))
        {
            add.setError("Invalid input ");
            return;
        }
        if(tcity.equals(""))
        {
            city.setError("Invalid input ");
            return;
        }
        if(thistory.equals(""))
        {
            history.setError("Invalid input ");
            return;
        }
        pd.show();
        mAuth.createUserWithEmailAndPassword(temail, tpass1)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        Log.d("TAG", "createUserWithEmail:onComplete:" + task.isSuccessful());

                        // If sign in fails, display a message to the user. If sign in succeeds
                        // the auth state listener will be notified and logic to handle the
                        // signed in user can be handled in the listener.
                        if (!task.isSuccessful()) {

                            pd.dismiss();
                            Toast.makeText(Patient_Signup.this, "Cannot Create now Try Again Later",
                                    Toast.LENGTH_LONG).show();
                        }
                        else
                        {
                            String uid=mAuth.getCurrentUser().getUid();
                            fd=FirebaseDatabase.getInstance().getReference().child("patients").child(uid);
                            Log.d("tabc",uid);
                            HashMap <String,String> hm=new HashMap<>();
                            hm.put("name",tname);
                            hm.put("mobile",tmobile);
                            hm.put("email",mAuth.getCurrentUser().getEmail());
                            hm.put("address",tadd);
                            hm.put("city",tcity);
                            hm.put("age",tage);
                            hm.put("gender",tgender);
                            hm.put("history",thistory);
                            fd.setValue(hm);
                            Log.d("tabc",fd.getKey());
                            pd.dismiss();
                            Toast.makeText(Patient_Signup.this, "User Created",
                                    Toast.LENGTH_LONG).show();
                            Intent intent=new Intent(Patient_Signup.this,LoginActivity.class);
                            intent.putExtra("flag","2");
                            startActivity(intent);
                            finish();
                           // mAuth.signOut();
                        }

                        // ...
                    }
                });

    }

    public void number(View view) {
        final Dialog d = new Dialog(Patient_Signup.this);
        d.setTitle("NumberPicker");

        d.setContentView(R.layout.dialog);
        Button b1 = (Button) d.findViewById(R.id.dialog_set);

        final NumberPicker np = (NumberPicker) d.findViewById(R.id.numberPicker1);
        np.setMaxValue(100);
        np.setMinValue(0);
        np.setWrapSelectorWheel(false);

        b1.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v) {
                number.setText("Age "+String.valueOf(np.getValue()));
                d.dismiss();
            }
        });

        d.show();
    }
    public void CreateDialog(View view) {
        final CharSequence[] items={"Ahmednagar","Akola","Amravati","Aurangabad","Baramati","Barshi","Beed","Bhusawal","Buldana","Chandrapur","Dhule","Gondiya","Ichalkaranji","Jalgaon","Jalna","Kolhapur","Latur","Manmad","Mumbai","Nagpur","Nashik","Osmanabad","Panvel","Parbhani","Pune","Sangli-Miraj-Kupwad","Solapur","Vasai","Virar","Wardha","Washim","Yavatmal" };
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Select city");
        builder.setSingleChoiceItems(items, -1, new DialogInterface.OnClickListener() {

            public void onClick(DialogInterface dialog, int item) {
                city.setText(items[item]);
                d1.dismiss();

            }
        });
        d1 = builder.create();
        d1.show();
    }
}
