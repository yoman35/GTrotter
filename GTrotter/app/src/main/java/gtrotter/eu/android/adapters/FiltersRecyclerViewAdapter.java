package gtrotter.eu.android.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Collections;
import java.util.List;

import gtrotter.eu.android.R;
import gtrotter.eu.android.models.Filter;

public class FiltersRecyclerViewAdapter extends RecyclerView.Adapter<FiltersRecyclerViewAdapter.FiltersRecyclerViewHolder> {

    private List<Filter> filters = Collections.emptyList();

    public FiltersRecyclerViewAdapter(final List<Filter> filters) {
        this.filters = filters;
    }

    @Override
    public FiltersRecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_map_filters_item, parent, false);
        return new FiltersRecyclerViewHolder(v);
    }

    @Override
    public void onBindViewHolder(FiltersRecyclerViewHolder holder, int position) {
        Filter current = this.filters.get(position);
        //holder.icon.setImageResource(current.getIdImage());
        holder.title.setText(current.getTitle());
    }

    @Override
    public int getItemCount() {
        return this.filters.size();
    }

    public static class FiltersRecyclerViewHolder extends RecyclerView.ViewHolder {

        private ImageView icon;
        private TextView title;

        public FiltersRecyclerViewHolder(View itemView) {
            super(itemView);
            icon = (ImageView) itemView.findViewById(R.id.filter_icon);
            title = (TextView) itemView.findViewById(R.id.filter_title);
        }
    }
}
