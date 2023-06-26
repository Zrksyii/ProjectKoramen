package com.example.projectkoramen

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.example.projectkoramen.databinding.ActivityMainBinding
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking

class MainActivity : AppCompatActivity(), View.OnClickListener {
    private val binding: ActivityMainBinding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }
    private lateinit var googleSignInClient: GoogleSignInClient
    private lateinit var auth: FirebaseAuth
    private val resultLauncher: ActivityResultLauncher<Intent> =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
            when (it.resultCode) {
                1 -> {
                    startActivity(Intent(this, Dashboard::class.java))
                    finish()
                }

                Activity.RESULT_OK -> {
                    val task = GoogleSignIn.getSignedInAccountFromIntent(it.data)
                    try {
                        val account = task.getResult(ApiException::class.java)!!
                        firebaseAuthWithGoogle(account.idToken!!)
                    } catch (e: ApiException) {
                        val view = binding.mainLayout
                        Snackbar.make(view, "Google sign in failed.", Snackbar.LENGTH_SHORT).show()
                    }
                }
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        runBlocking {
            installSplashScreen()
            delay(2000)
        }
        setContentView(binding.root)

        binding.apply {
            btnSign.setOnClickListener(this@MainActivity)
            btnGoogle.setOnClickListener(this@MainActivity)
            btnPhone.setOnClickListener(this@MainActivity)
            tvdaftar.setOnClickListener(this@MainActivity)
        }
        auth = Firebase.auth
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .build()
        googleSignInClient = GoogleSignIn.getClient(this, gso)
    }

    override fun onStart() {
        super.onStart()
        val currentUser = auth.currentUser
        if (currentUser != null) {
            val intent = Intent(this@MainActivity, Dashboard::class.java)
            resultLauncher.launch(intent)
        }
    }

    override fun onClick(p0: View?) {
        when (p0?.id) {
            R.id.btnSign -> signIn(
                binding.inputEmail.text.toString(),
                binding.inputPassword.text.toString()
            )

            R.id.tvdaftar -> startActivity(Intent(this, RegisterActivity::class.java))
            R.id.btnGoogle -> signInGoogle()
            R.id.btnPhone -> startActivity(Intent(this, PhoneAuthActivity::class.java))
        }
    }

    private fun signInGoogle() {
        val signInIntent = googleSignInClient.signInIntent
        resultLauncher.launch(signInIntent)
    }

    private fun signIn(email: String, password: String) {
        if (!validateForm()) {
            return
        }

        auth.signInWithEmailAndPassword(email, password).addOnCompleteListener { task ->
            if (task.isSuccessful) {
                startActivity(Intent(this, MainActivity::class.java))
                finish()
            } else {
                Toast.makeText(this, "Authentication failed", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun validateForm(): Boolean {
        var valid = true
        val email = binding.inputEmail.text.toString()
        if (TextUtils.isEmpty(email)) {
            binding.inputEmail.error = "Required."
            valid = false
        } else {
            binding.inputEmail.error = null
        }
        val password = binding.inputPassword.text.toString()
        if (TextUtils.isEmpty(password)) {
            binding.inputPassword.error = "Required."
            valid = false
        } else {
            binding.inputPassword.error = null
        }
        return valid
    }

    private fun firebaseAuthWithGoogle(idToken: String) {
        val credential = GoogleAuthProvider.getCredential(idToken, null)
        val view = binding.mainLayout
        auth.signInWithCredential(credential).addOnCompleteListener { task ->
            if (task.isSuccessful) {
                Snackbar.make(view, "Authentication Success.", Snackbar.LENGTH_SHORT).show()
                startActivity(Intent(this@MainActivity, Dashboard::class.java))
                finish()
            } else {
                Snackbar.make(view, "Authentication Failed.", Snackbar.LENGTH_SHORT).show()
            }
        }
    }
}