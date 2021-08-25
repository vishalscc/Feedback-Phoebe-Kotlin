package com.scc.shake

import android.content.Context
import android.content.Intent
import android.hardware.Sensor
import android.hardware.SensorManager
import android.os.Build
import android.os.VibrationEffect
import android.os.Vibrator
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.scc.shake.ShakeEffect.OnShakeListener
import com.scc.shake.api.ApiClient.apiService
import com.scc.shake.api.FeedbackModel
import com.scc.shake.utils.NetworkUtil
import com.scc.shake.utils.SharedPrefs
import com.scc.shake.utils.SharedPrefs.LOCAL_DATA
import com.scc.shake.utils.Utils
import org.json.JSONArray
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*

class FeedbackPhoebe {

    var submitColor = 0
    var dialogButtonColor = 0
    var cancelColor = 0
    private lateinit var vibrator: Vibrator
    private lateinit var mSensorListener: ShakeEffect
    private lateinit var mSensorManager: SensorManager

    fun launch(context: Context) {
        vibrator = context.getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
        mSensorManager = context.getSystemService(Context.SENSOR_SERVICE) as SensorManager
        mSensorListener = ShakeEffect()

        mSensorListener.setOnShakeListener(object : OnShakeListener {
            override fun onShake() {

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    vibrator.vibrate(
                        VibrationEffect.createOneShot(
                            100L,
                            VibrationEffect.DEFAULT_AMPLITUDE
                        )
                    )
                } else {
                    vibrator.vibrate(100L)
                }
                mSensorManager.unregisterListener(mSensorListener)

//                val feedback = Feedback(
//                    Build.MODEL,
//                    "Android " + Build.VERSION.RELEASE,
//                    "Android",
//                    context.javaClass.simpleName,
//                    "",
//                    Build.MANUFACTURER
//                )

                val feedback = Feedback()

                feedback.deviceModel = Build.MODEL
                feedback.deviceOS = "Android " + Build.VERSION.RELEASE
                feedback.deviceType = "Android"
                feedback.pageName = context.javaClass.simpleName
                feedback.text = ""
                feedback.manufacturer = Build.MANUFACTURER

                val intent = Intent(context, FeedBackActivity::class.java)
                intent.putExtra("feedback", feedback)
                intent.putExtra("cancelColor", cancelColor)
                intent.putExtra("submitColor", submitColor)
                intent.putExtra("dialogButtonColor", dialogButtonColor)
                context.startActivity(intent)

            }
        })

        syncData(context)
    }

    private fun syncData(context: Context) {
        if (NetworkUtil.isInternetConnect(context)) {
            val getJson: String? = SharedPrefs.getString(context, LOCAL_DATA)
            if (getJson!!.isNotEmpty()) {
                val gson = Gson()
                val type = object : TypeToken<List<Feedback?>?>() {}.type
                val list = gson.fromJson<List<Feedback>>(getJson, type)
                val jsonArray = JSONArray()
                for (feedback in list) {
                    try {
                        val jsonObject = JSONObject()
                        jsonObject.put("text", feedback.text)
                        jsonObject.put("os", feedback.deviceOS)
                        jsonObject.put("device_type", feedback.deviceType)
                        jsonObject.put("model", feedback.deviceModel)
                        jsonObject.put("page_name", feedback.pageName)
                        jsonArray.put(jsonObject)
                    } catch (e: Exception) {
                        e.printStackTrace()
                    }
                }
                apiService!!.syncFeedback(
                    Utils.getToken(context),
                    Locale.getDefault().language,
                    jsonArray
                )
                    ?.enqueue(object : Callback<FeedbackModel> {
                        override fun onResponse(
                            call: Call<FeedbackModel>,
                            response: Response<FeedbackModel>
                        ) {
                            if (response.isSuccessful && response.body()!!.code == 200) {
                                SharedPrefs.clearPrefs(context)
                            }
                        }

                        override fun onFailure(call: Call<FeedbackModel>, t: Throwable) {
                            t.printStackTrace()
                        }
                    })
            }
        }
    }

    fun register() {
        //Resume
        mSensorManager.registerListener(
            mSensorListener,
            mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER),
            SensorManager.SENSOR_DELAY_UI
        )
    }

    fun unRegister() {
        //Pause
        mSensorManager.unregisterListener(mSensorListener)
    }

}