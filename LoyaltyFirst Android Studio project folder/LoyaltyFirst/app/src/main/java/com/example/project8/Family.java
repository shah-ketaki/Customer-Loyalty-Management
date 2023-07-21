package com.example.project8;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.ArrayList;
import java.util.List;

public class Family extends AppCompatActivity {

    String cid="";
    String F_points="";
    String F_id = "";
    String TXN_ID = "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Family.this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_family);


        Intent intent=getIntent();
        cid=intent.getStringExtra("CID");
        List<String> tr_ref_list = new ArrayList<String>();
        List<String> tr_ref_txn_pnt = new ArrayList<String>();
        List<String> tr_ref_fam = new ArrayList<String>();
        List<String> tr_ref_fp = new ArrayList<String>();
        TextView t_desc =(TextView) findViewById(R.id.trdsc);

        Spinner sp1 = (Spinner) findViewById(R.id.transpinner2);


        ArrayAdapter<String> adp1 = new ArrayAdapter<String>(Family.this, android.R.layout.simple_spinner_dropdown_item, tr_ref_list);
        sp1.setAdapter(adp1);

        Button bkbtn = (Button) findViewById(R.id.f_back);
        Button f_add = (Button) findViewById(R.id.fadd);

        RequestQueue queue = Volley.newRequestQueue(Family.this);
        String url = "http://192.168.1.175:8080/loyaltyfirst/Transactions.jsp?cid=" + cid;

        StringRequest trareq = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String s) {
                if(s.equals("error")){
                    Toast.makeText(Family.this, "Error Fetching Customer details", Toast.LENGTH_SHORT).show();
                    finish();
                }
                else{
                    String p_str = Html.fromHtml(s).toString();
                    p_str = p_str.substring(12);
                    p_str.trim();


                    String[] t_Rows = p_str.trim().split("#");

                    //Toast.makeText(Family.this, p_str, Toast.LENGTH_SHORT).show();

                    for(int i=0; i<t_Rows.length; i++){
                        tr_ref_list.add(t_Rows[i].trim().split(",")[0]);
                    }
                    adp1.notifyDataSetChanged();



                }
            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(Family.this, error.getMessage(), Toast.LENGTH_SHORT).show();

                    }
                });
        queue.add(trareq);

        sp1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
              @Override
              public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                  //Toast.makeText(Family.this, adapterView.getSelectedItem().toString(), Toast.LENGTH_SHORT).show();
                  sp1.setSelection(i);
                  RequestQueue queue1 = Volley.newRequestQueue(Family.this);
                  String url1 = "http://192.168.1.175:8080/loyaltyfirst/SupportFamilyIncrease.jsp?cid=" + cid + "&tref=" + tr_ref_list.get(i);

                  StringRequest trareq1 = new StringRequest(Request.Method.GET, url1, new Response.Listener<String>() {
                      @Override
                      public void onResponse(String s) {
                          if (s.equals("error")) {
                              Toast.makeText(Family.this, "Error Fetching Customer details", Toast.LENGTH_SHORT).show();
                              finish();
                          } else {
                              String p_str = Html.fromHtml(s).toString();
                              p_str = p_str.substring(24);
                              p_str.trim();

                              String[] t_data = p_str.trim().split(",");

                              t_desc.setText("TXN Points: " + t_data[2] + "\n\nFamily ID: " + t_data[0] + "\n\nFamily Percent: 30");
                              F_points = t_data[1];
                              F_id = t_data[0];
                              TXN_ID = t_data[2];

                          }
                      }
                  },
                          new Response.ErrorListener() {
                              @Override
                              public void onErrorResponse(VolleyError error) {
                                  Toast.makeText(Family.this, error.getMessage(), Toast.LENGTH_SHORT).show();

                              }
                          });
                  queue1.add(trareq1);
              }

                @Override
                public void onNothingSelected(AdapterView<?> adapterView) {
                    Toast.makeText(Family.this, "No Selection", Toast.LENGTH_SHORT).show();
                }
          });


        f_add.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View view) {
                 RequestQueue queue2 = Volley.newRequestQueue(Family.this);
                 String url2 = "http://192.168.1.175:8080/loyaltyfirst/FamilyIncrease.jsp?fid=" + F_id + "&cid=" + cid + "&npoints=" + F_points;
                 //Toast.makeText(Family.this, url2, Toast.LENGTH_SHORT).show();

                 StringRequest trareq2 = new StringRequest(Request.Method.GET, url2, new Response.Listener<String>() {
                     @Override
                     public void onResponse(String s) {
                         String p_str = Html.fromHtml(s).toString();
                         p_str = p_str.substring(16);
                         p_str.trim();
                         Toast.makeText(Family.this, p_str, Toast.LENGTH_SHORT).show();

                     }
                 },

                 new Response.ErrorListener() {
                     @Override
                     public void onErrorResponse(VolleyError error) {
                         Toast.makeText(Family.this, error.getMessage(), Toast.LENGTH_SHORT).show();

                     }
                 });
                 queue2.add(trareq2);
             }
         });


        bkbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });




    }
}