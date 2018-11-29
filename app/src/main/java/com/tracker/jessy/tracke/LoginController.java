package com.tracker.jessy.tracke;

import android.content.Intent;
import android.util.Log;
import android.view.View;

public class LoginController implements View.OnClickListener {
    @Override
    public void onClick(View v) {
        switch(v.getId())
        {
            case R.id.btnLogin:
                Intent logIntent = new Intent(v.getContext(), LoginActivity.class);
                v.getContext().startActivity(logIntent);
                break;
            case R.id.btnSignUp:
                Log.d("View_id", "Shatoo");
                break;
            default:
                Log.d("View_id", "Taena");
        }
    }
}
