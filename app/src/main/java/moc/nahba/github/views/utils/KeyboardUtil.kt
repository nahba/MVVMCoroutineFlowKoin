@file:Suppress("DEPRECATION")

package moc.nahba.github.views.utils

import android.content.Context
import android.view.inputmethod.InputMethodManager
import com.google.android.material.textfield.TextInputEditText

object KeyboardUtil {
    @JvmStatic
    internal fun showKeyboard(editText: TextInputEditText, isForced: Boolean) {
        val inputMethodManager = editText.context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        if (isForced) {
            inputMethodManager.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0)
            editText.requestFocus()
        } else {
            inputMethodManager.showSoftInputFromInputMethod(editText.windowToken, 0)
        }
    }
    
    @JvmStatic
    internal fun hideKeyboard(editText: TextInputEditText) {
        val inputMethodManager = editText.context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.hideSoftInputFromWindow(editText.windowToken, 0)
    }
}