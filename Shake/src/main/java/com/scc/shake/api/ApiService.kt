package com.scc.shake.api

import org.json.JSONArray
import retrofit2.Call
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface ApiService {

    @FormUrlEncoded
    @POST("sentFeedback")
    fun sentFeedback(
        @Field("token") token: String?,
        @Field("lang") lang: String?,
        @Field("device_type") device_type: String?,
        @Field("model") model: String?,
        @Field("page_name") page_name: String?,
        @Field("text") text: String?,
        @Field("os") os: String?
    ): Call<FeedbackModel>


    @FormUrlEncoded
    @POST("syncFeedback")
    fun syncFeedback(
        @Field("token") token: String?,
        @Field("lang") lang: String?,
        @Field("aryData") device_type: JSONArray?
    ): Call<FeedbackModel>
}