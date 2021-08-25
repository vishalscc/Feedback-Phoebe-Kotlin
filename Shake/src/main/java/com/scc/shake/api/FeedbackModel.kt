package com.scc.shake.api

import com.google.gson.annotations.SerializedName
import com.google.gson.annotations.Expose

class FeedbackModel {
    @SerializedName("status")
    @Expose
    var status: Boolean? = null

    @SerializedName("code")
    @Expose
    var code: Int? = null

    @SerializedName("message")
    @Expose
    var message: String? = null
}