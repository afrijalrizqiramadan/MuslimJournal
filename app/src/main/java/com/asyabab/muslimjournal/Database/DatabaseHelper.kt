package com.asyabab.majelisrasulullah.database

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log

class DatabaseHelper internal constructor(context: Context?) : SQLiteOpenHelper(
    context,
    DatabaseContract.DATABASE_NAME,
    null,
    DatabaseContract.DATABASE_VERSION
) {
    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL(DatabaseContract.TableSurah.CREATE_TABLE)
        db.execSQL(DatabaseContract.TableAyat.CREATE_TABLE)
        db.execSQL(DatabaseContract.TableAsmaulHusna.CREATE_TABLE)
        db.execSQL(DatabaseContract.TableNote.CREATE_TABLE)
        db.execSQL(DatabaseContract.TableJadwalSholat.CREATE_TABLE)
        Log.d("Create", "DB")
    }

    override fun onUpgrade(
        db: SQLiteDatabase,
        oldVersion: Int,
        newVersion: Int
    ) {
    }

    companion object {
        private var helper: DatabaseHelper? = null
        fun initDatabase(context: Context?) {
            if (helper == null) {
                helper =
                    DatabaseHelper(context)
            }
        }

        val database: SQLiteDatabase
            get() = helper!!.writableDatabase
    }
}