package com.mis.route.newsapp.presentations.ui.splash

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.content.res.Configuration
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import com.mis.route.newsapp.Constants
import com.mis.route.newsapp.databinding.ActivitySplashBinding
import com.mis.route.newsapp.presentations.ui.home.HomeActivity
import java.util.Locale
import java.util.Timer
import java.util.TimerTask

@SuppressLint("CustomSplashScreen")
class SplashActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySplashBinding
    private lateinit var settingsSP: SharedPreferences
    private val timer = Timer()

    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivitySplashBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        settingsSP = getSharedPreferences(Constants.SP_SETTINGS_FILE_NAME, Context.MODE_PRIVATE)

//        // get a view model instance
//        val viewModel = ViewModelProvider(this)[SplashViewModel::class.java]
//
//        // update UI when view model notifies
//        viewModel.launchHomeActivity.observe(this) {
//            Toast.makeText(this, "here", Toast.LENGTH_SHORT).show()
//            val intent = Intent(this, HomeActivity::class.java)
//            startActivity(intent)
//            finish()
//        }

        changeLanguageAsNeeded()

        timer.schedule(object : TimerTask() {
            override fun run() {
                startActivity(Intent(this@SplashActivity, HomeActivity::class.java))
                finish()
            }
        }, 2000)
    }

    override fun onDestroy() {
        super.onDestroy()
        timer.cancel()
    }

    private fun getCurrentLanguageCode(): String {
        return resources.configuration.locales[0].language
    }

    private fun changeLanguageAsNeeded() {
        val savedLanguageCode =
            settingsSP.getString(Constants.PREF_LANGUAGE_KEY, Constants.ENGLISH_CODE)
        if (getCurrentLanguageCode() != savedLanguageCode) {
            setLocale(savedLanguageCode!!)
            ActivityCompat.recreate(this)
        }
    }

    private fun setLocale(languageCode: String) {
        val locale = Locale(languageCode)
        Locale.setDefault(locale)
        val config = Configuration()
        config.setLocale(locale)
        @Suppress("DEPRECATION")
        baseContext.resources.updateConfiguration(config, baseContext.resources.displayMetrics)
    }
}
