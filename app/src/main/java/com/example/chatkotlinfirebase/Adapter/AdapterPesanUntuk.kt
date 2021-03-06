package com.example.chatkotlinfirebase.Adapter

import com.example.chatkotlinfirebase.R
import com.example.chatkotlinfirebase.Model.User
import com.squareup.picasso.Picasso
import com.xwray.groupie.Item
import com.xwray.groupie.ViewHolder
import kotlinx.android.synthetic.main.item_chat_untuk.view.*

class AdapterPesanUntuk(val text:String,val currUser: User) : Item<ViewHolder>(){
    override fun getLayout(): Int {
        return R.layout.item_chat_untuk
    }

    override fun bind(viewHolder: ViewHolder, position: Int) {
        val item = viewHolder.itemView
        item.tv_pesan_untuk.text = text
        Picasso.get().load(currUser.photo).into(item.iv_chat_untuk_photo)

    }

}