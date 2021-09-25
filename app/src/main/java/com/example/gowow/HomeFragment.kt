package com.example.gowow

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.gowow.databinding.FragmentHomeBinding
import com.example.gowow.db.entity.Alarm
import com.example.gowow.recyclerview.AlarmAdapter
import com.example.gowow.viewmodel.RemViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : Fragment() {

    private lateinit var binding: FragmentHomeBinding

    private val viewModel: RemViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onStart() {
        super.onStart()
        binding = FragmentHomeBinding.bind(requireView())

        val adapter = AlarmAdapter(viewModel) { position -> adapterOnClick(position) }

        val recycle = binding.recyclerview
        recycle.adapter = adapter
        recycle.layoutManager = LinearLayoutManager(this.context)

        viewModel.getall().observe(this, {
            adapter.differ.submitList(it)
        })
        binding.floatingActionButton.setOnClickListener {
            findNavController().navigate(R.id.action_homeFragment_to_fragmentAddReminder)

        }
        binding.timer.setOnClickListener {
            findNavController().navigate(R.id.action_homeFragment_to_timerfragment)
        }

    }


    private fun adapterOnClick(alarm: Alarm) {

        val action = HomeFragmentDirections.actionHomeFragmentToFragmentAddReminder(alarm)
        findNavController().navigate(action)
        Toast.makeText(this.context, "${alarm.hour}", Toast.LENGTH_SHORT).show()
    }
}