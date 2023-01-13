package com.example.videomeeting.userActivitys

import android.content.Intent
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.NonNull
import androidx.appcompat.app.AppCompatActivity
import com.example.videomeeting.R
import com.example.videomeeting.myClass.User
import com.example.videomeeting.network.ApiClient
import com.example.videomeeting.network.ApiService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class OutgoingCall : AppCompatActivity() {
    private lateinit var type: String
    private lateinit var destUser: User
    private lateinit var user: User
    private lateinit var imageViewButtonCancel: ImageView
    private lateinit var textViewType: TextView
    private lateinit var textViewFullName: TextView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_outgoing_call)
        init()
    }
    private fun init() {
        user = (intent.getSerializableExtra("user") as? User)!!
        destUser = (intent.getSerializableExtra("destUser") as? User)!!
        type = (intent.getSerializableExtra("type") as? String)!!
        imageViewButtonCancel = findViewById<ImageView>(R.id.imageViewButtonCancel)
        textViewType = findViewById<TextView>(R.id.textViewType)
        textViewFullName = findViewById<TextView>(R.id.textViewFullName)
        if( type == "Call")
            textViewType.text = "${resources.getString(R.string.Call)}..."
        else
            textViewType.text = "${resources.getString(R.string.VideoCall)}..."
        textViewFullName.text = "${destUser.firstName} ${destUser.lastName}"
        setNewCall()
        buttonCancel()
    }
    private fun setNewCall(){
        if( type == "Call"){
           /* ApiClient.getClient().create(ApiService::class.java).sendRemoteMessage(HashMap(1, 2F),"d").
            enqueue(object : Callback<String?> {
                override fun onResponse(@NonNull call: Call<String?>, @NonNull response: Response<String?>) {
                    if(response.isSuccessful){
                        Toast.makeText(applicationContext, "test1", Toast.LENGTH_SHORT).show()

                    }else{
                        Toast.makeText(applicationContext, response.message(), Toast.LENGTH_SHORT).show()
                        finish()
                    }
                }
                override fun onFailure(@NonNull call: Call<String?>, @NonNull t: Throwable) {

                }
            })*/
        } else{

        }
    }
    private fun buttonCancel() { imageViewButtonCancel.setOnClickListener{ onBackPressed() } }
    override fun onBackPressed() {
        val intent = Intent(this, Home::class.java)
        intent.putExtra("user", user)
        startActivity(intent)
        finish()
    }
}