package com.sample.blackbox;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;


public class Setting extends Activity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.setting);

//        ImageButton btnGps = (ImageButton) findViewById(R.id.btnGps);
//        btnGps.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(Setting.this,Gps.class);
//                startActivity(intent);
//            }
//        });
//
//        ImageButton btnShock = (ImageButton) findViewById(R.id.btnShock);
//        btnShock.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(Setting.this, Shock.class);
//                startActivity(intent);
//            }
//        });

    }
}