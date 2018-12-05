package project.mobile.kau.com.mobileswproject

import retrofit2.Call
import retrofit2.http.GET

data class Object(var subject: String, var professor: String, var major:String, var time:String, var room:String)

interface ServerInterface {
    @GET("/schedule")
    fun getObject(): Call<ArrayList<Object>>
}