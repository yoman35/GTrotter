package gtrotter.eu.android.Requests;

import android.content.Context;
import android.content.SharedPreferences;

import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.google.gson.Gson;

import java.util.HashMap;
import java.util.Map;

import gtrotter.eu.android.R;
import gtrotter.eu.android.models.GTrotterRequest;

public class RqPostFacebook extends RqGTrotterAPIString {

    private String token;

    public RqPostFacebook(final Context context, String facebookToken) {
        super(context, Method.POST, "/user/facebook", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                // PARSING
                GTrotterRequest request = new Gson().fromJson(response, GTrotterRequest.class);
            }
        });
        this.token = facebookToken;
    }

    @Override
    protected Map<String, String> getParams() throws AuthFailureError {
        Map<String, String> map = new HashMap<>();
        map.put("token", this.token);
        return map;
    }
}
