package com.example.capstoneproject.ui.feature.item.diari.tekanandarah

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.capstoneproject.databinding.ActivityTekananBinding

class TekananActivity : AppCompatActivity() {

    private lateinit var binding: ActivityTekananBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTekananBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding?.fab?.setOnClickListener { view ->
            val intent = Intent(this, TambahTekananActivity::class.java)
            startActivity(intent)
        }
    }
}