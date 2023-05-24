package com.example.projectkoramen

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.projectkoramen.databinding.ActivityUserAccountBinding

class UserAccount : AppCompatActivity() {
    private lateinit var binding: ActivityUserAccountBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUserAccountBinding.inflate(layoutInflater)
        setContentView(binding.root)

        intentButtonUserAccount()
    }

    private fun intentButtonUserAccount() {
        binding.btnBack1.setOnClickListener{
            startActivity(Intent(this, Dashboard::class.java))
        }
        binding.iconlogout.setOnClickListener {
            startActivity(Intent(this, MainActivity::class.java))
        }
        binding.instagram.setOnClickListener {
            val intent = Intent(Intent.ACTION_VIEW)
            intent.data = Uri.parse("http://instagram.com/zrksyii_")
            startActivity(intent)
        }
        binding.tiktok.setOnClickListener {
            val intent = Intent(Intent.ACTION_VIEW)
            intent.data = Uri.parse("http://tiktok.com")
            startActivity(intent)
        }
        binding.twitter.setOnClickListener {
            val intent = Intent(Intent.ACTION_VIEW)
            intent.data = Uri.parse("http://twitter.com")
            startActivity(intent)
        }
        binding.youtube.setOnClickListener {
            val intent = Intent(Intent.ACTION_VIEW)
            intent.data = Uri.parse("http://youtube.com")
            startActivity(intent)
        }
        binding.email.setOnClickListener {
            val intent = Intent(Intent.ACTION_VIEW)
            intent.data = Uri.parse("mailto:akmalzarkasyi76@gmail.com")
            startActivity(intent)
        }
        binding.whatsapp.setOnClickListener {
            val intent = Intent(Intent.ACTION_VIEW)
            intent.data = Uri.parse("https://wa.me/62859148412757")
            startActivity(intent)
        }
    }
}