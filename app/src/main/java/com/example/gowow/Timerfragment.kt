package com.example.gowow

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.gowow.databinding.FragmentTimerfragmentBinding
import com.example.gowow.db.entity.Alarm
import com.example.gowow.viewmodel.RemViewModel
import com.example.gowow.viewmodel.SavedStateViewModel
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import java.util.*


class TimerFragment : BottomSheetDialogFragment() {

    private lateinit var binding: FragmentTimerfragmentBinding
    private val viewModel: RemViewModel by viewModels()

    private val savedStateViewModel: SavedStateViewModel by viewModels()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_timerfragment, container, false)

    }

    override fun onStart() {
        super.onStart()
        binding = FragmentTimerfragmentBinding.bind(requireView())
        settext()

        binding.min1.setOnClickListener {
            savedStateViewModel.updatetime(1)
            settext()
        }
        binding.min5.setOnClickListener {
            savedStateViewModel.updatetime(5)
            settext()
        }
        binding.min10.setOnClickListener {
            savedStateViewModel.updatetime(10)
            settext()

        }
        binding.min15.setOnClickListener {
            savedStateViewModel.updatetime(15)
            settext()

        }
        binding.min30.setOnClickListener {
            savedStateViewModel.updatetime(30)
            settext()

        }
        binding.min60.setOnClickListener {
            savedStateViewModel.updatetime(60)
            settext()
        }

        binding.reset.setOnClickListener {
            savedStateViewModel.resettimer()
            settext()
        }
        val alarmId = Random().nextInt(Int.MAX_VALUE)


        binding.addtimer.setOnClickListener {
            val cal = Calendar.getInstance()
            Log.d("timeaa", "${cal.get(Calendar.HOUR_OF_DAY)}")
            var minute = savedStateViewModel.gettimer().second
            var hour = savedStateViewModel.gettimer().first


            minute = minute?.plus(cal.get(Calendar.MINUTE))
            hour = hour?.plus(cal.get(Calendar.HOUR_OF_DAY))
            if (minute != null) {
                hour = hour?.plus(minute / 60)
            }
            minute = minute?.rem(60)
            hour = hour?.rem(24)

            val days = arrayListOf(false, false, false, false, false, false, false)
            val alarm = Alarm(
                alarmId,
                hour!!,
                minute!!,
                "",
                true,
                false,
                0,
                days = days
            )
            viewModel.insert(alarm)
            alarm.schedule(this.requireContext())
            findNavController().navigate(R.id.action_timerfragment_to_homeFragment)
        }


    }

    private fun settext() {

        binding.timefortimer.text = "%02d:%02d".format(savedStateViewModel.gettimer().first, savedStateViewModel.gettimer().second)
    }


}