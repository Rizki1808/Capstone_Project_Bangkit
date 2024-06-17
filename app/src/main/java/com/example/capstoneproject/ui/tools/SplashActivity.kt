package com.example.capstoneproject.ui.tools

import android.annotation.SuppressLint
import android.content.Intent
import android.os.AsyncTask
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.capstoneproject.R
import com.example.capstoneproject.ui.main.MainActivity

@SuppressLint("CustomSplashScreen")
class SplashActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        // Memulai tugas asyncronous untuk memuat data
        LoadDataTask().execute()
    }

    @SuppressLint("StaticFieldLeak")
    private inner class LoadDataTask : AsyncTask<Void, Void, Void>() {

        @Deprecated("Deprecated in Java")
        override fun doInBackground(vararg params: Void?): Void? {
            // Simulasikan pemuatan data (contohnya dengan menambahkan thread sleep)
            try {
                Thread.sleep(2000) // Simulasi pemuatan data selama 3 detik
            } catch (e: InterruptedException) {
                e.printStackTrace()
            }

            return null
        }

        @Deprecated("Deprecated in Java")
        override fun onPostExecute(result: Void?) {
            super.onPostExecute(result)

            // Setelah data dimuat, navigasi ke aktivitas utama
            val intent = Intent(this@SplashActivity, MainActivity::class.java)
            startActivity(intent)
            finish()
        }
    }
}