package gtrotter.eu.android.Requests;

import android.content.Context;
import android.widget.Toast;

import com.android.volley.Response;

public class RqPutStep extends RqGTrotterAPIString {
    public RqPutStep(final Context context, String idUser, String token) {
        super(context, Method.PUT, "/trip/" + idUser + "/step/?t=" + token, new Response.Listener<String>() {
            @Override
            public void onResponse(String s) {
                Toast.makeText(context, "OK", Toast.LENGTH_LONG).show();
            }
        });
    }
}
