package gtrotter.eu.android.utilities.Fab;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;

import gtrotter.eu.android.R;

public class GeoLocalisationFab extends FloatingActionButton {

    private static final int ICON_SHOW_ID = R.drawable.ic_my_location_white_36dp;
    private EGeoLocalisationFabState mState = EGeoLocalisationFabState.CLASSIC;

    public GeoLocalisationFab(Context context, AttributeSet attrs) {
        super(context, attrs);
        TypedArray a = context.getTheme().obtainStyledAttributes(
                attrs,
                R.styleable.FloatingActionButton,
                0, 0);
        try {
            int idShow = a.getInteger(R.styleable.GeoLocalisationFab_icon_geo, ICON_SHOW_ID);
            Drawable mIconShow = ContextCompat.getDrawable(context, idShow);
            this.setImageDrawable(mIconShow);
            setState(this.mState);
        } finally {
            a.recycle();
        }
    }

    public void setState(EGeoLocalisationFabState state) {
        this.mState = state;
    }

    public enum EGeoLocalisationFabState {
        CLASSIC("__CLASSIC__");
        private String mState;

        EGeoLocalisationFabState(String state) {
            this.mState = state;
        }

        @Override
        public String toString() {
            return this.mState;
        }
    }
}
