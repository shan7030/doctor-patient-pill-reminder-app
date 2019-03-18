package com.example.shantanu.samlple;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Vector;

class pusher12
{
    public  String Firstname;
    public  String Lastname;
    public  String age;
    public  String gender;
    public  String address;
    public  String contactno;
    pusher12()
    {

    }
    pusher12(String a,String b,String c,String d,String e,String f)
    {
        Firstname=a;
        Lastname=b;
        age=c;
        gender=d;
        address=e;
        contactno=f;
    }

}

public class DoctorInside1 extends AppCompatActivity {
    DatabaseReference dr;
    int index = 0;

    ListView listView;
    private FirebaseAuth firebaseAuth;
    pusher12 p1;
    String kl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor_inside1);
        firebaseAuth = FirebaseAuth.getInstance();
        final Vector<String> names = new Vector<String>();
        names.clear();
        FirebaseUser user = firebaseAuth.getCurrentUser();
        String uid = user.getUid();
        Log.v("DoctorInside1", "" + uid);

        dr = FirebaseDatabase.getInstance().getReference().child("Doctor_giving_Medicine/" + uid);

        dr.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                listView = (ListView) findViewById(R.id.name_list);

                for (DataSnapshot uniqueKeySnapshot : dataSnapshot.getChildren()) {
                    String bookskey = uniqueKeySnapshot.getKey();
                    Log.v("DoctorInside1", "" + bookskey);

                    DatabaseReference br1 = FirebaseDatabase.getInstance().getReference().child("Patients_info/" + bookskey);

                    br1.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot1) {
                            p1 = dataSnapshot1.getValue(pusher12.class);

                            kl = p1.Firstname;
                            Log.v("DoctorInside1","kl:"+kl);
                            names.add(kl);
                            Log.v("DoctorInside1", names.get(0));
                            ArrayAdapter adapter = new ArrayAdapter<String>(DoctorInside1.this, R.layout.patient_list, names);

                            listView.setAdapter(adapter);
                            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                                                @Override
                                                                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                                                                    String selected=(String)adapterView.getItemAtPosition(i);
                                                                    Log.v("DoctorInside1",""+selected);
                                                                    Stringpasser.name=selected;
                                                                    Log.v("DoctorInside1","Inside");
                                                                    Intent appInfo = new Intent(DoctorInside1.this, TabletList.class);
                                                                    startActivity(appInfo);

                                                                }
                                                            }
                            );
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError1) {

                        }
                    });


                }

                    /*for(int i=0;i<names.size();i++)
                    {
                        Log.v("DoctorInside1","problemishere"+names.get(i));
                    }*/


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });




}

  public void gotonewpatient(View view)
    {
       // Toast.makeText(this,"Please enter valid Contact number !",Toast.LENGTH_SHORT).show();
        Intent i=new Intent(this,AddNewPatient.class);
        startActivity(i);
    }

    public void gotoremovepatient(View view)
    {
        Intent i=new Intent(this,RemovePatient.class);
        startActivity(i);
    }
    private void Logout(){
        firebaseAuth.signOut();
        finish();
        startActivity(new Intent(DoctorInside1.this, MainActivity.class));
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
