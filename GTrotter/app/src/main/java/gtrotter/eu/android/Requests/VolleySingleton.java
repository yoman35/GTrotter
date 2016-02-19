package gtrotter.eu.android.Requests;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.LruCache;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class VolleySingleton {
    public static VolleySingleton instance_ = null;
    private RequestQueue requestQueue_;
    private ImageLoader imageLoader_;

    private VolleySingleton(Context context) {
        requestQueue_ = Volley.newRequestQueue(context);
        imageLoader_ = new ImageLoader(requestQueue_, new ImageLoader.ImageCache() {
            LruCache<String, Bitmap> cache = new LruCache<>((int) Runtime.getRuntime().maxMemory() / 1024 / 8);

            @Override
            public Bitmap getBitmap(String url) {
                return cache.get(url);
            }

            @Override
            public void putBitmap(String url, Bitmap bitmap) {
                cache.put(url, bitmap);
            }
        });
    }

    public static VolleySingleton getInstance(Context context) {
        if (instance_ == null) instance_ = new VolleySingleton(context);
        return instance_;
    }

    public RequestQueue getRequestQueue() {
        return requestQueue_;
    }

    public ImageLoader getImageLoader() {
        return imageLoader_;
    }

    public String trimMessage(String json, String key) {
        String trimmedString;
        try {
            JSONObject obj = new JSONObject(json);
            trimmedString = obj.getString(key);
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
        return trimmedString;
    }

}