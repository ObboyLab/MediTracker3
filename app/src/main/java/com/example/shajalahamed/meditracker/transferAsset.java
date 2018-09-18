package com.example.shajalahamed.meditracker;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
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

public class transferAsset extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transfer_asset);

    }

    public void transfer(View view){
        EditText ed = findViewById(R.id.nOwner);
        String owner = ed.getText().toString();
        Intent intent = getIntent();
        String a = intent.getStringExtra("productId");

        jsonPost(owner, a);
    }

    private void jsonPost(final String owner,final String productid){
        String url ="http://developers.obboylabs.com/api/Owner";

        JSONObject postparams = new JSONObject();
       String id = "org.meditracker.Product#" + productid ;

        try {
            postparams.put("$class","org.meditracker.Owner");
            postparams.put("email",owner);
            postparams.put("product",id);
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
