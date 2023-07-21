package com.example.project8;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Html;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.TableRow;
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

public class Trandetails extends AppCompatActivity {

    String cid="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_trandetails);

        Intent intent=getIntent();
        cid=intent.getStringExtra("CID");
        List<String> tr_ref_list = new ArrayList<String>();
        List<String> tr_ref_date = new ArrayList<String>();
        List<String> tr_ref_points = new ArrayList<String>();

        Spinner sp1 = (Spinner) findViewById(R.id.transpinner);
        ArrayAdapter<String> adp1 = new ArrayAdapter<String>(Trandetails.this, android.R.layout.simple_spinner_dropdown_item, tr_ref_list);
        sp1.setAdapter(adp1);

        TableLayout stk1 = (TableLayout) findViewById(R.id.table_main2);
        stk1.setBackgroundColor(Color.WHITE);
        TextView tr_date =(TextView) findViewById(R.id.tdate);
        TextView tr_points =(TextView) findViewById(R.id.tpnts);

        Button bkbtn = (Button) findViewById(R.id.t2_back);

        RequestQueue queue = Volley.newRequestQueue(Trandetails.this);
        String url = "http://192.168.1.175:8080/loyaltyfirst/Transactions.jsp?cid=" + cid;

        StringRequest trareq = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String s) {
                if(s.equals("error")){
                    Toast.makeText(Trandetails.this, "Error Fetching Customer details", Toast.LENGTH_SHORT).show();
                    finish();
                }
                else{
                    String p_str = Html.fromHtml(s).toString();
                    p_str = p_str.substring(12);
                    p_str.trim();


                    String[] t_Rows = p_str.trim().split("#");

                    //Toast.makeText(Trandetails.this, p_str, Toast.LENGTH_SHORT).show();

                    for(int i=0; i<t_Rows.length; i++){
                        tr_ref_list.add(t_Rows[i].trim().split(",")[0]);
                        tr_ref_date.add(t_Rows[i].trim().split(",")[1].trim().split(" ")[0]);
                        tr_ref_points.add(t_Rows[i].trim().split(",")[2]);

                    }
                    adp1.notifyDataSetChanged();



                }
            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(Trandetails.this, error.getMessage(), Toast.LENGTH_SHORT).show();

                    }
                });
        queue.add(trareq);



        sp1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                //Toast.makeText(Trandetails.this, adapterView.getSelectedItem().toString(), Toast.LENGTH_SHORT).show();
                sp1.setSelection(i);
                tr_date.setText(tr_ref_date.get(i));
                tr_points.setText((tr_ref_points.get(i)) + " Points");
                RequestQueue queue1 = Volley.newRequestQueue(Trandetails.this);
                String url1 = "http://192.168.1.175:8080/loyaltyfirst/TransactionDetails.jsp?tref=" + tr_ref_list.get(i);

                StringRequest trareq1 = new StringRequest(Request.Method.GET, url1, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String s) {
                        if(s.equals("error")){
                            Toast.makeText(Trandetails.this, "Error Fetching Customer details", Toast.LENGTH_SHORT).show();
                            finish();
                        }
                        else{
                            String p_str = Html.fromHtml(s).toString();
                            p_str = p_str.substring(19);
                            p_str.trim();


                            String[] t_Rows = p_str.trim().split("#");

                            //Toast.makeText(Trandetails.this, t_Rows[0], Toast.LENGTH_SHORT).show();
                            stk1.removeAllViews();
                            TableRow tbrow0 = new TableRow(Trandetails.this);
                            tbrow0.setBackgroundColor(Color.WHITE);
                            tbrow0.setGravity(View.FOCUS_LEFT);

                            TextView tv0 = new TextView(Trandetails.this);
                            tv0.setText("Prod. Name ");
                            tv0.setTextColor(Color.BLACK);
                            tv0.setGravity(Gravity.LEFT);

                            tbrow0.addView(tv0);
                            tbrow0.setBackgroundColor(Color.LTGRAY);
                            tbrow0.setPadding(0,10,0,10);
                            TextView tv1 = new TextView(Trandetails.this);
                            tv1.setText("Quantity ");
                            tv1.setTextColor(Color.BLACK);
                            tv1.setGravity(Gravity.LEFT);
                            tv1.setPadding(5,0,0,0);
                            tbrow0.addView(tv1);
                            TextView tv2 = new TextView(Trandetails.this);
                            tv2.setText("Points ");
                            tv2.setTextColor(Color.BLACK);
                            tv2.setGravity(Gravity.LEFT);
                            tbrow0.addView(tv2);
                            stk1.addView(tbrow0);


                            for(int i=0; i<t_Rows.length; i++){
                                String[] t_col = t_Rows[i].trim().split(",");

                                TableRow tbrow = new TableRow(Trandetails.this);
                                TextView t1v = new TextView(Trandetails.this);
                                t1v.setText(t_col[2] + "  ");
                                t1v.setTextColor(Color.BLACK);
                                t1v.setGravity(Gravity.LEFT);
                                t1v.setEms(9);
                                tbrow.addView(t1v);
                                TextView t2v = new TextView(Trandetails.this);

                                t2v.setText(t_col[4]);
                                t2v.setTextColor(Color.BLACK);
                                t2v.setGravity(Gravity.CENTER_HORIZONTAL);
                                tbrow.addView(t2v);
                                TextView t3v = new TextView(Trandetails.this);
                                t3v.setText(t_col[3] + "  ");
                                t3v.setTextColor(Color.BLACK);
                                t3v.setGravity(Gravity.LEFT);
                                tbrow.addView(t3v);
                                stk1.addView(tbrow);


                            }

                        }
                    }
                },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                Toast.makeText(Trandetails.this, error.getMessage(), Toast.LENGTH_SHORT).show();

                            }
                        });
                queue1.add(trareq1);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                Toast.makeText(Trandetails.this, "No Selection", Toast.LENGTH_SHORT).show();
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