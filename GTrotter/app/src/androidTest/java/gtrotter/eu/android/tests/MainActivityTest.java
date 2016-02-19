package gtrotter.eu.android.tests;

import android.support.v7.widget.Toolbar;
import android.test.ActivityInstrumentationTestCase2;
import android.test.suitebuilder.annotation.SmallTest;

import gtrotter.eu.android.R;
import gtrotter.eu.android.activities.MainActivity;

/**
 * Created by YomanHD on 07/05/2015.
 */
public class MainActivityTest extends ActivityInstrumentationTestCase2<MainActivity> {

    private MainActivity activity;

    public MainActivityTest() {
        super(MainActivity.class);
    }

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        activity = getActivity();
    }

    @SmallTest
    public void testToolbar() {
        Toolbar toolbar = (Toolbar) activity.findViewById(R.id.app_bar_main_activity);
        assertNotNull(toolbar);
    }

}
