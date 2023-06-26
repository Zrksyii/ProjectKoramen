package com.example.projectkoramen

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.os.Parcelable
import android.text.TextUtils
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.projectkoramen.data.SettingModel
import com.example.projectkoramen.databinding.ActivitySettingPreferenceBinding
import com.example.projectkoramen.db.SettingPreference

class SettingPreferenceActivity : AppCompatActivity(), View.OnClickListener {
    private lateinit var binding: ActivitySettingPreferenceBinding
    private lateinit var settingModel: SettingModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySettingPreferenceBinding.inflate(layoutInflater)
        setContentView(binding.root)

        settingModel = intent.parcelable<SettingModel>("SETTING") as SettingModel
        binding.btnSave.setOnClickListener(this)
        showPreferencesInForm()

        supportActionBar?.apply {
            title = getString(R.string.setting_page)
            setDisplayHomeAsUpEnabled(true)
        }
    }

    override fun onClick(p0: View?) {
        if (p0?.id == R.id.btn_save) {
            binding.apply {
                val name = edtName.text.toString().trim()
                val phoneNo = edtPhone.text.toString().trim()


                if (name.isEmpty()) {
                    edtName.error = getString(R.string.field_required)
                    return
                }

                if (phoneNo.isEmpty()) {
                    edtPhone.error = getString(R.string.field_required)
                    return
                }

                if (!TextUtils.isDigitsOnly(phoneNo)) {
                    edtPhone.error = getString(R.string.field_digit_only)
                    return
                }

                saveSetting(name, phoneNo)

                val resultIntent = Intent()
                resultIntent.putExtra(EXTRA_RESULT, settingModel)
                setResult(RESULT_CODE, resultIntent)

                finish()
            }
        }
    }


    private inline fun <reified T : Parcelable> Intent.parcelable(key: String): T? = when {
        Build.VERSION.SDK_INT >= 33 -> getParcelableExtra(key, T::class.java)
        else -> @Suppress("DEPRECATION") getParcelableExtra(key) as? T
    }

    private fun showPreferencesInForm() {
        binding.apply {
            edtName.setText(settingModel.name)
            edtPhone.setText(settingModel.phoneNumber)
        }
    }

    private fun saveSetting(
        name: String,
        phoneNo: String,
    ) {
        val settingPreference = SettingPreference(this)
        settingModel.name = name
        settingModel.phoneNumber = phoneNo
        settingPreference.setSetting(settingModel)
        Toast.makeText(this, "Data tersimpan", Toast.LENGTH_SHORT).show()
    }

    companion object {
        const val EXTRA_RESULT = "extra_result"
        const val RESULT_CODE = 101
    }
}