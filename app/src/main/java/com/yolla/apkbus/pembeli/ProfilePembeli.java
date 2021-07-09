package com.yolla.apkbus.pembeli;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import com.yolla.apkbus.R;
import com.yolla.apkbus.admin.HomeAdminActivity;
import com.yolla.apkbus.admin.Profile;
import com.yolla.apkbus.session.PrefSetting;

public class ProfilePembeli extends AppCompatActivity {

    TextView txtUserName, txtNama, txtGmail, txtNoHP;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_pembeli);

        getSupportActionBar().setTitle("Profile Pembeli");

        txtUserName = (TextView) findViewById(R.id.edtUserName);
        txtNama = (TextView) findViewById(R.id.edtNama);
        txtGmail = (TextView) findViewById(R.id.edtGmail);
        txtNoHP = (TextView) findViewById(R.id.edtNoHP);

        txtUserName.setText(PrefSetting.userName);
        txtNama.setText(PrefSetting.namaLengkap);
        txtGmail.setText(PrefSetting.email);
        txtNoHP.setText(PrefSetting.noTelp);
    }

    @Override
    public void onBackPressed() {
        Intent i = new Intent(ProfilePembeli.this, HomePembeli.class);
        startActivity(i);
        finish();
    }
}