package com.scc.shake.utils

import android.app.Activity
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentManager
import com.scc.shake.utils.DialogUtil
import com.scc.shake.utils.ProgressDialogFragment

object DialogUtil {
    private const val TAG_FRAGMENT = "Dialog"
    private var mDialogFragment: DialogFragment? = null
    fun showProgressDialog(activity: Activity?, fragmentManager: FragmentManager) {
        if (activity == null || activity.isFinishing) {
            return
        }
        if (mDialogFragment != null && mDialogFragment!!.isVisible) {
            return
        }
        val mPrevious = mDialogFragment
        mDialogFragment = ProgressDialogFragment()
        (mDialogFragment as ProgressDialogFragment).setCancelable(false)
        if (mPrevious != null) {
            fragmentManager.beginTransaction().remove(mPrevious).commitAllowingStateLoss()
        }
        fragmentManager.beginTransaction().add(mDialogFragment as ProgressDialogFragment, TAG_FRAGMENT)
            .commitAllowingStateLoss()
    }

    fun dismissProgressDialog() {
        if (mDialogFragment != null) {
            mDialogFragment!!.dismissAllowingStateLoss()
            mDialogFragment = null
        }
    }
}