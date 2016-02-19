package gtrotter.eu.android.activities;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;

import java.util.regex.Pattern;

import gtrotter.eu.android.R;
import gtrotter.eu.android.Requests.RqPostForgetPassword;
import gtrotter.eu.android.Requests.RqPostRegister;
import gtrotter.eu.android.Requests.RqPostSignIn;
import gtrotter.eu.android.Requests.VolleySingleton;
import gtrotter.eu.android.adapters.LoginViewPagerAdapter;
import gtrotter.eu.android.models.User;
import gtrotter.eu.android.slidingtab.SlidingTabLayout;
import gtrotter.eu.android.utilities.MyApplication;

public class LoginActivity extends AppCompatActivity {

    private Boolean exit = false;

    private EditText et_username;
    private EditText et_password;

    //Feature: RegExp control
    private Pattern rx_name = Pattern.compile("[A-Za-z-]{2,15}");
    private Pattern rx_username = Pattern.compile("[A-Za-z0-9_-]{2,15}");
    private Pattern rx_email = Pattern.compile("[A-Za-z0-9_\\.-]+@([A-Za-z0-9_-]+)\\.([A-Za-z0-9_-]+)+");
    private Pattern rx_mdp = Pattern.compile("[\\S\\d\\?]{6,24}");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        final Context context = this;
        //Feature: Tab View
        String titles[] = new String[]{getResources().getString(R.string.tab_sign_in_title),
                getResources().getString(R.string.tab_register_title)};
        LoginViewPagerAdapter adapter = new LoginViewPagerAdapter(getSupportFragmentManager(), titles, 2);
        ViewPager pager = (ViewPager) findViewById(R.id.pager);
        pager.setAdapter(adapter);
        SlidingTabLayout tabs = (SlidingTabLayout) findViewById(R.id.login_tabs);
        tabs.setDistributeEvenly(true);
        tabs.setCustomTabColorize(new SlidingTabLayout.TabColorize() {
            @Override
            public int getIndicatorColor(int position) {
                return ContextCompat.getColor(context, R.color.colorPrimary);
            }
        });
        tabs.setViewPager(pager);

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

    public void myOnClickedLogin(View view) {
        switch (view.getId()) {
            case R.id.btn_sign_in:
                btnSignIn();
                break;
            case R.id.btn_register:
                btnRegister();
                break;
            case R.id.btn_sign_in_facebook:
                btnSignInFacebook();
                break;
            case R.id.btn_sign_in_google:
                btnSignInGoogle();
                break;
            case R.id.btn_register_facebook:
                btnRegisterFacebook();
                break;
            case R.id.btn_register_google:
                btnRegisterGoogle();
                break;
            case R.id.img_login_hint_password:
                imgHintPassword();
                break;
            case R.id.tv_forget_password:
                forgetPassword();
                break;
        }
    }

