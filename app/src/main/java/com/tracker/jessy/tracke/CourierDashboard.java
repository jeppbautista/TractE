package com.tracker.jessy.tracke;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
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
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

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


                            DB.addListenerForSingleValueEvent(new ValueEventListener() {

                                @Override
                                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                    for (DataSnapshot ds : dataSnapshot.getChildren())
                                    {
//
//                                        Log.d("jepp", ds.toString());
//                                        Log.d("jep", ds.child("users").child("tracking").toString() + "==" + ds.child("users").child(mAuth.getCurrentUser().getUid()).child("tracking").toString());
//
//                                        Log.d("jepp", ds.child("username").toString() + "!=" + ds.child(mAuth.getCurrentUser().getUid()).child("username").toString());
                                        String tnum = ds.child(mAuth.getCurrentUser().getUid()).child("tracking").getValue().toString();
                                        for (DataSnapshot ds2 : ds.getChildren())
                                        {
                                            Log.d("jepp", ds2.child("tracking").getValue().toString() + "==" + tnum);
                                            Log.d("jepp", ds2.child("isCourier").getValue().toString());
                                            if((boolean)ds2.child("isCourier").getValue() == false)
                                            {
                                                if(ds2.child("tracking").getValue().toString().equals(tnum))
                                                {
                                                    Log.d("jepp",ds2.child("username").getValue().toString());
                                                    new SendMailTask(CourierDashboard.this).execute("jeppbautista@gmail.com",
                                                            "Walangforever123456!", ds2.child("username").getValue().toString(), "Delivery completed", "Hi, " +
                                                                    "Thanks for trusting Tracker E! We would like to inform you that your order with tracking number : " + ds2.child("tracking").toString() + " has been delivered. Thank you!");
                                                    DB.child("users").child(ds2.getKey().toString()).child("tracking").setValue("");
                                                    DB.child("users").child(mAuth.getCurrentUser().getUid()).child("isDelivered").setValue(true);
                                                    DB.child("users").child(mAuth.getCurrentUser().getUid()).child("tracking").setValue("");
                                                    DB.child("users").child(mAuth.getCurrentUser().getUid()).child("location").setValue(new com.tracker.jessy.tracke.Location());
                                                    stopService(new Intent(CourierDashboard.this, TrackerService.class));
                                                    goToMain();
                                                }
                                            }
                                        }


//                                        if(ds.child("users").child("tracking").toString() == ds.child("users").child(mAuth.getCurrentUser().getUid()).child("tracking").toString()
//                                                && ds.child("username").toString() != ds.child(mAuth.getCurrentUser().getUid()).child("username").toString()){
                                            //
//                                        }
                                    }
                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError databaseError) {

                                }
                            });




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
