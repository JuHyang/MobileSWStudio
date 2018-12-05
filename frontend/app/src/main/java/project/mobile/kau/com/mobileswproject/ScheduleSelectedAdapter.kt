package project.mobile.kau.com.mobileswproject

import android.graphics.Color
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView

class ScheduleSelectedAdapter(val final : ArrayList<Object>) : RecyclerView.Adapter<ScheduleSelectedAdapter.ViewHolder>() {
    override fun getItemCount(): Int {
        return final.size
    }
    class ViewHolder(val view : View) : RecyclerView.ViewHolder(view) {
        var name = view.findViewById(R.id.lecture) as TextView
        var professor = view.findViewById(R.id.professor) as TextView
        var btn = view.findViewById(R.id.close) as Button

    }
    override fun onCreateViewHolder(parent: ViewGroup, type: Int): ScheduleSelectedAdapter.ViewHolder {
        val lectureView = LayoutInflater.from(parent.context).inflate(R.layout.schedule_item_selected, parent,false)
        return ViewHolder(lectureView)
    }
    override fun onBindViewHolder(holder : ScheduleSelectedAdapter.ViewHolder, position: Int) {
        holder.btn.setOnClickListener {
            final.removeAt(position)
            notifyDataSetChanged()
        }
        holder.name.text = final[position].subject
        holder.professor.text = final[position].professor
    }
}