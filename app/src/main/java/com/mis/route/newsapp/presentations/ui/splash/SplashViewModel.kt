package com.mis.route.newsapp.presentations.ui.splash

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import java.util.Timer
import java.util.TimerTask

class SplashViewModel : ViewModel() {
    val launchHomeActivity = MutableLiveData(false)

    init {
        setTimer()
    }

    private fun setTimer() {
        Timer().schedule(object : TimerTask() {
            override fun run() {
                launchHomeActivity.postValue(true)
            }
        }, 3000)
    }

}