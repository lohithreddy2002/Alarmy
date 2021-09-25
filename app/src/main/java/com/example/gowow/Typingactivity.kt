package com.example.gowow

import android.graphics.Color
import android.os.Bundle
import android.text.Editable
import android.text.SpannableString
import android.text.TextWatcher
import android.text.style.BackgroundColorSpan
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.gowow.databinding.ActivityTypingactivityBinding
import kotlin.random.Random

class TypingActivity : AppCompatActivity() {
    private lateinit var string: SpannableString
    private lateinit var binding: ActivityTypingactivityBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTypingactivityBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

    private val watcher = object : TextWatcher {
        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

        }

        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

        }

        override fun afterTextChanged(s: Editable?) {
            val x = BackgroundColorSpan(Color.GREEN)
            val y = BackgroundColorSpan(Color.RED)
            val z = BackgroundColorSpan(Color.WHITE)

            val str = s.toString().lowercase()
            string.setSpan(z, str.length, string.length, 0)
            var j = 0
            for (i in str.indices) {
                if (i < string.length) {
                    if (str[i] == string[i]) {
                        string.setSpan(x, j, i + 1, 0)
                        binding.texttest.text = string
                        j++
                    } else {
                        string.setSpan(y, j, i + 1, 0)
                        binding.texttest.text = string
                    }
                }

            }
        }
    }

    override fun onStart() {
        super.onStart()
        var tocomplete = intent.getIntExtra("tocomp", 2)

        next(tocomplete)
        binding.textetest.addTextChangedListener(watcher)
        binding.button.setOnClickListener {
            if (binding.textetest.text.toString().lowercase() == string.toString().lowercase()) {
                next(tocomplete)
                tocomplete--
            } else {
                Toast.makeText(this, "tryagain", Toast.LENGTH_SHORT).show()
            }
        }
    }

    fun next(tocomp: Int) {
        if (tocomp < 1) {
            finish()
        } else {
            binding.textetest.text.clear()
            string = SpannableString(
                resources.getStringArray(R.array.Motivational_Phrases)[Random.nextInt(
                    0,
                    10
                )]
            )
            binding.texttest.text = string
        }
    }

}