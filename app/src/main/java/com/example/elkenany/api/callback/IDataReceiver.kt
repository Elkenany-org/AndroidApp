package com.example.elkenany.api.callback

import com.example.elkenany.api.retrofit_configs.retrofit

interface IDataReceiver {
 // ToDo --> implement functions that are required for receiving data from backend
}

object DataReceiverHandler {
    val singleton: IDataReceiver by lazy {
        retrofit.create(IDataReceiver::class.java)
    }
}