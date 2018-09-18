package com.example.shajalahamed.meditracker;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class chain extends AppCompatActivity {

    private TextView mTextViewResult;
    private TextView productId;
    private RequestQueue mQueue;
    private ArrayList<String> mEntries;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chain);

        mTextViewResult = findViewById(R.id.chain);
        Button showchain = findViewById(R.id.show_chain);
        productId = findViewById(R.id.productId);

        Intent intent = getIntent();
        final String a = intent.getStringExtra("productId");
        productId.append(a);

        mQueue = Volley.newRequestQueue(this);

        showchain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                jsonParse(a);
            }
        });

    }
    private void jsonParse(String id) {

            String url = "http://developers.obboylabs.com/api/queries/selectCommoditiesByOwner?productid="+id;

            RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());

            JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(
                    com.android.volley.Request.Method.GET,
                    url,
                    null,
                    new com.android.volley.Response.Listener<JSONArray>() {
                        @Override
                        public void onResponse(JSONArray response) {
                            try {
                                String owner = null;
                                JSONObject innerObject = response.getJSONObject(0);
                                JSONArray ownerArray = innerObject.getJSONArray("owners");


                                for (int i = 0; i < ownerArray.length(); i++) {
                                    JSONObject ls = ownerArray.getJSONObject(i);
                                    owner = ls.getString("email")+" -> ";
                                    mTextViewResult.append(owner);
                                }

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    },
                    new com.android.volley.Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            error.printStackTrace();
                        }
                    }
            );

            requestQueue.add(jsonArrayRequest);

        }

    }




