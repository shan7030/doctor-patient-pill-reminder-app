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
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Vector;

public class RemovePatient extends AppCompatActivity {
    DatabaseReference dr;
    int index = 0;

    ListView listView;
    private FirebaseAuth firebaseAuth;
    pusher12 p1;
    String kl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_remove_patient);
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
                listView = (ListView) findViewById(R.id.name1_list);

                for (DataSnapshot uniqueKeySnapshot : dataSnapshot.getChildren()) {
                    String bookskey = uniqueKeySnapshot.getKey();
                    Log.v("DoctorInside1", "" + bookskey);

                    DatabaseReference br1 = FirebaseDatabase.getInstance().getReference().child("Patients_info/" + bookskey);

                    br1.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot1) {
                            p1 = dataSnapshot1.getValue(pusher12.class);

                            kl = p1.Firstname;
                     //       Log.v("DoctorInside1","kl:"+kl);
                            names.add(kl);
                         //   Log.v("DoctorInside1", names.get(0));
                            ArrayAdapter adapter = new ArrayAdapter<String>(RemovePatient.this, R.layout.patient_list, names);

                            listView.setAdapter(adapter);
                            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                                                @Override
                                                                public void onItemClick(final AdapterView<?> adapterView, View view, final int i, long l) {


                                                                    dr= FirebaseDatabase.getInstance().getReference().child("Patients_info");

                                                                    dr.addValueEventListener(new ValueEventListener() {
                                                                        @Override
                                                                        public void onDataChange(DataSnapshot dataSnapshot) {
                                                                            String tbd=(String)adapterView.getItemAtPosition(i);
                                                                            Log.v("RemovePatient",""+tbd);
                                                                            pusher p1;
                                                                            for (DataSnapshot uniqueKeySnapshot : dataSnapshot.getChildren()) {
                                                                                p1=uniqueKeySnapshot.getValue(pusher.class);
                                                                                Log.v("RemovePatient",""+p1.Firstname);
                                                                               if(p1.Firstname.equals(tbd))
                                                                                {
                                                                                   String uid=uniqueKeySnapshot.getKey();
                                                                                    Log.v("RemovePatient",""+uid);
                                                                                    String uid1=firebaseAuth.getCurrentUser().getUid();
                                                                                    DatabaseReference ty=FirebaseDatabase.getInstance().getReference().child("Doctor_giving_Medicine/"+uid1+"/"+uid);
                                                                                    DatabaseReference tr=FirebaseDatabase.getInstance().getReference().child("Patients_with_Doctor/"+uid+"/"+uid1);
                                                                                    tr.getRef().removeValue();
                                                                                    ty.addListenerForSingleValueEvent(new ValueEventListener() {
                                                                                        @Override
                                                                                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                                                                                        dataSnapshot.getRef().removeValue();
                                                                                            Intent i=new Intent(RemovePatient.this,RemovePatient.class);
                                                                                            startActivity(i);
                                                                                        }

                                                                                        @Override
                                                                                        public void onCancelled(@NonNull DatabaseError databaseError) {

                                                                                        }
                                                                                    });
                                                                                  break;
                                                                                }
                                                                            }


                                                                        }

                                                                        @Override
                                                                        public void onCancelled(@NonNull DatabaseError databaseError) {

                                                                        }

                                                                    });
                                                                }
                                                            }
                            );
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError1) {

                        }
                    });


                }



            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });



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
