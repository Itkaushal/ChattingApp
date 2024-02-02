package com.kaushlendraprajapati.projectdemo

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.kaushlendraprajapati.projectdemo.databinding.ActivitySplashScreenBinding

class SplashScreen : AppCompatActivity() {
    private lateinit var mAuth: FirebaseAuth
    var splashScreenBinding:ActivitySplashScreenBinding?=null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        splashScreenBinding=ActivitySplashScreenBinding.inflate(layoutInflater)
        setContentView(splashScreenBinding!!.root)
        supportActionBar?.hide()
        mAuth=FirebaseAuth.getInstance()
        val user: FirebaseUser? =mAuth.currentUser

            Handler().postDelayed({
                if (user!=null)
                {
                    val intent = Intent(this@SplashScreen,MainActivity::class.java)
                    startActivity(intent)
                    finish()
                }
                else
                {
                    val intent = Intent(this@SplashScreen, LoginActivity::class.java)
                    startActivity(intent)
                    finish()
                }

            }, 2000)
    }
}