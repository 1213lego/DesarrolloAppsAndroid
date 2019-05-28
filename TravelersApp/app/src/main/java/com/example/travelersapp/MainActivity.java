package com.example.travelersapp;

import android.annotation.TargetApi;
import android.content.DialogInterface;
import android.graphics.Camera;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.hardware.camera2.CameraAccessException;
import android.hardware.camera2.CameraManager;
import android.os.Build;
import android.os.Vibrator;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

public class MainActivity extends AppCompatActivity implements SensorEventListener {

    public final static int TIEMPO_MINIMO =1000;

    private ImageView img_compass;
    private TextView txt_azimuth;
    private TextView txt_Pasos;
    private int mAzimuth;

    private SensorManager mSensorManager;

    private Sensor mVectorRotacion;
    private Sensor mAcelerometro;
    private Sensor mMagnetometro;
    private Sensor contadorPasos;
    private Sensor sensorProximidad;

    private CameraManager camManager;

    private float[] rMat = new float[9];
    private float[] orientacion = new float[9];
    private float[] ultAcelerometro = new float[3];
    private float[] ultMagnetometro = new float[3];

    private boolean estaPrendida;
    private boolean caminando = false;
    private boolean tieneSensor;
    private boolean tieneSensor2;
    private boolean ultAcelerometroSet;
    private boolean ultMagnetometroSet;
    private boolean sensorCamara;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mSensorManager= (SensorManager) getSystemService(SENSOR_SERVICE);


        img_compass= (ImageView) findViewById(R.id.imageCompass);
        txt_azimuth =(TextView) findViewById(R.id.txtAzimuth);
        txt_Pasos=(TextView) findViewById(R.id.txtSteps);

