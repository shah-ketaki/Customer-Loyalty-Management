package com.example.project8;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import android.text.Html;
import android.graphics.Bitmap;
import android.widget.ImageView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;



public class Home extends AppCompatActivity {
    String cid ="";
    String c_nm ="";
    String rwp = "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_home);

        Intent intent=getIntent();
        cid=intent.getStringExtra("CID");

        TextView c_name =(TextView) findViewById(R.id.custname);
        TextView r_points =(TextView) findViewById(R.id.custpoints);
        Button exbtn = (Button) findViewById(R.id.exitBtn);
        Button altrn = (Button) findViewById(R.id.allTran);
        Button trndet = (Button) findViewById(R.id.txnDetail);
        Button reddet = (Button) findViewById(R.id.redDetails);
        Button adfml = (Button) findViewById(R.id.addToFam);
        ImageView imageView=findViewById(R.id.imageView);


        RequestQueue queue = Volley.newRequestQueue(Home.this);
        String url = "http://192.168.1.175:8080/loyaltyfirst/Info.jsp?cid=" + cid;

        //Toast.makeText(Home.this, url, Toast.LENGTH_SHORT).show();

        StringRequest inforeq = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String s) {
                if(s.equals("error")){
                    Toast.makeText(Home.this, "Error Fetching Customer details", Toast.LENGTH_SHORT).show();
                    finish();
                }
                else{
                    String p_str = Html.fromHtml(s).toString();
                    p_str = p_str.substring(5);
                    p_str.trim();
                    //Toast.makeText(Home.this, p_str, Toast.LENGTH_SHORT).show();
                    String[] result = p_str.trim().split(",");
                    c_nm = result[0];
                    rwp = result[1];
                    c_name.setText(c_nm);
                    r_points.setText(rwp);
                }
            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(Home.this, error.getMessage(), Toast.LENGTH_SHORT).show();

                    }
                });
        queue.add(inforeq);

        String url2="http://192.168.1.175:8080/images/"+cid+".jpg";
        ImageRequest request2=new ImageRequest(url2, new Response.Listener<Bitmap>() {
            @Override
            public void onResponse(Bitmap bitmap) {
                imageView.setImageBitmap(bitmap);
            }
        },0,0,null,null);
        queue.add(request2);



        exbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        altrn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(Home.this,Transaction.class);
                intent.putExtra("CID",cid);
                startActivity(intent);
            }
        });

        trndet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(Home.this,Trandetails.class);
                intent.putExtra("CID",cid);
                startActivity(intent);
            }
        });

        reddet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(Home.this,Prize.class);
                intent.putExtra("CID",cid);
                startActivity(intent);
            }
        });
        adfml.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(Home.this,Family.class);
                intent.putExtra("CID",cid);
                startActivity(intent);
            }
        });



    }
}