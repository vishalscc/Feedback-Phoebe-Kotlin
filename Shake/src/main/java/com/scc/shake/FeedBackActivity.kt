package com.scc.shake

import android.content.DialogInterface
import android.content.res.ColorStateList
import android.os.Build
import android.os.Bundle
import android.text.SpannableString
import android.view.View
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.scc.shake.api.ApiClient.apiService
import com.scc.shake.api.FeedbackModel
import com.scc.shake.utils.DialogUtil.dismissProgressDialog
import com.scc.shake.utils.DialogUtil.showProgressDialog
import com.scc.shake.utils.NetworkUtil.isInternetConnect
import com.scc.shake.utils.SharedPrefs.LOCAL_DATA
import com.scc.shake.utils.SharedPrefs.getString
import com.scc.shake.utils.SharedPrefs.setString
import com.scc.shake.utils.Utils.getToken
import com.scc.shake.utils.Utils.typeface
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*

class FeedBackActivity : AppCompatActivity() {

    private val activity: AppCompatActivity = this
    private var feedback: Feedback? = null
    private var feedbackPhoebe: FeedbackPhoebe? = null
    private var title: SpannableString? = null
    private var msg: SpannableString? = null
    private var posBtn: SpannableString? = null
    private var negBtn: SpannableString? = null

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_feed_back)

        feedbackPhoebe = FeedbackPhoebe()

        feedback = intent.getParcelableExtra("feedback")


        val submit_btn = findViewById<TextView>(R.id.submit_btn)
        val cancel_btn = findViewById<TextView>(R.id.cancel_btn)
        val edt_report = findViewById<EditText>(R.id.edt_report)

        if (FeedbackConfig.submitButtonColor != 0) {
            submit_btn.backgroundTintList =
                ColorStateList.valueOf(getColor(FeedbackConfig.submitButtonColor))
        }
        if (FeedbackConfig.cancelButtonColor != 0) {
            cancel_btn.backgroundTintList =
                ColorStateList.valueOf(getColor(FeedbackConfig.cancelButtonColor))
        }

        if (FeedbackConfig.submitButtonTextColor != 0) {
            submit_btn.setTextColor(ColorStateList.valueOf(getColor(FeedbackConfig.submitButtonTextColor)))
        }

        if (FeedbackConfig.cancelButtonTextColor != 0) {
            cancel_btn.setTextColor(ColorStateList.valueOf(getColor(FeedbackConfig.cancelButtonTextColor)))
        }

        title = SpannableString("Feedback")
        msg = SpannableString("Are you sure you want to submit feedback?")
        posBtn = SpannableString("Yes")
        negBtn = SpannableString("No")

        if (FeedbackConfig.fontFromResource != null) {
            edt_report.setTypeface(FeedbackConfig.fontFromResource)
            cancel_btn.setTypeface(FeedbackConfig.fontFromResource)
            submit_btn.setTypeface(FeedbackConfig.fontFromResource)
            title = typeface(FeedbackConfig.fontFromResource, "Feedback")
            msg = typeface(
                FeedbackConfig.fontFromResource,
                "Are you sure you want to submit feedback?"
            )
            posBtn = typeface(FeedbackConfig.fontFromResource, "Yes")
            negBtn = typeface(FeedbackConfig.fontFromResource, "No")
        }

        if (FeedbackConfig.fontFromAssets != null) {
            edt_report.setTypeface(FeedbackConfig.fontFromAssets)
            cancel_btn.setTypeface(FeedbackConfig.fontFromAssets)
            submit_btn.setTypeface(FeedbackConfig.fontFromAssets)
            title = typeface(FeedbackConfig.fontFromAssets, "Feedback")
            msg = typeface(
                FeedbackConfig.fontFromAssets,
                "Are you sure you want to submit feedback?"
            )
            posBtn = typeface(FeedbackConfig.fontFromAssets, "Yes")
            negBtn = typeface(FeedbackConfig.fontFromAssets, "No")
        }

        submit_btn.setOnClickListener { view: View? ->
            if (edt_report.text.toString().isEmpty()) {
                Toast.makeText(this, "Enter Feedback", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            val alertDialog = AlertDialog.Builder(this)
                .setTitle(title)
                .setMessage(msg)
                .setPositiveButton(
                    posBtn
                ) { dialogInterface: DialogInterface?, i: Int ->
                    feedback!!.text = edt_report.text.toString()
                    send(feedback!!)
                }
                .setNegativeButton(
                    negBtn
                ) { dialogInterface: DialogInterface, i: Int -> dialogInterface.dismiss() }
                .create()

            alertDialog.setOnShowListener { dialogInterface: DialogInterface? ->
                if (FeedbackConfig.dialogButtonColor != 0) {
                    val color: Int = FeedbackConfig.dialogButtonColor
                    alertDialog.getButton(AlertDialog.BUTTON_POSITIVE)
                        .setTextColor(resources.getColor(color))
                    alertDialog.getButton(AlertDialog.BUTTON_NEGATIVE)
                        .setTextColor(resources.getColor(color))
                }
            }
            alertDialog.show()
        }

        cancel_btn.setOnClickListener { view: View? -> onBackPressed() }

    }

    private fun send(feedback: Feedback) {
        if (getToken(this)!!.isEmpty()) {
            Toast.makeText(this, "Api Token can't be empty", Toast.LENGTH_SHORT).show()
            return
        }
        if (isInternetConnect(this)) {
            saveRemotely(feedback)
        } else {
            saveLocally(feedback)
        }
    }

    private fun saveLocally(feedback: Feedback) {
        val gson = Gson()
        val getJson = getString(activity, LOCAL_DATA)
        val list: MutableList<Feedback>
        list = if (getJson!!.isEmpty()) {
            ArrayList()
        } else {
            val type = object : TypeToken<List<Feedback?>?>() {}.type
            gson.fromJson(getJson, type)
        }
        list.add(feedback)
        val setjson = gson.toJson(list)
        setString(activity, LOCAL_DATA, setjson)
        complete()
    }

    private fun complete() {
        Toast.makeText(activity, "Feedback send successfully", Toast.LENGTH_SHORT).show()
        finish()
    }

    private fun saveRemotely(feedback: Feedback) {
        showProgressDialog(activity, supportFragmentManager)
        apiService
            ?.sentFeedback(
                getToken(this),
                Locale.getDefault().language,
                feedback.deviceType,
                feedback.deviceModel,
                feedback.pageName,
                feedback.text,
                feedback.deviceOS
            )
            ?.enqueue(object : Callback<FeedbackModel> {
                override fun onResponse(
                    call: Call<FeedbackModel>,
                    response: Response<FeedbackModel>
                ) {
                    dismissProgressDialog()
                    if (response.isSuccessful && response.body()!!.code == 200) {
                        complete()
                    } else if (response.body()!!.code == 401) {
                        Toast.makeText(activity, "Something went wrong", Toast.LENGTH_SHORT).show()
                    }
                }

                override fun onFailure(call: Call<FeedbackModel>, t: Throwable) {
                    dismissProgressDialog()
                    t.printStackTrace()
                    Toast.makeText(activity, "Something went wrong", Toast.LENGTH_SHORT).show()
                }
            })
    }


}