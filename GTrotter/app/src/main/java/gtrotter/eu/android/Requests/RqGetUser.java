package gtrotter.eu.android.Requests;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.Response;
import com.google.gson.Gson;


import gtrotter.eu.android.models.GTrotterRequest;
import gtrotter.eu.android.utilities.MyApplication;

public class RqGetUser extends RqGTrotterAPIString {

    public RqGetUser(final Context context, String idUser, String token) {
        super(
                context,
                Request.Method.GET,
                "/user/id/" + idUser + "?t=" + token,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // PARSING
                        GTrotterRequest request = new Gson().fromJson(response, GTrotterRequest.class);
                        // SAVING
                        MyApplication.getInstance().putSP_Id(request.getUser().get_id());
                        MyApplication.getInstance().putSP_Login(request.getUser().getLocal().getLogin());
                        MyApplication.getInstance().putSP_FirstName(request.getUser().getFirstname());
                        MyApplication.getInstance().putSP_LastName(request.getUser().getLastname());
                        MyApplication.getInstance().putSP_Email(request.getUser().getEmail());
                        MyApplication.getInstance().putSP_Confirmed(Boolean.valueOf(request.getUser().getConfirmed()));
                    }
                }
        );
    }
}