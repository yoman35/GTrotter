package gtrotter.eu.android.utilities;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.widget.Toast;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import gtrotter.eu.android.R;
import gtrotter.eu.android.models.Contact;
import gtrotter.eu.android.models.Filter;
import gtrotter.eu.android.models.Image;
import gtrotter.eu.android.models.Trip;

/*
 * Singleton Application class
 * Can be called in the entire application
 * USE: MyApplication.getInstance();
 */
@SuppressWarnings("unused")
public class MyApplication extends Application {

    public static final int GEO_REQUEST_CODE = 100;

    private static MyApplication instance_;

    public static MyApplication getInstance() {
        return instance_;
    }

    public static Context getAppContext() {
        return instance_.getApplicationContext();
    }

    @Override
    public void onCreate() {
        super.onCreate();
        instance_ = this;
    }

    public void displayMessageLong(String toastString) {
        Toast.makeText(MyApplication.getAppContext(), toastString.toUpperCase(), Toast.LENGTH_LONG).show();
    }

    public void displayMessageShort(String toastString) {
        Toast.makeText(MyApplication.getAppContext(), toastString.toUpperCase(), Toast.LENGTH_SHORT).show();
    }

    public void displayPressBackAgain() {
        displayMessageShort(getString(R.string.press_back_again));
    }

    private final String CUR_FRAG = "__current_fragment";
    public final int F_MAP = 1;
    public final int F_PROFILE = 2;
    public final int F_PHOTOS = 3;
    public final int F_ABOUT = 4;

    private final String UNKNOWN = "__unknown";
    private final String REMEMBER = "__remember";
    private final String LOGIN = "__login";
    private final String PASSWORD = "__password";
    private final String TOKEN = "__token";
    private final String ID = "__id";
    private final String FIRST_NAME = "__firstname";
    private final String LAST_NAME = "__lastname";
    private final String EMAIL = "__email";
    private final String CONFIRMED = "__confirmed";
    private final String AVATAR = "__avatar";
    private final String PHONE = "__phone";
    private final String IMAGE = "__image-";
    private final String TRIP = "__trip-";
    private final String FILTER = "__filter-";

    public SharedPreferences getSharedPreferences() {
        return getSharedPreferences("__gtrotter", MODE_PRIVATE);
    }

    public SharedPreferences.Editor getEditor() {
        return getSharedPreferences().edit();
    }

    private int getSP_Int(String name) {
        return getSharedPreferences().getInt(name, -1);
    }

    private String getSP_String(String name) {
        return getSharedPreferences().getString(name, UNKNOWN);
    }

    private Boolean getSP_Boolean(String name) {
        return getSharedPreferences().getBoolean(name, false);
    }

    public int getSP_CurrentFragment() {
        return getSP_Int(CUR_FRAG);
    }

    public Boolean getSP_Remember() {
        return getSP_Boolean(REMEMBER);
    }

    public String getSP_Login() {
        return getSP_String(LOGIN);
    }

    public String getSP_Password() {
        return getSP_String(PASSWORD);
    }

    public String getSP_Token() {
        return getSP_String(TOKEN);
    }

    public String getSP_Id() {
        return getSP_String(ID);
    }

    public String getSP_FirstName() {
        return getSP_String(FIRST_NAME);
    }

    public String getSP_LastName() {
        return getSP_String(LAST_NAME);
    }

    public String getSP_Email() {
        return getSP_String(EMAIL);
    }

    public Boolean getSP_Confirmed() {
        return getSP_Boolean(CONFIRMED);
    }

    public String getSP_Avatar() {
        return getSP_String(AVATAR);
    }

    public String getSP_Phone() {
        return getSP_String(PHONE);
    }

    public String getSP_Image(int position) {
        return getSP_String(IMAGE + String.valueOf(position));
    }

    public String getSP_Trip(int position) {
        return getSP_String(TRIP + String.valueOf(position));
    }

    public String getSP_Filter(int position) {
        return getSP_String(FILTER + String.valueOf(position));
    }

    public void putSP_CurrentFragment(int value) {
        getEditor().putInt(CUR_FRAG, value).apply();
    }

    public void putSP_Remember(Boolean value) {
        getEditor().putBoolean(REMEMBER, value).apply();
    }

    public void putSP_Login(String value) {
        getEditor().putString(LOGIN, value).apply();
    }

    public void putSP_Password(String value) {
        getEditor().putString(PASSWORD, value).apply();
    }

    public void putSP_Token(String value) {
        getEditor().putString(TOKEN, value).apply();
    }

