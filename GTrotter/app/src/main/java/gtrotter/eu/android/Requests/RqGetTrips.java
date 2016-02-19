package gtrotter.eu.android.Requests;

import android.content.Context;
import android.util.Log;

import com.android.volley.Response;
import com.google.gson.Gson;

import gtrotter.eu.android.models.Trip;
import gtrotter.eu.android.models.TripsRequest;
import gtrotter.eu.android.utilities.MyApplication;

public class RqGetTrips extends RqGTrotterAPIString {

    private static final String LOG = "RQ_GET_TRIPS";

    public RqGetTrips(final Context context, String idUser, final String token) {
        super(context, Method.GET, "/trip/user/" + idUser + "?t=" + token, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d(LOG, LOG);
                Log.d(LOG, response);
                // PARSING
                TripsRequest req = new Gson().fromJson(response, TripsRequest.class);
                // SAVING
                int i = 0;
                for (Trip trip : req.getTrips())
                    MyApplication.getInstance().putSP_Trip(i++, new Gson().toJson(trip));

                RqGetPhotos rqGetPhotos = new RqGetPhotos(context, token);
                VolleySingleton.getInstance(context).getRequestQueue().add(rqGetPhotos);

            }
        });
    }

    public RqGetTrips(final Context context, String idUser, String token, Response.Listener<String> listener) {
        super(context, Method.GET, "/trip/user/" + idUser + "?t=" + token, listener);
    }
}