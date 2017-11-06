package com.example.dell.docplus;

import android.Manifest;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.location.Location;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.NumberPicker;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.vistrav.ask.Ask;

import java.io.IOException;
import java.util.HashMap;

import de.hdodenhof.circleimageview.CircleImageView;

import static android.R.attr.id;

public class Patient_Signup extends AppCompatActivity implements android.location.LocationListener {

    private FirebaseAuth mAuth;
    //FirebaseAuth.AuthStateListener authStateListener;
    private EditText name, email, mobile, pass1, pass2, add, city, history, number;
    private CircleImageView civ;
    private String tname, temail, tmobile, tpass1, tpass2, tadd, tcity, thistory, tage, tgender;
    private RadioButton rm, rf;
    //NumberPicker np;
    String uid;
    LocationManager lm;
    private DatabaseReference fd, dr;
    private ProgressDialog pd;
    private Dialog d1;
    int Image_Request_Code = 7;
    double latitude=-1,longitude=-1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient__signup);
        name = (EditText) findViewById(R.id.signup_pat_name);
        email = (EditText) findViewById(R.id.signup_pat_email);
        mobile = (EditText) findViewById(R.id.signup_pat_mob);
        number = (EditText) findViewById(R.id.signup_pat_agetext);
        pass1 = (EditText) findViewById(R.id.signup_pat_pass1);
        pass2 = (EditText) findViewById(R.id.signup_pat_pass2);
        add = (EditText) findViewById(R.id.signup_pat_add);
        city = (EditText) findViewById(R.id.signup_pat_city);
        history = (EditText) findViewById(R.id.signup_pat_history);
        rm = (RadioButton) findViewById(R.id.signup_pat_rbm);
        rf = (RadioButton) findViewById(R.id.signup_pat_rbf);
        civ = (CircleImageView) findViewById(R.id.signup_pat_image);
        ((RadioGroup) findViewById(R.id.signup_pat_r)).check(R.id.signup_pat_rbm);
        mAuth = FirebaseAuth.getInstance();

        pd = new ProgressDialog(Patient_Signup.this);
        pd.setMessage("Creating a new User for U");
        pd.setTitle("Create User");


        //Location details
        lm = (LocationManager) this.getSystemService(LOCATION_SERVICE);
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        lm.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 1000, 1, (android.location.LocationListener) this);
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
        if(civ.equals(R.drawable.prof))
        {
            Toast.makeText(this, "Please Select Image or Add Image Name", Toast.LENGTH_SHORT).show();
            return;
        }
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
        if(latitude==-1||longitude==-1)
        {
            Ask.on(this).id(id).forPermissions( Manifest.permission.ACCESS_FINE_LOCATION,Manifest.permission.ACCESS_COARSE_LOCATION).withRationales("Grant Location permission", "Grant Location permission").go();
            Toast.makeText(this, "Check permission", Toast.LENGTH_SHORT).show();
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
                            uid=mAuth.getCurrentUser().getUid();
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
                            hm.put("lat",""+latitude);
                            hm.put("long",""+longitude);
                            fd.setValue(hm);
                            Log.d("tabc",fd.getKey());
                            pd.dismiss();
                            UploadImageFileToFirebaseStorage();
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

    public void getImage(View view) {
        Intent intent = new Intent();
        // Setting intent type as image to select image from phone storage.
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Please Select Image"), Image_Request_Code);
    }
    private Uri FilePathUri ;
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == Image_Request_Code && resultCode == RESULT_OK && data != null && data.getData() != null) {
            FilePathUri = data.getData();
            Log.d("txyzuri",FilePathUri.toString());
            try {
                // Getting selected image into Bitmap.
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), FilePathUri);
                Log.d("txyzuri",FilePathUri.toString());
                // Setting up bitmap selected image into ImageView.
                civ.setImageBitmap(bitmap);
                // After selecting image change choose button above text.
                Toast.makeText(this,"Image Selected",Toast.LENGTH_SHORT).show();
            }
            catch (IOException e) {e.printStackTrace();}
        }
    }
    public String GetFileExtension(Uri uri) {
        ContentResolver contentResolver = getContentResolver();
        MimeTypeMap mimeTypeMap = MimeTypeMap.getSingleton();
        // Returning the file Extension.
        return mimeTypeMap.getExtensionFromMimeType(contentResolver.getType(uri)) ;
    }
    public void UploadImageFileToFirebaseStorage() {

        // Checking whether FilePathUri Is empty or not.
        if (FilePathUri != null) {

            // Setting progressDialog Title.
            //progressDialog.setTitle("Image is Uploading...")
            // Creating second StorageReference.
            StorageReference storageReference = FirebaseStorage.getInstance().getReference().child("profimag/" + uid + "." + GetFileExtension(FilePathUri));

            // Adding addOnSuccessListener to second StorageReference.
            storageReference.putFile(FilePathUri)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(final UploadTask.TaskSnapshot taskSnapshot) {
                            // Getting image name from EditText and store into string variable.
                            //   String TempImageName = ImageName.getText().toString().trim();
                            // Hiding the progressDialog after done uploading.
                            // progressDialog.dismiss();
                            // Showing toast message after done uploading.//Toast.makeText(getApplicationContext(), "Image Uploaded Successfully ", Toast.LENGTH_LONG).show();

                            @SuppressWarnings("VisibleForTests")
                            // ImageUploadInfo imageUploadInfo = new ImageUploadInfo(taskSnapshot.getDownloadUrl().toString());
                            final String url = taskSnapshot.getDownloadUrl().toString();
                            Log.d("check1",""+url);

                            // Getting image upload ID.
                            dr= FirebaseDatabase.getInstance().getReference().child("patients").child(uid).child("propic");
                            dr.setValue(url);
                            // On progress change upload time.
                        }
                    });
        }
        else {
            Toast.makeText(this, "Please Select Image or Add Image Name", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onLocationChanged(Location location) {
        latitude=location.getLatitude();
        longitude=location.getLongitude();
    }
    @Override
    public void onStatusChanged(String s, int i, Bundle bundle) {}
    @Override
    public void onProviderEnabled(String s) {}
    @Override
    public void onProviderDisabled(String s) {}
}
