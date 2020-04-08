package com.example.board

import android.app.Activity
import okhttp3.*
import org.json.JSONObject
import java.io.IOException

private var OkHttpClient = okhttp3.OkHttpClient().newBuilder().build()

fun register_my_account(activity: Activity, name:String, password:String,viewModel:ViewModel_Register){


     val url = "https://2c0f8055.ngrok.io/api/signup"
    val body = FormBody.Builder().add("name",name).add("password",password).build()
     val request = Request.Builder().url(url).post(body) .build()
    val call = OkHttpClient .newCall(request)
     call.enqueue(object: Callback {
     override fun onFailure(call: Call, e: IOException) {

                       }

     override fun onResponse(call: Call, response: Response) {

       val code = response.code()

       if (code == 200){

           activity.runOnUiThread {

             viewModel.set_register_code("200")



           }



       }

     }
    })


}