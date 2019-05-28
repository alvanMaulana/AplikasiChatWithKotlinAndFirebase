package com.example.chatkotlinfirebase.Activity

import android.content.Context
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import com.example.chatkotlinfirebase.Adapter.AdapterChatHistory
import com.example.chatkotlinfirebase.Model.Message
import com.example.chatkotlinfirebase.Model.User
import com.example.chatkotlinfirebase.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.ViewHolder
import kotlinx.android.synthetic.main.activity_home.*

class Home : AppCompatActivity() {
    val adapter = GroupAdapter<ViewHolder>()
    val pesanMap = HashMap<String?, Message?>()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        FirebaseDatabase.getInstance().setPersistenceEnabled(true)


        adapter.setOnItemClickListener { item, view ->



            val user = item as AdapterChatHistory

            val intent = Intent(view.context, ChatRoomActivity::class.java)
            //jika mau menggukan methode ini harus menggunakan parse label di kelas User
            intent.putExtra(FriendListActivity.FRIEND_KEY, user.dataTeman)
            startActivity(intent)

        }

        checkUserAccountSingnIn()

        loadDataPesan()

        initView()
    }

    private fun initView() {
        fab_home.setOnClickListener{
            FriendListActivity.launchIntent(this)
        }
    }

    private  fun refreshAdapter(){
        adapter.clear()
        pesanMap.values.forEach {
            val mess = it ?:return //error handling like if else
            adapter.add(AdapterChatHistory(mess))

        }

    }

    private fun loadDataPesan() {

        val MyId = FirebaseAuth.getInstance().uid
        val pesanTerakhir = FirebaseDatabase.getInstance().getReference("pesan-terakhir/$MyId")
        pesanTerakhir.keepSynced(true)


        pesanTerakhir.addChildEventListener(object  : ChildEventListener{
            override fun onCancelled(p0: DatabaseError) {

            }

            override fun onChildMoved(p0: DataSnapshot, p1: String?) {
            }

            override fun onChildChanged(p0: DataSnapshot, p1: String?) {

                //jika ingin menampilkan data pada saat di rubah
                val chatMessage = p0.getValue(Message::class.java)
                pesanMap[p0.key] = chatMessage
                refreshAdapter()



            }

            override fun onChildAdded(p0: DataSnapshot, p1: String?) {

                //jika ingin menampilkan data pada saat ditambah
                val chatMessage = p0.getValue(Message::class.java)
                adapter.add(AdapterChatHistory(chatMessage!!))
                refreshAdapter()


            }

            override fun onChildRemoved(p0: DataSnapshot) {
            }


        })
        rv_home_history.adapter = adapter






    }

    private fun fetchUser() {
        val uid = FirebaseAuth.getInstance().uid
        val ref =  FirebaseDatabase.getInstance().getReference("/user/$uid")
        ref.keepSynced(true)
        ref.addValueEventListener(object : ValueEventListener {
            override fun onCancelled(p0: DatabaseError) {

            }

            override fun onDataChange(p0: DataSnapshot) {
                Login.currentUserData = p0.getValue(User::class.java)!!

            }

        })

    }

    private fun checkUserAccountSingnIn() {
        if(FirebaseAuth.getInstance().uid.isNullOrEmpty()){
            Login.launchIntent(this)
        }
        else{ fetchUser()}
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

            val intent = Intent(context, Home::class.java)
            context.startActivity(intent)
        }

    }
}

