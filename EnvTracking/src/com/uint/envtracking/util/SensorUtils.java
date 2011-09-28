package com.uint.envtracking.util;

import com.uint.envtracking.R;

import android.hardware.Sensor;

public class SensorUtils {

	public static int typeToName(int type) {
		int res = R.string.dummy_text;

		switch (type) {
		case Sensor.TYPE_ACCELEROMETER:
			res = R.string.sensor_accelerometer;
			break;
		case Sensor.TYPE_GRAVITY:
			res = R.string.sensor_gravity;
			break;
		case Sensor.TYPE_GYROSCOPE:
			res = R.string.sensor_gyroscope;
			break;
		case Sensor.TYPE_LIGHT:
			res = R.string.sensor_light;
			break;
		case Sensor.TYPE_LINEAR_ACCELERATION:
			res = R.string.sensor_linear_acceleration;
			break;
		case Sensor.TYPE_MAGNETIC_FIELD:
			res = R.string.sensor_magnetic_field;
			break;
		case Sensor.TYPE_ORIENTATION:
			res = R.string.sensor_orientation;
			break;
		case Sensor.TYPE_PRESSURE:
			res = R.string.sensor_pressure;
			break;
		case Sensor.TYPE_PROXIMITY:
			res = R.string.sensor_proximity;	
			break;
		case Sensor.TYPE_ROTATION_VECTOR:
			res = R.string.sensor_rotation_vector;
			break;
		case Sensor.TYPE_TEMPERATURE:
			res = R.string.sensor_temperature;
			break;

		default:
			break;
		}

		return res;
	}

}
