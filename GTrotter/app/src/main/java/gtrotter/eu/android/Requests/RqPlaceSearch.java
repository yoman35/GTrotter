package gtrotter.eu.android.Requests;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

public class RqPlaceSearch extends StringRequest {
    public RqPlaceSearch(float latitude, float longitude, int radius, String type, String name, String key,
                         Response.Listener<String> listener, Response.ErrorListener errorListener) {
        super(Method.GET, "https://maps.googleapis.com/maps/api/place/nearbysearch/json?location=" +
               Float.toString(latitude) + "," + Float.toString(longitude) +
                "&raduius=" + Integer.toString(radius) +
                "&types=" + type + "&name=" + name +
                "&key=" + key, listener, errorListener);
    }
}