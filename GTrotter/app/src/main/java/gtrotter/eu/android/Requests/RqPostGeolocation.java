package gtrotter.eu.android.Requests;

import android.app.Activity;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

public class RqPostGeolocation extends StringRequest {

    private static final String LOG = "RQ_POST_GEOLOCATION";

    public RqPostGeolocation(final Activity context, String key, Response.Listener<String> res, Response.ErrorListener err) {
        super(Method.POST, "https://www.googleapis.com/geolocation/v1/geolocate?key=" + key, res, err);
    }
}
