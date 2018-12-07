package project.mobile.kau.com.mobileswproject

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.OrientationHelper
import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.Spinner
import com.orm.SugarRecord
import java.util.*

class ScheduleSelectActivity: AppCompatActivity() {

    private lateinit var selecting_recyclerView: RecyclerView
    private lateinit var selecting_viewAdapter: RecyclerView.Adapter<*>
    private lateinit var selecting_viewManager: RecyclerView.LayoutManager
    private lateinit var selected_recyclerView: RecyclerView
    private lateinit var selected_viewAdapter: RecyclerView.Adapter<*>
    private lateinit var selected_viewManager: RecyclerView.LayoutManager

    var alarmManager : AlarmManager? = null


    var spinner_lecture : Spinner? = null
    var add : Button? = null

    var major = ""
    var objects: ArrayList<ScheduleData> = arrayListOf()
    var select_object :ArrayList<ScheduleData> = arrayListOf()
    var final_list : ArrayList<ScheduleData> = arrayListOf()
    var dataBase : ArrayList<ScheduleData> = arrayListOf() //for db

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.schedule_select)
        alarmManager = this.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        initView()

        initRecycler()
        initModel()
        aboutView()
    }

    fun initView () {
        add = findViewById(R.id.add)
        spinner_lecture = findViewById(R.id.spinner_major)



    }

    fun initModel () {
        ScheduleGetServerInfo().getText(objects,select_object, selecting_viewAdapter)
    }

    fun initRecycler () {
        selected_viewManager = LinearLayoutManager(this,OrientationHelper.HORIZONTAL,false)
        selected_viewAdapter = ScheduleSelectedAdapter(final_list)
        selected_recyclerView = findViewById<RecyclerView>(R.id.selectedRecyclerView).apply {
            setHasFixedSize(true)
            layoutManager = selected_viewManager
            adapter = selected_viewAdapter
        }


        selecting_viewManager = LinearLayoutManager(this)
        selecting_viewAdapter = ScheduleListAdapter(select_object,selected_viewAdapter as ScheduleSelectedAdapter,major)
        selecting_recyclerView = findViewById<RecyclerView>(R.id.selectingRecyclerView).apply {
            setHasFixedSize(true)
            layoutManager = selecting_viewManager
            adapter = selecting_viewAdapter
        }

    }

    fun aboutView () {
        spinner_lecture?.adapter = ArrayAdapter.createFromResource(this,R.array.major,android.R.layout.simple_spinner_item)
        spinner_lecture?.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onNothingSelected(parent: AdapterView<*>?) {

            }
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                major = spinner_lecture?.getItemAtPosition(position).toString()
                select_object.clear()
                var major_list_string = arrayOf("전체","경영학부", "무인기융합전공", "물류전공", "소프트웨어학과", "영어학과", "인문자연학부", "자율주행융합전공"
                        , "항공교통물류학부", "항공교통전공", "항공우주및기계공학부", "항공우주법전공", "항공운항학과", "항공재료공학과", "항공전자정보공학부")
                var major_list = arrayOf("","경영학부", "무인기융합", "물류", "소프트", "영어", "인문자연학부", "자율주행융합전공"
                        , "항교물", "항공교통", "항우기학부", "항공우주법", "항공운항", "항공재료", "항전정학부")
                for (i: Int in 0..major_list.size - 1) {
                    if (major == major_list_string[i]) major = major_list[i]
                }
                for(i : Int in 0..objects.size-1){
                    if(major==""||major==objects[i].major){
                        select_object.add(objects[i])
                    }
                }
                selecting_recyclerView.adapter?.notifyDataSetChanged()
            }
        }



        add?.setOnClickListener {
            var temp : ScheduleData? = null
            for(i in final_list) {
                temp = i
                var roomResult = ""
                var roomTemp = temp.room.split(" / ")
                var size = roomTemp.size
                for (j in 0..(size - 1)) {
                    if (roomTemp[j].length < 2) {
                        if (j > 0) {
                            roomResult += "/"
                        }
                        roomResult += roomTemp[j][0] + roomTemp[j].subSequence(3, 6).toString()
                    }
                }
                temp.room = roomResult
                dataBase.add(temp)
                temp.save()
            }

            for (item in dataBase) {
                setAlarm(item)
            }

            finish()
        }
    }

    fun setAlarm (item : ScheduleData) {
        var id = item.id % 20
        var time = item.time
        var time_list = time.split("  ")
        for (i in time_list) {
            var triggerTime = setTriggerTime (i)
            var alarmIntent = Intent(this, ScheduleAlarmReceiver::class.java)
            alarmIntent.putExtra("id", id)
            var pending = PendingIntent.getBroadcast(this, id.toInt(), alarmIntent, PendingIntent.FLAG_UPDATE_CURRENT)
            alarmManager?.setInexactRepeating(AlarmManager.RTC_WAKEUP, triggerTime, 86400000, pending)
            id += 20
        }
    }

    fun setTriggerTime(time: String): Long {
        //"금)09:00∼11:00"
        var time_origin = time.slice(2 until time.length)
        var time_start = time_origin.split("∼")
        var time_list = time_start[0].split(":")
        var time_hour = time_list[0].toInt()
        var time_minuite = time_list[1].toInt()

        var currentTime = System.currentTimeMillis()
        var registerTime = Calendar.getInstance()



//        var c = Calendar.getInstance()
//        var minute = c.get(Calendar.MINUTE)

//        registerTime.set(Calendar.HOUR_OF_DAY, 10)
//        registerTime.set(Calendar.MINUTE, minute + 1)

        registerTime.set(Calendar.HOUR_OF_DAY, time_hour)
        registerTime.set(Calendar.MINUTE, time_minuite - 10)
        registerTime.set(Calendar.SECOND, 0)
        registerTime.set(Calendar.MILLISECOND, 0)

        var triggerTime = registerTime.timeInMillis

        if (currentTime > triggerTime) {
            triggerTime += 86400000
        }

        return triggerTime
    }
}