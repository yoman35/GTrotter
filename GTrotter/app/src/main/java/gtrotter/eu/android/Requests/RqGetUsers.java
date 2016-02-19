package gtrotter.eu.android.Requests;

import android.content.Context;

import com.android.volley.Response;
import com.google.gson.Gson;

import java.util.Collections;
import java.util.List;

import gtrotter.eu.android.models.GTrotterRequest;
import gtrotter.eu.android.models.User;

public class RqGetUsers extends RqGTrotterAPIString {

    private static List<User> users = Collections.emptyList();

    public RqGetUsers(Context context, String name, String token) {
        super(context, Method.GET, "/user/search/" + name + "?t=" + token, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                // PARSING
                GTrotterRequest req = new Gson().fromJson(response, GTrotterRequest.class);
                // SAVING
                users = req.getUsers();
            }
        });
    }

    public static List<User> getUsers() {
        return users;
    }

}
