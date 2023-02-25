package com.rafiadly.hackernewsapp.data.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Story(
    val id:Int,
    val title:String,
    val comments:Int = 0,
    val score:Int = 0,
    val date:String = "",
    val author:String = "",
    val desc:String = "",
    val commentIds:List<Int> = emptyList()
): Parcelable