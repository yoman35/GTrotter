package gtrotter.eu.android.adapters;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.Response;
import com.android.volley.VolleyError;

import java.util.List;

import gtrotter.eu.android.R;
import gtrotter.eu.android.Requests.RqPlaceSearch;
import gtrotter.eu.android.Requests.VolleySingleton;
import gtrotter.eu.android.models.Filter;

public class FilterPanelAdapter extends BaseAdapter {

    private List<Filter> mFilters;

    public FilterPanelAdapter(List<Filter> filters) {
        this.mFilters = filters;
    }

    @Override
    public int getCount() {
        return this.mFilters.size();
    }

    @Override
    public Object getItem(int position) {
        return this.mFilters.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, final ViewGroup parent) {
        final Context context = parent.getContext();
        final ImageView icon;
        final Filter current = this.mFilters.get(position);
        final View parentView = parent.getRootView();
        final TextView description = (TextView) parentView.findViewById(R.id.filter_panel_description);
        if (convertView == null) {
            icon = new ImageView(context);
            int match_parent = ViewGroup.LayoutParams.MATCH_PARENT;
            icon.setLayoutParams(new GridView.LayoutParams(match_parent, match_parent));
            icon.setScaleType(ImageView.ScaleType.CENTER_CROP);
            icon.setPadding(8, 8, 8, 8);
            icon.setActivated(false);
            icon.setImageResource(current.getImageId());
            icon.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (!icon.isActivated()) {
                        //Filter become active
                        icon.setImageDrawable(ContextCompat.getDrawable(v.getContext(), current.getImageIdFocus()));
                        description.setTextColor(ContextCompat.getColor(context, R.color.green));
                        description.setText(current.getTitle());
                        icon.setActivated(true);
                        //TODO: replace with current location (Shared preferences saving)
                        String apiKey = context.getString(R.string.api_key_server);
                        RqPlaceSearch rq = new RqPlaceSearch(48.8534100f, 2.3488000f, 20000, current.getTypes(), "*", apiKey,
                                new Response.Listener<String>() {
                                    @Override
                                    public void onResponse(String response) {
                                        String name = "FILTER " + current.getTitle();
                                        Log.d(name, response);
                                    }
                                },
                                new Response.ErrorListener() {
                                    @Override
                                    public void onErrorResponse(VolleyError error) {
                                        String name = "FILTER " + current.getTitle();
                                        Log.d(name, "ERROR!");
                                    }
                                });
                        VolleySingleton.getInstance(context).getRequestQueue().add(rq);
                    } else {
                        //Filter no longer active
                        icon.setImageDrawable(ContextCompat.getDrawable(v.getContext(), current.getImageId()));
                        description.setTextColor(ContextCompat.getColor(context, R.color.red));
                        description.setText(current.getTitle());
                        icon.setActivated(false);
                    }
                }
            });
        } else
            icon = (ImageView) convertView;
        return icon;
    }

}
