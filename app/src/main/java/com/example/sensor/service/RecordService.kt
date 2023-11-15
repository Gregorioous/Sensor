package com.example.sensor.service

import android.annotation.SuppressLint
import android.app.PendingIntent
import android.app.Service
import android.content.Intent
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.os.Binder
import android.os.IBinder
import androidx.core.app.NotificationCompat
import com.example.repository.entity.RecordEntity
import com.example.sensor.R
import com.example.sensor.model.UISensor
import com.example.sensor.utils.Globals
import com.example.sensor.utils.PermissionUtil
import com.example.sensor.utils.RECORD_CHANNEL_ID
import com.example.sensor.utils.RECORD_NOTIFICATION_ID
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import java.util.concurrent.LinkedTransferQueue
import javax.inject.Inject

@AndroidEntryPoint
class RecordService : Service() {
    @Inject
    lateinit var sensorManager: SensorManager

    private val binder = LocalBinder()

    private val job = SupervisorJob()

    private var model: UISensor? = null
    private var recordId: Long = -1

    private var isRecording = false


    private val events = LinkedTransferQueue<RecordEntity>()


    private val transfers = mutableListOf<RecordEntity>()


    private val _state: MutableStateFlow<Boolean> = MutableStateFlow(false)


    val state = _state.asStateFlow()

    private val builder: NotificationCompat.Builder
        get() = NotificationCompat.Builder(applicationContext, RECORD_CHANNEL_ID).apply {
            setContentTitle(applicationContext.getString(model!!.name))
            setSmallIcon(R.drawable.ic_app)
            setOngoing(true)
            addAction(
                R.drawable.ic_close_24,
                getString(android.R.string.cancel),
                PendingIntent.getService(
                    /* context = */ applicationContext,
                    /* requestCode = */ 0,
                    /* intent = */ Intent(applicationContext, RecordService::class.java).apply {
                        putExtra(ARG_ENABLED, false)
                    },
                    /* flags = */ PendingIntent.FLAG_UPDATE_CURRENT
                    /* isMutable = */
                )
            )
            priority = NotificationCompat.PRIORITY_DEFAULT
        }


    private var sensorEventListener: SensorEventListener = object : SensorEventListener {
        override fun onSensorChanged(event: SensorEvent?) {
            if (event == null) return
            events.add(
                RecordEntity(
                    id = 0,
                    recordId = recordId,
                    timestamp = event.timestamp,
                    x = event.values[0],
                    y = event.values.getOrElse(1) { 0.0f },
                    z = event.values.getOrElse(2) { 0.0f }
                )
            )
        }

        override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {

        }
    }

    override fun onBind(intent: Intent?): IBinder {
        return binder
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        val enableLog: Boolean = intent?.getBooleanExtra(ARG_ENABLED, false) ?: false

        if (enableLog) {
            _state.tryEmit(true)
            startRecordingService(intent!!)
        } else {
            _state.tryEmit(false)
            isRecording = false
            stopRecordingService()
        }

        return super.onStartCommand(intent, flags, startId)
    }

    override fun onDestroy() {
        _state.tryEmit(false)
        super.onDestroy()
        try {
            sensorManager.unregisterListener(sensorEventListener)
            stopForeground(STOP_FOREGROUND_REMOVE)
            stopSelf()
        } catch (e: Exception) {
            //  FirebaseCrashlytics.getInstance().recordException(e)
        }
    }

    override fun onUnbind(intent: Intent?): Boolean {
        return super.onUnbind(intent)
    }


    private fun startRecordingService(intent: Intent) {
        recordId = intent.getLongExtra(ARG_RECORDING_ID, -1)
        val sensorType = intent.getIntExtra(ARG_SENSOR_TYPE, 1)
        val sensor = sensorManager.getDefaultSensor(sensorType)

        if (model != null) {
            isRecording = false
            sensorManager.unregisterListener(sensorEventListener)
            job.cancel()
        }

        model = Globals.sensors.find { it.sensorType == sensorType }!!

        sensorManager.registerListener(
            sensorEventListener,
            sensor,
            SensorManager.SENSOR_DELAY_FASTEST
        )

        startForeground(
            RECORD_NOTIFICATION_ID,
            builder.setContentText(getString(model!!.name)).build()
        )

    }

    private fun stopRecordingService() {
        sensorManager.unregisterListener(sensorEventListener)
        stopForeground(STOP_FOREGROUND_REMOVE)
        stopSelf()
    }


    @SuppressLint("MissingPermission")
    private fun notify(text: String) {
        if (PermissionUtil.isNotificationPermissionGranted(applicationContext)) {
            val notification = builder.setContentText(text).build()
            startForeground(RECORD_NOTIFICATION_ID, notification)
        }
    }

    inner class LocalBinder : Binder() {
        fun getService(): RecordService = this@RecordService
    }

    companion object {
        const val ARG_ENABLED = "ARG_ENABLED"
        const val ARG_SENSOR_TYPE = "ARG_SENSOR_TYPE"
        const val ARG_RECORDING_ID = "ARG_RECORDING_ID"
    }
}