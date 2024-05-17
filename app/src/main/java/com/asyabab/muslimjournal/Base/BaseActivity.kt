package com.asyabab.majelisrasulullah.base

import android.content.Context
import android.os.Build
import android.os.Bundle
import android.view.Window
import android.view.WindowManager
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.asyabab.muslimjournal.DPApp
import com.asyabab.muslimjournal.R
import com.asyabab.muslimjournal.data.Repository
import com.asyabab.muslimjournal.utils.DialogHelper
import com.asyabab.muslimjournal.utils.getAppColor
import com.asyabab.muslimjournal.utils.lightStatusBar
//import com.facebook.login.LoginManager
import io.github.inflationx.viewpump.ViewPumpContextWrapper

open class BaseActivity : AppCompatActivity() {

    private lateinit var progressBar: AlertDialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
         progressBar = DialogHelper.loading(this)

//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
//            val w: Window = window // in Activity's onCreate() for instance
//            w.setFlags(
//                WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
//                WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS
//            )
//        }

        repository?.saveToken(
"Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJSUzI1NiJ9.eyJhdWQiOiIzIiwianRpIjoiMjdjNzc5NDA1ZjlkNWU1YjhiNWUxMjUxYWU5N2YwZDcwMTZmYTQ5YWQ2Mjc4OTY2YWNlOTBmOTljMzc4YjBmNTk0MmQ3NGE0YTQ4OGUyNTQiLCJpYXQiOiIxNjA5NDMxNTEzLjAyMzQwMCIsIm5iZiI6IjE2MDk0MzE1MTMuMDIzNDA1IiwiZXhwIjoiMTY0MDk2NzUxMy4wMjA3ODgiLCJzdWIiOiIxNyIsInNjb3BlcyI6WyJjdXN0b21lciJdfQ.XqHxe7UJ--yM4kw-QDsMxH263vgGnUgmsNl_fXY26tUYljguMC4DxB2gO39u-lS2jPfSeGosGs7agI4KadXl9uovf6UicaP4TAwqhClJrg6gqcW--zusbmSPZtxasNGiK-DtkhYzDWsSdhWXQD9T6QsqBaL85eElOrZYbCei26xyjpI-YfqJZimg8reWy3xmpn3RbZ9FTK4XK68MHEEgaAjv42_nbjMSiM_hyjsL9o2X9GD95Ni62xnQtA3EFgnpWqDI14a4vV7fGjf5mt2O18axI6haMq8xNd36dQSPi7K5hDF0Iec3RrM45IE9uvUSWzqhPJViarqouZkDyDEZFRk8B9yai3CtFcM8mWgcG2Wv-vmX2wIE3S6HnTEske-wYbvEnenj8lNrcyDZUKaAIZ3NfXfQTb7ALHdScUVAgzmUl95YMRDYYQLD29wY-z88nQUQ2Dd6XOpyB3pArJk1CAYYUQL3nfl8lZI10CZpRI4-WAyqhRlZijx7BDXOpdqpwK8zJ0InVVx0ZaetP648mgbT_elXqK61v492Ro223Q9YOOPMcjmawh8NVXOH6SCUkIpvxPJjes1GHYvipHBvwaaxY8BYHbLD2KRu4F_mOOo9IWA0cU3jOwA9JjR0LZpCJByJLWtCtnuXYmtYH7KDWnlWKGoiF7LvBkRjqv5Z1l0"
        )!!


    }

//    protected val mAuth: FirebaseAuth?
//        get() = FirebaseAuth.getInstance()
//    protected val mLoginManager: LoginManager?
//        get() = LoginManager.getInstance()
//    protected val loading: AlertDialog?
//        get() = progressBar


    override fun attachBaseContext(newBase: Context) {
        super.attachBaseContext(ViewPumpContextWrapper.wrap(newBase))
    }

    protected val repository: Repository?
        get() = (application as DPApp).repository


/*
    fun setToolbar(mToolbar: Toolbar) {
        setSupportActionBar(mToolbar)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        mToolbar.setNavigationIcon(R.drawable.theme9_ic_arrow_back)
        mToolbar.setNavigationOnClickListener { onBackPressed() }
        mToolbar.changeToolbarFont()
    }
*/


//    fun loadFragment(fragment: Fragment?) {
//        if (fragment != null) {
//            supportFragmentManager
//                .beginTransaction()
//                .replace(R.id.frame_container, fragment)
//                .commit()
//
//
//        }
//    }
//
//    fun loadFragment(fragment: Fragment?, uid: String) {
//        if (fragment != null) {
//            val bundle = Bundle()
//            bundle.putString("uid", uid)
//            fragment.arguments = bundle
//            supportFragmentManager
//                .beginTransaction()
//                .replace(R.id.frame_container, fragment)
//                .detach(fragment).attach(fragment)
//                .commit()
//        }
//    }
//
//
//    fun loadFragment(fragment: Fragment?, serializable: Serializable) {
//        if (fragment != null) {
//            val bundle = Bundle()
//            bundle.putSerializable("serial", serializable)
//            fragment.arguments = bundle
//            supportFragmentManager
//                .beginTransaction()
//                .replace(R.id.frame_container, fragment)
//                .commit()
//        }
//    }
//    fun loadFragment(fragment: Fragment?, string1: String, string2: String) {
//        if (fragment != null) {
//            val bundle = Bundle()
//            bundle.putString("string1", string1)
//            bundle.putString("string2", string2)
//            fragment.arguments = bundle
//            supportFragmentManager
//                ?.beginTransaction()
//                ?.replace(R.id.frame_container, fragment)
//                ?.detach(fragment)?.attach(fragment)
//                ?.commit()
//        }
//    }
//
//    fun logout() {
//        repository!!.saveToken("")
//        repository!!.setFirstTimeLaunch(true)
//        mAuth!!.signOut()
//        launchActivityWithNewTask<BerandaActivity>()
//    }
}
