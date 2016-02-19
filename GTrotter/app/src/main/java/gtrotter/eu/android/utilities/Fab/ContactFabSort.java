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
public class ContactFabSort extends FloatingActionButton {

    private static final int ICON_FAV_ID = R.drawable.ic_favorite_white_36dp;
    private static final int ICON_ALP_ID = R.drawable.ic_sort_by_alpha_white_36dp;

    private Drawable mIconFav, mIconAlpha;

    private EContactFabSort mState = EContactFabSort.ALPHA;

    public ContactFabSort(Context context, AttributeSet attrs) {
        super(context, attrs);
        TypedArray a = context.getTheme().obtainStyledAttributes(
                attrs,
                R.styleable.FloatingActionButton,
                0, 0);
        try {
            int idIconFav = a.getInteger(R.styleable.ContactFabSort_icon_favorite, ICON_FAV_ID);
            int idIconAlpha = a.getInteger(R.styleable.ContactFabSort_icon_alpha, ICON_ALP_ID);
            mIconFav = ContextCompat.getDrawable(context, idIconFav);
            mIconAlpha = ContextCompat.getDrawable(context, idIconAlpha);
            this.setVisibility(View.GONE);
            this.setImageDrawable(mIconAlpha);
        } finally {
            a.recycle();
        }
    }

    public void setState(EContactFabSort state) {
        this.mState = state;
        if (this.mState == EContactFabSort.ALPHA)
            this.setImageDrawable(mIconAlpha);
        else
            this.setImageDrawable(mIconFav);
    }

    public EContactFabSort getState() {
        return this.mState;
    }

    public enum EContactFabSort {
        FAVORITE ("__FAVORITE__"),
        ALPHA ("__ALPHA__");
        private String mState;
        EContactFabSort(String state) {
            this.mState = state;
        }
        @Override
        public String toString() {
            return this.mState;
        }
    }
}
