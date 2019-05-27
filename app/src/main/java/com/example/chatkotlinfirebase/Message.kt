package com.example.chatkotlinfirebase

class Message(var id:String,var fromId :String, var toId :String,var text :String, var time :Long) {


    constructor():this("","","","",-1)
}