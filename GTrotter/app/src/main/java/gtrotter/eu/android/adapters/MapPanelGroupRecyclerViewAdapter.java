package gtrotter.eu.android.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import gtrotter.eu.android.R;
import gtrotter.eu.android.models.Step;
import gtrotter.eu.android.models.Trip;
import gtrotter.eu.android.utilities.NestedLinearLayoutManager;
import gtrotter.eu.android.utilities.RecyclerItemClickListener;

public class MapPanelGroupRecyclerViewAdapter extends RecyclerView.Adapter<MapPanelGroupRecyclerViewAdapter.RVFragmentViewHolder> {

    private Context context;
    private RecyclerView recyclerView;
    private List<Trip> trips;

    public MapPanelGroupRecyclerViewAdapter(RecyclerView recyclerView, List<Trip> trips) {
        this.recyclerView = recyclerView;
        this.trips = trips;
    }

    @Override
    public RVFragmentViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        this.context = viewGroup.getContext();
        View v = LayoutInflater.from(this.context).inflate(R.layout.fragment_map_panel_group, viewGroup, false);
        final RVFragmentViewHolder vh = new RVFragmentViewHolder(v);
        this.recyclerView.addOnItemTouchListener(new RecyclerItemClickListener(this.context, new RecyclerItemClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.recycler_view_group_recycler_view);
                if (recyclerView.getVisibility() == View.GONE)
                    recyclerView.setVisibility(View.VISIBLE);
                else
                    recyclerView.setVisibility(View.GONE);
            }
        }));
        return vh;
    }

    public void onBindViewHolder(RVFragmentViewHolder vh, int pos) {
        vh.title.setText(this.trips.get(pos).getName());
        vh.recyclerView.setAdapter(new MapPanelItemRecyclerViewAdapter(getData(pos)));
        vh.recyclerView.setLayoutManager(new NestedLinearLayoutManager(this.context));
    }

    private List<Step> getData(int pos) {
        List<Step> steps = new ArrayList<>();
        steps.add(new Step(this.trips.get(pos).getStart()));
        //TODO: API -> make steps roads
        steps.add(new Step(this.trips.get(pos).getEnd()));
        return steps;
    }

    @Override
    public int getItemCount() {
        return this.trips.size();
    }

    class RVFragmentViewHolder extends RecyclerView.ViewHolder {

        TextView title;
        RecyclerView recyclerView;

        public RVFragmentViewHolder(View v) {
            super(v);
            title = (TextView) v.findViewById(R.id.recycler_view_group_title);
            recyclerView = (RecyclerView) v.findViewById(R.id.recycler_view_group_recycler_view);
        }
    }

}
