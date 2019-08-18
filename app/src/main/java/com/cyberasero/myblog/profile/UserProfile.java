package com.cyberasero.myblog.profile;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.cyberasero.myblog.R;
import com.cyberasero.myblog.volley.VolleySingleton;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class UserProfile extends AppCompatActivity {
    private String NAME,LAST_NAME,ACCOUNT,EMS,RMS,EMAIL,SITE,DEPARTMENT,PHONE,STATUS,DOH;
    private String appURL;
    Activity mContext = this;
    TextView mName,mLastName,mAccount,mEMS,mRMS,mEmail,mSite,mDepartment,mPhone,mStatus,mDOH;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);

        mName = findViewById(R.id.text_Name);
        mLastName = findViewById(R.id.text_LastName);
        mAccount = findViewById(R.id.text_Account);
        mEMS = findViewById(R.id.text_EMS);
        mRMS = findViewById(R.id.text_RMS);
        mEmail = findViewById(R.id.text_Email);
        mSite = findViewById(R.id.text_Site);
        mDepartment = findViewById(R.id.text_Department);
        mPhone = findViewById(R.id.text_Phone);
        mStatus = findViewById(R.id.text_Status);
        mDOH = findViewById(R.id.text_DOH);

        Intent data = getIntent();
        EMAIL = data.getStringExtra("email");
        appURL = "http://192.168.1.12/api/getUserDetails.php?email="+EMAIL;

        getUserDetails();

    }

    private void getUserDetails(){

        if (EMAIL.isEmpty()){
            AlertDialog.Builder alert = new AlertDialog.Builder(mContext);
            alert.setMessage("Email cannot be empty");
            alert.setCancelable(false);
            alert.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            });
            alert.show();
        }
        else {
            StringRequest stringRequest = new StringRequest(Request.Method.GET, appURL, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    try {
                        JSONObject jsonObject = new JSONObject(response);
                        NAME = jsonObject.getString("name");
                        LAST_NAME = jsonObject.getString("lname");
                        ACCOUNT = jsonObject.getString("acct");
                        EMS = jsonObject.getString("ems");
                        RMS = jsonObject.getString("rms");
                        EMAIL = jsonObject.getString("email");
                        SITE = jsonObject.getString("site");
                        DEPARTMENT = jsonObject.getString("dept");
                        PHONE = jsonObject.getString("phone");
                        STATUS = jsonObject.getString("status");
                        DOH = jsonObject.getString("doh");

                        mName.setText(NAME);
                        mLastName.setText(LAST_NAME);
                        mAccount.setText(ACCOUNT);
                        mEMS.setText(EMS);
                        mRMS.setText(RMS);
                        mEmail.setText(EMAIL);
                        mSite.setText(SITE);
                        mDepartment.setText(DEPARTMENT);
                        mPhone.setText(PHONE);
                        mStatus.setText(STATUS);
                        mDOH.setText(DOH);

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    AlertDialog.Builder alert;
                    NetworkResponse response = error.networkResponse;
                    if (response != null && response.data != null){
                        switch (response.statusCode) {
                            case 400:
                                alert = new AlertDialog.Builder(mContext);
                                alert.setTitle("Error");
                                alert.setMessage(response.data.toString());
                                alert.setCancelable(false);
                                alert.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.dismiss();
                                    }
                                });
                                alert.show();
                                break;
                            case 404:
                                alert = new AlertDialog.Builder(mContext);
                                alert.setTitle("Error");
                                alert.setMessage(response.data.toString());
                                alert.setCancelable(false);
                                alert.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.dismiss();
                                    }
                                });
                                alert.show();
                                break;
                            case 403:
                                alert = new AlertDialog.Builder(mContext);
                                alert.setTitle("Error");
                                alert.setMessage(response.data.toString());
                                alert.setCancelable(false);
                                alert.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.dismiss();
                                    }
                                });
                                alert.show();
                                break;
                        }
                    }
                    else {

                        alert = new AlertDialog.Builder(mContext);
                        alert.setTitle("Error");
                        alert.setMessage(error.toString());
                        alert.setCancelable(false);
                        alert.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        });
                        alert.show();
                    }
                }
            }) {
                @Override
                public Map<String, String> getHeaders() throws AuthFailureError {
                    HashMap<String , String> params = new HashMap<>();
                    params.put("Accept", "Application/json: charset=UTF-8");
                    return params;
                }
            };
            VolleySingleton.getInstance().addRequestQueue(stringRequest);
        }
    }
}
