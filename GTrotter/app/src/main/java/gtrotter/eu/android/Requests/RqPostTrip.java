package gtrotter.eu.android.Requests;

import android.content.Context;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.google.gson.Gson;

import java.util.HashMap;
import java.util.Map;

import gtrotter.eu.android.models.GTrotterRequest;
import gtrotter.eu.android.models.Trip;
import gtrotter.eu.android.utilities.MyApplication;

public class RqPostTrip extends RqGTrotterAPIString {

    private Trip trip;

    public RqPostTrip(final Context context, String token, Trip trip) {
        super(context, Method.POST, "/trip?t=" + token, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                GTrotterRequest req = new Gson().fromJson(response, GTrotterRequest.class);
                Toast.makeText(context, req.getRequest(), Toast.LENGTH_LONG).show();
            }
        });
        this.trip = trip;
    }

    @Override
    protected Map<String, String> getParams() throws AuthFailureError {
        Map<String, String> map = new HashMap<>();
        map.put("name", this.trip.getName());
        map.put("start_country", this.trip.getStart().getCountry());
        map.put("start_town", this.trip.getStart().getTown());
        map.put("start_date", this.trip.getStart().getDate());
        map.put("start_location", this.trip.getStart().getLocation());
        map.put("end_country", this.trip.getEnd().getCountry());
        map.put("end_town", this.trip.getEnd().getTown());
        map.put("end_date", this.trip.getEnd().getDate());
        map.put("end_location", this.trip.getEnd().getLocation());
        map.put("privacy", this.trip.getPrivacy());
        map.put("destination", this.trip.getEnd().getCountry());
        map.put("style", "0");
        map.put("traveler", MyApplication.getInstance().getSP_Id());
        return map;
    }
}