    public void putSP_Id(String value) {
        getEditor().putString(ID, value).apply();
    }

    public void putSP_FirstName(String value) {
        getEditor().putString(FIRST_NAME, value).apply();
    }

    public void putSP_LastName(String value) {
        getEditor().putString(LAST_NAME, value).apply();
    }

    public void putSP_Email(String value) {
        getEditor().putString(EMAIL, value).apply();
    }

    public void putSP_Confirmed(Boolean value) {
        getEditor().putBoolean(CONFIRMED, value).apply();
    }

    public void putSP_Avatar(String value) {
        getEditor().putString(AVATAR, value).apply();
    }

    public void putSP_Phone(String value) {
        getEditor().putString(PHONE, value).apply();
    }

    public void putSP_Image(int position, String value) {
        getEditor().putString(IMAGE + String.valueOf(position), value).apply();
    }

    public void putSP_Trip(int position, String value) {
        getEditor().putString(TRIP + String.valueOf(position), value).apply();
    }

    public void putSP_Filter(int position, String value) {
        getEditor().putString(FILTER + String.valueOf(position), value).apply();
    }

    public List<Trip> getTrips() {
        List<Trip> trips = new ArrayList<>();
        int i = 0;
        String res = getSP_Trip(i);
        while (!(res.equals(UNKNOWN))) {
            trips.add(new Gson().fromJson(res, Trip.class));
            res = getSP_Trip(++i);
        }
        return trips;
    }

    public List<Image> getPhotos() {
        List<Image> images = new ArrayList<>();
        int i = 0;
        String res = getSP_Image(i);
        while (!(res.equals(UNKNOWN))) {
            images.add(new Gson().fromJson(res, Image.class));
            res = getSP_Image(++i);
        }
        return images;
    }

    public List<Filter> getFilters() {
        List<Filter> filters = new ArrayList<>();
        int i = 0;
        String res = getSP_Filter(i);
        while (!(res.equals(UNKNOWN))) {
            filters.add(new Gson().fromJson(res, Filter.class));
            res = getSP_Filter(++i);
        }
        return filters;
    }

    public List<Contact> sortContactsByName(List<Contact> contacts) {
        Integer i, j, min;
        Contact temp;
        for (i = 0; i < contacts.size(); i++) {
            min = i;
            for (j = i; j < contacts.size(); j++)
                if (contacts.get(min).getName().compareTo(contacts.get(j).getName()) > 0)
                    min = j;
            temp = contacts.get(i);
            contacts.set(i, contacts.get(min));
            contacts.set(min, temp);
        }
        return contacts;
    }

    public List<Contact> sortContactsByFav(List<Contact> contacts) {
        Integer i, j, min;
        Contact temp;
        for (i = 0; i < contacts.size(); i++) {
            min = i;
            for (j = i; j < contacts.size(); j++)
                if (!contacts.get(min).ismFavor() && contacts.get(j).ismFavor())
                    min = j;
            temp = contacts.get(i);
            contacts.set(i, contacts.get(min));
            contacts.set(min, temp);
        }
        return contacts;
    }

    public static final String
            ADM_TYPES = "city_hall|embassy|establishment|local_government_office",
            TRA_TYPES = "airport|bus_station|subway_station|taxi_stand|train_station|gas_station|travel_agency",
            EME_TYPES = "car_repair|dentist|doctor|fire_station|hospital|lawyer|pharmacy|police|veterinary_care",
            PAR_TYPES = "amusement_park|bar|bowling_alley|casino|movie_theater|night_club",
            VIS_TYPES = "aquarium|art_gallery|hindu_temple|mosque|museum|park|synagogue|zoo",
            FND_TYPES = "bakery|cafe|bar|food|grocery_or_supermarket|liquor_store|meal_delivery|meal_takeaway|restaurant",
            SHO_TYPES = "book_store|clothing_store|convenience_store|department_store|electronics_store|florist|furniture_store|hardware_store|home_goods_store|jewelry_store|liquor_store|pet_store|shoe_store|shopping_mall|store",
            CIT_TYPES = "cemetery|church|courthouse|library|hindu_temple|mosque|park|school|stadium|synagogue|university",
            BNH_TYPES = "beauty_salon|dentist|doctor|gym|hair_care|health|hospital|spa",
            SLE_TYPES = "lodging",
            USE_TYPES = "atm|bank|car_rental|car_repair|car_wash|dentist|doctor|fire_station|gas_station|hospital|laundry|lawyer|locksmith|pharmacy|plumber|police|post_office|veterinary_care",
            FAV_TYPES = "";
}
