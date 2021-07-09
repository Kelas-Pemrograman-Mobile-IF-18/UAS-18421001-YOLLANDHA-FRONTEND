package com.yolla.apkbus.admin;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
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
import com.yolla.apkbus.R;
import com.yolla.apkbus.adapter.AdapterBus;
import com.yolla.apkbus.model.ModelBus;
import com.yolla.apkbus.server.BaseURL;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class ActivityDataBus extends AppCompatActivity {

    ProgressDialog pDialog;

    AdapterBus adapter;
    ListView list;

    ArrayList<ModelBus> newsList = new ArrayList<ModelBus>();
    private RequestQueue mRequestQueue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_data_bus);

        getSupportActionBar().setTitle("Data Bus");
        mRequestQueue = Volley.newRequestQueue(this);
        pDialog = new ProgressDialog(this);
        pDialog.setCancelable(false);

        list = (ListView) findViewById(R.id.array_list);
        newsList.clear();
        adapter = new AdapterBus(ActivityDataBus.this, newsList);
        list.setAdapter(adapter);
        getAllBus();
    }

    @Override
    public void onBackPressed() {
        Intent i = new Intent(ActivityDataBus.this, HomeAdminActivity.class);
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
                                            Intent a = new Intent(ActivityDataBus.this, EditBusDanHapusActivity.class);
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