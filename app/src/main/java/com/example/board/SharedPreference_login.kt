package com.example.board

import android.content.Context

class SharedPreference_login(context: Context)  {

    private val pref = context.getSharedPreferences("Total_login", Context.MODE_PRIVATE)

    val editor    = pref.edit()


    fun save_user_id(id:String){

         editor.putString("id",id).apply()

    }

    fun get_user_id():String?{

        return pref.getString("id","0")


    }


    fun save_name(name: String) {

        editor.putString("name", name).apply()

    }

    //xml 輸出

    fun get_name(): String? {
        return pref.getString("name", "未登入")

    }


    fun save_apitoken(apitoken: String) {

        editor.putString("apitoken", apitoken).apply()

    }


    //xml 輸出

    fun get_apitoken(): String? {
        return pref.getString("apitoken", "0")

    }



    fun delete() {

        editor.clear().commit()
    }


}