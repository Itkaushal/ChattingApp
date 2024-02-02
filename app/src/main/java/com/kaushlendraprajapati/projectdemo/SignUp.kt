package com.kaushlendraprajapati.projectdemo

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.kaushlendraprajapati.projectdemo.Modal.User
import com.kaushlendraprajapati.projectdemo.databinding.ActivitySignUpBinding

class SignUp : AppCompatActivity() {

    private lateinit var mAuth: FirebaseAuth
    var signUpBinding: ActivitySignUpBinding? = null
    private lateinit var mDbref: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        signUpBinding=ActivitySignUpBinding.inflate(layoutInflater)
        setContentView(signUpBinding!!.root)

        supportActionBar?.hide()
        mAuth = FirebaseAuth.getInstance()

        signUpBinding!!.signupRegister.setOnClickListener {

            val  name = signUpBinding!!.Name.text.toString()
            val  email = signUpBinding!!.emailRegiter.text.toString()
            val  password = signUpBinding!!.passwordRegister.text.toString()


            if(name!=null && !name.isEmpty() || email!=null && !email.isEmpty() || password!=null && !password.isEmpty())
            {
                signUp(name,email,password)
            }
            else{
                Toast.makeText(this,"enter user detail is null",Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun signUp( name: String, email: String, password: String ) {

        //creating new user..........
        mAuth.createUserWithEmailAndPassword(email,password)
            .addOnCompleteListener(this){ task ->
                if (task.isSuccessful)

                {
                    addUserInDatabase(name,email,mAuth.currentUser?.uid!!)
                    val intent = Intent(this,LoginActivity::class.java)
                    finish()
                    startActivity(intent)
                    Toast.makeText(this@SignUp,"user created...",Toast.LENGTH_SHORT).show()
                }
                else{
                    Toast.makeText(this@SignUp,"some error occur!",Toast.LENGTH_SHORT).show()
                }
            }

    }

    private fun addUserInDatabase(name: String, email: String, uid: String) {
        mDbref=FirebaseDatabase.getInstance().reference
        mDbref.child("user").child(uid).setValue(User(name,email,uid))
    }
}