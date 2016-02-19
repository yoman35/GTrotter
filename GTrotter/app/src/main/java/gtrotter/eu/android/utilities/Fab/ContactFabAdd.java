package gtrotter.eu.android.utilities.Fab;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.view.View;

import gtrotter.eu.android.R;

@SuppressWarnings("unused")
public class ContactFabAdd extends FloatingActionButton {
    private static final int ICON_ADD_ID = R.drawable.ic_add_white_36dp;

    private EContactFabAdd mState = EContactFabAdd.CLASSIC;
    private Drawable mIconAdd;

    public ContactFabAdd(Context context, AttributeSet attrs) {
        super(context, attrs);
        TypedArray a = context.getTheme().obtainStyledAttributes(
                attrs,
                R.styleable.FloatingActionButton,
                0, 0);
        try {
            int id = a.getInteger(R.styleable.ContactFabAdd_icon_add, ICON_ADD_ID);
            this.mIconAdd = ContextCompat.getDrawable(context, id);
            setState(this.mState);
            this.setVisibility(View.GONE);
        } finally {
            a.recycle();
        }
    }

    public void setState(EContactFabAdd state) {
        switch (state) {
            case CLASSIC:
                this.setImageDrawable(this.mIconAdd);
                break;
            default:
                this.setImageDrawable(this.mIconAdd);
                break;
        }
    }

    public EContactFabAdd getState() {
        return this.mState;
    }

    public enum EContactFabAdd {
        CLASSIC ("__CLASSIC__");
        private String mState;
        EContactFabAdd(String state) { this.mState = state; }
        @Override public String toString() { return this.mState; }
    }

}