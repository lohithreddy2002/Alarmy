package com.example.gowow.recyclerview

import android.content.Context
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.PopupMenu
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.gowow.R
import com.example.gowow.RemViewModel
import com.example.gowow.db.entity.Alarm
import com.google.android.material.snackbar.Snackbar

class adapter(var ReminderList: List<Alarm>, private val viewModel: RemViewModel,private val listner:(Alarm)->Unit) :
    RecyclerView.Adapter<adapter.viewholder>() {


    class viewholder(itemview: View) : RecyclerView.ViewHolder(itemview) {
        private val rem_time = itemview.findViewById<TextView>(R.id.rem_time)
        private val checkbox = itemview.findViewById<CheckBox>(R.id.active)

        fun bind(a: Alarm) {
            rem_time.text = "%02d:%02d".format(a.hour, a.minute)
            checkbox.isChecked = a.started

        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): viewholder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.recycler_layout, parent, false)
        return viewholder(view)
    }

    override fun onBindViewHolder(holder: viewholder, position: Int) {
        val alarm = ReminderList[position]

        val optionsbutton = holder.itemView.findViewById<TextView>(R.id.optionmenu)


        holder.itemView.setOnClickListener {
            if(alarm.recurring == true){
            listner(alarm)
        }
        else{
                Toast.makeText(holder.itemView.context, "Timer can not be edited", Toast.LENGTH_SHORT).show()
            }
        }

        fun onMenuItemClick(item: MenuItem, context: Context): Boolean {
            when (item.getItemId()) {
                R.id.delete -> {
                    viewModel.delete(alarm)
                    notifyItemRemoved(position)
                    notifyItemRangeChanged(0,ReminderList.size)
                    Snackbar.make(holder.itemView,"adsa",Snackbar.LENGTH_LONG).setAction("undo"){
                        viewModel.insert(alarm)
                    }.show()
                    Toast.makeText(context, "delete", Toast.LENGTH_SHORT).show()
                    return true
                }
                R.id.update -> {
                    Toast.makeText(context, "update", Toast.LENGTH_SHORT).show()
                    return true
                }
            }
            return false
        }
        optionsbutton.setOnClickListener {
            val popupMenu = PopupMenu(holder.itemView.context, optionsbutton)
            popupMenu.inflate(R.menu.alaramoptions)
            popupMenu.setOnMenuItemClickListener {
                onMenuItemClick(it, holder.itemView.context)
            }

            popupMenu.show()
        }






        holder.bind(alarm)
        val checkbox = holder.itemView.findViewById<CheckBox>(R.id.active)
        checkbox.setOnClickListener {
            if (!checkbox.isChecked) {
                alarm.cancelAlarm(holder.itemView.context);
                alarm.started = false
                viewModel.update(alarm);
            } else {

                alarm.schedule(holder.itemView.context);
                alarm.started = true
                viewModel.update(alarm);
            }
        }


    }


    override fun getItemCount(): Int {
        return ReminderList.size
    }
}