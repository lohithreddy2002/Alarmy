package com.example.gowow

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.google.android.material.button.MaterialButton


class Tasksfragment : DialogFragment() {

    private lateinit var listner: OntaskSelected

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(STYLE_NORMAL, R.style.Theme_Gowow)
    }

    interface OntaskSelected {
        fun sendTask(Label: String)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_tasksfragment, container, false)
    }

    override fun onStart() {
        super.onStart()
        dialog?.findViewById<MaterialButton>(R.id.steps)?.setOnClickListener {
            listner.sendTask("Steps")
            dismiss()
        }
        dialog?.findViewById<MaterialButton>(R.id.typing)?.setOnClickListener {
            listner.sendTask("Typing")
            dismiss()

        }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        try {
            listner = targetFragment as OntaskSelected
        } catch (e: java.lang.ClassCastException) {
            throw ClassCastException(
                (context.toString() +
                        " must implement NoticeDialogListener")
            )
        }
    }
}