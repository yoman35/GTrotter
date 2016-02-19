package gtrotter.eu.android.models;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@SuppressWarnings("unused")
public class TripsRequest {

    private String request;
    private List<Trip> trips = new ArrayList<>();
    private Map<String, Object> additionalProperties = new HashMap<>();

    /**
     *
     * @return
     * The request
     */
    public String getRequest() {
        return request;
    }

    /**
     *
     * @param request
     * The request
     */
    public void setRequest(String request) {
        this.request = request;
    }

    /**
     *
     * @return
     * The trips
     */
    public List<Trip> getTrips() {
        return trips;
    }

    /**
     *
     * @param trips
     * The trips
     */
    public void setTrips(List<Trip> trips) {
        this.trips = trips;
    }

    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }

}