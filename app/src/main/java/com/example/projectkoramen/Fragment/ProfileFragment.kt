package com.example.projectkoramen.Fragment

import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Parcelable
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import com.example.projectkoramen.Dashboard
import com.example.projectkoramen.MainActivity
import com.example.projectkoramen.R
import com.example.projectkoramen.SettingPreferenceActivity
import com.example.projectkoramen.data.SettingModel
import com.example.projectkoramen.databinding.FragmentProfileBinding
import com.example.projectkoramen.db.SettingPreference
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class ProfileFragment : Fragment(), View.OnClickListener {

    private lateinit var binding: FragmentProfileBinding
    private lateinit var settingModel: SettingModel
    private lateinit var mSettingPreference: SettingPreference
    private lateinit var auth: FirebaseAuth
    private lateinit var googleSignInClient: GoogleSignInClient

    private val resultLauncher: ActivityResultLauncher<Intent> =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.data != null && result.resultCode == SettingPreferenceActivity.RESULT_CODE) {
                settingModel =
                    result.data?.parcelable<SettingModel>(SettingPreferenceActivity.EXTRA_RESULT) as SettingModel
                populateView(settingModel)
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        auth = Firebase.auth

        val currentUser = auth.currentUser
        if (currentUser == null) {
            startActivity(Intent(requireActivity(), MainActivity::class.java))
            requireActivity().finish()
        }
        mSettingPreference = SettingPreference(requireContext())
    }

    override fun onClick(p0: View?) {
        when (p0?.id) {
            R.id.btnSignOut -> signOut()
            R.id.btnEmailVerify -> sendEmailVerification()
            R.id.btnSetting -> {
                val intent = Intent(requireContext(), SettingPreferenceActivity::class.java)
                intent.putExtra("SETTING", settingModel)
                resultLauncher.launch(intent)
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        binding = FragmentProfileBinding.inflate(inflater, container, false)
        intentButtonProfileFragment()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.apply {
            btnSignOut.setOnClickListener(this@ProfileFragment)
            btnEmailVerify.setOnClickListener(this@ProfileFragment)
            binding.btnSetting.setOnClickListener(this@ProfileFragment)
        }
        showExistingPreferences()

        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id)).requestEmail().build()
        googleSignInClient = GoogleSignIn.getClient(requireContext(), gso)
    }

    override fun onDestroyView() {
        super.onDestroyView()
    }

    private fun updateUI(currentUser: FirebaseUser) {
        binding.btnEmailVerify.isVisible = false
        currentUser.let {
            val name = it.displayName
            val phoneNumber = it.phoneNumber
            val email = it.email
            val photoUrl = it.photoUrl
            val emailVerified = it.isEmailVerified
            val uid = it.uid
            binding.tvName.text = name
            if (TextUtils.isEmpty(name)) {
                binding.tvName.text = "No_Name"
            }
            binding.tvUserId.text = email
            for (profile in it.providerData) {
                val providerId = profile.providerId
                if (providerId == "password" && !emailVerified) {
                    binding.btnEmailVerify.isVisible = true
                }
                if (providerId == "phone") {
                    binding.tvName.text = phoneNumber
                    binding.tvUserId.text = providerId
                }
            }
        }
    }

    override fun onStart() {
        super.onStart()
        val currentUser = auth.currentUser
        if (currentUser == null) {
            requireActivity().startActivity(Intent(requireActivity(), Dashboard::class.java))
            requireActivity().finish()
        } else {
            updateUI(currentUser)
        }
    }

    private inline fun <reified T : Parcelable> Intent.parcelable(key: String): T? = when {
        Build.VERSION.SDK_INT >= 33 -> getParcelableExtra(key, T::class.java)
        else -> @Suppress("DEPRECATION") getParcelableExtra(key) as? T
    }

    private fun showExistingPreferences() {
        settingModel = mSettingPreference.getSetting()
        populateView(settingModel)
    }

    private fun populateView(settingModel: SettingModel) {
        with(binding) {
            tvName.text = settingModel.name.toString().ifEmpty { getString(R.string.empty_message) }
            tvPhone.text =
                settingModel.phoneNumber.toString().ifEmpty { getString(R.string.empty_message) }
        }
    }

    private fun signOut() {
        auth.signOut()
        val currentUser = auth.currentUser
        if (currentUser == null) {
            startActivity(Intent(requireActivity(), MainActivity::class.java))
            requireActivity().finish()
        }
        googleSignInClient.signOut().addOnCompleteListener(requireActivity()) {}
    }

    private fun sendEmailVerification() {
        binding.btnEmailVerify.isEnabled = false
        val user = auth.currentUser
        user?.sendEmailVerification()?.addOnCompleteListener { task ->
            binding.btnEmailVerify.isEnabled = true
            if (task.isSuccessful) {
                Toast.makeText(
                    requireContext(),
                    "Verification email sent to ${user.email} ",
                    Toast.LENGTH_SHORT
                ).show()
            } else {
                Toast.makeText(
                    requireContext(), "Failed to send verification email.", Toast.LENGTH_SHORT
                ).show()
            }
        }
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
    }
}