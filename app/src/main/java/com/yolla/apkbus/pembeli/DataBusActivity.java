package com.yolla.apkbus.pembeli;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.yolla.apkbus.R;
import com.yolla.apkbus.adapter.AdapterBus;
import com.yolla.apkbus.admin.HomeAdminActivity;
import com.yolla.apkbus.model.ModelBus;
import com.yolla.apkbus.server.BaseURL;
import com.yolla.apkbus.session.PrefSetting;
import com.yolla.apkbus.session.SessionManager;
import com.yolla.apkbus.users.LoginActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class DataBusActivity extends AppCompatActivity {

    ProgressDialog pDialog;

    AdapterBus adapter;
    ListView list;

    ArrayList<ModelBus> newsList = new ArrayList<ModelBus>();
    private RequestQueue mRequestQueue;

//    FloatingActionButton floatingCart;

    SessionManager session;
    SharedPreferences prefs;
    PrefSetting prefSetting;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_data_bus2);

        prefSetting = new PrefSetting(this);
        prefs = prefSetting.getSharePreferences();

        session = new SessionManager(DataBusActivity.this);

        prefSetting.isLogin(session, prefs);

        getSupportActionBar().setTitle("Data Bus");
        mRequestQueue = Volley.newRequestQueue(this);
        pDialog = new ProgressDialog(this);
        pDialog.setCancelable(false);

        list = (ListView) findViewById(R.id.array_list);
//        floatingCart = (FloatingActionButton) findViewById(R.id.cart);
//
//        floatingCart.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
////                session.setLogin(false);
////                session.setSessid(0);
////                Intent i = new Intent(DataBusActivity.this, Cart.class);
////                startActivity(i);
////                finish();
//            }
//        });

        newsList.clear();
        adapter = new AdapterBus(DataBusActivity.this, newsList);
        list.setAdapter(adapter);
        getAllBus();
    }

    @Override
    public void onBackPressed() {
        Intent i = new Intent(DataBusActivity.this, HomePembeli.class);
        startActivity(i);
        finish();
    }

    private void getAllBus() {
        // Pass second argument as "null" for GET requests
        pDialog.setMessage("Loading");
        showDialog();
        JsonObjectRequest req = new JsonObjectRequest(Request.Method.GET, BaseURL.dataBus, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        hideDialog();
                        try {
                            boolean status = response.getBoolean("error");
                            if (status == false) {
                                Log.d("data bus = ", response.toString());
                                String data = response.getString("data");
                                JSONArray jsonArray = new JSONArray(data);
                                for (int i = 0; i < jsonArray.length(); i++) {
                                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                                    final ModelBus bus = new ModelBus();
                                    final String _id = jsonObject.getString("_id");
                                    final String namaBus = jsonObject.getString("namaBus");
                                    final String kodeBus = jsonObject.getString("kodeBus");
                                    final String jurusanBus = jsonObject.getString("jurusanBus");
                                    final String jamOperasional = jsonObject.getString("jamOperasional");
                                    final String hargaTiket = jsonObject.getString("hargaTiket");
                                    final String gambar = jsonObject.getString("gambar");
                                    bus.setKodeBus(kodeBus);
                                    bus.setNamaBus(namaBus);
                                    bus.setJurusanBus(jurusanBus);
                                    bus.setJamOperasional(jamOperasional);
                                    bus.setHargaTiket(hargaTiket);
                                    bus.setGambar(gambar);
                                    bus.set_id(_id);

                                    list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                        @Override
                                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                            // TODO Auto-generated method stub
                                            Intent a = new Intent(DataBusActivity.this, DetailBus.class);
                                            a.putExtra("kodeBus", newsList.get(position).getKodeBus());
                                            a.putExtra("_id", newsList.get(position).get_id());
                                            a.putExtra("namaBus", newsList.get(position).getNamaBus());
                                            a.putExtra("jurusanBus", newsList.get(position).getJurusanBus());
                                            a.putExtra("jamOperasional", newsList.get(position).getJamOperasional());
                                            a.putExtra("hargaTiket", newsList.get(position).getHargaTiket());
                                            a.putExtra("gambar", newsList.get(position).getGambar());
                                            startActivity(a);
                                        }
                                    });
                                    newsList.add(bus);
                                }
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        adapter.notifyDataSetChanged();
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.e("Error: ", error.getMessage());
                hideDialog();
            }
        });

        /* Add your Requests to the RequestQueue to execute */
        mRequestQueue.add(req);
    }

    private void showDialog() {
        if (!pDialog.isShowing())
            pDialog.show();
    }

    private void hideDialog() {
        if (pDialog.isShowing())
            pDialog.dismiss();
    }
}