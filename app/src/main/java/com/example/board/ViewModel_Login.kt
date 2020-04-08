package com.example.board

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData

class ViewModel_Login(app: Application): AndroidViewModel(app)  {

    private var login_code = MutableLiveData<String>()


    fun set_login_code (code:String){

        login_code.value = code

    }

    fun get_login_code (): MutableLiveData<String> {

        return login_code

    }
}