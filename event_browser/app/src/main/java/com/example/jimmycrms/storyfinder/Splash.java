package com.example.jimmycrms.storyfinder;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;

public class Splash extends Activity implements Runnable
{
    Thread mThread;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash_screen);
        mThread = new Thread(this);
        mThread.start();
    }
    @Override
    public void run()
    {
        try
        {
            Thread.sleep(2000);

        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        finally
        {

            startActivity(new Intent(Splash.this, MainActivity.class));
            finish();
        }
    }

}
