package gtrotter.eu.android.fragments;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;

import com.bluejamesbond.text.DocumentView;
import com.github.florent37.materialtextfield.MaterialTextField;
import com.google.android.gms.common.GoogleApiAvailability;

import gtrotter.eu.android.R;

public class AboutFragment extends Fragment implements AdapterView.OnItemSelectedListener{

    private static final String url_facebook = "https://www.facebook.com/pages/GTrotter/1491409031075558";
    private static final String url_twitter = "https://twitter.com/@GTrotterCom";
    private static final String mobile_email_address = "mobile@g-trotter.eu";
    private static final String general_email_address = "";
    private static final String feedback_email_address = "";
    private static final String admin_email_address = "";

    private static final int background_id = R.drawable.about_background;

    private EditText mObject;

    private boolean descriptionUp = true;

    private Context mContext;

    private String mDestination = mobile_email_address;

    public AboutFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        //Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_about, container, false);
        final Context context = container.getContext();
        final CardView desc = (CardView) v.findViewById(R.id.about_card_description);
        final CardView form = (CardView) v.findViewById(R.id.about_mail_form);
        mContext = context;

        setObjectEditText(v);
        setAboutBackground(context, v);
        setFacebookButton(v);
        setTwitterButton(v);
        setMapsButton(context, v);
        setContactUsButton(context, v, desc, form);
        setSendButton(context, v, desc, form);

