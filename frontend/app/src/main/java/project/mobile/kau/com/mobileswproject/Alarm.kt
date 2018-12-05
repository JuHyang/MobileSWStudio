package com.example.administrator.alarmkau

import android.annotation.SuppressLint
import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import kotlinx.android.synthetic.main.activity_main.*
import project.mobile.kau.com.mobileswproject.Data
import project.mobile.kau.com.mobileswproject.R
import java.util.*
import kotlin.collections.ArrayList

class Alarm : AppCompatActivity() {
/*
    @SuppressLint("ServiceCast")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val ScheduleArray = listOf<Data>(
            //Data("인공지능입문", 16, 18, 2),
            //Data("항공제어sw", 16, 19, 3),
            //Data("알고리즘 해석 및 설계", 16, 20, 4)
        )

        var alarmManager = this.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        var alarmIntent = Intent(this, MyBroadCastReceiver::class.java)
        var pending:PendingIntent? = null
        var pendingList = ArrayList<PendingIntent>()

        val generateBtn = findViewById(R.id.generateBtn) as Button
        val cancelBtn = findViewById(R.id.cancelBtn) as Button

        generateBtn.setOnClickListener({
            for (item in ScheduleArray) {
                var pending = PendingIntent.getBroadcast(this, item.id.toInt(), alarmIntent, 0)
                pendingList.add(pending)
                alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, setTriggerTime(item), 1000 * 60 * 60 * 24 * 7, pending)
            }
        })

        cancelBtn.setOnClickListener({
            for(item in pendingList){
                pending = item
                alarmManager.cancel(pending)
            }
        })

    }

    private fun setTriggerTime(classSchedule: Data): Long {

        //"금)09:00∼11:00"
        val time = classSchedule.time.slice(2..classSchedule.time.length-1)
        val temp = time.split("~")
        val hour = temp[0].split(":")[0].toInt()
        val minute = temp[1].split(":")[0].toInt()

        var atime: Long = System.currentTimeMillis()
        var curTime = Calendar.getInstance()
        curTime.set(Calendar.HOUR_OF_DAY, hour)
        curTime.set(Calendar.MINUTE, minute - 10)
        curTime.set(Calendar.SECOND, 0)
        curTime.set(Calendar.MILLISECOND, 0)
        var btime = curTime.timeInMillis
        var triggerTime = btime
        if (atime > btime) triggerTime += 1000 * 60 * 60 * 24 * 7 // 이미 지났을 경우엔 다음주로 넘겨 줌
        return triggerTime
    }*/
}
