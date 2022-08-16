package com.example.foregroundservice

import android.app.ActivityManager
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.example.foregroundservice.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity() {
    lateinit var b:ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        b=DataBindingUtil.setContentView(this,R.layout.activity_main)
        val intent = Intent(this,MyService2::class.java)

        if (isMyServiceRunning(MyService2::class.java)==true){
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
    private fun isMyServiceRunning(serviceClass: Class<*>): Boolean {
        val manager = getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager
        for (service in manager.getRunningServices(Int.MAX_VALUE)) {
            if (serviceClass.name == service.service.className) {
                return true
            }
        }
        return false
    }

}