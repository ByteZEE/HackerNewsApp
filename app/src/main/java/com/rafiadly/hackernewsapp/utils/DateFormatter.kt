package com.rafiadly.hackernewsapp.utils

import java.text.SimpleDateFormat

object DateFormatter {
    fun timestampToDate(ts:Long):String{
        try {
            val sdf = SimpleDateFormat("dd/MM/yyyy")
            return sdf.format(ts*1000L)
        } catch (e: Exception) {
            return e.toString()
        }
    }
}