package gtrotter.eu.android.Requests;

import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import com.android.volley.NoConnectionError;
import com.android.volley.Response;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import gtrotter.eu.android.R;
import gtrotter.eu.android.activities.LoginActivity;
import gtrotter.eu.android.activities.SplashScreenActivity;

public class RqGTrotterAPIString extends StringRequest {

    public RqGTrotterAPIString(final Context context, int method, String url, Response.Listener<String> listener) {
        super(method, context.getString(R.string.gtrotter_api_address) + url, listener, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        if (error instanceof NoConnectionError) {
                            Toast.makeText(context, "No internet", Toast.LENGTH_LONG).show();
                        } else if (error instanceof TimeoutError) {
                            Toast.makeText(context, "Server error", Toast.LENGTH_LONG).show();
                        } else if (error.networkResponse.data != null) {
                            String json;
                            json = new String(error.networkResponse.data);
                            String trimmed = VolleySingleton.getInstance(context).trimMessage(json, "message");
                            Toast.makeText(context, trimmed, Toast.LENGTH_LONG).show();
                        } else {
                            Toast.makeText(context, "Unknown error", Toast.LENGTH_LONG).show();
                        }
                        if (context instanceof SplashScreenActivity) {
                            context.startActivity(new Intent(context, LoginActivity.class));
                        }
                    }
                }

        );
    }
}
