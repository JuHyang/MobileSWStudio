package com.example.administrator.alarmkau

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent

class MyBroadCastReceiver : BroadcastReceiver(){
    override fun onReceive(context: Context, intent: Intent) {
        val notificationHelper = NotificationHelper(context)
        notificationHelper.initView()
        notificationHelper.generateAlarm()
    }
}
