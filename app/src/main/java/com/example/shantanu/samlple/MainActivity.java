package com.example.shantanu.samlple;

        import android.content.Intent;
        import android.support.v7.app.AppCompatActivity;
        import android.os.Bundle;
        import android.util.Log;
        import android.view.View;

        import com.google.firebase.auth.FirebaseAuth;
        import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {
    FirebaseAuth firebaseAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        firebaseAuth = FirebaseAuth.getInstance();
        FirebaseUser user = firebaseAuth.getCurrentUser();

        if(user != null){
            String useremail=user.getEmail();
            if(useremail.charAt(0)=='p') {
                Log.v("MainActivity", "" + useremail);
                finish();
                startActivity(new Intent(MainActivity.this, PatientInside.class));
            }
            else
            {
                finish();
                startActivity(new Intent(MainActivity.this, DoctorInside1.class));
            }
        }
    }
    public void topatient(View view)
    {
        Intent i=new Intent(this,Main2Activity.class);
        startActivity(i);
    }
    public void todoctor(View view)
    {
        Intent i=new Intent(this,Main3Activity.class);
        startActivity(i);
    }
}
