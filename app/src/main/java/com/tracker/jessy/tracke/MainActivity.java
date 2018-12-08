package com.tracker.jessy.tracke;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.w3c.dom.Text;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private final String TAG = "xxx";
    private FirebaseAuth mAuth;

    private CourierDashboard courierDashboard;

    @Override
    protected void onCreate(Bundle state) {
        super.onCreate(state);

        if (mAuth.getInstance().getCurrentUser() == null )
        {
            setContentView(R.layout.activity_main);

            Button signUp, logIn;
            signUp =  findViewById(R.id.btnSignUp);
            logIn = findViewById(R.id.btnLogin);

            signUp.setOnClickListener(this);
            logIn.setOnClickListener(this);
        }
        else
        {
            Intent logIntent = new Intent(MainActivity.this, LoginActivity.class);
            startActivity(logIntent);
        }

    }

    @Override
    public void onClick(View v) {

        final View v2 = v;

        switch(v.getId())
        {
            case R.id.btnLogin:

                String email = "";
                String pass = "";

                try {
                    email = ((TextView)findViewById(R.id.txtUser_login)).getText().toString();
                    pass = ((TextView)findViewById(R.id.txtPassword_login)).getText().toString();

                    mAuth = FirebaseAuth.getInstance();
                    mAuth.signInWithEmailAndPassword(email, pass)
                            .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if (task.isSuccessful()) {

                                        Intent logIntent = new Intent(MainActivity.this, LoginActivity.class);
//                                        Intent logIntent = new Intent(MainActivity.this, CourierDashboard.class);
                                        startActivity(logIntent);

                                    } else {
                                        Toast.makeText(MainActivity.this, "Invalid credentials. Log in failed", Toast.LENGTH_LONG).show();
                                    }

                                }
                            });
                } catch (NullPointerException e ){ Toast.makeText(MainActivity.this, "Login failed", Toast.LENGTH_LONG).show();}
                catch (IllegalArgumentException x){ Toast.makeText(MainActivity.this, "Login failed", Toast.LENGTH_LONG).show();}

                break;
            case R.id.btnSignUp:

                Intent signIntent = new Intent(v.getContext(), SignUpActivity.class);
                v.getContext().startActivity(signIntent);

                break;
            default:
                Log.d("View_id", "");
        }
    }
}