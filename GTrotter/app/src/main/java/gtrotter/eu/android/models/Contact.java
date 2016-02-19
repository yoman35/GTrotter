package gtrotter.eu.android.models;

@SuppressWarnings("unused")
public class Contact {
    public Contact(String name, String phone, boolean favor, String avatar) {
        mName = name;
        mPhone = phone;
        mFavor = favor;
        mAvatar = avatar;
    }

    private String mName;
    private String mPhone;
    private boolean mFavor;
    private String mAvatar;

    public boolean ismSelected() {
        return mSelected;
    }

    public void setSelection(boolean mSelected) {
        this.mSelected = mSelected;
    }

    private boolean mSelected;

    public String getName() {
        return mName;
    }

    public void setName(String mName) {
        this.mName = mName;
    }

    public String getPhoneNumber() {
        return mPhone;
    }

    public void setPhoneNumber(String mPhone) {
        this.mPhone = mPhone;
    }

    public boolean ismFavor() {
        return mFavor;
    }

    public void setFavorite(boolean mFavor) {
        this.mFavor = mFavor;
    }

    public String getAvatar() {
        return mAvatar;
    }

    public void setAvatar(String mAvatar) {
        this.mAvatar = mAvatar;
    }
}
