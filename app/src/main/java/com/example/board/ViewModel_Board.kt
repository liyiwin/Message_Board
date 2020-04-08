package com.example.board

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData

class ViewModel_Board(app: Application): AndroidViewModel(app)  {

    val essay_list = MutableLiveData<MutableList<Article_fundation_data>>()

    val message_list =   MutableLiveData<MutableList<Message_fundation_data>>()

    val reply_list =  MutableLiveData<MutableList<Reply_fundation_data>>()
    
    val add_essay_code = MutableLiveData<String>()

    fun set_essay_list (list:MutableList<Article_fundation_data>) {

        essay_list.value = list

    }

    fun get_essay_list ():MutableLiveData<MutableList<Article_fundation_data>>{

        return essay_list

    }

    fun set_message_list (list:MutableList<Message_fundation_data>) {

        message_list.value = list

    }

    fun get_message_list ():MutableLiveData<MutableList<Message_fundation_data>>{

        return message_list

    }

    fun set_reply_list (list:MutableList<Reply_fundation_data>) {

        reply_list.value = list

    }

    fun get_reply_list ():MutableLiveData<MutableList<Reply_fundation_data>>{

        return reply_list

    }

    fun set_add_essay_code(code:String){

        add_essay_code.value = code
    }

    fun get_add_essay_code():MutableLiveData<String>{

        return add_essay_code

    }

}