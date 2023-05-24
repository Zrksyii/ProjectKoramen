package com.example.projectkoramen

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.example.projectkoramen.databinding.ActivityMainBinding
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        runBlocking {
            installSplashScreen()
            delay(4000)
        }

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        intentButtonDaftar()
    }

    private fun intentButtonDaftar() {
        binding.btndaftar.setOnClickListener {
            startActivity(Intent(this, RegisterActivity::class.java))
        }
        binding.btnLogin.setOnClickListener {
            startActivity(Intent(this, Dashboard::class.java))
            Toast.makeText(this, "Berhasil Masuk", Toast.LENGTH_SHORT).show()
        }
        binding.btnGoogle.setOnClickListener {
            val intent = Intent(Intent.ACTION_VIEW)
            intent.data = Uri.parse("http://ittelkom-pwt.ac.id/")
            startActivity(intent)
        }
    }
}