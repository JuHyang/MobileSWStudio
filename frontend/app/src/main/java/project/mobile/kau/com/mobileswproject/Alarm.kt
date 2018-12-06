package com.example.administrator.alarmkau

import android.support.v7.app.AppCompatActivity

class Alarm : AppCompatActivity() {
/*
    @SuppressLint("ServiceCast")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.schedule_main)

        val ScheduleArray = listOf<ScheduleData>(
            //ScheduleData("인공지능입문", 16, 18, 2),
            //ScheduleData("항공제어sw", 16, 19, 3),
            //ScheduleData("알고리즘 해석 및 설계", 16, 20, 4)
        )

        var alarmManager = this.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        var alarmIntent = Intent(this, ScheduleAlarmReceiver::class.java)
        var pending:PendingIntent? = null
        var pendingList = ArrayList<PendingIntent>()

        val generateBtn = findViewById(R.id.button) as Button


        generateBtn.setOnClickListener({
            for (item in ScheduleArray) {
                var pending = PendingIntent.getBroadcast(this, item.id.toInt(), alarmIntent, 0)
                pendingList.add(pending)
                alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, setTriggerTime(item), 1000 * 60 * 60 * 24 * 7, pending)
            }
        })
/*
        cancelBtn.setOnClickListener({
            for(item in pendingList){
                pending = item
                alarmManager.cancel(pending)
            }
        })
*/
    }

    private fun setTriggerTime(classSchedule: ScheduleData): Long {
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
    }
    */
}
