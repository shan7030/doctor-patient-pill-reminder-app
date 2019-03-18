package com.example.shantanu.samlple;

import android.app.AlarmManager;
import android.app.DialogFragment;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.provider.AlarmClock;
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
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Calendar;
import java.util.Vector;

public class Tabletinfo extends AppCompatActivity implements TimePickerDialog.OnTimeSetListener
{

    ListView listView;
    TimePicker toppp;

    Vector<String> v=new Vector<String>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tabletinfo);
        /*TextView t=(TextView)findViewById(R.id.tablename);
        t.setText(uidclass.uidofdoc);*/
        FirebaseAuth fa=FirebaseAuth.getInstance();
        String uid_of_patient=fa.getCurrentUser().getUid();
        Log.v("Tabletinfo",""+uid_of_patient);
        Log.v("Tabletinfo",""+Stringpasser.uid);
        Log.v("Tabletinfo",""+uidclass.uidofdoc);
        DatabaseReference dref= FirebaseDatabase.getInstance().getReference().child("Doctor_giving_Medicine/"+uidclass.uidofdoc+"/"+uid_of_patient+"/"+Stringpasser.uid);
        dref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Table_info t=new Table_info();
                    t=dataSnapshot.getValue(Table_info.class);
                    TextView t3=(TextView)findViewById(R.id.tablename);
                    t3.setText("Name of the Tablet :"+Stringpasser.uid);

                    //Log.v("Tabletinfo","okokok:"+t.alltimes);

                    if(t.morning==1)
                    {
                        v.add("Morning");
                    }
                    if(t.afternoon==1)
                    {
                        v.add("Afternoon");
                    }
               if(t.evening==1)
               {
                   v.add("Evening");
               }
               if(t.night==1)
               {
                   v.add("Night");
               }
                listView = (ListView) findViewById(R.id.list_of_timings);

                ArrayAdapter adapter = new ArrayAdapter<String>(Tabletinfo.this, R.layout.patient_list,v);

                listView.setAdapter(adapter);
                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                        String timerpo = (String) adapterView.getItemAtPosition(i);

                        DialogFragment timepicker=new TimePickerFragment();
                        //timepicker.show(getSupportFragmentManager(),"time picker");
                        Calendar calendar=Calendar.getInstance();
                        if(timerpo.equals("Morning")) {
                            calendar.set(Calendar.HOUR_OF_DAY, 8);
                            calendar.set(Calendar.MINUTE, 00);
                            calendar.set(Calendar.SECOND, 00);
                        }
                        if(timerpo.equals("Evening"))
                        {
                            calendar.set(Calendar.HOUR_OF_DAY, 5);
                            calendar.set(Calendar.MINUTE, 00);
                            calendar.set(Calendar.SECOND, 00);
                        }
                        if(timerpo.equals("Afternoon"))
                        {
                            calendar.set(Calendar.HOUR_OF_DAY, 12);
                            calendar.set(Calendar.MINUTE, 00);
                            calendar.set(Calendar.SECOND, 00);
                        }
                        if(timerpo.equals("Night"))
                        {
                            calendar.set(Calendar.HOUR_OF_DAY, 8);
                            calendar.set(Calendar.MINUTE, 00);
                            calendar.set(Calendar.SECOND, 00);
                        }
                        Intent intent=new Intent(getApplicationContext(),Notification_Reciever.class);
                        intent.setAction("MY_NOTIFICATION_MESSAGE");
                        PendingIntent pendingIntent=PendingIntent.getBroadcast(getApplicationContext(),100,intent,PendingIntent.FLAG_UPDATE_CURRENT);
                        AlarmManager alarmManager=(AlarmManager)getSystemService(ALARM_SERVICE);
                        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP,calendar.getTimeInMillis(),AlarmManager.INTERVAL_DAY,pendingIntent);

                        Toast.makeText(Tabletinfo.this,"Alarm set for :"+timerpo,Toast.LENGTH_SHORT).show();




                    }

                });

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });




    }
    void addnotification(String tt)
    {

    }

    @Override
    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
        Log.v("Tabletinfo",""+hourOfDay);
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
