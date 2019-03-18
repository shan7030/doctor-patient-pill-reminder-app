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
import android.widget.ListView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Vector;

class uidd
{
    String uis;
}


class DoctorInfo12 {
    public  String Firstname;
    public String Lastname;
    public String Clinic_name;
    public String address;
    public String Clinic_Time;
    public String contact_no;
}
public class PatientInside extends AppCompatActivity {

    ListView listView;
    String uid;
    FirebaseAuth firebaseAuth;
      Vector<String> v=new Vector<String>();
     public void addString(String kkk)
     {
         v.add(kkk);
     }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient_inside);


        firebaseAuth = FirebaseAuth.getInstance();
       v.clear();
        FirebaseUser user = firebaseAuth.getCurrentUser();
         uid= user.getUid();
        DatabaseReference dr;

        dr = FirebaseDatabase.getInstance().getReference().child("Patients_with_Doctor/"+uid);

        dr.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(final DataSnapshot dataSnapshot) {

                 final Vector<String> bt=new Vector<String>();
                for (DataSnapshot uniqueKeySnapshot : dataSnapshot.getChildren()) {
                 String bookskey = uniqueKeySnapshot.getKey();

                    //Table_info t;
                  //  t=uniqueKeySnapshot.getValue(Table_info.class);

                    Log.v("PatientInside",""+bookskey);
                    DatabaseReference fg=FirebaseDatabase.getInstance().getReference().child("Doctors_info/"+bookskey);

                   // bt.add(bookskey);
                    uidclass.uidofdoc=bookskey;
                        fg.addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot1) {
                                DoctorInfo12 di=dataSnapshot1.getValue(DoctorInfo12.class);
                                if(di!=null)
                                {
                                bt.add(di.Firstname);
                                }
                                listView = (ListView) findViewById(R.id.list_4);

                                ArrayAdapter adapter = new ArrayAdapter<String>(PatientInside.this, R.layout.patient_list,bt);

                                listView.setAdapter(adapter);
                                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                                                    @Override
                                                                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                                                                        String selected=(String)adapterView.getItemAtPosition(i);
                                                                        Log.v("DoctorInside1",""+selected);
                                                                        Stringpasser.uid=selected;

                                                                        Log.v("DoctorInside1","Inside");
                                                                        Intent appInfo = new Intent(PatientInside.this, showtablets.class);
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
                          Log.v("PatientInside","trt");




                }




            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }
    public void gotoshowtablets(View view)
    {
        Intent i=new Intent(this,showtablets.class);
        startActivity(i);
    }
    private void Logout(){
        firebaseAuth.signOut();
        finish();
        startActivity(new Intent(PatientInside.this, MainActivity.class));
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
