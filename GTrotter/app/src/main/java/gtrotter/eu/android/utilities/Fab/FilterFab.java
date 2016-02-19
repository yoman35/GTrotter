package gtrotter.eu.android.utilities.Fab;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;

import gtrotter.eu.android.R;

public class FilterFab extends FloatingActionButton {

    private static final int ICON_SHOW_ID = R.drawable.ic_layers_white_36dp;

    private EFilterFabShow mState = EFilterFabShow.CLASSIC;

    public FilterFab(Context context, AttributeSet attrs) {
        super(context, attrs);
        TypedArray a = context.getTheme().obtainStyledAttributes(
                attrs,
                R.styleable.FloatingActionButton,
                0, 0);
        try {
            int idShow = a.getInteger(R.styleable.FilterFab_icon_show, ICON_SHOW_ID);
            Drawable mIconShow = ContextCompat.getDrawable(context, idShow);
            this.setImageDrawable(mIconShow);
            setState(this.mState);
        } finally {
            a.recycle();
        }
    }

    public void setState(EFilterFabShow state) {
        this.mState = state;
    }


    @SuppressWarnings("unused")
    public EFilterFabShow getState() {
        return this.mState;
    }

    public enum EFilterFabShow {
        CLASSIC("__CLASSIC__");
        private String mState;

        EFilterFabShow(String state) {
            this.mState = state;
        }

        @Override
        public String toString() {
            return this.mState;
        }
    }
}
