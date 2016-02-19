package gtrotter.eu.android.Requests;

import android.content.Context;
import android.widget.Toast;

import com.android.volley.Response;

public class RqPutTrip extends RqGTrotterAPIString {
    public RqPutTrip(final Context context, int method, String idUser, String token) {
        super(context, Method.PUT, "3000/trip/" + idUser + "/?t=" + token, new Response.Listener<String>() {
            @Override
            public void onResponse(String s) {
                Toast.makeText(context, "OK", Toast.LENGTH_LONG).show();
            }
        });
    }
}
