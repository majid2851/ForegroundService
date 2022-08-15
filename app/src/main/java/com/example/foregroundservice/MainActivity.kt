package com.example.foregroundservice

import android.app.PendingIntent
import android.app.Service
import android.content.Intent
import android.graphics.Color
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.example.foregroundservice.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    lateinit var b:ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        b=DataBindingUtil.setContentView(this,R.layout.activity_main)
        val myService2=MyService2()
        val intent = Intent(this,myService2::class.java)

        if (myService2.isServiceCreated()==true){
            b.status.setText(getString(R.string.active))
            b.status.setTextColor(Color.GREEN)
        }else{
            b.status.setText(getString(R.string.deActive))
            b.status.setTextColor(Color.RED)
        }

        b.startBtn.setOnClickListener {
            b.status.setText(getString(R.string.active))
            b.status.setTextColor(Color.GREEN)
            if (Build.VERSION.SDK_INT>=Build.VERSION_CODES.O){
                startForegroundService(intent)
            }else startService(intent)
        }

        b.stopBtn.setOnClickListener {
            stopService(intent)
            b.status.setText(getString(R.string.deActive))
            b.status.setTextColor(Color.RED)

        }

    }

    fun isRunning(serviceClass: Class<out Service?>?): Boolean {
        val intent = Intent(this, serviceClass)
        return PendingIntent.getService(this, 0, intent, PendingIntent.FLAG_NO_CREATE) != null
    }

}