package gtrotter.eu.android.Requests;

import android.content.Context;
import android.widget.Toast;

import com.android.volley.Response;
import com.google.gson.Gson;

import gtrotter.eu.android.models.GTrotterRequest;

public class RqGetLogout extends RqGTrotterAPIString {
    public RqGetLogout(final Context context, String token) {
        super(context, Method.GET, "/user/logout?t=" + token, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Toast.makeText(context, response, Toast.LENGTH_LONG).show();
            }
        });
    }
}
