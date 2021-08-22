package com.app.weatherdemo.apputils

import android.content.Context
import com.app.weatherdemo.R
import java.text.DecimalFormat



    /*****
     * @param - ºcelcius
     * @return-  convert ºcelcius to ºfahrenheit
     */
    fun celsiusToFahrenheit(celsius: Double): String {
        val f =  DecimalFormat().run {
            applyPattern(".##")
            parse(format(celsius.times(1.8).plus(32))).toDouble()
        }
        return "$f°F"
    }


    fun String.digitsOnly(someValue:String): String{
        val text = "-sefsddfd.ddfffds"
        val regex = Regex("[^0-9]")
        return someValue.replace("[^0-9]".toRegex(), "")
    }



fun convertCelsiusToFahrenheit(celsius: Double): String {
    val f =  DecimalFormat().run {
        applyPattern(".##")
        parse(format(celsius.times(1.8).plus(32))).toDouble()
    }

    return "$f°F"

}
