package com.asyabab.muslimjournal.Activity

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.os.Handler
import android.provider.Settings
import android.text.TextUtils
import android.util.Log
import android.view.View
import android.widget.Toast
import com.asyabab.endora.data.models.general.GeneralResponse
import com.asyabab.endora.data.models.login.LoginResponse
import com.asyabab.endora.data.models.login.LoginResponse2
import com.asyabab.endora.utils.launchActivity
import com.asyabab.endora.utils.launchActivityWithNewTask
import com.asyabab.endora.utils.onClick
import com.asyabab.muslimjournal.R
import com.facebook.CallbackManager
import com.facebook.FacebookCallback
import com.facebook.FacebookException
import com.facebook.FacebookSdk
import com.facebook.login.LoginResult
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FacebookAuthProvider
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.database.annotations.Nullable
import com.google.firebase.iid.FirebaseInstanceId
import com.google.gson.Gson
import kotlinx.android.synthetic.main.activity_masuk.*
import kotlinx.android.synthetic.main.activity_masuk.btDaftar
import kotlinx.android.synthetic.main.activity_masuk.btFBLogin
import kotlinx.android.synthetic.main.activity_masuk.btGoogleLogin
import kotlinx.android.synthetic.main.activity_masuk.btMasuk
import kotlinx.android.synthetic.main.activity_masuk.inputEmail
import kotlinx.android.synthetic.main.activity_masuk.inputPassword


class LoginActivity : BaseActivity() {
    private lateinit var callbackManager: CallbackManager
    lateinit var handler: Handler
    var TAG = "fbinfo"
    private var token = ""
    private var token_socmed = ""

    private val RC_SIGN_IN = 7
    private lateinit var mGoogleSignInClient: GoogleSignInClient
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        callbackManager = CallbackManager.Factory.create();

        FacebookSdk.sdkInitialize(this);
        setContentView(R.layout.activity_masuk)

        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .build()
        val androidId = Settings.Secure.getString(
            this.contentResolver,
            Settings.Secure.ANDROID_ID
        )

        FirebaseInstanceId.getInstance().instanceId
            .addOnSuccessListener { instanceIdResult ->
                token = instanceIdResult.token
            }

