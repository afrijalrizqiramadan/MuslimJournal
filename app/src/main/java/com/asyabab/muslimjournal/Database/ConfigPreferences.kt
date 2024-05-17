package com.asyabab.majelisrasulullah.database

import android.content.Context
import com.asyabab.majelisrasulullah.model.LocationInfo
import com.google.gson.Gson

/**
 * Class to save application configurations
 */
object ConfigPreferences {
    private const val MAIN_CONFIG = "application_settings"
    private const val LOCATION_INFO = "location_information"
    private const val QUIBLA_DEGREE = "quibla_degree"
    private const val ALARM = "alarm"
    private const val NEXT_PRAY = "next_pray"
    const val WEATHER_INFO = "Weather"
    const val TODAY_WETHER = "today_weather"
    const val WEEK_WETHER = "week_weather"
    private const val APP_LANGUAGE = "app_language"
    private const val PRAY_NOTIFY = "pray_notify"
    const val ZEKER_NOTIFY = "zeker_notifiy"
    const val ZEKER_NOTIFICATION = "zeker_notification"
    private const val SILENT_MOOD = "silent_mood"
    private const val LED_MOOD = "led_mood"
    private const val WIDGET_MONTH = "widget_month"
    private const val VIBRATION = "vibration_mood"
    private const val TWENTYFOUR = "twenty_four"
    private const val AZKAR_MOOD = "azkar_mood"
    private const val COUNTRY_POPUP = "country_popup"
    private const val APP_FIRST_OPEN = "application_first_open"

    /**
     * Function to save location information
     *
     * @param locationConfig
     */
    @JvmStatic
    fun setLocationConfig(context: Context, locationConfig: LocationInfo?) {
        val editor = context.getSharedPreferences(MAIN_CONFIG, Context.MODE_PRIVATE).edit()
        val gson = Gson()
        val json = gson.toJson(locationConfig)
        editor.putString(LOCATION_INFO, json)
        editor.apply()
    }

    /**
     * Function to get location information
     *
     * @return LocationInfo object
     */
    @JvmStatic
    fun getLocationConfig(context: Context): LocationInfo {
        val sharedPreferences = context.getSharedPreferences(
            MAIN_CONFIG,
            Context.MODE_PRIVATE
        )
        val gson = Gson()
        val json = sharedPreferences.getString(LOCATION_INFO, "")
        return gson.fromJson(json, LocationInfo::class.java)
    }

    @JvmStatic
    fun setQuibla(context: Context, degree: Int) {
        val editor = context.getSharedPreferences(MAIN_CONFIG, Context.MODE_PRIVATE).edit()
        editor.putInt(QUIBLA_DEGREE, degree)
        editor.apply()
    }

    /**
     * Function to get saved degree
     *
     * @return Quibla degree from north
     */
    fun getQuibla(context: Context): Int {
        val sharedPreferences = context.getSharedPreferences(
            MAIN_CONFIG,
            Context.MODE_PRIVATE
        )
        return sharedPreferences.getInt(QUIBLA_DEGREE, -1)
    }

    /**
     * function to set alarm
     *
     * @param alarm alarm
     */
    fun setAlarm(context: Context, alarm: Boolean) {
        val editor = context.getSharedPreferences(MAIN_CONFIG, Context.MODE_PRIVATE).edit()
        editor.putBoolean(ALARM, alarm)
        editor.apply()
    }

    /**
     * Function to get alarm state
     *
     * @return Alarm state
     */
    fun getAlarm(context: Context): Boolean {
        val sharedPreferences = context.getSharedPreferences(
            MAIN_CONFIG,
            Context.MODE_PRIVATE
        )
        return sharedPreferences.getBoolean(ALARM, false)
    }

    /**
     * Function to set next pray alarm time
     *
     * @param time
     */
    fun setNextPrayAlarm(context: Context, time: String?) {
        val editor = context.getSharedPreferences(MAIN_CONFIG, Context.MODE_PRIVATE).edit()
        editor.putString(NEXT_PRAY, time)
        editor.apply()
    }

