package gtrotter.eu.android.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Collections;
import java.util.List;

import gtrotter.eu.android.models.ImageAndText;
import gtrotter.eu.android.R;


public class ProfileInfoRecyclerViewAdapter extends RecyclerView.Adapter<ProfileInfoRecyclerViewAdapter.ProfileInfoRecyclerViewHolder> {

    private List<ImageAndText> entries = Collections.emptyList();

    public ProfileInfoRecyclerViewAdapter(List<ImageAndText> data) {
        this.entries = data;
    }

    @Override
    public ProfileInfoRecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        View v = LayoutInflater.from(context).inflate(R.layout.fragment_profile_tab_profile_info_item, parent, false);
        return new ProfileInfoRecyclerViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ProfileInfoRecyclerViewHolder holder, int position) {
        ImageAndText current = this.entries.get(position);
        holder.imageView.setImageResource(current.get_iconId());
        holder.InfoTextView.setText(current.get_title());
        if (!current.get_data().equals(""))
            holder.dataTextView.setText(current.get_data());
    }

    @Override
    public int getItemCount() {
        return entries.size();
    }

    public static class ProfileInfoRecyclerViewHolder extends RecyclerView.ViewHolder {

        private ImageView imageView;
        private TextView InfoTextView, dataTextView;

        public ProfileInfoRecyclerViewHolder(View itemView) {
            super(itemView);
            this.imageView = (ImageView) itemView.findViewById(R.id.recycler_view_row_icon);
            this.InfoTextView = (TextView) itemView.findViewById(R.id.recycler_view_row_title);
            this.dataTextView = (TextView) itemView.findViewById(R.id.recycler_view_row_data);
        }
    }
}