package com.appintuitions.rvkotlin.view.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import com.appintuitions.rvkotlin.R
import com.appintuitions.rvkotlin.view.MainActivity
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