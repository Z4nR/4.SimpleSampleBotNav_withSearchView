package com.zulham.gitroom.ui.user

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.zulham.gitroom.data.dummyData
import com.zulham.gitroom.data.model.ModelUser

class UserViewModel : ViewModel() {

    private val listUser = MutableLiveData<ArrayList<ModelUser>>()

    fun setData(){
        listUser.postValue(dummyData.list)
    }

    fun getData(): LiveData<ArrayList<ModelUser>>{
        return listUser
    }

    fun searchData(query: String?){
        val searchList = dummyData.list.filter {
            user -> user.username == query
        }

        listUser.postValue(searchList as ArrayList<ModelUser>?)
    }
}