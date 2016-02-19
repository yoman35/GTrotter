package gtrotter.eu.android.Requests;

import android.content.Context;
import android.widget.Toast;

import com.android.volley.Response;

public class RqPostImage extends RqGTrotterAPIString {
    public RqPostImage(final Context context, String token) {
        super(context, Method.POST, "/home/?t=" + token, new Response.Listener<String>() {
            @Override
            public void onResponse(String s) {
                Toast.makeText(context, "OK", Toast.LENGTH_LONG).show();
            }
        });
    }
}
