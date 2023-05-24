package com.example.projectkoramen.Fragment

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.projectkoramen.MainActivity
import com.example.projectkoramen.databinding.FragmentProfileBinding

class ProfileFragment : Fragment() {

    private lateinit var binding: FragmentProfileBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentProfileBinding.inflate(inflater, container, false)
        intentButtonProfileFragment()
        return binding.root
    }

    private fun intentButtonProfileFragment() {
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
        binding.iconlogout.setOnClickListener {
            val intent = Intent (activity, MainActivity::class.java)
            Toast.makeText(context,"LogOut Berhasil", Toast.LENGTH_LONG).show()
            activity?.startActivity(intent)
        }
    }
}