package com.tracker.jessy.tracke;

import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;

public class LoginController implements View.OnClickListener {


    private DatabaseReference mDatabase;
    private String foo;

    public void onClick(View v) {
        switch(v.getId())
        {
            case R.id.btnLogin:

                if (acceptCredentials(R.id.txtUser, R.id.txtPassword))
                {
                    Intent logIntent = new Intent(v.getContext(), LoginActivity.class);
                    v.getContext().startActivity(logIntent);
                }
                else
                {
                    Toast.makeText(v.getContext(),"Invalid credentials", Toast.LENGTH_LONG).show();
                }


                break;
            case R.id.btnSignUp:
                Log.d("View_id", "Shatoo");
                break;
            default:
                Log.d("View_id", "Taena");
        }
    }

    private boolean acceptCredentials(int txtUser, int txtPassword)
    {
        DBController db = new DBController();
        //db.retreiveUsername();
//        if(db.retreiveUsername().contains(txtUser))
//        {
//            PrintUtils.print("Contains");
//        }
//        else
//        {
//            PrintUtils.print("Not");
//        }
        return false;
    }
}
