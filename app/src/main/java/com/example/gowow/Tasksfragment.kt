package com.example.gowow

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.google.android.material.button.MaterialButton


class Tasksfragment : DialogFragment(), stepsinputDialog.stepscount {

    private lateinit var listner: OntaskSelected

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(STYLE_NORMAL, R.style.Theme_Gowow)
    }

    interface OntaskSelected {
        fun sendTask(Label: String, value: Int)

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
            val fm = fragmentManager
            val dialog = stepsinputDialog()
            dialog.setTargetFragment(this, 200)
            if (fm != null) {
                dialog.show(fm, "")
            }
        }
        dialog?.findViewById<MaterialButton>(R.id.typing)?.setOnClickListener {
            val fm = fragmentManager
            val dialog = typinginputdialog()
            dialog.setTargetFragment(this, 200)
            if (fm != null) {
                dialog.show(fm, "")
            }
        }
        dialog?.findViewById<MaterialButton>(R.id.shake)?.setOnClickListener {
            dismiss()
        }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        try {
            listner = targetFragment as OntaskSelected
        } catch (e: java.lang.ClassCastException) {
            throw ClassCastException(
                ("$context must implement NoticeDialogListener")
            )
        }
    }

    override fun sendstepscoount(steps: Int) {
        listner.sendTask("STEPS", steps)
        dismiss()
    }
}