package com.example.gowow


import android.app.AlarmManager
import android.content.Context
import android.hardware.SensorManager
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.gowow.databinding.FragmentAddReminderBinding
import com.example.gowow.db.entity.Alarm
import com.example.gowow.factory.RemFactory
import java.util.*


class FragmentAddReminder : Fragment(), snoozedialog.onsnoozeselected, LabelDialog.Onlabelseleccted,
    Tasksfragment.OntaskSelected {

    private lateinit var binding: FragmentAddReminderBinding
    private lateinit var alaramManager: AlarmManager
    private val args by navArgs<FragmentAddReminderArgs>()
    lateinit var viewModel: RemViewModel
    var task = "None"

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val databse = ReminderDatabase(this.requireContext())
        val repo = Remrepository(databse)
        val factory = RemFactory(repo)
        alaramManager = activity?.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        viewModel = ViewModelProvider(this, factory).get(RemViewModel::class.java)
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_add_reminder, container, false)
    }

    override fun onStart() {

        super.onStart()
        binding = FragmentAddReminderBinding.bind(requireView())
        if (args.alarm != null) {
            val ala = args.alarm

            binding.timePicker.hour = ala!!.hour
            binding.timePicker.minute = ala.minute
            binding.chip1.isChecked = ala.days[0]
            binding.chip2.isChecked = ala.days[1]
            binding.chip3.isChecked = ala.days[2]
            binding.chip4.isChecked = ala.days[3]
            binding.chip5.isChecked = ala.days[4]
            binding.chip6.isChecked = ala.days[5]
            binding.chip7.isChecked = ala.days[6]
            binding.snztime.text = ala.snoozetime.toString()

        }


        val timerem = binding.timeofrem

        binding.timePicker.setOnTimeChangedListener { view, hourOfDay, minute ->
            timerem.text = "$hourOfDay:$minute"
        }
        var snztime = 0
        snztime = args.snoozetime

        binding.button2.setOnClickListener {
            val hour = binding.timePicker.hour
            val minute = binding.timePicker.minute
            if(binding.snztime.text.toString() != ""){
            snztime = binding.snztime.text.toString().toInt()}
            if (binding.chipgrp.checkedChipIds.size != 0) {
                if (args.alarm == null) {
                    scheduleAlaram(hour, minute, null, snztime)
                } else {
                    scheduleAlaram(hour, minute, args.alarm, args.alarm!!.snoozetime)
                }

                Log.d("timeclock", "${hour}:${minute}")


              findNavController().navigate(R.id.action_fragmentAddReminder_to_homeFragment)
            } else {
                Toast.makeText(requireContext(), "Pick the dates", Toast.LENGTH_SHORT).show()
            }
        }

        binding.snooze.setOnClickListener {
            val fm: FragmentManager? = fragmentManager
            val dialog = snoozedialog()
            dialog.setTargetFragment(this,200)
            if (fm != null) {
                dialog.show(fm, "")
            }

        }
        binding.label.setOnClickListener {
            val fm: FragmentManager? = fragmentManager
            val dialog = LabelDialog()
            dialog.setTargetFragment(this, 201)
            if (fm != null) {
                dialog.show(fm, "")
            }
        }

        binding.tasks.setOnClickListener {
            val fm: FragmentManager? = fragmentManager
            val dialog = Tasksfragment()
            dialog.setTargetFragment(this, 201)
            if (fm != null) {
                dialog.show(fm, "")
            }
        }
    }


    private fun scheduleAlaram(hour: Int, minute: Int, args: Alarm? = null, snz: Int) {
        var alarmId = Random().nextInt(Int.MAX_VALUE)
        var snztime = 0
        if (args != null) {
            this.context?.let { args.cancelAlarm(it) }
            alarmId = args.alarmId

        }

val days = arrayListOf(
    binding.chip1.isChecked,
    binding.chip2.isChecked,
    binding.chip3.isChecked,
    binding.chip4.isChecked,
    binding.chip5.isChecked,
    binding.chip6.isChecked,
    binding.chip7.isChecked
)
        val alarm = Alarm(
            alarmId,
            hour,
            minute,
            "",
            true,
            true,
            snz,
            days
        )

        alarm.schedule(requireContext())

        if (args != null) {
            viewModel.update(alarm)
        } else {
            viewModel.insert(alarm)
        }

    }

    override fun sendInput(snz: Int): Int {
        binding.snztime.text = snz.toString()
        return snz
    }

    override fun sendlabel(Label: String) {
        binding.title.text = Label
    }

    override fun sendTask(Label: String) {
        task = Label
        binding.taskname.text = Label

    }

}



