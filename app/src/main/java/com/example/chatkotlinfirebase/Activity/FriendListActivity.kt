package com.example.chatkotlinfirebase.Activity

import android.content.Context
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.example.chatkotlinfirebase.Adapter.AdapterFriendList
import com.example.chatkotlinfirebase.Model.User
import com.example.chatkotlinfirebase.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.ViewHolder
import kotlinx.android.synthetic.main.activity_friend_list.*

class FriendListActivity : AppCompatActivity() {

    val adapter = GroupAdapter<ViewHolder>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_friend_list)

        fetchUser()






    }

    private fun fetchUser() {
        val fireDb = FirebaseDatabase.getInstance().getReference("/user/")
        fireDb.addListenerForSingleValueEvent(object : ValueEventListener{
            override fun onCancelled(p0: DatabaseError) {
            }
//mengambil data
            override fun onDataChange(p0: DataSnapshot) {
                p0.children.forEach{
                    val user = it.getValue(User::class.java) as User //bawa data dengan model



                    //untuk menampilkan user selain kita
                    if(user != null){
                        if(user.uid != FirebaseAuth.getInstance().uid){
                            adapter.add(AdapterFriendList(user))
                        }

                    }

                    // pindah ke chat room

                    adapter.setOnItemClickListener { item, view ->

                        val friendItem = item as AdapterFriendList

                        val intent = Intent(view.context, ChatRoomActivity::class.java)
                        //jika mau menggukan methode ini harus menggunakan parse label di kelas User
                        intent.putExtra(FRIEND_KEY,friendItem.user)
                        startActivity(intent)


                    }



                }
    rv_friend_list.adapter = adapter
            }

        })
    }

    companion object {

        val FRIEND_KEY = "friend_key "
        fun launchIntent(context: Context){
            val intent = Intent(context, FriendListActivity::class.java)
            context.startActivity(intent)
        }
    }
}
