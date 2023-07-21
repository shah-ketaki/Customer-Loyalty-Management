package com.example.project8;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Html;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.Toast;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

public class Transaction extends AppCompatActivity {
    String cid ="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_transaction);


        Intent intent=getIntent();
        cid=intent.getStringExtra("CID");

        Button bkbtn = (Button) findViewById(R.id.t1_back);

        RequestQueue queue = Volley.newRequestQueue(Transaction.this);
        String url = "http://192.168.1.175:8080/loyaltyfirst/Transactions.jsp?cid=" + cid;

        StringRequest trareq = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String s) {
                if(s.equals("error")){
                    Toast.makeText(Transaction.this, "Error Fetching Customer details", Toast.LENGTH_SHORT).show();
                    finish();
                }
                else{
                    String p_str = Html.fromHtml(s).toString();
                    p_str = p_str.substring(12);
                    p_str.trim();


                    String[] t_Rows = p_str.trim().split("#");

                    //Toast.makeText(Transaction.this, p_str, Toast.LENGTH_SHORT).show();

                    TableLayout stk = (TableLayout) findViewById(R.id.table_main);
                    stk.setBackgroundColor(Color.WHITE);
                    TableRow tbrow0 = new TableRow(Transaction.this);
                    tbrow0.setBackgroundColor(Color.WHITE);
                    tbrow0.setGravity(View.FOCUS_LEFT);

                    TextView tv0 = new TextView(Transaction.this);
                    tv0.setText("TXN REF ");
                    tv0.setTextColor(Color.BLACK);
                    tv0.setGravity(Gravity.LEFT);
                    tbrow0.setBackgroundColor(Color.LTGRAY);
                    tbrow0.setPadding(0,10,0,10);
                    tbrow0.addView(tv0);
                    TextView tv1 = new TextView(Transaction.this);
                    tv1.setText("Date ");
                    tv1.setTextColor(Color.BLACK);
                    tv1.setGravity(Gravity.LEFT);
                    tbrow0.addView(tv1);
                    TextView tv2 = new TextView(Transaction.this);
                    tv2.setText("Points ");
                    tv2.setTextColor(Color.BLACK);
                    tv2.setGravity(Gravity.LEFT);
                    tbrow0.addView(tv2);
                    TextView tv3 = new TextView(Transaction.this);
                    tv3.setText("Total ");
                    tv3.setTextColor(Color.BLACK);
                    tv3.setGravity(Gravity.LEFT);
                    tbrow0.addView(tv3);
                    stk.addView(tbrow0);

                    for(int i=0; i<t_Rows.length; i++){
                        String[] t_col = t_Rows[i].trim().split(",");

                        TableRow tbrow = new TableRow(Transaction.this);
                        TextView t1v = new TextView(Transaction.this);
                        t1v.setText(" " + t_col[0] + "  ");
                        t1v.setTextColor(Color.BLACK);
                        t1v.setGravity(Gravity.LEFT);
                        tbrow.addView(t1v);
                        TextView t2v = new TextView(Transaction.this);

                        t2v.setText( " " + t_col[1].trim().split(" ")[0] + "  ");
                        t2v.setTextColor(Color.BLACK);
                        t2v.setGravity(Gravity.LEFT);
                        tbrow.addView(t2v);
                        TextView t3v = new TextView(Transaction.this);
                        t3v.setText(" " + t_col[2] + "  ");
                        t3v.setTextColor(Color.BLACK);
                        t3v.setGravity(Gravity.LEFT);
                        tbrow.addView(t3v);
                        TextView t4v = new TextView(Transaction.this);
                        t4v.setText(" $" + t_col[3]+ "  ");
                        t4v.setTextColor(Color.BLACK);
                        t4v.setGravity(Gravity.LEFT);
                        tbrow.addView(t4v);
                        stk.addView(tbrow);
                    }


                }
            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(Transaction.this, error.getMessage(), Toast.LENGTH_SHORT).show();

                    }
                });
        queue.add(trareq);


        bkbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });


    }
}