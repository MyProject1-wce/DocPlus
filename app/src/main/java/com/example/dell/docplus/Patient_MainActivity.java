/*package com.example.dell.docplus;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;

import java.util.ArrayList;

import adapter.Model2;

public class Patient_MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private String uid;
    private RecyclerView recyclerView;
    private LinearLayoutManager linearLayoutManager;
    private ArrayList<Model2> models;
    private String myuid;
    private TextView nav_name;
    private TextView nav_email;
    private DatabaseReference dar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient__main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        /*FloatingActionButton fab = (FloatingActionButton) findViewById(fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });*

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        View hView =  navigationView.getHeaderView(0);

        nav_name=(TextView)hView.findViewById(R.id.nav_pat_name);
        nav_email=(TextView)hView.findViewById(R.id.nav_pat_email);
        uid=FirebaseAuth.getInstance().getCurrentUser().getUid();
        nav_email.setText(FirebaseAuth.getInstance().getCurrentUser().getEmail());
        //uid="bfHuvkkWbXSICP11lOYmtATI3yU2";
       /* DatabaseReference dr= FirebaseDatabase.getInstance().getReference().child("patients").child(uid);
        Log.d("txyz","2"+FirebaseAuth.getInstance().getCurrentUser().getUid());
        dr.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String a=String.valueOf(dataSnapshot.child("name").getValue());
                nav_name.setText(a);
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {}
        });

        linearLayoutManager =new LinearLayoutManager(this);
        recyclerView=(RecyclerView)findViewById(R.id.pat_main_recyclerview);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setHasFixedSize(true);
        models.add(new Model2("a","b","c","d","e","f"));
        //PatientAdapter patientAdapter=new PatientAdapter(Patient_MainActivity.this,models);
        //recyclerView.setAdapter(patientAdapter);
        //Fetching data into recyclerview
        //dar=FirebaseDatabase.getInstance().getReference().child("patlist").child(myuid);
        /*dar.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for(DataSnapshot ds:dataSnapshot.getChildren()){
                    String aptid=ds.getKey();
                    Log.d("txyzb",aptid);
                    DatabaseReference dr=FirebaseDatabase.getInstance().getReference().child("appointment").child(uid);
                    dr.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            dataSnapshot.getChildren();
                            String name,mobile,speciality,timestamp,timeslot,date;
                            name=dataSnapshot.child("docname").getValue().toString();
                            mobile=dataSnapshot.child("docmob").getValue().toString();
                            speciality=dataSnapshot.child("special").getValue().toString();
                            timestamp=dataSnapshot.child("timestamp").getValue().toString();
                            timeslot=dataSnapshot.child("apptslot").getValue().toString();
                            date=dataSnapshot.child("apptdate").getValue().toString();
                            Log.d("txyzb",date);
                            models.add(new Model2(name,mobile,speciality,timestamp,timeslot,date));
                            //PatientAdapter patientAdapter=new PatientAdapter(Patient_MainActivity.this,models);
                            //recyclerView.setAdapter(patientAdapter);
                        }
                        @Override
                        public void onCancelled(DatabaseError databaseError) {}
                    });
                    if (models.size()==0){
                        //Toast.makeText(doc_schedule.this,"No Records Found",Toast.LENGTH_SHORT).show();
                        recyclerView.setAdapter(null);
                    }
                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        dar.keepSynced(true);*
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


    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.patient__main, menu);
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

        if (id == R.id.nav_makeappt) {
            // Handle the camera action
            Intent intent =new Intent(Patient_MainActivity.this,Doc_Search.class);
            startActivity(intent);
            finish();
        } else if (id == R.id.nav_pat_profile) {

        } else if (id == R.id.nav_pat_logout) {
            FirebaseAuth.getInstance().signOut();
            Intent intent =new Intent(Patient_MainActivity.this,MainActivity.class);
            intent.putExtra("flag","2");
            startActivity(intent);
            finish();
        } else if (id == R.id.nav_pat_password) {
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
*/


package com.example.dell.docplus;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
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

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import de.hdodenhof.circleimageview.CircleImageView;

public class Patient_MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    String uid;
    TextView nav_name;
    TextView nav_email;
    CircleImageView civ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient__main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent =new Intent(Patient_MainActivity.this,Doc_Search.class);
                startActivity(intent);
                finish();
              /*  Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();*/
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view_pat);
        navigationView.setNavigationItemSelectedListener(this);
        View hView =  navigationView.getHeaderView(0);
        civ= (CircleImageView)hView.findViewById(R.id.nav_pat_image);
        nav_name=(TextView)hView.findViewById(R.id.nav_pat_name);
        nav_email=(TextView)hView.findViewById(R.id.nav_pat_email);
        uid=FirebaseAuth.getInstance().getCurrentUser().getUid();
        nav_email.setText(FirebaseAuth.getInstance().getCurrentUser().getEmail());
        //uid="bfHuvkkWbXSICP11lOYmtATI3yU2";
        DatabaseReference dr= FirebaseDatabase.getInstance().getReference().child("patients").child(uid);
        Log.d("txyz","2"+FirebaseAuth.getInstance().getCurrentUser().getUid());
        dr.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                String a=String.valueOf(dataSnapshot.child("name").getValue());
                String url=dataSnapshot.child("propic").getValue().toString();
                Glide.with(Patient_MainActivity.this).load(url).override(100,100).into(civ);
                nav_name.setText(a);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
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
        getMenuInflater().inflate(R.menu.patient__main, menu);
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

        if (id == R.id.nav_makeappt) {
            // Handle the camera action
            Intent intent =new Intent(Patient_MainActivity.this,Doc_Search.class);
            startActivity(intent);
            finish();
        } else if (id == R.id.nav_pat_profile) {
            Intent intent=new Intent(Patient_MainActivity.this,Patient_profile.class);
            startActivity(intent);
            finish();
        } else if (id == R.id.nav_pat_myappt) {
            Intent intent=new Intent(Patient_MainActivity.this,MyAppointment.class);
            startActivity(intent);
            finish();
        } else if (id == R.id.nav_pat_logout) {
            FirebaseAuth.getInstance().signOut();
            Intent intent =new Intent(Patient_MainActivity.this,MainActivity.class);
            intent.putExtra("flag","2");
            startActivity(intent);
            finish();
        } else if (id == R.id.nav_pat_password) {
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}