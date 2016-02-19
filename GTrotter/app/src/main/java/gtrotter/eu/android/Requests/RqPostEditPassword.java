package gtrotter.eu.android.Requests;

import android.content.Context;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Response;

import java.util.HashMap;
import java.util.Map;

public class RqPostEditPassword extends RqGTrotterAPIString {

    private String oldPwd, newPwd;

    public RqPostEditPassword(final Context context, String token, String oldPwd, String newPwd) {
        super(context, Method.POST, "/user/edit/password?t=" + token, new Response.Listener<String>() {
            @Override
            public void onResponse(String s) {
                Toast.makeText(context, "OK", Toast.LENGTH_LONG).show();
            }
        });
        this.oldPwd = oldPwd;
        this.newPwd = newPwd;
    }

    @Override
    protected Map<String, String> getParams() throws AuthFailureError {
        Map<String, String> map = new HashMap<>();
        map.put("oldPwd", this.oldPwd);
        map.put("newPwd", this.newPwd);
        return map;
    }
}
