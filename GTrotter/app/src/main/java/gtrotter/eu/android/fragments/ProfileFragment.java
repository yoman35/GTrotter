package gtrotter.eu.android.fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import gtrotter.eu.android.R;
import gtrotter.eu.android.utilities.MyApplication;

public class ProfileFragment extends Fragment {

    public ProfileFragment() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        MyApplication.getInstance().putSP_CurrentFragment(MyApplication.getInstance().F_PROFILE);
        View v = inflater.inflate(R.layout.fragment_profile, container, false);

        DetailFragment fragment = new DetailFragment();
        getFragmentManager().beginTransaction().replace(R.id.fragment_profile_container, fragment).commit();

        return v;
    }
}
