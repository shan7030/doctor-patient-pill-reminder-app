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
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Vector;

public class RemoveTablet extends AppCompatActivity {
    ListView listView;

String actual_name="";
    Vector<String> v1=new Vector<String>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_remove_tablet);
        FirebaseAuth firebaseAuth=FirebaseAuth.getInstance();
        firebaseAuth=FirebaseAuth.getInstance();
        String curruid=firebaseAuth.getUid();

        DatabaseReference dr1;
        dr1= FirebaseDatabase.getInstance().getReference().child("Doctor_giving_Medicine/"+curruid+"/"+Onlyuid.uid);
        dr1.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot uniqueKeySnapshot : dataSnapshot.getChildren()) {
                    String tablet_name=uniqueKeySnapshot.getKey();
                    //String tablet_time=uniqueKeySnapshot.getValue(String.class);
                   // String total="Name :"+tablet_name+"       Time:"+tablet_time;
                    //Log.v("TabletList",""+tablet_name+tablet_time);
                    if(!tablet_name.equals("Empty"))
                    v1.add(tablet_name);
                }

                listView = (ListView) findViewById(R.id.list_of_tablets);

                ArrayAdapter adapter = new ArrayAdapter<String>(RemoveTablet.this, R.layout.patient_list, v1);

                listView.setAdapter(adapter);
                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                        FirebaseAuth firebaseAuth=FirebaseAuth.getInstance();
                        firebaseAuth=FirebaseAuth.getInstance();
                        String curruid=firebaseAuth.getUid();

                        final String currentClick=(String)adapterView.getItemAtPosition(i);
                        actual_name=currentClick;
                        DatabaseReference drt=FirebaseDatabase.getInstance().getReference().child("Doctor_giving_Medicine/"+curruid+"/"+Onlyuid.uid);
                     drt.addValueEventListener(new ValueEventListener() {
                         @Override
                         public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                             for (DataSnapshot uniqueKeySnapshot : dataSnapshot.getChildren()) {
                                 String tablet_name=uniqueKeySnapshot.getKey();
                                 //Log.v("RemoveTablet",""+tablet_name+actual_name);
                                 if(tablet_name.equals(actual_name))
                                 {

                                     uniqueKeySnapshot.getRef().removeValue();
                                     Intent i=new Intent(RemoveTablet.this,RemoveTablet.class);
                                     startActivity(i);
                                     break;
                                 }

                             }
                         }

                         @Override
                         public void onCancelled(@NonNull DatabaseError databaseError) {

                         }
                     });


                        /*ty.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                dataSnapshot.getR;
                               // String value=dataSnapshot.getValue(String.class);
                                //Log.v("RemoveTablet",""+kk);
                                Intent i=new Intent(RemoveTablet.this,RemoveTablet.class);
                                startActivity(i);
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {

                            }
                        });*/
                    }
                });
            }


            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
    private void Logout(){
        FirebaseAuth firebaseAuth=FirebaseAuth.getInstance();
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
