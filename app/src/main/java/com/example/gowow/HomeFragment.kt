package com.example.gowow

import android.app.AlarmManager
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.gowow.databinding.FragmentHomeBinding
import com.example.gowow.db.entity.Alarm
import com.example.gowow.factory.RemFactory
import com.example.gowow.recyclerview.adapter


class HomeFragment : Fragment() {

    private lateinit var binding: FragmentHomeBinding
    private lateinit var alaramManager: AlarmManager
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

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

        val databse = ReminderDatabase(this.requireContext())
        val repo = Remrepository(databse)
        val factory = RemFactory(repo)

        val viewModel = ViewModelProvider(this, factory).get(RemViewModel::class.java)
        val adaper = adapter(listOf(), viewModel) { position -> adapterOnClick(position) }

        val recycle = binding.recyclerview
        recycle.adapter = adaper
        recycle.layoutManager = LinearLayoutManager(this.context)

        viewModel.getall().observe(this, {
            adaper.ReminderList = it
            adaper.notifyDataSetChanged()
        })
        binding.floatingActionButton.setOnClickListener {
            findNavController().navigate(R.id.action_homeFragment_to_fragmentAddReminder)

    }
        binding.timer.setOnClickListener {
            findNavController().navigate(R.id.action_homeFragment_to_timerfragment)
        }
        binding.test.setOnClickListener {
            val intent = Intent(this.context, Typingactivity::class.java)
            startActivity(intent)

        }


}


    fun adapterOnClick(alarm: Alarm) {

        val action = HomeFragmentDirections.actionHomeFragmentToFragmentAddReminder(alarm)
        findNavController().navigate(action)
        Toast.makeText(this.context, "${alarm.hour}", Toast.LENGTH_SHORT).show()
    }
}