    /**
     * Function to get next pray alarm time
     */
    fun getNextPrayAlarm(context: Context): String? {
        val sharedPreferences = context.getSharedPreferences(
            MAIN_CONFIG,
            Context.MODE_PRIVATE
        )
        return sharedPreferences.getString(NEXT_PRAY, null)
    }

    /**
     * Function to set application language
     *
     * @param language Application language
     */
    @JvmStatic
    fun setApplicationLanguage(context: Context, language: String?) {
        val editor = context.getSharedPreferences(MAIN_CONFIG, Context.MODE_PRIVATE).edit()
        editor.putString(APP_LANGUAGE, language)
        editor.apply()
    }

    /**
     * Function to get application language
     *
     * @return Application language
     */
    @JvmStatic
    fun getApplicationLanguage(context: Context): String? {
        val sharedPreferences = context.getSharedPreferences(
            MAIN_CONFIG,
            Context.MODE_PRIVATE
        )
        return sharedPreferences.getString(APP_LANGUAGE, "en")
    }

    /**
     * Function to set notification for praying
     *
     * @param notification Flag to notify or not
     */
    @JvmStatic
    fun setPrayingNotification(context: Context, notification: Boolean) {
        val editor = context.getSharedPreferences(MAIN_CONFIG, Context.MODE_PRIVATE).edit()
        editor.putBoolean(PRAY_NOTIFY, notification)
        editor.apply()
    }

    /**
     * Function to get notification of pray or not
     *
     * @return
     */
    @JvmStatic
    fun getPrayingNotification(context: Context): Boolean {
        val sharedPreferences = context.getSharedPreferences(MAIN_CONFIG, Context.MODE_PRIVATE)
        return sharedPreferences.getBoolean(PRAY_NOTIFY, false)
    }

    /**
     * Function to set silent mood
     *
     * @param silent Flag of silent mood
     */
    @JvmStatic
    fun setSilentMood(context: Context, silent: Boolean) {
        val editor = context.getSharedPreferences(MAIN_CONFIG, Context.MODE_PRIVATE).edit()
        editor.putBoolean(SILENT_MOOD, silent)
        editor.apply()
    }

    /**
     * Function to get silent mood
     *
     * @return Flag of silent mood
     */
    @JvmStatic
    fun getSilentMood(context: Context): Boolean {
        val sharedPreferences = context.getSharedPreferences(MAIN_CONFIG, Context.MODE_PRIVATE)
        return sharedPreferences.getBoolean(SILENT_MOOD, true)
    }

    /**
     * Function to set led mood
     *
     * @param led Flag of led mood
     */
    @JvmStatic
    fun setLedNotification(context: Context, led: Boolean) {
        val editor = context.getSharedPreferences(MAIN_CONFIG, Context.MODE_PRIVATE).edit()
        editor.putBoolean(LED_MOOD, led)
        editor.apply()
    }

    /**
     * Function to get led mood
     *
     * @return Flag of led mood
     */
    @JvmStatic
    fun getLedNotification(context: Context): Boolean {
        val sharedPreferences = context.getSharedPreferences(MAIN_CONFIG, Context.MODE_PRIVATE)
        return sharedPreferences.getBoolean(LED_MOOD, true)
    }

    /**
     * Function to set current widget month show
     *
     * @param context Application context
     * @param month   Current month
     */
    fun setCurrentWidgetMonth(context: Context, month: Int) {
        val editor = context.getSharedPreferences(MAIN_CONFIG, Context.MODE_PRIVATE).edit()
        editor.putInt(WIDGET_MONTH, month)
        editor.apply()
    }

    /**
     * Function to get current widget month show
     *
     * @param context Application context
     * @return Current month
     */
    fun getCurrentWidgetMonth(context: Context): Int {
        val sharedPreferences = context.getSharedPreferences(MAIN_CONFIG, Context.MODE_PRIVATE)
        return sharedPreferences.getInt(WIDGET_MONTH, 1)
    }

