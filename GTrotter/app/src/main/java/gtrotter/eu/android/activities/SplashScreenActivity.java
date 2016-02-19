package gtrotter.eu.android.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import gtrotter.eu.android.R;
import gtrotter.eu.android.Requests.RqPostSignIn;
import gtrotter.eu.android.Requests.VolleySingleton;
import gtrotter.eu.android.utilities.MyApplication;

public class SplashScreenActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (MyApplication.getInstance().getSP_Remember()) {
            // If credentials were stored, get user's information
            setContentView(R.layout.activity_splash_screen);
            VolleySingleton.getInstance(this).getRequestQueue().add(new RqPostSignIn(this,
                    MyApplication.getInstance().getSP_Login(),
                    MyApplication.getInstance().getSP_Password()));
        } else {
            // If no credential was stored, launch LoginActivity
            startActivity(new Intent(this, LoginActivity.class));
            this.finish();
        }
    }

}
