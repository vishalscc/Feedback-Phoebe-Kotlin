package com.scc.shake


import androidx.appcompat.app.AppCompatActivity
import com.scc.shake.FeedbackPhoebe
import android.os.Bundle

/**
 * Base Activity for all page so user only extend this class and rest will work automatically
 */
open class FeedbackPhoebeActivity : AppCompatActivity() {

    var feedbackPhoebe: FeedbackPhoebe? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        feedbackPhoebe = FeedbackPhoebe()
        feedbackPhoebe!!.launch(this)
    }

    override fun onResume() {
        super.onResume()
        feedbackPhoebe?.register()
    }

    override fun onPause() {
        super.onPause()
        feedbackPhoebe?.unRegister()
    }
}