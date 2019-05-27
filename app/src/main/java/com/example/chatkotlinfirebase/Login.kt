package com.example.chatkotlinfirebase

import android.content.Context
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_login.*

class Login : AppCompatActivity() {

    val firebase = FirebaseAuth.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

initView()

    }

    private fun initView() {
        btn_login.setOnClickListener{
            val email = login_email.text.toString().trim()
            val password = login_password.text.toString()
            firebase.signInWithEmailAndPassword(email,password)
                .addOnCompleteListener{
                    if(it.isSuccessful){
                        Toast.makeText(this,"user berhasil Login", Toast.LENGTH_LONG).show()
                        Home.launchIntent(this)

                    }
                    else{
                        Toast.makeText(this,"user gagal Login", Toast.LENGTH_LONG).show()

                    }

                }
        }


        login_register.setOnClickListener{
            Register.launchIntent(this)
        }
    }

    companion object {
        fun launchIntent(context: Context){
            val intent = Intent(context, Login::class.java)
            context.startActivity(intent)

        }

        fun launchIntentClearTask(context: Context){
            val intent = Intent(context, Login::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK.or(Intent.FLAG_ACTIVITY_CLEAR_TASK)
            context.startActivity(intent)

        }
    }
}
