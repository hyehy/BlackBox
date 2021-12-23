package com.sample.blackbox;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;

public class MainActivity extends Activity {
    int versionCode = BuildConfig.VERSION_CODE;
    String versionName = BuildConfig.VERSION_NAME;

    String test;
    String DirectoryName;
    ImageButton btnNewActivity;
    ImageButton btnNewActivity1;
    ImageButton btnNewActivity2;
    ImageButton btnNewActivity3;
    ImageButton btnNewActivity4;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setTitle("Black Box 동영상 플레이어 v" + versionName);
        Log.d("test", "Black Box 동영상 플레이어 시작");

        ActivityCompat.requestPermissions(this, new String[] {
                android.Manifest.permission.WRITE_EXTERNAL_STORAGE},MODE_PRIVATE);

        btnNewActivity = (ImageButton) findViewById(R.id.btnNewActivity);
        btnNewActivity1 = (ImageButton) findViewById(R.id.btnNewActivity1);
        btnNewActivity2 = (ImageButton) findViewById(R.id.btnNewActivity2);
        btnNewActivity3 = (ImageButton) findViewById(R.id.btnNewActivity3);
        btnNewActivity4 = (ImageButton) findViewById(R.id.btnNewActivity4);



        btnNewActivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this,SampleVideoRecorder.class);
                startActivity(intent);
            }
        });
        btnNewActivity1.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                test = SDCard.getExternalSDCardPath();
                if(test == null){
                    Toast.makeText(MainActivity.this, "외장형 SD카드가 장착되지 않았습니다.",
                            Toast.LENGTH_LONG).show();
                } else {
                    Intent intent = new Intent(getApplicationContext(), SetDirectoryName.class);
                    intent.putExtra("Name", test);
                    startActivityForResult(intent, 0);
                }
            }
        });

        btnNewActivity2.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                test = Environment.getExternalStorageDirectory().getAbsolutePath();
                Intent intent = new Intent(getApplicationContext(), SetDirectoryName.class);
                intent.putExtra("Name", test);
                startActivityForResult(intent, 0);
            }
        });

        btnNewActivity3.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if(DirectoryName != null){
                    Intent intent = new Intent(getApplicationContext(), FileListView.class);
                    intent.putExtra("Name", DirectoryName);
                    startActivity(intent);


                } else {
                    Toast.makeText(MainActivity.this, "SD카드 디렉토리를 먼저 설정해 주세요.",
                            Toast.LENGTH_LONG).show();
                }
            }
        });
        btnNewActivity4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,Setting.class);
                startActivity(intent);
            }
        });



    }

    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            DirectoryName = data.getStringExtra("Directory");
//            btnNewActivity3.setText("Directory Name : " + DirectoryName);
        }
    }
}
