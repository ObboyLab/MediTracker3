package com.example.shajalahamed.meditracker;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class newAsset extends AppCompatActivity {
    private RequestQueue mQueue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_asset);



        TextView productId = findViewById(R.id.productId);
        Button addAsset = findViewById(R.id.asset);

        Intent intent = getIntent();
        final String a = intent.getStringExtra("productId");
        productId.append(a);
        mQueue = Volley.newRequestQueue(this);

        addAsset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                jsonPost(a);
            }
        });

    }

    private void jsonPost(final String data){
        String url ="http://developers.obboylabs.com/api/Product";

        JSONObject postparams = new JSONObject();
        JSONArray jarr = new JSONArray();

        try {
            postparams.put("$class","org.meditracker.Product");
            postparams.put("productId",data);
            postparams.put("owners",jarr);
            Toast.makeText(this,postparams.toString(),Toast.LENGTH_LONG).show();

        } catch (JSONException e) {
            e.printStackTrace();
        }
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                Request.Method.POST,
                url,
                postparams,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                       print(response);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        }

        );
        requestQueue.add(jsonObjectRequest);
    }

    private void print(JSONObject response){
        Toast.makeText(this,response.toString(),Toast.LENGTH_LONG).show();
    }
}
