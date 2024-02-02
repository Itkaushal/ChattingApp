package com.kaushlendraprajapati.projectdemo

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.kaushlendraprajapati.projectdemo.databinding.ActivityLoginBinding

class LoginActivity : AppCompatActivity() {

    var loginBinding: ActivityLoginBinding? = null
    private lateinit var mAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        loginBinding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(loginBinding!!.root)
        supportActionBar?.hide()
        mAuth = FirebaseAuth.getInstance()

        loginBinding!!.SignUpButton.setOnClickListener {
            val intent = Intent(this,SignUp::class.java)
            startActivity(intent)
        }
        loginBinding!!.LoginButton.setOnClickListener {

            val email = loginBinding!!.Email.text.toString()
            val password = loginBinding!!.Password.text.toString()
            if (email!=null && !email.isEmpty() || password!=null && !password.isEmpty())
            {
                login(email,password);
            }
            else{
                Toast.makeText(this,"user invalid!",Toast.LENGTH_SHORT).show()
            }
        }


    }

    private fun login(email: String, password: String) {

            mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this) { task ->
                    if (task.isSuccessful) {
                        val intent = Intent(this, MainActivity::class.java)
                        startActivity(intent)
                        finish()
                        Toast.makeText(this@LoginActivity,"user login success",Toast.LENGTH_SHORT).show()
                    } else {
                        Toast.makeText(
                            this@LoginActivity, "user does not exist!", Toast.LENGTH_SHORT).show()

                    }
                }

    }
}