package gtrotter.eu.android.fragments;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.places.Places;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.List;

import gtrotter.eu.android.R;
import gtrotter.eu.android.models.Trip;
import gtrotter.eu.android.utilities.MyApplication;

public class MyMapFragment extends Fragment implements OnMapReadyCallback, GoogleMap.OnMapClickListener,
        GoogleMap.OnMapLongClickListener, GoogleMap.OnMarkerClickListener, GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener, AdapterView.OnItemSelectedListener {

    private static final String LOG = "MAP";

    private GoogleMap mMap;
    private Marker mLastMarker;

    private DrawerLayout mPanelTrips;

    private GoogleApiClient mApiClient;
    private View mView;

    public MyMapFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        MyApplication.getInstance().putSP_CurrentFragment(MyApplication.getInstance().F_MAP);
        View rootView = inflater.inflate(R.layout.fragment_map, container, false);
        mView = rootView;
        //mContext = container.getContext(); if needed that's the way

        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fragment_map_frame_layout_panel, new MapPanelFragment());
        FilterPanelFragment mFilters = new FilterPanelFragment();
        fragmentTransaction.replace(R.id.filter_panel_container, mFilters);
        fragmentTransaction.commit();

        //Get map asynchronously when its ready
        MapFragment mapFragment = MapFragment.newInstance();
        fragmentTransaction.add(R.id.map, mapFragment);
        mapFragment.getMapAsync(this);

        final Button myTrips = (Button) rootView.findViewById(R.id.map_button_trips);
        myTrips.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPanelTrips.openDrawer(GravityCompat.END);
            }
        });

        mPanelTrips = (DrawerLayout) rootView.findViewById(R.id.map_drawer_layout);
        final ActionBarDrawerToggle drawerToggle = new ActionBarDrawerToggle(getActivity(), mPanelTrips, null,
                R.string.navigation_open, R.string.navigation_close) {
            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                getActivity().invalidateOptionsMenu();
                if (myTrips.getVisibility() == View.VISIBLE)
                    myTrips.setVisibility(View.GONE);
            }

            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
                getActivity().invalidateOptionsMenu();
                myTrips.setVisibility(View.VISIBLE);
            }

            @Override
            public void onDrawerSlide(View drawerView, float slideOffset) {
                if (myTrips.getVisibility() == View.VISIBLE)
                    myTrips.setVisibility(View.GONE);
            }
        };
        mPanelTrips.setDrawerListener(drawerToggle);
        mPanelTrips.post(new Runnable() {
            @Override
            public void run() {
                drawerToggle.syncState();
            }
        });

        connectToAPI();


        return rootView;
    }

    @Override
    public void onMapReady(GoogleMap map) {
        mMap = map;
        getLocationCompat();
        mMap.setOnMapClickListener(this);
        mMap.setOnMarkerClickListener(this);
        mMap.setOnMapLongClickListener(this);

        Location lastLocation = getLastLocation();
        LatLng latLng = new LatLng(lastLocation.getLatitude(), lastLocation.getLongitude());
        mMap.animateCamera(CameraUpdateFactory.zoomTo(10));
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 5));

        ImageView geo = (ImageView) mView.findViewById(R.id.geoloc);
        geo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LatLng ll = new LatLng(48.8155399, 2.3638220);
                mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(ll, 14));
                createMarker(ll);
            }
        });

        ImageView zoomIn = (ImageView) mView.findViewById(R.id.zoomin);
        zoomIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mMap.animateCamera(CameraUpdateFactory.zoomIn());
            }
        });

        ImageView zoomOut = (ImageView) mView.findViewById(R.id.zoomout);
        zoomOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mMap.animateCamera(CameraUpdateFactory.zoomOut());
            }
        });

        mLastMarker = null;
    }

    private Location getLastLocation() {
        Activity context = getActivity();
        if (Build.VERSION.SDK_INT >= 23) {
            int granted = PackageManager.PERMISSION_GRANTED;
            String fine = Manifest.permission.ACCESS_FINE_LOCATION,
                    coarse = Manifest.permission.ACCESS_COARSE_LOCATION;
            if (ActivityCompat.checkSelfPermission(context, fine) != granted &&
                    ActivityCompat.checkSelfPermission(context, coarse) != granted) {
                requestPermissions(new String[]{fine, coarse}, MyApplication.GEO_REQUEST_CODE);
            }
        }
        Location last = LocationServices.FusedLocationApi.getLastLocation(mApiClient);
        if (last == null) {
            last = new Location("");
            last.setLatitude(48.1119800);
            last.setLongitude(-1.6742900);
        }
        return last;
    }

    @Override
    public void onMapClick(LatLng latLng) {
        Log.d("MAP", "CLICKED");
        if (mLastMarker != null)
            mLastMarker.remove();
        mLastMarker = createMarker(latLng);
    }

    @Override
    public void onMapLongClick(LatLng latLng) {
        Log.d("MAP", "LONG CLICK");
    }

    @Override
    public boolean onMarkerClick(Marker marker) {

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View v = inflater.inflate(R.layout.edit_step_dialog,
                (ViewGroup) mView.findViewById(R.id.edit_step));
        final EditText name = (EditText) v.findViewById(R.id.step_name);
        final EditText desc = (EditText) v.findViewById(R.id.step_desc);
        final Spinner spinner = (Spinner) v.findViewById(R.id.spinner_trips);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getActivity(),
                R.array.trips, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);
        builder.setView(v)
                .setPositiveButton("SAVE",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                String n = name.getText().toString();
                                String d = desc.getText().toString();
                                Toast.makeText(getActivity(), n + " added to " + mTripChoice, Toast.LENGTH_SHORT).show();
                            }
                        })
                .create()
                .show();
        return true;
    }

    private Marker createMarker(LatLng latLng) {
        MarkerOptions markerOptions = new MarkerOptions()
                .position(latLng)
                .title(getString(R.string.map_marker_edit))
                .draggable(true)
                .alpha(0.8f)
                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE));
        return mMap.addMarker(markerOptions);
    }

    // GOOGLE APIs
    private void connectToAPI() {
        mApiClient = new GoogleApiClient
                .Builder(getActivity())
                .addApi(Places.GEO_DATA_API)
                .addApi(Places.PLACE_DETECTION_API)
                .addApi(LocationServices.API)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .build();
    }

    @Override
    public void onConnected(Bundle bundle) {
        Log.d("API GOOGLE", "connected");

    }

    @Override
    public void onConnectionSuspended(int i) {
        Log.d("API GOOGLE", "suspended");
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        Log.d("API GOOGLE", "failed");
    }

    // PERMISSIONS
    private void getLocationCompat() {
        Activity context = getActivity();
        int granted = PackageManager.PERMISSION_GRANTED;
        String fine = Manifest.permission.ACCESS_FINE_LOCATION,
                coarse = Manifest.permission.ACCESS_COARSE_LOCATION;
        if (Build.VERSION.SDK_INT >= 23) {
            Log.d("PERMISSION", "Need to ask at runtime");
            if (ActivityCompat.checkSelfPermission(context, fine) != granted &&
                    ActivityCompat.checkSelfPermission(context, coarse) != granted) {
                requestPermissions(new String[]{fine, coarse}, MyApplication.GEO_REQUEST_CODE);
            }
        } else {
            Log.d("PERMISSION", "No need to ask at runtime");
            mMap.setMyLocationEnabled(true);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        int granted = PackageManager.PERMISSION_GRANTED;
        switch (requestCode) {
            case MyApplication.GEO_REQUEST_CODE:
                if (grantResults.length > 0 && grantResults[0] == granted) {
                    Log.d("PERMISSION", "AGREE!");
                    Activity context = getActivity();
                    String fine = Manifest.permission.ACCESS_FINE_LOCATION,
                            coarse = Manifest.permission.ACCESS_COARSE_LOCATION;
                    if (Build.VERSION.SDK_INT >= 23) {
                        if (ActivityCompat.checkSelfPermission(context, fine) != granted &&
                                ActivityCompat.checkSelfPermission(context, coarse) != granted) {
                            requestPermissions(new String[]{fine, coarse}, MyApplication.GEO_REQUEST_CODE);
                        }
                        mMap.setMyLocationEnabled(true);
                    } else {
                        mMap.setMyLocationEnabled(true);
                    }
                } else {
                    Log.d("PERMISSION", "DISAGREE...");
                }
                break;
            default:
                Log.d("PERMISSION", Integer.toString(requestCode) + " ...what's that?");
                break;
        }
    }

    @Override
    public void onStart() {
        mApiClient.connect();
        super.onStart();
    }

    @Override
    public void onStop() {
        mApiClient.disconnect();
        super.onStop();
    }
    private String mTripChoice = "Visit London";
    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        mTripChoice = (String) parent.getItemAtPosition(position);
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
