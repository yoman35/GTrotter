package gtrotter.eu.android.fragments;

import android.app.Fragment;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.LinearLayout;

import java.util.ArrayList;
import java.util.List;

import gtrotter.eu.android.R;
import gtrotter.eu.android.adapters.FilterPanelAdapter;
import gtrotter.eu.android.models.Filter;
import gtrotter.eu.android.utilities.Fab.FilterConstants;
import gtrotter.eu.android.utilities.Fab.FilterFab;
import gtrotter.eu.android.utilities.Fab.GeoLocalisationFab;
import gtrotter.eu.android.utilities.MyApplication;

public class FilterPanelFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_filter_panel, container, false);

        final LinearLayout panel = (LinearLayout) v.findViewById(R.id.filter_panel);
        final FilterFab fab = (FilterFab) v.findViewById(R.id.filter_fab);

        panel.setBackgroundColor(Color.argb(200, 62, 70, 76)); //#3E464C

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (panel.getVisibility()) {
                    case View.GONE:
                        panel.setVisibility(View.VISIBLE);
                        break;
                    case View.VISIBLE:
                        panel.setVisibility(View.GONE);
                        break;
                    default:
                        panel.setVisibility(View.GONE);
                        break;
                }
            }
        });

        GridView gridView = (GridView) v.findViewById(R.id.filter_panel_grid_view);
        gridView.setAdapter(new FilterPanelAdapter(getData()));

        return v;
    }

    private List<Filter> getData() {
        List<Filter> tmp = new ArrayList<>();
        Filter adm = new Filter(FilterConstants.FILTER_ADM, FilterConstants.FILTER_ADM_IMG, FilterConstants.FILTER_ADM_IMG_SELECT);
        adm.setTypes(MyApplication.ADM_TYPES);
        tmp.add(adm);
        Filter tra = new Filter(FilterConstants.FILTER_TRA, FilterConstants.FILTER_TRA_IMG, FilterConstants.FILTER_TRA_IMG_SELECT);
        tra.setTypes(MyApplication.TRA_TYPES);
        tmp.add(tra);
        Filter eme = new Filter(FilterConstants.FILTER_EME, FilterConstants.FILTER_EME_IMG, FilterConstants.FILTER_EME_IMG_SELECT);
        eme.setTypes(MyApplication.EME_TYPES);
        tmp.add(eme);
        Filter par = new Filter(FilterConstants.FILTER_PAR, FilterConstants.FILTER_PAR_IMG, FilterConstants.FILTER_PAR_IMG_SELECT);
        par.setTypes(MyApplication.PAR_TYPES);
        tmp.add(par);
        Filter vis = new Filter(FilterConstants.FILTER_VIS, FilterConstants.FILTER_VIS_IMG, FilterConstants.FILTER_VIS_IMG_SELECT);
        vis.setTypes(MyApplication.VIS_TYPES);
        tmp.add(vis);
        Filter fnd = new Filter(FilterConstants.FILTER_FND, FilterConstants.FILTER_FND_IMG, FilterConstants.FILTER_FND_IMG_SELECT);
        fnd.setTypes(MyApplication.FND_TYPES);
        tmp.add(fnd);
        Filter sho = new Filter(FilterConstants.FILTER_SHO, FilterConstants.FILTER_SHO_IMG, FilterConstants.FILTER_SHO_IMG_SELECT);
        sho.setTypes(MyApplication.SHO_TYPES);
        tmp.add(sho);
        Filter cit = new Filter(FilterConstants.FILTER_CIT, FilterConstants.FILTER_CIT_IMG, FilterConstants.FILTER_CIT_IMG_SELECT);
        cit.setTypes(MyApplication.CIT_TYPES);
        tmp.add(cit);
        Filter bnh = new Filter(FilterConstants.FILTER_BNH, FilterConstants.FILTER_BNH_IMG, FilterConstants.FILTER_BNH_IMG_SELECT);
        bnh.setTypes(MyApplication.BNH_TYPES);
        tmp.add(bnh);
        Filter sle = new Filter(FilterConstants.FILTER_SLE, FilterConstants.FILTER_SLE_IMG, FilterConstants.FILTER_SLE_IMG_SELECT);
        sle.setTypes(MyApplication.SLE_TYPES);
        tmp.add(sle);
        Filter use = new Filter(FilterConstants.FILTER_USE, FilterConstants.FILTER_USE_IMG, FilterConstants.FILTER_USE_IMG_SELECT);
        use.setTypes(MyApplication.USE_TYPES);
        tmp.add(use);
        Filter fav = new Filter(FilterConstants.FILTER_FAV, FilterConstants.FILTER_FAV_IMG, FilterConstants.FILTER_FAV_IMG_SELECT);
        fav.setTypes(MyApplication.FAV_TYPES);
        tmp.add(fav);
        return tmp;
    }

}