        mGoogleSignInClient = GoogleSignIn.getClient(this, gso)
        btMasuk.onClick {
            if (inputEmail.text.toString() == "") {
                tvGagalMasuk.visibility = View.VISIBLE
                tvGagalMasuk.text = "Masukkan Email"
            } else if (inputPassword.text.toString() == "") {
                tvGagalMasuk.visibility = View.VISIBLE
                tvGagalMasuk.text = "Masukkan  Password"
            } else {
                tvGagalMasuk.visibility = View.INVISIBLE
                loading?.show()
                val tokenFcm = token
                val deviceId = androidId
                val type = "username"
                login(
                    inputEmail.text.toString(),
                    inputPassword.text.toString(),
                    deviceId,
                    tokenFcm,
                    type,
                    tokenFcm
                )
            }
        }
        btDaftar.onClick {
            launchActivity<DaftarActivity> { }
        }
        btLupaSandi.onClick {


            launchActivity<LupaSandiActivity> { }
        }
        btGoogleLogin.onClick {
            masukDenganGoogle()
        }
        btFBLogin.onClick {
            masukDenganFacebook()
        }
    }

    fun lupaSandi(id: String) {
        repository!!.setMain(
            id,
            repository?.getToken()!!,
            object : GeneralResponse.GeneralResponseCallback {
                override fun onSuccess(generalResponse: GeneralResponse) {
                    if (generalResponse.status == true) {

                    }
                }

                override fun onFailure(message: String) {
                    Log.d("UbahProfil", "signInsuccess" + message)
                    Toast.makeText(applicationContext, "Server Sedang Error", Toast.LENGTH_LONG)
                        .show()

                }

            })
    }

    fun masukDenganFacebook() {
        mLoginManager?.logInWithReadPermissions(
            this@LoginActivity,
            listOf("email", "public_profile")
        )
        mLoginManager?.registerCallback(callbackManager, object :
            FacebookCallback<LoginResult> {
            override fun onSuccess(loginResult: LoginResult) {
                Log.d(TAG, "facebook:onSuccess:$loginResult")
                val accessToken = loginResult.accessToken.token
                token_socmed = loginResult.accessToken.token
                handleFacebookAccessToken(accessToken)
            }

            override fun onCancel() {
                Log.d(TAG, "facebook:onCancel")
                // ...
            }

            override fun onError(error: FacebookException) {
                Log.d(TAG, "facebook:onError", error)
                // ...
            }
        })
    }


    fun masukDenganGoogle() {
        val signInIntent = mGoogleSignInClient.signInIntent

        startActivityForResult(signInIntent, RC_SIGN_IN)
        loading?.show()

    }

    fun login(
        email: String,
        password: String,
        device_id: String,
        token_fcm: String,
        type: String,
        token_scm: String
    ) {
        repository?.login(
            email,
            password,
            device_id,
            token_fcm,
            type,
            token_scm,
            object : LoginResponse.LoginResponseCallback {
                override fun onSuccess(loginResponse: LoginResponse) {
                    Log.d("Login", "signInsuccess")
                    loading?.dismiss()
                    if (loginResponse.status == true) {
                        Log.d("Login", "signInsuccess2")
                        if (!TextUtils.isEmpty(loginResponse.data?.token.toString())) {
                            val token = loginResponse.data?.token.toString()
                            Log.d("Login", "signInsuccess22")
                            repository?.saveToken("Bearer $token")
                            val gson = Gson()
                            val json: String = gson.toJson(loginResponse.data)
                            repository?.saveProfile(json)
                            Log.d("Losgin", json)
                            Toast.makeText(applicationContext, "Berhasil Login", Toast.LENGTH_LONG)
                                .show()
                            launchActivityWithNewTask<BerandaActivity>()
                            finish()
                        }
                    } else {
                        Toast.makeText(applicationContext, loginResponse.message, Toast.LENGTH_LONG)
                            .show()
//                        Toast.makeText(this@LoginActivity, loginResponse.message2?.username?.get(0), Toast.LENGTH_SHORT).show()
//                        Toast.makeText(this@LoginActivity, loginResponse.message2?.password?.get(0), Toast.LENGTH_SHORT).show()
//                        tvGagalMasuk.visibility = View.VISIBLE
//                        tvGagalMasuk.text = "Email atau Password Salah"
                    }
                }

                override fun onFailure(message: String) {
                    if (message == "Kondisi2") {
                        repository?.login2(
                            email,
                            password,
                            token_fcm,
                            device_id,
                            type,
                            token_scm,
                            object : LoginResponse2.LoginResponse2Callback {
                                override fun onSuccess(loginResponse2: LoginResponse2) {
                                    Log.d("Login", "signInsuccess")
                                    loading?.dismiss()
                                    Toast.makeText(
                                        applicationContext,
                                        loginResponse2.message.toString(),
                                        Toast.LENGTH_LONG
                                    ).show()
                                    tvGagalMasuk.visibility = View.VISIBLE
                                    tvGagalMasuk.text = loginResponse2.message.toString()

                                }

                                override fun onFailure(message: String) {

                                    Log.d("Login", message)
                                    loading?.dismiss()
                                    tvGagalMasuk.visibility = View.VISIBLE
                                    tvGagalMasuk.text = "Akun Belum Terdaftar"
//                                    Toast.makeText(
//                                        applicationContext,
//                                        "Server Sedang Error2 $message",
//                                        Toast.LENGTH_LONG
//                                    ).show()
                                    mGoogleSignInClient.signOut()

                                }

                            }
                        )

                    } else {
                        Log.d("Login", message)
                        loading?.dismiss()
                        Toast.makeText(
                            applicationContext,
                            "General Error",
                            Toast.LENGTH_LONG
                        )
                            .show()
                        mGoogleSignInClient.signOut()
                    }
                }

            })
    }

    override fun onActivityResult(
        requestCode: Int,
        resultCode: Int,
        @Nullable data: Intent?
    ) {
        callbackManager.onActivityResult(requestCode, resultCode, data)

        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == RC_SIGN_IN) {
            val task: Task<GoogleSignInAccount> = GoogleSignIn.getSignedInAccountFromIntent(data)
            handleSignInResult(task)
        }

        // Pass the activity result back to the Facebook SDK
    }

    private fun handleSignInResult(completedTask: Task<GoogleSignInAccount>) {
        try {
            val acc: GoogleSignInAccount? = completedTask.getResult(ApiException::class.java)
//            Toast.makeText(this@LoginActivity, "Login Berhasil", Toast.LENGTH_SHORT).show()
            FirebaseGoogleAuth(acc)
        } catch (e: ApiException) {
//            Toast.makeText(this@LoginActivity, "Login Gagal", Toast.LENGTH_SHORT).show()
            FirebaseGoogleAuth(null)
        }
    }

    private fun handleFacebookAccessToken(token: String) {
        Log.d(TAG, "handleFacebookAccessToken:$token")

        val credential = FacebookAuthProvider.getCredential(token)
        mAuth?.signInWithCredential(credential)
            ?.addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    Log.d(TAG, "signInWithCredential:success")
                    val user = mAuth?.currentUser
                    Log.d(TAG, user.toString())

                    updateUI(user)
                } else {
                    // If sign in fails, display a message to the user.
                    Log.w(TAG, "signInWithCredential:failure", task.exception)
                    Toast.makeText(
                        baseContext, "Autentikasi Gagal",
                        Toast.LENGTH_SHORT
                    ).show()
                    updateUI(null)
                }

                // ...
            }
    }

    private fun FirebaseGoogleAuth(acct: GoogleSignInAccount?) {
        //check if the account is null
        if (acct != null) {
            token_socmed = acct.idToken
            val authCredential =
                GoogleAuthProvider.getCredential(acct.idToken, null)
            mAuth!!.signInWithCredential(authCredential)
                .addOnCompleteListener(
                    this
                ) { task ->
                    if (task.isSuccessful) {
                        loading?.dismiss()

//                        Toast.makeText(this@LoginActivity, "Berhasil", Toast.LENGTH_SHORT)
//                            .show()
                        val user = mAuth!!.currentUser
                        updateUI(user)

                    } else {
                        loading?.dismiss()

                        Toast.makeText(this@LoginActivity, "Gagal", Toast.LENGTH_SHORT).show()
                        updateUI(null)
                    }
                }
        } else {
            loading?.dismiss()

            Toast.makeText(this@LoginActivity, "Gagal", Toast.LENGTH_SHORT).show()
        }
    }

    private fun updateUI(fUser: FirebaseUser?) {
//        btnSignOut.setVisibility(View.VISIBLE)
        var personName = ""
        var personGivenName = ""
        var personFamilyName = ""
        var personEmail = ""
        var personId = ""
        var personPhoto: Uri? = null
        var account =
            GoogleSignIn.getLastSignedInAccount(applicationContext)
        if (fUser != null) {
            personName = fUser.displayName.toString()
            personGivenName = fUser.displayName.toString()
            personEmail = fUser.email.toString()
            personId = fUser.uid
            personPhoto = fUser.photoUrl
        }
        if (account != null) {
            personName = account.displayName.toString()
            personGivenName = account.givenName.toString()
            personFamilyName = account.familyName.toString()
            personEmail = account.email.toString()
            personId = account.id.toString()
            personPhoto = account.photoUrl


        }

//        Toast.makeText(this@LoginActivity, "Berhasil Masuk $personEmail", Toast.LENGTH_SHORT).show()

        val androidId = Settings.Secure.getString(
            this.contentResolver,
            Settings.Secure.ANDROID_ID
        )

        val tokenFcm = token
        val deviceId = androidId
        val type = "email"
        login(
            personEmail,
            "",
            deviceId,
            tokenFcm,
            type,
            "0"
        )

//        launchActivityWithNewTask<BerandaActivity>()

//    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
//        super.onActivityResult(requestCode, resultCode, data)
//
//        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
//        if (requestCode == RC_SIGN_IN) {
//            try {
//                // Google Sign In was successful, authenticate with Firebase
//                val task = GoogleSignIn.getSignedInAccountFromIntent(data)
//                val account = task.getResult(ApiException::class.java)
//                if (account != null) {
////                    Toast.makeText(this, account.email, Toast.LENGTH_SHORT).show()
////                    repository!!.checkEmail(
////                        account.email.toString(),
////                        object : GeneralResponse.GeneralResponseCallback {
////                            override fun onSuccess(generalResponse: GeneralResponse) {
////                                if (generalResponse.status == true) {
////                                    firebaseAuthWithGoogle(account)
////                                } else {
////                                    loading?.dismiss()
////                                    Toast.makeText(
////                                        this@DPWalkThroughActivity,
////                                        generalResponse.message,
////                                        Toast.LENGTH_SHORT
////                                    ).show()
////                                    mGoogleSignInClient.signOut()
////                                }
////                            }
////
////                            override fun onFailure(message: String) {
////                                loading?.dismiss()
////                                Toast.makeText(
////                                    this@DPWalkThroughActivity,
////                                    message,
////                                    Toast.LENGTH_SHORT
////                                ).show()
////                                mGoogleSignInClient.signOut()
////                            }
////
////                        }
////                    )
//                } else {
//                    loading?.dismiss()
//                }
//
//            } catch (e: ApiException) {
//                loading?.dismiss()
//                // Google Sign In failed, update UI appropriately
//                Log.w("Login", "Google sign in failed", e)
//                mGoogleSignInClient.signOut()
//                // ...
//            }
//
//        } else {
//            loading?.dismiss()
//        }
//    }
//
//    private fun firebaseAuthWithGoogle(acct: GoogleSignInAccount) {
//        Log.d("Login", "firebaseAuthWithGoogle:" + acct.id!!)
//
//        val credential = GoogleAuthProvider.getCredential(acct.idToken, null)
//        mAuth?.signInWithCredential(credential)
//            ?.addOnCompleteListener(this) { task ->
//                if (task.isSuccessful) {
//                    // Sign in success, update UI with the signed-in user's information
//                    Log.d("Login", "signInWithCredential:success")
//                    val user = mAuth!!.currentUser
//                    updateUserReference(user)
//                } else {
//                    loading?.dismiss()
//                    // If sign in fails, display a message to the user.
//                    Log.w("Login", "signInWithCredential:failure", task.exception)
//                    Toast.makeText(this, "Auth Failed", Toast.LENGTH_LONG).show()
//                    updateUI(null)
//                }
//            }
//    }
//
//    fun updateUserReference(user: FirebaseUser?) {
//        val deviceToken = FirebaseInstanceId.getInstance().token
//        val currentUserID = user!!.uid
//        val authMap = HashMap<String, Any?>()
//        authMap["email"] = user.email
//        authMap["user_type"] = "patient"
//        authMap["is_typing"] = false
//        authMap["device_token"] = deviceToken
////        authMap["login_time"] = saveCurrentTime
//        authMap["state"] = "online"
//        if (!TextUtils.isEmpty(user.displayName)) {
//            authMap["name"] = user.displayName
//        }
//        FirebaseDatabase.getInstance().reference
//            .child("Users").child(currentUserID).updateChildren(authMap)
//            .addOnCompleteListener { task1 ->
//                if (task1.isSuccessful) {
//                    updateUI(user)
//                } else {
//                    loading?.dismiss()
//                    Toast.makeText(this, task1.exception!!.message, Toast.LENGTH_SHORT)
//                        .show()
//                }
//            }
//    }
//
//    fun updateUI(user: FirebaseUser?) {
//        if (user != null) {
//            //Do your Stuff
//            Toast.makeText(this, "Hello ${user.displayName}", Toast.LENGTH_LONG).show()
//            login(user.email.toString(), user.uid)
//        }
//    }
    }
}

