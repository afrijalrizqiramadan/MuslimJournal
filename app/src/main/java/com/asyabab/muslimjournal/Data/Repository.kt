package com.asyabab.muslimjournal.Data


import android.content.Context
import com.asyabab.majelisrasulullah.data.models.artikel.ArtikelResponse
import com.asyabab.majelisrasulullah.data.models.doakategori.DoaKategoriResponse
import com.asyabab.majelisrasulullah.data.models.doalist.DoaListResponse
import com.asyabab.majelisrasulullah.data.models.jadwalsholat.JadwalSholatResponse
import com.asyabab.majelisrasulullah.data.network.APIRequest
import com.asyabab.majelisrasulullah.database.DatabaseAdapter
import com.asyabab.majelisrasulullah.model.Asma
import com.asyabab.majelisrasulullah.model.Ayat
import com.asyabab.majelisrasulullah.model.Surah
import org.json.JSONObject
import pasien.halalan.data.local.SharedPrefHelper
import java.util.*

class Repository(private val mContext: Context) {

    private val apiRequest: APIRequest = APIRequest()
    private val database = DatabaseAdapter(mContext)

    private val prefs: SharedPrefHelper = SharedPrefHelper(mContext)

    private var city = ArrayList<String>()
    private var spesialis = ArrayList<String>()


    fun saveToken(token: String?) {
        prefs.putString(SharedPrefHelper.ACCES_TOKEN, token!!)
    }

    fun getToken(): String? {
        return prefs.getString(SharedPrefHelper.ACCES_TOKEN)
    }
    fun setFirstTimeLaunch(isFirstTime: Boolean) {
        prefs.setFirstTimeLaunch(isFirstTime)
    }

    fun isFirstTimeLaunch(): Boolean {
        return prefs.isFirstTimeLaunch()
    }

    fun saveJadwalSholat(token: String?) {
        prefs.putString(SharedPrefHelper.ACCES_JADWAL, token!!)
    }

    fun getJadwalSholat(): String? {
        return prefs.getString(SharedPrefHelper.ACCES_TOKEN)
    }


    fun getArtikel(callback: ArtikelResponse.ArtikelResponseCallback) {
        apiRequest.getArticle(callback)

    }

    fun getDoaKategori(json: String, callback: DoaKategoriResponse.DoaKetegoriResponseCallback) {
        apiRequest.getDoaKategori(json,callback)

    }
    fun getDoaList(json: String, callback: DoaListResponse.DoaListResponseCallback) {
        apiRequest.getDoaList(json,callback)

    }

    fun getSurah(): ArrayList<Surah> {
        return database.getSurah()
    }

    fun getAyat(surah:String): ArrayList<Ayat> {
        return database.getAyat(surah)
    }
    fun getAsmaulHusna(): ArrayList<Asma> {
        return database.getAsmaulHusna()
    }

    fun getJadwalSholat(kota:String, negara:String, metode:String, offset:String, callback: JadwalSholatResponse.JadwalSholatResponseCallback) {
        apiRequest.getJadwalSholat(kota,negara, metode, offset, callback)
    }
}