        return v;
    }

    private void setMapsButton(Context context, View v) {
        final DocumentView title = (DocumentView) v.findViewById(R.id.about_description_title);
        final DocumentView description = (DocumentView)v.findViewById(R.id.about_description);
        final String mapsNotices = GoogleApiAvailability.getInstance().getOpenSourceSoftwareLicenseInfo(context);
        CardView maps = (CardView) v.findViewById(R.id.about_maps);
        maps.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (descriptionUp) {
                    title.setVisibility(View.GONE);
                    description.setText(mapsNotices);
                    descriptionUp = false;
                }
                else {
                    title.setVisibility(View.VISIBLE);
                    description.setText(getString(R.string.about_description));
                    descriptionUp = true;
                }
            }
        });
    }

    private void setObjectEditText(View v) {
        EditText mMessage = (EditText) v.findViewById(R.id.form_message);
        RelativeLayout.LayoutParams p = (RelativeLayout.LayoutParams) mMessage.getLayoutParams();
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
            LinearLayout object = (LinearLayout) v.findViewById(R.id.form_object_container_deprecated);
            object.setVisibility(View.VISIBLE);
            mObject = (EditText) v.findViewById(R.id.form_object_deprecated);
            p.addRule(RelativeLayout.BELOW, R.id.form_object_container_deprecated);
        } else {
            MaterialTextField object = (MaterialTextField) v.findViewById(R.id.form_object_container);
            object.setVisibility(View.VISIBLE);
            mObject = (EditText) v.findViewById(R.id.form_object);
            p.addRule(RelativeLayout.BELOW, R.id.form_object_container);
        }
        mMessage.setLayoutParams(p);
    }

    private void setSendButton(final Context context, View v, final CardView desc, final CardView form) {
        //Send email button (email form)
        CardView sendMail = (CardView) v.findViewById(R.id.form_send_mail);
        final EditText message = (EditText) v.findViewById(R.id.form_message);
        final TextView error = (TextView) v.findViewById(R.id.form_error);
        final ImageView logo = (ImageView) v.findViewById(R.id.about_contact_us_image);
        sendMail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String errorMsg;
                String obj = mObject.getText().toString().trim();
                String msg = message.getText().toString().trim();
                if (obj.length() == 0) {
                    errorMsg = "Error: " + getString(R.string.form_error_object);
                    error.setText(errorMsg);
                    error.setVisibility(View.VISIBLE);
                } else if (msg.length() <= 5 || msg.length() >= 200) {
                    errorMsg = "Error: " + getString(R.string.form_error_message);
                    error.setText(errorMsg);
                    error.setVisibility(View.VISIBLE);
                } else {
                    errorMsg = "";
                    error.setText(errorMsg);
                    error.setVisibility(View.GONE);
                    Intent emailIntent = new Intent(Intent.ACTION_SEND);
                    emailIntent.setType("message/rfc822");
                    Log.d("About send", mDestination);
                    emailIntent.putExtra(Intent.EXTRA_EMAIL, new String[]{mDestination});
                    emailIntent.putExtra(Intent.EXTRA_SUBJECT, obj);
                    emailIntent.putExtra(Intent.EXTRA_TEXT, msg);
                    try {
                        startActivity(emailIntent);
                        mObject.setText("");
                        message.setText("");
                        error.setText("");
                        logo.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.ic_done_white_48dp));
                        form.setVisibility(View.GONE);
                        desc.setVisibility(View.VISIBLE);
                    } catch (android.content.ActivityNotFoundException ex) {
                        errorMsg = "Error: " + getString(R.string.no_email_app);
                        error.setText(errorMsg);
                        error.setVisibility(View.VISIBLE);
                    }
                }
            }
        });
    }

    private void setContactUsButton(final Context context, View v, final CardView desc, final CardView form) {
        //Contact US button

        final CardView contactUs = (CardView) v.findViewById(R.id.about_contact_us);
        final View rootView = v.findViewById(R.id.about_layout);
        final ImageView logo = (ImageView) v.findViewById(R.id.about_contact_us_image);

        //Get the spinner (destinations) and set the adapter
        Spinner spinner = (Spinner) form.findViewById(R.id.form_destination);
        int arrayResource = R.array.destinations;
        int layoutItem = android.R.layout.simple_spinner_item;
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(context, arrayResource, layoutItem);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);

        //Click on the Contact icon change from description to form
        contactUs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                logo.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.ic_mail_outline_white_48dp));
                if (form.getVisibility() == View.VISIBLE) {
                    form.setVisibility(View.GONE);
                    desc.setVisibility(View.VISIBLE);
                } else {
                    form.setVisibility(View.VISIBLE);
                    desc.setVisibility(View.GONE);
                }
            }
        });

        //Remove Contact Us button when keyboard is up
        rootView.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                int heightDiff = rootView.getRootView().getHeight() - rootView.getHeight();
                if (heightDiff > 500) {
                    if (contactUs.getVisibility() == View.VISIBLE) {
                        contactUs.setVisibility(View.GONE);
                        ViewGroup.MarginLayoutParams mlp = (ViewGroup.MarginLayoutParams) form.getLayoutParams();
                        mlp.bottomMargin = 0;
                        form.setLayoutParams(mlp);
                    }
                } else {
                    if (contactUs.getVisibility() == View.GONE) {
                        contactUs.setVisibility(View.VISIBLE);
                        ViewGroup.MarginLayoutParams mlp = (ViewGroup.MarginLayoutParams) form.getLayoutParams();
                        mlp.bottomMargin = (int) getResources().getDimension(R.dimen.footer_button_margin_height);
                        form.setLayoutParams(mlp);
                    }
                }
            }
        });
    }

    private void setTwitterButton(View v) {
        //Twitter button
        CardView twitter = (CardView) v.findViewById(R.id.about_twitter);
        twitter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(url_twitter));
                startActivity(i);
            }
        });
    }

    private void setFacebookButton(View v) {
        //Facebook Button
        CardView facebook = (CardView) v.findViewById(R.id.about_facebook);
        facebook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(url_facebook));
                startActivity(i);
            }
        });
    }

    private void setAboutBackground(Context context, View v) {
        //Background
        Drawable background = ContextCompat.getDrawable(context, background_id);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN)
            v.setBackground(background);
        else
            //noinspection deprecation
            v.setBackgroundDrawable(background);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        String res = (String) parent.getItemAtPosition(position);
        switch (res) {
            case "Mobile":
                mDestination = mobile_email_address;
                break;
            case "General":
                mDestination = general_email_address;
                break;
            case "Feedback":
                mDestination = feedback_email_address;
                break;
            case "Admin":
                mDestination = admin_email_address;
                break;
            default:
                mDestination = mobile_email_address;
                break;
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