        iniciar();
    }




    @Override
    public void onSensorChanged(SensorEvent event) {
        if(event.sensor.getType()==Sensor.TYPE_ROTATION_VECTOR )
        {
            SensorManager.getRotationMatrixFromVector(rMat,event.values);
            mAzimuth = (int) ((Math.toDegrees(SensorManager.getOrientation(rMat,orientacion)[0])+360)%360);
        }
        if(event.sensor.getType()==Sensor.TYPE_STEP_COUNTER)
        {
            txt_Pasos.setText(String.valueOf("Pasos : "+(int)event.values[0]));
        }
        if(event.sensor.getType()==Sensor.TYPE_PROXIMITY)
        {
            if(event.values[0]<=1)
            {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    try {
                        prenderCamara(event);
                    } catch (CameraAccessException e) {
                        e.printStackTrace();
                    }
                }
            }

        }
        if(event.sensor.getType()==Sensor.TYPE_ACCELEROMETER)
        {
            System.arraycopy(event.values,0,ultAcelerometro,0,event.values.length);
            ultAcelerometroSet=true;
        }
        else if(event.sensor.getType()==Sensor.TYPE_MAGNETIC_FIELD)
        {
            System.arraycopy(event.values,0,ultMagnetometro,0,event.values.length);
            ultMagnetometroSet=true;
        }


        if(ultAcelerometroSet&&ultMagnetometroSet)
        {
            SensorManager.getRotationMatrix(rMat,null,ultAcelerometro,ultMagnetometro);
            SensorManager.getOrientation(rMat,orientacion);
            mAzimuth= (int) ((Math.toDegrees(SensorManager.getOrientation(rMat,orientacion)[0])+360)%360);
        }

        pintarCompas();
    }
    @TargetApi(Build.VERSION_CODES.M)
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public void prenderCamara(SensorEvent event) throws CameraAccessException {
        camManager = (CameraManager)getSystemService(CAMERA_SERVICE);
        String camId=null;
        if(camManager!=null)
        {
            try {
                camId=camManager.getCameraIdList()[0];
            } catch (CameraAccessException e) {
                e.printStackTrace();
            }
        }
        if(camManager!=null)
        {
            if(!estaPrendida)
            {
                estaPrendida=true;
                camManager.setTorchMode(camId,true);
            }
            else
            {
                estaPrendida=false;
                camManager.setTorchMode(camId,false);
            }
        }

    }

    public void pintarCompas()
    {
        mAzimuth=Math.round(mAzimuth);
        img_compass.setRotation(-mAzimuth);

        String direccion = "";
        if(mAzimuth >=350 || mAzimuth<=10)
        {
            direccion= "N";
        }
        if(mAzimuth <350 || mAzimuth>280)
        {
            direccion= "NE";
        }

        if(mAzimuth <=280 || mAzimuth>260)
        {
            direccion= "W";
        }
        if(mAzimuth <=260 || mAzimuth>190)
        {
            direccion= "SW";
        }
        if(mAzimuth <=190 || mAzimuth>170)
        {
            direccion= "S";
        }
        if(mAzimuth <=170 || mAzimuth>100)
        {
            direccion= "SE";
        }
        if(mAzimuth <=100 || mAzimuth>80)
        {
            direccion= "E";
        }
        if(mAzimuth <=80 || mAzimuth>10)
        {
            direccion= "NW";
        }
        txt_azimuth.setText(mAzimuth+"°"+ direccion);
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }
    public void iniciar(){
        if(mSensorManager.getDefaultSensor(Sensor.TYPE_ROTATION_VECTOR)==null)
        {
            if(mSensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD)==null||mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER)==null)
            {
                noSensorAlert();
            }
            else{
                Toast.makeText(this, "Tiene los dos sensores", Toast.LENGTH_SHORT).show();
                mAcelerometro = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
                mMagnetometro = mSensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD);

                tieneSensor=mSensorManager.registerListener(this,mAcelerometro,SensorManager.SENSOR_DELAY_UI );
                tieneSensor2=mSensorManager.registerListener(this,mMagnetometro,SensorManager.SENSOR_DELAY_UI );
            }
        }
        else
        {
            Toast.makeText(this, "Solo sensor de rotacion", Toast.LENGTH_SHORT).show();
            mVectorRotacion = mSensorManager.getDefaultSensor(Sensor.TYPE_ROTATION_VECTOR);
            tieneSensor=mSensorManager.registerListener(this,mVectorRotacion,SensorManager.SENSOR_DELAY_UI );
        }
        if(mSensorManager.getDefaultSensor(Sensor.TYPE_PROXIMITY)==null)
        {
            Toast.makeText(this, "No Tiene Sensor de Proximidad", Toast.LENGTH_SHORT).show();
        }
        else
        {
            sensorProximidad=mSensorManager.getDefaultSensor(Sensor.TYPE_PROXIMITY);
            sensorCamara=mSensorManager.registerListener(this,sensorProximidad,SensorManager.SENSOR_DELAY_UI);
        }

        if(mSensorManager.getDefaultSensor((Sensor.TYPE_STEP_COUNTER))==null)
        {
            Toast.makeText(this, "No tiene Sensor para contar pasos", Toast.LENGTH_SHORT).show();
        }
        else
        {
            caminando=true;
            contadorPasos=mSensorManager.getDefaultSensor(Sensor.TYPE_STEP_COUNTER);
            mSensorManager.registerListener(this,contadorPasos,SensorManager.SENSOR_DELAY_UI );
        }
    }

    public void noSensorAlert()
    {


        Toast.makeText(this, "NO CONTIENE SENSORES GEOMAGNETICOS", Toast.LENGTH_SHORT).show();
    }
    public void stop()
    {
        if(tieneSensor && tieneSensor2)
        {
            mSensorManager.unregisterListener(this,mAcelerometro);
            mSensorManager.unregisterListener(this,mMagnetometro);
        }
        else
        {
            if(tieneSensor)
            {
                mSensorManager.unregisterListener(this,mVectorRotacion);
            }
        }
        if(sensorCamara)
        {
            mSensorManager.unregisterListener(this,sensorProximidad);
        }

    }

    @Override
    protected void onPause() {
        super.onPause();
        stop();
    }

    @Override

    protected void onResume() {

        super.onResume();

        iniciar();
    }
}
