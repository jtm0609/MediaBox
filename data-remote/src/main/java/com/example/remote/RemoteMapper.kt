package com.example.remote

interface RemoteMapper<DataModel> {
    fun toData(): DataModel
}
