package com.tracker.jessy.tracke;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

public class LoginActivity extends Activity implements View.OnClickListener{

    private final int REQUEST_CAMERA = 1;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_main);

        Button btnCam = findViewById(R.id.btnCamera);
        btnCam.setOnClickListener(this);
    }

    @Override
    public void onBackPressed()
    {

    }

    private void scanBarcode() {
        new IntentIntegrator(this).initiateScan();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if(result != null) {
            if(result.getContents() == null) {
                Toast.makeText(this, "Cancelled", Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(this, "Scanned: " + result.getContents(), Toast.LENGTH_LONG).show();
                final String tNum = result.getContents();
                if(isServiceOk())
                {

                    mAuth = FirebaseAuth.getInstance();
                    final boolean[] courier = new boolean[1];

                    final DatabaseReference DB = FirebaseDatabase.getInstance().getReference();
                    DB.child("users").child(mAuth.getCurrentUser().getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            courier[0] = (boolean) dataSnapshot.child("isCourier").getValue();
                            if(courier[0] == true)
                            {
                                DB.child("users").child(mAuth.getCurrentUser().getUid()).child("tracking").setValue(tNum);
                                startTrackerService();
                                finish();
                            }
                            else
                            {
                                Intent intent = new Intent(LoginActivity.this, MapActivity.class);
                                intent.putExtra("TRACKING", tNum);
                                startActivity(intent);
                            }
                        }
                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });
                }
            }
        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }

    private void logOut()
    {
        mAuth.getInstance().signOut();
        startActivity(new Intent(LoginActivity.this, MainActivity.class));
        finish();
    }

    @Override
    public void onClick(View v) {

        switch (v.getId())
        {
            case R.id.btnCamera:
                scanBarcode();
                break;

        }
    }


    private static final String TAG ="xxx";

    private static final int ERROR_DIALOG_REQUEST = 9001;

    public boolean isServiceOk(){
        Log.d(TAG, "isServiceOk: checking google service version");

        int available = GoogleApiAvailability.getInstance().isGooglePlayServicesAvailable(LoginActivity.this);

        if(available == ConnectionResult.SUCCESS){
            Log.d(TAG, "isServiceOk: Google Play Service is working");
            return true;
        }else if(GoogleApiAvailability.getInstance().isUserResolvableError(available)){
            Log.d(TAG, "isServiceOk: an error occured buw we can fix it");
            Dialog dialog = GoogleApiAvailability.getInstance().getErrorDialog(LoginActivity.this,available, ERROR_DIALOG_REQUEST);
            dialog.show();
        }else{
            Toast.makeText(this,"", Toast.LENGTH_SHORT).show();
        }
        return false;
    }

    private void startTrackerService()
    {
        Log.d(TAG, "Running...");
        startService(new Intent(this, TrackerService.class));

    }

}
