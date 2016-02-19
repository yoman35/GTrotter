package gtrotter.eu.android.Requests;

import android.app.Activity;
import android.content.Intent;

import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.google.gson.Gson;

import java.util.HashMap;
import java.util.Map;

import gtrotter.eu.android.activities.MainActivity;
import gtrotter.eu.android.models.GTrotterRequest;
import gtrotter.eu.android.models.User;
import gtrotter.eu.android.utilities.MyApplication;

public class RqPostRegister extends RqGTrotterAPIString {

    private User user;

    public RqPostRegister(final Activity context, User user) {
        super(context, Method.POST, "/user", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                // PARSING
                GTrotterRequest req = new Gson().fromJson(response, GTrotterRequest.class);
                // SAVING
                if (req.getToken() == null)
                    MyApplication.getInstance().displayMessageLong(req.getMessage());
                else {
                    MyApplication.getInstance().putSP_Remember(true);
                    MyApplication.getInstance().putSP_FirstName(req.getUser().getFirstname());
                    MyApplication.getInstance().putSP_LastName(req.getUser().getLastname());
                    MyApplication.getInstance().putSP_Login(req.getUser().getLogin());
                    MyApplication.getInstance().putSP_Email(req.getUser().getEmail());
                    MyApplication.getInstance().putSP_Token(req.getToken());
                    context.startActivity(new Intent(context, MainActivity.class));
                }
            }
        });
        this.user = user;
    }

    @Override
    protected Map<String, String> getParams() throws AuthFailureError {
        Map<String, String> map = new HashMap<>();
        map.put("firstname", this.user.getFirstname());
        map.put("lastname", this.user.getLastname());
        map.put("login", this.user.getLogin());
        map.put("email", this.user.getEmail());
        map.put("password", this.user.getPassword());
        return map;
    }
}
