package com.example.gowow

import android.content.Context
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import com.example.gowow.factory.RemFactory
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class LabelDialog:BottomSheetDialogFragment() {

    private lateinit var listener: Onlabelseleccted
    val viewModel: RemViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val databse = ReminderDatabase(requireContext())
        val repo = Remrepository(databse)
        val factory = RemFactory(repo)

        return inflater.inflate(R.layout.fragment_label_dialog, container, false)
    }

    interface Onlabelseleccted{
        fun sendlabel(Label:String)
    }
    override fun onAttach(context: Context) {
        super.onAttach(context)
        Log.d("host", "$host")
        try {
            listener = targetFragment as Onlabelseleccted

        } catch (e: ClassCastException) {
            throw ClassCastException(
                (context.toString() +
                        " must implement NoticeDialogListener")
            )

        }
    }




    override fun onStart() {
        super.onStart()

        dialog?.findViewById<Button>(R.id.labelenter)?.setOnClickListener {
            val tosubmit = dialog?.findViewById<EditText>(R.id.labeltext)?.text.toString()
            listener.sendlabel(tosubmit)
            dismiss()
        }

    }

}