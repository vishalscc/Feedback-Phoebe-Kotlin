package com.scc.shake.utils

import android.content.Context
import android.content.SharedPreferences
import com.scc.shake.utils.SharedPrefs

/**
 * Utility class for SharedPreferences
 */
object SharedPrefs {
    const val LOCAL_DATA = "local_data"

    private var mPreferences: SharedPreferences? = null

    private fun getInstance(context: Context): SharedPreferences? {
        if (mPreferences == null) {
            mPreferences = context.applicationContext
                .getSharedPreferences("_data", Context.MODE_PRIVATE)
        }
        return mPreferences
    }

    fun contain(context: Context, key: String?): Boolean {
        return getInstance(context)!!.contains(key)
    }

    fun clearPrefs(context: Context) {
        getInstance(context)!!.edit().clear().apply()
    }

    //region Booleans
    fun setBoolean(context: Context, key: String?, value: Boolean) {
        getInstance(context)!!.edit().putBoolean(key, value).apply()
    }

    fun getBoolean(context: Context, key: String?): Boolean {
        return getInstance(context)!!.getBoolean(key, false)
    }

    fun getBoolean(context: Context, key: String?, defaultValue: Boolean): Boolean {
        return getInstance(context)!!.getBoolean(key, defaultValue)
    }

    //endregion
    //region Strings
    fun setString(context: Context, key: String?, value: String?) {
        getInstance(context)!!.edit().putString(key, value).apply()
    }

    fun getString(context: Context, key: String?): String? {
        return getInstance(context)!!.getString(key, "")
    }

    fun getString(context: Context, key: String?, defaultValue: String?): String? {
        return getInstance(context)!!.getString(key, defaultValue)
    }

    //endregion
    //region Integers
    fun setInt(context: Context, key: String?, value: Int) {
        getInstance(context)!!.edit().putInt(key, value).apply()
    }

    fun getInt(context: Context, key: String?): Int {
        return getInstance(context)!!.getInt(key, 0)
    }

    fun getInt(context: Context, key: String?, defaultValue: Int): Int {
        return getInstance(context)!!.getInt(key, defaultValue)
    }

    //endregion
    //region Floats
    fun setFloat(context: Context, key: String?, value: Float) {
        getInstance(context)!!.edit().putFloat(key, value).apply()
    }

    fun getFloat(context: Context, key: String?): Float {
        return getInstance(context)!!.getFloat(key, 0f)
    }

    fun getFloat(context: Context, key: String?, defaultValue: Float): Float {
        return getInstance(context)!!.getFloat(key, defaultValue)
    }

    //endregion
    //region Longs
    fun setLong(context: Context, key: String?, value: Long) {
        getInstance(context)!!.edit().putLong(key, value).apply()
    }

    fun getLong(context: Context, key: String?): Long {
        return getInstance(context)!!.getLong(key, 0)
    }

    fun getLong(context: Context, key: String?, defaultValue: Long): Long {
        return getInstance(context)!!.getLong(key, defaultValue)
    }

    //endregion
    //region Double
    fun setDouble(context: Context, key: String?, value: Double) {
        getInstance(context)!!.edit().putLong(key, java.lang.Double.doubleToRawLongBits(value))
            .apply()
    }

    fun getDouble(context: Context, key: String?): Double {
        return java.lang.Double.longBitsToDouble(
            getInstance(context)!!.getLong(key, java.lang.Double.doubleToRawLongBits(0.0))
        )
    }

    fun getDouble(context: Context, key: String?, defValue: Double?): Double {
        return java.lang.Double.longBitsToDouble(
            getInstance(context)!!
                .getLong(key, java.lang.Double.doubleToRawLongBits(defValue!!))
        )
    }

    //endregion
    fun remove(context: Context, key: String?) {
        getInstance(context)!!.edit().remove(key).apply()
    }

    fun setStringSet(context: Context, key: String?, mSet: Set<String?>?) {
        getInstance(context)!!.edit().putStringSet(key, mSet).apply()
    }

    fun getStringSet(context: Context, key: String?): Set<String>? {
        return getInstance(context)!!.getStringSet(key, null)
    }
}