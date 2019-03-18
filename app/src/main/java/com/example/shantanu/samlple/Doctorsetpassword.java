package com.example.shantanu.samlple;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.IOException;

class push1
{
    public String Firstname;
    public String Lastname;
    public String Clinic_name;
    public String address;
    public String Clinic_Time;
    public String contact_no;

    push1(String a,String b,String c,String d,String e,String f)
    {
        Firstname=a;
        Lastname=b;
        Clinic_name=c;
        address=d;
        Clinic_Time=e;
        contact_no=f;
    }
}

public class Doctorsetpassword extends AppCompatActivity {

    private DatabaseReference dr;
    private EditText password;
    private EditText cpassword;
    private EditText username;
    private FirebaseAuth firebaseAuth;
    private ImageView userProfilePic;
    private static int PICK_IMAGE = 123;
    private Uri imagePath;
    private FirebaseStorage firebaseStorage;

    private StorageReference storageReference;

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode == PICK_IMAGE && resultCode == RESULT_OK && data.getData() != null){
            imagePath = data.getData();
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), imagePath);
                userProfilePic.setImageBitmap(bitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctorsetpassword);
        username=(EditText)findViewById(R.id.uname);
        password=(EditText)findViewById(R.id.pass);
        cpassword=(EditText)findViewById(R.id.cpass);
        userProfilePic = (ImageView)findViewById(R.id.ivProfilePic);
        firebaseStorage = FirebaseStorage.getInstance();

        storageReference = firebaseStorage.getReference();


        userProfilePic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setType("images/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent, "Select Image"), PICK_IMAGE);
            }
        });
    }

    public void tologindoctor(View view)
    {
        Intent i=new Intent(this,Main3Activity.class);
        startActivity(i);
    }
    public void pushCreateAccount(View view)
    {
        firebaseAuth= FirebaseAuth.getInstance();
        String uname=username.getText().toString().trim();
        String pass=password.getText().toString().trim();
        String cpass=cpassword.getText().toString().trim();
        if(!uname.contains("."))
        {
            Toast.makeText(this,"Please enter valid the email",Toast.LENGTH_SHORT).show();
            return;
        }
        if(pass.length()<=6)
        {
            Toast.makeText(this,"Password too short!",Toast.LENGTH_SHORT).show();
            return;
        }
        if(TextUtils.isEmpty(uname))
        {
            Toast.makeText(this,"Please enter the email",Toast.LENGTH_SHORT).show();
            return;
        }
        else if(TextUtils.isEmpty(pass))
        {
            Toast.makeText(this,"Please enter the Password",Toast.LENGTH_SHORT).show();
            return;
        }
        else if(TextUtils.isEmpty(cpass))
        {
            Toast.makeText(this,"Please enter Confirm Password",Toast.LENGTH_SHORT).show();
            return;
        }
        else if(!pass.equals(cpass))
        {
            Toast.makeText(this,"Password's dosen't match",Toast.LENGTH_SHORT).show();
            return;
        }
      /*  Log.v("Patientsetpassword",uname);
        Log.v("Patientsetpassword",pass);
        Log.v("PatientsetpassworPatientsd",PatientInfo.Firstname);*/
        uname="d"+uname;
        firebaseAuth.createUserWithEmailAndPassword(uname, pass)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            FirebaseUser user= firebaseAuth.getCurrentUser();
                            //
                            Toast.makeText(Doctorsetpassword.this, "Authentication Passed.",
                                    Toast.LENGTH_SHORT).show();

                            dr= FirebaseDatabase.getInstance().getReference();
                            //dr.child("Patients_info").child().push().setValue(patientInfo);
                          push1 h=new push1(DoctorInfo.Firstname,DoctorInfo.Lastname,DoctorInfo.Clinic_name,DoctorInfo.address,DoctorInfo.Clinic_Time,DoctorInfo.contact_no);
                           String uid=user.getUid();
                           dr.child("Doctors_info/"+uid).setValue(h);


                            StorageReference imageReference = storageReference.child(firebaseAuth.getUid()).child("Images").child("Profile Pic");  //User id/Images/Profile Pic.jpg
                            UploadTask uploadTask = imageReference.putFile(imagePath);
                            uploadTask.addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Toast.makeText(Doctorsetpassword.this, "Upload failed!", Toast.LENGTH_SHORT).show();
                                }
                            }).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
                                @Override
                                public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
                                    Toast.makeText(Doctorsetpassword.this, "Upload successful!", Toast.LENGTH_SHORT).show();
                                }
                            });

                        } else {
                            // If sign in fails, display a message to the user.
                            Toast.makeText(Doctorsetpassword.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                            //updateUI(null);
                        }

                        // ...
                    }
                });


      /*  HashMap<String,String> h1=new HashMap<String, String>();

        h1.put("Lastname",PatientInfo.Lastname);
        dr.child("Patients_info/"+uid).setValue(h1);*/

    }

}
