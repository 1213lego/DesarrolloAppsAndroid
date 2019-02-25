package com.example.myapplication;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.widget.TextView;
import android.widget.Toast;

public class Main2Activity extends AppCompatActivity
{
    private float x1,x2,y1,y2;
    private String retorno;
    private int num;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        Intent intent=getIntent();
        retorno=intent.getStringExtra(MainActivity.RETORNO);
        num=intent.getIntExtra(MainActivity.Valor,0);
        TextView textView=findViewById(R.id.txt);
        textView.setText(num+"");
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
                    Intent i = new Intent(Main2Activity.this, Main2Activity.class);
                    i.putExtra(MainActivity.RETORNO,MainActivity.LEFT);
                    if(retorno.equals(MainActivity.LEFT))
                    {
                        finish();
                    }
                    else
                    {
                        i.putExtra(MainActivity.Valor,num-1);
                        i.putExtra(MainActivity.RETORNO,MainActivity.RIGHT);
                        startActivity(i);
                    }
                }
                else if(x1 > x2)
                {
                    Intent i = new Intent(Main2Activity.this, Main2Activity.class);
                    i.putExtra(MainActivity.RETORNO,MainActivity.RIGHT);
                    if(retorno.equals(MainActivity.RIGHT))
                    {
                        finish();
                    }
                    else
                    {
                        i.putExtra(MainActivity.Valor,num+1);
                        i.putExtra(MainActivity.RETORNO,MainActivity.LEFT);
                        startActivity(i);
                    }
                } break;
        }
        return false;
    }

    @Override
    protected void onDestroy() 
    {
        super.onDestroy();
        Toast.makeText(this, "onDestroy", Toast.LENGTH_SHORT).show();
    }
}
