package com.sample.blackbox;


import androidx.appcompat.app.AppCompatActivity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.os.SystemClock;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.Toast;

public class Shock extends AppCompatActivity implements SensorEventListener {

    /** ========== [구현 부분 설명 실시] ========== */
    /**
     * 1. class 클래스에서 SensorEventListener 를 implements 상속 받습니다
     * 2. onResume 에서 디바이스가 가속도 센서를 지원하는지 확인합니다
     * 3. onResume 에서 가속도 센서를 지원하는 경우 리시버를 등록합니다
     * 3. oncreate 액티비티 시작 상태에서 기능 동작을 실시합니다
     * 4. onPause 에서 액티비티 실행이 정지가 된 경우 리시버 등록을 해제합니다
     * */

    /** ========== [클래스 전역 변수 선언] ==========*/
    String deviceSensor = "";
    SensorManager mSensorManager = null;
    Sensor sensor = null;
    private long eventTime = 0;


    /** ========== [클래스 컴포 넌트 선언] ==========*/
    EditText sensor_edit;
    EditText result_edit;


    /** ========== [액티비티 시작 메소드] ========== */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.shock);
        Log.d("---","---");
        Log.d("//===========//","================================================");
        Log.d("","\n"+"[A_Shake > onCreate() 메소드 : 액티비티 시작 실시]");
        Log.d("//===========//","================================================");
        Log.d("---","---");

        /** ========== [컴포넌트 매칭 실시] ========== */
        sensor_edit = (EditText)findViewById(R.id.sensor_edit);
        sensor_edit.setText(String.valueOf(checkDeviceAccelerometer()));

        result_edit = (EditText)findViewById(R.id.result_edit);
        result_edit.setText("");

    }//TODO [메인 종료]


    /** ========== [디바이스 가속도 지원 확인] ========== **/
    public boolean checkDeviceAccelerometer(){
        boolean result = false;
        try {
            mSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
            sensor = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
            if (sensor != null) {
                Log.d("---","---");
                Log.w("//===========//","================================================");
                Log.d("","\n"+"[A_Shake > checkDeviceAccelerometer() 메소드 : 디바이스 가속도 지원 확인 실시]");
                Log.d("","\n"+"[지원 여부 : true]");
                Log.w("//===========//","================================================");
                Log.d("---","---");
                result = true;
            }
            else {
                Log.d("---","---");
                Log.e("//===========//","================================================");
                Log.d("","\n"+"[A_Shake > checkDeviceAccelerometer() 메소드 : 디바이스 가속도 지원 확인 실시]");
                Log.d("","\n"+"[지원 여부 : false]");
                Log.e("//===========//","================================================");
                Log.d("---","---");
                result = false;
            }
        }
        catch (Exception e){
            e.printStackTrace();
        }
        deviceSensor = String.valueOf(result);
        return result;
    }


    /** ========== [Sensor 리시버 등록 메소드] ========== **/
    public void registerSensor(){
        try {
            if(mSensorManager != null && sensor != null){
                Log.d("---","---");
                Log.w("//===========//","================================================");
                Log.d("","\n"+"[A_Shake > registerSensor() 메소드 : 센서 리시버 등록 수행 실시]");
                Log.w("//===========//","================================================");
                Log.d("---","---");
                mSensorManager.registerListener(Shock.this, sensor, SensorManager.SENSOR_DELAY_NORMAL);
            }
            else {
                Log.d("---","---");
                Log.e("//===========//","================================================");
                Log.d("","\n"+"[A_Shake > registerSensor() 메소드 : 센서 리시버 등록 수행 실패]");
                Log.e("//===========//","================================================");
                Log.d("---","---");
                //TODO [Alert 팝업창 알림 실시]
                getAlertDialog("[알 림]",
                        "센서 리시버 등록 실패 ...",
                        "확인", "", "");
            }
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

    /** ========== [Sensor 리시버 해제 메소드] ========== **/
    public void unregisterSensor(){
        try {
            if(mSensorManager != null && sensor != null){
                Log.d("---","---");
                Log.w("//===========//","================================================");
                Log.d("","\n"+"[A_Shake > registerSensor() 메소드 : 센서 리시버 해제 수행 실시]");
                Log.w("//===========//","================================================");
                Log.d("---","---");
                mSensorManager.unregisterListener(Shock.this);
            }
            else {
                Log.d("---","---");
                Log.e("//===========//","================================================");
                Log.d("","\n"+"[A_Shake > registerSensor() 메소드 : 센서 리시버 해제 수행 실패]");
                Log.e("//===========//","================================================");
                Log.d("---","---");
            }
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }


    /** ========== [SensorEventListener 이벤트 상속] ========== **/
    @Override
    public void onSensorChanged(SensorEvent event) {
        if (event.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {
            Log.d("---","---");
            Log.d("//===========//","================================================");
            Log.d("","\n"+"[A_Shake > onSensorChanged() 메소드 : 가속도 센서 값 변경 상태 확인]");
            Log.d("//===========//","================================================");
            Log.d("---","---");

            //TODO [흔들기 감지를 위한 변수값 선언]
            //final float SHAKE_GRAVITY = 2.7F;
            final float SHAKE_GRAVITY = 0.5F;

            //TODO [이벤트로 들어온 X, Y, Z 값 확인]
            float axisX = event.values[0];
            float axisY = event.values[1];
            float axisZ = event.values[2];

            //TODO [중력 가속도 X, Y, Z 값 구하기]
            float gravityX = (axisX / SensorManager.GRAVITY_EARTH);
            float gravityY = (axisY / SensorManager.GRAVITY_EARTH);
            float gravityZ = (axisZ / SensorManager.GRAVITY_EARTH);

            float data = gravityX * gravityX * gravityY * gravityY * gravityZ * gravityZ;
            double squared = Math.sqrt(Double.valueOf(data));

            float result = (float) squared;
            Log.d("---","---");
            Log.w("//===========//","================================================");
            Log.d("","\n"+"[A_Shake > onSensorChanged() 메소드 : 가속도 센서 값 변경 상태 확인]");
            Log.d("","\n"+"[result : "+String.valueOf(result)+"]");
            Log.w("//===========//","================================================");
            Log.d("---","---");
            result_edit.setText(String.valueOf(result));

            //TODO [흔들기 발생 결과 확인]
            if(result > SHAKE_GRAVITY){
                // TODO [2초 이내 다시 발생한 이벤트라면 무시]
                if (SystemClock.elapsedRealtime() - eventTime < 2000){
                    return;
                }
                eventTime = SystemClock.elapsedRealtime();

                //TODO [흔들기 발생 로직 처리 실시 : Alert 팝업창 알림 실시]
                getAlertDialog("[알 림]",
                        "충격이감지되었습니다 !!! 주행모드로 자동변환됩니다",
                        "확인", "", "");
                Intent intent = new Intent(Shock.this,SampleVideoRecorder.class);
                startActivity(intent);
            }
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
    }


    /** ========== [액티비티 실행 준비 메소드] ========== **/
    @Override
    public void onResume(){
        super.onResume();
        Log.d("---","---");
        Log.w("//===========//","================================================");
        Log.d("","\n"+"[A_Shake > onResume() 메소드 : 액티비티 실행 준비 실시]");
        Log.w("//===========//","================================================");
        Log.d("---","---");
        //TODO [외부 브라우저 복귀 시 화면 전환 애니메이션 없애기 위함]
        try {
            overridePendingTransition(0,0);
        }
        catch (Exception e){
            e.printStackTrace();
        }

        //TODO [디바이스 가속도 지원 여부 확인 및 센서 리시버 등록 실시]
        if(checkDeviceAccelerometer() == true){
            registerSensor();
        }
    }


    /** ========== [액티비티 실행 정지 메소드] ========== **/
    @Override
    public void onPause(){
        super.onPause();
        Log.d("---","---");
        Log.e("//===========//","================================================");
        Log.d("","\n"+"[A_Shake > onPause() 메소드 : 액티비티 실행 정지 실시]");
        Log.e("//===========//","================================================");
        Log.d("---","---");
        //TODO [외부 브라우저 복귀 시 화면 전환 애니메이션 없애기 위함]
        try {
            overridePendingTransition(0,0);
        }
        catch (Exception e){
            e.printStackTrace();
        }

        //TODO [센서 리시버 등록 해제 수행 실시]
        unregisterSensor();
    }


    /** ========== [바깥 레이아웃 클릭 시 키보드 내림] ========== */
    @Override
    public boolean dispatchTouchEvent(MotionEvent event){
        int action = event.getAction();
        switch(action){
            case(MotionEvent.ACTION_DOWN):
                //TODO [키보드 창 내리는 용도]
                try {
                    InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(sensor_edit.getWindowToken(), 0);
                    imm.hideSoftInputFromWindow(result_edit.getWindowToken(), 0);
                }
                catch (Exception e){
                    e.printStackTrace();
                }
                break;
            case(MotionEvent.ACTION_MOVE):
                break;
            default:
                break;
        }
        return super.dispatchTouchEvent(event);
    }


    /**==========  [백버튼 터치시 뒤로 가기] ========== **/
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        //TODO [디바이스의 키 이벤트가 발생했는데, 뒤로가기 이벤트 일때]
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            Log.d("---","---");
            Log.d("//===========//","================================================");
            Log.d("","\n"+"[A_Shake > onKeyDown() 메소드 : 백버튼 터치시 뒤로 가기 이벤트 실시]");
            Log.d("//===========//","================================================");
            Log.d("---","---");
            //TODO [액티비티 종료 실시]
            try {
                finish();
                overridePendingTransition(0,0);
            }
            catch (Exception e){
                e.printStackTrace();
            }
        }
        return true;
    }


    /** ========== [액티비티 종료 메소드] ========== */
    @Override
    public void onDestroy(){
        super.onDestroy();
        Log.d("---","---");
        Log.d("//===========//","================================================");
        Log.d("","\n"+"[A_Shake > onDestroy() 메소드 : 액티비티 종료 실시]");
        Log.d("//===========//","================================================");
        Log.d("---","---");
    }


    /** ========== [AlertDialog 팝업창 호출 메소드 정의 실시] ========== */
    public void getAlertDialog(String header, String content, String ok, String no, String normal){
        //TODO [타이틀 및 내용 표시]
        final String Tittle = String.valueOf(header);
        final String Message = String.valueOf(content);

        //TODO [버튼 이름 정의]
        String buttonYes = String.valueOf(ok);
        String buttonNo = String.valueOf(no);
        String buttonNature = String.valueOf(normal);

        try {
            //TODO [AlertDialog 팝업창 생성]
            new AlertDialog.Builder(Shock.this)
                    .setTitle(Tittle) //[팝업창 타이틀 지정]
                    //.setIcon(R.drawable.tk_app_icon) //[팝업창 아이콘 지정]
                    .setMessage(Message) //[팝업창 내용 지정]
                    .setCancelable(false) //[외부 레이아웃 클릭시도 팝업창이 사라지지않게 설정]
                    .setPositiveButton(buttonYes, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            // TODO Auto-generated method stub
                        }
                    })
                    .setNegativeButton(buttonNo, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            // TODO Auto-generated method stub
                        }
                    })
                    .setNeutralButton(buttonNature, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            // TODO Auto-generated method stub
                        }
                    })
                    .show();
        }
        catch (Exception e){
            Toast.makeText(getApplicationContext(), Tittle+"\n"+Message,Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }
    }


}//TODO [클래스 종료]

