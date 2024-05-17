package com.asyabab.muslimjournal

import android.app.Application
import android.content.Context
import android.content.res.Resources
import com.asyabab.muslimjournal.database.DatabaseHelper
import com.asyabab.muslimjournal.utils.PreferenceApp
import java.io.BufferedReader
import java.io.InputStreamReader

class App : Application() {
    override fun onCreate() {
        super.onCreate()
        appContext = applicationContext
        Companion.resources = resources
        DatabaseHelper.initDatabase(this)
        PreferenceApp.initPreferences(this)
    }

    companion object {
        var appContext: Context? = null
            private set
        private var resources: Resources? = null
        fun getRawResources(res: Int): BufferedReader {
            val streamReader = resources!!.openRawResource(res)
            return BufferedReader(InputStreamReader(streamReader))
        }

    }
}