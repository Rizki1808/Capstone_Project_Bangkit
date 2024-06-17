package com.example.capstoneproject.ui.feature.item.diari

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.capstoneproject.databinding.ActivityDiaryBinding
import com.example.capstoneproject.ui.feature.item.diari.tekanandarah.TekananActivity
import com.example.capstoneproject.ui.feature.item.diari.guladarah.GulaActivity

class DiaryActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDiaryBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDiaryBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.cardViewPressure.setOnClickListener {
            val intent = Intent(this, TekananActivity::class.java)
            startActivity(intent)
        }
        binding.cardViewSugar.setOnClickListener {
            val intent = Intent(this, GulaActivity::class.java)
            startActivity(intent)
        }
    }
}