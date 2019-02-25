package com.example.myapplication;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    private float x1,x2,y1,y2;
    public final static String LEFT="Lefy";
    public final static String RIGHT="Right";
    public final static String RETORNO ="Accion";
    public final static String Valor="Valor";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public boolean onTouchEvent(MotionEvent touchEvent)
    {
        switch(touchEvent.getAction())
        {
            case MotionEvent.ACTION_DOWN: x1 = touchEvent.getX();
                y1 = touchEvent.getY();
                break;

            case MotionEvent.ACTION_UP: x2 = touchEvent.getX();
                y2 = touchEvent.getY();
                if(x1 < x2)
                {
                    Intent i = new Intent(MainActivity.this, Main2Activity.class);
                    i.putExtra(RETORNO,RIGHT);
                    i.putExtra(Valor,-1);
                    startActivity(i);
                }
                else if(x1 > x2)
                {
                    Intent i = new Intent(MainActivity.this, Main2Activity.class);
                    i.putExtra(RETORNO,LEFT);
                    i.putExtra(Valor,1);
                    startActivity(i);
                }
                break;
        }
        return false;
    }
    public void btnTabbedActivity(View view)
    {
        Intent intent=new Intent(this,TabbedActivity.class);
        startActivity(intent);
    }
    public void btnTabbedActivityActionBar(View view)
    {
        Intent intent=new Intent(this,TabbedActivityActionBar.class);
        startActivity(intent);
    }
    public void btnTabbedActivityBarSpinner(View view)
    {
        Intent intent=new Intent(this,TabbedActivityBarSpinner.class);
        startActivity(intent);
    }
}
