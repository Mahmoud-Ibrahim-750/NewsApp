package com.mis.route.newsapp.presentations.ui.splash

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.mis.route.newsapp.databinding.ActivitySplashBinding
import com.mis.route.newsapp.presentations.ui.home.HomeActivity
import java.util.Timer
import java.util.TimerTask

@SuppressLint("CustomSplashScreen")
class SplashActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySplashBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivitySplashBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

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

        Timer().schedule(object : TimerTask() {
            override fun run() {
                val intent = Intent(this@SplashActivity, HomeActivity::class.java)
                startActivity(intent)
                finish()
            }
        }, 2000)
    }
}