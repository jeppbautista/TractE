package com.tracker.jessy.tracke;

import android.support.annotation.NonNull;
import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

public class DBController implements ValueEventListener {

    @Override
    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
        Log.d("FIREBASE MSG", dataSnapshot.getChildren().toString());
    }

    @Override
    public void onCancelled(@NonNull DatabaseError databaseError) {
        Log.d("FIREBASE ERROR", databaseError.toException().toString());
    }
}

