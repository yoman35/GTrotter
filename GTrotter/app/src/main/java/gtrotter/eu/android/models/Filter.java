package gtrotter.eu.android.models;

@SuppressWarnings("unused")
public class Filter {

    public Filter(String title, int imageId, int imageIdFocus) {
        this.title = title;
        this.imageId = imageId;
        this.imageIdFocus = imageIdFocus;
    }

    private String title;
    private int imageId;
    private int imageIdFocus;

    public String getTypes() {
        return types;
    }

    public void setTypes(String types) {
        this.types = types;
    }

    private String types;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getImageId() {
        return imageId;
    }

    public void setImageId(int imageId) {
        this.imageId = imageId;
    }

    public int getImageIdFocus() {
        return imageIdFocus;
    }

    public void setImageIdFocus(int imageIdFocus) {
        this.imageIdFocus = imageIdFocus;
    }
}
