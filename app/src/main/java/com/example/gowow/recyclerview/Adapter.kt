package com.example.gowow.recyclerview

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.PopupMenu
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.gowow.R
import com.example.gowow.viewmodel.RemViewModel
import com.example.gowow.databinding.RecyclerLayoutBinding
import com.example.gowow.db.entity.Alarm
import com.google.android.material.snackbar.Snackbar

class AlarmAdapter(
    private val viewModel: RemViewModel,
    private val listener: (Alarm) -> Unit
) :
    RecyclerView.Adapter<AlarmAdapter.ViewHolder>() {

    private val callBack = object : DiffUtil.ItemCallback<Alarm>() {
        override fun areItemsTheSame(oldItem: Alarm, newItem: Alarm): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: Alarm, newItem: Alarm): Boolean {
            return oldItem.alarmId == newItem.alarmId
        }

    }

    val differ = AsyncListDiffer(this, callBack)


    inner class ViewHolder(private val ItemBinding: RecyclerLayoutBinding) :
        RecyclerView.ViewHolder(ItemBinding.root) {
        fun bind(a: Alarm) {
            var text = ""
            ItemBinding.remTime.text = "%02d:%02d".format(a.hour, a.minute)
            ItemBinding.active.isChecked = a.started
            if (!a.recurring) {
                ItemBinding.type.setImageResource(R.drawable.ic_timer1)
            } else {

                if (a.stepsCount > 0) {
                    ItemBinding.type.setImageResource(R.drawable.ic_footsteps)
                } else if (a.shakeCount > 0) {
                    ItemBinding.type.setImageResource(R.drawable.ic_shake)
                } else if (a.typingwords > 0) {
                    ItemBinding.type.setImageResource(R.drawable.ic_typing)
                } else {
                    ItemBinding.type.setImageResource(R.drawable.ic_alarm)
                }
                if (a.days[0] and a.days[1] and a.days[2] and a.days[3] and a.days[4] and a.days[5] and a.days[6]) {
                    text = "Everyday"
                    ItemBinding.days.text = text
                    Log.d("``", "1")
                } else if (a.days[1] && a.days[2] && a.days[3] && a.days[4] && a.days[5]) {
                    text = "Weekdays"
                    ItemBinding.days.text = text
                    Log.d("``", "1")
                } else if (a.days[0] && a.days[6]) {
                    text = "Weekends"
                    ItemBinding.days.text = text
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
                    ItemBinding.days.text = text
                }
            }
            ItemBinding.root.setOnClickListener {
                if (a.recurring) {
                    listener(a)
                } else {
                    Toast.makeText(
                       ItemBinding.root.context,
                        "Timer can not be edited",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }

            fun onMenuItemClick(item: MenuItem, context: Context): Boolean {
                when (item.getItemId()) {
                    R.id.delete -> {
                        viewModel.delete(a)
                        notifyItemRemoved(position)
                        notifyItemRangeChanged(0, differ.currentList.size)
                        Snackbar.make(ItemBinding.root, "adsa", Snackbar.LENGTH_LONG).setAction("undo") {
                            viewModel.insert(a)
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
            ItemBinding.optionmenu.setOnClickListener {
                val popupMenu = PopupMenu(ItemBinding.root.context, ItemBinding.optionmenu)
                popupMenu.inflate(R.menu.alaramoptions)
                popupMenu.setOnMenuItemClickListener {
                    onMenuItemClick(it,ItemBinding.root.context)
                }

                popupMenu.show()
            }
            ItemBinding.active.setOnClickListener {
                if (!ItemBinding.active.isChecked) {
                    a.cancelAlarm(ItemBinding.root.context)
                    a.started = false
                    viewModel.update(a)
                } else {

                    a.schedule(ItemBinding.root.context)
                    a.started = true
                    viewModel.update(a)
                }
            }

        }


    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = RecyclerLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val alarm = differ.currentList[position]
        holder.bind(alarm)

    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }
}