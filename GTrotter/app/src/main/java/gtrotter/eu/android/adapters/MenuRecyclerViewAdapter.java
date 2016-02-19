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

public class MenuRecyclerViewAdapter extends RecyclerView.Adapter<MenuRecyclerViewAdapter.MyViewHolder> {
    private LayoutInflater _inflater;
    private List<ImageAndText> _data = Collections.emptyList();

    public MenuRecyclerViewAdapter(Context context, List<ImageAndText> data) {
        this._inflater = LayoutInflater.from(context);
        this._data = data;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = this._inflater.inflate(R.layout.fragment_menu_item, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        ImageAndText current = this._data.get(position);
        holder._title.setText(current.get_title());
        holder._image.setImageResource(current.get_iconId());
    }

    @Override
    public int getItemCount() {
        return this._data.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        private TextView _title;
        private ImageView _image;

        public MyViewHolder(View itemView) {
            super(itemView);
            this._title = (TextView) itemView.findViewById(R.id.recycler_view_row_title);
            this._image = (ImageView) itemView.findViewById(R.id.recycler_view_row_icon);
        }
    }

}
