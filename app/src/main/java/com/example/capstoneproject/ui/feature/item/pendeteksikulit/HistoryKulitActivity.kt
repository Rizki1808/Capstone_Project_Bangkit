package com.example.capstoneproject.ui.feature.item.pendeteksikulit

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.capstoneproject.databinding.ActivityHistoryBinding

class HistoryKulitActivity : AppCompatActivity() {

    private lateinit var binding: ActivityHistoryBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHistoryBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}