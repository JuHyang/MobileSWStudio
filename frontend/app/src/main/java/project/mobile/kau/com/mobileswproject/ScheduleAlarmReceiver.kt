package project.mobile.kau.com.mobileswproject

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.orm.SugarRecord
import project.mobile.kau.com.mobileswproject.ScheduleData
import android.os.Bundle
import project.mobile.kau.com.mobileswproject.ScheduleNotification
import java.util.*


class ScheduleAlarmReceiver : BroadcastReceiver(){
    private var extra: Bundle? = null
    var datas = SugarRecord.listAll(ScheduleData::class.java)

    override fun onReceive(context: Context, intent: Intent) {
        val notificationHelper = ScheduleNotification(context)
        extra = intent.extras
        var id = extra?.getLong("id")

        if (checkDay(id)) {
            notificationHelper.initView()
            notificationHelper.generateAlarm()
        }
    }

    fun checkDay (id : Long?) : Boolean{
        var calendar = Calendar.getInstance()
        var day = calendar.get(Calendar.DAY_OF_WEEK)
        var dayString = ""
        var dataDay = ""
        when (day) {
            1 -> dayString = "일"
            2 -> dayString = "월"
            3 -> dayString = "화"
            4 -> dayString = "수"
            5 -> dayString = "목"
            6 -> dayString = "금"
            7 -> dayString = "토"
        }

        if (id != null) {
            for (data in datas) {
                var dataId = data.id
                if (dataId == id) {
                    dataDay = data.room[0].toString()
                    return dataDay == dayString
                } else if (dataId + 20 == id) {
                    var room_temp = data.room.split("  ")
                    dataDay = room_temp[1][0].toString()
                    return dataDay == dayString
                }
            }
        }
        return false
    }
}
