package com.example.chatkotlinfirebase

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import kotlinx.android.synthetic.main.activity_register.*
import java.util.*

class Register : AppCompatActivity() {

    val auth = FirebaseAuth.getInstance()


    val PICK_PHOTO = 100
    val PICK_CAMERA = 101
    var PHOTO_URI : Uri? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        initView()
    }

    private fun initView() {
        btn_register.setOnClickListener {
            registerUserToFireBase()
        }

            iv_register_pp.setOnClickListener{
                getPhotoFromPhone()
            }


    }

    private fun getPhotoFromPhone() {
        val intent = Intent(Intent.ACTION_PICK)
        intent.type="image/*"
        startActivityForResult(intent,PICK_PHOTO)



    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if(requestCode == PICK_PHOTO){
            if (resultCode == Activity.RESULT_OK && data!!.data !=null){


                PHOTO_URI  = data.data
                val bitmap = MediaStore.Images.Media.getBitmap(contentResolver,PHOTO_URI)
                iv_register_pp.setImageBitmap(bitmap)

            }
        }
    }

    private fun registerUserToFireBase() {
        auth.createUserWithEmailAndPassword(txt_email.text.toString(),txt_password.text.toString())
            .addOnCompleteListener{
                if(it.isSuccessful){
                    Toast.makeText(this,"user berhasil dibuat",Toast.LENGTH_LONG).show()


                    //jika email dan passwordnya behasil disimpan
                    //maka upload foto

                    uploadPhotoToFirebase()
                }
                else{
                    Toast.makeText(this,"Gagak",Toast.LENGTH_LONG).show()

                }

            }
            .addOnFailureListener{
                Toast.makeText(this,"Gagal",Toast.LENGTH_LONG).show()

            }
    }

    private fun uploadPhotoToFirebase() {
        val photoName = UUID.randomUUID().toString()
        val uploadFirebase = FirebaseStorage.getInstance().getReference("rap/images/$photoName")


        uploadFirebase.putFile(PHOTO_URI!!)
            .addOnSuccessListener {
                Home.launchIntent(this)

                uploadFirebase.downloadUrl.addOnSuccessListener {

                    Toast.makeText(this,"$it",Toast.LENGTH_LONG).show()
                    //Simpan semua data ke dalam database

                    saveAllDatatoDatabase(it.toString())

                }

            }


    }

    private fun saveAllDatatoDatabase(photoUrl: String) {
        val uid = FirebaseAuth.getInstance().uid
        val db = FirebaseDatabase.getInstance().getReference("user/$uid")
        db.setValue(
            User(
                txt_nama.text.toString(),
                photoUrl,
                txt_email.text.toString()
            )
        )
            .addOnSuccessListener{
                Toast.makeText(this,"Sukses disimpan ke database",Toast.LENGTH_LONG).show()
                Home.launchIntent(this)



            }
            .addOnFailureListener{

            }
    }

    companion object {
        fun launchIntent(context: Context){
            val intent = Intent(context,Register::class.java)
            context.startActivity(intent)
        }
    }
}
