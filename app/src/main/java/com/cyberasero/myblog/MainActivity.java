package com.cyberasero.myblog;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.cyberasero.myblog.volley.VolleySingleton;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
    private String appURL;
    private String EMAIL,PASSWORD;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        appURL = "http://192.168.1.4/api/logIn.php";
    }
    private void login(){

        StringRequest stringRequest = new StringRequest(Request.Method.POST, appURL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String , String> params = new HashMap<>();
                params.put("Accept", "Application/json: charset=UTF-8");
                return params;
            }

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String , String> params = new HashMap<>();
                params.put("email",EMAIL);
                params.put("password",PASSWORD);
                return params;
            }
        };
        VolleySingleton.getInstance().addRequestQueue(stringRequest);


    }
}
