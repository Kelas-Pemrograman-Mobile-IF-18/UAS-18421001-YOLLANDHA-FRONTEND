package com.yolla.apkbus.pembeli;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

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
import com.yolla.apkbus.admin.ActivityDataBus;
import com.yolla.apkbus.admin.EditBusDanHapusActivity;
import com.yolla.apkbus.admin.HomeAdminActivity;
import com.yolla.apkbus.admin.InputDataBus;
import com.yolla.apkbus.admin.Profile;
import com.yolla.apkbus.model.ModelBus;
import com.yolla.apkbus.server.BaseURL;
import com.yolla.apkbus.session.PrefSetting;
import com.yolla.apkbus.session.SessionManager;
import com.yolla.apkbus.users.LoginActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class HomePembeli extends AppCompatActivity {

    SessionManager session;
    SharedPreferences prefs;
    PrefSetting prefSetting;
    CardView cardExit, cardDataBus, cardInputBus, cardProfile, Card_cart, cardTransaksi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_pembeli);

        prefSetting = new PrefSetting(this);
        prefs = prefSetting.getSharePreferences();

        session = new SessionManager(HomePembeli.this);

        prefSetting.isLogin(session, prefs);


        cardExit = (CardView) findViewById(R.id.cardExit);
        cardDataBus = (CardView) findViewById(R.id.cardDataBus);
        cardInputBus = (CardView) findViewById(R.id.cardInputBus);
        cardProfile = (CardView) findViewById(R.id.cardProfile);
        Card_cart = (CardView) findViewById(R.id.Card_Cart);

        cardExit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                session.setLogin(false);
                session.setSessid(0);
                Intent i = new Intent(HomePembeli.this, LoginActivity.class);
                startActivity(i);
                finish();
            }
        });

        cardDataBus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(HomePembeli.this, DataBusActivity.class);
                startActivity(i);
                finish();
            }
        });

        cardProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(HomePembeli.this, ProfilePembeli.class);
                startActivity(i);
                finish();
            }
        });

        Card_cart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(HomePembeli.this, Cart.class);
                startActivity(i);
                finish();
            }
        });
    }
}