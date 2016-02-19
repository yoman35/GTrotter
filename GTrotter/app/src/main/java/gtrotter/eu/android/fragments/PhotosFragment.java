package gtrotter.eu.android.fragments;

import android.app.Fragment;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.google.gson.Gson;
import com.veinhorn.scrollgalleryview.ScrollGalleryView;

import gtrotter.eu.android.R;
import gtrotter.eu.android.Requests.RqGTrotterAPIString;
import gtrotter.eu.android.Requests.RqGetPhoto;
import gtrotter.eu.android.Requests.VolleySingleton;
import gtrotter.eu.android.models.GTrotterRequest;
import gtrotter.eu.android.models.Image;
import gtrotter.eu.android.utilities.MyApplication;

public class PhotosFragment extends Fragment {

    public PhotosFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        MyApplication.getInstance().putSP_CurrentFragment(MyApplication.getInstance().F_PHOTOS);
        setHasOptionsMenu(true);
        final Context context = container.getContext();
        View v = inflater.inflate(R.layout.fragment_photos, container, false);

        FragmentManager manager = ((FragmentActivity) getActivity()).getSupportFragmentManager();
        final ScrollGalleryView gallery = (ScrollGalleryView) v.findViewById(R.id.scroll_gallery_view);
        gallery.setThumbnailSize(100)
                .setZoom(true)
                .setFragmentManager(manager);
        int method = Request.Method.GET;
        final String token = MyApplication.getInstance().getSP_Token();
        String url = "/home?t=" + token;
        VolleySingleton.getInstance(context).getRequestQueue().add(
                new RqGTrotterAPIString(context, method, url, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // PARSING
                        GTrotterRequest req = new Gson().fromJson(response, GTrotterRequest.class);
                        // SAVING
                        int i = 0;
                        for (Image image : req.getImages()) {
                            VolleySingleton.getInstance(context).getRequestQueue().add(new RqGetPhoto(context, image.getId(), token,
                                            new Response.Listener<Bitmap>() {
                                                @Override
                                                public void onResponse(Bitmap response) {
                                                    gallery.addImage(response);
                                                }
                                            },
                                            new Response.ErrorListener() {
                                                @Override
                                                public void onErrorResponse(VolleyError error) {
                                                    gallery.addImage(BitmapFactory.decodeResource(context.getResources(), R.drawable.no_image_thumb));
                                                }
                                            }));
                            MyApplication.getInstance().putSP_Image(i, new Gson().toJson(image));
                            i++;
                        }

                    }
                })
        );

        return v;
    }

}
