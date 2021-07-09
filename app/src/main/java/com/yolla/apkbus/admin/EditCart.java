package com.yolla.apkbus.admin;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.yolla.apkbus.R;
import com.yolla.apkbus.server.BaseURL;
import com.yolla.apkbus.session.PrefSetting;

import org.json.JSONException;
import org.json.JSONObject;

public class EditCart extends AppCompatActivity {

    EditText edtKodeBus, edtNamaBus, edtJurusanBus, edtJamOperasional, edtHargaTiket, edtJumlahTiket, edtJumlahHarga, edtUserName;
    Button hapusCart;

    String strKodeBus, strNamaBus, strJurusanBus, strJamOperasional, strHargaTiket, strJumlahTiket, strJumlahHarga, _id, strUserName;

    private RequestQueue mRequestQueue;

    ProgressDialog pDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_cart);

        mRequestQueue = Volley.newRequestQueue(this);

        edtUserName = (EditText) findViewById(R.id.edtUserName);
        edtKodeBus = (EditText) findViewById(R.id.edtKodeBus);
        edtNamaBus = (EditText) findViewById(R.id.edtNamaBus);
        edtJurusanBus = (EditText) findViewById(R.id.edtJurusanBus);
        edtJamOperasional = (EditText) findViewById(R.id.edtJamOperasional);
        edtHargaTiket = (EditText) findViewById(R.id.edtHargaTiket);
        edtJumlahTiket = (EditText) findViewById(R.id.edtJumlahTiket);
        edtJumlahHarga = (EditText) findViewById(R.id.edtJumlahHarga);


        hapusCart = (Button) findViewById(R.id.hapus);

        Intent i = getIntent();
        strUserName= i.getStringExtra("userName");
        strKodeBus = i.getStringExtra("kodeBus");
        strNamaBus = i.getStringExtra("namaBus");
        strJurusanBus = i.getStringExtra("jurusanBus");
        strJamOperasional = i.getStringExtra("jamOperasional");
        strHargaTiket = i.getStringExtra("hargaTiket");
        strJumlahTiket = i.getStringExtra("jumlahTiket");
        strJumlahHarga = i.getStringExtra("jumlahHarga");
        _id = i.getStringExtra("_id");

        edtUserName.setText(strUserName);
        edtKodeBus.setText(strKodeBus);
        edtNamaBus.setText(strNamaBus);
        edtJurusanBus.setText(strJurusanBus);
        edtJamOperasional.setText(strJamOperasional);
        edtHargaTiket.setText(strHargaTiket);
        edtJumlahTiket.setText(strJumlahTiket);
        edtJumlahHarga.setText(strJumlahHarga);

        hapusCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(EditCart.this);

                builder.setTitle("Konfirmasi");
                builder.setMessage("Yakin ingin menghapus Pesanan " + strUserName + "nih ? ");

                builder.setPositiveButton("Hapus dong", new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialog, int which) {
                        // Do nothing but close the dialog
                        hapusCart();
                    }
                });
                builder.setNegativeButton("Tidak dong", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        // Do nothing
                        dialog.dismiss();
                    }
                });

                AlertDialog alert = builder.create();
                alert.show();

            }
        });
    }

    public void hapusCart(){

        pDialog.setMessage("Mohon Tunggu nih.....");
        showDialog();

        JsonObjectRequest req = new JsonObjectRequest(Request.Method.DELETE, BaseURL.hapusCart, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        hideDialog();
                        try {
                            String strMsg = response.getString("msg");
                            boolean status= response.getBoolean("error");
                            if(status == false){
                                Toast.makeText(getApplicationContext(), strMsg, Toast.LENGTH_LONG).show();
                                Intent i = new Intent(EditCart.this, CartAdmin.class);
                                startActivity(i);
                                finish();
                            }else {
                                Toast.makeText(getApplicationContext(), strMsg, Toast.LENGTH_LONG).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.e("Error: ", error.getMessage());
                hideDialog();
            }
        });
        mRequestQueue.add(req);
    }

    private void showDialog(){
        if(!pDialog.isShowing()){
            pDialog.show();
        }
    }

    private void hideDialog(){
        if(pDialog.isShowing()){
            pDialog.dismiss();
        }
    }
}