package com.example.gowow

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioButton
import android.widget.RadioGroup
import com.google.android.material.bottomsheet.BottomSheetDialogFragment


class SnoozeDialog : BottomSheetDialogFragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_snoozedialog, container, false)
    }

    private lateinit var listener: onsnoozeselected

    interface onsnoozeselected {
        fun sendInput(snz: Int): Int
    }

    override fun onStart() {
        super.onStart()
        dialog?.findViewById<RadioGroup>(R.id.snozetime)
            ?.setOnCheckedChangeListener { group, checkedId ->
                when (checkedId) {
                    R.id.s1 -> listener.sendInput(1)
                    R.id.s5 -> listener.sendInput(5)
                    R.id.s10 -> listener.sendInput(10)
                    R.id.s15 -> listener.sendInput(15)
                    R.id.s20 -> listener.sendInput(20)
                    R.id.s25 -> listener.sendInput(25)
                    R.id.s30 -> listener.sendInput(30)
                }
                dismiss()
            }

    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        Log.d("host", "$host")
        try {
            listener = targetFragment as onsnoozeselected

        } catch (e: ClassCastException) {
            throw ClassCastException(
                (context.toString() +
                        " must implement NoticeDialogListener")
            )

        }
    }

}