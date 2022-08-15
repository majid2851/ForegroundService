package com.example.foregroundservice

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.example.kotlin_wallet.model.data.ApiClient
import com.example.kotlin_wallet.model.data.ApiService
import java.lang.Appendable

class MyViewModel(application: Application): AndroidViewModel(application)
{
    fun myFunc(){
        return ApiClient.getRetrofit()!!.create(ApiService::class.java).myFunc()
    }

}