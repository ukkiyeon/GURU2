package com.example.guru2

import com.example.guru2.data.Walk
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.Call

class WalkOpenApi {

    companion object {
        val DOMAIN = "https://api.odcloud.kr/api/"
        val API_KEY = "AIzaSyAThw4etbSYJMUA_GpVxyKNqRwKW6gU1lA"
    }
}

interface WalkOpenService {
    @GET("{api_key}/json/WalkInfo/1/55")
    fun getWalk(@Path("api_key") key: String): Call<Walk>
}