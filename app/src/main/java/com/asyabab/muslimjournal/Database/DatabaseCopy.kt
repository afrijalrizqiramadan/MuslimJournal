package com.asyabab.majelisrasulullah.database

import android.content.Context
import android.content.Intent
import android.os.AsyncTask
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import com.asyabab.majelisrasulullah.service.CopyDatabase
import java.io.*

/**
 * Class to copying data in background
 */
class DatabaseCopy(private val context: Context) : AsyncTask<String?, Void?, Boolean>() {
    override fun doInBackground(vararg params: String?): Boolean {
        var `in`: InputStream? = null
        var fileSize = 0
        var copyedSize = 0
        try {
            `in` = context.assets.open("muslim_organizer.sqlite.png")
            val out: OutputStream = FileOutputStream(params[0])
            fileSize = `in`.available()
            val buf = ByteArray(1024 * 3)
            var len: Int
            while (`in`.read(buf).also { len = it } > 0) {
                copyedSize += len
                out.write(buf, 0, len)
                val copying = Intent("coping_main_database")
                copying.putExtra("file_size", fileSize)
                copying.putExtra("coping_size", copyedSize)
                LocalBroadcastManager.getInstance(context).sendBroadcast(copying)
            }
            `in`.close()
            out.close()
        } catch (e: FileNotFoundException) {
            e.printStackTrace()
        } catch (e: IOException) {
            e.printStackTrace()
        }
        return fileSize == copyedSize
    }

    override fun onPostExecute(aBoolean: Boolean) {
        super.onPostExecute(aBoolean)
        if (aBoolean) {
            val copying = Intent("coping_main_database")
            copying.putExtra("finish", 1)
            LocalBroadcastManager.getInstance(context).sendBroadcast(copying)
            context.stopService(Intent(context, CopyDatabase::class.java))
        }
    }
}