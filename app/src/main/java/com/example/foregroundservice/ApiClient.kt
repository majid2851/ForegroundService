package com.example.kotlin_wallet.model.data

import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory


class ApiClient
{
    companion object{
        val baseUrl=""

        private var retrofit: Retrofit?=null

        fun getRetrofit() : Retrofit?
        {
            if(retrofit== null)
            {
                retrofit= Retrofit.Builder().baseUrl(baseUrl)
                    .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .build()
            }
            return retrofit

        }


    }





}