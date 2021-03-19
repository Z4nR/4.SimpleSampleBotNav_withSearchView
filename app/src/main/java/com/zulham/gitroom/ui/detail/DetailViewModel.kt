package com.zulham.gitroom.ui.detail

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.loopj.android.http.AsyncHttpClient
import com.loopj.android.http.AsyncHttpResponseHandler
import com.zulham.gitroom.data.model.ModelUserDetail
import cz.msebera.android.httpclient.Header
import org.json.JSONObject

class DetailViewModel: ViewModel() {

    private val detailData = MutableLiveData<ModelUserDetail>()

    private val isError: MutableLiveData<Boolean> = MutableLiveData(false)

    private val errorMessage = MutableLiveData<String>()

    fun setDetail(login: String?){

        val client =AsyncHttpClient()

        val url = "https://api.github.com/users/$login"

        client.addHeader("Authorization", "Bearer //token")
        client.addHeader("User-Agent", "request")
        client.get(url, object : AsyncHttpResponseHandler(){
            override fun onSuccess(statusCode: Int, headers: Array<out Header>?, responseBody: ByteArray?) {
                try {
                    val result = responseBody?.let { String(it) }

                    val res = result?.let { JSONObject(result) }

                    if (res != null){
                        val detailUser = ModelUserDetail(
                                avatar_url = res.getString("avatar_url"),
                                name = checkNullToString(res, "name"),
                                location = checkNullToString(res, "location"),
                                company = checkNullToString(res, "company"),
                                repository = res.getInt("public_repos"),
                                login = checkNullToString(res, "login"),
                                follower = res.getInt("followers"),
                                following = res.getInt("following")
                        )

                        detailData.value = detailUser

                    }
                } catch (e: Exception){
                    e.message?.let { setError(true, it) }
                    e.printStackTrace()
                }
            }

            override fun onFailure(statusCode: Int, headers: Array<out Header>?, responseBody: ByteArray?, error: Throwable?) {
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

    fun getDetail(): LiveData<ModelUserDetail>{
        return detailData
    }

    fun checkNullToString(res:JSONObject, key: String): String {
        return if (res.isNull(key)) "Kosong" else res.getString(key)
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