package gtrotter.eu.android.fragments;

import android.app.Fragment;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.android.volley.Response;

import java.util.List;

import gtrotter.eu.android.R;
import gtrotter.eu.android.Requests.RqGetTrips;
import gtrotter.eu.android.Requests.RqPostTrip;
import gtrotter.eu.android.Requests.VolleySingleton;
import gtrotter.eu.android.adapters.MapPanelGroupRecyclerViewAdapter;
import gtrotter.eu.android.models.End;
import gtrotter.eu.android.models.Start;
import gtrotter.eu.android.models.Trip;
import gtrotter.eu.android.utilities.MyApplication;

public class MapPanelFragment extends Fragment {

    private final static int idContainer = R.id.add_trip_container,
            idImageCross = R.id.fragment_map_panel_add_trip,
            idDrawablePlus = R.drawable.ic_add_circle_outline_white_36dp,
            idDrawableCancel = R.drawable.ic_del_circle_outline_white_36dp;

    private View mView;
    private RecyclerView mRecyclerView;
    private RelativeLayout mAddTripLayout;
    private ImageView mCross;
    private EditText mAddTitle,
            mFromPlace, mFromDate,
            mEndPlace, mEndDate;
    private CheckBox mPrivate;
    private Button mSave;

    public MapPanelFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.fragment_map_panel, container, false);

        initElements();
        initTripList();
        initImageCross();
        initSaveNewTrip();

        return mView;
    }

    private void initElements() {
        mRecyclerView = (RecyclerView) mView.findViewById(R.id.fragment_map_panel_recycler_view);
        mAddTripLayout = (RelativeLayout) mView.findViewById(idContainer);
        mAddTripLayout.setVisibility(View.GONE);
        mCross = (ImageView) mView.findViewById(idImageCross);
        mAddTitle = (EditText) mView.findViewById(R.id.create_trip_name);
        mFromPlace = (EditText) mView.findViewById(R.id.create_trip_start_town);
        mFromDate = (EditText) mView.findViewById(R.id.create_trip_start_date);
        mEndPlace = (EditText) mView.findViewById(R.id.create_trip_end_town);
        mEndDate = (EditText) mView.findViewById(R.id.create_trip_end_date);
        mPrivate = (CheckBox) mView.findViewById(R.id.create_trip_privacy);
        mSave = (Button) mView.findViewById(R.id.create_trip_save);
    }

    private void initImageCross() {
        mCross.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mAddTripLayout.getVisibility() == View.GONE) {
                    openPanel();
                } else {
                    closePanel();
                }
            }
        });
    }

    private void initSaveNewTrip() {
        mSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveInfo();
                cleanPanel();
                closePanel();
            }
        });
    }

    private void saveInfo() {
        //check elements
        if (validElements()) {
            sendRequestAddTrip();
            reloadRecyclerView();
        } else {
            showSavingError();
        }
    }

    private void showSavingError() {
        //TODO: Dialog to indicate the error
    }

    private void reloadRecyclerView() {
        //TODO: Reload data in adapter (rq or add last manually)geny
    }

    private boolean validElements() {
        //TODO: validate or not those panel's elements
        return true;
    }

    private void sendRequestAddTrip() {
        String token = MyApplication.getInstance().getSP_Token();
        Trip trip = new Trip();
        trip.setName(mAddTitle.getText().toString());
        Start start = new Start();
        String startName = mFromPlace.getText().toString();
        start.setCountry(startName);
        start.setTown(startName);
        start.setLocation(startName);
        String startDate = toGTDate(mFromDate.getText().toString());
        start.setDate(startDate);
        trip.setStart(start);
        End end = new End();
        String endName = mFromPlace.getText().toString();
        end.setTown(endName);
        end.setCountry(endName);
        end.setLocation(endName);
        String endDate = toGTDate(mEndDate.getText().toString());
        end.setDate(endDate);
        trip.setEnd(end);
        String privacy = mPrivate.isChecked() ? "y" : "n";
        trip.setPrivacy(privacy);
        RqPostTrip rq = new RqPostTrip(getActivity(), token, trip);
        VolleySingleton.getInstance(getActivity()).getRequestQueue().add(rq);
    }

    private String toGTDate(String date) {
        String[] splitDate = date.split("/");
        if (splitDate.length != 3)
            return getCurrentDate();
        return splitDate[2] + '-' + splitDate[1] + '-' + splitDate[0];
    }

    private String getCurrentDate() {
        //TODO: get current date "YYYY-MM-JJ"
        return "2016-02-01";
    }

    private void cleanPanel() {
        mAddTitle.setText("");
        mFromPlace.setText("");
        mFromDate.setText("");
        mEndPlace.setText("");
        mEndDate.setText("");
        mPrivate.setChecked(false);
    }

    private void openPanel() {
        if (mAddTripLayout.getVisibility() == View.GONE) {
            final Drawable del = ContextCompat.getDrawable(getActivity(), idDrawableCancel);
            mAddTripLayout.setVisibility(View.VISIBLE);
            mCross.setImageDrawable(del);
        }
    }

    private void closePanel() {
        if (mAddTripLayout.getVisibility() == View.VISIBLE) {
            final Drawable add = ContextCompat.getDrawable(getActivity(), idDrawablePlus);
            mAddTripLayout.setVisibility(View.GONE);
            mCross.setImageDrawable(add);
        }
    }

    private void initTripList() {
        mRecyclerView.setAdapter(new MapPanelGroupRecyclerViewAdapter(mRecyclerView, getData()));
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
    }

    private List<Trip> getData() {
        return MyApplication.getInstance().getTrips();
    }
}
