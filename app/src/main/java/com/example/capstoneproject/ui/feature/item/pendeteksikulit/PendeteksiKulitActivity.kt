package com.example.capstoneproject.ui.feature.item.pendeteksikulit

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.example.capstoneproject.databinding.ActivityPendeteksiKulitBinding
import com.example.capstoneproject.ui.feature.item.getImageUri
import com.yalantis.ucrop.UCrop
import java.io.File

class PendeteksiKulitActivity : AppCompatActivity() {

    private lateinit var binding: ActivityPendeteksiKulitBinding
    private var currentImageUri: Uri? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPendeteksiKulitBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Set the ActionBar title
        val actionBar = supportActionBar
        actionBar?.title = "Selamat Datang"

        binding.btnCamera.setOnClickListener { startCamera() }
        binding.btnGalery.setOnClickListener { startGallery() }

        binding.ivHistoryKulit.setOnClickListener {
            val intent = Intent(this, HistoryKulitActivity::class.java)
            startActivity(intent)
        }
    }

    // Camera
    private fun startCamera() {
        currentImageUri = getImageUri(this)
        launcherIntentCamera.launch(currentImageUri!!)
    }

    private val launcherIntentCamera = registerForActivityResult(
        ActivityResultContracts.TakePicture()
    ) { isSuccess ->
        if (isSuccess) {
            showImage()
        }
    }

    // Gallery
    private fun startGallery() {
        launcherGallery.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
    }

    private val launcherGallery = registerForActivityResult(
        ActivityResultContracts.PickVisualMedia()
    ) { uri: Uri? ->
        if (uri != null) {
            showImage()
            startCrop(uri)
        } else {
            Log.d("Photo Picker", "No media selected")
        }
    }

    private fun startCrop(uri: Uri) {
        val destinationUri = Uri.fromFile(File(cacheDir, "cropped_${System.currentTimeMillis()}"))
        currentImageUri = destinationUri
        UCrop.of(uri, destinationUri)
            .start(this)
    }

    @Deprecated("This method has been deprecated in favor of using the Activity Result API\n      which brings increased type safety via an {@link ActivityResultContract} and the prebuilt\n      contracts for common intents available in\n      {@link androidx.activity.result.contract.ActivityResultContracts}, provides hooks for\n      testing, and allow receiving results in separate, testable classes independent from your\n      activity. Use\n      {@link #registerForActivityResult(ActivityResultContract, ActivityResultCallback)}\n      with the appropriate {@link ActivityResultContract} and handling the result in the\n      {@link ActivityResultCallback#onActivityResult(Object) callback}.")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == UCrop.REQUEST_CROP && resultCode == RESULT_OK) {
            currentImageUri = UCrop.getOutput(data!!)
            showImage()
        }
    }

    // Show Image
    private fun showImage() {
        currentImageUri?.let {
            Log.d("Image URI", "showImage: $it")
            binding.ivPreviewImageKulit.setImageURI(it)
        }
    }
}