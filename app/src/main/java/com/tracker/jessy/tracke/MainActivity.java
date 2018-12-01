package com.tracker.jessy.tracke;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    private final String TAG = "MESSAGE_123";

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

    }


}
