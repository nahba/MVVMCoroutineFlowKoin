@file:Suppress("SameParameterValue")

package moc.nahba.github.views.utils

import android.app.Activity
import android.view.View
import com.google.android.material.snackbar.Snackbar
import moc.nahba.github.views.contracts.SnackCallback

object SnackbarUtil {
    @JvmStatic
    internal fun showSnackbar(activity: Activity?, message: String?) {
        if (null != activity && null != message) {
            showSnackbar(activity.findViewById(android.R.id.content) as View, message, Snackbar.LENGTH_LONG)
        }
    }
    
    @JvmStatic
    private fun showSnackbar(view: View, message: String, length: Int) {
        Snackbar.make(view, message, length).show()
    }
    
    @JvmStatic
    internal fun showSnackbarWithSingleAction(activity: Activity?, message: String?, length: Int, action: String, actionTextColor: Int, callback: SnackCallback?) {
        if (null != activity && null != message) {
            showSnackbarWithSingleAction(activity.findViewById(android.R.id.content) as View, message, length, action, actionTextColor, callback)
        }
    }
    
    @JvmStatic
    private fun showSnackbarWithSingleAction(view: View, message: String, length: Int, action: String, actionTextColor: Int, callback: SnackCallback?) {
        val snack = Snackbar.make(view, message, length)
        snack.setAction(action) {
            snack.dismiss()
            callback?.onDismissClicked()
        }
        snack.setActionTextColor(actionTextColor)
        snack.show()
    }
}