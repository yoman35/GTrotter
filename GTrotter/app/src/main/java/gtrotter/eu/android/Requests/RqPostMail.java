package gtrotter.eu.android.Requests;

import android.content.Context;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Response;

import java.util.HashMap;
import java.util.Map;

import gtrotter.eu.android.models.Mail;

public class RqPostMail extends RqGTrotterAPIString {

    private Mail mail;

    public RqPostMail(final Context context, Mail mail) {
        super(context, Method.POST, "/contact", new Response.Listener<String>() {
            @Override
            public void onResponse(String s) {
                Toast.makeText(context, "OK", Toast.LENGTH_LONG).show();
            }
        });
        this.mail = mail;
    }

    @Override
    protected Map<String, String> getParams() throws AuthFailureError {
        Map<String, String> map = new HashMap<>();
        map.put("subject", this.mail.getSubject());
        map.put("name", this.mail.getName());
        map.put("description", this.mail.getDescription());
        map.put("email_user", this.mail.getEmail());
        map.put("email_team", this.mail.getEmail_team());
        return map;
    }
}
