package com.example.chatkotlinfirebase

import android.content.Context
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
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

                        adapter.add(AdapterFriendList(user))

                    // pindah ke chat room

                    adapter.setOnItemClickListener { item, view ->
                        ChatRoomActivity.launchIntent(view.context)

                    }



                }
    rv_friend_list.adapter = adapter
            }

        })
    }

    companion object {
        fun launchIntent(context: Context){
            val intent = Intent(context,FriendListActivity::class.java)
            context.startActivity(intent)
        }
    }
}
