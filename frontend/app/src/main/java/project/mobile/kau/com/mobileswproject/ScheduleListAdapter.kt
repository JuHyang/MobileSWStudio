package project.mobile.kau.com.mobileswproject

import android.app.Activity
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import com.orm.SugarRecord

class ScheduleListAdapter(val context : Activity, val select : ArrayList<ScheduleData>, val finalAdapter: ScheduleSelectedAdapter, val major : String = "") : RecyclerView.Adapter<ScheduleListAdapter.ViewHolder>() {
    override fun getItemCount(): Int {
        return select.size
    }
    class ViewHolder(val view : View) : RecyclerView.ViewHolder(view) {
        var info = view.findViewById(R.id.info) as TextView
        var time = view.findViewById(R.id.time) as TextView
        var btn = view.findViewById(R.id.button) as Button

    }
    override fun onCreateViewHolder(parent: ViewGroup, type: Int): ScheduleListAdapter.ViewHolder {
        val lectureView = LayoutInflater.from(parent.context).inflate(R.layout.schedule_lecture_info, parent,false)
        return ViewHolder(lectureView)
    }
    override fun onBindViewHolder(holder : ScheduleListAdapter.ViewHolder, position: Int) {
        holder.btn.setOnClickListener {
            var resultDB = true
            var resultlist = true
            var resultTime = true
            var resultNow = true
            var selectedSchedule = select[position]
            var scheduleDatas : ArrayList<ScheduleData> = SugarRecord.listAll(ScheduleData::class.java) as ArrayList<ScheduleData>

            for (schedule in scheduleDatas) {
                if (selectedSchedule.major == schedule.major &&
                        selectedSchedule.professor == schedule.professor &&
                        selectedSchedule.time == schedule.time) {
                    resultDB = false
                    break
                }
            }
            if (resultDB) {
                if (selectedSchedule in finalAdapter.aFinal) {
                    resultlist = false
                }
            }

            if (resultDB && resultlist) {
                resultTime = judgeTime(scheduleDatas, selectedSchedule)
            }

            if (resultDB && resultlist && resultTime) {
                resultNow = judgeTime(finalAdapter.aFinal, selectedSchedule)
            }

            if (resultDB && resultlist && resultTime && resultNow) {
                finalAdapter.aFinal.add(0, selectedSchedule)
                finalAdapter.notifyDataSetChanged()
            } else {
                var toast = Toast.makeText(context, "이미 등록되어 있거나, 시간이 겹치는 과목입니다.", Toast.LENGTH_LONG)
                toast.show()
            }

        }
        holder.info.text = select[position].subject + " " + select[position].professor + " " + select[position].room
        holder.time.text = select[position].time
    }

    fun judgeTime (scheduleDatas : ArrayList<ScheduleData>, selectedSChedule : ScheduleData) : Boolean {
        var timeIntList : ArrayList<Int> = arrayListOf()
        for (schedule in scheduleDatas) {
            var time = schedule.time
            var timeList = time.split("  ")
            var timeInt = 0
            for (time in timeList) { // time 화)10:00∼13:00
                when (time[0].toString()) {
                    "일" -> timeInt = 1
                    "월" -> timeInt = 2
                    "화" -> timeInt = 3
                    "수" -> timeInt = 4
                    "목" -> timeInt = 5
                    "금" -> timeInt = 6
                    "토" -> timeInt = 7
                }

                timeInt *= 10000

                var timeTemp = time.subSequence(2, time.length) // timeTemp = 10:00∼13:00
                var timeStart = (timeTemp.subSequence(0,2).toString() + timeTemp.subSequence(3,5).toString()).toInt() + timeInt
                var timeEnd = (timeTemp.subSequence(6,8).toString() + timeTemp.subSequence(9, 11).toString()).toInt() + timeInt


                while (timeStart < timeEnd) {
                    timeIntList.add(timeStart)
                    timeStart = timeStart + 30
                    if (timeStart % 100 == 60) {
                        timeStart = timeStart + 40
                    }
                }

            }
        }
        var timeInput = selectedSChedule.time
        var timeListInput = timeInput.split("  ")
        var timeIntInput = 0
        for (time in timeListInput) {
            when (time[0].toString()) {
                "일" -> timeIntInput = 1
                "월" -> timeIntInput = 2
                "화" -> timeIntInput = 3
                "수" -> timeIntInput = 4
                "목" -> timeIntInput = 5
                "금" -> timeIntInput = 6
                "토" -> timeIntInput = 7
            }

            timeIntInput *= 10000

            var timeTempInput = time.subSequence(2, time.length) // timeTemp = 10:00∼13:00
            var timeStartInput = (timeTempInput.subSequence(0, 2).toString() + timeTempInput.subSequence(3, 5).toString()).toInt() + timeIntInput
            var timeEndInput = (timeTempInput.subSequence(6, 8).toString() + timeTempInput.subSequence(9, 11).toString()).toInt() + timeIntInput


            while (timeStartInput < timeEndInput) {
                if (timeStartInput in timeIntList) {
                    return false
                }
                timeStartInput = timeStartInput + 30
                if (timeStartInput % 100 == 60) {
                    timeStartInput = timeStartInput + 40
                }
            }
        }

        return true
    }
}