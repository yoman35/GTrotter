package gtrotter.eu.android.utilities.Fab;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.view.View;

import gtrotter.eu.android.R;

public class ContactFabDelete extends FloatingActionButton {

    private static final int ICON_DELETE_ID = R.drawable.ic_delete_white_36dp;
    private static final int ICON_UNDO_ID = R.drawable.ic_undo_white_36dp;

    private Drawable mIconDelete, mIconUndo;
    private EContactDelete mState = EContactDelete.CLASSIC;

    public ContactFabDelete(Context context, AttributeSet attrs) {
        super(context, attrs);
        TypedArray a = context.getTheme().obtainStyledAttributes(
                attrs,
                R.styleable.FloatingActionButton,
                0, 0);
        try {
            int idDelete = a.getInteger(R.styleable.ContactFabDelete_icon_delete, ICON_DELETE_ID);
            int idUndo = a.getInteger(R.styleable.ContactFabDelete_icon_undo, ICON_UNDO_ID);
            this.mIconDelete = ContextCompat.getDrawable(context, idDelete);
            this.mIconUndo = ContextCompat.getDrawable(context, idUndo);
            setState(this.mState);
            this.setVisibility(View.GONE);
        } finally {
            a.recycle();
        }
    }

    public EContactDelete getState() {
        return this.mState;
    }

    public void setState(EContactDelete state) {
        this.mState = state;
        switch (state) {
            case CLASSIC:
                this.setImageDrawable(this.mIconDelete);
                break;
            case UNDO:
                this.setImageDrawable(this.mIconUndo);
                break;
            default:
                this.setImageDrawable(this.mIconDelete);
                break;
        }
    }

    public enum EContactDelete {
        CLASSIC ("__CLASSIC__"),
        UNDO ("__UNDO__");
        private String mState = "";
        EContactDelete(String state) { this.mState = state; }
        @Override
        public String toString() {
            return this.mState;
        }
    }

}
