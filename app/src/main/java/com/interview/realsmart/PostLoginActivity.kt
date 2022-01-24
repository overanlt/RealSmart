package com.interview.realsmart

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.EditText
import android.widget.TextView
import com.google.gson.Gson

class PostLoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_post_login)
        val data = Gson().fromJson(intent.getStringExtra("data"),ResponseLoginModel::class.java)

        val tvAccess =findViewById<TextView>(R.id.tvAccess)
        val tvRefresh =findViewById<TextView>(R.id.tvRefresh)

        tvAccess.text = data.data.accessToken
        tvRefresh.text = data.data.refreshToken

    }
}