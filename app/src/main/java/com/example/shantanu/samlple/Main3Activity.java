package com.example.shantanu.samlple;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class Main3Activity extends AppCompatActivity {

    private FirebaseAuth firebaseAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);
        firebaseAuth=FirebaseAuth.getInstance();

    }
    public void signup_doc2(View view)
    {
        Intent i=new Intent(this,Main4Activity.class);
        startActivity(i);
    }
    public void opensuccessful(View view)
    {

        String usr="d"+((EditText)findViewById(R.id.editText)).getText().toString().trim();
        String pas=((EditText)findViewById(R.id.editText2)).getText().toString().trim();

        if(usr.equals("d"))
        {
            Toast.makeText(Main3Activity.this, "Enter valid emailid.",
                    Toast.LENGTH_SHORT).show();
            return;
        }
        if(pas.equals(""))
        {
            Toast.makeText(Main3Activity.this, "Enter valid password.",
                    Toast.LENGTH_SHORT).show();
            return;
        }
        int flag=0;
        for(int i=0;i<usr.length();i++)
        {
            if(usr.charAt(i)=='@')
            {
                flag++;
            }
            if(usr.charAt(i)=='.')
            {
                flag++;
            }
        }
        if(flag!=2)
        {
            Toast.makeText(Main3Activity.this, "Enter valid emailid.",
                    Toast.LENGTH_SHORT).show();
            return;
        }
        for(int i=0;i<usr.length();i++)
        firebaseAuth.signInWithEmailAndPassword(usr,pas)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful())
                        {
                            Toast.makeText(Main3Activity.this, "Authentication Passed.",
                                    Toast.LENGTH_SHORT).show();

                            Intent i=new Intent(Main3Activity.this,DoctorInside1.class);
                            startActivity(i);

                        }
                        else
                        {
                            Toast.makeText(Main3Activity.this, "Authentication Failed.",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });

    }
}
