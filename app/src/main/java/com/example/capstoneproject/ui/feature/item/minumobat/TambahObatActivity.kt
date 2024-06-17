package com.example.capstoneproject.ui.feature.item.minumobat

import android.Manifest
import android.app.AlarmManager
import android.app.PendingIntent
import android.app.TimePickerDialog
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import com.example.capstoneproject.R
import com.example.capstoneproject.data.tools.ApplicationViewModelFactory
import com.example.capstoneproject.data.database.Drugs
import com.example.capstoneproject.data.helper.DateHelper
import com.example.capstoneproject.databinding.ActivityTambahObatBinding
import java.util.Calendar

class TambahObatActivity : AppCompatActivity() {

    private var _activityDrugsTambahObatBinding: ActivityTambahObatBinding? = null
    private val binding get() = _activityDrugsTambahObatBinding!!
    private lateinit var viewModel: TambahObatViewModel

    private var isEdit = false
    private var drugs: Drugs? = null
    private var selectedHour = 0
    private var selectedMinute = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _activityDrugsTambahObatBinding = ActivityTambahObatBinding.inflate(layoutInflater)
        setContentView(binding.root)

        checkAndRequestPermissions()

        viewModel = obtainViewModel(this@TambahObatActivity)

        drugs = intent.getParcelableExtra(EXTRA_DRUGS)
        if (drugs != null) {
            isEdit = true
        } else {
            drugs = Drugs(0, "", "", "", "", "")
        }

        val actionBarTitle: String
        val btnTitle: String

        if (isEdit) {
            actionBarTitle = getString(R.string.change)
            btnTitle = getString(R.string.update)
            if (drugs != null) {
                drugs?.let { drugs ->
                    binding.edNamaObat.setText(drugs.name)
                    binding.edDosis.setText(drugs.dose)
                    binding.edJamMinum.setText(drugs.timeToTake)
                    binding.edAturan.setText(drugs.instructions)
                    binding.edCatatan.setText(drugs.notes)

                    // Parse the existing time
                    val timeParts = drugs.timeToTake.split(":")
                    if (timeParts.size == 2) {
                        selectedHour = timeParts[0].toInt()
                        selectedMinute = timeParts[1].toInt()
                    }
                }
            }
        } else {
            actionBarTitle = getString(R.string.add)
            btnTitle = getString(R.string.save)
        }

        supportActionBar?.title = actionBarTitle
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        binding.btnSimpan.text = btnTitle

        // Show TimePickerDialog when time EditText is clicked
        binding.edJamMinum.setOnClickListener {
            val calendar = Calendar.getInstance()
            val hour = calendar.get(Calendar.HOUR_OF_DAY)
            val minute = calendar.get(Calendar.MINUTE)

            val timePickerDialog = TimePickerDialog(this, { _, selectedHour, selectedMinute ->
                this.selectedHour = selectedHour
                this.selectedMinute = selectedMinute
                binding.edJamMinum.setText(String.format("%02d:%02d", selectedHour, selectedMinute))
            }, hour, minute, true)

            timePickerDialog.show()
        }

