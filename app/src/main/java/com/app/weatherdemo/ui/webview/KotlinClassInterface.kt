package com.app.weatherdemo.ui.webview

import android.app.AlertDialog
import android.content.Context
import android.webkit.JavascriptInterface


internal class KotlinClassInterface(var mContext: Context) {

    @JavascriptInterface
    fun openAndroidDialog() {
        val myDialog = AlertDialog.Builder(mContext)
        myDialog.setTitle("Thanks for sharing your feedback")
        myDialog.setMessage("Your feedback have been registered!!!")
        myDialog.setPositiveButton("Cool!", null)
        myDialog.show()
    }
}