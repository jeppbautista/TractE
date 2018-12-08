package com.tracker.jessy.tracke;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class SignUpActivity  extends Activity {

    private final String TAG = "xxx";
    private RadioGroup radioGroup;
    private RadioButton radioButton;

    private FirebaseAuth mAuth;
    private boolean isSuccess = false;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signup_main);

        radioGroup = findViewById(R.id.radioGrp);
        Button btnSignUp = findViewById(R.id.btnSignup);

        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int selectedId = radioGroup.getCheckedRadioButtonId();

                radioButton = (RadioButton) findViewById(selectedId);
                createFirebaseUser();

            }
        });
    }

    private void createFirebaseUser()
    {

        mAuth = FirebaseAuth.getInstance();


        final String email = ((TextView)findViewById(R.id.txtEmail_2)).getText().toString();
        final String pass = ((TextView)findViewById(R.id.txtPassword_login)).getText().toString();


        if (email == "" || pass == "")
        {
            mAuth.createUserWithEmailAndPassword(email, pass)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful())
                            {

                                FirebaseUser currentUser = mAuth.getCurrentUser();
                                DatabaseReference ref = FirebaseDatabase.getInstance().getReference("users/"+currentUser.getUid().toString());
                                String userType = "";
                                try
                                {
                                    userType = radioButton.getText().toString();
                                    User user = new User(currentUser.getEmail(), userType.equals("Courier") ? true : false, false,"" , new Location());
                                    ref.setValue(user);

                                    Toast.makeText(SignUpActivity.this, "Authentication success.",
                                            Toast.LENGTH_SHORT).show();

                                }
                                catch (Exception e)
                                {
                                    Toast.makeText(SignUpActivity.this, "Please select a user type.",
                                            Toast.LENGTH_SHORT).show();
                                }

                            }
                            else
                            {
                                Toast.makeText(SignUpActivity.this, "Authentication failed.",
                                        Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
        }
        else
        {
            Toast.makeText(SignUpActivity.this, "Please fill up the fields.",
                    Toast.LENGTH_SHORT).show();
        }

    }


}
