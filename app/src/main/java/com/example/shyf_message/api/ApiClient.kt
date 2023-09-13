package com.example.shyf_message.api

import retrofit2.Retrofit
import retrofit2.converter.scalars.ScalarsConverterFactory

class ApiClient {
    companion object {
        private var retrofit: Retrofit? = null
        fun getInstance(): Retrofit{
            if(retrofit==null) {
                retrofit = Retrofit.Builder().baseUrl("https://fcm.googleapis.com/fcm/").addConverterFactory(ScalarsConverterFactory.create()).build()
            }
            return retrofit!!
        }
    }
}