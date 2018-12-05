package project.mobile.kau.com.mobileswproject

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

class ScheduleSelectActivity: AppCompatActivity() {

    private lateinit var selecting_recyclerView: RecyclerView
    private lateinit var selecting_viewAdapter: RecyclerView.Adapter<*>
    private lateinit var selecting_viewManager: RecyclerView.LayoutManager
    private lateinit var selected_recyclerView: RecyclerView
    private lateinit var selected_viewAdapter: RecyclerView.Adapter<*>
    private lateinit var selected_viewManager: RecyclerView.LayoutManager
    var major = ""
    var objects: ArrayList<Object> = arrayListOf()
    var select_object :ArrayList<Object> = arrayListOf()
    var final_list : ArrayList<Object> = arrayListOf()
    var dataBase = SugarRecord.listAll(Data::class.java) as ArrayList<Data> //for db

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.schedule_select)
        val spinner_lecture = findViewById(R.id.spinner_major) as Spinner
        spinner_lecture.adapter = ArrayAdapter.createFromResource(this,R.array.major,android.R.layout.simple_spinner_item)
        spinner_lecture.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onNothingSelected(parent: AdapterView<*>?) {

            }
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                major = spinner_lecture.getItemAtPosition(position).toString()
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

        selected_viewManager = LinearLayoutManager(this,OrientationHelper.HORIZONTAL,false)
        selected_viewAdapter = ScheduleSelectedAdapter(final_list)
        selected_recyclerView = findViewById<RecyclerView>(R.id.selectedRecyclerView).apply {
            setHasFixedSize(true)
            layoutManager = selected_viewManager
            adapter = selected_viewAdapter
        }

        selecting_viewManager = LinearLayoutManager(this)
        selecting_viewAdapter = ScheduleAdapter(select_object,selected_viewAdapter as ScheduleSelectedAdapter,major)
        selecting_recyclerView = findViewById<RecyclerView>(R.id.selectingRecyclerView).apply {
            setHasFixedSize(true)
            layoutManager = selecting_viewManager
            adapter = selecting_viewAdapter
        }
        ScheduleGetServerInfo().getText(objects,select_object, selecting_viewAdapter as ScheduleAdapter)

        val add: Button = findViewById(R.id.add)
        add.setOnClickListener {
            for(i:Int in 0..final_list.size-1) {
                val temp = Data(final_list[i].subject, final_list[i].professor, final_list[i].major, final_list[i].time, final_list[i].room)
                dataBase.add(temp)
                temp.save()
            }
            val intent = Intent(this,ScheduleActivity::class.java)
            startActivity(intent)
        }
    }
}
