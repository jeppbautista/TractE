package com.tracker.jessy.tracke;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class MainActivity extends AppCompatActivity {

    private final String TAG = "MESSAGE_123";
    private DatabaseReference mDatabase;

    @Override
    protected void onCreate(Bundle state) {
        super.onCreate(state);
        setContentView(R.layout.activity_main);

        Button signUp, logIn;
        signUp =  findViewById(R.id.btnSignUp);
        logIn = findViewById(R.id.btnLogin);

        LoginController loginController = new LoginController();

        signUp.setOnClickListener(loginController);
        logIn.setOnClickListener(loginController);


        DBController dbcontroller = new DBController();
        mDatabase = FirebaseDatabase.getInstance().getReference();
        mDatabase.addValueEventListener(dbcontroller);
//        mDatabase.child("admin2").setValue(new User("admin", "admin", false));
    }


}
