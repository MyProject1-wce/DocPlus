package com.example.dell.docplus;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import static com.example.dell.docplus.R.layout.activity_login;

public class LoginActivity extends AppCompatActivity {

    private EditText email,password;
    private Button btn;
    private FirebaseAuth mAuth;
    private int str;
    private FirebaseAuth.AuthStateListener mAuthListener;
    ProgressDialog pd;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(activity_login);
        ActionBar actionBar=getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeButtonEnabled(true);
        str=Integer.parseInt(this.getIntent().getExtras().getString("flag"));
        Toast.makeText(LoginActivity.this, ""+str,
                Toast.LENGTH_SHORT).show();
        mAuth = FirebaseAuth.getInstance();
        email=(EditText)findViewById(R.id.login_email);
        password=(EditText)findViewById(R.id.login_password);
        btn=(Button)findViewById(R.id.login_button);
        pd=new ProgressDialog(LoginActivity.this,ProgressDialog.STYLE_SPINNER);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Login_user();
                pd.setTitle("Login");
                pd.setMessage("Logining in!!");
                pd.setCanceledOnTouchOutside(false);
                pd.show();
                //pd.setCanceledOnTouchOutside(true);

            }
        });
    }
    public void onBackPressed() {
        //your method call
        super.onBackPressed();
        Intent in=new Intent(LoginActivity.this,MainActivity.class);
        startActivity(in);
        finish();
    }
    public boolean onOptionsItemSelected(MenuItem item) {
                onBackPressed();
                //this.finish();
                return true;

    }
    void Login_user()
    {
        String temail,tpass;
        temail=email.getText().toString();
        tpass=password.getText().toString();
        if(temail.equals("") || tpass.equals("") || !temail.contains("@") || tpass.length()<6) {
            Toast.makeText(LoginActivity.this,"Check Login Credentials",Toast.LENGTH_LONG);
            email.setError("Check input");
            password.setError("Check input");
            pd.dismiss();
            return;
        }

        mAuth.signInWithEmailAndPassword(temail, tpass)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        Log.d("TAG", "signInWithEmail:onComplete:" + task.isSuccessful());

                        // If sign in fails, display a message to the user. If sign in succeeds
                        // the auth state listener will be notified and logic to handle the
                        // signed in user can be handled in the listener.
                        if (!task.isSuccessful()) {
                            pd.dismiss();
                            Log.w("TAG", "signInWithEmail:failed", task.getException());
                            Toast.makeText(LoginActivity.this, "Auth Failed",
                                    Toast.LENGTH_SHORT).show();

                        }
                        else {
                            pd.dismiss();
                            final String uid=mAuth.getCurrentUser().getUid();
                            Log.d("txyz",uid);
                           /* String filename = "flag";
                            FileOutputStream outputStream;

                            try {
                                outputStream = openFileOutput(filename, Context.MODE_PRIVATE);
                                outputStream.write(str);
                                outputStream.close();
                                Log.d("fileabc","Success");
                            } catch (Exception e) {
                                Log.d("fileabc","fail");
                                e.printStackTrace();
                            }*/

                            if(str==1)
                            {
                                DatabaseReference doc= FirebaseDatabase.getInstance().getReference().child("doctors");
                                doc.addValueEventListener(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(DataSnapshot dataSnapshot) {
                                        boolean flag=false;
                                        for(DataSnapshot ds:dataSnapshot.getChildren()){
                                            String curr=ds.getKey();
                                            Log.d("txyz","curr "+curr);
                                            if(curr.equals(uid)){
                                                flag=true;
                                            }
                                        }
                                        if(flag==true)
                                        {
                                            Intent intent=new Intent(LoginActivity.this,Doc_MainActivity.class);
                                            //intent.putExtra("flag","1");
                                            startActivity(intent);
                                            finish();
                                        }
                                        if(flag==false){
                                        mAuth.signOut();
                                        Toast.makeText(LoginActivity.this,"U r not registered as doctor",Toast.LENGTH_LONG).show();
                                        Intent intent=new Intent(LoginActivity.this,MainActivity.class);
                                        startActivity(intent);
                                        finish();
                                        }
                                    }

                                    @Override
                                    public void onCancelled(DatabaseError databaseError) {

                                    }
                                });

                            }
                            else
                            {
                                DatabaseReference doc= FirebaseDatabase.getInstance().getReference().child("patients");
                                doc.addValueEventListener(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(DataSnapshot dataSnapshot) {
                                        boolean flag=false;
                                        for(DataSnapshot ds:dataSnapshot.getChildren()){
                                            String curr=ds.getKey();
                                            Log.d("txyz","curr "+curr);
                                            if(curr.equals(uid)){
                                                flag=true;
                                            }
                                        }
                                        if(flag==true)
                                        {
                                            Intent intent=new Intent(LoginActivity.this,Patient_MainActivity.class);
                                            //intent.putExtra("flag","1");
                                            startActivity(intent);
                                            finish();
                                        }
                                        if(flag==false){
                                            mAuth.signOut();
                                            Toast.makeText(LoginActivity.this,"U r not registered as Patient",Toast.LENGTH_LONG).show();
                                            Intent intent=new Intent(LoginActivity.this,MainActivity.class);
                                            startActivity(intent);
                                            finish();
                                        }
                                    }

                                    @Override
                                    public void onCancelled(DatabaseError databaseError) {

                                    }
                                });
                            }
                            Toast.makeText(LoginActivity.this, "Auth Success",
                                    Toast.LENGTH_SHORT).show();

                        }
                        // ...
                    }

                });

    }

    public void newuser(View view) {
        if(str==2) {
            Intent intent = new Intent(LoginActivity.this, Patient_Signup.class);
            //intent.putExtra("flag",2);
            startActivity(intent);
            finish();
        }
        else
        {
            Intent intent = new Intent(LoginActivity.this, Doctor_Signup.class);
            //intent.putExtra("flag",1);
            startActivity(intent);
            finish();
        }

    }
}
