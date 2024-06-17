package com.example.capstoneproject.ui.feature.item.pendeteksijerawat

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.capstoneproject.databinding.ActivityPendeteksiJerawatBinding

class PendeteksiJerawatActivity : AppCompatActivity() {

    private lateinit var binding: ActivityPendeteksiJerawatBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPendeteksiJerawatBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}