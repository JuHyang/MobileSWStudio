package project.mobile.kau.com.mobileswproject

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.ContextWrapper
import android.content.Intent
import android.content.pm.ResolveInfo
import android.os.Build
import android.support.v4.app.NotificationCompat
import project.mobile.kau.com.mobileswproject.R
import java.lang.Exception
import java.util.*

class ScheduleNotification(base: Context): ContextWrapper(base){

    var context = base
    lateinit var kauIntent: Intent
    lateinit var builder: NotificationCompat.Builder
    lateinit var pendingIntent: PendingIntent
    lateinit var mNotificationManager : NotificationManager
    lateinit var vibrate : LongArray
    internal var exist : Boolean = false
    var minute = 0

    var push : Intent? = null

    fun initView(){
        kauIntent = packageManager.getLaunchIntentForPackage("kr.co.symtra.kauid")
        builder = NotificationCompat.Builder(this, "default")
        exist = packageList
        if (exist) { //유저 핸드폰 내에 KAU ID 어플리케이션이 Exist할 때만 KAUintent를 실행해 줌, 앱이 없다면 알림 클릭 시 아무런 동작도 하지 않는다.
            pendingIntent = PendingIntent.getActivity(this, 1, kauIntent, PendingIntent.FLAG_UPDATE_CURRENT)
        } else {
            var intent = Intent (this, SplashActivity::class.java). apply {
                flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
            }
            pendingIntent = PendingIntent.getActivity(this, 1, intent, PendingIntent.FLAG_UPDATE_CURRENT)
        }

        mNotificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager//오레오 버전은 notification 채널을 등록해 줘야 함
        vibrate = longArrayOf(0, 100, 200, 300)

        var c = Calendar.getInstance()
        minute = c.get(Calendar.MINUTE)
    }

    fun generateAlarm(){
        builder.setSmallIcon(R.drawable.kauimg)
        builder.setContentTitle("그대여, 출첵은 하셨는가?")
        builder.setContentText("수업 출석체크 하려면 클릭!")//~~수업 내용은 추 후 수업 내용에 따라 달라져야 하므로 변수를 집어넣어 줄 것이다. aboutview 내부에서 변경되게끔!
        builder.setVibrate(vibrate)
        builder.setWhen(System.currentTimeMillis())
        builder.priority = Notification.PRIORITY_MAX
        builder.setAutoCancel(true) //알림을 클릭했을 때 알림이 사라지도록
        builder.setFullScreenIntent(pendingIntent, true);
        builder.setContentIntent(pendingIntent) //알림을 클릭했을 때 pendingIntent가 실행되도록 함

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {//O는 오레오 버전임을 명시, 오레오 버전은 알림 채널이 명시돼야 한다.
            mNotificationManager.createNotificationChannel(NotificationChannel("default", "NotiChannel", NotificationManager.IMPORTANCE_DEFAULT))
        }
        mNotificationManager.notify(1, builder.build())

    }

    //KAU ID 어플이 유저의 스마트 폰에 존재하는 지 검사
    val packageList: Boolean
        get() {
            var isExist = false

            val pkgMgr= packageManager
            val mApps: List<ResolveInfo>
            val mainIntent = Intent(Intent.ACTION_MAIN, null)
            mainIntent.addCategory(Intent.CATEGORY_LAUNCHER)
            mApps = pkgMgr.queryIntentActivities(mainIntent, 0)

            try {
                for (i in mApps.indices) {
                    if (mApps[i].activityInfo.packageName.startsWith("kr.co.symtra.kauid")) {
                        isExist = true
                        break
                    }
                }
            } catch (e: Exception) {
                isExist = false
            }

            return isExist
        }
}
