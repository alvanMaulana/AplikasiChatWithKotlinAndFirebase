package com.example.chatkotlinfirebase

import android.view.View
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.squareup.picasso.Picasso
import com.xwray.groupie.Item
import com.xwray.groupie.ViewHolder
import kotlinx.android.synthetic.main.item_chat_history.view.*

class AdapterChatHistory(val chat: Message) : Item<ViewHolder>(){

    var dataTeman :User? = null
    override fun getLayout(): Int {
        return  R.layout.item_chat_history

    }

    override fun bind(viewHolder: ViewHolder, position: Int) {

        val item = viewHolder.itemView
        var idTeman :String


        var uid = FirebaseAuth.getInstance().uid
        if(uid == chat.fromId){
            idTeman = chat.toId
        }
        else{
            idTeman = chat.fromId
        }

        //ambil semua data teman dari uid

        val userData = FirebaseDatabase.getInstance().getReference("/user/$idTeman")
        userData.addValueEventListener( object  :ValueEventListener{
            override fun onCancelled(p0: DatabaseError) {

            }

            override fun onDataChange(p0: DataSnapshot) {
                dataTeman = p0.getValue(User::class.java)
                Picasso.get().load(dataTeman!!.photo).into(item.iv_chat_history)
                item.tv_chat_history_username.text = dataTeman!!.name

            }

        })






        item.tv_chat_history_lastest_message.text = chat.text


    }
}