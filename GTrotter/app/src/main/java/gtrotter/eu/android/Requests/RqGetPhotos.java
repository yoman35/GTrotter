package gtrotter.eu.android.Requests;

import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.android.volley.Response;
import com.google.gson.Gson;

import gtrotter.eu.android.activities.LoginActivity;
import gtrotter.eu.android.activities.MainActivity;
import gtrotter.eu.android.activities.SplashScreenActivity;
import gtrotter.eu.android.models.GTrotterRequest;
import gtrotter.eu.android.models.Image;
import gtrotter.eu.android.utilities.MyApplication;

public class RqGetPhotos extends RqGTrotterAPIString {
    private static final String LOG = "RQ_GET_PHOTOS";

    public RqGetPhotos(final Context context, final String token) {
        super(context, Method.GET, "/home?t=" + token, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d(LOG, LOG);
                // PARSING
                GTrotterRequest req = new Gson().fromJson(response, GTrotterRequest.class);
                // SAVING
                int i = 0;
                for (Image image : req.getImages()) {
                    Log.d(LOG, image.getId());
                    MyApplication.getInstance().putSP_Image(i, new Gson().toJson(image));
                    i++;
                }
                if (context instanceof SplashScreenActivity) {
                    ((SplashScreenActivity) context).finish();
                } else if (context instanceof LoginActivity) {
                    ((LoginActivity) context).finish();
                }
                context.startActivity(new Intent(context, MainActivity.class));
            }
        });
    }
}
