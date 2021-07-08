package com.example.gowow


import android.app.AlarmManager
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
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
    val vm: SavedStateViewModel by viewModels()
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
        if (vm.getsnztime() != null) {
            binding.snztime.text = vm.getsnztime().toString()
        }
        if (vm.getLbel() != "") {
            binding.title.text = vm.getLbel()
        }
        if (vm.gettime().first != null) {
            binding.timePicker.hour = vm.gettime().first!!
        }
        if (vm.gettime().second != null) {
            binding.timePicker.minute = vm.gettime().second!!
        }



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
            binding.title.text = ala.title.toString()

        }


        val timerem = binding.timeofrem

        binding.timePicker.setOnTimeChangedListener { view, hourOfDay, minute ->
            timerem.text = "$hourOfDay:$minute"
            vm.settime(hourOfDay, minute)
        }
        var snztime = 0
        snztime = args.snoozetime

        binding.button2.setOnClickListener {
            val hour = binding.timePicker.hour
            val minute = binding.timePicker.minute
            if (binding.snztime.text.toString() != "") {
                snztime = binding.snztime.text.toString().toInt()
            }
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
            val fm = fragmentManager
            val dialog = snoozedialog()
            dialog.setTargetFragment(this, 200)
            if (fm != null) {
                dialog.show(fm, "")
            }

        }
        binding.label.setOnClickListener {
            val fm = fragmentManager
            val dialog = LabelDialog()
            dialog.setTargetFragment(this, 201)
            if (fm != null) {
                dialog.show(fm, "")
            }
        }



        binding.tasks.setOnClickListener {
            val fm = fragmentManager
            val dialog = Tasksfragment()
            dialog.setTargetFragment(this, 201)
            if (fm != null) {
                dialog.show(fm, "")
            }
        }
    }


    private fun scheduleAlaram(hour: Int, minute: Int, args: Alarm? = null, snz: Int) {
        var alarmId = Random().nextInt(Int.MAX_VALUE)
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
            vm.getLbel(),
            true,
            true,
            snoozetime = snz,
            days = days
        )
        val count = binding.count.text.toString()
        when (binding.taskname.text) {
            "STEPS" -> {
                alarm.stepsCount = Integer.parseInt(count)
            }
            "SHAKE" -> {
                alarm.shakeCount = Integer.parseInt(count)
            }
            "TYPING" -> {
                alarm.typingwords = Integer.parseInt(count)

            }

        }


        alarm.schedule(requireContext())

        if (args != null) {
            viewModel.update(alarm)
        } else {
            viewModel.insert(alarm)
        }

    }

    override fun sendInput(snz: Int): Int {
        vm.setsnztime(snz)
        binding.snztime.text = vm.getsnztime().toString()
        return snz
    }

    override fun sendlabel(Label: String) {
        vm.setLabel(Label)
        binding.title.text = vm.getLbel()
    }

    override fun sendTask(Label: String, value: Int) {
        task = Label
        binding.taskname.text = Label
        binding.count.text = value.toString()

    }

    override fun sendtypetask(Label: String, value: Int, type: String) {
        task = Label
        binding.taskname.text = Label
        binding.count.text = value.toString()


    }


}



