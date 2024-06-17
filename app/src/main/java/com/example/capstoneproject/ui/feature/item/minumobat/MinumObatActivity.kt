package com.example.capstoneproject.ui.feature.item.minumobat

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.capstoneproject.data.tools.ApplicationViewModelFactory
import com.example.capstoneproject.databinding.ActivityMinumObatBinding

class MinumObatActivity : AppCompatActivity() {

    private var _activityMinumObatBinding: ActivityMinumObatBinding? = null
    private val binding get() = _activityMinumObatBinding
    private lateinit var adapter: MinumObatAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _activityMinumObatBinding = ActivityMinumObatBinding.inflate(layoutInflater)
        setContentView(binding?.root)

        val viewModel = obtainViewModel(this@MinumObatActivity)
        viewModel.getAllDrugs().observe(this) { drugsList ->
            if (drugsList != null) {
                adapter.setListDrugs(drugsList)
            }
        }

        adapter = MinumObatAdapter()
        binding?.rvDrugs?.adapter = adapter
        binding?.rvDrugs?.setHasFixedSize(true)
        binding?.rvDrugs?.layoutManager = LinearLayoutManager(this)

        binding?.fab?.setOnClickListener { view ->
            val intent = Intent(this, TambahObatActivity::class.java)
            startActivity(intent)
        }
    }

    private fun obtainViewModel(activity: AppCompatActivity): MinumObatViewModel {
        val factory = ApplicationViewModelFactory.getInstance(activity.application)
        return ViewModelProvider(activity, factory).get(MinumObatViewModel::class.java)

    }

    override fun onDestroy() {
        super.onDestroy()
        _activityMinumObatBinding = null
    }
}