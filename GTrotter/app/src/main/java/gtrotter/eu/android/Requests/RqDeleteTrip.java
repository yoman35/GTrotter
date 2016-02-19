package gtrotter.eu.android.Requests;

import android.content.Context;
import android.widget.Toast;

import com.android.volley.Response;

public class RqDeleteTrip extends RqGTrotterAPIString {
    public RqDeleteTrip(final Context context, String idUser, String token) {
        super(context, Method.DELETE, "/trip/" + idUser + "?t=" + token, new Response.Listener<String>() {
            @Override
            public void onResponse(String s) {
                Toast.makeText(context, "OK", Toast.LENGTH_LONG).show();
            }
        });
    }
}
