package com.example.market;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

public class Auth extends AppCompatActivity implements View.OnClickListener {

    TextView msg;
    EditText username,password;
    Button login;
    RequestQueue queue;
    String getUrl = "http://10.0.2.2:8090/users";

    // creating constant keys for shared preferences.
    public static final String SHARED_PREFS = "shared_prefs";

    // key for storing email.
    public static final String USER_KEY = "user_key";

    // key for storing password.
    public static final String PASSWORD_KEY = "password_key";

    // variable for shared preferences.
    SharedPreferences sharedpreferences;
    String ses_user, ses_password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auth);
        username = findViewById(R.id.username);
        password = findViewById(R.id.password);
        login = findViewById(R.id.button);
        msg = findViewById(R.id.msg);



        // getting the data which is stored in shared preferences.
        sharedpreferences = getSharedPreferences(SHARED_PREFS, Context.MODE_PRIVATE);

        // in shared prefs inside het string method
        // we are passing key value as EMAIL_KEY and
        // default value is
        // set to null if not present.
        ses_user = sharedpreferences.getString(USER_KEY, null);
        ses_password = sharedpreferences.getString(PASSWORD_KEY, null);


        login.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if(view==login){
            login(username.getText().toString(), password.getText().toString());
        }
    }

    private void login(String username, String password) {

        if (TextUtils.isEmpty(username) && TextUtils.isEmpty(password)) {
            msg.setText("The fields are empty");
        } else {
            queue = Volley.newRequestQueue(this);

            StringRequest request = new StringRequest(Request.Method.GET, getUrl, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    //Log.d("ReTAG", response.toString());
                    boolean flag = false;
                    JSONObject obj = null;
                    JSONArray array = null;
                    try {
                        obj = new JSONObject(response);
                        array = array = obj.getJSONObject("_embedded").getJSONArray("users");
                        // Log.d("ReTAG", array.toString());
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                    for(int i = 0 ; i < array.length() ; i++){
                        String u_name = null , u_pass = null;
                        try {
                            u_name = array.getJSONObject(i).getString("username");
                            u_pass = array.getJSONObject(i).getString("password");
                            if(username.equals(u_name) && password.equals(u_pass)){
                                flag = true;
                                Log.d("Login", u_name+" " + u_pass);

                                SharedPreferences.Editor editor = sharedpreferences.edit();

                                // below two lines will put values for
                                // email and password in shared preferences.
                                editor.putString(USER_KEY,username);
                                editor.putString(PASSWORD_KEY, password);

                                // to save our data with key and value.
                                editor.apply();

                                // starting new activity.
                                Intent intent= new Intent(Auth.this, MainActivity.class);
                                startActivity(intent);
                                finish();

                                return;

                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }

                    if(!flag) msg.setText("Username or password incorrect");
                    else msg.setText("");

                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    //Log.d("ErrTAG", "Error: " + error.getMessage());


                }
            })
            {


            };

            queue.add(request);
        }

    }

    @Override
    protected void onStart() {
        super.onStart();
        if (ses_user != null && ses_user != null) {
            Intent i = new Intent(Auth.this, MainActivity.class);
            startActivity(i);
        }
    }
}