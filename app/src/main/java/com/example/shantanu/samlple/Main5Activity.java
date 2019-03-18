package com.example.shantanu.samlple;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;



public class Main5Activity extends AppCompatActivity {

    private String Firstname;
    private String Lastname;
    private String age;
    private String gender;
    private String address;
    private String contactno;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main5);
    }
    public void topatientsetpassword(View view)
    {

        Firstname=((EditText)findViewById(R.id.fname1)).getText().toString().trim();
        Lastname=((EditText)findViewById(R.id.lname1)).getText().toString().trim();

            age = ((EditText) findViewById(R.id.age1)).getText().toString().trim();

            for(int i=0;i<age.length();i++)
            {
                if(age.charAt(i)<48 || age.charAt(i)>57)
                {
                    Toast.makeText(this,"Please enter valid age!",Toast.LENGTH_SHORT).show();
                    return;
                }

            }

        gender=((EditText)findViewById(R.id.gender1)).getText().toString().trim();
        address=((EditText)findViewById(R.id.address1)).getText().toString().trim();
        contactno=((EditText)findViewById(R.id.contact1)).getText().toString().trim();

        if(contactno.length()!=10)
        {
            Toast.makeText(this,"Please enter valid Contact number !",Toast.LENGTH_SHORT).show();
            return;
        }
        for(int i=0;i<contactno.length();i++)
        {
            if(contactno.charAt(i)<48 || contactno.charAt(i)>57)
            {
                Toast.makeText(this,"Please enter valid Contact number !",Toast.LENGTH_SHORT).show();
                return;
            }

        }

        PatientInfo.Firstname=Firstname;
        PatientInfo.Lastname=Lastname;
        PatientInfo.contactno=contactno;
        PatientInfo.address=address;
        PatientInfo.age=age;
        PatientInfo.gender=gender;




        Intent i=new Intent(this,Patientsetpassword.class);
        startActivity(i);
    }

}
