package com.yolla.apkbus.pembeli;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
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
import com.yolla.apkbus.adapter.AdapterCart;
import com.yolla.apkbus.admin.ActivityDataBus;
import com.yolla.apkbus.admin.EditBusDanHapusActivity;
import com.yolla.apkbus.admin.HomeAdminActivity;
import com.yolla.apkbus.model.ModelBus;
import com.yolla.apkbus.model.ModelCart;
import com.yolla.apkbus.server.BaseURL;
import com.yolla.apkbus.session.PrefSetting;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class Cart extends AppCompatActivity {

    ProgressDialog pDialog;

    AdapterCart adapter;
    ListView list;

    ArrayList<ModelCart> newsList = new ArrayList<ModelCart>();
    private RequestQueue mRequestQueue;

    String userNameUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

        getSupportActionBar().setTitle("Keranjang-Ku");
        mRequestQueue = Volley.newRequestQueue(this);
        pDialog = new ProgressDialog(this);
        pDialog.setCancelable(false);

        list = (ListView) findViewById(R.id.array_list);
        newsList.clear();
        adapter = new AdapterCart(Cart.this, newsList);
        list.setAdapter(adapter);
        userNameUser = PrefSetting.userName;
        getAllCart(userNameUser);
    }

    @Override
    public void onBackPressed() {
        Intent i = new Intent(Cart.this, HomePembeli.class);
        startActivity(i);
        finish();
    }

    private void getAllCart(String userNameUser) {
        // Pass second argument as "null" for GET requests
        pDialog.setMessage("Loading");
        showDialog();
        JsonObjectRequest req = new JsonObjectRequest(Request.Method.GET, BaseURL.dataCart + userNameUser, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        hideDialog();
                        try {
                            boolean status = response.getBoolean("error");
                            if (status == false) {
                                Log.d("Keranjang-Ku = ", response.toString());
                                String data = response.getString("data");
                                JSONArray jsonArray = new JSONArray(data);
                                for (int i = 0; i < jsonArray.length(); i++) {
                                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                                    final ModelCart cart = new ModelCart();
                                    final String _id = jsonObject.getString("_id");
                                    final String namaBus = jsonObject.getString("namaBus");
                                    final String kodeBus = jsonObject.getString("kodeBus");
                                    final String jurusanBus = jsonObject.getString("jurusanBus");
                                    final String jamOperasional = jsonObject.getString("jamOperasional");
                                    final String hargaTiket = jsonObject.getString("hargaTiket");
                                    final String jumlahTiket = jsonObject.getString("jumlahTiket");
//                                    final String jumlahHarga = jsonObject.getString("jumlahHarga");
//                                    final String gambar = jsonObject.getString("gambar");
                                    cart.setKodeBus(kodeBus);
                                    cart.setNamaBus(namaBus);
                                    cart.setJurusanBus(jurusanBus);
                                    cart.setJamOperasional(jamOperasional);
                                    cart.setHargaTiket(hargaTiket);
                                    cart.setJumlahTiket(jumlahTiket);
                                    int a = Integer.parseInt(hargaTiket);
                                    int b = Integer.parseInt(jumlahTiket);
                                    int ab = a*b;
                                    cart.setJumlahHarga(String.valueOf(ab));
//                                    cart.setGambar(gambar);
                                    cart.set_id(_id);

                                    list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                        @Override
                                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                            // TODO Auto-generated method stub
                                            Intent a = new Intent(Cart.this, Pembayaran.class);
                                            a.putExtra("kodeBus", newsList.get(position).getKodeBus());
                                            a.putExtra("_id", newsList.get(position).get_id());
                                            a.putExtra("namaBus", newsList.get(position).getNamaBus());
                                            a.putExtra("jurusanBus", newsList.get(position).getJurusanBus());
                                            a.putExtra("jamOperasional", newsList.get(position).getJamOperasional());
                                            a.putExtra("hargaTiket", newsList.get(position).getHargaTiket());
                                            a.putExtra("jumlahTiket", newsList.get(position).getJumlahTiket());
                                            a.putExtra("jumlahHarga", newsList.get(position).getJumlahHarga());
//                                            a.putExtra("gambar", newsList.get(position).getGambar());
                                            startActivity(a);
                                        }
                                    });
                                    newsList.add(cart);
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