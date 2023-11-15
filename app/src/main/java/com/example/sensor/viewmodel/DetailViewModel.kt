package com.example.sensor.viewmodel

import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.example.repository.entity.SessionEntity
import com.example.repository.repositiry.SessionRepository
import com.example.sensor.utils.ARG_SENSOR_TYPE
import com.example.sensor.utils.Globals
import dagger.hilt.android.lifecycle.HiltViewModel
import java.util.concurrent.ConcurrentLinkedQueue
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    val sensorManager: SensorManager,
    private val sessionRepository: SessionRepository,
) : ViewModel() {

    val sensorType = savedStateHandle.get<Int>(ARG_SENSOR_TYPE)!!

    val sensor: Sensor = sensorManager.getDefaultSensor(sensorType)!!

    val itemSensor = Globals.sensors.find {
        it.sensorType == sensorType
    }!!
    val xValues = ConcurrentLinkedQueue<Float>()

    val yValues = ConcurrentLinkedQueue<Float>()

    val zValues = ConcurrentLinkedQueue<Float>()

    val sensorEventListener: SensorEventListener = when (itemSensor.axes) {
        1 -> object : SensorEventListener {
            override fun onSensorChanged(event: SensorEvent?) {
                if (event == null) return
                if (xValues.size >= itemSensor.dataPoints) {
                    runCatching { xValues.remove() }
                }
                xValues.add(event.values[0])
            }

            override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {
                /* do nothing */
            }
        }

        else -> object : SensorEventListener {
            override fun onSensorChanged(event: SensorEvent?) {
                if (event == null) return
                if (xValues.size >= itemSensor.dataPoints) {
                    runCatching {
                        xValues.remove()
                        yValues.remove()
                        zValues.remove()
                    }
                }
                xValues.add(event.values[0])
                yValues.add(event.values[1])
                zValues.add(event.values[2])
            }

            override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {
                /* do nothing */
            }
        }
    }


    suspend fun insertSensor(): Long {
        return sessionRepository.insert(
            SessionEntity(
                id = 0,
                sensorType = sensorType,
                timestamp = System.currentTimeMillis()
            )
        )
    }
}