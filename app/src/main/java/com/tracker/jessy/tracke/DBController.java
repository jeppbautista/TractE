package com.tracker.jessy.tracke;

import android.support.annotation.NonNull;
import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.tracker.jessy.tracke.utils.PrintUtils;
//import com.tracker.jessy.tracke.utils.PrintUtils;

import java.util.ArrayList;
import java.util.Arrays;
//import java.util.concurrent.CountDownLatch;

public class DBController implements ValueEventListener {

    private DatabaseReference DB;
    private User user;
    private static ArrayList<User> users;

    public DBController()
    {
        DB = FirebaseDatabase.getInstance().getReference();
        DB.addValueEventListener(this);
        DB.push();
    }
//
//    private ArrayList getList()
//    {
//        return users;
//    }

    @Override
    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

//        CountDownLatch done = new CountDownLatch(1);
//
//        this.users = new ArrayList<User>();
//
//        for (DataSnapshot d : dataSnapshot.getChildren())
//        {
//            this.users.add(new User(d.child("username").getValue().toString(), d.child("password").getValue().toString(), (boolean)d.child("isCourier").getValue()));
//
//        }
//
//        try {
//            done.await();
//        } catch (InterruptedException e) {
//            PrintUtils.print(e.toString());
//        }


    }

    @Override
    public void onCancelled(@NonNull DatabaseError databaseError) {
        Log.e("FIREBASE ERROR", databaseError.toException().toString());
    }

//    public void printAllData()
//    {
//        DB.push();
//    }
//
    public ArrayList<String> retreiveUsername()
    {
        DatabaseReference temp = FirebaseDatabase.getInstance().getReference();
        temp.addValueEventListener(this);
        temp.push();

        PrintUtils.print(this.users.toString());
//        ArrayList<String> usernames = new ArrayList<>();
//
//        for (User us : users)
//        {
//            usernames.add(us.getUsername());
//        }

        return new ArrayList<String>(Arrays.asList(new String[]{"foo"}));
    }
}

