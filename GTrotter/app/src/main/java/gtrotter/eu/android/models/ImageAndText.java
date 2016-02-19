package gtrotter.eu.android.models;

@SuppressWarnings("unused")
public class ImageAndText {

    private Integer _iconId;
    private String _title;
    private String _data;

    public Integer get_iconId() {
        return _iconId;
    }

    public String get_title() {
        return _title;
    }

    public String get_data() { return _data; }

    public ImageAndText(Integer iconId, String title) {
        this._iconId = iconId;
        this._title = title;
    }

    public ImageAndText(Integer iconId, String title, String data) {
        this._iconId = iconId;
        this._title = title;
        this._data = data;
    }

}

