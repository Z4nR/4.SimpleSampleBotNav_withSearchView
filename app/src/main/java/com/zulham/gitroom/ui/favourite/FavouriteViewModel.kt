package com.zulham.gitroom.ui.favourite

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class FavouriteViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "Sorry, this feature will update soon, please stay tune. \n \n Regard \n Z|Storm"
    }
    val text: LiveData<String> = _text
}