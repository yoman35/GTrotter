package gtrotter.eu.android.models;

import java.util.HashMap;
import java.util.Map;

@SuppressWarnings("unused")
public class Image {

    private String _id;
    private String id_owner;
    private String address;
    private Map<String, Object> additionalProperties = new HashMap<>();

    public Boolean getSelected() {
        return selected;
    }

    public void setSelected(Boolean selected) {
        this.selected = selected;
    }

    private Boolean selected = false;

    /**
     *
     * @return
     * The Id
     */
    public String getId() {
        return _id;
    }

    /**
     *
     * @param Id
     * The _id
     */
    public void setId(String Id) {
        this._id = Id;
    }

    /**
     *
     * @return
     * The idOwner
     */
    public String getIdOwner() {
        return id_owner;
    }

    /**
     *
     * @param idOwner
     * The id_owner
     */
    public void setIdOwner(String idOwner) {
        this.id_owner = idOwner;
    }

    /**
     *
     * @return
     * The address
     */
    public String getAddress() {
        return address;
    }

    /**
     *
     * @param address
     * The address
     */
    public void setAddress(String address) {
        this.address = address;
    }

    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }

}