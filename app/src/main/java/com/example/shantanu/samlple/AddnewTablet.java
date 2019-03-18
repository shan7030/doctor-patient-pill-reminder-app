package com.example.shantanu.samlple;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

class Table_info
{
    public String tablet_name;
    public int morning;
    public int afternoon;
    public int evening;
    public int night;
    public String alltimes;
    Table_info()
    {
        alltimes="";
    }

}

public class AddnewTablet extends AppCompatActivity {
    private DatabaseReference dr;
    private FirebaseAuth firebaseAuth;
    int k1=0,k2=0,k3=0,k4=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addnew_tablet);
        firebaseAuth=FirebaseAuth.getInstance();

    }

    public void pwer(View view)
    {

        String tn=((EditText)findViewById(R.id.name)).getText().toString().trim();
       // String time=((EditText)findViewById(R.id.name2)).getText().toString().trim();
     Table_info t=new Table_info();
       t.tablet_name=tn;

      /*  Log.v("AddnewTablet",""+tn);
        Log.v("AddnewTablet",""+Onlyuid.uid);
*/
        FirebaseUser user= firebaseAuth.getCurrentUser();
        String curruid=user.getUid();
        t.alltimes="";
        if(k1%2==1)
        {
            t.alltimes=t.alltimes+"Morning";
            t.morning=1;
        }
        else
        {
            t.morning=0;
        }

        if(k2%2==1)
        {
            t.alltimes=t.alltimes+"Afternoon";
            t.afternoon=1;
        }
        else
        {
            t.afternoon=0;
        }

        if(k3%2==1)
        {
            t.alltimes=t.alltimes+"Evening";
            t.evening=1;
        }
        else
        {
            t.evening=0;
        }

        if(k4%2==1)
        {
            t.alltimes=t.alltimes+"Night";
            t.night=1;
        }
        else
        {
            t.night=0;
        }
        Log.v("AddnewTablet",""+curruid);
       dr= FirebaseDatabase.getInstance().getReference().child("Doctor_giving_Medicine/"+curruid+"/"+Onlyuid.uid+"/"+tn);
        dr.setValue(t);
        Intent i=new Intent(this,TabletList.class);
        startActivity(i);

    }
    public void opentimer(View view)
    {

        k1++;
    }
    public void opentimer1(View view)
    {

        k2++;
    }
    public void opentimer2(View view)
    {
        k3++;
    }
    public void opentimer3(View view)
    {
        k4++;
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
