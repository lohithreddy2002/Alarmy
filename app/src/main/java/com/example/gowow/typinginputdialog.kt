package com.example.gowow

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import com.google.android.material.button.MaterialButton
import kotlinx.serialization.descriptors.PrimitiveKind


class typinginputdialog : DialogFragment() {
    private lateinit var listner: typecount
    override fun onCreate(savedInstanceState: Bundle?) {
        setStyle(STYLE_NORMAL, R.style.Theme_Gowow)
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_typinginputdialog, container, false)
    }

    override fun onStart() {
        super.onStart()
//
//        dialog?.findViewById<RadioGroup>(R.id.typegrp)?.setOnCheckedChangeListener { group, checkedId ->
//            if(checkedId == R.id.ownphrase){
//                dismiss()
//            }
//
//        }

        dialog?.findViewById<MaterialButton>(R.id.submit_type)?.setOnClickListener {
            if (dialog?.findViewById<EditText>(R.id.editTextNumber2)?.text.toString() != "") {
                Toast.makeText(this.context, "enter valid integer", Toast.LENGTH_SHORT).show()
                val words =
                    Integer.parseInt(dialog?.findViewById<EditText>(R.id.editTextNumber2)?.text.toString())
                listner.sendtypecount(words)
                dismiss()
            }
        }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        listner = targetFragment as typecount
    }

    interface typecount {
        fun sendtypecount(words: Int)
    }


}