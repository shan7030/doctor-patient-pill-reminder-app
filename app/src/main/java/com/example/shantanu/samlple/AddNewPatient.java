package com.example.shantanu.samlple;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

class pusher1
{
    public  String Firstname;
    public  String Lastname;
    public  String age;
    public  String gender;
    public  String address;
    public  String contactno;
    pusher1()
    {

    }
    pusher1(String a,String b,String c,String d,String e,String f)
    {
        Firstname=a;
        Lastname=b;
        age=c;
        gender=d;
        address=e;
        contactno=f;
    }

}


class Table_info1
{
    public String tablet_name;
    public int morning;
    public int afternoon;
    public int evening;
    public int night;
    public String alltimes="";

}

public class AddNewPatient extends AppCompatActivity {
    DatabaseReference dr;
    pusher1 p1=new pusher1();
    private FirebaseAuth firebaseAuth;
    int flag=0;
    public int idd=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new_patient);
        firebaseAuth= FirebaseAuth.getInstance();

    }
    public void paaa(View view)
    {

        String uid;
        final String name=((EditText)findViewById(R.id.name)).getText().toString().trim();
        dr= FirebaseDatabase.getInstance().getReference().child("Patients_info");

        dr.addValueEventListener(new ValueEventListener() {
        @Override
     public void onDataChange(DataSnapshot dataSnapshot) {

         for(DataSnapshot uniqueKeySnapshot : dataSnapshot.getChildren()){
                                                               //Loop 1 to go through all the child nodes of users
         String bookskey = uniqueKeySnapshot.getKey();
         Log.v("AddNewPatient",bookskey);
          pusher1 p1;

            p1=uniqueKeySnapshot.getValue(pusher1.class);
            DatabaseReference drf=FirebaseDatabase.getInstance().getReference();
             if(name.equals(p1.Firstname))
             {
                 FirebaseUser user= firebaseAuth.getCurrentUser();

                 String uid=user.getUid();
                 Table_info t=new Table_info();
                 t.tablet_name="Empty";
                drf.child("Doctor_giving_Medicine/"+uid+"/"+bookskey+"/"+t.tablet_name).setValue(t);
                drf.child("Patients_with_Doctor/"+bookskey+"/"+uid).setValue(uid);
                //drf.child("Doctor_giving_Medicine/"+uid).setValue(bookskey);
                flag=1;
                return;
             }
     }
          }


            @Override
             public void onCancelled(DatabaseError databaseError) {

         }
        });

        if(flag==0)
        {
            Toast.makeText(this,"Entered Patient Dosen't exists in the database!",Toast.LENGTH_SHORT).show();

        }
        else
        {
            Toast.makeText(this,"Patient added succesfully!",Toast.LENGTH_SHORT).show();
            Intent i=new Intent(this,DoctorInside1.class);
            startActivity(i);
        }


    }
    private void Logout(){
        firebaseAuth.signOut();
        finish();
        startActivity(new Intent(this, MainActivity.class));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch(item.getItemId()){
            case R.id.logoutMenu:{
                Logout();
                break;
            }
            /*
            case R.id.profileMenu:
                startActivity(new Intent(PreviousComplaints.this, ProfileActivity.class));
                break;*/

        }
        return super.onOptionsItemSelected(item);
    }
}
