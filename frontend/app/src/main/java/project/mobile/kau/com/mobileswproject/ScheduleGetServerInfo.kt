package project.mobile.kau.com.mobileswproject

import android.util.Log
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ScheduleGetServerInfo{
        fun getText(array: ArrayList<Object>, select: ArrayList<Object>,adapter: ScheduleAdapter) {
            val retrofit = Retrofit.Builder()
                    .baseUrl("http://192.168.195.126:8001")//ip address
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()

            var service = retrofit.create(ServerInterface::class.java)

            service.getObject().enqueue(object : Callback<ArrayList<Object>> {
                override fun onFailure(call: Call<ArrayList<Object>>, t: Throwable) {
                    Log.d("MainActivity", "\n\n"+t.message+"\n\n");
                }
                override fun onResponse(call: Call<ArrayList<Object>>, response: Response<ArrayList<Object>>) {
                    if (response.isSuccessful) {
                        array.addAll(response.body()!!)
                        select.addAll(response.body()!!)
                        adapter.notifyDataSetChanged()
                    }else{
                    }
                }

            })
        }
}