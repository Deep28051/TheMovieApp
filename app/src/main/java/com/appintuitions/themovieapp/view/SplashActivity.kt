package com.appintuitions.themovieapp.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.appintuitions.themovieapp.R
import java.util.*

class SplashActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        Timer().schedule(object : TimerTask(){
            override fun run() {
                intent = Intent(this@SplashActivity,MainActivity::class.java)
                startActivity(intent)
                finishAffinity()
            }

        },2500)

    }

    override fun onPause() {
        super.onPause()
        finishAffinity()
    }
}