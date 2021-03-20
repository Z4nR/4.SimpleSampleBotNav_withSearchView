package com.zulham.gitroom.ui.favourite

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class FavouriteViewModel : ViewModel() {

    private val tesString = MutableLiveData<String>()

    fun getTesString(): LiveData<String> {
        return tesString
    }

    fun setTesString(tesString: String) {
        this.tesString.postValue(tesString)
    }
}