    private void forgetPassword() {
        final int layoutSuccess = R.layout.layout_custom_toast_forget_password_success;
        final int layoutError = R.layout.layout_custom_toast_forget_password_error;
        final int viewGroupSuccess = R.id.toast_forget_password_success;
        final int viewGroupError = R.id.toast_forget_password_error;
        final int gravity = Gravity.CENTER_HORIZONTAL | Gravity.CENTER_VERTICAL;
        final Context context = this;
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        LayoutInflater inflater = getLayoutInflater();
        View v = inflater.inflate(R.layout.forget_password_dialog,
                (ViewGroup) findViewById(R.id.forget_password_dialog));
        final EditText email = (EditText) v.findViewById(R.id.forget_password_dialog_email);
        builder.setView(v)
                .setPositiveButton(R.string.forget_password_dialog_submit,
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                final ProgressDialog pd = new ProgressDialog(context);
                                pd.setTitle(getString(R.string.forget_password_progress_title));
                                pd.setMessage(getString(R.string.forget_password_progress_status));
                                pd.show();
                                RqPostForgetPassword rq = new RqPostForgetPassword(
                                        email.getText().toString(),
                                        new Response.Listener<String>() {
                                            @Override
                                            public void onResponse(String response) {
                                                pd.dismiss();
                                                LayoutInflater inflater = getLayoutInflater();
                                                View myToast = inflater.inflate(layoutSuccess,
                                                        (ViewGroup) findViewById(viewGroupSuccess));
                                                Toast toast = new Toast(getApplicationContext());
                                                toast.setGravity(gravity, 0, 0);
                                                toast.setDuration(Toast.LENGTH_LONG);
                                                toast.setView(myToast);
                                                toast.show();
                                            }
                                        },
                                        new Response.ErrorListener() {
                                            @Override
                                            public void onErrorResponse(VolleyError error) {
                                                pd.dismiss();
                                                LayoutInflater inflater = getLayoutInflater();
                                                View myToast = inflater.inflate(layoutError,
                                                        (ViewGroup) findViewById(viewGroupError));
                                                Toast toast = new Toast(getApplicationContext());
                                                toast.setGravity(gravity, 0, 0);
                                                toast.setDuration(Toast.LENGTH_LONG);
                                                toast.setView(myToast);
                                                toast.show();
                                            }
                                        }
                                );
                                VolleySingleton.getInstance(context).getRequestQueue().add(rq);
                            }
                        })
                .create()
                .show();
    }

    private void btnSignIn() {
        et_username = (EditText) findViewById(R.id.et_login_username);
        et_password = (EditText) findViewById(R.id.et_login_password);
        final String username = String.valueOf(et_username.getText());
        final String password = String.valueOf(et_password.getText());
        if (oneFieldIsEmpty(username, password))
            return;
        VolleySingleton.getInstance(this).getRequestQueue().add(new RqPostSignIn(this, username, password));
    }

    private void btnRegister() {
        EditText et_firstName = (EditText) findViewById(R.id.et_register_first_name);
        EditText et_lastName = (EditText) findViewById(R.id.et_register_last_name);
        et_username = (EditText) findViewById(R.id.et_register_username);
        EditText et_email = (EditText) findViewById(R.id.et_register_email);
        et_password = (EditText) findViewById(R.id.et_register_password);
        EditText et_confirm = (EditText) findViewById(R.id.et_register_confirm_password);

        String firstName = String.valueOf(et_firstName.getText());
        String lastName = String.valueOf(et_lastName.getText());
        String username = String.valueOf(et_username.getText());
        String email = String.valueOf(et_email.getText());
        String password = String.valueOf(et_password.getText());
        String confirm = String.valueOf(et_confirm.getText());

        if (oneFieldIsEmpty(firstName, lastName, username, email, password, confirm))
            return;
        if (!confirm.equals(password)) {
            MyApplication.getInstance().displayMessageLong(getResources().getString(R.string.error_confirm_password));
            return;
        }

        boolean fn = rx_name.matcher(firstName).matches();
        boolean ln = rx_name.matcher(lastName).matches();
        boolean un = rx_username.matcher(username).matches();
        boolean em = rx_email.matcher(email).matches();
        boolean ps = rx_mdp.matcher(password).matches();
        if (fn && ln && un && em && ps) {
            User user = new User(String.valueOf(et_firstName.getText()), String.valueOf(et_lastName.getText()),
                    String.valueOf(et_username.getText()), String.valueOf(et_email.getText()),
                    String.valueOf(et_password.getText()));
            VolleySingleton.getInstance(this).getRequestQueue().add(new RqPostRegister(this, user));
        }

        startActivity(new Intent(this, MainActivity.class));
    }

    private void btnSignInFacebook() {
        //TODO: btnSignInFacebook
        //startActivity(new Intent(this, MainActivity.class));
    }

    private void btnSignInGoogle() {
        //TODO: btnSignInGoogle
        //startActivity(new Intent(this, MainActivity.class));
    }

    private void btnRegisterFacebook() {
        //TODO: btnRegisterFacebook
        //startActivity(new Intent(this, MainActivity.class));
    }

    private void btnRegisterGoogle() {
        //TODO: btnRegisterGoogle
        //startActivity(new Intent(this, MainActivity.class));
    }

    private void imgHintPassword() {
        LayoutInflater inflater = getLayoutInflater();
        View myToast = inflater.inflate(R.layout.layout_custom_hint,
                (ViewGroup) findViewById(R.id.toast_password_hint));
        Toast toast = new Toast(getApplicationContext());
        toast.setGravity(Gravity.CENTER_HORIZONTAL | Gravity.CENTER_VERTICAL, 0, 0);
        toast.setDuration(Toast.LENGTH_LONG);
        toast.setView(myToast);
        toast.show();
    }

    private boolean oneFieldIsEmpty(String... strings) {
        for (String s : strings) {
            if (s.trim().isEmpty()) {
                MyApplication.getInstance().displayMessageLong(getResources().getString(R.string.missing_field));
                return true;
            }
        }
        return false;
    }
}
