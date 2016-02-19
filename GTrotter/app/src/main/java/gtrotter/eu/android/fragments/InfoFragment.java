package gtrotter.eu.android.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import gtrotter.eu.android.R;
import gtrotter.eu.android.utilities.MyApplication;

/**
 * A simple {@link Fragment} subclass.
 */
public class InfoFragment extends Fragment {


    public InfoFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_info, container, false);

        TextView name = (TextView) v.findViewById(R.id.user_name);
        TextView email = (TextView) v.findViewById(R.id.user_email);
        TextView tvNbTrips = (TextView) v.findViewById(R.id.user_nb_trips);
        TextView login = (TextView) v.findViewById(R.id.user_login);
        String nbTrips = String.valueOf(MyApplication.getInstance().getTrips().size());
        String n = MyApplication.getInstance().getSP_FirstName() + " " + MyApplication.getInstance().getSP_LastName();
        String l = MyApplication.getInstance().getSP_Login();
        name.setText(name.getText() + " " + n);
        email.setText(email.getText() + " " + MyApplication.getInstance().getSP_Email());
        tvNbTrips.setText(tvNbTrips.getText() + " " + nbTrips);
        login.setText(login.getText() + " " + MyApplication.getInstance().getSP_Login());
        return v;
    }

}
