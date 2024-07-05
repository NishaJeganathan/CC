package com.example.cheesechase

import android.media.Image
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface gameApiInterface {

    @GET("/obstacleLimit")
    fun getObstacleLimit(): Call<obstacleLimitResponse>
    @GET("/image")
    fun getImage(@Query("character") character:String):Call<responseBody>
}

data class obstacleLimitResponse(val obstacleLimit:Int)
