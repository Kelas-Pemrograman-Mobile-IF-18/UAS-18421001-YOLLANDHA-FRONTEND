package com.yolla.apkbus.pembeli;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.squareup.picasso.Picasso;
import com.yolla.apkbus.R;
import com.yolla.apkbus.server.BaseURL;
import com.yolla.apkbus.session.PrefSetting;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

public class Pembayaran extends AppCompatActivity {

    EditText edtKodeBus, edtNamaBus, edtJurusanBus, edtJamOperasional, edtHargaTiket, edtJumlahTiket, edtJumlahHarga;
    Button bayar;

    String strKodeBus, strNamaBus, strJurusanBus, strJamOperasional, strHargaTiket, strJumlahTiket, strJumlahHarga, _id;


    private RequestQueue mRequest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pembayaran);

        mRequest = Volley.newRequestQueue(this);

        edtKodeBus = (EditText) findViewById(R.id.edtKodeBus);
        edtNamaBus = (EditText) findViewById(R.id.edtNamaBus);
        edtJurusanBus = (EditText) findViewById(R.id.edtJurusanBus);
        edtJamOperasional = (EditText) findViewById(R.id.edtJamOperasional);
        edtHargaTiket = (EditText) findViewById(R.id.edtHargaTiket);
        edtJumlahTiket = (EditText) findViewById(R.id.edtJumlahTiket);
        edtJumlahHarga = (EditText) findViewById(R.id.edtJumlahHarga);

        bayar = (Button) findViewById(R.id.bayar);

        Intent i = getIntent();
        strKodeBus = i.getStringExtra("kodeBus");
        strNamaBus = i.getStringExtra("namaBus");
        strJurusanBus = i.getStringExtra("jurusanBus");
        strJamOperasional = i.getStringExtra("jamOperasional");
        strHargaTiket = i.getStringExtra("hargaTiket");
        strJumlahTiket = i.getStringExtra("jumlahTiket");
        strJumlahHarga = i.getStringExtra("jumlahHarga");
        _id = i.getStringExtra("_id");

        String usernameUser = PrefSetting.userName;

        edtKodeBus.setText(strKodeBus);
        edtNamaBus.setText(strNamaBus);
        edtJurusanBus.setText(strJurusanBus);
        edtJamOperasional.setText(strJamOperasional);
        edtHargaTiket.setText(strHargaTiket);
        edtJumlahTiket.setText(strJumlahTiket);
        edtJumlahHarga.setText(strJumlahHarga);
}}