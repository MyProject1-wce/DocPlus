package com.example.dell.docplus;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
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

import java.io.File;
import java.util.HashMap;

import static com.example.dell.docplus.R.layout.dialog;

public class Doctor_Signup extends AppCompatActivity {

    private FirebaseAuth mAuth;
    //FirebaseAuth.AuthStateListener authStateListener;
    private EditText name,email,mobile,pass1,pass2,cname,cadd,exp,special,degree,fees,number ,city;
    private String tname,temail,tmobile,tpass1,tpass2 ,tcname,tcadd,texp,tspecial,tdegree,tfees,tage,tcity,tgender;
    private DatabaseReference fd;
    private ProgressDialog pd;
    private NumberPicker numberPicker;
    Dialog d1,dialog1;
    private RadioButton rf,rm;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor__signup);
        name=(EditText)findViewById(R.id.signup_doc_name);
        email=(EditText)findViewById(R.id.signup_doc_email);
        mobile=(EditText)findViewById(R.id.signup_doc_mob);
        pass1=(EditText)findViewById(R.id.signup_doc_pass1);
        pass2=(EditText)findViewById(R.id.signup_doc_pass2);
        cname=(EditText)findViewById(R.id.signup_doc_cname);
        city=(EditText)findViewById(R.id.signup_doc_city);
        cadd=(EditText)findViewById(R.id.signup_doc_cadd);
        number=(EditText)findViewById(R.id.signup_doc_agetext);
        exp=(EditText)findViewById(R.id.signup_doc_experience);
        special=(EditText)findViewById(R.id.signup_doc_speciality);
        degree=(EditText)findViewById(R.id.signup_doc_degree);
        fees=(EditText)findViewById(R.id.signup_doc_fees);
        rf=(RadioButton)findViewById(R.id.signup_doc_rbf);
        rm=(RadioButton)findViewById(R.id.signup_doc_rbm);
        ((RadioGroup)findViewById(R.id.signup_doc_rg)).check(R.id.signup_doc_rbm);
        mAuth=FirebaseAuth.getInstance();

        pd=new ProgressDialog(Doctor_Signup.this);
        pd.setMessage("Creating a new User for U");
        pd.setTitle("Create User");
    }

    public void onBackPressed() {
        //your method call
        super.onBackPressed();
        Intent in=new Intent(Doctor_Signup.this,LoginActivity.class);
        in.putExtra("flag","1");
        startActivity(in);
        finish();
    }
    public void CreateUser(View view) {
        tname=name.getText().toString();
        temail=email.getText().toString();
        tmobile=mobile.getText().toString();
        tpass1=pass1.getText().toString();
        tpass2=pass2.getText().toString();
        tcname=cname.getText().toString();
        tcadd=cadd.getText().toString();
        texp=exp.getText().toString();
        tspecial=special.getText().toString();
        tdegree=degree.getText().toString();
        tfees=fees.getText().toString();
      //  tcity=city.getText().toString();
        if(rm.isChecked())
            tgender="Male";
        else
            tgender="Female";
        tage="18";
       // tage=number.getText().toString();
        if(tname.equals(""))
        {
            name.setError("Empty Field");
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
        if(tspecial.equals(""))
        {
            special.setError("Empty Field");
            return;
        }
        if(tdegree.equals(""))
        {
            degree.setError("Empty Field");
            return;
        }
        if(texp.equals(""))
        {
            exp.setError("Empty Field");
            return;
        }
        if(tcname.equals(""))
        {
            cname.setError("Empty Field");
            return;
        }
        if(tcadd.equals(""))
        {
            cadd.setError("Empty Field");
            return;
        }
        if(tfees.equals(""))
        {
            fees.setError("Empty Field");
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
                            Toast.makeText(Doctor_Signup.this, "Cannot Create now Try Again Later",
                                    Toast.LENGTH_LONG).show();
                        }
                        else
                        {
                            String uid=mAuth.getCurrentUser().getUid();
                            fd= FirebaseDatabase.getInstance().getReference().child("doctors").child(uid);
                            Log.d("tabc",uid);
                            HashMap<String,String> hm=new HashMap<>();
                            hm.put("name",tname);
                            hm.put("mobile",tmobile);
                            hm.put("email",mAuth.getCurrentUser().getEmail());
                            hm.put("cname",tcname);
                            hm.put("cadd",tcadd);
                            hm.put("experience",texp);
                            hm.put("speciality",tspecial);
                            hm.put("age",tage);
                            hm.put("degree",tdegree);
                            hm.put("gender",tgender);
                            hm.put("fees",tfees);
                            hm.put("city",tcity);
                            fd.setValue(hm);
                            Log.d("tabc",fd.getKey());
                            pd.dismiss();
                            Toast.makeText(Doctor_Signup.this, "User Created",
                                    Toast.LENGTH_LONG).show();
                            Intent intent=new Intent(Doctor_Signup.this,LoginActivity.class);
                            intent.putExtra("flag","1");
                            startActivity(intent);
                            finish();
                            // mAuth.signOut();
                        }

                        // ...
                    }
                });

    }


    public void camera(View view) {
        File myDir=new File("/sdcard/saved_images");
        myDir.mkdirs();

        File imageFile = new File(myDir, "image.jpg");

        //imageFile=new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES),uid+".jpg");
        Uri tempUri=Uri.fromFile(imageFile);
        Intent intent=new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        intent.putExtra(MediaStore.EXTRA_OUTPUT,tempUri);
        startActivityForResult(intent,0);
    }
    public void number1(View view) {
        final Dialog d = new Dialog(Doctor_Signup.this);
        d.setTitle("NumberPicker");

        d.setContentView(dialog);
        Button b1 = (Button) d.findViewById(R.id.dialog_set);

        final NumberPicker np = (NumberPicker) d.findViewById(R.id.numberPicker1);
        np.setMaxValue(75);
        np.setMinValue(18);
        np.setWrapSelectorWheel(false);

        b1.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v) {
                number.setText("Age "+String.valueOf(np.getValue()));
                tage=""+np.getValue();
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
                tcity=""+items[item];
                d1.dismiss();

            }
        });
        d1 = builder.create();
        d1.show();
    }

    public void pickSpecial1(View view) {
        dialog1=new Dialog(Doctor_Signup.this);
        final CharSequence[] items={"Anesthesiologist","Cardiologist","Dermatologist","Nephrologist","Neurologist","Gynecologist","Ophthalmologist","Otolaryngologist","Pathologist","Pediatrician","Plastic Surgeon","Psychiatrist","Rheumatologist","Urologist" };
        //ArrayList<CharSequence>list=new ArrayList<>();
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Select Speciality");


        builder.setSingleChoiceItems(items, -1, new DialogInterface.OnClickListener() {

            public void onClick(DialogInterface dialog, int item) {
                special.setText(items[item]);
                tspecial=""+items[item];
                dialog1.dismiss();

            }
        });
        dialog1 = builder.create();
        dialog1.show();
    }
}
