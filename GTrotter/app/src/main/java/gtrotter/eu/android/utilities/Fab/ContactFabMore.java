package gtrotter.eu.android.utilities.Fab;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;

import gtrotter.eu.android.R;

public class ContactFabMore extends FloatingActionButton {
    private static final int ICON_MORE_ID = R.drawable.ic_add_white_36dp;
    private static final int ICON_SELECT_ID = R.drawable.ic_check_white_36dp;

    private Drawable mIconMore, mIconSelect;
    private EContactFabMore mState = EContactFabMore.CLASSIC;

    public ContactFabMore(Context context, AttributeSet attrs) {
        super(context, attrs);
        TypedArray a = context.getTheme().obtainStyledAttributes(
                attrs,
                R.styleable.FloatingActionButton,
                0, 0);
        try {
            int idMore = a.getInteger(R.styleable.ContactFabMore_icon_more, ICON_MORE_ID);
            int idSelect = a.getInteger(R.styleable.ContactFabMore_icon_select, ICON_SELECT_ID);
            this.mIconMore = ContextCompat.getDrawable(context, idMore);
            this.mIconSelect = ContextCompat.getDrawable(context, idSelect);
            setState(this.mState);
        } finally {
            a.recycle();
        }
    }

    public EContactFabMore getState() {
        return this.mState;
    }

    public void setState(EContactFabMore state) {
        this.mState = state;
        switch (this.mState) {
            case CLASSIC:
                this.setImageDrawable(this.mIconMore);
                break;
            case SELECT:
                this.setImageDrawable(this.mIconSelect);
                break;
            default:
                this.setImageDrawable(this.mIconMore);
                break;
        }
    }

    public enum EContactFabMore {
        CLASSIC("__CLASSIC__"),
        SELECT("__SELECT__");
        private String mState = "";

        EContactFabMore(String name) {
            this.mState = name;
        }

        @Override
        public String toString() {
            return this.mState;
        }
    }

}
