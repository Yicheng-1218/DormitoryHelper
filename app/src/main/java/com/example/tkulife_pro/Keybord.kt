package com.example.tkulife_pro

import android.app.Activity
import android.view.View
import android.view.inputmethod.InputMethodManager

class Keyboard {

    companion object{
        fun show(activity: Activity) {
            val imm = activity.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.toggleSoftInput(0, InputMethodManager.HIDE_IMPLICIT_ONLY) // show
        }

        fun hide(activity: Activity,view: View) {
            val imm = activity.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(view.windowToken,0)
        }

        fun toggle(activity: Activity,view: View) {
            val imm = activity.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
            if (imm.isActive) {
                hide(activity,view)
            } else {
                show(activity)
            }
        }
    }


}