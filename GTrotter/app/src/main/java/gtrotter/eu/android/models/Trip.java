package gtrotter.eu.android.models;

import java.util.HashMap;
import java.util.Map;

@SuppressWarnings("unused")
public class Trip {

    private String _id;
    private String name;
    private Start start;
    private End end;
    private String privacy;
    private String authorId;
    private Map<String, Object> additionalProperties = new HashMap<>();

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
     * The name
     */
    public String getName() {
        return name;
    }

    /**
     *
     * @param name
     * The name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     *
     * @return
     * The start
     */
    public Start getStart() {
        return start;
    }

    /**
     *
     * @param start
     * The start
     */
    public void setStart(Start start) {
        this.start = start;
    }

    /**
     *
     * @return
     * The end
     */
    public End getEnd() {
        return end;
    }

    /**
     *
     * @param end
     * The end
     */
    public void setEnd(End end) {
        this.end = end;
    }

    /**
     *
     * @return
     * The privacy
     */
    public String getPrivacy() {
        return privacy;
    }

    /**
     *
     * @param privacy
     * The privacy
     */
    public void setPrivacy(String privacy) {
        this.privacy = privacy;
    }

    /**
     *
     * @return
     * The authorId
     */
    public String getAuthorId() {
        return authorId;
    }

    /**
     *
     * @param authorId
     * The author_id
     */
    public void setAuthorId(String authorId) {
        this.authorId = authorId;
    }

    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }

}