    /**
     * Function to set vibration mode
     *
     * @param context       Application context
     * @param vibrationFlag Vibration mode on / off
     */
    @JvmStatic
    fun setVibrationMode(context: Context, vibrationFlag: Boolean) {
        val editor = context.getSharedPreferences(MAIN_CONFIG, Context.MODE_PRIVATE).edit()
        editor.putBoolean(VIBRATION, vibrationFlag)
        editor.apply()
    }

    /**
     * Function to get vibration mode
     *
     * @param context Application context
     * @return Current vibration mode
     */
    @JvmStatic
    fun getVibrationMode(context: Context): Boolean {
        val sharedPreferences = context.getSharedPreferences(MAIN_CONFIG, Context.MODE_PRIVATE)
        return sharedPreferences.getBoolean(VIBRATION, true)
    }

    /**
     * Function to set twenty four hour mode show
     *
     * @param context        Application context
     * @param twentyFourFlag on / off
     */
    @JvmStatic
    fun setTwentyFourMode(context: Context, twentyFourFlag: Boolean) {
        val editor = context.getSharedPreferences(MAIN_CONFIG, Context.MODE_PRIVATE).edit()
        editor.putBoolean(TWENTYFOUR, twentyFourFlag)
        editor.apply()
    }

    /**
     * Function to get twenty four hour mode
     *
     * @param context Application context
     * @return on / off
     */
    @JvmStatic
    fun getTwentyFourMode(context: Context): Boolean {
        val sharedPreferences = context.getSharedPreferences(MAIN_CONFIG, Context.MODE_PRIVATE)
        return sharedPreferences.getBoolean(TWENTYFOUR, false)
    }

    /**
     * Function to set azkar mood
     *
     * @param context   Application context
     * @param azkarMood Azkar flag on / off
     */
    @JvmStatic
    fun setAzkarMood(context: Context, azkarMood: Boolean) {
        val editor = context.getSharedPreferences(MAIN_CONFIG, Context.MODE_PRIVATE).edit()
        editor.putBoolean(AZKAR_MOOD, azkarMood)
        editor.apply()
    }

    /**
     * Function to get azkar mood
     *
     * @param context Application context
     * @return Azkar flag on / off
     */
    @JvmStatic
    fun getAzkarMood(context: Context): Boolean {
        val sharedPreferences = context.getSharedPreferences(MAIN_CONFIG, Context.MODE_PRIVATE)
        return sharedPreferences.getBoolean(AZKAR_MOOD, true)
    }

    /**
     * Function to set world prayer selected country
     *
     * @param context      Application context
     * @param locationInfo Location Information of selected country
     */
    @JvmStatic
    fun setWorldPrayerCountry(context: Context, locationInfo: LocationInfo?) {
        val editor = context.getSharedPreferences(MAIN_CONFIG, Context.MODE_PRIVATE).edit()
        val gson = Gson()
        val json = gson.toJson(locationInfo)
        editor.putString(COUNTRY_POPUP, json)
        editor.apply()
    }

    /**
     * Function to get world prayer selected country
     *
     * @param context Application context
     * @return LocationInfo of selected country
     */
    @JvmStatic
    fun getWorldPrayerCountry(context: Context): LocationInfo {
        val sharedPreferences = context.getSharedPreferences(
            MAIN_CONFIG,
            Context.MODE_PRIVATE
        )
        val gson = Gson()
        val json = sharedPreferences.getString(COUNTRY_POPUP, "")
        return gson.fromJson(json, LocationInfo::class.java)
    }

    /**
     * Function to set application first open done
     *
     * @param context Application context
     */
    fun setApplicationFirstOpenDone(context: Context) {
        val editor = context.getSharedPreferences(MAIN_CONFIG, Context.MODE_PRIVATE).edit()
        editor.putBoolean(APP_FIRST_OPEN, false)
        editor.apply()
    }

    /**
     * Function to check if app 1st open or not
     *
     * @param context Application context
     * @return Flag of 1st open or not
     */
    fun IsApplicationFirstOpen(context: Context): Boolean {
        val sharedPreferences = context.getSharedPreferences(MAIN_CONFIG, Context.MODE_PRIVATE)
        return sharedPreferences.getBoolean(APP_FIRST_OPEN, true)
    }
}