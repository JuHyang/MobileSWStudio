package com.example.administrator.alarmkau

import android.app.Activity
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.ContextWrapper
import android.content.Intent
import android.content.pm.PackageManager
import android.content.pm.ResolveInfo
import android.os.Build
import android.support.v4.app.NotificationCompat
import android.widget.Toast
import java.lang.Exception
import java.util.*

class MyBroadCastReceiver : BroadcastReceiver(){
    override fun onReceive(context: Context, intent: Intent) {

            val notificationHelper = NotificationHelper(context)

            notificationHelper.initView()

            notificationHelper.generateAlarm()

    }
}