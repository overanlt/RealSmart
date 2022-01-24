package com.interview.realsmart

import com.google.gson.Gson
import okhttp3.Callback
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Response

class Api {

    interface ResponseResult{
        fun response(httpCode:Int?, res: ResponseLoginModel?)
    }


    fun loginApi (loginModel:LoginModel,onResultListener: ResponseResult){
        val apiInterface = ApiInterface.create().login(loginModel)
        apiInterface.enqueue(object : retrofit2.Callback<ResponseBody?> {
            override fun onResponse(call: Call<ResponseBody?>, response: Response<ResponseBody?>) {
                onResultListener.response(response.code(),Gson().fromJson(response.body()?.string(),ResponseLoginModel ::class.java))
            }

            override fun onFailure(call: Call<ResponseBody?>, t: Throwable) {
                onResultListener.response(0,null)
            }

        })
    }
}