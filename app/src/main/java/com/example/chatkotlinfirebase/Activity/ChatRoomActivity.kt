package com.example.chatkotlinfirebase.Activity

import android.content.Context
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.example.chatkotlinfirebase.Adapter.AdapterPesanDari
import com.example.chatkotlinfirebase.Adapter.AdapterPesanUntuk
import com.example.chatkotlinfirebase.Model.Message
import com.example.chatkotlinfirebase.Model.User
import com.example.chatkotlinfirebase.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.ChildEventListener
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.ViewHolder
import kotlinx.android.synthetic.main.activity_chat_room.*

class ChatRoomActivity : AppCompatActivity() {
    lateinit var friend : User //data tidak nul tapi datanya terlambat
    val adapter = GroupAdapter<ViewHolder>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chat_room)



        //mengambil data parcelable yang dikirimkan
        friend = intent.getParcelableExtra<User>(FriendListActivity.FRIEND_KEY)
        supportActionBar!!.title = friend.name


        rv_chat_room_list.adapter = adapter

        initView()
        loadMessageFromFirebase()
    }

    private fun loadMessageFromFirebase() {
        val MyId = Login.currentUserData.uid
        val FriendId = friend.uid
        val reference = FirebaseDatabase.getInstance().getReference("/message-user/$MyId/$FriendId")

        reference.addChildEventListener(object :ChildEventListener{
            override fun onCancelled(p0: DatabaseError) {

            }

            override fun onChildMoved(p0: DataSnapshot, p1: String?) {
            }

            override fun onChildChanged(p0: DataSnapshot, p1: String?) {
            }

            override fun onChildAdded(p0: DataSnapshot, p1: String?) {
                val messageCollection = p0.getValue(Message::class.java)
                if(messageCollection != null){

                    if(messageCollection.fromId == FirebaseAuth.getInstance().uid)
                    {
                        adapter.add(
                            AdapterPesanUntuk(
                                messageCollection.text,
                                Login.currentUserData
                            )
                        )

                        //disini tambahkan foto kita
                    }
                    else {

                        adapter.add(
                            AdapterPesanDari(
                                messageCollection.text,
                                friend
                            )
                        )
                        //disini tambahkan foto orang lain

                    }
                }

            }

            override fun onChildRemoved(p0: DataSnapshot) {
            }


        })
    }

    private fun initView() {
        btn_chat_room_send_message.setOnClickListener{

            sendMessage()

        }

    }

    private fun sendMessage() {
        //disini pesan akan diolah untuk dikirim


        //kirim pesan ke user yang harus dikirimkan


        val fromId = FirebaseAuth.getInstance().uid.toString()
        val toId = friend.uid

        //bikin folder user to user sendiri

        val messageDbReference = FirebaseDatabase.getInstance().getReference("/message-user/$fromId/$toId").push()
        val messageDbReferenceTo = FirebaseDatabase.getInstance().getReference("/message-user/$toId/$fromId").push()

        val id = messageDbReference.key.toString()
        val pesan = et_chat_room_chat_message.text.toString()
        val time = System.currentTimeMillis()/1000




        messageDbReference.setValue(
            Message(id, fromId, toId, pesan, time)
        )
            .addOnSuccessListener {
//                Toast.makeText(this,"berhasil mengirim pesan", Toast.LENGTH_LONG).show()
                et_chat_room_chat_message.text.clear()
                rv_chat_room_list.smoothScrollToPosition(adapter.itemCount-1)

                messageDbReferenceTo.setValue(
                    Message(id, fromId, toId, pesan, time)
                )
            }

        //menyimpan ke dua database masing masing user

        val  pesanTerakhir = FirebaseDatabase.getInstance().getReference("pesan-terakhir/$fromId/$toId")
        val  pesanTerakhirUntuk = FirebaseDatabase.getInstance().getReference("pesan-terakhir/$toId/$fromId")


        pesanTerakhir.setValue(Message(id, fromId, toId, pesan, time))
        pesanTerakhirUntuk.setValue(Message(id, fromId, toId, pesan, time))
    }

    companion object {
        fun launchIntent(context: Context){
            val intent = Intent(context, ChatRoomActivity::class.java)
            context.startActivity(intent)

        }
    }
}
