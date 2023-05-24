package com.example.projectkoramen

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.projectkoramen.databinding.ActivityRegisterBinding

class RegisterActivity : AppCompatActivity() {
    private lateinit var binding: ActivityRegisterBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        intentButtonMasuk()
    }

    private fun intentButtonMasuk() {
        binding.btnMasuk.setOnClickListener {
            startActivity(Intent(this, MainActivity::class.java))
        }
        binding.btnDaftar.setOnClickListener {
            startActivity(Intent(this, MainActivity::class.java))
            Toast.makeText(this, "Berhasil Mendaftar", Toast.LENGTH_SHORT).show()
        }
        binding.btnGoogle2.setOnClickListener {
            val intent = Intent(Intent.ACTION_VIEW)
            intent.data = Uri.parse("http://ittelkom-pwt.ac.id/")
            startActivity(intent)
        }
    }
}