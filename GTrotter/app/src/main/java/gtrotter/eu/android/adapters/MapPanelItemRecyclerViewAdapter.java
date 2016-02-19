package gtrotter.eu.android.adapters;

import android.graphics.drawable.Drawable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import gtrotter.eu.android.R;
import gtrotter.eu.android.models.Step;

public class MapPanelItemRecyclerViewAdapter extends RecyclerView.Adapter<MapPanelItemRecyclerViewAdapter.RVTripViewHolder> {

    public List<Step> steps;

    public MapPanelItemRecyclerViewAdapter(List<Step> steps) {
        this.steps = steps;
    }

    @Override
    public RVTripViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.fragment_map_panel_item, viewGroup, false);
        return new RVTripViewHolder(v);
    }

    @Override
    public void onBindViewHolder(RVTripViewHolder vh, int pos) {
        Step current = this.steps.get(pos);
        vh.title.setText(current.getLocation());
    }
    @Override
    public int getItemCount() {
        return this.steps.size();
    }

    public static class RVTripViewHolder extends RecyclerView.ViewHolder {
        TextView title;

        public RVTripViewHolder(View v) {
            super(v);
            title = (TextView) v.findViewById(R.id.recycler_view_item_title);
            Drawable drawable = v.findViewById(R.id.step_info).getBackground();
            drawable.setAlpha(153);
        }
    }
}