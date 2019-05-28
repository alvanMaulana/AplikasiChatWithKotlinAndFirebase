package com.example.chatkotlinfirebase.Activity

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.os.Handler
import com.example.chatkotlinfirebase.R

class SplashScreen : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)

        object : CountDownTimer(( 4000), 1000) {
            override fun onFinish() {
                gotoHome()

            }

            override fun onTick(p0: Long) {

            }

        }.start()

    }

    private fun gotoHome() {
        Home.launchIntent(this)
        finish()
    }
}
