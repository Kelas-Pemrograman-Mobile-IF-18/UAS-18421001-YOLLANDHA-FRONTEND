package com.yolla.apkbus.admin;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;

import com.yolla.apkbus.R;
import com.yolla.apkbus.session.PrefSetting;
import com.yolla.apkbus.session.SessionManager;
import com.yolla.apkbus.users.LoginActivity;

public class HomeAdminActivity extends AppCompatActivity {

    SessionManager session;
    SharedPreferences prefs;
    PrefSetting prefSetting;
    CardView cardExit, cardDataBus, cardInputBus, cardProfile, Card_cart, cardTransaksi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_admin);

        prefSetting = new PrefSetting(this);
        prefs = prefSetting.getSharePreferences();

        session = new SessionManager(HomeAdminActivity.this);

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
                Intent i = new Intent(HomeAdminActivity.this, LoginActivity.class);
                startActivity(i);
                finish();
            }
        });

        cardDataBus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(HomeAdminActivity.this, ActivityDataBus.class);
                startActivity(i);
                finish();
            }
        });

        cardInputBus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(HomeAdminActivity.this, InputDataBus.class);
                startActivity(i);
                finish();
            }
        });

        cardProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(HomeAdminActivity.this, Profile.class);
                startActivity(i);
                finish();
            }
        });

        Card_cart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(HomeAdminActivity.this, CartAdmin.class);
                startActivity(i);
                finish();
            }
        });
    }
}