        binding.btnSimpan.setOnClickListener {
            val name = binding.edNamaObat.text.toString().trim()
            val dose = binding.edDosis.text.toString().trim()
            val timeToTake = binding.edJamMinum.text.toString().trim()
            val instructions = binding.edAturan.text.toString().trim()
            val notes = binding.edCatatan.text.toString().trim()

            when {
                name.isEmpty() -> {
                    binding.edNamaObat.error = getString(R.string.empty)
                }
                dose.isEmpty() -> {
                    binding.edDosis.error = getString(R.string.empty)
                }
                timeToTake.isEmpty() -> {
                    binding.edJamMinum.error = getString(R.string.empty)
                }
                instructions.isEmpty() -> {
                    binding.edAturan.error = getString(R.string.empty)
                }
                else -> {
                    drugs.let { drugs ->
                        drugs?.name = name
                        drugs?.dose = dose
                        drugs?.timeToTake = timeToTake
                        drugs?.instructions = instructions
                        drugs?.notes = notes
                    }
                    if (isEdit) {
                        viewModel.updateDrug(drugs as Drugs)
                        showToast(getString(R.string.changed))
                    } else {
                        drugs.let { drugs ->
                            drugs?.date = DateHelper.getCurrentDate()
                        }
                        viewModel.insertDrug(drugs as Drugs)
                        showToast(getString(R.string.added))
                    }
                    scheduleNotification(name, selectedHour, selectedMinute)  // Schedule the notification
                    finish()
                }
            }
        }
    }

    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    override fun onDestroy() {
        super.onDestroy()
        _activityDrugsTambahObatBinding = null
    }

    private fun obtainViewModel(activity: AppCompatActivity): TambahObatViewModel {
        val factory = ApplicationViewModelFactory.getInstance(activity.application)
        return ViewModelProvider(activity, factory).get(TambahObatViewModel::class.java)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        if (isEdit) {
            menuInflater.inflate(R.menu.drugs_menu, menu)
        }
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_delete -> showAlertDialog(ALERT_DIALOG_DELETE)
            android.R.id.home -> showAlertDialog(ALERT_DIALOG_CLOSE)
        }
        return super.onOptionsItemSelected(item)
    }

    private fun showAlertDialog(type: Int) {
        val isDialogClose = type == ALERT_DIALOG_CLOSE
        val dialogTitle: String
        val dialogMessage: String
        if (isDialogClose) {
            dialogTitle = getString(R.string.cancel)
            dialogMessage = getString(R.string.message_cancel)
        } else {
            dialogMessage = getString(R.string.message_delete)
            dialogTitle = getString(R.string.delete)
        }
        val alertDialogBuilder = AlertDialog.Builder(this)
        with(alertDialogBuilder) {
            setTitle(dialogTitle)
            setMessage(dialogMessage)
            setCancelable(false)
            setPositiveButton(getString(R.string.yes)) { _, _ ->
                if (!isDialogClose) {
                    viewModel.deleteDrug(drugs as Drugs)
                    showToast(getString(R.string.deleted))
                }
                finish()
            }
            setNegativeButton(getString(R.string.no)) { dialog, _ -> dialog.cancel() }
        }
        val alertDialog = alertDialogBuilder.create()
        alertDialog.show()
    }

    private fun checkAndRequestPermissions() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.POST_NOTIFICATIONS), NOTIFICATION_PERMISSION_CODE)
            } else {
                checkAndRequestExactAlarmPermission()
            }
        } else {
            checkAndRequestExactAlarmPermission()
        }
    }

    private fun checkAndRequestExactAlarmPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            val alarmManager = getSystemService(Context.ALARM_SERVICE) as AlarmManager
            if (!alarmManager.canScheduleExactAlarms()) {
                val intent = Intent(Settings.ACTION_REQUEST_SCHEDULE_EXACT_ALARM)
                startActivity(intent)
            }
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == NOTIFICATION_PERMISSION_CODE) {
            if ((grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED)) {
                checkAndRequestExactAlarmPermission()
            } else {
                showPermissionDeniedDialog()
            }
        }
    }

    private fun showPermissionDeniedDialog() {
        AlertDialog.Builder(this)
            .setTitle("Notification Permission Required")
            .setMessage("To receive medication reminders, please enable notifications for this app in your device settings.")
            .setPositiveButton("Settings") { _, _ ->
                val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS).apply {
                    data = Uri.fromParts("package", packageName, null)
                }
                startActivity(intent)
            }
            .setNegativeButton("Cancel", null)
            .show()
    }

    private fun scheduleNotification(drugName: String, hour: Int, minute: Int) {
        val intent = Intent(this, NotificationReceiver::class.java).apply {
            putExtra("drugName", drugName)
            putExtra("notificationId", drugName.hashCode())
        }

        val pendingIntent = PendingIntent.getBroadcast(this, drugName.hashCode(), intent, PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE)
        val alarmManager = getSystemService(Context.ALARM_SERVICE) as AlarmManager

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S && !alarmManager.canScheduleExactAlarms()) {
            Toast.makeText(this, "Exact alarm permission required", Toast.LENGTH_SHORT).show()
            return
        }

        val calendar = Calendar.getInstance().apply {
            set(Calendar.HOUR_OF_DAY, hour)
            set(Calendar.MINUTE, minute)
            set(Calendar.SECOND, 0)
            if (before(Calendar.getInstance())) {
                add(Calendar.DAY_OF_MONTH, 1)
            }
        }

        Log.d("ScheduleNotification", "Scheduling notification for $drugName at ${calendar.time}")

        alarmManager.setExact(AlarmManager.RTC_WAKEUP, calendar.timeInMillis, pendingIntent)
    }

    companion object {
        const val EXTRA_DRUGS = "extra_drugs"
        const val ALERT_DIALOG_CLOSE = 10
        const val ALERT_DIALOG_DELETE = 20
        const val NOTIFICATION_PERMISSION_CODE = 1001
    }
}
