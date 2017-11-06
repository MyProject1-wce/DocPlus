package com.example.dell.docplus;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import de.hdodenhof.circleimageview.CircleImageView;


public class Doc_MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    DatabaseReference dr= FirebaseDatabase.getInstance().getReference();
    TextView nav_name,nav_email;
    CircleImageView iv;
    String uid,url;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doc__main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);
       // setContentView(R.layout.nav_header_doc__main);

       /* FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });*/

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view_doc);
        navigationView.setNavigationItemSelectedListener(this);
        View hView =  navigationView.getHeaderView(0);
        iv= (CircleImageView) hView.findViewById(R.id.nav_doc_image1);
        nav_name=(TextView)hView.findViewById(R.id.nav_doc_name);
        nav_email=(TextView)hView.findViewById(R.id.nav_doc_email);
        uid=FirebaseAuth.getInstance().getCurrentUser().getUid();
        nav_email.setText(FirebaseAuth.getInstance().getCurrentUser().getEmail());
        //uid="bfHuvkkWbXSICP11lOYmtATI3yU2";
        DatabaseReference dr=FirebaseDatabase.getInstance().getReference().child("doctors").child(uid);
        Log.d("txyz","2"+FirebaseAuth.getInstance().getCurrentUser().getUid());
        dr.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                String a=String.valueOf(dataSnapshot.child("name").getValue());
                url=dataSnapshot.child("propic").getValue().toString();
                Log.d("txyza",url);
               Glide.with(Doc_MainActivity.this).load(url).override(100,100).into(iv);
                nav_name.setText(a);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        /*StorageReference sr= FirebaseStorage.getInstance().getReference().child("images/b.jpg");
        File local;
        try {
             local=File.createTempFile("images","jpg");
            sr.getFile(local).addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {
                    taskSnapshot.toString();
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        }

        iv= (ImageView) findViewById(R.id.nav_doc_image);
        //Log.d("txyz",sr.getDownloadUrl().getResult().toString()) ;
       /*sr.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                Picasso.with(Doc_MainActivity.this).load(uri.toString()).into(iv);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(Doc_MainActivity.this,"Cannot Load",Toast.LENGTH_SHORT).show();
            }
        });*/
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.doc__main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_schedule) {
            Intent intent=new Intent(Doc_MainActivity.this,doc_schedule.class);
            startActivity(intent);
            finish();



        } else if (id == R.id.nav_profile) {
            Intent intent=new Intent(Doc_MainActivity.this,Doc_profile.class);
            startActivity(intent);
            finish();

        } else if (id == R.id.nav_password) {
            String email=FirebaseAuth.getInstance().getCurrentUser().getEmail();
            Log.d("txyz",email);
            FirebaseAuth.getInstance().sendPasswordResetEmail(email)
                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                Log.d("txyz", "Email sent.");
                                Toast.makeText(Doc_MainActivity.this,"Mail Sent",Toast.LENGTH_LONG).show();
                            }
                        }
                    });
        } else if (id == R.id.nav_logout) {
            FirebaseAuth.getInstance().signOut();
            Intent intent =new Intent(Doc_MainActivity.this,MainActivity.class);
            intent.putExtra("flag","1");
            startActivity(intent);
            finish() ;

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
