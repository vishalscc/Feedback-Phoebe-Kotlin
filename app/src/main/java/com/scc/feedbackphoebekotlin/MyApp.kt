package com.scc.feedbackphoebekotlin

import android.app.Application
import com.scc.shake.FeedbackConfig


class MyApp: Application() {

    override fun onCreate() {
        super.onCreate()


        FeedbackConfig(this)
            .setCancelButtonColor(R.color.teal_700)
            .setDialogButtonColor(R.color.blue)
            .setSubmitButtonTextColor(R.color.design_default_color_error)
            .setCancelButtonTextColor(R.color.teal_200)
//            .setFontFromAssets("mark_pro.ttf")
            .setFontFromResource(R.font.mark_pro)
            .setSubmitButtonColor(R.color.teal_200);

    }
}