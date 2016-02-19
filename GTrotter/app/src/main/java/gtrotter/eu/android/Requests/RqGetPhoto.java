package gtrotter.eu.android.Requests;

import android.content.Context;
import android.graphics.Bitmap;
import android.widget.Toast;

import com.android.volley.Response;

public class RqGetPhoto extends RqGTrotterAPIBitmap {
    public RqGetPhoto(final Context context, String idImage, String token, Response.Listener<Bitmap> listener, Response.ErrorListener errorListener) {
        super(context, "/home/" + idImage + "?t=" + token, listener, errorListener);
    }
}