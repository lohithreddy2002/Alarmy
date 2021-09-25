package com.example.gowow

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import com.google.android.material.bottomsheet.BottomSheetDialogFragment


class StepsInputDialog : BottomSheetDialogFragment() {

    private lateinit var listner: stepscount

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_stepsinput_dialog, container, false)
    }

    override fun onStart() {
        super.onStart()

        dialog?.findViewById<Button>(R.id.submit_steps)?.setOnClickListener {
            val steps =
                Integer.parseInt(dialog?.findViewById<EditText>(R.id.editTextNumber)?.text.toString())
            listner.sendstepscoount(steps)
            dismiss()
        }
        dialog?.findViewById<Button>(R.id.cancel_steps)?.setOnClickListener {
            dismiss()
        }

    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        listner = targetFragment as stepscount
    }

    interface stepscount {
        fun sendstepscoount(steps: Int)
    }

}