package gtrotter.eu.android.Requests;

import android.app.Activity;
import android.util.Log;
import android.widget.CheckBox;

import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.google.gson.Gson;

import java.util.HashMap;
import java.util.Map;

import gtrotter.eu.android.R;
import gtrotter.eu.android.activities.LoginActivity;
import gtrotter.eu.android.models.GTrotterRequest;
import gtrotter.eu.android.models.User;
import gtrotter.eu.android.utilities.MyApplication;

public class RqPostSignIn extends RqGTrotterAPIString {

    private static final String LOG = "RQ_POST_SIGN_IN";

    private String login, password;

    public RqPostSignIn(final Activity context, String login, String password) {
        super(context, Method.POST, "/user/login", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d(LOG, LOG);
                // PARSING
                GTrotterRequest req = new Gson().fromJson(response, GTrotterRequest.class);
                // SAVING
                // If we are on the LoginActivity
                if (context instanceof LoginActivity) {
                    if (((CheckBox) context.findViewById(R.id.cb_remember_me)).isChecked())
                        MyApplication.getInstance().putSP_Remember(true);
                    else
                        MyApplication.getInstance().putSP_Remember(false);
                }

                User dude = req.getUser();
                String id = dude.get_id();
                String firstname = dude.getFirstname();
                String lastname = dude.getLastname();
                String token = req.getToken();
                String login = dude.getLocal().getLogin();
                String password = dude.getLocal().getPassword();
                String email = dude.getEmail();
                boolean confirmed = Boolean.valueOf(dude.getConfirmed());
                String avatar = dude.getAvatar();
                MyApplication.getInstance().putSP_Id(id);
                MyApplication.getInstance().putSP_FirstName(firstname);
                MyApplication.getInstance().putSP_LastName(lastname);
                MyApplication.getInstance().putSP_Login(login);
                MyApplication.getInstance().putSP_Password(password);
                MyApplication.getInstance().putSP_Email(email);
                MyApplication.getInstance().putSP_Confirmed(confirmed);
                MyApplication.getInstance().putSP_Avatar(avatar);
                MyApplication.getInstance().putSP_Token(token);
                RqGetTrips tripsRequest = new RqGetTrips(context, id, token);
                VolleySingleton.getInstance(context).getRequestQueue().add(tripsRequest);
            }
        });
        this.login = login;
        this.password = password;
    }

    @Override
    protected Map<String, String> getParams() throws AuthFailureError {
        Map<String, String> map = new HashMap<>();
        map.put("login", this.login);
        map.put("password", this.password);
        return map;
    }
}
