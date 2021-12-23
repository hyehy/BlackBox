package com.sample.blackbox;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.hardware.Camera;
import android.media.CamcorderProfile;
import android.media.MediaRecorder;
import androidx.appcompat.app.AppCompatActivity;

import android.net.Uri;
import android.os.Bundle;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;
import com.gun0912.tedpermission.PermissionListener;
import com.gun0912.tedpermission.TedPermission;
import java.util.ArrayList;

public class SampleVideoRecorder extends Activity implements SurfaceHolder.Callback {

    private Camera camera;
    private MediaRecorder mediaRecorder;
    private ImageButton btn_record;
    private Button btn_stop;
    private SurfaceView surfaceView;
    private SurfaceHolder surfaceHolder;
    private boolean recording = false;

    int number;

    ImageButton but2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.videorecoder);

        ImageButton but1,but3,but4,but6,but7;
        but1=(ImageButton)findViewById(R.id.but1);
        but2=(ImageButton)findViewById(R.id.but2);
        but3=(ImageButton)findViewById(R.id.but3);
        but4=(ImageButton)findViewById(R.id.but4);
        but6=(ImageButton)findViewById(R.id.but6);
        but7=(ImageButton)findViewById(R.id.but7);
//        ImageView iv=(ImageView)findViewById(R.id.iv);

        but1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(getApplicationContext(),Setting.class);
                startActivity(intent);
            }
        });
         but2.setOnClickListener(new View.OnClickListener() {//중심선 (이미지 있음 이름 centerline
             @Override
             public void onClick(View view) {

             }
         });
        but3.setOnClickListener(new View.OnClickListener() {//지도
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(getApplicationContext(),Gps.class);
                startActivity(intent);
            }
        });
        but4.setOnClickListener(new View.OnClickListener() {//영상 틀어주기
            @Override
            public void onClick(View view) {

            }
        });
        but6.setOnClickListener(new View.OnClickListener() {//충격 감지
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), Shock.class);
                startActivity(intent);
            }
        });
        but7.setOnClickListener(new View.OnClickListener() {//전화
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_DIAL);
                intent.setData(Uri.parse("tel:119"));
                //Intent intent = new Intent(Intent.ACTION_CALL,Uri.parse("tel:12345"));
                startActivity(intent);
            }
        });

        TedPermission.with(this)
                .setPermissionListener(permission)
                .setRationaleMessage("녹화를 위하여 권한을 허용해주세요.")
                .setDeniedMessage("권한이 거부되었습니다. 설정 > 권한에서 허용해주세요.")
                .setPermissions(Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.RECORD_AUDIO)
                .check();

        btn_record = (ImageButton)findViewById(R.id.btn_record);
        btn_record.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (recording) {
                    mediaRecorder.stop();
                    mediaRecorder.release();
                    camera.lock();
                    recording = false;
                    Toast.makeText(SampleVideoRecorder.this, "녹화가 중지되었습니다.", Toast.LENGTH_SHORT).show();
                } else {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(SampleVideoRecorder.this, "녹화가 시작되었습니다.", Toast.LENGTH_SHORT).show();
                            try {
                                mediaRecorder = new MediaRecorder();
                                camera.unlock();
                                mediaRecorder.setCamera(camera);
                                mediaRecorder.setAudioSource(MediaRecorder.AudioSource.CAMCORDER);
                                mediaRecorder.setVideoSource(MediaRecorder.VideoSource.CAMERA);
                                mediaRecorder.setProfile(CamcorderProfile.get(CamcorderProfile.QUALITY_720P));
                                mediaRecorder.setOrientationHint(90);
                                mediaRecorder.setOutputFile("/sdcard/Pictures/TEST2.mp4");
                                mediaRecorder.setPreviewDisplay(surfaceHolder.getSurface());
                                mediaRecorder.prepare();
                                mediaRecorder.start();
                                recording = true;
                            } catch (Exception e) {
                                e.printStackTrace();
                                mediaRecorder.release();
                            }
                        }
                    });
                }
            }
        });
    }

    PermissionListener permission = new PermissionListener() {
        @Override
        public void onPermissionGranted() {
            Toast.makeText(SampleVideoRecorder.this, "권한 허가", Toast.LENGTH_SHORT).show();

            camera = Camera.open();
            camera.setDisplayOrientation(90);
            surfaceView = (SurfaceView)findViewById(R.id.surfaceView);
            surfaceHolder = surfaceView.getHolder();
            surfaceHolder.addCallback(SampleVideoRecorder.this);
            surfaceHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);

        }

        @Override
        public void onPermissionDenied(ArrayList<String> deniedPermissions) {
            Toast.makeText(SampleVideoRecorder.this, "권한 거부", Toast.LENGTH_SHORT).show();
        }
    };

    @Override
    public void surfaceCreated(SurfaceHolder holder) {

    }

    private void refreshCamera(Camera camera) {
        if (surfaceHolder.getSurface() == null) {
            return;
        }

        try {
            camera.stopPreview();
        } catch (Exception e) {
            e.printStackTrace();
        }

        setCamera(camera);
    }

    private void setCamera(Camera cam) {

        camera = cam;

    }


    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
        refreshCamera(camera);
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {

    }
}
 

