package gtrotter.eu.android.Requests;

import android.content.Context;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Response;

import java.util.HashMap;
import java.util.Map;

import gtrotter.eu.android.models.User;

public class RqPutUser extends RqGTrotterAPIString {

    private User user;

    public RqPutUser(final Context context, String token, User user) {
        super(context, Method.PUT, "/user/edit/?t=" + token, new Response.Listener<String>() {
            @Override
            public void onResponse(String s) {
                Toast.makeText(context, "OK", Toast.LENGTH_LONG).show();
            }
        });
        this.user = user;
    }

    @Override
    protected Map<String, String> getParams() throws AuthFailureError {
        Map<String, String> map = new HashMap<>();
        map.put("firstname", user.getFirstname() );
        map.put("lastname", user.getLastname() );
        map.put("age", String.valueOf(user.getAge()));
        map.put("genre", user.getGenre() );
        map.put("email", user.getEmail() );
        map.put("location", user.getLocation() );
        map.put("langue", user.getLanguage() );
        map.put("humeur", user.getMood() );
        map.put("preference", user.getPreference() );
        map.put("restriction", user.getRestriction() );
        return map;
    }
}
