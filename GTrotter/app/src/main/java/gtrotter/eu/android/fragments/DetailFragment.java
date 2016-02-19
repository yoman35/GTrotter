package gtrotter.eu.android.fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import gtrotter.eu.android.R;
import gtrotter.eu.android.adapters.ProfileViewPagerAdapter;
import gtrotter.eu.android.slidingtab.SlidingTabLayout;


public class DetailFragment extends Fragment {

    private final static int HEADER = R.id.detail_fragment_header_container;

    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_detail, container, false);

        FragmentActivity activity = ((FragmentActivity) container.getContext());
        FragmentManager manager = activity.getSupportFragmentManager();
        HeaderDetailFragment header = new HeaderDetailFragment();
        manager.beginTransaction().replace(HEADER, header).commit();

        String titles[] = new String[]{
                getResources().getString(R.string.profile_tab_contact_title),
                getResources().getString(R.string.profile_tab_information_title)
        };
        ViewPager viewPager = (ViewPager) v.findViewById(R.id.fragment_detail_view_pager);
        viewPager.setAdapter(new ProfileViewPagerAdapter(manager, titles, 2));
        SlidingTabLayout slidingTabLayout = (SlidingTabLayout) v.findViewById(R.id.fragment_detail_sliding_tab_layout);
        slidingTabLayout.setDistributeEvenly(true);
        slidingTabLayout.setCustomTabColorize(new SlidingTabLayout.TabColorize() {
            @Override
            public int getIndicatorColor(int position) {
                return ContextCompat.getColor(container.getContext(), R.color.colorPrimary);
            }
        });
        slidingTabLayout.setViewPager(viewPager);
        return v;
    }
}
