package com.example.shantanu.samlple;

import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

import java.util.Vector;
class Table_info12
{
    public String tablet_name;
    public String time;

    Table_info12(String a,String b)
    {
        tablet_name=a;
        time=b;
    }
}

public class TabletList extends AppCompatActivity {
    DatabaseReference dr;
    Vector<String> v1=new Vector<String>();
    ListView listView;
    private FirebaseAuth firebaseAuth;
    private FirebaseDatabase firebaseDatabase;
    private FirebaseStorage firebaseStorage;
    private ImageView profilePic;
    pusher p1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tablet_list);
        dr= FirebaseDatabase.getInstance().getReference().child("Patients_info");
        Log.v("TabletList",""+Stringpasser.name);
        firebaseStorage = FirebaseStorage.getInstance();

        profilePic=(ImageView)findViewById(R.id.ivProfilePic) ;

        dr.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {


                for (DataSnapshot uniqueKeySnapshot : dataSnapshot.getChildren()) {
                    p1=uniqueKeySnapshot.getValue(pusher.class);
                    Log.v("TabletList",""+p1.Firstname);
                    if(p1.Firstname.equals(Stringpasser.name))
                    {
                        Log.v("TabletList",""+p1.Lastname);
                        TextView t1=(TextView)findViewById(R.id.first);
                        PatientInfo.Firstname=p1.Firstname;
                        PatientInfo.Lastname=p1.Lastname;
                        PatientInfo.gender=p1.gender;
                        PatientInfo.contactno=p1.gender;
                        PatientInfo.address=p1.address;
                        PatientInfo.age=p1.age;
                        t1.setText("First Name :"+p1.Firstname);
                        TextView t2=(TextView)findViewById(R.id.last);
                        t2.setText("Last Name :"+p1.Lastname);
                        TextView t3=(TextView)findViewById(R.id.contact);
                        t3.setText("Contact Number :"+p1.contactno);
                        TextView t4=(TextView)findViewById(R.id.age);
                        t4.setText("Age :"+p1.age);
                        Onlyuid.uid=uniqueKeySnapshot.getKey();
                        FirebaseAuth firebaseAuth=FirebaseAuth.getInstance();
                        firebaseAuth=FirebaseAuth.getInstance();
                        String curruid=firebaseAuth.getUid();
                        StorageReference storageReference = firebaseStorage.getReference();

                        storageReference.child(Onlyuid.uid).child("Images/Profile Pic").getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(Uri uri) {
                                Picasso.get().load(uri).fit().centerCrop().into(profilePic);
                            }
                        });
                        DatabaseReference dr1;
                        dr1= FirebaseDatabase.getInstance().getReference().child("Doctor_giving_Medicine/"+curruid+"/"+Onlyuid.uid);
                        dr1.addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                for (DataSnapshot uniqueKeySnapshot : dataSnapshot.getChildren()) {
                                    String tablet_name=uniqueKeySnapshot.getKey();
                                    //String tablet_time=uniqueKeySnapshot.getValue(String.class);



                                  //  String total="Name :"+tablet_name+"               Time:"+tablet_time;
                                   if(!tablet_name.equals("Empty")) {
                                       v1.add(tablet_name);
                                   }
                                }

                                listView = (ListView) findViewById(R.id.list_2);

                                ArrayAdapter adapter = new ArrayAdapter<String>(TabletList.this, R.layout.patient_list, v1);

                                listView.setAdapter(adapter);
                            }


                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {

                            }
                        });
                        //Log.v("TabletList",""+uid);
                        break;
                    }
    }


}

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

                }

     });




    }

        public void openewtablet(View view)
        {
            Intent i=new Intent(this,AddnewTablet.class);
            startActivity(i);
        }
        public void removetablet(View view)
        {
            Intent i=new Intent(this,RemoveTablet.class);
            startActivity(i);

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

