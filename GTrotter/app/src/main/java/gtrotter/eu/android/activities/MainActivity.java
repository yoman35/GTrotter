package gtrotter.eu.android.activities;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import gtrotter.eu.android.R;
import gtrotter.eu.android.fragments.AboutFragment;
import gtrotter.eu.android.fragments.MenuFragment;
import gtrotter.eu.android.fragments.MyMapFragment;
import gtrotter.eu.android.fragments.PhotosFragment;
import gtrotter.eu.android.fragments.ProfileFragment;
import gtrotter.eu.android.utilities.MyApplication;

public class MainActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private Boolean exit = false;

    Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initToolbar();
        initNavigationDrawer();

        MyMapFragment mMapFragment = new MyMapFragment();
        FragmentManager fm = getFragmentManager();
        FragmentTransaction transaction = fm.beginTransaction();
        switch (MyApplication.getInstance().getSP_CurrentFragment()) {
            case 1:
                transaction.add(R.id.main_container, mMapFragment);
                break;
            case 2:
                transaction.add(R.id.main_container, new ProfileFragment());
                break;
            case 3:
                transaction.add(R.id.main_container, new PhotosFragment());
                break;
            case 4:
                transaction.add(R.id.main_container, new AboutFragment());
                break;
            default:
                transaction.add(R.id.main_container, new MyMapFragment());
                break;
        }
        transaction.addToBackStack(null);
        transaction.commit();


        TextView headerUserName = (TextView) findViewById(R.id.navigation_drawer_header_name);
        TextView emailUser = (TextView) findViewById(R.id.navigation_drawer_header_subtext);
        String fullName = MyApplication.getInstance().getSP_FirstName() + " " + MyApplication.getInstance().getSP_LastName();
        headerUserName.setText(fullName);
        emailUser.setText(MyApplication.getInstance().getSP_Email());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.action_settings:
                intent = new Intent(this, SettingsActivity.class);
                startActivity(intent);
                return true;
            case R.id.synchronize:
                synchronize();
                return true;
            case R.id.log_out:
                logout();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onBackPressed() {
        if (exit)
            finish();
        else {
            MyApplication.getInstance().displayPressBackAgain();
            exit = true;
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    exit = false;
                }
            }, 3000);
        }
    }

    private void initToolbar() {
        toolbar = (Toolbar) findViewById(R.id.app_bar_main_activity);
        setSupportActionBar(toolbar);
        toolbar.setLogo(R.drawable.ic_launcher);
    }

    private void initNavigationDrawer() {
        MenuFragment menuFragment = (MenuFragment)
                getFragmentManager().findFragmentById(R.id.fragment_navigation_drawer);
        menuFragment.setUp((DrawerLayout) findViewById(R.id.drawer_layout), toolbar);
    }

    public void logout() {
        MyApplication.getInstance().getEditor().clear().apply();
        intent = new Intent(this, LoginActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
        startActivity(intent);
    }


    private void synchronize() {
        MyApplication.getInstance().displayMessageShort("Synchronize");
    }

}
