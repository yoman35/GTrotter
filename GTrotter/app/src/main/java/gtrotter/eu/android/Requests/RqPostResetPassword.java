package gtrotter.eu.android.Requests;

import android.content.Context;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.google.gson.Gson;

import java.util.HashMap;
import java.util.Map;

import gtrotter.eu.android.models.GTrotterRequest;

public class RqPostResetPassword extends RqGTrotterAPIString {

    private String email;

    public RqPostResetPassword(final Context context, String email) {
        super(context, Method.POST, "/user/reset/password", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                // PARSING
                GTrotterRequest req = new Gson().fromJson(response, GTrotterRequest.class);
                // SAVING
                Toast.makeText(context, req.getRequest(), Toast.LENGTH_LONG).show();
            }
        });
        this.email = email;
    }

    @Override
    protected Map<String, String> getParams() throws AuthFailureError {
        Map<String, String> map = new HashMap<>();
        map.put("email", this.email);
        return map;
    }
}
