package project.mobile.kau.com.mobileswproject

import android.support.v7.widget.RecyclerView
import android.util.Log
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ScheduleGetServerInfo{
        fun getText(array: ArrayList<ScheduleData>, select: ArrayList<ScheduleData>, selectingAdapter : RecyclerView.Adapter<*>) {
            val retrofit = Retrofit.Builder()
                    .baseUrl("http://ec2-3-16-12-40.us-east-2.compute.amazonaws.com:8001")//ip address
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()

            var service = retrofit.create(ServerInterface::class.java)

            service.getObject().enqueue(object : Callback<ArrayList<ScheduleData>> {
                override fun onFailure(call: Call<ArrayList<ScheduleData>>, t: Throwable) {
                    Log.d("MainActivity", "\n\n"+t.message+"\n\n");
                }
                override fun onResponse(call: Call<ArrayList<ScheduleData>>, response: Response<ArrayList<ScheduleData>>) {
                    if (response.isSuccessful) {
                        array.addAll(response.body()!!)
                        select.addAll(response.body()!!)
                    }else{
                    }
                    selectingAdapter.notifyDataSetChanged()
                }

            })
        }
}