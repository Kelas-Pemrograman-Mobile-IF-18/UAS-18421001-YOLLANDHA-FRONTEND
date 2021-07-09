package com.yolla.apkbus.pembeli;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.ornach.nobobutton.NoboButton;
import com.squareup.picasso.Picasso;
import com.yolla.apkbus.R;
import com.yolla.apkbus.admin.ActivityDataBus;
import com.yolla.apkbus.admin.InputDataBus;
import com.yolla.apkbus.server.BaseURL;
import com.yolla.apkbus.session.PrefSetting;
import com.yolla.apkbus.users.LoginActivity;
import com.yolla.apkbus.users.RegisActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

public class DetailBus extends AppCompatActivity {

    EditText edtKodeBus, edtNamaBus, edtJurusanBus, edtJamOperasional, edtHargaTiket, edtJumlahTiket;
    ImageView imgGambar;
    Button btnTambahKekeranjang;

    String strKodeBus, strNamaBus, strJurusanBus, strJamOperasional, strHargaTiket, strGambar, _id;


    private RequestQueue mRequest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_bus);

        mRequest = Volley.newRequestQueue(this);

        edtKodeBus = (EditText) findViewById(R.id.edtKodeBus);
        edtNamaBus = (EditText) findViewById(R.id.edtNamaBus);
        edtJurusanBus = (EditText) findViewById(R.id.edtJurusanBus);
        edtJamOperasional = (EditText) findViewById(R.id.edtJamOperasional);
        edtHargaTiket = (EditText) findViewById(R.id.edtHargaTiket);
        edtJumlahTiket = (EditText) findViewById(R.id.edtJumlahTiket);

        imgGambar = (ImageView) findViewById(R.id.gambar);

        btnTambahKekeranjang = (Button) findViewById(R.id.tambahKekeranjang);

        Intent i = getIntent();
        strKodeBus = i.getStringExtra("kodeBus");
        strNamaBus = i.getStringExtra("namaBus");
        strJurusanBus = i.getStringExtra("jurusanBus");
        strJamOperasional = i.getStringExtra("jamOperasional");
        strHargaTiket = i.getStringExtra("hargaTiket");
        strGambar = i.getStringExtra("gambar");
        _id = i.getStringExtra("_id");

        String usernameUser = PrefSetting.userName;

        edtKodeBus.setText(strKodeBus);
        edtNamaBus.setText(strNamaBus);
        edtJurusanBus.setText(strJurusanBus);
        edtJamOperasional.setText(strJamOperasional);
        edtHargaTiket.setText(strHargaTiket);
        Picasso.get().load(BaseURL.baseURL + "gambar/" + strGambar)
                .into(imgGambar);

        btnTambahKekeranjang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String datastrUserName = usernameUser;
                String datastrKodeBus = strKodeBus;
                String datastrNamaBus = strNamaBus;
                String datastrJurusanBus = strJurusanBus;
                String datastrJamOperasional = strJamOperasional;
                String datastrHargaTiket = strHargaTiket;
                String datastrJumlahTiket = edtJumlahTiket.getText().toString();
                if (datastrJumlahTiket.isEmpty()) {
                    Toast.makeText(getApplicationContext(), "jumlah tiket kosong nih", Toast.LENGTH_LONG).show();
                } else {
                    TambahKekeranjang(datastrUserName, datastrKodeBus, datastrNamaBus, datastrJurusanBus, datastrJamOperasional, datastrHargaTiket, datastrJumlahTiket);
                }
            }
        });
    }

    public void TambahKekeranjang(String datastrUserName, String datastrKodeBus, String datastrNamaBus, String datastrJurusanBus, String datastrJamOperasional, String datastrHargaTiket, String datastrJumlahTiket){
        // Post params to be sent to the server
        HashMap<String, String> params = new HashMap<String, String>();
        params.put("userName", datastrUserName);
        params.put("kodeBus", datastrKodeBus);
        params.put("namaBus", datastrNamaBus);
        params.put("jurusanBus", datastrJurusanBus);
        params.put("jamOperasional", datastrJamOperasional);
        params.put("hargaTiket", datastrHargaTiket);
        params.put("jumlahTiket", datastrJumlahTiket);

//        System.out.println("DATA"+datastrUserName);

        final JsonObjectRequest req = new JsonObjectRequest(Request.Method.POST, BaseURL.insertCart, new JSONObject(params),
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        // input data
                        System.out.println("DATA = "+response);

                        try {
                            JSONObject jsonObject = new JSONObject(response.toString());
                            String strMsg = jsonObject.getString("msg");
                            boolean status = jsonObject.getBoolean("error");
                            if(status == false){
                                Toast.makeText(getApplicationContext(), "berhasil tambah tiket donggg", Toast.LENGTH_LONG).show();
                                Intent i = new Intent(DetailBus.this, DataBusActivity.class);
                                startActivity(i);
                                finish();
                            }else{
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
            }
        });
        mRequest.add(req);
    }
}