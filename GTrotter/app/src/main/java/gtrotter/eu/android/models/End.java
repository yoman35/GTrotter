package gtrotter.eu.android.models;

import java.util.HashMap;
import java.util.Map;

@SuppressWarnings("unused")
public class End {

    private String country;
    private String town;
    private String date;
    private String location;
    private Map<String, Object> additionalProperties = new HashMap<>();

    /**
     *
     * @return
     * The country
     */
    public String getCountry() {
        return country;
    }

    /**
     *
     * @param country
     * The country
     */
    public void setCountry(String country) {
        this.country = country;
    }

    /**
     *
     * @return
     * The town
     */
    public String getTown() {
        return town;
    }

    /**
     *
     * @param town
     * The town
     */
    public void setTown(String town) {
        this.town = town;
    }

    /**
     *
     * @return
     * The date
     */
    public String getDate() {
        return date;
    }

    /**
     *
     * @param date
     * The date
     */
    public void setDate(String date) {
        this.date = date;
    }

    /**
     *
     * @return
     * The location
     */
    public String getLocation() {
        return location;
    }

    /**
     *
     * @param location
     * The location
     */
    public void setLocation(String location) {
        this.location = location;
    }

    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }

}