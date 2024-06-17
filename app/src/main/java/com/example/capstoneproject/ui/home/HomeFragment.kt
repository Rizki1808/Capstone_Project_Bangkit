package com.example.capstoneproject.ui.home

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.capstoneproject.databinding.FragmentHomeBinding
import com.example.capstoneproject.ui.feature.item.diari.DiaryActivity
import com.example.capstoneproject.ui.feature.item.infopenyakit.InfoPenyakitActivity
import com.example.capstoneproject.ui.feature.item.minumobat.MinumObatActivity
import com.example.capstoneproject.ui.feature.item.rumahsakit.MapsActivity
import com.example.capstoneproject.ui.feature.item.pendeteksijerawat.PendeteksiJerawatActivity
import com.example.capstoneproject.ui.feature.item.pendeteksikulit.PendeteksiKulitActivity

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val homeViewModel =
            ViewModelProvider(this).get(HomeViewModel::class.java)

        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root

        binding.icInfoPenyakit.setOnClickListener {
            val intent = Intent(activity, InfoPenyakitActivity::class.java)
            startActivity(intent)
        }

        binding.icPendeteksiKulit.setOnClickListener {
            val intent = Intent(activity, PendeteksiKulitActivity::class.java)
            startActivity(intent)
        }

        binding.icPendeteksiJerawat.setOnClickListener {
            val intent = Intent(activity, PendeteksiJerawatActivity::class.java)
            startActivity(intent)
        }

        binding.icRumahSakitTerdekat.setOnClickListener {
            val intent = Intent(activity, MapsActivity::class.java)
            startActivity(intent)
        }

        binding.icPengingatMinumObat.setOnClickListener {
            val intent = Intent(activity, MinumObatActivity::class.java)
            startActivity(intent)
        }

        binding.icDiariKesehatan.setOnClickListener {
            val intent = Intent(activity, DiaryActivity::class.java)
            startActivity(intent)
        }

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}