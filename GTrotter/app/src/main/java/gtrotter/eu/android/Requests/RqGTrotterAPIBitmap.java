package gtrotter.eu.android.Requests;

import android.content.Context;
import android.graphics.Bitmap;

import com.android.volley.Response;
import com.android.volley.toolbox.ImageRequest;
import gtrotter.eu.android.R;

public class RqGTrotterAPIBitmap extends ImageRequest {
    public RqGTrotterAPIBitmap(final Context context, String url, Response.Listener<Bitmap> listener, Response.ErrorListener errorListener) {
        super(context.getString(R.string.gtrotter_api_address) + url, listener, 0, 0, null, null, errorListener);
    }
}