package project.mobile.kau.com.mobileswproject

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView
import org.w3c.dom.Text

class RecyclerViewer(subjectList : ArrayList<MySubject>) : RecyclerView.Adapter<RecyclerViewer.ViewHolder>() {
    private var mySubjectList : ArrayList<MySubject> = subjectList

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view){
        var textView_subject : TextView = view.findViewById(R.id.textView_subject)
        //var textView_buildingName : TextView = view.findViewById(R.id.textView_buidingName)
        var textView_roomNumber : TextView = view.findViewById(R.id.textView_roomNumber)

    }
    override fun onBindViewHolder(viewHolder: RecyclerViewer.ViewHolder, position: Int) {
        var temp : MySubject = mySubjectList.get(position)
        viewHolder.textView_subject.text = temp.subjectName
        //viewHolder.textView_buildingName.text = temp.buildingName
        viewHolder.textView_roomNumber.text = temp.roomNumber
    }

    override fun getItemCount(): Int {
        return mySubjectList.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, position : Int): RecyclerViewer.ViewHolder {
        val view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_viewer, parent, false)

        return ViewHolder(view)
    }


}