package com.example.project8;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import android.content.Intent;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.button.MaterialButton;

public class MainActivity extends AppCompatActivity {
    String cid = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);

        TextView username = (TextView) findViewById(R.id.username);
        TextView password = (TextView) findViewById(R.id.password);
        MaterialButton loginbtn = (MaterialButton) findViewById(R.id.loginbtn);

        loginbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                RequestQueue queue = Volley.newRequestQueue(MainActivity.this);
                String usr = username.getText().toString();
                String pas = password.getText().toString();
                String url = "http://192.168.1.175:8080/loyaltyfirst/login?user=" + usr + "&pass=" + pas;
                //Toast.makeText(MainActivity.this, url, Toast.LENGTH_SHORT).show();
                StringRequest request = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String s) {
                        if(s.contains("no")){
                            Toast.makeText(MainActivity.this, "Username/Password Invalid", Toast.LENGTH_SHORT).show();
                        }
                        else if(s.contains("yes")){
                            String[] result = s.trim().split(":");
                            cid = result[1];
                            //Toast.makeText(MainActivity.this, "CID: " +cid , Toast.LENGTH_SHORT).show();
                            Intent intent=new Intent(MainActivity.this,Home.class);
                            intent.putExtra("CID",cid);
                            startActivity(intent);
                        }
                        else{
                            Toast.makeText(MainActivity.this, "Error: "+s, Toast.LENGTH_SHORT).show();
                        }
                    }
                },

                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Toast.makeText(MainActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();

                        }
                    });

                queue.add(request);

            }

        });
    }
}