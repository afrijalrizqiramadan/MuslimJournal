package com.asyabab.majelisrasulullah.database

import android.provider.BaseColumns

object DatabaseContract {
    const val DATABASE_NAME = "majmu.db"
    const val DATABASE_VERSION = 2
    const val LOAD_TERJEMEMAHAN_INDONESIA = "TerjemahanIndonesia"
    const val LOAD_TERJEMEMAHAN_ENGLISH = "TerjemahanEnglish"

    object TableSurah : BaseColumns {
        const val TABLE_SURAH = "table_surah"
        const val SURAH = "Surah"
        const val AYAT = "Ayat"
        const val TERJEMAHAN_INDONESIA =
            LOAD_TERJEMEMAHAN_INDONESIA
        const val TERJEMAHAN_ENGLISH =
            LOAD_TERJEMEMAHAN_ENGLISH
        const val JUMLAH_AYAT = "Jumlah_Ayat"
        const val JENIS = "Jenis"
        const val CREATE_TABLE =
            ("CREATE TABLE IF NOT EXISTS " + TABLE_SURAH + "("
                    + SURAH + " TEXT,"
                    + AYAT + " TEXT,"
                    + TERJEMAHAN_INDONESIA + " TEXT,"
                    + TERJEMAHAN_ENGLISH + " TEXT,"
                    + JUMLAH_AYAT + " TEXT,"
                    + JENIS + " TEXT)")
        const val QUERY_STATEMENT =
            "INSERT INTO $TABLE_SURAH($SURAH,$AYAT,$TERJEMAHAN_INDONESIA,$TERJEMAHAN_ENGLISH,$JUMLAH_AYAT,$JENIS) VALUES (?,?,?,?,?,?)"
    }

    object TableAyat : BaseColumns {
        const val TABLE_AYAT = "table_ayat"
        const val SURAH = "Surah"
        const val AYAT = "Ayat"
        const val ARAB = "Arab"
        const val TERJEMAHAN_INDONESIA =
            LOAD_TERJEMEMAHAN_INDONESIA
        const val TERJEMAHAN_ENGLISH =
            LOAD_TERJEMEMAHAN_ENGLISH
        const val CREATE_TABLE =
            ("CREATE TABLE IF NOT EXISTS " + TABLE_AYAT + "("
                    + SURAH + " TEXT,"
                    + AYAT + " TEXT,"
                    + ARAB + " TEXT,"
                    + TERJEMAHAN_INDONESIA + " TEXT,"
                    + TERJEMAHAN_ENGLISH + " TEXT)")
        const val QUERY_STATEMENT =
            "INSERT INTO $TABLE_AYAT($SURAH,$AYAT,$ARAB,$TERJEMAHAN_INDONESIA,$TERJEMAHAN_ENGLISH) VALUES (?,?,?,?,?)"
    }

    object TableAsmaulHusna : BaseColumns {
        const val TABLE_ASMA = "table_asmaulhusna"
        const val NO = "no"
        const val LATIN = "latin"
        const val ARAB = "arab"
        const val INDONESIA = "indonesia"
        const val INGGRIS = "inggris"
        const val CREATE_TABLE =
            ("CREATE TABLE IF NOT EXISTS " + TABLE_ASMA + "("
                    + NO + " TEXT,"
                    + LATIN + " TEXT,"
                    + ARAB + " TEXT,"
                    + INDONESIA + " TEXT,"
                    + INGGRIS + " TEXT)")
        const val QUERY_STATEMENT =
            "INSERT INTO $TABLE_ASMA($NO,$LATIN,$ARAB,$INDONESIA,$INGGRIS) VALUES (?,?,?,?,?)"
    }

    object TableNote : BaseColumns {
        const val TABLE_NOTE = "table_note"
        const val ID = "no"
        const val NAMA = "nama"
        const val STATUS = "status"
        const val STATUS2 = "status2"
        const val CREATE_TABLE =
            ("CREATE TABLE IF NOT EXISTS " + TABLE_NOTE + "("
                    + ID + " TEXT,"
                    + NAMA + " TEXT,"
                    + STATUS + " TEXT,"
                    + STATUS2 + " TEXT)")
        const val QUERY_UPDATE =
            "UPDATE $TABLE_NOTE SET $STATUS=(?) WHERE $NAMA= (?)"
        const val QUERY_UPDATE2 =
            "UPDATE $TABLE_NOTE SET $STATUS2=(?) WHERE $NAMA= (?)"
        const val QUERY_STATEMENT =
            "INSERT INTO $TABLE_NOTE($ID,$NAMA,$STATUS) VALUES (?,?,?)"
    }

    object TableJadwalSholat : BaseColumns {
        const val TABLE_SHOLAT = "table_jadwalsholat"
        const val ID = "no"
        const val TANGGAL = "tanggal"
        const val SUBUH = "subuh"
        const val DUHUR = "duhur"
        const val ASHAR = "ashar"
        const val MAGHRIB = "maghrib"
        const val ISYA = "isya"
        const val CREATE_TABLE =
            ("CREATE TABLE IF NOT EXISTS " + TABLE_SHOLAT + "("
                    + ID + " TEXT,"
                    + TANGGAL + " TEXT,"
                    + SUBUH + " TEXT,"
                    + DUHUR + " TEXT,"
                    + ASHAR + " TEXT,"
                    + MAGHRIB + " TEXT,"
                    + ISYA + " TEXT)")
        const val DELETE =
            "DELETE FROM $TABLE_SHOLAT"
        const val QUERY_STATEMENT =
            "INSERT INTO $TABLE_SHOLAT($ID,$TANGGAL,$SUBUH,$DUHUR,$ASHAR,$MAGHRIB,$ISYA) VALUES (?,?,?,?,?,?,?)"
    }
}