package com.tracker.jessy.tracke;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class CourierDashboard extends Activity {

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_courier_dashboard);

        Button finish = findViewById(R.id.btnFinishTransaction);

        finish.setOnClickListener( new ClickListener() );

    }

    @Override
    public void onBackPressed()
    {

    }

    private class ClickListener implements  View.OnClickListener{
        @Override
        public void onClick(View v) {
            mAuth = FirebaseAuth.getInstance();
            final DatabaseReference DB = FirebaseDatabase.getInstance().getReference();


            AlertDialog.Builder builder;
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                builder = new AlertDialog.Builder(v.getContext(), android.R.style.Theme_Material_Dialog_Alert);
            } else {
                builder = new AlertDialog.Builder(v.getContext());
            }
            builder.setTitle("End Transaction")
                    .setMessage("Are you sure you want to finish your transaction?")
                    .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            new SendMailTask(CourierDashboard.this).execute("jeppbautista@gmail.com",
                                    "Walangforever12345!", "receiver@email.com", "emailSubject", "emailBody");

                            DB.child("users").child(mAuth.getCurrentUser().getUid()).child("isDelivered").setValue(true);
                            DB.child("users").child(mAuth.getCurrentUser().getUid()).child("tracking").setValue("");
                            DB.child("users").child(mAuth.getCurrentUser().getUid()).child("location").setValue(new com.tracker.jessy.tracke.Location());

                            stopService(new Intent(CourierDashboard.this, TrackerService.class));
                            goToMain();
                        }
                    })
                    .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {

                        }
                    })
                    .setIcon(android.R.drawable.ic_dialog_alert)
                    .show();
        }
    }

    private void goToMain()
    {
        Intent intent = new Intent(CourierDashboard.this, LoginActivity.class);
        startActivity(intent);
        finish();
    }
}
