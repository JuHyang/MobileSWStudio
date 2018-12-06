package project.mobile.kau.com.mobileswproject

import android.graphics.Color
import android.support.v7.app.AlertDialog
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView

class TableAdapter(context : TimeTableActivity, subjectList : ArrayList<TableData>) : RecyclerView.Adapter<TableAdapter.ViewHolder>() {

    private var context = context
    private var tableDataList : ArrayList<TableData> = subjectList

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view){
        var textView_subject : TextView = view.findViewById(R.id.textView_subject)
        //var textView_buildingName : TextView = view.findViewById(R.id.textView_buidingName)
        var textView_roomNumber : TextView = view.findViewById(R.id.textView_roomNumber)
        var tableView : View = view.findViewById(R.id.tableView)

    }
    override fun onBindViewHolder(viewHolder: TableAdapter.ViewHolder, position: Int) {
        var temp : TableData = tableDataList.get(position)
        viewHolder.textView_subject.text = temp.subjectName
        //viewHolder.textView_buildingName.text = temp.buildingName
        viewHolder.textView_roomNumber.text = temp.roomNumber
        viewHolder.tableView.setBackgroundColor(temp.color)

        viewHolder.tableView.setOnClickListener {
            if (temp.color != Color.rgb(255,255,255)) {
//                openDialog(position)
            }
        }
    }

    override fun getItemCount(): Int {
        return tableDataList.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, position : Int): TableAdapter.ViewHolder {
        val view = LayoutInflater.from(parent.getContext()).inflate(R.layout.table_item, parent, false)

        return ViewHolder(view)
    }


    fun openDialog (position : Int) {
        var builder : AlertDialog.Builder = AlertDialog.Builder(context)
        builder.setTitle("확인")
        builder.setMessage("?")

        builder.setPositiveButton("확인"){dialog, which ->

            //알람 전체 삭제 들어가야함
        }
        builder.setNegativeButton("취소") {dialog, which ->

        }

        var alertDialog : AlertDialog = builder.create()
        alertDialog.show()
    }


}