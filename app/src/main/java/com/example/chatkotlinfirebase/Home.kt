package com.example.chatkotlinfirebase

import android.content.Context
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import com.google.firebase.auth.FirebaseAuth
import java.security.AccessControlContext

class Home : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        checkUserAccountSingnIn()
    }

    private fun checkUserAccountSingnIn() {
        if(FirebaseAuth.getInstance().uid.isNullOrEmpty()){
            Login.launchIntent(this)

        }
    }

    //buat menu
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.nav_menu_home,menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when(item!!.itemId){
            R.id.nav_sign_out ->{
                signOutUser()

            }

            R.id.nav_message ->{
                FriendListActivity.launchIntent(this)

            }


        }
        return super.onOptionsItemSelected(item)
    }

    private fun signOutUser() {

        FirebaseAuth.getInstance().signOut() // untuk menandai bahwa user sudah signout
        Login.launchIntentClearTask(this)
    }

    companion object {
          fun  launchIntent(context: Context){

            val intent = Intent(context,Home::class.java)
            context.startActivity(intent)
        }
    }
}

