package com.rafiadly.hackernewsapp.data.source.local.preferences

import android.annotation.SuppressLint
import android.content.Context
import com.rafiadly.hackernewsapp.data.model.Story

class Preferences(private val context: Context) {
    companion object {
        private const val PREFS_NAME = "story_pref"
        private const val ID_STORY = "ID_STORY"
        private const val TITLE_STORY = "TITLE_STORY"

        @SuppressLint("StaticFieldLeak")
        @Volatile
        private var instance: Preferences? = null
        fun getInstance(mContext: Context): Preferences =
            instance ?: synchronized(this) {
                instance ?: Preferences(mContext.applicationContext).apply { instance = this }
            }
    }

    private val preferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)

    fun setStory(story: Story){
        val editor = preferences.edit()
        editor.putInt(ID_STORY, story.id)
        editor.putString(TITLE_STORY, story.title)
        editor.apply()
    }

    fun getStory(): Story {
        return Story(
            id = preferences.getInt(ID_STORY, 0),
            title = preferences.getString(TITLE_STORY, "") ?: "",
        )
    }
}