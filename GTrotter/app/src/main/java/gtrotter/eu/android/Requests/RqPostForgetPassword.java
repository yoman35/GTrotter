package gtrotter.eu.android.Requests;

import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class RqPostForgetPassword extends StringRequest {

    private String mEmail;

    public RqPostForgetPassword(String email, Response.Listener<String> listener, Response.ErrorListener errorListener) {
        super(Method.POST, "https://www.g-trotter.eu:3000/user/reset/password", listener, errorListener);
        mEmail = email;
    }

    @Override
    protected VolleyError parseNetworkError(VolleyError volleyError) {
        if(volleyError.networkResponse != null && volleyError.networkResponse.data != null)
            volleyError = new VolleyError(new String(volleyError.networkResponse.data));
        return volleyError;
    }

    @Override
    protected Map<String, String> getParams() throws AuthFailureError {
        Map<String, String> params = new HashMap<>();
        params.put("email", mEmail);
        return params;
    }
}
