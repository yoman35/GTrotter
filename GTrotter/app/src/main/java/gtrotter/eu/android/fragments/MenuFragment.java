package gtrotter.eu.android.fragments;

import android.app.FragmentTransaction;
import android.content.Context;
import android.os.Bundle;
import android.app.Fragment;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.app.FragmentManager;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import gtrotter.eu.android.R;
import gtrotter.eu.android.adapters.MenuRecyclerViewAdapter;
import gtrotter.eu.android.models.ImageAndText;

public class MenuFragment extends Fragment {

    private ActionBarDrawerToggle mDrawerToggle;

    private DrawerLayout drawerLayout_;

    public MenuFragment() {}

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        //Get the view
        View layout_ = inflater.inflate(R.layout.fragment_menu, container, false);

        //Adding a recycler view (menu) + adapter + listener
        RecyclerView _recyclerView = (RecyclerView) layout_.findViewById(R.id.recycler_view_navigation_drawer);
        MenuRecyclerViewAdapter adapter = new MenuRecyclerViewAdapter(getActivity(), getData());
        _recyclerView.setAdapter(adapter);
        _recyclerView.addOnItemTouchListener(new NavigationDrawerOnItemTouchListener(getActivity(),
                new IClickListener() {
                    @Override
                    public void onClick(View v, int position) {
                        FragmentManager fm = getFragmentManager();
                        FragmentTransaction ft = fm.beginTransaction();
                        switch (((TextView) v.findViewById(R.id.recycler_view_row_title)).getText().toString()) {
                            case "Map":
                                MyMapFragment myMapFragment = new MyMapFragment();
                                ft.replace(R.id.main_container, myMapFragment);
                                break;
                            case "Profile":
                                ProfileFragment notebookFragment = new ProfileFragment();
                                ft.replace(R.id.main_container, notebookFragment);
                                break;
                            case "Photos":
                                PhotosFragment photosFragment = new PhotosFragment();
                                ft.replace(R.id.main_container, photosFragment);
                                break;
                            case "What about GTrotter?":
                                AboutFragment aboutFragment = new AboutFragment();
                                ft.replace(R.id.main_container, aboutFragment);
                                break;
                            default:
                                AboutFragment inProgress = new AboutFragment();
                                ft.replace(R.id.main_container, inProgress);
                                break;

                        }
                        ft.addToBackStack(null);
                        ft.commit();
                        drawerLayout_.closeDrawers();
                    }
                }

        ));
        _recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        return layout_;
    }

    public void setUp(DrawerLayout drawerLayout, final Toolbar toolbar) {
        mDrawerToggle = new ActionBarDrawerToggle(getActivity(), drawerLayout, toolbar, R.string.navigation_open, R.string.navigation_close) {
            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                getActivity().invalidateOptionsMenu();
            }

            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
                getActivity().invalidateOptionsMenu();
            }

            @Override
            public void onDrawerSlide(View drawerView, float slideOffset) {
                if (slideOffset < 0.6) {
                    toolbar.setAlpha(1 - slideOffset);
                }
            }
        };

        drawerLayout.setDrawerListener(mDrawerToggle);
        drawerLayout.post(new Runnable() {
            @Override
            public void run() {
                mDrawerToggle.syncState();
            }
        });
        drawerLayout_ = drawerLayout;
    }

    public List<ImageAndText> getData() {
        List<ImageAndText> data = new ArrayList<>();

        data.add(new ImageAndText(R.drawable.ic_map_icon, getString(R.string.navigation_drawer_map)));
        data.add(new ImageAndText(R.drawable.ic_trip, getString(R.string.navigation_drawer_book)));
        data.add(new ImageAndText(R.drawable.ic_photo_icon, getString(R.string.navigation_drawer_photos)));
        data.add(new ImageAndText(R.drawable.ic_plus_icon, getString(R.string.navigation_drawer_about)));

        return data;
    }

    static class NavigationDrawerOnItemTouchListener implements RecyclerView.OnItemTouchListener {
        private GestureDetector _gestureDetector;
        private IClickListener _clickListener;

        public NavigationDrawerOnItemTouchListener(Context context, final IClickListener clickListener) {

            this._clickListener = clickListener;
            this._gestureDetector = new GestureDetector(context, new GestureDetector.SimpleOnGestureListener() {
                @Override
                public boolean onSingleTapUp(MotionEvent e) {
                    return true;
                }
            });
        }

        @Override
        public boolean onInterceptTouchEvent(RecyclerView rv, MotionEvent e) {
            View child = rv.findChildViewUnder(e.getX(), e.getY());

            if (child != null && _clickListener != null && this._gestureDetector.onTouchEvent(e)) {
                this._clickListener.onClick(child, rv.getChildAdapterPosition(child));
            }
            return false;
        }

        @Override
        public void onTouchEvent(RecyclerView rv, MotionEvent e) {
        }

        @Override
        public void onRequestDisallowInterceptTouchEvent(boolean b) {

        }
    }

    public interface IClickListener {
        void onClick(View v, int position);
    }

}