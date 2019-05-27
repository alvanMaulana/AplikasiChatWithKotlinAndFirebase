package com.example.chatkotlinfirebase

import android.content.Context
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.ViewHolder
import kotlinx.android.synthetic.main.activity_chat_room.*

class ChatRoomActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chat_room)

        val adapter = GroupAdapter<ViewHolder>()
        adapter.add(AdapterPesanDari())
        adapter.add(AdapterPesanUntuk())
        adapter.add(AdapterPesanDari())
        adapter.add(AdapterPesanUntuk())
        adapter.add(AdapterPesanDari())

        rv_chat_room_list.adapter = adapter
    }

    companion object {
        fun launchIntent(context: Context){
            val intent = Intent(context,ChatRoomActivity::class.java)
            context.startActivity(intent)

        }
    }
}
