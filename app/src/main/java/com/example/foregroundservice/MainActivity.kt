package com.example.foregroundservice

import android.app.ActivityManager
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.example.foregroundservice.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity() {
    lateinit var b:ActivityMainBinding
    lateinit var myService:MyService
    companion object{
        lateinit var tv:TextView
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        b=DataBindingUtil.setContentView(this,R.layout.activity_main)
        tv=b.status


       myService=MyService()
       val intent = Intent(this,myService::class.java)


        if (isMyServiceRunning(myService::class.java)==true){
            b.status.setText(getString(R.string.active))
            b.status.setTextColor(Color.GREEN)
        }else{
            b.status.setText(getString(R.string.deActive))
            b.status.setTextColor(Color.RED)
        }

        b.startBtn.setOnClickListener {
            b.status.setText(getString(R.string.active))
            b.status.setTextColor(Color.GREEN)
            if (isMyServiceRunning(myService::class.java)==false){
                if (Build.VERSION.SDK_INT>=Build.VERSION_CODES.O){
                    startForegroundService(intent)
                }else startService(intent)
            }

        }

        b.stopBtn.setOnClickListener {
            stopService(intent)
            b.status.setText(getString(R.string.deActive))
            b.status.setTextColor(Color.RED)

        }



    }

    override fun onStart() {
        super.onStart()

    }

    override fun onPause() {
        super.onPause()

    }

    override fun onResume() {
        super.onResume()

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