package com.scc.feedbackphoebekotlin

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.scc.shake.FeedbackPhoebeActivity

class MainActivity : FeedbackPhoebeActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)



    }
}