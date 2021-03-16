package com.zulham.gitroom.ui.following

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.loopj.android.http.AsyncHttpClient
import com.loopj.android.http.AsyncHttpResponseHandler
import com.zulham.gitroom.data.model.ModelFollow
import cz.msebera.android.httpclient.Header
import org.json.JSONArray

class FollowingViewModel: ViewModel() {

    private val listFollowing = MutableLiveData<ArrayList<ModelFollow>>()

    private val isError: MutableLiveData<Boolean> = MutableLiveData(false)

    private val errorMessage = MutableLiveData<String>()

    fun setFollowing(login: String){

        val data = ArrayList<ModelFollow>()

        val client = AsyncHttpClient()

        val url = "https://api.github.com/users/$login/following"

        client.addHeader("Authorization", "Bearer e180388f2600ea71360c486c9b488b17cb78aab6")
        client.addHeader("User-Agent", "request")
        client.get(url, object : AsyncHttpResponseHandler(){
            override fun onSuccess(
                statusCode: Int,
                headers: Array<out Header>?,
                responseBody: ByteArray?
            ) {

                try {
                    val result = responseBody?.let { String(it) }

                    val res = JSONArray(result)

                    for (i in 0 until res.length()){
                        val jsonObj = res.getJSONObject(i)
                        data.add(
                            ModelFollow(
                                id = jsonObj.getInt("id"),
                                login = jsonObj.getString("login"),
                                avatar_url = jsonObj.getString("avatar_url")
                            )
                        )

                    }

                    listFollowing.value = data

                } catch (e: Exception) {
                    e.message?.let { setError(true, it) }
                    e.printStackTrace()
                }
            }

            override fun onFailure(
                statusCode: Int,
                headers: Array<out Header>?,
                responseBody: ByteArray?,
                error: Throwable?
            ) {
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

    fun getFollowing(): LiveData<ArrayList<ModelFollow>> {
        return listFollowing
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