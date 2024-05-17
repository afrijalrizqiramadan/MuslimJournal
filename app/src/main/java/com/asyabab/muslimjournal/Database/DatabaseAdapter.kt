package com.asyabab.majelisrasulullah.database

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteQueryBuilder
import android.util.Log
import com.asyabab.majelisrasulullah.model.Asma
import com.asyabab.majelisrasulullah.model.Ayat
import com.asyabab.majelisrasulullah.model.Surah
import com.readystatesoftware.sqliteasset.SQLiteAssetHelper

class DatabaseAdapter(context: Context?) : SQLiteAssetHelper(context, DB_NAME, null, DB_VER) {

    fun getSurah(): ArrayList<Surah> {
        val database: SQLiteDatabase = DatabaseHelper.database
        val qb = SQLiteQueryBuilder()
        val sqlTable = DatabaseContract.TableSurah.TABLE_SURAH
        qb.tables = sqlTable
        val c = qb.query(database, null, null, null, null, null, null)
        val result: ArrayList<Surah> = ArrayList()
        if (c.moveToFirst()) {
            do {
                val surah = c.getString(c.getColumnIndexOrThrow(DatabaseContract.TableSurah.SURAH))
                val ayat= c.getString(c.getColumnIndexOrThrow(DatabaseContract.TableSurah.AYAT))
                val terjemah= c.getString(c.getColumnIndexOrThrow(DatabaseContract.TableSurah.TERJEMAHAN_INDONESIA))
                val jumlahayat= c.getString(c.getColumnIndexOrThrow(DatabaseContract.TableSurah.JUMLAH_AYAT))
                val jenis= c.getString(c.getColumnIndexOrThrow(DatabaseContract.TableSurah.JENIS))
                Log.d("hasilsurah", c.getString(c.getColumnIndex(DatabaseContract.TableSurah.SURAH)))
                result.add(
                    Surah(surah,ayat,terjemah,jumlahayat,jenis)
                )
            } while (c.moveToNext())
        }
        return result
    }

    fun getAyat(surah:String): ArrayList<Ayat> {
        val database: SQLiteDatabase = DatabaseHelper.database
        val qb = SQLiteQueryBuilder()
        val sqlTable = DatabaseContract.TableAyat.TABLE_AYAT
        qb.tables = sqlTable

        val c = qb.query(database, null, DatabaseContract.TableAyat.SURAH + " LIKE '" + surah + "'", null, null, null, null)
        val result: ArrayList<Ayat> = ArrayList()
        if (c.moveToFirst()) {
            do {
                val surah = c.getString(c.getColumnIndexOrThrow(DatabaseContract.TableAyat.SURAH))
                val ayat= c.getString(c.getColumnIndexOrThrow(DatabaseContract.TableAyat.AYAT))
                val terjemah= c.getString(c.getColumnIndexOrThrow(DatabaseContract.TableAyat.ARAB))
                val terjemahan = c.getString(c.getColumnIndexOrThrow(DatabaseContract.TableAyat.TERJEMAHAN_INDONESIA))
                Log.d("hasilsurah", c.getString(c.getColumnIndex(DatabaseContract.TableSurah.SURAH)))
                result.add(
                    Ayat(surah,ayat,terjemah,terjemahan)
                )
            } while (c.moveToNext())
        }
        return result
    }
    fun getAsmaulHusna(): ArrayList<Asma> {
        val database: SQLiteDatabase = DatabaseHelper.database
        val qb = SQLiteQueryBuilder()
        val sqlTable = DatabaseContract.TableAsmaulHusna.TABLE_ASMA
        qb.tables = sqlTable

        val c = qb.query(database, null, null, null, null, null, null)
        val result: ArrayList<Asma> = ArrayList()
        if (c.moveToFirst()) {
            do {
                val arab = c.getString(c.getColumnIndexOrThrow(DatabaseContract.TableAsmaulHusna.ARAB))
                val indonesia= c.getString(c.getColumnIndexOrThrow(DatabaseContract.TableAsmaulHusna.INDONESIA))
                val latin= c.getString(c.getColumnIndexOrThrow(DatabaseContract.TableAsmaulHusna.LATIN))
                val no = c.getString(c.getColumnIndexOrThrow(DatabaseContract.TableAsmaulHusna.NO))
                val inggris = c.getString(c.getColumnIndexOrThrow(DatabaseContract.TableAsmaulHusna.INGGRIS))
                result.add(
                    Asma(no,latin,arab,indonesia,inggris)
                )
            } while (c.moveToNext())
        }
        return result
    }

   companion object {
        private const val DB_NAME = "majmu"
        private const val DB_VER = 1
    }
}