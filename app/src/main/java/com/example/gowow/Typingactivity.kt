package com.example.gowow

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.SpannableString
import android.text.Spanned
import android.text.TextWatcher
import android.text.style.BackgroundColorSpan
import android.text.style.ForegroundColorSpan
import android.text.style.TextAppearanceSpan
import android.text.style.UnderlineSpan
import android.util.Log
import android.widget.TextView
import com.example.gowow.databinding.ActivityTypingactivityBinding

class Typingactivity : AppCompatActivity() {
    private lateinit var binding: ActivityTypingactivityBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTypingactivityBinding.inflate(layoutInflater)

        setContentView(binding.root)
    }
    val string = SpannableString("this is a spannable string")
    val watcher = object :TextWatcher {
        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

        }

        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

        }
        override fun afterTextChanged(s: Editable?) {
            val x = BackgroundColorSpan(Color.GREEN)
            val y = BackgroundColorSpan(Color.RED)
            val z = BackgroundColorSpan(Color.WHITE)

            val str = s.toString()
            string.setSpan(z,str.length,string.length,0)
            var j = 0
            for(i in str.indices) {
                if (i < string.length) {
                    if (str[i] == string[i]) {
                        string.setSpan(x, j, i + 1, 0)
                        binding.texttest.text = string
                        j++
                    } else {
                        string.setSpan(y, j, i + 1, 0)
                        j
                        binding.texttest.text = string
                    }
                }


            }
        }
    }

        override fun onStart() {
        super.onStart()
            binding.texttest.text = string
        binding.textetest.addTextChangedListener(watcher)
    }

}