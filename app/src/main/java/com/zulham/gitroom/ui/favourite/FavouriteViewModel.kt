package com.zulham.gitroom.ui.favourite

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class FavouriteViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "Whoaahh thanks for attentionn, please wait few time new feature will release  \n \n Regard \n Z|Storm"
    }
    val text: LiveData<String> = _text
}