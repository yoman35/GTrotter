package gtrotter.eu.android.Requests;

import android.content.Context;
import android.content.SharedPreferences;

import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.google.gson.Gson;

import java.util.HashMap;
import java.util.Map;

import gtrotter.eu.android.models.GTrotterRequest;

public class RqPostGooglePlus extends RqGTrotterAPIString {

    private String token;

    public RqPostGooglePlus(final Context context, String googleToken) {
        super(context, Method.POST, "/user/google", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                // PARSING
                GTrotterRequest req = new Gson().fromJson(response, GTrotterRequest.class);
            }
        });
        this.token = googleToken;
    }

    @Override
    protected Map<String, String> getParams() throws AuthFailureError {
        Map<String, String> map = new HashMap<>();
        map.put("token", this.token);
        return map;
    }
}
