package com.example.gowow.recyclerview

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.recyclerview.widget.RecyclerView
import com.example.gowow.R
import com.example.gowow.RemViewModel
import com.example.gowow.db.entity.Alarm
import com.google.android.material.snackbar.Snackbar

class adapter(
    var ReminderList: List<Alarm>,
    private val viewModel: RemViewModel,
    private val listner: (Alarm) -> Unit
) :
    RecyclerView.Adapter<adapter.viewholder>() {


    class viewholder(itemview: View) : RecyclerView.ViewHolder(itemview) {
        private val rem_time = itemview.findViewById<TextView>(R.id.rem_time)
        private val checkbox = itemview.findViewById<CheckBox>(R.id.active)
        private val imageview = itemview.findViewById<ImageView>(R.id.type)
        private val days = itemview.findViewById<TextView>(R.id.days)

        fun bind(a: Alarm) {
            var text = ""
            rem_time.text = "%02d:%02d".format(a.hour, a.minute)
            checkbox.isChecked = a.started
            if (!a.recurring) {
                imageview.setImageResource(R.drawable.ic_timer1)
            } else {

                if (a.stepsCount > 0) {
                    imageview.setImageResource(R.drawable.ic_footsteps)
                } else if (a.shakeCount > 0) {
                    imageview.setImageResource(R.drawable.ic_shake)
                } else if (a.typingwords > 0) {
                    imageview.setImageResource(R.drawable.ic_typing)
                } else {
                    imageview.setImageResource(R.drawable.ic_alarm)
                }
                Log.d("``", "${a.days}")

                if (a.days[0] and a.days[1] and a.days[2] and a.days[3] and a.days[4] and a.days[5] and a.days[6]) {
                    text = "Everyday"
                    days.text = text
                    Log.d("``", "1")
                } else if (a.days[1] && a.days[2] && a.days[3] && a.days[4] && a.days[5]) {
                    text = "Weekdays"
                    days.text = text
                    Log.d("``", "1")
                } else if (a.days[0] && a.days[6]) {
                    text = "Weekends"
                    days.text = text
                    Log.d("``", "1")
                } else {
                    if (a.days[0]) {
                        text += "Mon, "
                        Log.d("``", "2")
                    }
                    if (a.days[1]) {
                        text += "Tue, "
                    }
                    if (a.days[2]) {
                        text += "Wed, "
                    }
                    if (a.days[3]) {
                        text += "Thu, "
                    }
                    if (a.days[4]) {
                        text += "Fri, "
                    }
                    if (a.days[5]) {
                        text += "Sat, "
                    }
                    if (a.days[6]) {
                        text += "Sun"
                    }
                    Log.d("``", "1")
                    days.text = text
                }
            }


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
            if (alarm.recurring) {
                listner(alarm)
            } else {
                Toast.makeText(
                    holder.itemView.context,
                    "Timer can not be edited",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }

        fun onMenuItemClick(item: MenuItem, context: Context): Boolean {
            when (item.getItemId()) {
                R.id.delete -> {
                    viewModel.delete(alarm)
                    notifyItemRemoved(position)
                    notifyItemRangeChanged(0, ReminderList.size)
                    Snackbar.make(holder.itemView, "adsa", Snackbar.LENGTH_LONG).setAction("undo") {
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
                alarm.cancelAlarm(holder.itemView.context)
                alarm.started = false
                viewModel.update(alarm)
            } else {

                alarm.schedule(holder.itemView.context)
                alarm.started = true
                viewModel.update(alarm)
            }
        }


    }


    override fun getItemCount(): Int {
        return ReminderList.size
    }
}