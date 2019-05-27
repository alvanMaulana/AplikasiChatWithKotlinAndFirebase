package com.example.chatkotlinfirebase

import com.xwray.groupie.Item
import com.xwray.groupie.ViewHolder

class AdapterPesanUntuk : Item<ViewHolder>(){
    override fun getLayout(): Int {
        return R.layout.item_chat_untuk
    }

    override fun bind(viewHolder: ViewHolder, position: Int) {
    }

}