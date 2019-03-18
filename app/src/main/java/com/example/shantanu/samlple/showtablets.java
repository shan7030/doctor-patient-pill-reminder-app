package com.example.shantanu.samlple;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

import java.util.Calendar;
import java.util.Vector;

public class showtablets extends AppCompatActivity {

    ListView listView;
    private FirebaseAuth firebaseAuth;
    private FirebaseDatabase firebaseDatabase;
    private FirebaseStorage firebaseStorage;
    private ImageView profilePic;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_showtablets);

        profilePic=(ImageView)findViewById(R.id.ivProfilePic) ;

        firebaseStorage = FirebaseStorage.getInstance();

        //     Log.v("showtablets",""+Stringpasser.uid);
        DatabaseReference dref= FirebaseDatabase.getInstance().getReference().child("Doctors_info");
        dref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot unique:dataSnapshot.getChildren())
                {
                    DoctorInfo12 t=unique.getValue(DoctorInfo12.class);
                    Log.v("showtablets","1"+t.Firstname+Stringpasser.uid);
                    if(t.Firstname.equals(Stringpasser.uid))
                    {
                        uidclass.uidofdoc=unique.getKey();
                        Stringpasser.uid=unique.getKey();
                        Log.v("showtablets","Inside"+Stringpasser.uid);
                        DoctorInfo.Firstname=t.Firstname;
                        DoctorInfo.Lastname=t.Lastname;
                        DoctorInfo.Clinic_Time=t.Clinic_Time;
                        DoctorInfo.Clinic_name=t.Clinic_name;
                        DoctorInfo.address=t.address;
                        DoctorInfo.contact_no=t.contact_no;
                        break;

                    }

                }
                Log.v("showtablets",""+Stringpasser.uid);
                Log.v("showtablets",""+DoctorInfo.Firstname);
                TextView t1=(TextView)findViewById(R.id.text1);
                TextView t2=(TextView)findViewById(R.id.text2);
                TextView t3=(TextView)findViewById(R.id.text3);
                TextView t4=(TextView)findViewById(R.id.text4);
                TextView t5=(TextView)findViewById(R.id.text5);
                TextView t6=(TextView)findViewById(R.id.text6);
                String k="First Name :"+DoctorInfo.Firstname;
                t1.setText(k);
                k="Last Name  :"+DoctorInfo.Lastname;
                t2.setText(k);
                k="Contact No :"+DoctorInfo.contact_no;
                t3.setText(k);
                k="Address  :"+DoctorInfo.address;
                t4.setText(k);
                k="Clinic Time :"+DoctorInfo.Clinic_Time;
                t5.setText(k);
                k="Clinic Name :"+DoctorInfo.Clinic_name;
                t6.setText(k);
                StorageReference storageReference = firebaseStorage.getReference();
                storageReference.child(uidclass.uidofdoc).child("Images/Profile Pic").getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        Picasso.get().load(uri).fit().centerCrop().into(profilePic);
                    }
                });
                FirebaseAuth firebaseAuth=FirebaseAuth.getInstance();
                FirebaseUser user = firebaseAuth.getCurrentUser();
                String uider = user.getUid();
                DatabaseReference drt=FirebaseDatabase.getInstance().getReference().child("Doctor_giving_Medicine/"+Stringpasser.uid+"/"+uider);
                drt.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        final Vector<String> vs=new Vector<String>();

                        for(DataSnapshot ds:dataSnapshot.getChildren())
                        {
                            Table_info1 table_info=ds.getValue(Table_info1.class);
                            if(!table_info.tablet_name.equals("Empty"))
                            vs.add(table_info.tablet_name);
                        }
                        listView = (ListView) findViewById(R.id.list_5);

                        final ArrayAdapter adapter = new ArrayAdapter<String>(showtablets.this, R.layout.patient_list,vs);

                        listView.setAdapter(adapter);
                        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                                            @Override
                                                            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                                                               /*String selected=(String)adapterView.getItemAtPosition(i);
                                                                Log.v("DoctorInside1",""+selected);
                                                                Stringpasser.uid=selected;
                                                                Log.v("DoctorInside1","Inside");
                                                                Intent appInfo = new Intent(PatientInside.this, showtablets.class);
                                                                startActivity(appInfo);*/
                                                               Stringpasser.uid=(String)adapterView.getItemAtPosition(i);

                                                                Intent appInfo = new Intent(showtablets.this, Tabletinfo.class);
                                                                startActivity(appInfo);
                                                            }
                                                        }
                        );
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

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
