package project.mobile.kau.com.mobileswproject

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView
import android.widget.Button
import com.orm.SugarRecord

class TimeTableActivity : AppCompatActivity() {

    var colors : ArrayList<Int> = arrayListOf()
    var buttonAdd : Button? = null
    var buttonRefresh : Button? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_time_table)
        initModel()
        initView()
        aboutView()

    }

    var recyclerView: RecyclerView? = null
    var recyclerViewManager: RecyclerView.LayoutManager? = null
    var recyclerViewAdapter: RecyclerView.Adapter<TableAdapter.ViewHolder>? = null

    var subjectList: ArrayList<TableData> = arrayListOf()
    var informationList: ArrayList<ScheduleData> = arrayListOf ()


    override fun onResume() {
        super.onResume()

        initModel()
        initView()
        recyclerViewAdapter?.notifyDataSetChanged()
    }

    private fun aboutView () {
        buttonAdd?.setOnClickListener {

            val intent = Intent(this,ScheduleSelectActivity::class.java)
            startActivity(intent)

        }

        buttonRefresh?.setOnClickListener {
            openDialog()

        }
    }

    private fun initView() {

        recyclerView = findViewById(R.id.recyclerView)
        recyclerView?.setHasFixedSize(false)
        recyclerViewManager = GridLayoutManager(this, 6)
        recyclerViewAdapter = TableAdapter(this, subjectList)
        recyclerView?.layoutManager = recyclerViewManager
        recyclerView?.adapter = recyclerViewAdapter

        buttonAdd = findViewById(R.id.addButton)
        buttonRefresh = findViewById(R.id.buttonRefresh)

    }


    private fun initModel() {

        colors.clear()
        colors.add(Color.rgb(234,133,188))
        colors.add(Color.rgb(233,196,106))
        colors.add(Color.rgb(162,194,106))
        colors.add(Color.rgb(216,162,212))
        colors.add(Color.rgb(122,161,220))
        colors.add(Color.rgb(245,164,101))
        colors.add(Color.rgb(129,209,191))


        subjectList = arrayListOf()
        informationList = SugarRecord.listAll(ScheduleData::class.java) as ArrayList<ScheduleData>

        var time: Int = 9
        var half: Int = 30

        var roomFirstName: ArrayList<String> = ArrayList<String>()
        var roomSecondName: ArrayList<String> = ArrayList<String>()

        var subjectDay: ArrayList<String> = ArrayList<String>()
        var subjectSecondDay: ArrayList<String> = ArrayList<String>()

        var timeHalf: ArrayList<Int> = ArrayList<Int>()
        var timeSecondHalf: ArrayList<Int> = ArrayList<Int>()

        var timeMin: ArrayList<Int> = ArrayList<Int>()
        var timeSecondMin: ArrayList<Int> = ArrayList<Int>()

        var timeMax: ArrayList<Int> = ArrayList<Int>()
        var timeSecondMax: ArrayList<Int> = ArrayList<Int>()
        /*
        informationList.add(ScheduleData("컴퓨터구조론", "교수1", "전공1", "월)10:30~12:00 수)10:30~12:00", "강의동101"))
        informationList.add(ScheduleData("알고리즘", "교수2", "전공2", "화)10:30~12:00 목)10:30~12:00", "전자관402"))
        informationList.add(ScheduleData("모바일sw", "교수3", "전공3", "수)14:00~16:00 수)16:00~18:00", "전자관402/전자관420"))
        informationList.add(ScheduleData("인공지능입문", "교수4", "전공4", "월)15:00~16:30 수)09:00~10:30", "강의동102"))
*/

        subjectList.clear()
        subjectList.add(TableData(""))
        subjectList.add(TableData("월"))
        subjectList.add(TableData("화"))
        subjectList.add(TableData("수"))
        subjectList.add(TableData("목"))
        subjectList.add(TableData("금"))

        var maxIndex = maxIndex (informationList)
        maxIndex /= 6
        maxIndex += 3

        for (j in 0..maxIndex) {

            for (i in 0..5) {
                if (i == 0) {
                    if (j % 2 == 0) {
                        subjectList.add(TableData((time.toString())))
                    } else {//(j%2 == 1) j를 2로 나눈 나머지가 1이면 9시에서 10시로 증가
                        subjectList.add(TableData(""))
                        //9:30분 없애고 싶으면 위에 내용 빈칸으로 설정
                        time++
                    }
                } else {
                    if (j <= maxIndex) {
                        subjectList.add(TableData(""))
                    }
                }
            }
        }

        for (i in 0..(informationList.size - 1)) { // 요일 따로, 시간따로 구분

            var timeNum: String = informationList.get(i).time
            var timeNum_arr1 = timeNum.subSequence(2, 4).toString()//2,4
            var timeNum_arr2 = timeNum.subSequence(5, 7).toString()//5,7
            var timeNum_arr3 = Integer.parseInt("$timeNum_arr1$timeNum_arr2")
            var timeNum_arr4 = Integer.parseInt("$timeNum_arr2")
            timeHalf.add(i, timeNum_arr4)
            timeMin.add(i, timeNum_arr3)



            timeNum_arr1 = timeNum.subSequence(8, 10).toString()//8,10
            timeNum_arr2 = timeNum.subSequence(11, 13).toString()//11,13
            timeNum_arr3 = Integer.parseInt("$timeNum_arr1$timeNum_arr2")
            timeMax.add(i, timeNum_arr3)

            subjectDay.add(i, timeNum.subSequence(0, 1).toString()) // 앞에시간 정리 끝

            //check = timeNum.length , check > 13
            if (informationList.get(i).time.length > 13) {
                var timeNum_temp: String? = timeNum.substring(14) // 두번째시간 추출
                if (timeNum_temp?.length != null) {
                    timeNum_arr1 = timeNum_temp?.subSequence(3, 5).toString()
                    timeNum_arr2 = timeNum_temp?.subSequence(6, 8).toString()
                    timeNum_arr3 = Integer.parseInt("$timeNum_arr1$timeNum_arr2")
                    timeNum_arr4 = Integer.parseInt("$timeNum_arr2")
                    timeSecondHalf.add(i, timeNum_arr4)
                    timeSecondMin.add(i, timeNum_arr3)

                    timeNum_arr1 = timeNum_temp?.subSequence(9, 11).toString()
                    timeNum_arr2 = timeNum_temp?.subSequence(12, 14).toString()
                    timeNum_arr3 = Integer.parseInt("$timeNum_arr1$timeNum_arr2")
                    timeSecondMax.add(i, timeNum_arr3)

                    subjectSecondDay.add(i, timeNum_temp?.subSequence(1, 2).toString())
                }
            } else {
                subjectSecondDay.add(i, "")
                timeSecondHalf.add(i, 0)
                timeSecondMax.add(i, 0)
                timeSecondMin.add(i, 0)

            }

            if (informationList.get(i).room.length > 8) {
                var roomNum: String = informationList.get(i).room
                var roomNum_arr1 = roomNum.split("/")
                roomFirstName.add(i, roomNum_arr1[0])


                var roomNum_temp: String? = roomNum_arr1[1]
                if (roomNum_temp?.length != null) {
                    roomSecondName.add(i, roomNum_temp)
                }
            } else {
                roomFirstName.add(i, informationList.get(i).room)
                roomSecondName.add(i, "")
            }

        }

        for (i in 0..(informationList.size - 1)) { // 위치에 알맞게 배정
            var fitableColumNum: Int = 0
            var fitableStartRowNum: Int = 0
            var sucessiveClassTime: Int = 0
            var fitableSecondColumNum: Int = 0
            var fitableSecondStartRowNum: Int = 0
            var sucessiveSecondClassTime: Int = 0
            var cnt: Int = 1
            var positionSecond: Int = 0
            var position: Int = 0

            fitableStartRowNum = (((timeMin[i] - timeHalf[i]) / 100) - 9) % 13 * 2 + 1
            if (timeHalf[i] > 0) {
                fitableStartRowNum++
            }
            when (subjectDay[i]) {
                "월" -> fitableColumNum = 1
                "화" -> fitableColumNum = 2
                "수" -> fitableColumNum = 3
                "목" -> fitableColumNum = 4
                "금" -> fitableColumNum = 5
            }
            when (timeMax[i] - timeMin[i]) {
                100 -> sucessiveClassTime = 2
                130 -> sucessiveClassTime = 3
                170 -> sucessiveClassTime = 3
                200 -> sucessiveClassTime = 4
                230 -> sucessiveClassTime = 5
                300 -> sucessiveClassTime = 6
                330 -> sucessiveClassTime = 7
                400 -> sucessiveClassTime = 8
                430 -> sucessiveSecondClassTime = 9
                500 -> sucessiveSecondClassTime = 10
            }

            for (j in 0..maxIndex) {
                if (cnt > sucessiveClassTime) {
                    cnt = 1
                    break
                }
                for (k in 0..5) {
                    if (j == fitableStartRowNum && k == fitableColumNum) {
                        subjectList[position].color = colors[i]
                        if (cnt == 1) {
                            subjectList[position].subjectName = "${informationList.get(i).subject}"

                        }
                        if (cnt == 2) {
                            subjectList[position].roomNumber = "${roomFirstName[i]}"
                        }
                        fitableStartRowNum++
                        cnt++
                    }
                    position++
                }
            }
            if (informationList.get(i).time.length > 13) {

                var cntSecond: Int = 1
                fitableSecondStartRowNum = (((timeSecondMin[i] - timeSecondHalf[i]) / 100) - 9) % 13 * 2 + 1
                if (timeSecondHalf[i] > 0) {
                    fitableSecondStartRowNum++
                }
                when (subjectSecondDay[i]) {
                    "월" -> fitableSecondColumNum = 1
                    "화" -> fitableSecondColumNum = 2
                    "수" -> fitableSecondColumNum = 3
                    "목" -> fitableSecondColumNum = 4
                    "금" -> fitableSecondColumNum = 5
                }
                when (timeSecondMax[i] - timeSecondMin[i]) {
                    100 -> sucessiveSecondClassTime = 2
                    130 -> sucessiveSecondClassTime = 3
                    170 -> sucessiveSecondClassTime = 3
                    200 -> sucessiveSecondClassTime = 4
                    230 -> sucessiveSecondClassTime = 5
                    300 -> sucessiveSecondClassTime = 6
                    330 -> sucessiveSecondClassTime = 7
                    400 -> sucessiveSecondClassTime = 8
                    430 -> sucessiveSecondClassTime = 9
                    500 -> sucessiveSecondClassTime = 10
                }
                for (j in 0..maxIndex) {
                    if (cntSecond > sucessiveSecondClassTime) {
                        cntSecond = 1
                        break
                    }
                    for (k in 0..5) {
                        if (j == fitableSecondStartRowNum && k == fitableSecondColumNum) {
                            subjectList[positionSecond].color = colors[i]
                            if (cntSecond == 1) {
                                subjectList[positionSecond].subjectName = "${informationList.get(i).subject}"
                            }
                            if (cntSecond == 2) {
                                if (roomSecondName[i].length > 2) {
                                    subjectList[positionSecond].roomNumber = "${roomSecondName[i]}"
                                } else {
                                    subjectList[positionSecond].roomNumber = "${roomFirstName[i]}"
                                }
                            }
                            fitableSecondStartRowNum++
                            cntSecond++
                        }
                        positionSecond++
                    }
                }
            }
        }
    }


    fun maxIndex (list : ArrayList<ScheduleData>) : Int {


        var max = 0

        var time = ""
        for (i in 0..(list.size - 1)) {
            var index = 0
            var time_arr = list[i].time.split("/")

            for (j in 0..(time_arr.size - 1)) {
                var temp_time = time_arr[j]
                if (temp_time.contains("월")) {
                    index = 1
                } else if (temp_time.contains("화")) {
                    index = 2
                } else if (temp_time.contains("수")) {
                    index = 3
                } else if (temp_time.contains("목")) {
                    index = 4
                } else if (temp_time.contains("금")) {
                    index = 5
                }

                temp_time.substring(2)

                var timeNum_arr = temp_time.split("∼")
                var hour : Int = 0
                var timeHour_arr : List<String> = listOf()

                timeHour_arr = timeNum_arr[1].split(":")
                hour = Integer.parseInt(timeHour_arr[0])
                var timeNum_2 = ((hour - 9) * 2 + 1) * 6 + index
                if (timeHour_arr[1].equals("30")) {
                    timeNum_2 += 6
                }

                if (max < timeNum_2) {
                    max = timeNum_2
                }
            }
        }

        return max
    }

    fun openDialog () {
        var builder : AlertDialog.Builder = AlertDialog.Builder(this)
        builder.setTitle("확인")
        builder.setMessage("시간표를 초기화 시키시겠습니까 ?")

        builder.setPositiveButton("확인"){dialog, which ->
            SugarRecord.deleteAll(ScheduleData::class.java)
            for (data in informationList) {
                alarmCancel(data)
            }
            informationList.clear()

            initModel()
            initView()
            recyclerViewAdapter?.notifyDataSetChanged()

            //알람 전체 삭제 들어가야함
        }
        builder.setNegativeButton("취소") {dialog, which ->

        }

        var alertDialog : AlertDialog = builder.create()
        alertDialog.show()
    }

    fun alarmCancel (data : ScheduleData) {
        var alarmManager : AlarmManager = this.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        var dataId = data.id % 20
        var times = data.time.split("  ")
        for (time in times) {
            var intent = Intent (this, ScheduleAlarmReceiver::class.java)
            var pending = PendingIntent.getBroadcast(this, dataId.toInt(), intent, PendingIntent.FLAG_UPDATE_CURRENT)
            if (pending != null) {
                alarmManager.cancel(pending)
                pending.cancel()
            }
            dataId += 20
        }
    }
}