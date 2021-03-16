package com.zulham.gitroom.ui.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.loopj.android.http.AsyncHttpClient
import com.loopj.android.http.AsyncHttpResponseHandler
import com.zulham.gitroom.data.model.ModelRepo
import cz.msebera.android.httpclient.Header
import org.json.JSONArray

class RepoViewModel: ViewModel() {

    private val listRepo = MutableLiveData<ArrayList<ModelRepo>>()

    private val isError: MutableLiveData<Boolean> = MutableLiveData(false)

    private val errorMessage = MutableLiveData<String>()

    fun setRepository(login: String){

        val repo = ArrayList<ModelRepo>()

        val client = AsyncHttpClient()

        val url = "https://api.github.com/users/$login/repos"

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
                        repo.add(
                            ModelRepo(
                                push = jsonObj.getString("pushed_at"),
                                name = jsonObj.getString("name")
                            )
                        )

                    }

                    listRepo.value = repo

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

    fun getRepository(): LiveData<ArrayList<ModelRepo>> {
        return listRepo
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