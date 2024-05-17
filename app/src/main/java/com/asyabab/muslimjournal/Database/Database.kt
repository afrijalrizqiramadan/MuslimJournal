package com.asyabab.majelisrasulullah.database

import android.database.sqlite.SQLiteDatabase
import com.asyabab.majelisrasulullah.model.*
import java.util.*

/**
 * Class to handel all database transactions
 */
class Database {
    private val MAIN_DATABSE =
        "/data/data/com.asyabab.majelisrasulullah/" + "muslim_organizer.sqlite.png"

    /**
     * Function to open connection with Database
     *
     * @return Database object
     */
    fun openDB(): SQLiteDatabase {
        return SQLiteDatabase.openDatabase(MAIN_DATABSE, null, 0)
    }

    /**
     * Function to close database connection
     *
     * @param db Database object to close
     */
    fun closeDB(db: SQLiteDatabase) {
        db.close()
    }

    /**
     * Function to get location information
     *
     * @param latitude  Latitude degree
     * @param longitude Longitude degree
     * @return Object contains all location info
     */
    fun getLocationInfo(latitude: Float, longitude: Float): LocationInfo {
        val db = openDB()
        val sql = "select  b.En_Name , b.Ar_Name , b.iso3 , a.city ," +
                " b.Continent_Code ,  b.number  , b.mazhab , b.way , b.dls , a.time_zone ," +
                " (latitude - " + latitude + ")*(latitude - " + latitude + ")+(longitude - " + longitude + ")" +
                "*(longitude - " + longitude + ") as ed , a.Ar_Name  from cityd a , countries b where" +
                " b.code = a.country order by ed asc limit 1;"
        val cursor = db.rawQuery(sql, null)
        cursor.moveToFirst()
        val locationInfo = LocationInfo(
            latitude, longitude,
            cursor.getString(0), cursor.getString(1), cursor.getString(2),
            cursor.getString(3), cursor.getString(4), cursor.getInt(5),
            cursor.getInt(6), cursor.getInt(7), cursor.getInt(8), cursor.getInt(9).toFloat(),
            if (cursor.getString(11) == null) cursor.getString(3) else cursor.getString(11)
        )
        cursor.close()
        closeDB(db)
        return locationInfo
    }

    /**
     * Function to get all zekr
     *
     * @return List of zeker and some information
     */
    val allAzkarTypes: List<ZekerType>
        get() {
            val zekerTypeList: MutableList<ZekerType> = ArrayList()
            val db = openDB()
            val sql =
                "select a.ZekrTypeID , a.ZekrTypeName , count( b.TypeID ) from azkartypes a ," +
                        "  azkar b where a.ZekrTypeID = b.TypeID group by a.ZekrTypeName order by  a.ZekrTypeID;"
            val cursor = db.rawQuery(sql, null)
            cursor.moveToFirst()
            while (!cursor.isAfterLast) {
                zekerTypeList.add(
                    ZekerType(
                        cursor.getInt(0),
                        cursor.getString(1),
                        cursor.getInt(2)
                    )
                )
                cursor.moveToNext()
            }
            cursor.close()
            closeDB(db)
            return zekerTypeList
        }

    /**
     * Function get azkar of certain type
     *
     * @param type zeker type
     * @return List of azkar
     */
    fun getAllAzkarOfType(type: Int): List<Zeker> {
        val zekerList: MutableList<Zeker> = ArrayList()
        val db = openDB()
        val sql = "select ZekrContent , ZekrNoOfRep , Fadl from azkar where TypeID = $type  ;"
        val cursor = db.rawQuery(sql, null)
        cursor.moveToFirst()
        while (!cursor.isAfterLast) {
            zekerList.add(
                Zeker(
                    cursor.getString(0),
                    cursor.getString(2), cursor.getInt(1)
                )
            )
            cursor.moveToNext()
        }
        cursor.close()
        closeDB(db)
        return zekerList
    }

    /**
     * Function to get all countries
     *
     * @return List of all countries
     */
    val allCountries: List<Country>
        get() {
            val countries: MutableList<Country> = ArrayList()
            val db = openDB()
            val sql = "select Code , EN_Name , AR_Name  from Countries ;"
            val cursor = db.rawQuery(sql, null)
            cursor.moveToFirst()
            while (!cursor.isAfterLast) {
                countries.add(
                    Country(
                        cursor.getString(1),
                        cursor.getString(2),
                        cursor.getString(0)
                    )
                )
                cursor.moveToNext()
            }
            cursor.close()
            closeDB(db)
            return countries
        }

    /**
     * Function to get all cities of country
     *
     * @param code Country code
     * @return List of all cities
     */
    fun getAllCities(code: String): List<City> {
        val cities: MutableList<City> = ArrayList()
        val db = openDB()
        val sql = "select * from cityd where country = '$code' ;"
        val cursor = db.rawQuery(sql, null)
        cursor.moveToFirst()
        while (!cursor.isAfterLast) {
            cities.add(
                City(
                    cursor.getString(1),
                    cursor.getString(6),
                    cursor.getFloat(2),
                    cursor.getFloat(3)
                )
            )
            cursor.moveToNext()
        }
        cursor.close()
        closeDB(db)
        return cities
    }

    /**
     * Function to check if country is islamic or not
     *
     * @return Flag islamic or not
     */
    fun isIslamicCountry(code: String): Boolean {
        return try {
            val db = openDB()
            val sql = "select islamic from countries where Code = '$code'"
            val cursor = db.rawQuery(sql, null)
            cursor.moveToFirst()
            var type = 0
            while (!cursor.isAfterLast) {
                type = cursor.getInt(0)
                cursor.moveToNext()
            }
            cursor.close()
            closeDB(db)
            type == 1
        } catch (e: Exception) {
            e.printStackTrace()
            false
        }
    }
}