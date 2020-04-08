package com.example.board

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData

class ViewModel_Register(app: Application): AndroidViewModel(app) {

    private var register_code = MutableLiveData<String>()


    fun set_register_code (code:String){

        register_code.value = code

    }


    fun get_register_code ():MutableLiveData<String>{

      return register_code

    }






}