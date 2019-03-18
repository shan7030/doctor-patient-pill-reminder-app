package com.example.shantanu.samlple;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class Main4Activity extends AppCompatActivity {
    private String Firstname;
    private String Lastname;
    private String Clinic_name;
    private String address;
    private String Clinic_Time;
    private String contact_no;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main4);
    }

    public void todoctorsetpass(View view)
    {
        Firstname=((EditText)findViewById(R.id.fname1)).getText().toString().trim();

        Lastname=((EditText)findViewById(R.id.lname1)).getText().toString().trim();

        Clinic_name=((EditText)findViewById(R.id.clinic1)).getText().toString().trim();
        address=((EditText)findViewById(R.id.address1)).getText().toString().trim();
        Clinic_Time=((EditText)findViewById(R.id.clinictime1)).getText().toString().trim();
        for(int i=0;i<Clinic_Time.length();i++)
        {
            if((Clinic_Time.charAt(i)<48 || Clinic_Time.charAt(i)>57) && Clinic_Time.charAt(i)!='-' && Clinic_Time.charAt(i)!=' ' && Clinic_Time.charAt(i)!=':')
            {
                Toast.makeText(this,"Please enter time as hh:min-hh:min !",Toast.LENGTH_SHORT).show();
                return;
            }
        }
        contact_no=((EditText)findViewById(R.id.contact1)).getText().toString().trim();
        if(contact_no.length()!=10)
        {
            Toast.makeText(this,"Please enter valid Contact number !",Toast.LENGTH_SHORT).show();
            return;
        }
        for(int i=0;i<contact_no.length();i++)
        {
            if(contact_no.charAt(i)<48 || contact_no.charAt(i)>57)
            {
                Toast.makeText(this,"Please enter valid Contact number !",Toast.LENGTH_SHORT).show();
                return;
            }

        }
        DoctorInfo.Firstname=Firstname;
        DoctorInfo.Lastname=Lastname;
        DoctorInfo.Clinic_name=Clinic_name;
        DoctorInfo.Clinic_Time=Clinic_Time;
        DoctorInfo.address=address;
        DoctorInfo.contact_no=contact_no;
        Intent i=new Intent(this,Doctorsetpassword.class);
        startActivity(i);
    }

}
