package com.asyabab.muslimjournal.Activity

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.os.Handler
import android.provider.Settings.Secure
import android.text.TextUtils
import android.util.Log
import android.view.View
import android.widget.Toast
import com.asyabab.endora.data.models.register.RegisterResponse
import com.asyabab.endora.data.models.register.RegisterResponse2
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
import com.google.gson.GsonBuilder
import kotlinx.android.synthetic.main.activity_daftar.*
import java.util.regex.Matcher
import java.util.regex.Pattern


class DaftarActivity : BaseActivity() {
    private lateinit var callbackManager: CallbackManager
    var TAG = "fbinfo"
    lateinit var handler: Handler
    private val RC_SIGN_IN = 7
    private lateinit var mGoogleSignInClient: GoogleSignInClient
    private var token = ""
    private var tokenScm = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        callbackManager = CallbackManager.Factory.create();
        FacebookSdk.sdkInitialize(this);
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .build()
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso)
        setContentView(R.layout.activity_daftar)
        FirebaseInstanceId.getInstance().instanceId
            .addOnSuccessListener { instanceIdResult ->
                token = instanceIdResult.token //Token\

            }
        val androidId = Secure.getString(
            this.contentResolver,
            Secure.ANDROID_ID
        )

        Log.d("deviceId", "deviceId:$androidId")

        btMasuk.onClick {
            launchActivity<LoginActivity>()
        }
        btDaftar.onClick {
            val email = inputEmail.text.toString()
            val password = inputPassword.text.toString()
            val passwordulang = inputPasswordUlang.text.toString()
            val username = inputUsername.text.toString()
            if (username == "") {
                tvGagalDaftar.visibility = View.VISIBLE
                tvGagalDaftar.text = "Masukkan Username"
            } else if (username.length < 6) {
                tvGagalDaftar.visibility = View.VISIBLE
                tvGagalDaftar.text = "Username Minimal 6 Karakter"
            } else if (email == "") {
                tvGagalDaftar.visibility = View.VISIBLE
                tvGagalDaftar.text = "Masukkan Email"
            } else if (!emailValidator(email)) {
                tvGagalDaftar.visibility = View.VISIBLE
                tvGagalDaftar.text = "Isi Email Dengan Benar"
            } else if (password == "") {
                tvGagalDaftar.visibility = View.VISIBLE
                tvGagalDaftar.text = "Masukkan Kata Sandi"
            } else if (passwordulang == "") {
                tvGagalDaftar.visibility = View.VISIBLE
                tvGagalDaftar.text = "Masukkan Ulang Kata Sandi"
            } else if (passwordulang != password) {
                tvGagalDaftar.visibility = View.VISIBLE
                tvGagalDaftar.text = "Password Harus Sama"
            } else {

                val tokenFcm = token
                val deviceId = androidId
                val type = "username"
                val tokenScm = ""

                loading?.show()
                register(username, email, password, tokenFcm, deviceId, tokenScm, type)
            }
        }
        btFBLogin.onClick {
            masukDenganFacebook()
        }
        btGoogleLogin.onClick {
            masukDenganGoogle()
        }

    }

    fun emailValidator(email: String?): Boolean {
        val pattern: Pattern
        val matcher: Matcher
        val EMAIL_PATTERN =
            "^[_A-Za-z0-9-]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$"
        pattern = Pattern.compile(EMAIL_PATTERN)
        matcher = pattern.matcher(email)
        return matcher.matches()
    }

    fun masukDenganFacebook() {
        mLoginManager?.logInWithReadPermissions(
            this@DaftarActivity,
            listOf("email", "public_profile", "user_friends")
        )
        mLoginManager?.registerCallback(callbackManager, object :
            FacebookCallback<LoginResult> {
            override fun onSuccess(loginResult: LoginResult) {
                Log.d(TAG, "facebook:onSuccess:$loginResult")
                val accessToken = loginResult.accessToken.token
                tokenScm = accessToken
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

    fun register(
        username: String,
        email: String,
        password: String,
        token_fcm: String,
        device_id: String,
        token_scm: String,
        type: String
    ) {
        repository?.register(
            username,
            email,
            password,
            token_fcm,
            device_id,
            token_scm,
            type,
            object : RegisterResponse.RegisterResponseCallback {
                override fun onSuccess(registerResponse: RegisterResponse) {
                    Log.d("Login", "signInsuccess")
                    loading?.dismiss()
//                    Log.d("Login", "" + registerResponse.message?.email.toString())

                    if (registerResponse.status == true) {
                        Log.d("Login", "signInsuccess2")
                        if (!TextUtils.isEmpty(registerResponse.data?.token.toString())) {
                            val token = registerResponse.data?.token.toString()
//                            repository?.saveToken(token)

                            launchActivityWithNewTask<BerhasilDaftarActivity>()
                            finish()
                        }
                    } else {
//                        if (registerResponse.message?.username?.toString()
//                                .equals("[validation.unique]")!!
//                        ) {
//                            tvGagalDaftar.visibility = View.VISIBLE
//                            tvGagalDaftar.text = "Username Sudah Terdaftar"
//                        } else if (registerResponse.message?.email?.toString()
//                                .equals("[validation.unique]")!!
//                        ) {
//                            tvGagalDaftar.visibility = View.VISIBLE
//                            tvGagalDaftar.text = "Email Sudah Terdaftar"
//                        } else {
                        tvGagalDaftar.visibility = View.VISIBLE
                        tvGagalDaftar.text = "Daftar Gagal"
//                        }
                    }
                }

                override fun onFailure(message: String) {
//                    if (message == "Kondisi2") {
                        repository?.register2(
                            username,
                            email,
                            password,
                            token_fcm,
                            device_id,
                            token_scm,
                            type,
                            object : RegisterResponse2.RegisterResponse2Callback {
                                override fun onSuccess(response2: RegisterResponse2) {
                                    Log.d("Logindddd", "signInsuccess")
                                    loading?.dismiss()

                                    if (response2.message?.username?.toString()
                                            .equals("[validation.unique]")!!
                                    ) {
                                        tvGagalDaftar.visibility = View.VISIBLE
                                        tvGagalDaftar.text = "Username Sudah Terdaftar"
                                    } else if (response2.message?.email?.toString()
                                            .equals("[validation.unique]")!!
                                    ) {
                                        tvGagalDaftar.visibility = View.VISIBLE
                                        tvGagalDaftar.text = "Email Sudah Terdaftar"
                                    } else if (response2.message?.username?.toString()
                                            .equals("[validation.min.string]")!!
                                    ) {
                                        tvGagalDaftar.visibility = View.VISIBLE
                                        tvGagalDaftar.text = "Minimal 6 Karakter"
                                    } else {
                                        tvGagalDaftar.visibility = View.VISIBLE
                                        tvGagalDaftar.text = "Daftar Gagal"

                                    }
                                }

                                override fun onFailure(message: String) {
                                    val gson = GsonBuilder().create()

                                    val response1 =
                                        gson.fromJson(
                                            message.toString(),
                                            RegisterResponse2::class.java
                                        )
                                    Log.d("Logindddd", response1.message?.email.toString())
                                    if (response1.message?.username?.toString()
                                            .equals("[validation.unique]")!!
                                    ) {
                                        tvGagalDaftar.visibility = View.VISIBLE
                                        tvGagalDaftar.text = "Username Sudah Terdaftar"
                                    } else if (response1.message?.email?.toString()
                                            .equals("[validation.unique]")!!
                                    ) {
                                        tvGagalDaftar.visibility = View.VISIBLE
                                        tvGagalDaftar.text = "Email Sudah Terdaftar"
                                    } else if (response1.message?.username?.toString()
                                            .equals("[validation.min.string]")!!
                                    ) {
                                        tvGagalDaftar.visibility = View.VISIBLE
                                        tvGagalDaftar.text = "Minimal 6 Karakter"
                                    } else {
                                        tvGagalDaftar.visibility = View.VISIBLE
                                        tvGagalDaftar.text = "Daftar Gagal"

                                    }
                                    loading?.dismiss()
//                                    Toast.makeText(
//                                        applicationContext,
//                                        "Server Sedang Error",
//                                        Toast.LENGTH_LONG
//                                    ).show()
                                    mGoogleSignInClient.signOut()

                                }

                            }
                        )

//                    } else {
//                        Log.d("Login", message)
//                        loading?.dismiss()
//                        Toast.makeText(
//                            applicationContext,
//                            "Server Sedang Error$message",
//                            Toast.LENGTH_LONG
//                        )
//                            .show()
//                        mGoogleSignInClient.signOut()
//                    }
//
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
//            Toast.makeText(this@DaftarActivity, "Signed In Successfully", Toast.LENGTH_SHORT).show()
            FirebaseGoogleAuth(acc)
        } catch (e: ApiException) {
//            Toast.makeText(this@DaftarActivity, "Sign In Failed", Toast.LENGTH_SHORT).show()
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
                    updateUI(user)
                } else {
                    // If sign in fails, display a message to the user.
                    Log.w(TAG, "signInWithCredential:failure", task.exception)
                    Toast.makeText(
                        baseContext, "Authentication failed.",
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
            tokenScm = acct.idToken

            val authCredential =
                GoogleAuthProvider.getCredential(acct.idToken, null)
            mAuth!!.signInWithCredential(authCredential)
                .addOnCompleteListener(
                    this
                ) { task ->
                    if (task.isSuccessful) {
                        loading?.dismiss()

//                        Toast.makeText(this@DaftarActivity, "Successful", Toast.LENGTH_SHORT)
//                            .show()
                        val user = mAuth!!.currentUser
                        updateUI(user)

                    } else {
                        loading?.dismiss()

                        Toast.makeText(this@DaftarActivity, "Gagal Login", Toast.LENGTH_SHORT)
                            .show()
                        updateUI(null)
                    }
                }
        } else {
            loading?.dismiss()

            Toast.makeText(this@DaftarActivity, "Gagal Login", Toast.LENGTH_SHORT).show()
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
        val androidId = Secure.getString(
            this.contentResolver,
            Secure.ANDROID_ID
        )
        val tokenFcm = token
        val deviceId = androidId
        val password = "kosong"
        val type = "email"
//            loading?.show()

        register(
            (personGivenName + personFamilyName).replace(" ", "").toLowerCase(),
            personEmail,
            password,
            tokenFcm,
            deviceId,
            personId,
            type
        )
//        Toast.makeText(
//            this@DaftarActivity,
//            personId,
//            Toast.LENGTH_SHORT
//        )
//            .show()//

//        launchActivityWithNewTask<BerandaActivity>()
    }

}