package com.example.sensor.utils

import android.hardware.Sensor
import com.example.sensor.R
import com.example.sensor.model.UISensor

const val ARG_SENSOR_TYPE = "ARG_SENSOR_TYPE"
const val keyPreferenceTheme = "preferenceTheme"
const val keyTheme = "theme"

const val RECORD_CHANNEL_ID = "1"
const val RECORD_NOTIFICATION_ID = 101

object Globals {
    var isNightMode: Boolean = false


    val sensors = listOf(
        UISensor(
            sensorType = Sensor.TYPE_ACCELEROMETER,
            axes = 3,
            dataPoints = 100,
            name = R.string.sensorAccelerometer,
            unit = R.string.unitAcceleration,
            info = R.string.infoAccelerometer,
            color = R.color.red,
            icon = R.drawable.ic_acceleration
        ),
        UISensor(
            sensorType = Sensor.TYPE_MAGNETIC_FIELD,
            axes = 3,
            dataPoints = 100,
            name = R.string.sensorMagneticField,
            unit = R.string.unitMagneticField,
            info = R.string.infoMagneticField,
            color = R.color.pink,
            icon = R.drawable.ic_magnet
        ),
        UISensor(
            sensorType = Sensor.TYPE_GRAVITY,
            axes = 3,
            dataPoints = 100,
            name = R.string.sensorGravity,
            unit = R.string.unitAcceleration,
            info = R.string.infoGravity,
            color = R.color.purple,
            icon = R.drawable.ic_gravity
        ),
        UISensor(
            sensorType = Sensor.TYPE_GYROSCOPE,
            axes = 3,
            dataPoints = 200,
            name = R.string.sensorGyroscope,
            unit = R.string.unitAngularVelocity,
            info = R.string.infoGyroscope,
            color = R.color.deep_blue,
            icon = R.drawable.ic_gyroscope
        ),
        UISensor(
            sensorType = Sensor.TYPE_LINEAR_ACCELERATION,
            axes = 3,
            dataPoints = 100,
            name = R.string.sensorLinearAcceleration,
            unit = R.string.unitAcceleration,
            info = R.string.infoLinearAcceleration,
            color = R.color.indigo,
            R.drawable.ic_linearacceleration
        ),
        UISensor(
            sensorType = Sensor.TYPE_AMBIENT_TEMPERATURE,
            axes = 1,
            dataPoints = 50,
            name = R.string.sensorAmbientTemperature,
            unit = R.string.unitTemperature,
            info = R.string.infoAmbientTemperature,
            color = R.color.blue,
            icon = R.drawable.ic_temperature
        ),
        UISensor(
            sensorType = Sensor.TYPE_LIGHT,
            axes = 1,
            dataPoints = 50,
            name = R.string.sensorLight,
            unit = R.string.unitIlluminance,
            info = R.string.infoLight,
            color = R.color.light_blue,
            icon = R.drawable.ic_light
        ),
        UISensor(
            sensorType = Sensor.TYPE_PRESSURE,
            axes = 1,
            dataPoints = 50,
            name = R.string.sensorPressure,
            unit = R.string.unitPressure,
            info = R.string.infoPressure,
            color = R.color.cyan,
            icon = R.drawable.ic_pressure
        ),
        UISensor(
            sensorType = Sensor.TYPE_RELATIVE_HUMIDITY,
            axes = 1,
            dataPoints = 50,
            name = R.string.sensorRelativeHumidity,
            unit = R.string.unitPercent,
            info = R.string.infoRelativeHumidity,
            color = R.color.teal,
            icon = R.drawable.ic_humidity
        ),
        UISensor(
            sensorType = Sensor.TYPE_GEOMAGNETIC_ROTATION_VECTOR,
            axes = 3,
            dataPoints = 100,
            name = R.string.sensorGeomagneticRotationVector,
            unit = R.string.unitNone,
            info = R.string.infoGeomagneticRotationVector,
            color = R.color.green,
            icon = R.drawable.ic_rotate
        ),
        UISensor(
            sensorType = Sensor.TYPE_PROXIMITY,
            axes = 1,
            dataPoints = 25,
            name = R.string.sensorProximity,
            unit = R.string.unitProximity,
            info = R.string.infoProximity,
            color = R.color.light_green,
            icon = R.drawable.ic_proximity
        ),
    )
}