package gtrotter.eu.android.Requests;

import android.content.Context;
import android.content.SharedPreferences;

import com.android.volley.Response;
import com.google.gson.Gson;

import gtrotter.eu.android.R;
import gtrotter.eu.android.models.GTrotterRequest;
import gtrotter.eu.android.utilities.MyApplication;

public class RqPostAvatar extends RqGTrotterAPIString {
    public RqPostAvatar(final Context context, String idUser, String token) {
        super(context, Method.POST, "/user/edit/avatar/" + idUser + "?t=" + token, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                // PARSING
                GTrotterRequest req = new Gson().fromJson(response, GTrotterRequest.class);
                // SAVING
                MyApplication.getInstance().putSP_Avatar(req.getUser().getAvatar());
            }
        });
    }
}
