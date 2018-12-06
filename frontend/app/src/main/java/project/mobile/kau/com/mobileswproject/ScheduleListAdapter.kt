package project.mobile.kau.com.mobileswproject

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView

class ScheduleListAdapter(val select : ArrayList<ScheduleData>, val finalAdapter: ScheduleSelectedAdapter, val major : String = "") : RecyclerView.Adapter<ScheduleListAdapter.ViewHolder>() {
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
            finalAdapter.aFinal.add(0, select[position])
            finalAdapter.notifyDataSetChanged()

        }
        holder.info.text = select[position].subject + " " + select[position].professor + " " + select[position].room
        holder.time.text = select[position].time
    }
}