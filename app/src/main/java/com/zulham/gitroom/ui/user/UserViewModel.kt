package com.zulham.gitroom.ui.user

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.loopj.android.http.AsyncHttpClient
import com.loopj.android.http.AsyncHttpResponseHandler
import com.zulham.gitroom.data.model.ModelUser
import cz.msebera.android.httpclient.Header
import org.json.JSONObject

class UserViewModel : ViewModel() {

    private val listUsers = MutableLiveData<ArrayList<ModelUser>>()

    private val isError = MutableLiveData<Boolean>()

    private val errorMessage = MutableLiveData<String>()

    fun setData(username: String){

        isError.value = false

        val listUser = ArrayList<ModelUser>()

        val client = AsyncHttpClient()

        val url = "https://api.github.com/search/users?q=$username"

        client.addHeader("Authorization", "token 5c88782c474849c7af4212f1cde234b288d65864")
        client.addHeader("User-Agent", "request")
        client.get(url, object : AsyncHttpResponseHandler(){
            override fun onSuccess(statusCode: Int, headers: Array<Header>, responseBody: ByteArray) {
                val result = String(responseBody)

                try {
                    val responseObject = JSONObject(result)
                    val items = responseObject.getJSONArray("items")

                    for (i in 0 until items.length()){
                        val jsonObj = items.getJSONObject(i)
                        listUser.add(
                                ModelUser(
                                        id = jsonObj.getInt("id"),
                                        login = jsonObj.getString("login"),
                                        avatar_url = jsonObj.getString("avatar_url"),
                                        url = jsonObj.getString("url")
                                )
                        )

                    }

                    listUsers.value = listUser


                } catch (e: Exception) {
                    e.message?.let { setError(true, it) }
                    e.printStackTrace()
                }
            }

            override fun onFailure(statusCode: Int, headers: Array<Header>, responseBody: ByteArray?, error: Throwable?) {
                val errorMessages =
                        when (statusCode) {
                            401 -> "$statusCode : Bad Request"
                            403 -> "$statusCode : Forbidden"
                            404 -> "$statusCode : Not Found"
                            else -> "$statusCode : ${error?.message}"
                        }
                isError.value = true
                errorMessage.value = errorMessages
            }

        })
    }

    fun getData(): LiveData<ArrayList<ModelUser>>{
        return listUsers
    }

    fun setError(error: Boolean, message: String) {
        isError.value = error
        errorMessage.value = message
    }

    fun getIsError(): LiveData<Boolean> {
        return isError
    }

    fun getErrorMessage(): LiveData<String> {
        return errorMessage
    }

}